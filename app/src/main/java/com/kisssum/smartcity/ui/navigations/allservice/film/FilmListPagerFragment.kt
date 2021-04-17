package com.kisssum.smartcity.ui.navigations.allservice.film

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisssum.smartcity.adapter.allservice.film.FilmListPagerListAdapater
import com.kisssum.smartcity.databinding.FragmentFilmListPagerBinding
import com.kisssum.smartcity.state.film.FilmModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilmListPagerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilmListPagerFragment(val fileType: Int) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val IS_POPULAR = 0
    private val IS_UPCOMING = 1
    private lateinit var binding: FragmentFilmListPagerBinding
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
        binding = FragmentFilmListPagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        binding.flpList.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            val listAdapter = when (fileType) {
                IS_POPULAR -> FilmListPagerListAdapater(requireContext(), viewModel.getPopularData().value!!, fileType)
                else -> FilmListPagerListAdapater(requireContext(), viewModel.getUpcomingData().value!!, fileType)
            }

            this.adapter = listAdapter
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(FilmModel::class.java)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FilmListPagerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FilmListPagerFragment(0).apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}