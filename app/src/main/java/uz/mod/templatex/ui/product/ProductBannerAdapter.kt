package uz.mod.templatex.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProductBannerItemBinding

class ProductBannerAdapter : RecyclerView.Adapter<ProductBannerAdapter.ViewHolder>() {

    private var items: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ProductBannerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position)

    fun setItems(it: List<String>?) {
        items = it ?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ProductBannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String, position: Int) {
            binding.apply {
                item = url
                executePendingBindings()

                root.setOnClickListener {
                    it.findNavController().navigate(
                        R.id.fullScreenImageFragment,
                        bundleOf(
                            "images" to items.toTypedArray(),
                            "selectedPosition" to position
                        )
                    )
                }
            }
        }
    }
}