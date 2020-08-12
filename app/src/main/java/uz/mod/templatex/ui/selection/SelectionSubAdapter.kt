package uz.mod.templatex.ui.selection

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.SelectionSubHorizontalItemBinding
import uz.mod.templatex.databinding.SelectionSubVerticalItemBinding
import uz.mod.templatex.model.local.entity.HomeItem
import uz.mod.templatex.model.local.entity.HomeSubItem
import uz.mod.templatex.utils.extension.toDp


const val Component_002 = 0
const val Component_003 = 1

class SelectionSubAdapter(
    private var items: List<HomeSubItem> = arrayListOf(),
    private val homeItem: HomeItem
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Component_002 -> {

                val binding = SelectionSubVerticalItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                val displayMetrics = DisplayMetrics()
                (parent.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)

                val width = displayMetrics.widthPixels

                val params = binding.root.layoutParams
                val offset = 16
                params.width = ((width - offset.toDp()) * 0.58).toInt()
                binding.root.layoutParams = params
                VerticalViewHolder(binding)
            }
            else -> HorizontalViewHolder(
                SelectionSubHorizontalItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (homeItem.isVerticalComponent()) Component_002
        else Component_003
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HorizontalViewHolder -> holder.bind(items[position])
            is VerticalViewHolder -> holder.bind(items[position])
        }
    }

    //Text and image on top of each other
    inner class VerticalViewHolder(val binding: SelectionSubVerticalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(homeSubItem: HomeSubItem): Unit = with(binding) {
            item = homeSubItem
            executePendingBindings()
            root.setOnClickListener {
                it.findNavController().navigate(
                    SelectionFragmentDirections.actionGlobalProductsFragment(
                        homeSubItem.id,
                        homeSubItem.name
                    )
                )
            }
        }
    }

    //Text and image next to each other
    inner class HorizontalViewHolder(val binding: SelectionSubHorizontalItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(homeSubItem: HomeSubItem): Unit = with(binding) {
            item = homeSubItem
            executePendingBindings()
            root.setOnClickListener {
                it.findNavController().navigate(
                    SelectionFragmentDirections.actionGlobalProductsFragment(
                        homeSubItem.id,
                        homeSubItem.name
                    )
                )
            }
        }
    }
}


