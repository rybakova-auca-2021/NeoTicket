package com.example.neoticket.view.main.concerts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SearchView
import com.example.neoticket.databinding.FragmentConcertLocationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ConcertLocationFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentConcertLocationBinding
    private var searchText: String = ""
    private val radioButtons: List<CompoundButton> by lazy {
        listOf(
            binding.rbAsanbay,
            binding.rbCetnterStadion,
            binding.rbSportPalace,
            binding.rbNosorog,
            binding.rbPhilarmonic,
            binding.rbRetroMetro,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConcertLocationBinding.inflate(inflater, container, false)
        radioButtons.forEach { radioButton ->
            radioButton.setOnCheckedChangeListener { _, isChecked ->
                updateChooseButtonState(isChecked)
            }
        }
        search()
        binding.btnChoose.setOnClickListener {
        }
        return binding.root
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