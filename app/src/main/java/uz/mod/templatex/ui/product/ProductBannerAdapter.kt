package uz.mod.templatex.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ProductBannerItemBinding

class ProductBannerAdapter() :
    RecyclerView.Adapter<ProductBannerAdapter.ViewHolder>() {

    private var items: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ProductBannerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: List<String>?) {
        items = it ?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ProductBannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.apply {
                item = url
                executePendingBindings()
            }
        }
    }
}