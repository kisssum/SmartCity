package com.kisssum.smartcity.ui.navigations.home

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kisssum.smartcity.R
import com.kisssum.smartcity.adapter.home.HotServiceListAdpater
import com.kisssum.smartcity.adapter.home.ServiceRecommendAdapater
import com.kisssum.smartcity.databinding.FragmentHomeBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.API.getHomeeRotationListUrl
import com.kisssum.smartcity.tool.API.getServiceRecommendListUrl
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.DecodeJson.decodeHomeRotationList
import com.kisssum.smartcity.tool.DecodeJson.decodeServiceRecommendList
import com.kisssum.smartcity.ui.navigations.news.NewsPagerFragment
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var handler: Handler? = null
    private val Home_Rotation = 0
    private val Service_Recommend = 1
    private val Service_Recommend_Img = 2
    private var homeRotationUrls: ArrayList<String>? = null
    private lateinit var hotThemeUrl: ArrayList<Map<String, Any>>
    private var serviceRecommendMaps: ArrayList<Map<String, Any>>? = null
    private lateinit var newsType: ArrayList<Map<String, Any>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == Home_Rotation) {
                    homeRotationUrls = msg.obj as ArrayList<String>
                    initLunbotu()
                } else if (msg.what == Service_Recommend) {
                    serviceRecommendMaps = msg.obj as ArrayList<Map<String, Any>>
                    initServiceList()
                }
            }
        }

        // 轮播图
        lunbotuUrl

        // 服务列表
        serviceRecommendUrl

        // 热门主题
        getHotThemeUrl()

        // 搜索框
        initSearch()

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


    private fun getHotThemeUrl() {
        val queue = Volley.newRequestQueue(requireContext())

        val stringRequest = StringRequest(
                API.getHotThemeListUrl(requireContext()),
                {
                    hotThemeUrl = DecodeJson.decodeHotThemeList(it)
                    initHotTheme()
                }, {}
        )

        queue.add(stringRequest)
    }

    private val serviceRecommendUrl: Unit
        private get() {
            Thread {
                val client = OkHttpClient()
                val request: Request = Request.Builder()
                        .url(getServiceRecommendListUrl(requireContext()))
                        .build()
                try {
                    val response = client.newCall(request).execute()
                    val string = response.body!!.string()
                    val imgs = decodeServiceRecommendList(string)
                    val message = Message()
                    message.what = Service_Recommend
                    message.obj = imgs
                    handler!!.sendMessage(message)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }

    private fun initSearch() {
        binding.homeSearchView.border.setOnClickListener { v: View? -> Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_navControlFragment_to_newsSearchFragment) }
    }

    private fun initNews() {
        binding.homeNewsPager.apply {
            this.adapter = object : FragmentStateAdapter(requireActivity()) {
                override fun createFragment(position: Int) = NewsPagerFragment(newsType[position]["dictCode"].toString().toInt())
                override fun getItemCount() = newsType.size
            }

            this.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

            TabLayoutMediator(binding.homeNewsTablayout, binding.homeNewsPager) { tab: TabLayout.Tab, position: Int -> tab.text = newsType[position]["dictLabel"].toString() }.attach()
        }
    }

    private fun initLunbotu() {
        val topViewAdapter: FragmentStateAdapter = object : FragmentStateAdapter(requireActivity()) {
            override fun createFragment(position: Int): Fragment {
                return HomeTopViewPagerFragment(position, homeRotationUrls!![position])
            }

            override fun getItemCount(): Int {
                return 4
            }
        }
        binding.homeTopViewPager.adapter = topViewAdapter
        binding.homeTopViewPager.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

        // 无限滚轮
        loopTopViewPager()
    }

    private val lunbotuUrl: Unit
        private get() {
            Thread {
                val client = OkHttpClient()
                val request: Request = Request.Builder()
                        .url(getHomeeRotationListUrl(requireContext()))
                        .build()
                try {
                    val response = client.newCall(request).execute()
                    val string = response.body!!.string()
                    val imgs = decodeHomeRotationList(string)
                    val message = Message()
                    message.what = Home_Rotation
                    message.obj = imgs
                    handler!!.sendMessage(message)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }.start()
        }

    private fun initServiceList() {
        binding.homeServiceList.layoutManager = GridLayoutManager(requireContext(), 5)
        val serviceRecommendAdapater = ServiceRecommendAdapater(requireActivity(), serviceRecommendMaps!!)
        binding.homeServiceList.adapter = serviceRecommendAdapater

        // old
        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = Navigation.findNavController(requireActivity(), R.id.fragment_main)
        binding.homeServiceListMy.serviceGovernmentAffairs.setOnClickListener { bottomNavigationView.selectedItemId = R.id.item_allservice }
        binding.homeServiceListMy.serviceFilm.setOnClickListener { navController.navigate(R.id.action_navControlFragment_to_filmFragment) }
        binding.homeServiceListMy.serviceRealWeather.setOnClickListener { navController.navigate(R.id.action_navControlFragment_to_realWeatherFragment) }
        binding.homeServiceListMy.serviceViolationQuery.setOnClickListener { navController.navigate(R.id.action_navControlFragment_to_violationQueryFragment) }
        binding.homeServiceListMy.servicePension.setOnClickListener { bottomNavigationView.selectedItemId = R.id.item_allservice }
        binding.homeServiceListMy.serviceLivingExpenses.setOnClickListener { navController.navigate(R.id.action_navControlFragment_to_livingExpensesFragment) }
        binding.homeServiceListMy.serviceMedicalTreatment.setOnClickListener { navController.navigate(R.id.action_navControlFragment_to_medicalFragment); }
        binding.homeServiceListMy.serviceOutpatientAppointment.setOnClickListener { navController.navigate(R.id.action_navControlFragment_to_outpatientAppointmentFragment); }
        binding.homeServiceListMy.serviceSmartBus.setOnClickListener { navController.navigate(R.id.action_navControlFragment_to_smartBusFragment); }
        binding.homeServiceListMy.serviceMore.setOnClickListener { bottomNavigationView.selectedItemId = R.id.item_allservice; }
    }

    private fun initHotTheme() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        val HotServiceAdpater = HotServiceListAdpater(requireContext(), hotThemeUrl)
        binding.homeHotServiceList.layoutManager = layoutManager
        binding.homeHotServiceList.adapter = HotServiceAdpater
    }

    private fun loopTopViewPager() {
        Handler().postDelayed({
            var cIndex = binding.homeTopViewPager.currentItem
            if (cIndex >= 4) cIndex = 0 else cIndex++
            binding.homeTopViewPager.currentItem = cIndex
            loopTopViewPager()
        }, 3000)
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