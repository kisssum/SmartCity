package com.kisssum.smartcity.ui.navigations.me.orderList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kisssum.smartcity.adapter.me.OrderListUnPaidExtendableListAdapter
import com.kisssum.smartcity.databinding.FragmentOrderListUnPaidBinding
import com.kisssum.smartcity.state.BillModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrderListUnPaidFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderListUnPaidFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var billViewModel: BillModel
    private lateinit var binding: FragmentOrderListUnPaidBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentOrderListUnPaidBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initList()
    }

    private fun initViewModel() {
        billViewModel = ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(BillModel::class.java)
    }

    private fun initList() {
        binding.olupList.apply {
            val orderListUnPaidExtendableListAdapter = OrderListUnPaidExtendableListAdapter(requireContext(), billViewModel.getGroupData().value!!, billViewModel.getChildData().value!!)
            this.setAdapter(orderListUnPaidExtendableListAdapter)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrderListUnPaidFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                OrderListUnPaidFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}