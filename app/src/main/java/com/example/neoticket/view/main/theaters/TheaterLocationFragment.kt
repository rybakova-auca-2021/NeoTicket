package com.example.neoticket.view.main.theaters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SearchView
import com.example.neoticket.databinding.FragmentTheaterLocationBinding
import com.example.neoticket.view.main.sport.OnLocationSelectedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface OnTheaterLocationSelectedListener {
    fun onLocationSelected(location: String)
}

class TheaterLocationFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentTheaterLocationBinding
    private var searchText: String = ""
    private var locationSelectedListener: OnTheaterLocationSelectedListener? = null

    private val radioButtons: List<CompoundButton> by lazy {
        listOf(
            binding.theaterOpera,
            binding.philarmonic,
            binding.russianTheater,
            binding.dramaticTheater,
            binding.dollTheater,
            binding.academicDramaticTheater,
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTheaterLocationBinding.inflate(inflater, container, false)
        radioButtons.forEach { radioButton ->
            radioButton.setOnCheckedChangeListener { _, isChecked ->
                updateChooseButtonState(isChecked)
            }
        }
        search()
        binding.btnChoose.setOnClickListener {
            val selectedLocation = radioButtons.find { it.isChecked }?.text.toString()
            println(selectedLocation)
            locationSelectedListener?.onLocationSelected(selectedLocation)
            dismiss()
        }

        return binding.root
    }

    fun setOnLocationSelectedListener(listener: OnTheaterLocationSelectedListener) {
        locationSelectedListener = listener
    }

    private fun updateChooseButtonState(isAnyRadioButtonChecked: Boolean) {
        binding.btnChoose.isEnabled = isAnyRadioButtonChecked
    }

    private fun search() {
        binding.btnSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchText = newText.orEmpty()
                filterRadioButtons()
                return true
            }
        })
    }

    private fun filterRadioButtons() {
        val lowerCaseSearchText = searchText.toLowerCase()

        radioButtons.forEach { radioButton ->
            val radioButtonText = radioButton.text.toString().toLowerCase()
            val isMatch = radioButtonText.contains(lowerCaseSearchText)

            radioButton.visibility = if (isMatch) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
        binding.imgNothingWasFound.visibility = if (radioButtons.any { it.visibility == View.VISIBLE }) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}