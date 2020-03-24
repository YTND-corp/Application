package uz.uzmobile.templatex.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.CartItemBinding
import uz.uzmobile.templatex.model.local.entity.Product

class CartAdapter(private var items: ArrayList<Product>) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: ArrayList<Product>) {
        items = it
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, items.size)
    }

    inner class ViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.item = item
            binding.executePendingBindings()
        }
    }

    interface ItemClickListener {
        fun onClick(item: Product)
    }
}