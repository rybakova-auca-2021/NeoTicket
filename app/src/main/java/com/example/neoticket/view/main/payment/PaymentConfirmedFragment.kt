package com.example.neoticket.view.main.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentPaymentConfirmedBinding

class PaymentConfirmedFragment : Fragment() {
    private lateinit var binding: FragmentPaymentConfirmedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentConfirmedBinding.inflate(inflater, container, false)
        return binding.root
    }
}