package com.example.neoticket.view.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.Utils.Util
import com.example.neoticket.databinding.FragmentCodeVerificationBinding
import com.example.neoticket.viewModel.auth.CheckCodeViewModel
import com.example.neoticket.viewModel.auth.LoginOrRegisterViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.math.log

class CodeVerificationFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentCodeVerificationBinding
    private val viewModel: CheckCodeViewModel by viewModels()
    private var countDownTimer: CountDownTimer? = null
    private val loginViewModel: LoginOrRegisterViewModel by viewModels()
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
        val email = arguments?.getString("email")
        binding.codeText.text = "Код был отправлен на почту  $email"
        if (email != null) {
            setupValidation(email)
            startCountdownTimer(email)
        }
        setupNavigation()

    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            navigateToProfileFragment()
        }
    }

    private fun navigateToProfileFragment() {
        dismiss()
    }

    private fun setupValidation(email: String) {
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
                         checkCode(pin, email)
                    }
                }
            })
        }
    }

    private fun register(email: String) {
        loginViewModel.register(email,
            onSuccess = {
                        println("code was sent")
            },
            onError = {
                // Handle error
            }
        )
    }

    private fun startCountdownTimer(email: String) {
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
                    binding.btnSendCode.setOnClickListener {
                        register(email)
                    }
                }
            }
        }

        countDownTimer?.start()
    }

    private fun checkCode(pin: String, email: String) {
        viewModel.checkCode(
            pin,
            onSuccess = {
                if (Util.isUserRegistered == false) {
                    dismiss()
                    val bottomSheetFragment = AdditionalInfoFragment().apply {
                        val args = Bundle().apply {
                            putString("email", email)
                        }
                        arguments = args
                    }
                    bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
                } else if (Util.isUserRegistered == true) {
                    navigateToProfileFragment()
                }
            },
            onError = {
                binding.pinview.setTextColor(resources.getColor(R.color.main_orange))
                binding.wrongCodeMsg.visibility = View.VISIBLE
            }
        )
    }

    override fun onDestroyView() {
        countDownTimer?.cancel()
        super.onDestroyView()
    }
}