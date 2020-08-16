package uz.mod.templatex.ui.products

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ItemFastFilterBinding
import uz.mod.templatex.model.local.entity.Brand

class FastFilterAdapter(val listener: (item: Brand) -> Unit) : RecyclerView.Adapter<FastFilterAdapter.FilterViewHolder>() {

    private var items = listOf<Brand>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(ItemFastFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<Brand>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    inner class FilterViewHolder(val binding: ItemFastFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(brand: Brand) {
            binding.apply {
                item = brand
                executePendingBindings()

                if (brand.selected) {
                    val foregroundColor = ContextCompat.getColor(root.context, R.color.white)
                    mbFilter.setTextColor(foregroundColor)
                    mbFilter.setBackgroundColor(ContextCompat.getColor(root.context, R.color.black))
                    mbFilter.icon = root.context.getDrawable(R.drawable.ic_baseline_close_24)
                    mbFilter.iconTint = ColorStateList.valueOf(foregroundColor)
                } else {
                    val foregroundColor = ContextCompat.getColor(root.context, R.color.black)
                    mbFilter.setTextColor(foregroundColor)
                    mbFilter.setBackgroundColor(ContextCompat.getColor(root.context, R.color.gray))
                    mbFilter.icon = root.context.getDrawable(R.drawable.ic_baseline_add_24)
                    mbFilter.iconTint = ColorStateList.valueOf(foregroundColor)
                }

                mbFilter.setOnClickListener {
                    brand.selected = !brand.selected
                    listener.invoke(brand)
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }
}