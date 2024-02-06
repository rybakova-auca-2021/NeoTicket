package com.example.neoticket.view.main.movie

import DetailImageAdapter
import ScheduleAdapter
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
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentDetailMovieInCinemaBinding
import com.example.neoticket.model.ShowTime
import com.example.neoticket.model.StartTime
import com.example.neoticket.viewModel.cinema.GetShowTimeViewModel
import com.example.neoticket.viewModel.cinema.MovieDetailViewModel
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.text.SimpleDateFormat
import java.util.Date
import javax.annotation.Nullable

class DetailMovieInCinemaFragment : Fragment() {
    private lateinit var binding: FragmentDetailMovieInCinemaBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private val timeViewModel: GetShowTimeViewModel by viewModels()
    private lateinit var adapter: DetailImageAdapter
    private lateinit var scheduleAdapter: ScheduleAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var scheduleRV: RecyclerView
    private lateinit var mPicker: DayScrollDatePicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailMovieInCinemaBinding.inflate(inflater, container, false)
        recyclerView = binding.rvMovieImages
        scheduleRV = binding.rvCinemas
        mPicker = binding.date
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        setupAdapter()
        getValue()
        if (id != null) {
            getMovieDetail(id)
            getShowTime(id)
            setupNavigation(id)
        }
    }

    private fun setupAdapter() {
        adapter = DetailImageAdapter(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        scheduleAdapter = ScheduleAdapter(
            emptyList(),
            navigateToTicketPurchasePage = {
                    startTime, item -> navigateToTicketPurchasePage(startTime, item)
            }
        )
        scheduleRV.layoutManager =
            LinearLayoutManager(requireContext())
        scheduleRV.adapter = scheduleAdapter
    }

    private fun setupNavigation(id: Int) {
        binding.btnMovieDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.action_detailMovieInCinemaFragment_to_descriptionFragment, bundle)
        }
        binding.btnSchedule.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.scheduleFragment, bundle)
        }
        binding.btnBack.setOnClickListener {
            when (arguments?.getString("sourceFragment")) {
                "mainPageFragment" -> findNavController().navigate(R.id.mainPageFragment)
                "nowInCinema" -> findNavController().navigate(R.id.cinemaFragment)
            }
        }

    }

    private fun navigateToTicketPurchasePage(startTime: StartTime, showTime: ShowTime) {
        val bundle = Bundle()
        bundle.putInt("movie", showTime.movie)
        bundle.putInt("showTimeId", showTime.id)
        findNavController().navigate(R.id.chooseTicketFragment, bundle)
    }

    private fun getMovieDetail(id: Int) {
        viewModel.movieDetailLiveData.observe(viewLifecycleOwner, Observer { result ->
            binding.movieTitle.text = result?.title
            binding.movieTime.text = result?.release_date
            binding.movieRate.text = result?.rating.toString()
            binding.movieDescription.text = result?.description
            result?.detail_images?.let { adapter.updateData(it) }
            Glide.with(binding.imgOverlay.context)
                .applyDefaultRequestOptions(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .load(result?.image)
                .into(binding.imgOverlay)

            Glide.with(binding.imageView4.context)
                .applyDefaultRequestOptions(RequestOptions.bitmapTransform(RoundedCornersTransformation(15, 5)))
                .load(result?.image)
                .into(binding.imageView4)
        })
        viewModel.getCinemaDetail(id)
    }

    private fun getValue() {
        mPicker.setStartDate(3, 2, 2024)
//        val currentDate = Date()
//        val startDate = SimpleDateFormat("yyyy-MM-dd").format(currentDate)
        scheduleAdapter.filterByDateTime("2024-02-03")
        scheduleAdapter.notifyDataSetChanged()

        mPicker.getSelectedDate(object : OnDateSelectedListener {
            @SuppressLint("SimpleDateFormat")
            override fun onDateSelected(@Nullable date: Date?) {
                date?.let {
                    val selectedDate = SimpleDateFormat("yyyy-MM-dd").format(date)
                    scheduleAdapter.filterByDateTime(selectedDate)
                    scheduleAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun getShowTime(id: Int) {
        timeViewModel.showTimeLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                val desiredDate = "2024-02-03"
                val filteredData = result.filter { it.start_date == desiredDate }
                scheduleAdapter.updateData(filteredData)
            }
        })
        timeViewModel.getShowTimeByMovie(id.toString())
    }
}