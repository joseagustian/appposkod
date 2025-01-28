package id.jsaproject.apposkod

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import id.jsaproject.apposkod.databinding.FragmentHomeBinding
import id.jsaproject.apposkod.utils.FilterProvinceType
import id.jsaproject.apposkod.utils.ViewModelFactory

class HomeFragment : Fragment()  {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var application: Application
    private val binding get() = _binding!!
    private lateinit var provinceDataViewModel: ProvinceDataViewModel
    private lateinit var provinceListRecyclerViewAdapter: ProvinceListRecyclerViewAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container, false)
        application = requireActivity().application
        provinceListRecyclerViewAdapter = ProvinceListRecyclerViewAdapter()
        initViewModel(application)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView(requireContext())

        val filterProvinceTypeString = listOf(
            "Tanpa Filter",
            "Berdasarkan Abjad (A - Z)",
            "Berdasarkan Abjad (Z - A)"
        )


        val adapterFilterProvince = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            filterProvinceTypeString
        )

        binding.spinnerSortProvince.adapter = adapterFilterProvince

        binding.btnSortProvince.setOnClickListener {
            binding.spinnerSortProvince.performClick()
        }

        binding.spinnerSortProvince.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedFilter = filterProvinceTypeString[position]
                when (selectedFilter) {
                    "Berdasarkan Abjad (A - Z)" -> {
                        provinceDataViewModel.filterProvinceList(FilterProvinceType.A_TO_Z)
                    }
                    "Berdasarkan Abjad (Z - A)" -> {
                        provinceDataViewModel.filterProvinceList(FilterProvinceType.Z_TO_A)
                    }
                    "Tanpa Filter" -> {
                        provinceDataViewModel.filterProvinceList(FilterProvinceType.DEFAULT)
                    }
                    else -> { provinceDataViewModel.filterProvinceList(FilterProvinceType.DEFAULT) }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        provinceDataViewModel.getProvinceDataList()

        provinceDataViewModel.provinceDataList.observe(viewLifecycleOwner) { provinceList ->
            provinceList.let { data ->
                if (data != null) {
                    provinceListRecyclerViewAdapter.setProvinceList(data)
                }
            }
        }
    }

    private fun initViewModel(
        application: Application
    ) {
        provinceDataViewModel = ViewModelProvider(
            this,
            ViewModelFactory(application)
        )[ProvinceDataViewModel::class.java]
    }

    private fun setRecyclerView(ctx: Context) {
        binding.rvProvinceList.layoutManager = LinearLayoutManager(ctx)
        binding.rvProvinceList.adapter = provinceListRecyclerViewAdapter
    }
}