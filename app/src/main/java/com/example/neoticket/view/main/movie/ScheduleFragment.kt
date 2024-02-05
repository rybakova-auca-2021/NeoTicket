package com.example.neoticket.view.main.movie

import ScheduleAdapter
import android.annotation.SuppressLint
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
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentScheduleBinding
import com.example.neoticket.model.ShowTime
import com.example.neoticket.model.StartTime
import com.example.neoticket.viewModel.cinema.GetShowTimeViewModel
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.text.SimpleDateFormat
import java.util.Date
import javax.annotation.Nullable


class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var mPicker: DayScrollDatePicker
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ScheduleAdapter
    private val viewModel: GetShowTimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        recyclerView = binding.rvCinemas
        mPicker = binding.date
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        val id = arguments?.getInt("id")
        if (id != null) {
            getShowTime(id)
            getValue()
        }
        setupNavigation()
    }

    private fun setupAdapter() {
        adapter = ScheduleAdapter(emptyList(), this)
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupNavigation() {
        binding.imageView.setOnClickListener {
            findNavController().navigate(R.id.detailMovieInCinemaFragment)
        }
    }

    fun navigateToTicketPurchasePage(startTime: StartTime, showTime: ShowTime) {
        val bundle = Bundle()
        bundle.putString("time", startTime.time)
        bundle.putInt("movie", showTime.movie)
        bundle.putInt("showTimeId", showTime.id)
        findNavController().navigate(R.id.chooseTicketFragment, bundle)
    }

    private fun getValue() {
        val currentDate = Date()
        val startDate = SimpleDateFormat("yyyy-MM-dd").format(currentDate)
        adapter.filterByDateTime(startDate)
        adapter.notifyDataSetChanged()

        mPicker.getSelectedDate(object : OnDateSelectedListener {
            @SuppressLint("SimpleDateFormat")
            override fun onDateSelected(@Nullable date: Date?) {
                date?.let {
                    val selectedDate = SimpleDateFormat("yyyy-MM-dd").format(date)
                    adapter.filterByDateTime(selectedDate)
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }


    private fun getShowTime(id: Int) {
        viewModel.showTimeLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
                val filteredData = result.filter { it.start_date == currentDate }
                adapter.updateData(filteredData)
            }
        })
        viewModel.getShowTimeByMovie(id.toString())
    }
}