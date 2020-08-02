package uz.mod.templatex.ui.profile.authorized.myOrder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemOrderAttributeBinding
import uz.mod.templatex.model.remote.response.profile.AttributeCombination

class AttributeAdapter : RecyclerView.Adapter<AttributeAdapter.AttributeViewHolder>() {

    private var items: List<AttributeCombination> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AttributeViewHolder(
        ItemOrderAttributeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: AttributeViewHolder, position: Int) =
        holder.bind(items[position])

    fun setItems(items: List<AttributeCombination>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    class AttributeViewHolder(val binding: ItemOrderAttributeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AttributeCombination): Unit = with(binding) {
            this.item = item
            executePendingBindings()
        }
    }
}