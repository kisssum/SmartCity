package com.kisssum.smartcity.ui.navigations.news

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisssum.smartcity.R
import com.kisssum.smartcity.adapter.home.HomeServiceListAdpater
import com.kisssum.smartcity.adapter.news.NewsListAdpater
import com.kisssum.smartcity.databinding.FragmentNewsSearchBinding
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [NewsSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsSearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var binding: FragmentNewsSearchBinding
    private var handler: Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = Color.RED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsSearchToolbar.apply {
            this.title = when (requireArguments().getInt("type")) {
                TYPE_NEWS -> {
                    "搜索新闻"
                }
                TYPE_SERVICE -> {
                    "搜索服务"
                }
                else -> "搜索"
            }

            this.setNavigationOnClickListener { v: View? ->
                val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
                controller.popBackStack()
            }
        }

        binding.newsSearchView.apply {
            this.isIconified = false

            this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    when (requireArguments().getInt("type")) {
                        TYPE_NEWS -> {
                            searchNews(newText)
                        }
                        TYPE_SERVICE -> {
                            searchService(newText)
                        }
                    }
                    return false
                }
            })
        }
    }

    private fun searchService(text: String) {
        if (text != "") {
            GlobalScope.launch(Dispatchers.Main) {
                val serviceListString =
                    withContext(Dispatchers.IO) { MRString.getHomeServiceList() }
                val serviceListObj = DecodeJson.decodeServiceListByTitle(serviceListString, text)

                binding.newsSearchList.apply {
                    this.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                    val dAdapter =
                        HomeServiceListAdpater(requireContext(), serviceListObj, isSearch = true)
                    this.adapter = dAdapter
                }
            }
        } else {
            binding.newsSearchList.apply {
                val dAdapter =
                    HomeServiceListAdpater(requireContext(), ArrayList(), isSearch = true)

                this.adapter = dAdapter
            }
        }
    }

    private fun searchNews(text: String) {
        if (text != "") {
            GlobalScope.launch(Dispatchers.Main) {
                val newsListString =
                    withContext(Dispatchers.IO) { MRString.getNewsListByTitle(text) }
                val newsListObj = DecodeJson.decodeNewsTypeList(newsListString)

                binding.newsSearchList.apply {
                    this.layoutManager =
                        LinearLayoutManager(
                            requireContext(),
                            LinearLayoutManager.VERTICAL,
                            false
                        )

                    val dAdapter =
                        NewsListAdpater(requireContext(), newsListObj, false, false, true)
                    this.adapter = dAdapter
                }
            }
        } else {
            binding.newsSearchList.apply {
                val dAdapter = NewsListAdpater(
                    requireContext(),
                    ArrayList(),
                    false,
                    false,
                    true
                )
                this.adapter = dAdapter
            }
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        val TYPE_NEWS = 0;
        val TYPE_SERVICE = 1;

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewsSearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): NewsSearchFragment {
            val fragment = NewsSearchFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}