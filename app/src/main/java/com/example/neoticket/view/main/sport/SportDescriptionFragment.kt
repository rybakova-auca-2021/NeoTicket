package com.example.neoticket.view.main.sport

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentSportDescriptionBinding
import com.example.neoticket.viewModel.sport.SportDetailViewModel

class SportDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentSportDescriptionBinding
    private val viewModel: SportDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSportDescriptionBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).hideBtmNav()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sportId = arguments?.getInt("id")
        if (sportId != null) {
            setupData(sportId)
            setupNavigation(sportId)
        }
    }

    private fun setupNavigation(id: Int) {
        binding.btnBack.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.sportDetailFragment, bundle)
        }
    }

    private fun setupData(id: Int) {
        viewModel.getSportDetail(id)
        viewModel.sportDetailLiveData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.sportDescription.text = result.description
                binding.sportDate.text = result.sport_date
                binding.sportPlace.text = result.place.name
                binding.sportTime.text = result.time
                binding.ageLimit.text = "${result.age_limit}+"
            }
        }
    }
}