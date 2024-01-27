package com.example.neoticket.view.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.adapters.AllQuestionsAdapter
import com.example.neoticket.databinding.FragmentAppInfoBinding
import com.example.neoticket.databinding.FragmentInfoPageBinding

class InfoPageFragment : Fragment() {
    private lateinit var binding: FragmentInfoPageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AllQuestionsAdapter
    // private val viewModel: GetQuestionsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInfoPageBinding.inflate(inflater, container, false)
        recyclerView = binding.rvInfoCards
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = AllQuestionsAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        setupNavigation()
        // getQuestions()
    }

    private fun setupNavigation() {
        binding.cardView2.setOnClickListener {
            findNavController().navigate(R.id.action_infoPageFragment_to_appInfoFragment)
        }

//        adapter.setOnItemClickListener(object : AllQuestionsAdapter.OnItemClickListener {
//            override fun onItemClick(question: Question) {
//                val bundle = Bundle()
//                bundle.putInt("id", question.id)
//                findNavController().navigate(R.id.action_infoFragment_to_answerFragment, bundle)
//            }
//        })
    }

//    private fun getQuestions() {
//        viewModel.getAllQuestion() {
//                question -> adapter.updateData(question)
//        }
//    }
}