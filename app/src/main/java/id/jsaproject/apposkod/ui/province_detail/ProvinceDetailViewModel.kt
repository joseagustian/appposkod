package id.jsaproject.apposkod.ui.province_detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.jsaproject.apposkod.data.model.ProvinceDataModel
import id.jsaproject.apposkod.data.model.RegencyDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProvinceDetailViewModel(private val application: Application) : ViewModel() {
    private val _provinceData = MutableLiveData<ProvinceDataModel?>()
    val provinceData: LiveData<ProvinceDataModel?> = _provinceData

    fun getProvinceData(provinceId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jsonString = application.assets.open("json/province.json").bufferedReader().use { it.readText() }
                val provinceList = parseJsonToProvinceData(jsonString)

                withContext(Dispatchers.Main) {
                    val provinceData = provinceList?.find { it.id.toString() == provinceId }
                    _provinceData.value = provinceData
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
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

    private val _regencyList = MutableLiveData<List<RegencyDataModel>?>()
    val regencyList: LiveData<List<RegencyDataModel>?> = _regencyList
    fun getRegencyData(provinceId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jsonString = application.assets.open("json/regency.json").bufferedReader().use { it.readText() }
                val regencyList = parseJsonToRegencyData(jsonString)

                withContext(Dispatchers.Main) {
                    val regencyData = regencyList?.filter { regency ->
                        val regencyProvinceId = regency.id?.substringBefore(".")
                        regencyProvinceId == provinceId
                    }
                    _regencyList.value = regencyData
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseJsonToRegencyData(jsonString: String): List<RegencyDataModel>? {
        return try {
            val gson = Gson()
            val type = object : TypeToken<List<RegencyDataModel>>() {}.type
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}