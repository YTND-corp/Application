package uz.mod.templatex.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemCardAttributeBinding
import uz.mod.templatex.model.local.entity.AttributeCombination

class AttributeAdapter : RecyclerView.Adapter<AttributeAdapter.AttributeViewHolder>() {

    private var items: List<AttributeCombination> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AttributeViewHolder(
        ItemCardAttributeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: AttributeViewHolder, position: Int) =
        holder.bind(items[position])

    fun setItems(items: List<AttributeCombination>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    class AttributeViewHolder(val binding: ItemCardAttributeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AttributeCombination) {
            binding.apply {
                this.item = item
                executePendingBindings()
            }
        }
    }
}