package uz.mod.templatex.ui.supportCenter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemSupportCenterBinding
import uz.mod.templatex.model.remote.response.profile.Page

class SupportCenterAdapter(val listener: (item: Page) -> Unit) : RecyclerView.Adapter<SupportCenterAdapter.ItemViewHolder>() {

    private var items = listOf<Page>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemSupportCenterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<Page>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(val binding: ItemSupportCenterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(page: Page) {
            binding.apply {
                item = page
                executePendingBindings()

                root.setOnClickListener {
                    listener.invoke(page)
                }
            }
        }
    }
}