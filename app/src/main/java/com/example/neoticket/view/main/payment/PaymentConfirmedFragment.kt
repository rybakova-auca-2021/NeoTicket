package com.example.neoticket.view.main.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentPaymentConfirmedBinding
import com.example.neoticket.view.profile.ProfileDataViewModel

class PaymentConfirmedFragment : Fragment() {
    private lateinit var binding: FragmentPaymentConfirmedBinding
    private val viewModel: ProfileDataViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentConfirmedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val totalPrice = arguments?.getString("total_price")
        val ticketType = arguments?.getString("ticket_type")
        binding.ticketCategory.text = ticketType
        binding.payment.text = totalPrice
        setupCard()
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_paymentConfirmedFragment_to_paymentFragment)
        }
        binding.btnPay.setOnClickListener {
            findNavController().navigate(R.id.myTicketsFragment)
        }
    }

    private fun setupCard() {
        viewModel.getCard { cardList ->
            cardList.let {
                if (it.isNotEmpty()) {
                    val card = it[0]
                    binding.textCardMoney.text = card.balance
                    val lastFourDigits = card.account_number.takeLast(4)
                    binding.textCardNumber.text = "Платежная карта $lastFourDigits"
                } else {
                    binding.card.visibility = View.GONE
                }
            }
        }
    }
}