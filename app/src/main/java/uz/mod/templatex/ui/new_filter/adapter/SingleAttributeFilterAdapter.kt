package uz.mod.templatex.ui.new_filter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_main_filter_attribute.view.*
import uz.mod.templatex.databinding.ItemFilterAttributeValueBinding
import uz.mod.templatex.model.local.entity.IValue

class SingleAttributeFilterAdapter(
    val items: MutableList<AttributeValueItem> = mutableListOf(),
    val listener: (item: AttributeValueItem) -> Unit
) : RecyclerView.Adapter<SingleAttributeFilterAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return items[position].hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFilterAttributeValueBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    data class AttributeValueItem(val attribute: IValue)

    inner class ViewHolder(val binding: ItemFilterAttributeValueBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(attributeItem: AttributeValueItem) {
            binding.attribute = attributeItem.attribute
            itemView.clickableCl.setOnClickListener {
                listener.invoke(attributeItem)
            }
        }
    }
}