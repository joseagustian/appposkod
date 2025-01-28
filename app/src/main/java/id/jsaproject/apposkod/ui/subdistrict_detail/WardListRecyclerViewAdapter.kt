package id.jsaproject.apposkod.ui.subdistrict_detail
import android.content.ClipData
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import id.jsaproject.apposkod.data.model.WardDataModel
import id.jsaproject.apposkod.databinding.ItemWardCardBinding

class WardListRecyclerViewAdapter :
    RecyclerView.Adapter<WardListRecyclerViewAdapter.ListViewHolder>(){

    private val wardDataList = ArrayList<WardDataModel>()

    class ListViewHolder(private val binding: ItemWardCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: WardDataModel) {
            binding.txtNamaKelurahan.text = data.namaKelurahan
            binding.txtKodePos.text = data.kodePos
            binding.txtKodeWilayahKelurahan.text = data.id
            binding.btnClipboard.setOnClickListener {
                val context: Context = itemView.context
                copyToClipboard(context, data)
            }
        }

        private fun copyToClipboard(context: Context, data: WardDataModel) {
            val textToCopy = data.kodePos

            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager

            val clip = ClipData.newPlainText("Ward ID", textToCopy)

            clipboard.setPrimaryClip(clip)

            Toast.makeText(context, "Kode Pos ${data.namaKelurahan} Berhasil Tersalin", Toast.LENGTH_SHORT).show()
        }
    }

    fun setWardList(data: List<WardDataModel?>) {
        wardDataList.clear()
        wardDataList.addAll(data.filterNotNull())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemWardCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = wardDataList.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(wardDataList[position])
    }
}