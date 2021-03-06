package com.kisssum.smartcity.ui.navigations.allservice.livingExpenses.accountManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisssum.smartcity.R
import com.kisssum.smartcity.adapter.allservice.livingExpenses.LivingExpensesAccountManagementAdapater
import com.kisssum.smartcity.databinding.FragmentLivingExpensesAccountManagementBinding
import com.kisssum.smartcity.state.livingExpenses.LivingExpensesAccountManagementModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LivingExpensesAccountManagementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LivingExpensesAccountManagementFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentLivingExpensesAccountManagementBinding
    private lateinit var viewModel: LivingExpensesAccountManagementModel
    private lateinit var dadpater: LivingExpensesAccountManagementAdapater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentLivingExpensesAccountManagementBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iniViewModel()
        initToolbar()
        initList()
        initFeatures()
    }

    private fun iniViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(LivingExpensesAccountManagementModel::class.java)

        viewModel.getData().observe(viewLifecycleOwner) {
            dadpater.setData(it)
        }
    }

    private fun initList() {
        binding.leamList.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            dadpater = LivingExpensesAccountManagementAdapater(requireContext(), viewModel.getData().value!!)
            this.adapter = dadpater
        }
    }

    private fun initFeatures() {
        binding.leamNewManagement.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_livingExpensesAccountManagementFragment_to_livingExpensesAccountManagementNewFragment)
        }
    }

    private fun initToolbar() {
        binding.leamToolbar.setNavigationOnClickListener { v ->
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
         * @return A new instance of fragment LivingExpensesAccountManagementFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                LivingExpensesAccountManagementFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}