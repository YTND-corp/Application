package uz.uzmobile.templatex.ui.catalog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.catalog_detail_item.view.*
import kotlinx.android.synthetic.main.catalog_item.view.*
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.model.local.entity.Catalog

class CatalogAdapter(private var items: ArrayList<Catalog> = arrayListOf(),val listener: CatalogListener) : RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.catalog_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun setItems(items: ArrayList<Catalog>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Catalog, listener: CatalogListener?) {
            itemView.text.text = item.name
            itemView.setOnClickListener {
                listener?.onItemClick(item)
            }
        }
    }

    interface CatalogListener {
        fun onItemClick(item: Catalog)
    }
}