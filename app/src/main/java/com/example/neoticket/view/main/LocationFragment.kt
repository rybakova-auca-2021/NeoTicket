package com.example.neoticket.view.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.CompoundButton
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.neoticket.databinding.FragmentLocationBinding
import com.example.neoticket.view.main.concerts.OnConcertLocationSelectedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

interface OnCitySelectedListener {
    fun onCitySelected(location: String)
}

class LocationFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentLocationBinding
    private var citySelectedListener: OnCitySelectedListener? = null

    private val radioButtons: List<CompoundButton> by lazy {
        listOf(
            binding.rbBishkek,
            binding.rbTokmok,
            binding.rbOsh,
            binding.rbNaryn,
            binding.rbKarakol,
            binding.rbTalas,
            binding.rbBalykchy
        )
    }

    private var searchText: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        radioButtons.forEach { radioButton ->
            radioButton.setOnCheckedChangeListener { _, isChecked ->
                updateChooseButtonState(isChecked)
            }
        }
        search()

        binding.btnChoose.setOnClickListener {
            val selectedLocation = radioButtons.find { it.isChecked }?.text.toString()
            println(selectedLocation)
            citySelectedListener?.onCitySelected(selectedLocation)
            dismiss()
        }

        return binding.root
    }


    fun setOnLocationSelectedListener(listener: OnCitySelectedListener) {
        citySelectedListener = listener
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

            Log.d("LocationFragment", "Radio Button Text: $radioButtonText, Is Match: $isMatch")

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
