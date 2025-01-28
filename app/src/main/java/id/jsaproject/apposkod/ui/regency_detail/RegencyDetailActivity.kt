package id.jsaproject.apposkod.ui.regency_detail

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.jsaproject.apposkod.databinding.ActivityRegencyDetailBinding
import id.jsaproject.apposkod.utils.ViewModelFactory

class RegencyDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegencyDetailBinding
    private var regencyId: String = ""
    private lateinit var regencyDetailViewModel: RegencyDetailViewModel
    private lateinit var subdistricListRecyclerViewAdapter: SubdistricListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subdistricListRecyclerViewAdapter = SubdistricListRecyclerViewAdapter()

        binding = ActivityRegencyDetailBinding.inflate(layoutInflater)
        setRecyclerView(this)
        setContentView(binding.root)
        initViewModel(application)

        regencyId = intent.getStringExtra("idRegency").toString()
        regencyDetailViewModel.getRegencyData(regencyId)
        regencyDetailViewModel.getSubdistrictData(regencyId)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.edtSearchSubdistrict.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                regencyDetailViewModel.searchSubdistrictData(
                    query,
                    regencyId
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
        regencyDetailViewModel = ViewModelProvider(
            this,
            ViewModelFactory(application)
        )[RegencyDetailViewModel::class.java]
    }

    private fun setRecyclerView(ctx: Context) {
        binding.rvSubdistricsList.layoutManager = LinearLayoutManager(ctx)
        binding.rvSubdistricsList.adapter = subdistricListRecyclerViewAdapter
    }

    private fun setContentRegencyDetail() {
        regencyDetailViewModel.regencyData.observe(this) { regencyData ->
            if (regencyData != null) {
                binding.lblRegencyName.text = regencyData.namaKabupaten
            }
        }
    }

    private fun setContentLoading() {
        regencyDetailViewModel.isSubdistrictListLoading.observe(this) { isLoading ->
            if (isLoading) {
                binding.loadingLayout.visibility = View.VISIBLE
                binding.rvSubdistricsList.visibility = View.GONE
            } else {
                binding.loadingLayout.visibility = View.GONE
                binding.rvSubdistricsList.visibility = View.VISIBLE
            }
        }
    }

    private fun setContentRegencyList() {
        regencyDetailViewModel.subdistrictList.observe(this) { subdistricList ->
            if (subdistricList != null) {
                subdistricListRecyclerViewAdapter.setSubdistricList(subdistricList)
            }
        }
    }
}