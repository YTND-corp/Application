package uz.mod.templatex.ui.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_brand.view.*
import uz.mod.templatex.R
import uz.mod.templatex.model.local.entity.Brand

class BrandAdapter(val listener: (item: Brand) -> Unit) : RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {

    private var items: List<Brand> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        return BrandViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_brand, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<Brand>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    inner class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Brand) {
            itemView.btnBrand.text = item.name
            itemView.btnBrand.setOnClickListener {
                listener.invoke(item)
            }
        }
    }
}