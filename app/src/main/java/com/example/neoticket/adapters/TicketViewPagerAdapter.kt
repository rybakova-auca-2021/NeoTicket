package com.example.neoticket.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.neoticket.view.myTickets.TicketCodeFragment
import com.example.neoticket.view.myTickets.TicketQrFragment

class TicketViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TicketQrFragment()
            1 -> TicketCodeFragment()
            else -> Fragment()
        }
    }
}