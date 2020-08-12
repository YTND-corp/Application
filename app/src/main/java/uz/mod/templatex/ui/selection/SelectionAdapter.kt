package uz.mod.templatex.ui.selection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import timber.log.Timber
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
            if (homeItem.isBanner()) {
                bannerHeader.text = homeItem.title

                if (homeItem.items?.isNotEmpty() == true) {
                    Glide.with(binding.root.context)
                        .load(homeItem.items.first().image)
                        .into(image)
                }
                image.setOnClickListener {

                    it.findNavController().navigate(
                        SelectionFragmentDirections.actionGlobalProductsFragment(
                            homeItem.id,
                            homeItem.title
                        )
                    )
                }

            } else {
                actualHeader.text = homeItem.title
                actuals.adapter = SelectionSubAdapter(homeItem.id, homeItem.items ?: arrayListOf())
            }
        }
    }
}