package id.jsaproject.apposkod.ui.regency_detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.jsaproject.apposkod.data.model.RegencyDataModel
import id.jsaproject.apposkod.data.model.SubdistrictDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegencyDetailViewModel(private val application: Application) : ViewModel() {
    private val _regencyData = MutableLiveData<RegencyDataModel?>()
    val regencyData: LiveData<RegencyDataModel?> = _regencyData

    fun getRegencyData(regencyId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jsonString = application.assets.open("json/regency.json").bufferedReader().use { it.readText() }
                val regencyList = parseJsonToRegencyData(jsonString)

                withContext(Dispatchers.Main) {
                    val regencyData = regencyList?.find { it.id.toString() == regencyId }
                    _regencyData.value = regencyData
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

    private val _subdistrictList = MutableLiveData<List<SubdistrictDataModel>?>()
    val subdistrictList: LiveData<List<SubdistrictDataModel>?> = _subdistrictList

    private var _isSubdistrictListLoading = MutableLiveData<Boolean>()
    val isSubdistrictListLoading: LiveData<Boolean> = _isSubdistrictListLoading

    private var _unfilteredSubdistrictList: List<SubdistrictDataModel>? = null

    fun getSubdistrictData(regencyId: String) {
        _isSubdistrictListLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jsonString = application.assets.open("json/subdistricts.json").bufferedReader().use { it.readText() }
                val subdistrictList = parseJsonToSubdistrictData(jsonString)

                _unfilteredSubdistrictList = subdistrictList?.filter { subdistrict ->
                    val subdistrictRegencyId = subdistrict.id
                        ?.split(".")
                        ?.take(2)
                        ?.joinToString(".")
                    subdistrictRegencyId == regencyId
                }

                withContext(Dispatchers.Main) {
                    _subdistrictList.value = _unfilteredSubdistrictList
                    _isSubdistrictListLoading.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun searchSubdistrictData(query: String, regencyId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val filteredSubdistrictList = _unfilteredSubdistrictList?.filter { subdistrict ->
                    val subdistrictRegencyId = subdistrict.id
                        ?.split(".")
                        ?.take(2)
                        ?.joinToString(".")
                    subdistrictRegencyId == regencyId
                }

                val finalFilteredList = filteredSubdistrictList?.filter { subdistrict ->
                    subdistrict.namaKecamatan?.contains(query, ignoreCase = true) == true
                }

                withContext(Dispatchers.Main) {
                    _subdistrictList.value = finalFilteredList
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun parseJsonToSubdistrictData(jsonString: String): List<SubdistrictDataModel>? {
        return try {
            val gson = Gson()
            val type = object : TypeToken<List<SubdistrictDataModel>>() {}.type
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}