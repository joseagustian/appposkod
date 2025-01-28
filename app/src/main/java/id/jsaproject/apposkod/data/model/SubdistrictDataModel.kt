package id.jsaproject.apposkod.data.model

import com.google.gson.annotations.SerializedName

data class SubdistrictDataModel (
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("nama_kecamatan")
    val namaKecamatan: String? = null,
)