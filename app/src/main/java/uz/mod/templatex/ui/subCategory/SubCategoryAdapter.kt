package uz.mod.templatex.ui.subCategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.SubCategoryItemBinding
import uz.mod.templatex.model.local.entity.SubCategory

class SubCategoryAdapter(private var items: List<SubCategory> = listOf()) :
    RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        SubCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])
    fun setItems(it: List<SubCategory>?) {
        items = it ?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: SubCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subCategory: SubCategory): Unit = with(binding) {
            item = subCategory
            executePendingBindings()
            root.setOnClickListener {
                println("Subcategory parentID ${subCategory.parentId} name ${subCategory.name}")
                it.findNavController()
                    .navigate(
                        SubCategoryFragmentDirections.actionGlobalProductsFragment(
                            subCategory.id,
                            subCategory.name
                        )
                    )
            }
        }
    }
}
