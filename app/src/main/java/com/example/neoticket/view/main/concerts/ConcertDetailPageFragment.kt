package com.example.neoticket.view.main.concerts

import DetailImageAdapter
import TheaterScheduleAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.Utils.DateUtils
import com.example.neoticket.databinding.FragmentConcertDetailPageBinding
import com.example.neoticket.model.Cinema
import com.example.neoticket.model.CombinedShowTime
import com.example.neoticket.model.DetailImage
import com.example.neoticket.viewModel.concerts.ConcertListViewModel
import jp.wasabeef.glide.transformations.BlurTransformation

class ConcertDetailPageFragment : Fragment() {
    private lateinit var binding: FragmentConcertDetailPageBinding
    private lateinit var rvImages: RecyclerView
    private lateinit var rvSchedule: RecyclerView
    private lateinit var adapter: DetailImageAdapter
    private lateinit var scheduleAdapter: TheaterScheduleAdapter
    private val concertListViewModel: ConcertListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConcertDetailPageBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).hideBtmNav()
        rvImages = binding.rvConcertImages
        rvSchedule = binding.rvDate
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        setupAdapter()
        if (id != null) {
            setupConcertData(id)
            setupNavigation(id)
        }
    }

    private fun setupAdapter() {
        adapter = DetailImageAdapter(emptyList())
        rvImages.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvImages.adapter = adapter

        scheduleAdapter = TheaterScheduleAdapter(emptyList())
        rvSchedule.layoutManager = LinearLayoutManager(requireContext())
        rvSchedule.adapter = scheduleAdapter
    }

    private fun setupNavigation(id: Int) {
        adapter.setOnItemClickListener(object : DetailImageAdapter.OnItemClickListener {
            override fun onImageClick(item: DetailImage) {
                val bundle = Bundle()
                bundle.putString("image", item.image)
                findNavController().navigate(R.id.imageDialogFragment, bundle)
            }
        })

        scheduleAdapter.setOnItemClickListener(object : TheaterScheduleAdapter.OnItemClickListener{
            override fun onItemClick(item: CombinedShowTime) {
                val bundle = Bundle()
                bundle.putInt("id", id)
                findNavController().navigate(R.id.concertChooseSectorFragment, bundle)
            }
        })
        binding.btnConcertDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.action_concertDetailPageFragment_to_concertDescriptionFragment, bundle)
        }
        binding.btnBack.setOnClickListener {
            when (arguments?.getString("sourceFragment")) {
                "mainPageFragment" -> findNavController().navigate(R.id.mainPageFragment)
                "mainConcertPage" -> findNavController().navigate(R.id.mainConcertPageFragment)
                else -> findNavController().navigate(R.id.mainPageFragment)
            }
        }
    }

    private fun setupConcertData(theaterId: Int) {
        concertListViewModel.concertsLiveData.observe(viewLifecycleOwner, Observer { theaters ->
            val theater = theaters?.find { it.id == theaterId }
            binding.concertTitle.text = theater?.title
            binding.moviePlace.text = theater?.place?.name
            val formattedDate = DateUtils.formatRussianDate(theater?.concert_date, "yyyy-MM-dd")
            binding.movieDate.text = formattedDate
            binding.movieDescription.text = theater?.description
            theater?.detail_images?.let { adapter.updateData(it) }
            Glide.with(binding.imgOverlay.context)
                .applyDefaultRequestOptions(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .load(theater?.detail_images?.get(0)?.image)
                .into(binding.imgOverlay)

            Glide.with(binding.imgConcert.context)
                .load(theater?.detail_images?.get(0)?.image)
                .transform(CenterCrop(), RoundedCorners(20))
                .into(binding.imgConcert)

            if (theater != null) {
                scheduleAdapter.updateData(theater.show_times)
            }
        })
        concertListViewModel.getConcerts()
    }

}