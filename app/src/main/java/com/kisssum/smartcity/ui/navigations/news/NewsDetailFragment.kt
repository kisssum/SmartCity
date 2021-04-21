package com.kisssum.smartcity.ui.navigations.news

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.kisssum.smartcity.R
import com.kisssum.smartcity.adapter.news.NewsListAdpater
import com.kisssum.smartcity.databinding.FragmentNewsDetailBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

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
    private val adpater: NewsListAdpater? = null
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentNewsDetailBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = Color.RED
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arguments = arguments

        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)

                val string = msg.obj as String
                if (JSONObject(string).getInt("code") == 200) {
                    Toast.makeText(requireContext(), "评论成功", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "评论失败", Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(requireContext(), JSONObject(string).toString(), Toast.LENGTH_SHORT).show()
                Log.d("QT", JSONObject(string).toString())
            }
        }

        // Toolbar
        binding.newsDetailToolbar.setNavigationOnClickListener { v: View? -> Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp() }
        binding.newsDetailToolbar.title = arguments!!.getString("title")

        Glide.with(requireActivity()).load(API.getBaseUrl(requireContext()) + arguments.getString("imgUrl")).into(binding.newsDetailImg)
        binding.newsDetailContent.text = arguments.getString("content")
        initNews()

        initCommendList()

        initNewsCommentAdd()

        // webView
//        binding!!.newDetailWeb.settings.javaScriptEnabled = true
//        binding!!.newDetailWeb.settings.builtInZoomControls = true
//        binding!!.newDetailWeb.webViewClient = WebViewClient()
//        binding!!.newDetailWeb.loadUrl(arguments.getString("url")!!)

        // list
//        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
//        binding!!.newsDetailList.layoutManager = layoutManager
        //        adpater = new NewsListAdpater(0, requireContext(), 3);
//        binding.newsDetailList.setAdapter(adpater);
    }

    private fun initNewsCommentAdd() {
        binding.newsDetailAdd.setOnClickListener {
            Thread {
                OkHttpClient().apply {
                    val jsonObject = JSONObject().apply {
                        this.put("userId", API.getUserId(requireContext()))
                        this.put("pressId", arguments?.getInt("id")!!)
                        this.put("content", binding.newsDetailContent.text.toString())
                    }

                    val mediaType = "application/json;charset=utf-8".toMediaTypeOrNull()
                    val requestBody = RequestBody.create(mediaType, jsonObject.toString())

                    val request = Request.Builder()
                            .url(API.getNewsCommentAdd(requireContext()))
                            .post(requestBody)
                            .header("Authorization", API.getToken(requireContext()))
                            .build()

                    val sring = this.newCall(request).execute().body!!.string()

                    val message = Message()
                    message.obj = sring
                    handler.sendMessage(message)
                }
            }.start()
        }
    }

    private fun initCommendList() {
        Volley.newRequestQueue(requireContext()).apply {
//            Log.d("QT", API.getNewsCommentList(requireContext(), arguments?.getInt("id")!!))
            val stringRequest = StringRequest(
                    API.getNewsCommentList(requireContext(), arguments?.getInt("id")!!),
                    {
                        val comments = DecodeJson.decodeNewsCommentList(it)
                        Log.d("QT", comments.size.toString())
                        Log.d("QT", API.getNewsCommentList(requireContext(), arguments?.getInt("id")!!))

                        binding.newsDetailCommentSize.text = comments.size.toString()

                        binding.newsDetailcommentList.apply {
                            this.adapter = SimpleAdapter(requireContext(), comments,
                                    R.layout.list_style_news_detail_comment_list,
                                    arrayOf("nickName", "content"),
                                    intArrayOf(R.id.lsndclName, R.id.lsndclText))
                        }
                    },
                    {}
            )

            this.add(stringRequest)
        }
    }

    private fun initNews() {
        binding.newsDetailList.apply {
            this.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            val l = ArrayList<Map<String, Any>>()
            val map = HashMap<String, Any>().apply {
                this["id"] = arguments?.getInt("id")!!
                this["title"] = arguments?.getString("title")!!
                this["content"] = arguments?.getString("content")!!
                this["imgUrl"] = arguments?.getString("imgUrl")!!
                this["viewsNumber"] = arguments?.getString("viewsNumber")!!
                this["likeNumber"] = arguments?.getString("likeNumber")!!
                this["createTime"] = arguments?.getString("createTime")!!
            }
            l.add(map)

            val dAdapter = NewsListAdpater(requireContext(), l, isHome = true, isDetail = true)
            this.adapter = dAdapter
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