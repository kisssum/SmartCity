package com.kisssum.smartcity.ui.navigations.allservice.film

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.kisssum.smartcity.R
import com.kisssum.smartcity.adapter.allservice.film.FilmCommentAdapater
import com.kisssum.smartcity.databinding.FragmentFilmDetailBinding
import com.kisssum.smartcity.state.film.FilmModel
import java.util.*
import kotlin.collections.HashMap

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilmDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilmDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val IS_POPULAR = 0
    private val IS_UPCOMING = 1
    private lateinit var binding: FragmentFilmDetailBinding
    private lateinit var viewModel: FilmModel
    private lateinit var commentListAdpater: FilmCommentAdapater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFilmDetailBinding.inflate(inflater)
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
        initIntroduction()
        initList()
        initWriteComment()
        initGoChoiceTheater()
    }

    private fun initGoChoiceTheater() {
        binding.fdBuyTicket.setOnClickListener {
            val bundle = Bundle().apply {
                this.putInt("position", arguments?.getInt("position")!!)
                this.putInt("type", arguments?.getInt("type")!!)
            }

            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigate(R.id.action_filmDetailFragment_to_filmChoiceTheaterFragment, bundle)
        }
    }

    private fun initWriteComment() {
        binding.fdWriteComment.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.alertdialog_style_comment, null)

            AlertDialog.Builder(requireContext())
                    .setTitle("你的评论")
                    .setCancelable(false)
                    .setView(view)
                    .setPositiveButton("发表") { dialogInterface: DialogInterface, i: Int ->
                        run {
                            val map = HashMap<String, String>().apply {
                                this["userName"] = "root"
                                this["userScore"] = (view.findViewById<RatingBar>(R.id.ascStar).rating * 2).toString()
                                this["commentText"] = view.findViewById<TextView>(R.id.ascText).text.toString()

                                val c = Calendar.getInstance()
                                this["commentTime"] = "${c[Calendar.YEAR]}-${c[Calendar.MONTH] + 1}-${c[Calendar.DAY_OF_MONTH]}"
                                this["likes"] = "0"
                            }

                            viewModel.getCommentData().value?.add(0, map)
                        }
                    }
                    .setNegativeButton("取消") { dialogInterface: DialogInterface, i: Int ->
                        run {

                        }
                    }
                    .create()
                    .show()
        }
    }

    private fun initIntroduction() {
        binding.fdIntroductionBar.setOnClickListener {
            if (binding.fdIntroduction.maxLines == 3) {
                binding.fdIntroduction.maxLines = 20
                binding.fdFold.text = "折叠"
            } else {
                binding.fdIntroduction.maxLines = 3
                binding.fdFold.text = "展开"
            }
        }

        binding.fdIntroduction.setOnClickListener {
            if (binding.fdIntroduction.maxLines == 3) {
                binding.fdIntroduction.maxLines = 20
                binding.fdFold.text = "折叠"
            } else {
                binding.fdIntroduction.maxLines = 3
                binding.fdFold.text = "展开"
            }
        }
    }

    private fun initList() {
        binding.fdCommentList.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            commentListAdpater = FilmCommentAdapater(requireContext(), viewModel.getCommentData().value!!)
            this.adapter = commentListAdpater
        }
    }

    private fun restore() {
        val type = arguments?.getInt("type")
        val position = arguments?.getInt("position")

        val map = when (type) {
            IS_POPULAR -> viewModel.getPopularData().value!![position!!]
            else -> viewModel.getUpcomingData().value!![position!!]
        }

        binding.fdName.text = map["name"]
        binding.fdEnglishName.text = map["nameEnglish"]
        binding.fdType.text = map["type"]
        binding.fdUpDate.text = map["upDate"]
        binding.fdScore.text = map["score"]
        binding.fdWantSeeSize.text = "${map["wantSeeSize"]}人想看"
        binding.fdSeenSize.text = "${map["seenSize"]}人看过"
        binding.fdScoreSize.text = "${map["scoreSize"]}人评"
        binding.fdIntroduction.text = map["introduction"]
        binding.fdCommentSize.text = "总评论${viewModel.getCommentData().value!!.size}人"
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(FilmModel::class.java)

        viewModel.getCommentData().observe(viewLifecycleOwner) {
            commentListAdpater.setData1(it)
        }
    }

    private fun initToolbar() {
        binding.fdToolbar.setNavigationOnClickListener { v ->
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
         * @return A new instance of fragment FilmDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FilmDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}