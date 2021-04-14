package com.kisssum.smartcity.ui.navigations.allservice.livingExpenses.accountManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentLivingExpensesAccountManagementNewBinding
import com.kisssum.smartcity.state.livingExpenses.LivingExpensesAccountManagementModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LivingExpensesAccountManagementNewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LivingExpensesAccountManagementNewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentLivingExpensesAccountManagementNewBinding
    private lateinit var viewModel: LivingExpensesAccountManagementModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLivingExpensesAccountManagementNewBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initToolbar()
        initFeatures()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(LivingExpensesAccountManagementModel::class.java)
    }

    private fun initFeatures() {
        binding.leamnMyHome.setOnClickListener { binding.leamnChoice.text = "我家" }
        binding.leamnParents.setOnClickListener { binding.leamnChoice.text = "父母" }
        binding.leamnLandLord.setOnClickListener { binding.leamnChoice.text = "房东" }
        binding.leamnFriend.setOnClickListener { binding.leamnChoice.text = "朋友" }
        binding.leamnCustomize.setOnClickListener { binding.leamnChoice.text = "自定义" }

        binding.leamnOk.setOnClickListener {
            if (binding.leamnChoice.text.toString() == "")
                Toast.makeText(requireContext(), "未选择", Toast.LENGTH_SHORT).show()
            else {
                val map = HashMap<String, String>()
                map["name"] = binding.leamnChoice.text.toString()
                map["payType"] = "水费"
                map["accountNumber"] = "84688468|4-2-1"
                viewModel.getData().value?.add(map)

                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
            }
        }
    }

    private fun initToolbar() {
        binding.leamnToolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LivingExpensesAccountManagementNewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LivingExpensesAccountManagementNewFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}