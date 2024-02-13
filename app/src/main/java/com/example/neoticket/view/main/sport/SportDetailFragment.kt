package com.example.neoticket.view.main.sport

import DetailImageAdapter
import TheaterScheduleAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.neoticket.databinding.FragmentSportDetailBinding
import com.example.neoticket.model.CombinedShowTime
import com.example.neoticket.model.DetailImage
import com.example.neoticket.viewModel.sport.SportListViewModel
import jp.wasabeef.glide.transformations.BlurTransformation

class SportDetailFragment : Fragment() {
    private lateinit var binding: FragmentSportDetailBinding
    private lateinit var rvImages: RecyclerView
    private lateinit var rvSchedule: RecyclerView
    private lateinit var adapter: DetailImageAdapter
    private lateinit var scheduleAdapter: TheaterScheduleAdapter
    private val sportViewModel: SportListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportDetailBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).hideBtmNav()
        rvImages = binding.rvSportImages
        rvSchedule = binding.rvDate
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sportId = arguments?.getInt("id")
        setupAdapter()
        if (sportId != null) {
            setupSportData(sportId)
            setupNavigation(sportId)
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
                findNavController().navigate(R.id.sportChooseSectorFragment)
            }
        })
        binding.btnSportDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.action_sportDetailFragment_to_sportDescriptionFragment, bundle)
        }
        binding.btnBack.setOnClickListener {
            when (arguments?.getString("sourceFragment")) {
                "mainPageFragment" -> findNavController().navigate(R.id.mainPageFragment)
                "mainSportPage" -> findNavController().navigate(R.id.mainSportFragment)
                else -> findNavController().navigate(R.id.mainSportFragment)
            }

        }
    }

    private fun setupSportData(sportId: Int) {

        sportViewModel.sportLiveData.observe(viewLifecycleOwner, Observer { sports ->
            val sport = sports?.find { it.id == sportId }
            binding.sportTitle.text = sport?.title
            binding.sportPlace.text = sport?.place?.name
            val formattedDate = DateUtils.formatRussianDate(sport?.sport_date, "yyyy-MM-dd")
            binding.sportDate.text = formattedDate
            binding.sportDescription.text = sport?.description
            sport?.detail_images?.let { adapter.updateData(it) }
            Glide.with(binding.imgOverlay.context)
                .applyDefaultRequestOptions(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .load(sport?.detail_images?.get(0)?.image)
                .into(binding.imgOverlay)

            Glide.with(binding.imgSport.context)
                .load(sport?.detail_images?.get(0)?.image)
                .transform(CenterCrop(), RoundedCorners(20))
                .into(binding.imgSport)

            if (sport != null) {
                scheduleAdapter.updateData(sport.show_times)
            }
        })
        sportViewModel.getSportList()
    }


}