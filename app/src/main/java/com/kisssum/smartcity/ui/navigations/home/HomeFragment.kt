package com.kisssum.smartcity.ui.navigations.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kisssum.smartcity.R
import com.kisssum.smartcity.adapter.allservice.ServiceListAdpater
import com.kisssum.smartcity.adapter.news.NewsListAdpater
import com.kisssum.smartcity.databinding.FragmentHomeBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import com.kisssum.smartcity.ui.navigations.news.NewsSearchFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var newsPage: Int = 1
    private val newsData = ArrayList<Map<String, String>>()

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

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
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 搜索框
        initSearch()
        initSmartRefresh()
        GlobalScope.launch(Dispatchers.Main) { loadData() }
    }

    private suspend fun initRotation() {
        val rotationListString =
            withContext(Dispatchers.IO) { MRString.getHomeRotationList() }
        val rotationListObj = DecodeJson.decodeHomeRotationList(rotationListString)
        val imgUrl = ArrayList<String>()
        rotationListObj.forEach {
            imgUrl.add(it["advImg"].toString())
        }

        binding.homeBanner.apply {
            this.setImageLoader(object : com.youth.banner.loader.ImageLoader() {
                override fun displayImage(
                    context: Context?,
                    path: Any?,
                    imageView: ImageView?
                ) {
                    Glide.with(requireActivity())
                        .load(API.getBaseUrl() + path.toString())
                        .into(imageView!!)
                }
            })

            this.setDelayTime(2000)
            this.setImages(imgUrl)
            this.start()
            this.setOnBannerListener {
                val id = rotationListObj[it]["targetId"]

                val bundle = Bundle()
                bundle.putInt("id", id!!.toInt())
                Navigation.findNavController(requireActivity(), R.id.fragment_main)
                    .navigate(R.id.action_navControlFragment_to_newsDetailFragment, bundle)
            }
        }
    }

    private suspend fun initServiceList() {
        val serviceListString =
            withContext(Dispatchers.IO) { MRString.getHomeServiceList() }
        val serviceListObj = DecodeJson.decodeServiceList(serviceListString).apply {
            val map = HashMap<String, String>()
            map["id"] = "-1"
            map["serviceName"] = "更多服务"
            this.add(map)
        }
        binding.homeServiceList.apply {
            this.layoutManager = GridLayoutManager(requireContext(), 5)
            this.adapter = ServiceListAdpater(
                requireContext(),
                serviceListObj,
                true
            )
        }
    }

    private suspend fun loadData() {
        initRotation()
        initServiceList()
        initNewsList()
    }

    private fun initSmartRefresh() {
        binding.homeSmartRefresh.apply {
            this.setOnRefreshListener {
                newsPage = 1
                GlobalScope.launch(Dispatchers.Main) { loadData() }
                binding.homeSmartRefresh.finishRefresh()
            }
            this.setOnLoadMoreListener {
                newsPage++
                GlobalScope.launch(Dispatchers.Main) { initNewsList() }
                binding.homeSmartRefresh.finishLoadMore()
            }
        }
    }

    private suspend fun initNewsList() {
        val newsListString = withContext(Dispatchers.IO) { MRString.getNewsList(newsPage) }
        val newsListObj = DecodeJson.decodeNewsTypeList(newsListString)
        binding.homeNewsList.apply {
            this.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            if (newsPage == 1) newsData.clear()
            newsData.addAll(newsListObj)

            val dAdapter = NewsListAdpater(requireContext(), newsData, true, false)
            this.adapter = dAdapter
        }
    }

    private fun initSearch() {
        binding.homeSearchView.border.setOnClickListener { v: View? ->
            val bundle = Bundle()
            bundle.putInt("type", NewsSearchFragment.TYPE_NEWS)
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}