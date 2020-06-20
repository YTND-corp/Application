package uz.mod.templatex.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProductBannerIndicatorBinding
import uz.mod.templatex.databinding.ProductBannerItemBinding
import uz.mod.templatex.extension.color

class ProductBannerIndicatorAdapter(private var items: List<String> = arrayListOf()) :
    RecyclerView.Adapter<ProductBannerIndicatorAdapter.ViewHolder>() {
    private var selected = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ProductBannerIndicatorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: List<String>?) {
        items = it?: arrayListOf()
        notifyDataSetChanged()
    }

    fun setSelected(snapPosition: Int) {
        selected = snapPosition
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ProductBannerIndicatorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.apply {
                indicator.setCardBackgroundColor(this.root.context.color(
                    if (adapterPosition == selected) R.color.indicator_current
                    else R.color.indicator_default))

            }
        }
    }
}