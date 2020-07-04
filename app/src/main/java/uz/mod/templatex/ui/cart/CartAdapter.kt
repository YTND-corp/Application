package uz.mod.templatex.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.CartItemBinding
import uz.mod.templatex.model.local.entity.Product

class CartAdapter(val listener: ItemListener) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var isEditing = false
    private var items: List<Product> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int): Product = items.get(position)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: List<Product>?) {
        items = it ?: listOf()
        notifyDataSetChanged()
    }

    fun setIsEditing(it: Boolean) {
        isEditing = it
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                item = product
                isEditing = this@CartAdapter.isEditing
                executePendingBindings()

                root.setOnClickListener {
                    it.findNavController().navigate(
                        CartFragmentDirections.actionGlobalProductFragment(
                            product.id
                        )
                    )
                }

                select.setOnCheckedChangeListener { compoundButton, b ->
                    if (compoundButton.isPressed) {
                        listener.select(product)
                    }
                }

                minus.setOnClickListener {
                    listener.minus(product)
                }

                plus.setOnClickListener {
                    listener.plus(product)
                }
            }
        }
    }

    interface ItemListener {
        fun select(product: Product)
        fun minus(product: Product)
        fun plus(product: Product)
    }
}