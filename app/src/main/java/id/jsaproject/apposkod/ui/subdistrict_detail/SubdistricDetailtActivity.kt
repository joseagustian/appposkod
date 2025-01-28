package id.jsaproject.apposkod.ui.subdistrict_detail

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.jsaproject.apposkod.databinding.ActivitySubdistrictDetailBinding
import id.jsaproject.apposkod.utils.ViewModelFactory

class SubdistrictDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubdistrictDetailBinding
    private var subdistrictId: String = ""
    private var query: String = ""
    private lateinit var subdistrictDetailViewModel: SubdistrictDetailViewModel
    private lateinit var wardListRecyclerViewAdapter: WardListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wardListRecyclerViewAdapter = WardListRecyclerViewAdapter()

        binding = ActivitySubdistrictDetailBinding.inflate(layoutInflater)
        setRecyclerView(this)
        setContentView(binding.root)
        initViewModel(application)

        subdistrictId = intent.getStringExtra("idSubdistrict").toString()
        subdistrictDetailViewModel.getSubdistrictData(subdistrictId)
        subdistrictDetailViewModel.getWardData(subdistrictId)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.edtSearchWards.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                query = s.toString().trim()
                subdistrictDetailViewModel.searchWardData(
                    subdistrictId,
                    query
                )
            }
        })

        setContentRegencyDetail()
        setContentLoading()
        setContentRegencyList()
    }

    private fun initViewModel(
        application: Application
    ) {
        subdistrictDetailViewModel = ViewModelProvider(
            this,
            ViewModelFactory(application)
        )[SubdistrictDetailViewModel::class.java]
    }

    private fun setRecyclerView(ctx: Context) {
        binding.rvWardList.layoutManager = LinearLayoutManager(ctx)
        binding.rvWardList.adapter = wardListRecyclerViewAdapter
    }

    private fun setContentRegencyDetail() {
        subdistrictDetailViewModel.subdistrictData.observe(this) { subdistrictData ->
            if (subdistrictData != null) {
                binding.lblSubdistrictName.text = subdistrictData.namaKecamatan
            }
        }
    }

    private fun setContentLoading() {
        subdistrictDetailViewModel.isWardListLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.loadingLayout.visibility = View.VISIBLE
                binding.rvWardList.visibility = View.GONE
            } else {
                binding.loadingLayout.visibility = View.GONE
                binding.rvWardList.visibility = View.VISIBLE
            }
        }
    }

    private fun setContentRegencyList() {
        subdistrictDetailViewModel.wardList.observe(this) { wardList ->
            if (wardList != null) {
                wardListRecyclerViewAdapter.setWardList(wardList)
            }
        }
    }
}