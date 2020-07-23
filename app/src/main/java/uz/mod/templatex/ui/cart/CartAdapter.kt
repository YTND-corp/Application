package uz.mod.templatex.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CartItemBinding
import uz.mod.templatex.model.local.entity.AttributeCombination
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.ui.product.ProductFragmentDirections

class CartAdapter(val listener: ItemListener) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var items: List<Product> = arrayListOf()
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    fun setItems(it: List<Product>?) {
        items = it ?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                item = product
                executePendingBindings()

                rvAttributes.layoutManager = LinearLayoutManager(binding.root.context).apply {
                    initialPrefetchItemCount = product.combinations?.size ?: 0
                }
                rvAttributes.adapter =
                    AttributeAdapter().apply {
                        val attributes = mutableListOf<AttributeCombination>()
                        attributes.add(AttributeCombination("No", product.reference))
                        attributes.add(
                            AttributeCombination(
                                binding.root.context.getString(R.string.price),
                                product.priceFormatted()
                            )
                        )
                        product.combinations?.let { attributes.addAll(it) }
                        setItems(attributes)
                    }
                rvAttributes.setRecycledViewPool(viewPool)

                root.setOnClickListener {
                    it.findNavController().navigate(ProductFragmentDirections.actionGlobalProductFragment(product.id))
                }

                minus.setOnClickListener {
                    listener.minus(product)
                }

                plus.setOnClickListener {
                    listener.plus(product)
                }

                favorite.setOnClickListener {
                    listener.favoriteToggle(product)
                }
            }
        }
    }

    interface ItemListener {
        fun minus(product: Product)
        fun plus(product: Product)
        fun favoriteToggle(product: Product)
    }
}