package uz.mod.templatex.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.R
import uz.mod.templatex.databinding.CategoryItemBinding
import uz.mod.templatex.model.local.entity.Category

class CategoryAdapter(
    private var items: List<Category> = arrayListOf(), val listener: (item: Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])


    fun setItems(items: List<Category>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category): Unit = with(binding) {
            item = category
            executePendingBindings()

            //TODO This is a temporary solution in the future, a flag will be added in the backend.
            val textColor = if (category.id == 393)
                ContextCompat.getColor(binding.root.context, R.color.red)
            else
                ContextCompat.getColor(binding.root.context, R.color.black)

            tvCategory.setTextColor(textColor)

            root.setOnClickListener {
                listener.invoke(category)
            }
        }
    }
}