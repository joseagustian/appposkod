package id.jsaproject.apposkod.ui.province_detail

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.jsaproject.apposkod.R
import id.jsaproject.apposkod.databinding.ActivityProvinceDetailBinding
import id.jsaproject.apposkod.utils.ViewModelFactory

class ProvinceDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProvinceDetailBinding
    private lateinit var provinceDetailViewModel: ProvinceDetailViewModel
    private var provinceId: String = ""
    private lateinit var regencyListRecyclerViewAdapter: RegencyListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        regencyListRecyclerViewAdapter = RegencyListRecyclerViewAdapter()

        binding = ActivityProvinceDetailBinding.inflate(layoutInflater)
        setRecyclerView(this)
        setContentView(binding.root)
        initViewModel(application)

        provinceId = intent.getStringExtra("idProvince").toString()

        provinceDetailViewModel.getProvinceData(provinceId)
        provinceDetailViewModel.getRegencyData(provinceId)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        setContentProvinceDetail(provinceId)
        setContentRegencyList()
    }

    private fun initViewModel(
        application: Application
    ) {
        provinceDetailViewModel = ViewModelProvider(
            this,
            ViewModelFactory(application)
        )[ProvinceDetailViewModel::class.java]
    }

    private fun setRecyclerView(ctx: Context) {
        binding.rvRegencyList.layoutManager = LinearLayoutManager(ctx)
        binding.rvRegencyList.adapter = regencyListRecyclerViewAdapter
    }

    private fun setContentProvinceDetail(provinceId: String) {
        provinceDetailViewModel.provinceData.observe(this) { provinceData ->
            if (provinceData != null) {
                binding.lblProvinceName.text = provinceData.namaProvinsi
                binding.txtProvincePopulation.text = "Jumlah Penduduk: ${provinceData.jumlahPenduduk}"
                binding.txtProvinceDesc.text = provinceData.deskripsi
            }
        }

        val mapFileName = "peta_prov${provinceId}"

        val mapResId = binding.root.context.resources.getIdentifier(
            mapFileName, "drawable", binding.root.context.packageName
        )

        if (mapResId != 0) {
            binding.imgProvinceMap.setImageResource(mapResId)
        } else {
            binding.imgProvinceMap.setImageResource(R.drawable.ic_launcher_background)
        }
    }

    private fun setContentRegencyList() {
        provinceDetailViewModel.regencyList.observe(this) { regencyData ->
            if (regencyData != null) {
                regencyListRecyclerViewAdapter.setRegencyList(regencyData)
            }
        }
    }
}