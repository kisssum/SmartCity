package com.kisssum.smartcity.ui.navigations.allservice.violationQuery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentViolationRecordsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ViolationRecordsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViolationRecordsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentViolationRecordsBinding
    private val data = ArrayList<Map<String, String>>()
    lateinit var simpleAdapter: SimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentViolationRecordsBinding.inflate(inflater);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolBar()
        initCardText()
        initList()

        if (data.size == 0) {
            data.addAll(getData(5))
        }

        if (data.size != 6) {
            binding.vrMoreFind.visibility = View.INVISIBLE
        }

        binding.vrMoreFind.setOnClickListener {
            moreFind()
            binding.vrMoreFind.visibility = View.INVISIBLE
        }
    }

    private fun initToolBar() {
        binding.vrToolar.setNavigationOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    private fun initCardText() {
        val type = arguments?.getInt("violation_card_type")
        val number = arguments?.getString("violation_card_number")
        binding.vrCardNumberRight.text = "${resources.getStringArray(R.array.card_type)[type!!]} $number"
    }

    private fun initList() {
        simpleAdapter = SimpleAdapter(requireContext(), data, R.layout.list_style_violation_record,
                arrayOf("vrlsPlace", "vrlsFraction", "vrlsMoney", "vrlsTime", "vrlsStatus"),
                intArrayOf(R.id.vrlsPlace, R.id.vrlsFration, R.id.vrlsMoney, R.id.vrlsTime, R.id.vrlsStatus))

        binding.vrList.adapter = simpleAdapter

        binding.vrList.setOnItemClickListener { adapterView, view, i, l ->
            run {
                val bundle = Bundle()
                bundle.putString("vrlsTime", data[i]["vrlsTime"])
                bundle.putString("vrlsPlace", data[i]["vrlsPlace"])
                bundle.putString("vrlsActivity", data[i]["vrlsActivity"])
                bundle.putString("vrlsInformBookNumber", data[i]["vrlsInformBookNumber"])
                bundle.putString("vrlsFraction", data[i]["vrlsFraction"])
                bundle.putString("vrlsMoney", data[i]["vrlsMoney"])

                val navController = Navigation.findNavController(requireActivity(), R.id.fragment_main)
                navController.navigate(R.id.action_violationRecordsFragment_to_violationDetailsFragment, bundle)
            }
        }
    }

    private fun moreFind() {
        data.addAll(getData(10))
        simpleAdapter.notifyDataSetChanged()
    }

    private fun getData(count: Int): ArrayList<Map<String, String>> {
        val arrayList = ArrayList<Map<String, String>>()
        var hashMap: HashMap<String, String>

        for (i in 0..count) {
            hashMap = HashMap()

            hashMap.put("vrlsPlace", "北京中关村48号路段")
            hashMap.put("vrlsFraction", "-3分")
            hashMap.put("vrlsMoney", "-200￥")
            hashMap.put("vrlsTime", "2019.01.03")
            hashMap.put("vrlsStatus", "处理中")
            hashMap.put("vrlsActivity", "货车追尾")
            hashMap.put("vrlsInformBookNumber", "1025468421575685")

            arrayList.add(hashMap)
        }

        return arrayList
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViolationRecordsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ViolationRecordsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}