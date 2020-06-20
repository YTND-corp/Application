package uz.mod.templatex.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ProoductSizeItemBinding
import uz.mod.templatex.model.remote.responce.ProductSize

class ProductSizeAdapter() :
    RecyclerView.Adapter<ProductSizeAdapter.ViewHolder>() {
    private var items: List<ProductSize> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ViewHolder(ProoductSizeItemBinding.inflate( LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: List<ProductSize>?) {
        items = it?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ProoductSizeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(size: ProductSize) {
            binding.apply {
                item = size
                executePendingBindings()
            }
        }
    }
}