package uz.uzmobile.templatex.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.uzmobile.templatex.databinding.CartItemBinding
import uz.uzmobile.templatex.model.local.entity.Product
import uz.uzmobile.templatex.model.remote.responce.CartProduct
import uz.uzmobile.templatex.model.remote.responce.CartProductWrapper

class CartAdapter(
    private var select: (item: Int) -> Unit,
    private var sub: (item: Int) -> Unit,
    private var add: (item: Int) -> Unit
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var isEditing  = false
    private var items: List<CartProductWrapper> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    fun getItem(position: Int): CartProductWrapper = items.get(position)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: List<CartProductWrapper>) {
        items = it
        notifyDataSetChanged()
    }

    fun setIsEditing(it: Boolean) {
        isEditing = it
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: CartProductWrapper) {
            binding.apply {
                item = product
                isEditing = this@CartAdapter.isEditing
                executePendingBindings()


                select.setOnCheckedChangeListener { compoundButton, b ->
                    if (compoundButton.isPressed) this@CartAdapter.select.invoke(product.id)
                }

                minus.setOnClickListener {
                    this@CartAdapter.sub.invoke(product.id)
                }

                plus.setOnClickListener {
                    this@CartAdapter.add.invoke(product.id)
                }
            }
        }
    }
}