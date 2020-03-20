package uz.uzmobile.templatex.ui.selection

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.extension.toDp
import uz.uzmobile.templatex.model.local.entity.Actual


class ActualAdapter(private var items: ArrayList<Actual> = arrayListOf(),val listener: ItemClickListener) : RecyclerView.Adapter<ActualAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.actual_item, parent, false)

        val displayMetrics = DisplayMetrics()
        (parent.context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        val params = view.layoutParams
        val offset: Int = 16
        params.width = ((width - offset.toDp()) * 0.58).toInt()
        view.layoutParams = params

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    fun setItems(items: ArrayList<Actual>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Actual, listener: ItemClickListener?) {
//            itemView.text.text = item.name
//            itemView.setOnClickListener {
//                listener?.onItemClick(item)
//            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(item: Actual)
    }
}