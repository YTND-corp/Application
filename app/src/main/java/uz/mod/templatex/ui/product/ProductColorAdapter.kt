package uz.mod.templatex.ui.product

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ProductColorItemBinding
import uz.mod.templatex.model.remote.response.ProductColor

class ProductColorAdapter(private var listener: (item: ProductColor)-> Unit) :
    RecyclerView.Adapter<ProductColorAdapter.ViewHolder>() {
    private var items: List<ProductColor> = listOf()
    private var selected: ProductColor? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProductColorItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val displayMetrics = DisplayMetrics()
        (parent.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val params = binding.root.layoutParams
        params.width = (width * 0.2).toInt()
        binding.root.layoutParams = params
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: List<ProductColor>?) {
        items = it?: listOf()
    }

    fun setSelectedColor(selectedColor: ProductColor?) {
        this.selected = selectedColor
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ProductColorItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(color: ProductColor) {
            binding.apply {
                url = if(color.images?.isNullOrEmpty() == false) color.images.first() else ""
                executePendingBindings()

                image.isSelected =  color.id == selected?.id

                root.setOnClickListener {
                    listener.invoke(color)
                }
            }
        }
    }
}