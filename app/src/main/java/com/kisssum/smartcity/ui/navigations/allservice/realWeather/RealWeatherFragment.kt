package com.kisssum.smartcity.ui.navigations.allservice.realWeather

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentRealWeatherBinding
import java.util.*
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RealWeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RealWeatherFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding: FragmentRealWeatherBinding
    private val random = Random
    private val weatherMax = 40
    private val weatherMin = -10
    private val temperatures = arrayOf("晴", "多云", "阴", "阵雨", "雷阵雨", "雷阵雨伴有冰雹", "雨夹雪", "小雨", "中雨", "大雨", "暴雨", "大暴雨", "特大暴雨", "阵雪", "小雪", "中雪", "大雪", "暴雪", "雾", "冻雨", "沙尘暴", "小到中雨", "中到大雨", "大到暴雨", "暴雨到大暴雨", "大暴雨到特大暴雨", "小到中雪", "中到大雪", "大到暴雪", "浮尘", "扬沙", "强沙尘暴", "霾")
    private val UVmax = 6000
    private val UVmin = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentRealWeatherBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initFeatures()
        initReShow()
    }

    private fun initReShow() {
        Handler().postDelayed({
            setUV(binding.rwUVintensity, binding.rwUVtips)
            setCold(binding.rwColdIntensity, binding.rwColdTips)
            setDressing(binding.rwDressingIntensity, binding.rwDressingTips)
            setSport(binding.rwSportIntensity, binding.rwSportTips)
            setAir(binding.rwAirIntensity, binding.rwAirTips)

            initReShow()
        }, 3000)
    }

    private fun initFeatures() {
        setCloudy(binding.rwCWeather, binding.rwCTemperature, binding.rwCDate, 0)
        setCloudy(binding.rwWeather0, binding.rwTemperature0, binding.rwDate0, 1)
        setCloudy(binding.rwWeather1, binding.rwTemperature1, binding.rwDate1, 2)
        setCloudy(binding.rwWeather2, binding.rwTemperature2, binding.rwDate2, 3)
        setCloudy(binding.rwWeather3, binding.rwTemperature3, binding.rwDate3, 4)

        setUV(binding.rwUVintensity, binding.rwUVtips)
        setCold(binding.rwColdIntensity, binding.rwColdTips)
        setDressing(binding.rwDressingIntensity, binding.rwDressingTips)
        setSport(binding.rwSportIntensity, binding.rwSportTips)
        setAir(binding.rwAirIntensity, binding.rwAirTips)
    }

    private fun setAir(intensity: TextView, tips: TextView) {
        val k = random.nextInt(100) % (100 + 1)

        intensity.text = when {
            k > 100 -> "${k}(污染)"
            k >= 30 -> "${k}(良)"
            k > 0 -> "${k}(优)"
            else -> ""
        }

        tips.text = when {
            k > 100 -> "空气质量差，不适合户外活动"
            k >= 30 -> "易感人群应适当减少室外活动"
            k > 0 -> "空气质量非常好，非常适合户外活动，趁机出去多呼吸新鲜空气"
            else -> ""
        }
    }

    private fun setSport(intensity: TextView, tips: TextView) {
        val k = random.nextInt(8000) % (8000 + 1)

        intensity.text = when {
            k > 6000 -> "${k}(较不宜)"
            k >= 3000 -> "${k}(中)"
            k > 0 -> "${k}(适宜)"
            else -> ""
        }

        tips.text = when {
            k > 6000 -> "空气氧气含量低，请在室内进行休闲运动"
            k >= 3000 -> "易感人群应适当减少室外活动"
            k > 0 -> "气候适宜，推荐您进行户外运动"
            else -> ""
        }
    }

    private fun setDressing(intensity: TextView, tips: TextView) {
        val k = binding.rwCWeather.text.toString().toInt()

        intensity.text = when {
            k > 21 -> "${k}(热)"
            k >= 12 -> "${k}(舒适)"
            else -> "${k}(冷)"
        }

        tips.text = when {
            k > 21 -> "适合穿T恤、短薄外套等夏季服装"
            k >= 12 -> "建议穿短袖衬衫、单裤等服装"
            else -> "建议穿长袖衬衫、单裤等服装"
        }
    }

    private fun setCold(intensity: TextView, tips: TextView) {
        val k = binding.rwCWeather.text.toString().toInt()

        intensity.text = when {
            k >= 8 -> "${k}(少发)"
            else -> "${k}(较易发)"
        }

        tips.text = when {
            k >= 8 -> "无明显降温，感冒机率较低"
            else -> "温度低，风较大，较易发生感冒，注意防护"
        }
    }

    private fun setUV(intensity: TextView, tips: TextView) {
        val k = random.nextInt(UVmax) % (UVmax - UVmin + 1) + UVmin

        intensity.text = when {
            k > 3000 -> "${k}(强)"
            k >= 1000 -> "${k}(中等)"
            k > 0 -> "${k}(弱)"
            else -> ""
        }

        tips.text = when {
            k > 3000 -> "尽量减少外出，需要涂抹高倍数防晒霜"
            k >= 1000 -> "涂擦SPF大于15、PA+防晒护肤品"
            k > 0 -> "辐射较弱，涂擦SPF12~15、PA+护肤品"
            else -> ""
        }
    }

    private fun setCloudy(weather: TextView, temperature: TextView, date: TextView, cDay: Int) {
        val w = random.nextInt(weatherMax) % (weatherMax - weatherMin + 1) + weatherMin
        weather.text = when (cDay) {
            0 -> w.toString()
            else -> "$w°c"
        }

        val t = random.nextInt(temperatures.size) % (temperatures.size + 1)
        temperature.text = temperatures[t]

        val c = Calendar.getInstance()
        date.text = when (cDay) {
            0 -> "${c[Calendar.YEAR]}.${c[Calendar.MONTH] + 1}.${c[Calendar.DAY_OF_MONTH] + cDay}"
            else -> "${c[Calendar.MONTH] + 1}.${c[Calendar.DAY_OF_MONTH] + cDay}"
        }
    }

    override fun onResume() {
        super.onResume()

        requireActivity().window.statusBarColor = Color.RED
    }

    private fun initToolbar() {
        binding.rwToolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }

        binding.rwToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_real_weather_refresh -> {
                    initFeatures()
                }
                else -> ""
            }

            return@setOnMenuItemClickListener true
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RealWeatherFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                RealWeatherFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}