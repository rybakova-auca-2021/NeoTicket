package com.example.neoticket.view.main.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.R
import com.example.neoticket.Utils.QRCodeUtils
import com.example.neoticket.Utils.Util
import com.example.neoticket.adapters.TicketAdapter
import com.example.neoticket.databinding.FragmentConfirmTicketSeatsBinding
import com.example.neoticket.room.MyApplication
import com.example.neoticket.room.TicketDao
import com.example.neoticket.room.TicketData
import com.example.neoticket.view.auth.RegisterFragment
import com.example.neoticket.viewModel.cinema.CreateTicketViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConfirmTicketSeatsFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentConfirmTicketSeatsBinding
    private val viewModel: CreateTicketViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TicketAdapter
    private val ticketDao: TicketDao by lazy {
        (requireActivity().application as MyApplication).database.ticketDao()
    }

    companion object {
        fun newInstance(showTime: Int): ConfirmTicketSeatsFragment {
            val fragment = ConfirmTicketSeatsFragment()
            val args = Bundle()
            args.putInt("showTimeId", showTime)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmTicketSeatsBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getTickets()
    }

    private fun setupRecyclerView() {
        adapter = TicketAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun createTicket(ticketDataList: List<TicketData>) {
        val showTimeId = arguments?.getInt("showTimeId", 0) ?: 0
        binding.btnNext.setOnClickListener {
            if (Util.token != null) {
                viewModel.createTicketLiveData.observe(viewLifecycleOwner, Observer { result ->
                    if (result != null) {
                        val bundle = Bundle()
                        bundle.putInt("order", result.order)
                        bundle.putString("source", "movieTicket")
                        findNavController().navigate(R.id.confirmPageFragment, bundle)
                    }
                })

                val seats = ticketDataList.map { it.id }
                val type = ticketDataList[0].ticketType

                val qrCodeData = "QR Code for registration"
                val barCodeData = "Bar Code for registration"
                val qrCodeBitmap = QRCodeUtils.textToImageEncode(qrCodeData, 200)
                val barCodeBitmap = QRCodeUtils.BarcodeUtils.generateBarcode(barCodeData, 200, 100)
                if (qrCodeBitmap != null && barCodeBitmap != null) {
                    val qrCodeImagePath = QRCodeUtils.saveImage(requireContext(), qrCodeBitmap)
                    val barCodeImagePath = QRCodeUtils.saveImage(requireContext(), barCodeBitmap)
                    if (qrCodeImagePath.isNotEmpty()) {
                        Util.id?.let { it1 ->
                            viewModel.createTicket(
                                    showTimeId,
                                    seats,
                                    it1,
                                    type,
                                    qrCodeImagePath,
                                    barCodeImagePath
                                )
                        }
                    }
                }
            } else {
                val bottomSheetFragment = RegisterFragment()
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            }
        }
    }

    private fun getTickets() {
        CoroutineScope(Dispatchers.IO).launch {
            val tickets = ticketDao.getAllTickets()
            withContext(Dispatchers.Main) {
                if (tickets.isNotEmpty()) {
                    binding.numOfTickets.text = "${tickets.size} билет"
                    adapter.updateData(tickets)
                    createTicket(tickets)
                    deleteTicket()
                }
            }
        }
    }

    private fun deleteTicket() {
        adapter.setOnItemClickListener(object : TicketAdapter.OnItemClickListener {
            override fun onItemClick(item: TicketData) {
                CoroutineScope(Dispatchers.IO).launch {
                    ticketDao.deleteTicket(item.rowNumber, item.seatNumber, item.ticketType)
                    println("Item was deleted")
                    val updatedTickets = ticketDao.getAllTickets()
                    withContext(Dispatchers.Main) {
                        if (updatedTickets.isNotEmpty()) {
                            binding.numOfTickets.text = "${updatedTickets.size} билет"
                            adapter.updateData(updatedTickets)
                        } else {
                            Toast.makeText(requireContext(), "All tickets deleted", Toast.LENGTH_SHORT).show()
                            dismiss()
                        }
                    }
                }
            }
        })
    }
}
