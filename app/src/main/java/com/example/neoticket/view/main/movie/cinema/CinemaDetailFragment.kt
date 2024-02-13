package com.example.neoticket.view.main.movie.cinema

import DetailImageAdapter
import ScheduleMovieAdapter
import android.annotation.SuppressLint
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
import com.bumptech.glide.request.RequestOptions
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentCinemaDetailBinding
import com.example.neoticket.model.CinemaShowTime
import com.example.neoticket.model.DetailImage
import com.example.neoticket.model.MovieDetail
import com.example.neoticket.model.ShowTime
import com.example.neoticket.model.StartTime
import com.example.neoticket.viewModel.cinema.CinemaDetailViewModel
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.text.SimpleDateFormat
import java.util.Date
import javax.annotation.Nullable

class CinemaDetailFragment : Fragment() {
    private lateinit var binding: FragmentCinemaDetailBinding
    private val viewModel: CinemaDetailViewModel by viewModels()
    private lateinit var adapter: DetailImageAdapter
    private lateinit var showTimeAdapter: ScheduleMovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var rvShowTime: RecyclerView
    private lateinit var mPicker: DayScrollDatePicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCinemaDetailBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).hideBtmNav()
        recyclerView = binding.recyclerView2
        rvShowTime = binding.rvMovies
        mPicker = binding.date
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        setupAdapter()
        getValue()
        if (id != null) {
            getCinemaDetail(id)
            setupNavigation()
        }
    }

    private fun setupAdapter() {
        adapter = DetailImageAdapter(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        showTimeAdapter = ScheduleMovieAdapter(
            emptyList(),
            navigateToTicketPurchasePage = {
                    startTime, item -> navigateToTicketPurchasePage(startTime, item)
            }
        )
        rvShowTime.layoutManager = LinearLayoutManager(requireContext())
        rvShowTime.adapter = showTimeAdapter
    }

    private fun navigateToTicketPurchasePage(startTime: StartTime, showTime: CinemaShowTime) {
        val bundle = Bundle()
        bundle.putInt("movie", showTime.movie)
        bundle.putInt("showTimeId", showTime.id)
        findNavController().navigate(R.id.chooseTicketFragment, bundle)
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            val bottomSheetFragment = CinemaListFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
        adapter.setOnItemClickListener(object : DetailImageAdapter.OnItemClickListener {
            override fun onImageClick(item: DetailImage) {
                val bundle = Bundle()
                bundle.putString("image", item.image)
                findNavController().navigate(R.id.imageDialogFragment, bundle)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun getCinemaDetail(id: Int) {
        viewModel.cinemaDetailLiveData.observe(viewLifecycleOwner, Observer { result ->
            binding.cinemaDescription.text = result?.description
            binding.cinemaName.text = result?.name
            result?.detail_images?.let { adapter.updateData(it) }
            Glide.with(binding.cinemaImg.context)
                .applyDefaultRequestOptions(
                    RequestOptions.bitmapTransform(
                        RoundedCornersTransformation(15, 5)
                    ))
                .load(result?.image)
                .into(binding.cinemaImg)
            if (result != null) {
                val filteredShowTimes = result.show_times.filter { showTime ->
                    showTime.start_date == "2024-02-03"
                }
                showTimeAdapter.updateData(filteredShowTimes)
            }
        })
        viewModel.getCinemaDetail(id)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getValue() {
        mPicker.setStartDate(3, 2, 2024)
        showTimeAdapter.filterByDateTime("2024-02-03")
        showTimeAdapter.notifyDataSetChanged()

        mPicker.getSelectedDate { date ->
            date?.let {
                val selectedDate = SimpleDateFormat("yyyy-MM-dd").format(date)
                showTimeAdapter.filterByDateTime(selectedDate)
                showTimeAdapter.notifyDataSetChanged()
            }
        }
    }
}