package com.kisssum.smartcity.ui.navigations.allservice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import androidx.recyclerview.widget.GridLayoutManager
import com.kisssum.smartcity.adapter.allservice.ServiceListAdpater
import com.kisssum.smartcity.databinding.FragmentAllServiceBinding
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import com.kisssum.smartcity.ui.navigations.news.NewsSearchFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllServiceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 搜索框
        initSearch()

        GlobalScope.launch(Dispatchers.Main) {
            val serviceListString =
                withContext(Dispatchers.IO) { MRString.getHomeServiceList() }
            val serviceListObj = DecodeJson.decodeServiceList(serviceListString)

            // 左栏
            val serviceTypeList = ArrayList<String>().apply {
                this.add("全部服务")
                this.add("车主服务")
                this.add("生活服务")
                this.add("便民服务")
            }
            binding.allServiceTypeList.apply {
                this.adapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        serviceTypeList
                    )
                this.setOnItemClickListener { parent, view, position, id ->
                    val data = ArrayList<Map<String, String>>()

                    binding.asNextList.adapter = when (position) {
                        0 -> {
                            ServiceListAdpater(requireContext(), serviceListObj)
                        }
                        else -> {
                            serviceListObj.forEach {
                                if (it["serviceType"] == serviceTypeList[position]) data.add(it)
                            }

                            ServiceListAdpater(requireContext(), data)
                        }
                    }
                }
            }

            // 右栏
            binding.asNextList.apply {
                this.layoutManager = GridLayoutManager(requireContext(), 3)
                this.adapter =
                    ServiceListAdpater(requireContext(), serviceListObj)
            }
        }
    }

    private fun initSearch() {
        binding.allServiceSearchView.border.setOnClickListener { v: View? ->
            val bundle = Bundle()
            bundle.putInt("type", NewsSearchFragment.TYPE_SERVICE)

            Navigation.findNavController(
                requireActivity(),
                R.id.fragment_main
            ).navigate(R.id.action_navControlFragment_to_newsSearchFragment, bundle)
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