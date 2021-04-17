package com.kisssum.smartcity.ui.navigations.allservice.film

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kisssum.smartcity.R
import com.kisssum.smartcity.adapter.allservice.film.FilmChoiceSessionFilmListAdapater
import com.kisssum.smartcity.databinding.FragmentFilmChoiceSessionBinding
import com.kisssum.smartcity.state.film.FilmModel
import java.util.*
import kotlin.random.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilmChoiceSessionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilmChoiceSessionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val IS_POPULAR = 0
    private val IS_UPCOMING = 1
    private lateinit var binding: FragmentFilmChoiceSessionBinding
    private lateinit var viewModel: FilmModel
    private var theaterPosition = 0
    private var filmType = 0
    private var filmPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFilmChoiceSessionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        theaterPosition = arguments?.getInt("theaterPosition")!!
        filmType = arguments?.getInt("filmType")!!
        filmPosition = arguments?.getInt("filmPosition")!!

        initToolbar()
        initViewModel()
        restore()
        initFilmList()
        initViewPager()
    }

    private fun initFilmList() {
        binding.fcsFilmList.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            this.adapter = FilmChoiceSessionFilmListAdapater(requireContext(), filmType, viewModel)
        }
    }

    private fun initViewPager() {
        val random = Random
        val max = 7
        val min = 2
        val count = random.nextInt(max) % (max - min + 1) + min

        binding.fcsViewPager.adapter = object : FragmentStateAdapter(requireActivity()) {
            override fun getItemCount() = count

            override fun createFragment(position: Int) = FilmChoiceSessionListFragment(theaterPosition, filmType, filmPosition)
        }

        val c = Calendar.getInstance()
        TabLayoutMediator(binding.fcsTabLayout, binding.fcsViewPager) { tab: TabLayout.Tab, i: Int ->
            tab.text = "${c[Calendar.MONTH] + 1}月${c[Calendar.DAY_OF_MONTH] + i}日"
        }.attach()
    }

    private fun restoreTheater() {
        val thearterMap = viewModel.getSurroundingTheaterData().value?.get(theaterPosition)!!.apply {
            binding.fcsTeaterName.text = this["name"]
            binding.fcsTeaterAddress.text = this["address"]
        }
    }

    private fun restoreFilm() {
        Log.d("QT", filmPosition.toString())
        val filmMap = when (filmType) {
            IS_POPULAR -> viewModel.getPopularData().value?.get(filmPosition)!!
            else -> viewModel.getUpcomingData().value?.get(filmPosition)!!
        }.apply {
            binding.fcsFilmName.text = this["name"]
            binding.fcsFilmScore.text = "${this["score"]}分"
        }
    }

    private fun restore() {
        restoreTheater()
        restoreFilm()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(FilmModel::class.java)

        viewModel.getSessionFilmPosition().observe(viewLifecycleOwner) {
            if (it != -1) {
                filmPosition = it
                restoreFilm()
                initViewPager()
            }
        }
    }

    private fun initToolbar() {
        binding.fcsToolbar.setNavigationOnClickListener { v ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.getSessionFilmPosition().value = -1
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FilmChoiceSessionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FilmChoiceSessionFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}