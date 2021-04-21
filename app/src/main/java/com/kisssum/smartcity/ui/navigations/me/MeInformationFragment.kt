package com.kisssum.smartcity.ui.navigations.me

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.kisssum.smartcity.R
import com.kisssum.smartcity.databinding.FragmentMeInformationBinding
import com.kisssum.smartcity.tool.API
import com.kisssum.smartcity.tool.DecodeJson
import com.kisssum.smartcity.tool.MRString
import com.kisssum.smartcity.tool.UpdateUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.jar.Manifest

/**
 * A simple [Fragment] subclass.
 * Use the [MeInformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MeInformationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var binding: FragmentMeInformationBinding
    private lateinit var handler: Handler
    private lateinit var userInfo: Map<String, Any>

    private val RESTORE = 0
    private val SAVE = 1
    private val IS_SELECTIMG = 3
    private val IS_CAPTURE = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentMeInformationBinding.inflate(inflater)
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

    private fun resotre() {
        GlobalScope.launch(Dispatchers.Main) {
            val userInfoString = withContext(Dispatchers.IO) { MRString.getUserInfo(requireContext()) }
            val userInfoObj = DecodeJson.decodeUserInfo(userInfoString)

            if (userInfoObj["avatar"] != "")
                Glide.with(requireActivity()).load(userInfoObj["avatar"]).into(binding.miImg)

            binding.miName.setText(userInfoObj["nickName"])
            binding.miSex.isChecked = when (userInfoObj["sex"]) {
                "0" -> false
                else -> true
            }
            binding.miPhone.setText(userInfoObj["phonenumber"])
            binding.miEmail.setText(userInfoObj["email"])
            binding.zjNumber.setText(userInfoObj["idCard"])
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resotre()
        initBtn()
    }

    private fun initBtn() {
        binding.meInformationToolbar.setNavigationOnClickListener { v: View? -> Navigation.findNavController(requireActivity(), R.id.fragment_main).navigateUp() }

        binding.meInformationToolbar.setOnMenuItemClickListener { item: MenuItem ->
            if (item.itemId == R.id.item_me_information_change) {
                GlobalScope.launch(Dispatchers.Main) {
                    val email = binding.miEmail.text.toString()
                    val idCard = binding.zjNumber.text.toString()
                    val nickname = binding.miName.text.toString()
                    val miPhone = binding.miPhone.text.toString()
                    val sex = when (binding.miSex.isChecked) {
                        false -> "1"
                        else -> "0"
                    }

                    val userUpdateString = withContext(Dispatchers.IO) { MRString.getUserUpdate(requireContext(), email, idCard, nickname, miPhone, sex) }
                    val userUpdateObj = DecodeJson.decodeUserUpdate(userUpdateString)

                    if (userUpdateObj == "") {
                        UpdateUI.toastUi(requireContext(), "修改失败!")
                    } else {
                        UpdateUI.toastUi(requireContext(), "修改成功!")
                    }
                }
            }
            true
        }

        binding.miImg.setOnClickListener {
            AlertDialog.Builder(requireContext())
                    .setTitle("头像")
                    .setMessage("选择方式")
                    .setPositiveButton("图库") { dialogInterface: DialogInterface, i: Int ->
                        run {
                            if (requireActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                goSelectImg()
                            } else {
                                requireActivity().requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), IS_SELECTIMG)
                            }
                        }
                    }
                    .setNegativeButton("拍照") { dialogInterface: DialogInterface, i: Int ->
                        run {
                            if (requireActivity().checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                                goCapture()
                            } else {
                                requireActivity().requestPermissions(arrayOf(android.Manifest.permission.CAMERA), IS_CAPTURE)
                            }
                        }
                    }
                    .create()
                    .show()
        }
    }

    private fun goSelectImg() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, IS_SELECTIMG)
    }

    private fun goCapture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, IS_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            IS_SELECTIMG -> {
                val d = data?.data
                binding.miImg.setImageURI(d)
            }
            IS_CAPTURE -> {
                val d = data?.extras?.get("data") as Bitmap
                binding.miImg.setImageBitmap(d)
            }
            else -> ""
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            IS_SELECTIMG -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goSelectImg()
                }
            }
            IS_CAPTURE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    goCapture()
                }
            }
            else -> ""
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
         * @return A new instance of fragment MeInformationFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): MeInformationFragment {
            val fragment = MeInformationFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}