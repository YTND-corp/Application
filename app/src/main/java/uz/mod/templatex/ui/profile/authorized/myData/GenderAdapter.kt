package uz.mod.templatex.ui.profile.authorized.myData

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import uz.mod.templatex.R
import uz.mod.templatex.model.remote.response.profile.Gender

class GenderAdapter(val context: Context?) : BaseAdapter() {

    private var items: List<Gender> = listOf()
    private var mInflater: LayoutInflater = LayoutInflater.from(context)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: GenderViewHolder

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_gender, parent, false)
            holder = GenderViewHolder(view)
            view?.tag = holder
        } else {
            view = convertView
            holder = view.tag as GenderViewHolder
        }

        holder.bind(items[position])

        return view
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int) = 0L

    override fun getCount() = items.size

    fun setItems(items: List<Gender>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    fun getGender(position: Int) = items[position]

    inner class GenderViewHolder(itemView: View?) {

        private val gender: TextView? = itemView?.findViewById(R.id.tvGender)

        fun bind(item: Gender) {
            gender?.text = item.name
        }
    }
}