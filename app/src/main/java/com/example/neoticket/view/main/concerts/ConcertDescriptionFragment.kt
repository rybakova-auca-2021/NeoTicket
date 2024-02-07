package com.example.neoticket.view.main.concerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentConcertDescriptionBinding
import com.example.neoticket.viewModel.concerts.ConcertDetailViewModel

class ConcertDescriptionFragment : Fragment() {
    private lateinit var binding: FragmentConcertDescriptionBinding
    private val viewModel: ConcertDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConcertDescriptionBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).hideBtmNav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        if (id != null) {
            setupData(id)
            setupNavigation(id)
        }
    }

    private fun setupNavigation(id: Int) {
        binding.btnBack.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.concertDetailPageFragment, bundle)
        }
    }

    private fun setupData(id: Int) {
        viewModel.getConcertDetail(id)
        viewModel.concertDetailLiveData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.concertDescription.text = result.description
                binding.concertDate.text = result.concert_date
                binding.concertPlace.text = result.place.name
                binding.concertArtist.text = result.artist
                binding.ageLimit.text = "${result.age_limit}+"
            }
        }
    }
}