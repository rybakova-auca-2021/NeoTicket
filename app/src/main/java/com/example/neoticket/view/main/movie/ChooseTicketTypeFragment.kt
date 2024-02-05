package com.example.neoticket.view.main.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.neoticket.databinding.FragmentChooseTicketTypeBinding
import com.example.neoticket.viewModel.cinema.GetTicketTypesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseTicketTypeFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChooseTicketTypeBinding
    private val viewModel: GetTicketTypesViewModel by viewModels()

    companion object {
        fun newInstance(rowNumber: Int, seatNumber: Int): ChooseTicketTypeFragment {
            val fragment = ChooseTicketTypeFragment()
            val args = Bundle()
            args.putInt("rowNumber", rowNumber)
            args.putInt("seatNumber", seatNumber)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseTicketTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rowNumber = arguments?.getInt("rowNumber", 0) ?: 0
        val seatNumber = arguments?.getInt("seatNumber", 0) ?: 0
        binding.textChoosenSeat.text = "$rowNumber ряд, $seatNumber место"
        getTicketTypes()
    }

    private fun getTicketTypes() {
        viewModel.ticketTypeLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                binding.childPrice.text = result[0].price
                binding.studentPrice.text = result[1].price
                binding.adultPrice.text = result[2].price
            }
        })
        viewModel.getTicketTypes()
    }


}