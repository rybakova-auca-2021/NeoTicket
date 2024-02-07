package com.example.neoticket.view.main.theaters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentTheaterDescriptionBinding
import com.example.neoticket.viewModel.theater.TheaterDetailViewModel


class TheaterDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentTheaterDescriptionBinding
    private val viewModel: TheaterDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTheaterDescriptionBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).hideBtmNav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val theaterId = arguments?.getInt("id")
        if (theaterId != null) {
            setupData(theaterId)
            setupNavigation(theaterId)
        }
    }

    private fun setupNavigation(id: Int) {
        binding.btnBack.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.theaterDetailFragment, bundle)
        }
    }

    private fun setupData(id: Int) {
        viewModel.getTheaterDetail(id)
        viewModel.theaterDetailLiveData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.theaterDescription.text = result.description
                binding.yearOfPublication.text = result.year_of_issue
                binding.country.text = result.country_of_origin
                binding.producer.text = result.composer
                binding.mainActors.text = result.main_actors
                binding.genre.text = result.genre.name
                binding.duration.text = result.duration
                binding.ageLimit.text = "${result.age_limit}+"
            }
        }
    }
}