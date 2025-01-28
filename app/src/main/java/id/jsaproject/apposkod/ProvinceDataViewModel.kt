package id.jsaproject.apposkod

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.jsaproject.apposkod.data.model.ProvinceDataModel
import id.jsaproject.apposkod.utils.FilterProvinceType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProvinceDataViewModel(private val application: Application) : ViewModel() {
    private val _provinceDataList = MutableLiveData<List<ProvinceDataModel>?>()
    val provinceDataList: LiveData<List<ProvinceDataModel>?> = _provinceDataList
    private var originalProvinceList: List<ProvinceDataModel>? = null

    fun getProvinceDataList() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jsonString = application.assets.open("json/province.json").bufferedReader().use { it.readText() }
                val provinceList = parseJsonToProvinceData(jsonString)

                withContext(Dispatchers.Main) {
                    _provinceDataList.value = provinceList
                    originalProvinceList = provinceList
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun filterProvinceList(filterProvinceType: FilterProvinceType) {
        val currentProvinceList = _provinceDataList.value
        if (currentProvinceList != null) {
            val sortedProvinceList = when (filterProvinceType) {
                FilterProvinceType.A_TO_Z -> currentProvinceList.sortedBy { it.namaProvinsi }
                FilterProvinceType.Z_TO_A -> currentProvinceList.sortedByDescending { it.namaProvinsi }
                FilterProvinceType.DEFAULT -> originalProvinceList
            }

            _provinceDataList.value = sortedProvinceList
        }

    }

    private fun parseJsonToProvinceData(jsonString: String): List<ProvinceDataModel>? {
        return try {
            val gson = Gson()
            val type = object : TypeToken<List<ProvinceDataModel>>() {}.type
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}