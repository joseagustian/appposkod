package id.jsaproject.apposkod.ui.subdistrict_detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.jsaproject.apposkod.data.model.RegencyDataModel
import id.jsaproject.apposkod.data.model.SubdistrictDataModel
import id.jsaproject.apposkod.data.model.WardDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubdistrictDetailViewModel(private val application: Application) : ViewModel() {
    private val _subdistrictData = MutableLiveData<SubdistrictDataModel?>()
    val subdistrictData: LiveData<SubdistrictDataModel?> = _subdistrictData

    fun getSubdistrictData(subdistrictId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jsonString = application.assets.open("json/subdistricts.json").bufferedReader().use { it.readText() }
                val subdistrictList = parseJsonToSubdistrictData(jsonString)

                withContext(Dispatchers.Main) {
                    val subdistrictData = subdistrictList?.find { it.id.toString() == subdistrictId }
                    _subdistrictData.value = subdistrictData
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

    private val _wardList = MutableLiveData<List<WardDataModel>?>()
    val wardList: LiveData<List<WardDataModel>?> = _wardList

    private val _isWardListLoading = MutableLiveData<Boolean>()
    val isWardListLoading: LiveData<Boolean> = _isWardListLoading

    private var _unfilteredWardList: List<WardDataModel>? = null

    fun getWardData(subdistrictId: String) {
        _isWardListLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val jsonString = application.assets.open("json/wards.json").bufferedReader().use { it.readText() }
                val filteredWardList = parseJsonToWardData(jsonString)?.filter { ward ->
                    val wardSubdistrictId = ward.id
                        ?.split(".")
                        ?.take(3)
                        ?.joinToString(".")
                    wardSubdistrictId == subdistrictId
                }

                withContext(Dispatchers.Main) {
                    _unfilteredWardList = filteredWardList
                    _wardList.value = filteredWardList
                    _isWardListLoading.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun searchWardData(subdistrictId: String, query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val filteredWardList = _unfilteredWardList?.filter { ward ->
                    val wardSubdistrictId = ward.id
                        ?.split(".")
                        ?.take(3)
                        ?.joinToString(".")
                    wardSubdistrictId == subdistrictId
                }

                val finalFilteredList = filteredWardList?.filter { ward ->
                    ward.namaKelurahan?.contains(query, ignoreCase = true) == true
                }

                withContext(Dispatchers.Main) {
                    _wardList.value = finalFilteredList
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun parseJsonToWardData(jsonString: String): List<WardDataModel>? {
        return try {
            val gson = Gson()
            val type = object : TypeToken<List<WardDataModel>>() {}.type
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}