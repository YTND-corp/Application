package uz.uzmobile.templatex.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.profile_item.view.*
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.ProfileItemBinding
import uz.uzmobile.templatex.model.local.entity.ProfileItem

class ProfileAdapter(private val items: List<ProfileItem>) :
    RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.profile_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ProfileItem) {
            itemView.profile_item_text.text = item.text
            itemView.profile_item_image.setImageDrawable(item.image)
            if (item.id == items.last().id) itemView.profile_item_divider.visibility = View.GONE
        }
    }
}