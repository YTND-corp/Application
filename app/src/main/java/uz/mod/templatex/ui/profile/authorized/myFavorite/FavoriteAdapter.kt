package uz.mod.templatex.ui.profile.authorized.myFavorite

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ProfileItemFavoriteBinding
import uz.mod.templatex.model.local.entity.Favorite

class FavoriteAdapter(private val listener: FavoriteAdapterListener) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    interface FavoriteAdapterListener {
        fun onItemClick(id: Int)
        fun onRemoveClick(id: Int)
    }

    private var items: List<Favorite> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteViewHolder(
        ProfileItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        listener
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(items[position])

    fun setItems(items: List<Favorite>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(
        val binding: ProfileItemFavoriteBinding,
        val listener: FavoriteAdapterListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite): Unit = with(binding) {

            item = favorite
            tvOldPrice.paintFlags = tvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            favorite.currencies[0].apply {
                if (discount == 0) {
                    tvDiscount.visibility = View.GONE
                } else {
                    tvDiscount.visibility = View.VISIBLE
                    tvDiscount.text = "$discount%"
                }

                if (oldPrice == 0) {
                    tvOldPrice.visibility = View.GONE
                } else {
                    tvOldPrice.visibility = View.VISIBLE
                }

                root.setOnClickListener {
                    listener.onItemClick(favorite.id)
                }

                btnClose.setOnClickListener {
                    listener.onRemoveClick(favorite.id)
                }
            }
        }
    }
}