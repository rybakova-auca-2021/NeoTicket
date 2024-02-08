package com.example.neoticket.view.main.payment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neoticket.Utils.SwipeTouchListener
import com.example.neoticket.databinding.FragmentConcertChooseSectorBinding

class ConcertChooseSectorFragment : Fragment() {
    private lateinit var binding: FragmentConcertChooseSectorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConcertChooseSectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

}