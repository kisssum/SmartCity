package com.kisssum.smartcity.ui.guidepages

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentGuideMainBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import com.youth.banner.loader.ImageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            GlobalScope.launch(Dispatchers.Main) {
                val rotationList = withContext(Dispatchers.IO) { MRString.getGuideRotationList(requireContext()) }
                val rotationObj = DecodeJson.decodeGuideRotationList(rotationList)

                binding.guideBanner.apply {
                    this.setImageLoader(object : ImageLoader() {
                        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                            Glide.with(requireActivity()).load(API.getBaseUrl() + path.toString()).into(imageView!!)
                        }
                    })
                    this.setImages(rotationObj)
                    this.setDelayTime(2000)
                    this.isAutoPlay(false)
                    this.start()
                }
            }

            binding.guideGo.setOnClickListener { v ->
                // 保存
                sp.edit().putBoolean("isFirstEnter", false).apply()
                navigation()
            }
        } else {
            navigation()
        }
    }

    private fun navigation() {
        val controller = Navigation.findNavController(requireActivity(), R.id.fragment_main)
        controller.popBackStack()
        controller.navigate(R.id.navControlFragment)
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