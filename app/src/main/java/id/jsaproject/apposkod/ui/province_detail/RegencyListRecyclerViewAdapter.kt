package id.jsaproject.apposkod.ui.province_detail
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.jsaproject.apposkod.data.model.RegencyDataModel
import id.jsaproject.apposkod.databinding.ItemRegencyCardBinding
import id.jsaproject.apposkod.ui.regency_detail.RegencyDetailActivity

class RegencyListRecyclerViewAdapter :
    RecyclerView.Adapter<RegencyListRecyclerViewAdapter.ListViewHolder>(){

    private val regencyDataList = ArrayList<RegencyDataModel>()

    class ListViewHolder(private val binding: ItemRegencyCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: RegencyDataModel) {
            binding.txtNamaKabupaten.text = data.namaKabupaten
            binding.txtKodeWilayahKabupaten.text = data.id.toString()
            binding.cvRegency.setOnClickListener {
                val intent = Intent(itemView.context, RegencyDetailActivity::class.java)
                intent.putExtra("idRegency", data.id.toString())
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }

    fun setRegencyList(data: List<RegencyDataModel?>) {
        regencyDataList.clear()
        regencyDataList.addAll(data.filterNotNull())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRegencyCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = regencyDataList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(regencyDataList[position])
    }
}