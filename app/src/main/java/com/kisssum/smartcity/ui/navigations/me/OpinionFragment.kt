package com.kisssum.smartcity.ui.navigations.me

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentOpinionBinding
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import com.kisssum.smartcity.tool.UpdateUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [OpinionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OpinionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var binding: FragmentOpinionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentOpinionBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window.statusBarColor = Color.RED
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().window.statusBarColor = Color.TRANSPARENT
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.opinionToolbar.setNavigationOnClickListener { v: View? -> Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp() }

        binding.btnSubmit.setOnClickListener { v: View? ->
            if (binding.opinionTitle.text.toString() == "" || binding.opinionContent.text.toString() == "") {
                Toast.makeText(requireContext(), "标题和内容不能为空!", Toast.LENGTH_SHORT).show()
            } else {
                GlobalScope.launch(Dispatchers.Main) {
                    val title = binding.opinionTitle.text.toString()
                    val content = binding.opinionContent.text.toString()

                    val feedBackString = withContext(Dispatchers.IO) { MRString.getFeedBack(requireContext(), title, content) }
                    val feedBackObj = DecodeJson.decodeFeedBack(feedBackString)

                    if (feedBackObj == "") {
                        UpdateUI.toastUi(requireContext(), "提交出错!")
                    } else {
                        UpdateUI.toastUi(requireContext(), "提交成功!")
                    }
                }
            }

            binding.opinionContent.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    binding.opinionCount.text = (150 - s.length).toString()
                }
            })
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
         * @return A new instance of fragment OpinionFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): OpinionFragment {
            val fragment = OpinionFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}