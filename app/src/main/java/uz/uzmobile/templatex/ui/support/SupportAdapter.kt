package uz.uzmobile.templatex.ui.support

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.model.local.entity.Support
import uz.uzmobile.templatex.ui.custom.LineViewHolder

class SupportAdapter(private val items: List<Support>) :
    RecyclerView.Adapter<SupportAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.support_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Support) {

        }
    }
}