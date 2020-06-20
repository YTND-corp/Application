package uz.mod.templatex.ui.selection

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.SelectionSubItemBinding
import uz.mod.templatex.extension.toDp
import uz.mod.templatex.model.local.entity.HomeSubItem


class SelectionSubAdapter(
    private var categoryId: Int,
    private var items: List<HomeSubItem> = arrayListOf()
) : RecyclerView.Adapter<SelectionSubAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SelectionSubItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val displayMetrics = DisplayMetrics()
        (parent.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val params = binding.root.layoutParams
        val offset: Int = 16
        params.width = ((width - offset.toDp()) * 0.58).toInt()
        binding.root.layoutParams = params

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ViewHolder(val binding: SelectionSubItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(homeSubItem: HomeSubItem) {
            binding.apply {
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
}