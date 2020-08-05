package uz.mod.templatex.ui.checkoutFinal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemCheckoutPriceBinding
import uz.mod.templatex.model.local.entity.Product

class PriceAdapter (private val checkoutFinalViewModel : CheckoutFinalViewModel) : RecyclerView.Adapter<PriceAdapter.PriceViewHolder>() {

    private var items: List<Product> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        return PriceViewHolder(ItemCheckoutPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    fun setItems(it: List<Product>?) {
        items = it ?: listOf()
        notifyDataSetChanged()
    }


    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int)  = holder.bind(items[position])

    inner class PriceViewHolder(val binding: ItemCheckoutPriceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product):Unit = with(binding) {
            item = product
            viewModel = checkoutFinalViewModel
            executePendingBindings()
        }
    }
}