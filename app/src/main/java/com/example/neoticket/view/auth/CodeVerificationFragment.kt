package com.example.neoticket.view.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.Utils.Util
import com.example.neoticket.databinding.FragmentCodeVerificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CodeVerificationFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCodeVerificationBinding
    private var countDownTimer: CountDownTimer? = null
    private var timeRemaining: Long = 60000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCodeVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val phone = Util.phone
        binding.codeText.text = "Код был отправлен на номер  $phone"
        setupValidation()
        setupNavigation()
        startCountdownTimer()
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            navigateToProfileFragment()
        }
    }

    private fun navigateToProfileFragment() {
        findNavController().navigate(R.id.profileFragment)
        dismiss()
    }

    private fun setupValidation() {
        val textWatchers = arrayOf(
            binding.pinview
        )

        for (watchedText in textWatchers) {
            watchedText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    val pin = binding.pinview.text.toString()
                    if (pin.length == 4) {
                        // checkCode(userId, pin)
                    }
                }
            })
        }
    }

    private fun startCountdownTimer() {
        countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                if (isAdded) {
                    binding.time.text = "${millisUntilFinished / 1000} сек"
                }
            }

            @SuppressLint("ResourceAsColor")
            override fun onFinish() {
                timeRemaining = 0
                if (isAdded) {
                    binding.btnSendCode.isEnabled = true
                    val whiteColor = resources.getColor(R.color.white)
                    binding.btnSendCode.setTextColor(whiteColor)
                }
            }
        }

        countDownTimer?.start()
    }


    override fun onDestroyView() {
        countDownTimer?.cancel()
        super.onDestroyView()
    }
}