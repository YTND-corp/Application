package uz.uzmobile.templatex.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import uz.uzmobile.templatex.databinding.ProductItemBinding
import uz.uzmobile.templatex.model.local.entity.Product

class ProductAdapter(private var listener: (id: Int, isFavorite: Boolean)-> Unit) :

    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private var items: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: List<Product>?) {
        items = it ?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                item = product
                executePendingBindings()
                Timber.e("ProductId = ${product.image}")
                favorite.setOnCheckedChangeListener { compoundButton, b ->
                    if (compoundButton.isPressed) {
                        listener.invoke(product.id, !product.isFavorite)
                    }
                }

                root.setOnClickListener {
                    it.findNavController().navigate(
                        ProductsFragmentDirections.actionGlobalProductFragment(
                            product.categoryId,
                            product.id
                        )
                    )
                }
            }
        }
    }
}