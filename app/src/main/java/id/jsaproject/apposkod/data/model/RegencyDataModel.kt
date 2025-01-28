package id.jsaproject.apposkod.data.model

import com.google.gson.annotations.SerializedName

data class RegencyDataModel (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("nama_kabupaten")
    val namaKabupaten: String? = null,

    @field:SerializedName("type")
    val type: String? = null,
)