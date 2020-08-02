package uz.mod.templatex.ui.checkoutFinal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemCheckoutProductBinding

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemCheckoutProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = 3

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

    }

    class ProductViewHolder(binding: ItemCheckoutProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }
}