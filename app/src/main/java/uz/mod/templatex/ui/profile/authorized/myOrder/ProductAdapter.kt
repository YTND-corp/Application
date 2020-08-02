package uz.mod.templatex.ui.profile.authorized.myOrder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemOrderProductBinding
import uz.mod.templatex.model.remote.response.profile.Product
import uz.mod.templatex.utils.GlideApp

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var items: List<Product> = listOf()
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ProductViewHolder(
        ItemOrderProductBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        viewPool
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(items[position])

    fun setItems(items: List<Product>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    class ProductViewHolder(
        val binding: ItemOrderProductBinding,
        private val viewPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product): Unit = with(binding) {
            item = product
            executePendingBindings()

            rvAttributes.layoutManager = LinearLayoutManager(binding.root.context).apply {
                initialPrefetchItemCount = product.attributeCombination.size
            }
            rvAttributes.adapter =
                AttributeAdapter().apply { setItems(product.attributeCombination) }
            rvAttributes.setRecycledViewPool(viewPool)

            GlideApp.with(myOrderImageImg)
                .load(product.image)
                .into(myOrderImageImg)
            
        }
    }
}