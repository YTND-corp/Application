package uz.mod.templatex.ui.profile.authorized.myAddresses.createEdit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import uz.mod.templatex.R
import uz.mod.templatex.model.local.entity.profile.ProfileRegion

class RegionAdapter(val context: Context?) : BaseAdapter() {

    private var items: List<ProfileRegion> = listOf()
    private var mInflater: LayoutInflater = LayoutInflater.from(context)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: RegionViewHolder

        if (convertView == null) {
            view = mInflater.inflate(R.layout.item_region, parent, false)
            holder = RegionViewHolder(view)
            view?.tag = holder
        } else {
            view = convertView
            holder = view.tag as RegionViewHolder
        }

        holder.bind(items[position])

        return view
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int) = 0L

    override fun getCount() = items.size

    fun setItems(items: List<ProfileRegion>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    fun getRegion(position: Int) = items[position]

    inner class RegionViewHolder(itemView: View?) {

        val mTextView: TextView? = itemView?.findViewById(R.id.tvRegion)

        fun bind(item: ProfileRegion) {
            mTextView?.text = item.name
        }
    }
}