package com.kisssum.smartcity.ui.navigations.news

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kisssum.smartcity.databinding.FragmentNewsBinding
import com.kisssum.smartcity.state.NewsModel
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var binding: FragmentNewsBinding? = null
    private var model: NewsModel? = null
    private val handler = Handler()
    private lateinit var newsType: ArrayList<Map<String, Any>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentNewsBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(requireActivity(), AndroidViewModelFactory(requireActivity().application)).get(NewsModel::class.java)

        // 轮播图
        initLunbotu()

        // 新闻专栏
        Volley.newRequestQueue(requireContext()).apply {
            val newsTypeString = StringRequest(
                    API.getNewsTypeUrl(requireContext()),
                    {
                        newsType = DecodeJson.decodeNewsType(it)
                        initNews()
                    },
                    {}
            )

            this.add(newsTypeString)
        }
    }

    private fun initLunbotu() {
        val topViewAdapter: FragmentStateAdapter = object : FragmentStateAdapter(requireActivity()) {
            override fun createFragment(position: Int): Fragment {
                return NewsTopViewPagerFragment(model!!.data[position])
            }

            override fun getItemCount(): Int {
                return model!!.count
            }
        }
        binding!!.newsMainLunBoPager.adapter = topViewAdapter
        binding!!.newsMainLunBoPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        // 无限滚轮
        loopTopViewPager()
    }

    private fun loopTopViewPager() {
        handler.postDelayed({
            var cIndex = binding!!.newsMainLunBoPager.currentItem
            if (cIndex >= model!!.count - 1) cIndex = 0 else cIndex++
            binding!!.newsMainLunBoPager.currentItem = cIndex
            loopTopViewPager()
        }, 3000)
    }

    private fun initNews() {
        binding!!.newsMainPager.apply {
            this.adapter = object : FragmentStateAdapter(requireActivity()) {
                override fun createFragment(position: Int) = NewsPagerFragment(newsType[position]["dictCode"].toString().toInt(), false)
                override fun getItemCount() = newsType.size
            }

            this.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

            TabLayoutMediator(binding!!.newsMainTablayout, binding!!.newsMainPager) { tab: TabLayout.Tab, position: Int -> tab.text = newsType[position]["dictLabel"].toString() }.attach()
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