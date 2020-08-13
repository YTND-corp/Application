package uz.mod.templatex.ui.selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.mod.templatex.databinding.SelectionItemBinding
import uz.mod.templatex.model.local.entity.HomeItem


class SelectionAdapter(
    private var items: List<HomeItem> = arrayListOf()
) : RecyclerView.Adapter<SelectionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(SelectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])


    fun setItems(items: List<HomeItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: SelectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(homeItem: HomeItem): Unit = with(binding) {
            item = homeItem
            executePendingBindings()
            when {
                homeItem.isBanner() -> {
                    val subItem = homeItem.items?.firstOrNull()
                    bannerHeader.text = homeItem.title
                    if (subItem != null) {
                        Glide.with(binding.root.context)
                            .load(subItem.image)
                            .into(image)
                    }
                    image.setOnClickListener {
                        it.findNavController().navigate(
                            SelectionFragmentDirections.actionGlobalProductsFragment(
                                subItem?.id ?: homeItem.id,
                                homeItem.title
                            )
                        )
                    }

                }
                homeItem.isVerticalComponent() -> {
                    actuals.layoutManager = LinearLayoutManager(actuals.context, LinearLayoutManager.HORIZONTAL, false)
                    actualHeader.text = homeItem.title
                    actuals.adapter = SelectionSubAdapter(homeItem.items ?: arrayListOf(), homeItem)
                }
                else -> {
                    actualHeader.text = homeItem.title
                    actuals.adapter = SelectionSubAdapter(homeItem.items ?: arrayListOf(), homeItem)
                }
            }
        }
    }
}