package com.kisssum.smartcity.ui.navigations.news

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kisssum.smartcity.R
import com.kisssum.smartcity.adapter.news.NewsCommentsListAdpater
import com.kisssum.smartcity.databinding.FragmentNewsDetailBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import com.kisssum.smartcity.tool.UpdateUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [NewsDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var binding: FragmentNewsDetailBinding
    private lateinit var handler: Handler

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
        binding = FragmentNewsDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = Color.RED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireArguments().getInt("id", 0)

        GlobalScope.launch(Dispatchers.Main) {
            val newsDetailString =
                withContext(Dispatchers.IO) { MRString.getNewsDetail(id) }
            val newsDetailObj = DecodeJson.decodeNewsDetail(newsDetailString)
            binding.newsDetailTitle.text = newsDetailObj["title"]
            binding.newsDetailsubTitle.text = newsDetailObj["subTitle"]
            Glide.with(requireActivity()).load(API.getBaseUrl() + newsDetailObj["cover"])
                .into(binding.newsDetailImg)
            binding.newsDetailPublichDate.text = newsDetailObj["publishDate"]
            binding.newsDetailContent.text = newsDetailObj["content"]

            val newsCommentString =
                withContext(Dispatchers.IO) { MRString.getNewsComments(id) }
            val newsCommentObj = DecodeJson.decodeNewsCommentsList(newsCommentString)
            binding.newsDetailCommentSize.text = newsCommentObj.size.toString()
            binding.newsDetailcommentList.apply {
                this.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                val dAdapter = NewsCommentsListAdpater(requireContext(), newsCommentObj)
                this.adapter = dAdapter
            }
        }

        binding.newsDetailAdd.setOnClickListener {
            val content = binding.newsDetailCommentcotent.text.toString()

            GlobalScope.launch(Dispatchers.Main) {
                val newsCommentAddString: String =
                    withContext(Dispatchers.IO) {
                        MRString.getNewsCommentAdd(
                            requireContext(),
                            id,
                            content
                        )
                    }
                val newsCommentAddObj = DecodeJson.decodeCommentAdd(newsCommentAddString)

                if (newsCommentAddObj == "") {
                    UpdateUI.toastUi(requireContext(), "发布失败!")
                } else {
                    UpdateUI.toastUi(requireContext(), "发布成功!")

                    val newsCommentString =
                        withContext(Dispatchers.IO) { MRString.getNewsComments(id) }
                    val newsCommentObj = DecodeJson.decodeNewsCommentsList(newsCommentString)
                    binding.newsDetailCommentSize.text = newsCommentObj.size.toString()
                    binding.newsDetailcommentList.apply {
                        this.layoutManager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )

                        val dAdapter = NewsCommentsListAdpater(requireContext(), newsCommentObj)
                        this.adapter = dAdapter
                    }
                }
            }
        }

        // Toolbar
        binding.newsDetailToolbar.setNavigationOnClickListener { v: View? ->
            Navigation.findNavController(
                requireActivity(),
                R.id.fragment_main
            ).navigateUp()
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
         * @return A new instance of fragment NewsDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): NewsDetailFragment {
            val fragment = NewsDetailFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}