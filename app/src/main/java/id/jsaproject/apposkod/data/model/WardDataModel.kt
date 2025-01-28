package id.jsaproject.apposkod.data.model

import com.google.gson.annotations.SerializedName

data class WardDataModel (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("nama_kelurahan")
    val namaKelurahan: String? = null,

    @field:SerializedName("zip")
    val kodePos: String? = null
)