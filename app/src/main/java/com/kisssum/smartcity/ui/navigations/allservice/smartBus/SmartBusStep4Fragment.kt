package com.kisssum.smartcity.ui.navigations.allservice.smartBus

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentSmartBusStep4Binding
import com.kisssum.smartcity.state.BillModel
import com.kisssum.smartcity.state.SmartBusModel
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SmartBusStep4Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SmartBusStep4Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentSmartBusStep4Binding
    private lateinit var viewModel: SmartBusModel
    private lateinit var billViewModel: BillModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSmartBusStep4Binding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initToolbar()
        resotre()

        binding.sbs4Commit.setOnClickListener {
            val mapGroup = HashMap<String, String>().apply {
                val position = arguments?.getInt("position")!!
                val map = viewModel.getGroupData().value?.get(position)!!

                this["id"] = UUID.randomUUID().toString()
                this["XianName"] = map["XianName"]!!
                this["StartPlace"] = map["StartPlace"]!!
                this["EndPlace"] = map["EndPlace"]!!
                this["TicketPrice"] = map["TicketPrice"]!!
            }

            val mapChild = ArrayList<String>().apply {
                val split = arguments?.getString("dataText")!!.split(" ")
                split.forEach { this.add(it) }
            }

            billViewModel.getGroupData().value?.add(mapGroup)
            billViewModel.getChildData().value?.add(mapChild)

            Toast.makeText(requireContext(), "提交成功!", Toast.LENGTH_SHORT).show()
            binding.sbs4Commit.isEnabled = false

            val control = Navigation.findNavController(requireActivity(), R.id.fragment_main)
            control.setGraph(R.navigation.nav_main)
            control.navigate(R.id.smartBusFragment)
            control.navigate(R.id.orderListFragment)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(SmartBusModel::class.java)

        billViewModel = ViewModelProvider(
                requireActivity(),
                ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(BillModel::class.java)
    }

    private fun initToolbar() {
        binding.sbs4Toolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    private fun resotre() {
        val position = arguments?.getInt("position")
        val map = viewModel.getGroupData().value?.get(position!!)

        binding.sbs4Name.text = "乘车路线:${map?.get("StartPlace")} —— ${map?.get("EndPlace")}"
        binding.sbs4PersonalName.text = "乘客姓名:${arguments?.getString("personalName")}"
        binding.sbs4PhoneNumber.text = "手机号码:${arguments?.getString("numberPhone")}"
        binding.sbs4StartUp.text = "上车地点:${arguments?.getString("startUp")}"
        binding.sbs4EndClose.text = "下车地点:${arguments?.getString("endClose")}"
        binding.sbs4DataText.text = "乘车日期:${arguments?.getString("dataText")}"
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SmartBusStep4Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SmartBusStep4Fragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}