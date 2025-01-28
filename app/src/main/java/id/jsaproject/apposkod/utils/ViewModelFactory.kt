package id.jsaproject.apposkod.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.jsaproject.apposkod.ProvinceDataViewModel
import id.jsaproject.apposkod.ui.province_detail.ProvinceDetailViewModel
import id.jsaproject.apposkod.ui.regency_detail.RegencyDetailViewModel
import id.jsaproject.apposkod.ui.subdistrict_detail.SubdistrictDetailViewModel

class ViewModelFactory(
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(ProvinceDataViewModel::class.java) -> {
                return ProvinceDataViewModel(application) as T
            }
            modelClass.isAssignableFrom(ProvinceDetailViewModel::class.java) -> {
                return ProvinceDetailViewModel(application) as T
            }
            modelClass.isAssignableFrom(RegencyDetailViewModel::class.java) -> {
                return RegencyDetailViewModel(application) as T
            }
            modelClass.isAssignableFrom(SubdistrictDetailViewModel::class.java) -> {
                return SubdistrictDetailViewModel(application) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}