package uz.mod.templatex.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.FavoriteItemBinding
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.ui.product.ProductFragmentDirections
import uz.mod.templatex.ui.products.ProductsFragmentDirections

class FavoriteAdapter(private var items: List<Product> = listOf()) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: List<Product>?) {
        items = it?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                item = product
                executePendingBindings()
                root.setOnClickListener {
                    it.findNavController().navigate(ProductFragmentDirections.actionGlobalProductFragment(product.id))
                }
            }
        }
    }
}