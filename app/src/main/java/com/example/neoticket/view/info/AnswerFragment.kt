package com.example.neoticket.view.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentAnswerBinding
import com.example.neoticket.viewModel.info.GetQuestionAnswerViewModel

class AnswerFragment : Fragment() {
    private lateinit var binding: FragmentAnswerBinding
    private val viewModel: GetQuestionAnswerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnswerBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).hideBtmNav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        if (id != null) {
             getQuestionAnswer(id)
        }
        setupNavigation()
    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.action_answerFragment_to_infoPageFragment)
        }
    }

    private fun getQuestionAnswer(id: Int) {
        viewModel.questionsLiveData.observe(viewLifecycleOwner, Observer {
            result -> binding.infoQuestion.text = result?.question
            binding.infoAnswer.text = result?.answer
        })
        viewModel.getQuestionAnswer(id)
    }
}