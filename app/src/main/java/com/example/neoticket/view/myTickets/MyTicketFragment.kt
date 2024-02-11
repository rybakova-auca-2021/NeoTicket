package com.example.neoticket.view.myTickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.neoticket.adapters.TicketViewPagerAdapter
import com.example.neoticket.databinding.FragmentMyTicketBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator

class MyTicketFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentMyTicketBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyTicketBinding.inflate(inflater, container, false)
        val id = arguments?.getInt("id")

        val tabLayout = binding.tabLayout
        val viewPager2 = binding.fragmentHolder

        viewPager2.adapter = id?.let {
            TicketViewPagerAdapter(parentFragmentManager, lifecycle,
                it
            )
        }

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "QR-код"
                1 -> tab.text = "Штрихкод"
            }
        }.attach()
        return binding.root
    }

}