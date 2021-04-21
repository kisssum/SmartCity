package com.kisssum.smartcity.ui.navigations.allservice.film

import android.graphics.Color
import android.os.Bundle
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
import com.kisssum.smartcity.adapter.allservice.film.FilmPopularAdapater
import com.kisssum.smartcity.adapter.allservice.film.FilmUpcomingAdapater
import com.kisssum.smartcity.databinding.FragmentFilmBinding
import com.kisssum.smartcity.state.film.FilmModel
import com.kisssum.smartcity.ui.navigations.news.NewsPagerFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilmFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val IS_POPULAR = 0
    private val IS_UPCOMING = 1
    private lateinit var binding: FragmentFilmBinding
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
        binding = FragmentFilmBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initToolbar()
        initPopularList()
        initUpcomingList()
        initViewPager()
    }

    private fun initUpcomingList() {
        binding.fUpcomingSize.apply {
            this.text = "全部${viewModel.getUpcomingData().value?.size}部"

            this.setOnClickListener {
                val bundle = Bundle().apply {
                    this.putInt("filmStatus", IS_UPCOMING)
                }

                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_filmFragment_to_filmListFragment, bundle)
            }
        }

        binding.fUpcomingList.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            this.adapter = FilmUpcomingAdapater(requireContext(), viewModel.getUpcomingData().value!!, viewModel)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(FilmModel::class.java)
    }

    private fun initPopularList() {
        binding.fPopularSize.apply {
            this.text = "全部${viewModel.getPopularData().value?.size}部"

            this.setOnClickListener {
                val bundle = Bundle().apply {
                    this.putInt("filmStatus", IS_POPULAR)
                }

                Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_filmFragment_to_filmListFragment, bundle)
            }
        }

        binding.fPopularList.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            this.adapter = FilmPopularAdapater(requireContext(), viewModel.getPopularData().value!!)
        }
    }

    override fun onResume() {
        super.onResume()

        requireActivity().window.statusBarColor = Color.RED
    }

    private fun initViewPager() {
        binding.fViewPager.adapter = object : FragmentStateAdapter(requireActivity()) {
            override fun getItemCount() = 2

            override fun createFragment(position: Int) = when (position) {
                0 -> FilmSurroundingTheaterFragment()
                else -> NewsPagerFragment(-1, false)
//                else -> FilmStarNewsFragment()
            }
        }

        TabLayoutMediator(binding.fTabLayout, binding.fViewPager) { tab: TabLayout.Tab, i: Int ->
            tab.text = when (i) {
                0 -> "周边影院"
                else -> "星闻"
            }
        }.attach()
    }

    private fun initToolbar() {
        binding.fToolbar.setNavigationOnClickListener { v ->
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
         * @return A new instance of fragment FilmFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FilmFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}