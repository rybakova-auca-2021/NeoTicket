package com.example.neoticket.view.main.theaters

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
import com.example.neoticket.databinding.FragmentTheaterChooseSectorBinding
import com.example.neoticket.view.main.concerts.SectorDialogFragment
import com.example.neoticket.viewModel.theater.TheaterSectionListViewModel


class TheaterChooseSectorFragment : Fragment() {
    private lateinit var binding: FragmentTheaterChooseSectorBinding
    private val viewModel: TheaterSectionListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTheaterChooseSectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val theaterId = arguments?.getInt("id")

        setData()
        setupZoomListeners()
        if (theaterId != null) {
            setupNavigation(theaterId)
            setupSectorClickListeners(theaterId)
        }
    }

    private fun setupNavigation(id: Int) {
        binding.btnBack.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", id)
            findNavController().navigate(R.id.theaterDetailFragment, bundle)
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

    private fun setupSectorClickListeners(theaterId: Int) {
        val sectorIds = listOf(
            1, 2, 3
        )
        val sectorViews = listOf(
            binding.theaterParter, binding.amfiTheater, binding.balcony
        )
        sectorViews.forEachIndexed { index, sectorView ->
            sectorView.setOnClickListener {
                sectorDetailDialog("Сектор ${getSectorName(index)}", sectorIds[index], theaterId)
            }
        }
    }

    private fun getSectorName(index: Int): String {
        val row = 'A' + index / 10
        val seatNumber = (index % 10) + 1
        return "$row$seatNumber"
    }

    private fun sectorDetailDialog(title: String, id: Int, theaterId: Int) {
        val dialog = SectorDialogFragment()
        val bundle = Bundle().apply {
            putString("source", "TheaterSector")
            putString("title", title)
            putInt("id", id)
            putInt("theaterId", theaterId)
        }
        dialog.arguments = bundle
        dialog.show(parentFragmentManager, dialog.tag)
    }

    private fun setData() {
        viewModel.sectionListLiveData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.title.text = result[0].theater_name
                binding.place.text = result[0].place
            }
        }
        viewModel.getSectionList()
    }
}