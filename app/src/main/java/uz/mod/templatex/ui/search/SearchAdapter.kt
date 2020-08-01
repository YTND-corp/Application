package uz.mod.templatex.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemSearchBinding
import uz.mod.templatex.model.local.entity.Product

class SearchAdapter(val listener: (item: Product) -> Unit) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    private var items: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<Product>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    inner class SearchViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                this.item = product
                executePendingBindings()
                root.setOnClickListener {
                    listener.invoke(product)
                }
            }
        }
    }
}