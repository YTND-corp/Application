package uz.mod.templatex.ui.product.adapter

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ProductItemBinding
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.utils.extension.toPx

class ProductHorizontalAdapter(private var mListener: ClickListener) :
    RecyclerView.Adapter<ProductHorizontalAdapter.ViewHolder>() {

    interface ClickListener {
        fun onItemClick(item: Product)
        fun onFavoriteClick(item: Product, position: Int)
    }

    private var items: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ProductItemBinding.inflate(inflater, parent, false)


        val displayMetrics = DisplayMetrics()
        (parent.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val offset: Int = 16

        val params = view.root.layoutParams
        params.width = ((width - offset.toPx()) * 0.5).toInt()
        view.root.layoutParams = params

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position)

    fun setItems(it: List<Product>?) {
        items = it ?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, position: Int) {
            binding.apply {
                item = product
                executePendingBindings()

                root.setOnClickListener {
                    mListener.onItemClick(product)
                }

                favorite.setOnCheckedChangeListener { compoundButton, b ->
                    if (compoundButton.isPressed) {
                        mListener.onFavoriteClick(product, position)
                    }
                }
            }
        }
    }
}