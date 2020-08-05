package uz.mod.templatex.ui.checkoutFinal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemCheckoutProductBinding
import uz.mod.templatex.model.local.entity.Product

class ProductAdapter (private val checkoutFinalViewModel : CheckoutFinalViewModel) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var items: List<Product> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemCheckoutProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun setItems(it: List<Product>?) {
        items = it ?: listOf()
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) = holder.bind(items[position])


    inner class ProductViewHolder(val binding: ItemCheckoutProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) : Unit = with(binding){
            item = product
            viewModel = checkoutFinalViewModel
            executePendingBindings()
        }
    }
}