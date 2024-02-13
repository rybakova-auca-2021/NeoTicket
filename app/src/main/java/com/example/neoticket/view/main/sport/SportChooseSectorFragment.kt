package com.example.neoticket.view.main.sport

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.Utils.SwipeTouchListener
import com.example.neoticket.databinding.FragmentConcertChooseSectorBinding
import com.example.neoticket.databinding.FragmentSportChooseSectorBinding
import com.example.neoticket.view.main.concerts.SectorDialogFragment
import com.example.neoticket.viewModel.sport.GetSportSectionListViewModel

class SportChooseSectorFragment : Fragment() {
    private lateinit var binding: FragmentSportChooseSectorBinding
    private val viewModel: GetSportSectionListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSportChooseSectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        if (id != null) {
            setupNavigation(id)
        }
        setData()
        setupZoomListeners()
        setupSectorClickListeners()
    }

    private fun setupNavigation(id: Int) {
        binding.btnBack.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.sportDetailFragment, bundle)
        }
    }

    private fun setupZoomListeners() {
        binding.btnZoomIn.setOnClickListener { zoomIn() }
        binding.btnZoomOut.setOnClickListener { zoomOut() }
        binding.cardSector.setOnTouchListener(SwipeTouchListener(binding.cardSector))
        binding.cardSector.requestDisallowInterceptTouchEvent(true)
    }

    private fun zoomIn() {
        val newScaleFactor = binding.cardSector.scaleX + 0.1f
        binding.cardSector.scaleX = newScaleFactor
        binding.cardSector.scaleY = newScaleFactor
    }

    private fun zoomOut() {
        val newScaleFactor = binding.cardSector.scaleX - 0.1f
        binding.cardSector.scaleX = newScaleFactor
        binding.cardSector.scaleY = newScaleFactor
    }

    private fun setupSectorClickListeners() {
        val sectorIds = listOf(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27
        )
        val sectorViews = listOf(
            binding.sectorA1, binding.sectorA2, binding.sectorA3, binding.sectorA4,
            binding.sectorB1, binding.sectorB2, binding.sectorB3, binding.sectorB4,
            binding.sectorB5, binding.sectorB6, binding.sectorB7, binding.sectorB8,
            binding.sectorB9, binding.sectorB10, binding.sectorC1, binding.sectorC2,
            binding.sectorC3, binding.sectorC4, binding.sectorC5, binding.sectorC6,
            binding.sectorC7, binding.sectorC8, binding.sectorC9, binding.sectorC10,
            binding.sectorC11, binding.sectorC12, binding.sectorC13
        )
        sectorViews.forEachIndexed { index, sectorView ->
            sectorView.setOnClickListener {
                sectorDetailDialog("Сектор ${getSectorName(index)}", sectorIds[index])
            }
        }
    }

    private fun getSectorName(index: Int): String {
        val row = 'A' + index / 10
        val seatNumber = (index % 10) + 1
        return "$row$seatNumber"
    }

    private fun sectorDetailDialog(title: String, id: Int) {
        val dialog = SectorDialogFragment()
        val bundle = Bundle().apply {
            putString("source", "SportSector")
            putString("title", title)
            putInt("id", id)
        }
        dialog.arguments = bundle
        dialog.show(parentFragmentManager, dialog.tag)
    }

    private fun setData() {
        viewModel.sectionListLiveData.observe(viewLifecycleOwner) { result ->
            if (result != null && result.isNotEmpty()) {
                binding.artist.text = result[0].sport_name
                binding.place.text = result[0].place
            }
        }
        viewModel.getSectionList()
    }
}
