package uz.mod.templatex.ui.new_filter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_main_filter_title.view.*
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ItemMainFilterAttributeBinding
import uz.mod.templatex.databinding.ItemMainFilterSortBinding
import uz.mod.templatex.model.local.entity.FilterAttribute
import uz.mod.templatex.ui.new_filter.SharedFilterViewModel

class MainFilterAdapter(val items : MutableList<MainFilterDataItem<*>> = mutableListOf(),val sharedFilterViewModel: SharedFilterViewModel) : RecyclerView.Adapter<MainFilterAdapter.MainFilterHolder>() {
    abstract class MainFilterHolder(v : View) : RecyclerView.ViewHolder(v){
        abstract fun bind(item : MainFilterDataItem<*>)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFilterHolder {
        when(viewType){
            0 -> return MainFilterTitleViewHolder(parent)
            1 -> return MainFilterSortViewHolder(ItemMainFilterSortBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            2 -> return MainFilterAttributeViewHolder(ItemMainFilterAttributeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            3 -> return MainFilterBrandViewHolder(ItemMainFilterAttributeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
        throw IllegalStateException("Bad data in adapter")
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        when{
            items[position] is TitleItem -> return 0
            items[position] is SortItem -> return 1
            items[position] is AttributeItem -> return 2
            items[position] is BrandItem -> return 3
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: MainFilterHolder, position: Int) {
        holder.bind(items[position])
    }

    abstract class MainFilterDataItem<T>(val data : T)
    data class TitleItem(val title : String) : MainFilterDataItem<String>(title)
    data class SortItem(val title : String) : MainFilterDataItem<String>(title)
    data class AttributeItem(val attribute : FilterAttribute) : MainFilterDataItem<FilterAttribute>(attribute)
    class BrandItem : MainFilterDataItem<Int>(0)

    inner class MainFilterTitleViewHolder(parent : ViewGroup) : MainFilterHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_filter_title,parent,false)) {
        override fun bind(title : MainFilterDataItem<*>) {
            val titleItem = title as TitleItem
            itemView.textView.text = titleItem.title
        }
    }

    inner class MainFilterAttributeViewHolder(val binding : ItemMainFilterAttributeBinding) : MainFilterHolder(binding.root) {
        override fun bind(title : MainFilterDataItem<*>) {
            val attributeItem = title as AttributeItem
            binding.attribute =attributeItem.attribute
        }
    }

    inner class MainFilterBrandViewHolder(val binding : ItemMainFilterAttributeBinding) : MainFilterHolder(binding.root) {
        override fun bind(title : MainFilterDataItem<*>) {
            val brandItem = title as BrandItem
            binding.title.text = "Бренд"
        }
    }
    inner class MainFilterSortViewHolder(val binding : ItemMainFilterSortBinding) : MainFilterHolder(binding.root) {
        override fun bind(title : MainFilterDataItem<*>) {
            val sortItem = title as SortItem
            binding.sort = sortItem.title
            binding.dto = sharedFilterViewModel.activeFilter
        }
    }
}