package com.example.neoticket.view.main.movie

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
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentScheduleBinding
import com.example.neoticket.model.ShowTime
import com.example.neoticket.model.StartTime
import com.example.neoticket.viewModel.cinema.GetShowTimeViewModel
import com.harrywhewell.scrolldatepicker.DayScrollDatePicker
import com.harrywhewell.scrolldatepicker.OnDateSelectedListener
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
            setupNavigation(id)
            getValue(id)
        }
    }

    private fun setupAdapter() {
        adapter = ScheduleAdapter(
            emptyList(),
            navigateToTicketPurchasePage = {
                    startTime, item -> navigateToTicketPurchasePage(startTime, item)
            }
        )
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupNavigation(id: Int) {
        binding.imageView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.detailMovieInCinemaFragment, bundle)
        }
    }

    private fun navigateToTicketPurchasePage(startTime: StartTime, showTime: ShowTime) {
        val bundle = Bundle()
        bundle.putInt("movie", showTime.movie)
        bundle.putInt("showTimeId", showTime.id)
        findNavController().navigate(R.id.chooseTicketFragment, bundle)
    }

    private fun getValue(id: Int) {
        mPicker.setStartDate(3, 2, 2024)
//        val currentDate = Date()
//        val startDate = SimpleDateFormat("yyyy-MM-dd").format(currentDate)
        adapter.filterByDateTime("2024-02-03")
        adapter.notifyDataSetChanged()

        mPicker.getSelectedDate(object : OnDateSelectedListener {
            @SuppressLint("SimpleDateFormat")
            override fun onDateSelected(@Nullable date: Date?) {
                date?.let {
                    val selectedDate = SimpleDateFormat("yyyy-MM-dd").format(date)
                    viewModel.showTimeLiveData.observe(viewLifecycleOwner, Observer { result ->
                        if (result != null) {
                            val filteredData = result.filter { it.start_date == selectedDate }
                            adapter.updateData(filteredData)
                        }
                    })
                    viewModel.getShowTimeByMovie(id.toString())
                }
            }
        })
    }


    private fun getShowTime(id: Int) {
        viewModel.showTimeLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                val desiredDate = "2024-02-03"
                val filteredData = result.filter { it.start_date == desiredDate }
                adapter.updateData(filteredData)
            }
        })
        viewModel.getShowTimeByMovie(id.toString())
    }
}