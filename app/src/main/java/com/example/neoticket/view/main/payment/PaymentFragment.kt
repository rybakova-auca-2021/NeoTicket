package com.example.neoticket.view.main.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.Utils.Util
import com.example.neoticket.databinding.FragmentPaymentBinding
import com.example.neoticket.view.profile.ProfileDataViewModel
import com.example.neoticket.viewModel.cinema.OrderMovieDetailViewModel

class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding
    private val viewModel: ProfileDataViewModel by viewModels()
    private val checkOutViewModel: OrderMovieDetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ticketType = arguments?.getString("ticket_type")
        val totalPrice = arguments?.getString("total_price")
        val order = arguments?.getInt("movie_order")
        binding.ticketCategory.text = ticketType
        binding.payment.text = totalPrice
        setupCard()
        if (order != null && totalPrice != null) {
            setupNavigation(order, totalPrice)
        }
    }

    private fun setupNavigation(order: Int, total_price: String) {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_paymentFragment_to_confirmPageFragment)
        }
        binding.btnPay.setOnClickListener {
            checkoutOrder(order, total_price)
        }
    }

    private fun checkoutOrder(order: Int, total_price: String) {
        checkOutViewModel.orderMovieLiveData.observe(viewLifecycleOwner, Observer {
            result ->
            if (result != null) {
                val bundle = Bundle()
                bundle.putString("ticket_type", "Кинотеатры")
                bundle.putString("total_price", total_price)
                findNavController().navigate(R.id.action_paymentFragment_to_paymentConfirmedFragment, bundle)
            }
        })
        Util.id?.let { checkOutViewModel.orderMovie(it, order) }
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