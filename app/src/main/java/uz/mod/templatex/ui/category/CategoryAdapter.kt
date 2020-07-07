package uz.mod.templatex.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.CategoryItemBinding
import uz.mod.templatex.model.local.entity.Category
import uz.mod.templatex.ui.subCategory.SubCategoryFragmentDirections

class CategoryAdapter(
    private var items: List<Category> = arrayListOf()
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.apply {
                item = category
                executePendingBindings()
                root.setOnClickListener {
                    it.findNavController().navigate(
                        CategoryFragmentDirections.actionCategoryFragmentToSubCategoryFragment(
                            category
                        )
                    )
                }
            }
        }
    }
}