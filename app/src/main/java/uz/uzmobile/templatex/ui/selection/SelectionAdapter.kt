package uz.uzmobile.templatex.ui.selection

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.selection_item.view.*
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.model.local.entity.Actual
import uz.uzmobile.templatex.model.local.entity.Selection


class SelectionAdapter(
    private var items: ArrayList<Selection> = arrayListOf(),
    val listener: SelectionListener
) : RecyclerView.Adapter<SelectionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.selection_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun setItems(items: ArrayList<Selection>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Selection, listener: SelectionListener?) {
            itemView.actuals.setHasFixedSize(true)
            itemView.actuals.adapter = ActualAdapter(
                arrayListOf(Actual(0), Actual(1), Actual(2)),
                object : ActualAdapter.ItemClickListener {
                    override fun onItemClick(item: Actual) {

                    }
                })
//            itemView.text.text = item.name
//            itemView.setOnClickListener {
//                listener?.onItemClick(item)
//            }
        }
    }

    interface SelectionListener {
        fun onItemClick(item: Selection)
    }
}