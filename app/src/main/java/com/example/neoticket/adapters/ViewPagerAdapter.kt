package com.example.neoticket.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.neoticket.view.main.movie.NowInCinemaFragment
import com.example.neoticket.view.main.movie.SoonInCinemaFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NowInCinemaFragment()
            1 -> SoonInCinemaFragment()
            else -> Fragment()
        }
    }
}