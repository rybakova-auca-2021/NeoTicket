package com.example.neoticket.view.main.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.adapters.NotificationsAdapter
import com.example.neoticket.databinding.FragmentNotificationsBinding
import com.example.neoticket.viewModel.notifications.DeleteNotificationsViewModel
import com.example.neoticket.viewModel.notifications.GetNotificationsViewModel
import com.google.android.material.snackbar.Snackbar

class NotificationsFragment : Fragment() {
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationsAdapter
    private lateinit var deleteSnackbar: Snackbar
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val getNotificationViewModel: GetNotificationsViewModel by viewModels()
    private val deleteNotifications: DeleteNotificationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        recyclerView = binding.rvNotifications
        swipeRefreshLayout = binding.swipeRefreshLayout

        (requireActivity() as MainActivity).hideBtmNav()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()
        setupNavigation()
        getNotifications()
        setupSwipeRefreshLayout()
    }

    private fun setupSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            getNotifications()
            swipeRefreshLayout.isRefreshing = false
        }
    }
    private fun setupAdapter() {
        adapter = NotificationsAdapter(emptyList(), requireContext())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : NotificationsAdapter.OnItemClickListener {
            override fun onItemClick(notification: com.example.neoticket.model.Notification) {
                val bundle = Bundle()
                bundle.putInt("id", notification.id)
                adapter.markNotificationAsReadById(notification.id)
                Log.d("NotificationsFragment", "Clicked on notification ${notification.id}")
                findNavController().navigate(R.id.detailNotificationFragment, bundle)
            }
        })

    }

    private fun setupNavigation() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.mainPageFragment)
        }
        binding.btnClear.setOnClickListener {
            deleteNotifications()
        }
    }

    private fun getNotifications() {
        getNotificationViewModel.notificationsLiveData.observe(viewLifecycleOwner, Observer {
                notification ->
            if (notification != null) {
                adapter.updateData(notification)
            }
            binding.rvNotifications.visibility = View.VISIBLE
            binding.btnClear.visibility = View.VISIBLE
            binding.noNotificationsMsg.visibility = View.GONE
            binding.imgNoNotifications.visibility = View.GONE

            if (notification != null) {
                if (notification.isEmpty()) {
                    binding.rvNotifications.visibility = View.GONE
                    binding.btnClear.visibility = View.GONE
                    binding.noNotificationsMsg.visibility = View.VISIBLE
                    binding.imgNoNotifications.visibility = View.VISIBLE
                }
            }
        })
        getNotificationViewModel.getNotifications()
    }



    private fun deleteNotifications() {
        deleteNotifications.deleteAllNotifications(
            onSuccess = {
                binding.imgNoNotifications.visibility = View.VISIBLE
                binding.noNotificationsMsg.visibility = View.VISIBLE
                binding.rvNotifications.visibility = View.GONE

                val rootView: View = binding.root
                val message = "Все уведомления удалены"
                val duration = Snackbar.LENGTH_SHORT

                val snackbar = Snackbar.make(rootView, message, duration)
                snackbar.show()

            },
            onError = {
                binding.imgNoNotifications.visibility = View.VISIBLE
                binding.noNotificationsMsg.visibility = View.VISIBLE
                binding.rvNotifications.visibility = View.GONE
            }
        )
    }
}