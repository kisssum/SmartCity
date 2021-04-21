package com.kisssum.smartcity.ui.guidepages

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.viewpager.widget.ViewPager
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentGuideMainBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson
import com.youth.banner.loader.ImageLoader

/**
 * A simple [Fragment] subclass.
 * Use the [GuideMainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GuideMainFragment : Fragment() {
    private lateinit var binding: FragmentGuideMainBinding

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentGuideMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val isFirstEnter = sp.getBoolean("isFirstEnter", true)

        // 判断是否是第一次进入
        if (isFirstEnter) {
            loadRotationImgs()
        } else {
            val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
            controller.popBackStack()
            controller.navigate(R.id.navControlFragment)
        }
    }

    private fun loadRotationImgs() {
        val sp = requireActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE)

        Volley.newRequestQueue(requireContext()).apply {
            val stirngRequest = StringRequest(
                    API.getGuideRotationListUrl(requireContext()),
                    {
                        val urls = DecodeJson.decodeGuideRotationList(it)

                        binding.guideBanner.apply {
                            this.setImageLoader(object : ImageLoader() {
                                override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                                    Glide.with(context!!).load(API.getBaseUrl(context) + path.toString()).into(imageView!!)
                                }
                            })
                            this.setImages(urls)
                            this.isAutoPlay(false)
                            this.start()
                            
                            this.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                                }

                                override fun onPageSelected(position: Int) {
                                    if (position == urls.size - 1) {
                                        binding.guideNetwork.visibility = View.VISIBLE
                                        binding.guideGo.visibility = View.VISIBLE

                                        binding.guideGo.setOnClickListener { v ->
                                            // 保存
                                            sp.edit().putBoolean("isFirstEnter", false).apply()
                                            val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
                                            controller.popBackStack()
                                            controller.navigate(R.id.navControlFragment)
                                        }

                                        binding.guideNetwork.setOnClickListener { v ->
                                            val view1 = layoutInflater.inflate(R.layout.alertdialog_change_ip, null)
                                            val ip = view1.findViewById<EditText>(R.id.ip)
                                            val duankou = view1.findViewById<EditText>(R.id.duankou)
                                            ip.setText(sp.getString("ip", ""))
                                            duankou.setText(sp.getString("duankou", ""))
                                            AlertDialog.Builder(requireContext())
                                                    .setTitle("网络设置")
                                                    .setView(view1)
                                                    .setPositiveButton("保存") { dialog: DialogInterface?, which: Int ->
                                                        sp.edit()
                                                                .putString("ip", ip.text.toString())
                                                                .putString("duankou", duankou.text.toString())
                                                                .apply()
                                                    }
                                                    .setNegativeButton("取消") { dialog: DialogInterface?, which: Int -> }
                                                    .create()
                                                    .show()
                                        }
                                    } else {
                                        binding.guideNetwork.visibility = View.INVISIBLE
                                        binding.guideGo.visibility = View.INVISIBLE
                                    }
                                }

                                override fun onPageScrollStateChanged(state: Int) {

                                }
                            })
                        }
                    },
                    {}
            )

            this.add(stirngRequest)
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }

    override fun onDestroyView() {
        super.onDestroyView()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
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
         * @return A new instance of fragment GuideMainFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): GuideMainFragment {
            val fragment = GuideMainFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}