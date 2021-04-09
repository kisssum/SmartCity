package com.kisssum.smartcity.ui.navigations.allservice.violationQuery

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentViolationQueryBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViolationQueryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViolationQueryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentViolationQueryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentViolationQueryBinding.inflate(inflater)
        return binding.root;
    }

    override fun onResume() {
        super.onResume()

        requireActivity().window.statusBarColor = Color.RED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initQuery()
    }

    private fun initToolbar() {
        binding.vqToolar.setNavigationOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    private fun initQuery() {
        binding.vqQuery.setOnClickListener {
            if (binding.vqCardNumber.text.toString() != "") {
                val bundle = Bundle()
                bundle.putInt("violation_card_type", binding.vqNumberType.selectedItemPosition)
                bundle.putString("violation_card_number", binding.vqCardNumber.text.toString())

                val navControl = Navigation.findNavController(requireActivity(), R.id.fragment_main)
                navControl.navigate(R.id.action_violationQueryFragment_to_violationRecordsFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "请输入车牌号码!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViolationQueryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ViolationQueryFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}