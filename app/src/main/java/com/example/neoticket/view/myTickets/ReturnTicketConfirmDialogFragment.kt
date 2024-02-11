package com.example.neoticket.view.myTickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentReturnTicketConfirmDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ReturnTicketConfirmDialogFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentReturnTicketConfirmDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReturnTicketConfirmDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderId = arguments?.getInt("orderId")
        val id = arguments?.getInt("id")
        val type = arguments?.getString("type")
        val price = arguments?.getString("totalPrice")
        binding.textReturnBack.text = "Сумма возврата $price c"
        if (orderId != null && type != null && id != null) {
            setupNavigation(id, orderId, type)
        }
    }

    private fun setupNavigation(id: Int, order: Int, type: String) {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnContinue.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("orderId", order)
            bundle.putInt("id", id)
            bundle.putString("type", type)
            findNavController().navigate(R.id.returnTicketConfirmFragment, bundle)
            dismiss()
        }
    }
}