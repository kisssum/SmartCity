package com.kisssum.smartcity.ui.navigations.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisssum.smartcity.adapter.news.NewsListAdpater
import com.kisssum.smartcity.databinding.FragmentNewsPagerBinding
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [NewsPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsPagerFragment(private val type: Int, private val isHome: Boolean) : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var page: Int = 1
    private val data = ArrayList<Map<String, String>>()
    private lateinit var binding: FragmentNewsPagerBinding

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
        binding = FragmentNewsPagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch(Dispatchers.Main) { initList() }
        initSmartRefresh()
    }

    private fun initSmartRefresh() {
        binding.newsSmart.apply {
            this.setOnRefreshListener {
                page = 1
                GlobalScope.launch(Dispatchers.Main) { initList() }
                binding.newsSmart.finishRefresh()
            }
            this.setOnLoadMoreListener {
                page++
                GlobalScope.launch(Dispatchers.Main) { initList() }
                binding.newsSmart.finishLoadMore()
            }
        }
    }

    private suspend fun initList() {
        val newsTypeListString =
            withContext(Dispatchers.IO) { MRString.getHomeNewsTypeList(type, page) }
        val newsTypeListObj = DecodeJson.decodeNewsTypeList(newsTypeListString)

        binding.newsPagerList.apply {
            this.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            if (page == 1) data.clear()
            data.addAll(newsTypeListObj)

            val dAdapter = NewsListAdpater(requireContext(), data, isHome, false)
            this.adapter = dAdapter
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
         * @return A new instance of fragment NewsPagerFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): NewsPagerFragment {
            val fragment = NewsPagerFragment(0, false)
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}