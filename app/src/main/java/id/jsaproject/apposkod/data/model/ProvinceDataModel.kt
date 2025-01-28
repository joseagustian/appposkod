package id.jsaproject.apposkod.data.model
import com.google.gson.annotations.SerializedName

data class ProvinceDataModel (
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nama_provinsi")
    val namaProvinsi: String? = null,

    @field:SerializedName("jumlah_kabupaten")
    val jumlahKabupaten: String? = null,

    @field:SerializedName("jumlah_kecamatan")
    val jumlahKecamatan: String? = null,

    @field:SerializedName("jumlah_kelurahan")
    val jumlahKelurahan: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("jumlah_penduduk")
    val jumlahPenduduk: String? = null,

    @field:SerializedName("luas_wilayah")
    val luasWilayah: String? = null
)