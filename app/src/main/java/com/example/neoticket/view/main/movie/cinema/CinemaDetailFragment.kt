package com.example.neoticket.view.main.movie.cinema

import DetailImageAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.neoticket.MainActivity
import com.example.neoticket.databinding.FragmentCinemaDetailBinding
import com.example.neoticket.viewModel.cinema.CinemaDetailViewModel
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class CinemaDetailFragment : Fragment() {
    private lateinit var binding: FragmentCinemaDetailBinding
    private val viewModel: CinemaDetailViewModel by viewModels()
    private lateinit var adapter: DetailImageAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCinemaDetailBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).hideBtmNav()
        recyclerView = binding.recyclerView2
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        setupAdapter()
        if (id != null) {
            getCinemaDetail(id)
            setupNavigation(id)
        }
    }

    private fun setupAdapter() {
        adapter = DetailImageAdapter(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter
    }

    private fun setupNavigation(id: Int) {
        binding.btnBack.setOnClickListener {
            val bottomSheetFragment = CinemaListFragment()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
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
        })
        viewModel.getCinemaDetail(id)
    }
}