package uz.mod.templatex.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.product_item.view.*
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProductItemBinding
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.ui.product.ProductFragmentDirections
import uz.mod.templatex.utils.GlideApp

class ProductAdapter(private var listener: (item: Product, position: Int) -> Unit) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var items: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position)

    fun setItems(it: List<Product>?) {
        items = it?.toMutableList() ?: mutableListOf()
        notifyDataSetChanged()
    }

    fun addItems(items: List<Product>?) {
        items?.let { this.items.addAll(it) }
        notifyDataSetChanged()
    }

    fun updateItem(item: Product, position: Int) {
        item.isFavorite = !item.isFavorite
        notifyItemChanged(position)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        GlideApp.with(holder.itemView.context)
            .clear(holder.itemView.image)
    }

    inner class ViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product, position: Int): Unit = with(binding) {
            item = product
            executePendingBindings()

            if (product.hasDiscount()) price.setTextColor(ContextCompat.getColor(price.context, R.color.red))
            else price.setTextColor(ContextCompat.getColor(price.context, R.color.blackTextColor))

            favorite.setOnCheckedChangeListener { compoundButton, _ ->
                if (compoundButton.isPressed) listener.invoke(product, position)
            }

            root.setOnClickListener {
                it.findNavController().navigate(
                    ProductFragmentDirections.actionGlobalProductFragment(product.id)
                )
            }
        }
    }
}