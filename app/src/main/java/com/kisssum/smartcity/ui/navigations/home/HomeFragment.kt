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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kisssum.smartcity.R
import com.kisssum.smartcity.adapter.home.HomeServiceListAdpater
import com.kisssum.smartcity.adapter.news.NewsListAdpater
import com.kisssum.smartcity.databinding.FragmentHomeBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

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

        init()
    }

    private fun initMyServiceList() {
        binding.homeServiceList.layoutManager = GridLayoutManager(requireContext(), 5)

        val bottomNavigationView =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navController = Navigation.findNavController(requireActivity(), R.id.fragment_main)

//        binding.homeServiceListMy.serviceGovernmentAffairs.setOnClickListener {
//            bottomNavigationView.selectedItemId = R.id.item_allservice
//        }
//        binding.homeServiceListMy.serviceFilm.setOnClickListener { navController.navigate(R.id.action_navControlFragment_to_filmFragment) }
        binding.homeServiceListMy.serviceRealWeather.setOnClickListener { navController.navigate(R.id.action_navControlFragment_to_realWeatherFragment) }
        binding.homeServiceListMy.serviceViolationQuery.setOnClickListener {
            navController.navigate(
                R.id.action_navControlFragment_to_violationQueryFragment
            )
        }
//        binding.homeServiceListMy.servicePension.setOnClickListener {
//            bottomNavigationView.selectedItemId = R.id.item_allservice
//        }
//        binding.homeServiceListMy.serviceLivingExpenses.setOnClickListener {
//            navController.navigate(
//                R.id.action_navControlFragment_to_livingExpensesFragment
//            )
//        }
//        binding.homeServiceListMy.serviceMedicalTreatment.setOnClickListener {
//            navController.navigate(
//                R.id.action_navControlFragment_to_medicalFragment
//            );
//        }
        binding.homeServiceListMy.serviceOutpatientAppointment.setOnClickListener {
            navController.navigate(
                R.id.action_navControlFragment_to_outpatientAppointmentFragment
            );
        }
//        binding.homeServiceListMy.serviceSmartBus.setOnClickListener { navController.navigate(R.id.action_navControlFragment_to_smartBusFragment); }
//        binding.homeServiceListMy.serviceMore.setOnClickListener {
//            bottomNavigationView.selectedItemId = R.id.item_allservice;
//        }
    }

    private fun init() {
        GlobalScope.launch(Dispatchers.Main) {
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
                this.adapter = HomeServiceListAdpater(
                    requireContext(),
                    serviceListObj,
                    true
                )
            }

            val newsListString = withContext(Dispatchers.IO) { MRString.getNewsList() }
            val newsListObj = DecodeJson.decodeNewsTypeList(newsListString)
            binding.homeNewsList.apply {
                this.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                val dAdapter = NewsListAdpater(requireContext(), newsListObj, true, false)
                this.adapter = dAdapter
            }
        }

        // 搜索框
        initSearch()
    }

    private fun initSearch() {
        binding.homeSearchView.border.setOnClickListener { v: View? ->
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