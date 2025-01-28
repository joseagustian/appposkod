package id.jsaproject.apposkod.ui.regency_detail
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import id.jsaproject.apposkod.data.model.SubdistrictDataModel
import id.jsaproject.apposkod.databinding.ItemSubdistricCardBinding
import id.jsaproject.apposkod.ui.subdistrict_detail.SubdistrictDetailActivity

class SubdistricListRecyclerViewAdapter :
    RecyclerView.Adapter<SubdistricListRecyclerViewAdapter.ListViewHolder>(){

    private val subdistrictDataList = ArrayList<SubdistrictDataModel>()

    class ListViewHolder(private val binding: ItemSubdistricCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: SubdistrictDataModel) {
            binding.txtNamaKecamatan.text = data.namaKecamatan
            binding.txtKodeWilayahKecamatan.text = data.id.toString()
            binding.cvSubDistrict.setOnClickListener {
                val intent = Intent(itemView.context, SubdistrictDetailActivity::class.java)
                intent.putExtra("idSubdistrict", data.id.toString())
                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }

    fun setSubdistricList(data: List<SubdistrictDataModel?>) {
        subdistrictDataList.clear()
        subdistrictDataList.addAll(data.filterNotNull())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemSubdistricCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = subdistrictDataList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(subdistrictDataList[position])
    }
}