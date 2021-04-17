package com.kisssum.smartcity.ui.navigations.allservice.film

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.kisssum.smartcity.R
import com.kisssum.smartcity.adapter.allservice.film.FilmChoiceSeatListAdapater
import com.kisssum.smartcity.databinding.FragmentFilmChoiceSeatBinding
import com.kisssum.smartcity.state.film.FilmModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilmChoiceSeatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilmChoiceSeatFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val IS_POPULAR = 0
    private val IS_UPCOMING = 1
    private lateinit var binding: FragmentFilmChoiceSeatBinding
    private lateinit var viewModel: FilmModel
    private var theaterPosition = 0
    private var filmType = 0
    private var filmPosition = 0
    private var seesionPosition = 0
    private var seatData = ArrayList<ArrayList<Int>>()
    private val rowData = ArrayList<Int>()
    private val columnData = ArrayList<Int>()
    private lateinit var filmChoiceSeatListAdapater: FilmChoiceSeatListAdapater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFilmChoiceSeatBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        theaterPosition = arguments?.getInt("theaterPosition")!!
        filmType = arguments?.getInt("filmType")!!
        filmPosition = arguments?.getInt("filmPosition")!!
        seesionPosition = arguments?.getInt("seesionPosition")!!

        initViewModel()
        initToolbar()
        restore()
        initList()
        initFeatures()
    }

    private fun initFeatures() {
        binding.fcstAddSeat.setOnClickListener {
            if (rowData.size < 4) {
                rowData.add(resources.getStringArray(R.array.choice_row)[binding.fcstRow.selectedItemPosition].toInt())
                columnData.add(resources.getStringArray(R.array.choice_column)[binding.fcstColumn.selectedItemPosition].toInt())

                seatData = ArrayList<ArrayList<Int>>().apply {
                    this.add(rowData)
                    this.add(columnData)
                }

                filmChoiceSeatListAdapater.setseatData(seatData)
            } else {
                Toast.makeText(requireContext(), "一次最多购买${rowData.size}张票", Toast.LENGTH_SHORT).show()
            }
        }

        binding.fcstOkSeat.setOnClickListener {
            if (rowData.size != 0) {
                val price = viewModel.getSessionData().value?.get(seesionPosition)!!["price"]!!.toFloat() * rowData.size
                val view = layoutInflater.inflate(R.layout.alertdialog_qrcode, null).apply {
                    this.findViewById<ImageView>(R.id.aqQrCode).setImageBitmap(getQRCode("共${price}元", 600, 600))
                }

                AlertDialog.Builder(requireContext())
                        .setTitle("扫码支付")
                        .setCancelable(true)
                        .setView(view)
                        .setPositiveButton("已支付") { dialogInterface: DialogInterface, i: Int ->
                            Toast.makeText(requireContext(), "支付成功!", Toast.LENGTH_SHORT).show()
                        }
                        .setNegativeButton("放弃支付") { dialogInterface: DialogInterface, i: Int -> }
                        .create()
                        .show()
            } else {
                Toast.makeText(requireContext(), "请选择座位!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getQRCode(text: String, width: Int, height: Int): Bitmap {
        QRCodeWriter().let {
            val hints = HashMap<EncodeHintType, String>().apply {
                this[EncodeHintType.CHARACTER_SET] = "utf-8"
            }

            val encode = it.encode(text, BarcodeFormat.QR_CODE, width, height, hints)

            val pixes = IntArray(width * height).apply {
                for (i in 0 until height)
                    for (j in 0 until width) {
                        this[i * width + j] = when (encode[j, i]) {
                            true -> 0x000000
                            else -> 0xffffff
                        }
                    }
            }

            return Bitmap.createBitmap(pixes, 0, width, width, height, Bitmap.Config.RGB_565)
        }
    }

    private fun initList() {
        binding.fcstSeatList.apply {
            this.layoutManager = GridLayoutManager(requireContext(), 4)

            seatData = ArrayList<ArrayList<Int>>().apply {
                this.add(rowData)
                this.add(columnData)
            }

            filmChoiceSeatListAdapater = FilmChoiceSeatListAdapater(requireContext(), seatData, viewModel.getSessionData().value?.get(seesionPosition)!!["price"]!!)
            this.adapter = filmChoiceSeatListAdapater
        }
    }

    private fun restore() {
        // restore film name
        when (filmType) {
            IS_POPULAR -> viewModel.getPopularData().value?.get(filmPosition)!!
            else -> viewModel.getUpcomingData().value?.get(filmPosition)!!
        }.apply {
            binding.fcstFilmName.text = this["name"]
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity(), ViewModelProvider.AndroidViewModelFactory(requireActivity().application)).get(FilmModel::class.java)
    }

    private fun initToolbar() {
        binding.fcstToolbar.setNavigationOnClickListener { v ->
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
         * @return A new instance of fragment FilmChoiceSeatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FilmChoiceSeatFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}