package id.jsaproject.apposkod

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.jsaproject.apposkod.data.model.ProvinceDataModel
import id.jsaproject.apposkod.databinding.ItemProvinceCardBinding
import id.jsaproject.apposkod.ui.province_detail.ProvinceDetailActivity

class ProvinceListRecyclerViewAdapter :
    RecyclerView.Adapter<ProvinceListRecyclerViewAdapter.ListViewHolder>(){

    private val provinceList = ArrayList<ProvinceDataModel>()

    class ListViewHolder(private val binding: ItemProvinceCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ProvinceDataModel) {
            binding.txtNamaProvinsi.text = data.namaProvinsi ?: "-"
            binding.txtJumlahKabupaten.text = data.jumlahKabupaten ?: "-"
            binding.txtJumlahKecamatan.text = data.jumlahKecamatan ?: "-"
            binding.txtJumlahKelurahan.text = data.jumlahKelurahan ?: "-"
            binding.txtLuasProvinsi.text = data.luasWilayah ?: "-"
            val logoName = "logoprovinsi_${data.id}"

            val logoResId = binding.root.context.resources.getIdentifier(
                logoName, "drawable", binding.root.context.packageName
            )
            if (logoResId != 0) {
                binding.imgProvinceLogo.setImageResource(logoResId)
            } else {
                binding.imgProvinceLogo.setImageResource(R.drawable.ic_launcher_background)
            }

            binding.cvProvinces.setOnClickListener {
                val intent = Intent(itemView.context, ProvinceDetailActivity::class.java)
                intent.putExtra("idProvince", data.id.toString())
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }

    fun setProvinceList(data: List<ProvinceDataModel?>) {
        provinceList.clear()
        provinceList.addAll(data.filterNotNull())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemProvinceCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = provinceList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(provinceList[position])
    }
}