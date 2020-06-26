package uz.mod.templatex.ui.profile.authorized.myOrders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemMyOrderBinding
import uz.mod.templatex.model.local.entity.Product

class ProfileMyOrdersAdapter() :
    RecyclerView.Adapter<ProfileMyOrdersAdapter.ViewHolder>() {

    private var items: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemMyOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(items: List<Product>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        val binding: ItemMyOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                executePendingBindings()
            }
        }
    }
}