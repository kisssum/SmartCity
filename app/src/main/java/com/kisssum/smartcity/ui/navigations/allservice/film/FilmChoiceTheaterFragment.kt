package com.kisssum.smartcity.ui.navigations.allservice.film

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentFilmChoiceTheaterBinding
import com.kisssum.smartcity.state.film.FilmModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilmChoiceTheaterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilmChoiceTheaterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val IS_POPULAR = 0
    private val IS_UPCOMING = 1
    private lateinit var binding: FragmentFilmChoiceTheaterBinding
    private lateinit var viewModel: FilmModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFilmChoiceTheaterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initToolbar()
        restore()
        initFeatures()
    }

    private fun initFeatures() {
        binding.fctCinemaBrand.prompt = "品牌"

        initViewpager()
    }

    private fun initViewpager() {
        val c = Calendar.getInstance()
        val filmType = arguments?.getInt("type")!!
        val filmPosition = arguments?.getInt("position")!!

        binding.fctViewPager.adapter = object : FragmentStateAdapter(requireActivity()) {
            override fun getItemCount() = 7

            override fun createFragment(position: Int) = FilmTheaterListFragment(position, filmType, filmPosition)
        }

        TabLayoutMediator(binding.fctTabLayout, binding.fctViewPager) { tab: TabLayout.Tab, i: Int ->
            tab.text = "${c[Calendar.MONTH] + 1}月${c[Calendar.DAY_OF_MONTH] + i}日"
        }.attach()
    }

    private fun restore() {
        val type = arguments?.getInt("type")
        val filmPosition = arguments?.getInt("position")

        val map = when (type) {
            IS_POPULAR -> viewModel.getPopularData().value!![filmPosition!!]
            else -> viewModel.getUpcomingData().value!![filmPosition!!]
        }

        binding.fctName.text = map["name"]
        binding.fctEnglishName.text = map["nameEnglish"]
        binding.fctType.text = map["type"]
        binding.fctUpDate.text = map["upDate"]
        binding.fctDuration.text = "${map["duration"]}分钟"
        binding.fctScore.text = map["score"]
        binding.fctWantSeeSize.text = "${map["wantSeeSize"]}人想看"
        binding.fctSeenSize.text = "${map["seenSize"]}人看过"
        binding.fctScoreSize.text = "${map["scoreSize"]}人评"
    }


    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(FilmModel::class.java)
    }

    private fun initToolbar() {
        binding.fctToolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FilmChoiceTheaterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FilmChoiceTheaterFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}