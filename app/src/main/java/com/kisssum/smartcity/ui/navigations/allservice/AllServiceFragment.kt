package com.kisssum.smartcity.ui.navigations.allservice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.kisssum.smartcity.R
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.kisssum.smartcity.adapter.home.ServiceRecommendAdapater
import com.kisssum.smartcity.databinding.FragmentAllServiceBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson

/**
 * A simple [Fragment] subclass.
 * Use the [AllServiceFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AllServiceFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var binding: FragmentAllServiceBinding
    private lateinit var data: ArrayList<Map<String, Any>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAllServiceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Volley.newRequestQueue(requireContext()).apply {
            val stringRequest = StringRequest(
                    API.getServiceFirst(requireContext()),
                    {
                        data = DecodeJson.decodeServiceFirst(it)
                        initFirstList()
                    },
                    {}
            )

            this.add(stringRequest)
        }
    }

    private fun initFirstList() {
        val name = ArrayList<String>().apply {
            data.forEach { this.add(it["dictLabel"].toString()) }
        }

        binding.asFirstList.adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, name)
        binding.asFirstList.setOnItemClickListener { adapterView, view, i, l ->
            run {
                iniNextList(data[i]["dictValue"].toString())
            }
        }
    }

    private fun iniNextList(dictValue: String) {
        Volley.newRequestQueue(requireContext()).apply {
            val stringRequest = StringRequest(
                    API.getServiceAllList(requireContext()),
                    {
                        val d = DecodeJson.decodeServiceAllList(it, dictValue)

                        binding.asNextList.apply {
                            this.layoutManager = GridLayoutManager(requireContext(), 5)
                            this.adapter = ServiceRecommendAdapater(requireActivity(), d,false)
                        }
                    },
                    {}
            )

            this.add(stringRequest)
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AllServiceFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): AllServiceFragment {
            val fragment = AllServiceFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}