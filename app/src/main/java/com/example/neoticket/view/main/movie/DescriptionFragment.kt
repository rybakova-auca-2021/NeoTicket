package com.example.neoticket.view.main.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentDescriptionBinding
import com.example.neoticket.viewModel.cinema.MovieDetailViewModel
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


class DescriptionFragment : Fragment() {
    private lateinit var binding: FragmentDescriptionBinding
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        if (id != null) {
            getMovieDetail(id)
        }
        binding.btnBack.setOnClickListener {
            val bundle = Bundle()
            if (id != null) {
                bundle.putInt("id", id)
            }
            when (arguments?.getString("sourceFragment") ?: "") {
                "soonInCinema" -> findNavController().navigate(R.id.detailMovieFragment, bundle)
                "nowInCinema" -> findNavController().navigate(R.id.detailMovieInCinemaFragment, bundle)
                else -> findNavController().popBackStack()
            }
        }
    }

    private fun getMovieDetail(id: Int) {
        viewModel.movieDetailLiveData.observe(viewLifecycleOwner, Observer { result ->
            binding.movieDescription.text = result?.description
            binding.yearOfPublication.text = result?.release_date
            binding.country.text = result?.country_of_origin
            binding.producer.text = result?.director
            binding.mainActors.text = result?.main_actors
            val genresNames = result?.genres?.joinToString(", ") { it.name }
            binding.genre.text = genresNames
            binding.duration.text = result?.film_duration
            binding.ageLimit.text = result?.age_limit.toString()
        })
        viewModel.getCinemaDetail(id)
    }
}