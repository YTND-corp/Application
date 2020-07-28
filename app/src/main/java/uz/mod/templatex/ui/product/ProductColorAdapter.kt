package uz.mod.templatex.ui.product

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ProductColorItemBinding
import uz.mod.templatex.model.remote.response.ProductColor

class ProductColorAdapter(private var listener: (item: ProductColor) -> Unit) :
    RecyclerView.Adapter<ProductColorAdapter.ViewHolder>() {
    private var items: List<ProductColor> = listOf()
    private var selected: ProductColor? = null
    private var shouldChooseColor = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductColorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(colors: List<ProductColor>?) {
        items = colors ?: listOf()
    }

    fun setSelectedColor(selectedColor: ProductColor?) = selectedColor?.apply {
        selected = this
        notifyItemRangeChanged(0, items.size)
    }

    inner class ViewHolder(val binding: ProductColorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(color: ProductColor) {
            binding.apply {
                executePendingBindings()

                rbColor.isChecked = shouldChooseColor && selected == color

                color.color?.let {
                    rbColor.buttonTintList = ColorStateList.valueOf(Color.parseColor(it))
                }

                rbColor.setOnClickListener {
                    listener.invoke(color)
                    shouldChooseColor = true
                }
            }
        }
    }
}