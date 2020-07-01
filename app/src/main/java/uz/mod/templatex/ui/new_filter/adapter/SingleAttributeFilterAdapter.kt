package uz.mod.templatex.ui.new_filter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_main_filter_attribute.view.*
import kotlinx.android.synthetic.main.item_main_filter_attribute.view.title
import kotlinx.android.synthetic.main.item_main_filter_sort.view.*
import kotlinx.android.synthetic.main.item_main_filter_title.view.*
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ItemFilterAttributeValueBinding
import uz.mod.templatex.model.local.entity.AttributeValue
import uz.mod.templatex.model.local.entity.FilterAttribute
import uz.mod.templatex.model.local.entity.IValue
import uz.mod.templatex.ui.new_filter.MainFilterFragmentDirections
import uz.mod.templatex.ui.new_filter.SharedFilterViewModel

class SingleAttributeFilterAdapter(val items : MutableList<AttributeValueItem> = mutableListOf(), val sharedFilterViewModel: SharedFilterViewModel, val attrId: Int)
    : RecyclerView.Adapter<SingleAttributeFilterAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return items[position].hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemFilterAttributeValueBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    data class AttributeValueItem(val attribute : IValue)

    inner class ViewHolder(val binding : ItemFilterAttributeValueBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(attributeItem : AttributeValueItem) {
            binding.attribute = attributeItem.attribute
            itemView.clickableCl.setOnClickListener {
                if (attrId==-2){
                    val find = sharedFilterViewModel.currentFilter?.brands?.find { it.id == attributeItem.attribute.id }
                    find?.apply { selected = !selected }
                } else {
                    val find = sharedFilterViewModel.currentFilter?.attributes?.find { it.id == attrId }?.values?.find { it.id == attributeItem.attribute.id }
                    find?.apply { selected = !selected }
                }
                notifyDataSetChanged()
            }
        }
    }
}