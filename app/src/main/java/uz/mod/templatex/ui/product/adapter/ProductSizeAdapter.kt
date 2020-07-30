package uz.mod.templatex.ui.product.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ProductSizeItemBinding
import uz.mod.templatex.model.remote.response.ProductSize

class ProductSizeAdapter(private var listener: (item: ProductSize) -> Unit) :
    RecyclerView.Adapter<ProductSizeAdapter.ViewHolder>() {
    private var items: List<ProductSize> = listOf()
    private var selected: ProductSize? = null
    private var shouldSelectSize = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ProductSizeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: List<ProductSize>?) {
        items = it ?: listOf()
    }

    fun setSelectedSize(it: ProductSize?) {
        this.selected = it
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ProductSizeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(size: ProductSize) {
            binding.apply {
                item = size
                executePendingBindings()

                text.isSelected = shouldSelectSize && size.id == selected?.id

                root.setOnClickListener {
                    listener.invoke(size)
                    shouldSelectSize = true
                }
            }
        }
    }
}