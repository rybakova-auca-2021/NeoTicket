package com.example.neoticket.view.main.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentDetailNotificationBinding
import com.example.neoticket.viewModel.notifications.DeleteNotificationByIdViewModel
import com.example.neoticket.viewModel.notifications.GetNotificationByIdViewModel
import com.google.android.material.snackbar.Snackbar

class DetailNotificationFragment : Fragment() {
    private lateinit var binding: FragmentDetailNotificationBinding
    private val deleteNotificationViewModel: DeleteNotificationByIdViewModel by viewModels()
    private val viewModel: GetNotificationByIdViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        if (id != null) {
            getPromotionDetail(id)
        }
        if (id != null) {
            setupNavigation(id)
        }
    }

    private fun setupNavigation(id: Int) {
        binding.btnBack.setOnClickListener{
            findNavController().navigate(R.id.notificationsFragment)
        }
        binding.btnClear.setOnClickListener {
            deleteNotification(id)
        }
    }

    private fun deleteNotification(id: Int) {
        deleteNotificationViewModel.deleteNotifications(
            id,
            onSuccess = {
                val rootView: View = binding.root
                val message = "Уведомления удалено"
                val duration = Snackbar.LENGTH_SHORT

                val snackbar = Snackbar.make(rootView, message, duration)
                snackbar.show()
                findNavController().navigate(R.id.notificationsFragment)
            },
            onError = {
                findNavController().navigate(R.id.notificationsFragment)
            }
        )
    }

    private fun getPromotionDetail(id: Int) {
        viewModel.getNotificationDetail(id)
        viewModel.notificationsLiveData.observe(viewLifecycleOwner, Observer {
                detail ->
            binding.notificationTitle.text = detail?.title
            binding.notificationText.text = detail?.body
        })
    }
}