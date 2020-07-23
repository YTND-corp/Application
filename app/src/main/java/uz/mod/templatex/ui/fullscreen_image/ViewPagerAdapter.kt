package uz.mod.templatex.ui.fullscreen_image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_fullscreen_image.view.*
import uz.mod.templatex.R
import uz.mod.templatex.utils.GlideApp

class ViewPagerAdapter(private var items: List<String> = listOf()) :
    RecyclerView.Adapter<ViewPagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_fullscreen_image, parent, false))
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(items: List<String>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: String) {
            GlideApp.with(itemView.ivImage)
                .load(item)
                .into(itemView.ivImage)
        }
    }
}