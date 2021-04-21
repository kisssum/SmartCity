package com.kisssum.smartcity.ui.navigations.news

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentNewsBinding
import com.kisssum.smartcity.state.NewsModel
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsType: ArrayList<Map<String, Any>>
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
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 搜索框
        initSearch()

        GlobalScope.launch(Dispatchers.Main) {
            val newsCategoryListString =
                withContext(Dispatchers.IO) { MRString.getHomeNewsCategoryList() }
            val newsCategoryListObj = DecodeJson.decodeNewsCategoryList(newsCategoryListString)
            binding.newsMainPager.apply {
                this.adapter = object : FragmentStateAdapter(requireActivity()) {
                    override fun createFragment(position: Int) =
                        NewsPagerFragment(newsCategoryListObj[position]["id"]!!.toInt(), true)

                    override fun getItemCount() = newsCategoryListObj.size
                }
                this.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

                TabLayoutMediator(
                    binding.newsMainTablayout,
                    binding.newsMainPager
                ) { tab: TabLayout.Tab, position: Int ->
                    tab.text = newsCategoryListObj[position]["name"]
                }.attach()
            }
        }
    }

    private fun initSearch() {
        binding.newsSearchView.border.setOnClickListener { v: View? ->
            Navigation.findNavController(
                requireActivity(),
                R.id.fragment_main
            ).navigate(R.id.action_navControlFragment_to_newsSearchFragment)
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
         * @return A new instance of fragment NewsFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): NewsFragment {
            val fragment = NewsFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}