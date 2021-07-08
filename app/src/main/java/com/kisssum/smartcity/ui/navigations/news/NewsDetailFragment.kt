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
    private var newsId: Int = 0

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

        newsId = requireArguments().getInt("id", 0)

        GlobalScope.launch(Dispatchers.Main) {
            initNewsDetail()
            initNewsCommentsList()
        }

        binding.newsDetailAdd.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) { initNewsCommentsAdd() }
        }

        // Toolbar
        binding.newsDetailToolbar.setNavigationOnClickListener { v: View? ->
            Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp()
        }
    }

    private suspend fun initNewsCommentsAdd() {
        val content = binding.newsDetailCommentcotent.text.toString()

        if (content == "") {
            UpdateUI.toastUi(requireContext(), "请输入内容!")
        } else {
            val newsCommentAddString: String =
                withContext(Dispatchers.IO) {
                    MRString.getNewsCommentAdd(
                        requireContext(),
                        newsId,
                        content
                    )
                }
            val newsCommentAddObj = DecodeJson.decodeCommentAdd(newsCommentAddString)

            if (newsCommentAddObj == "") {
                UpdateUI.toastUi(requireContext(), "发布失败!")
            } else {
                UpdateUI.toastUi(requireContext(), "发布成功!")
                binding.newsDetailCommentcotent.setText("")

                val newsCommentString =
                    withContext(Dispatchers.IO) { MRString.getNewsComments(newsId) }
                val newsCommentObj = DecodeJson.decodeNewsCommentsList(newsCommentString)
                binding.newsDetailCommentSize.text = newsCommentObj.size.toString()
                binding.newsDetailcommentList.apply {
                    this.layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

                    val dAdapter = NewsCommentsListAdpater(requireContext(), newsCommentObj)
                    this.adapter = dAdapter
                }
            }
        }
    }

    private suspend fun initNewsCommentsList() {
        val newsCommentString =
            withContext(Dispatchers.IO) { MRString.getNewsComments(newsId) }
        val newsCommentObj = DecodeJson.decodeNewsCommentsList(newsCommentString)
        binding.newsDetailCommentSize.text = newsCommentObj.size.toString()
        binding.newsDetailcommentList.apply {
            this.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            val dAdapter = NewsCommentsListAdpater(requireContext(), newsCommentObj)
            this.adapter = dAdapter
        }
    }

    private suspend fun initNewsDetail() {
        val newsDetailString =
            withContext(Dispatchers.IO) { MRString.getNewsDetail(newsId) }
        val newsDetailObj = DecodeJson.decodeNewsDetail(newsDetailString)
        binding.newsDetailTitle.text = "标题:${newsDetailObj["title"]}"
        binding.newsDetailsubTitle.text = "副标题:${newsDetailObj["subTitle"]}"
        Glide.with(requireActivity()).load(API.getBaseUrl() + newsDetailObj["cover"])
            .into(binding.newsDetailImg)
        binding.newsDetailPublichDate.text = "发布时间:${newsDetailObj["publishDate"]}"
        binding.newsDetailWebContent.loadData(
            newsDetailObj["content"].toString(),
            "text/html",
            "UTF-8"
        )
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