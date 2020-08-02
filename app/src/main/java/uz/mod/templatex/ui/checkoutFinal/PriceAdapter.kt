package uz.mod.templatex.ui.checkoutFinal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemCheckoutPriceBinding

class PriceAdapter : RecyclerView.Adapter<PriceAdapter.PriceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceViewHolder {
        return PriceViewHolder(ItemCheckoutPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = 3

    override fun onBindViewHolder(holder: PriceViewHolder, position: Int) {

    }

    class PriceViewHolder(val binding: ItemCheckoutPriceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }
}