package com.example.neoticket.view.main.movie

import DetailImageAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
import com.bumptech.glide.request.RequestOptions
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.Utils.DateUtils
import com.example.neoticket.databinding.FragmentDetailMovieBinding
import com.example.neoticket.model.DetailImage
import com.example.neoticket.viewModel.cinema.MovieDetailViewModel
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class DetailMovieFragment : Fragment() {
    private lateinit var binding: FragmentDetailMovieBinding
    private val viewModel: MovieDetailViewModel by viewModels()
    private lateinit var adapter: DetailImageAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        recyclerView = binding.rvMovieImages
        (requireActivity() as MainActivity).hideBtmNav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        setupAdapter()
        if (id != null) {
            getMovieDetail(id)
            setupNavigation(id)
        }
    }

    private fun setupAdapter() {
        adapter = DetailImageAdapter(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupNavigation(id: Int) {
        adapter.setOnItemClickListener(object : DetailImageAdapter.OnItemClickListener {
            override fun onImageClick(item: DetailImage) {
                val bundle = Bundle()
                bundle.putString("image", item.image)
                findNavController().navigate(R.id.imageDialogFragment, bundle)
            }
        })
        binding.btnMovieDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.action_detailMovieFragment_to_descriptionFragment, bundle)
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.cinemaFragment)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getMovieDetail(id: Int) {
        viewModel.movieDetailLiveData.observe(viewLifecycleOwner, Observer { result ->
            binding.movieTitle.text = result?.title
            val formattedDate = DateUtils.formatRussianDate(result?.release_date, "yyyy-MM-dd")
            binding.movieTime.text = "В прокате с $formattedDate"
            binding.movieDescription.text = result?.description
            val genresNames = result?.genres?.joinToString(", ") { it.name }
            binding.movieCategory.text = genresNames
            if (result != null) {
                binding.movieDuration.text = "${result.country_of_origin}, ${result.film_duration}, ${result.age_limit}+"
            }
            result?.detail_images?.let { adapter.updateData(it) }
            Glide.with(binding.imgOverlay.context)
                .applyDefaultRequestOptions(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .load(result?.image)
                .into(binding.imgOverlay)

            Glide.with(binding.imageView4.context)
                .applyDefaultRequestOptions(
                    RequestOptions.bitmapTransform(
                        RoundedCornersTransformation(15, 5)
                    ))
                .load(result?.image)
                .into(binding.imageView4)

            result?.link_to_video?.let { videoLink ->
                binding.imageView4.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(videoLink))
                    startActivity(intent)
                }
            }
        })
        viewModel.getCinemaDetail(id)
    }
}