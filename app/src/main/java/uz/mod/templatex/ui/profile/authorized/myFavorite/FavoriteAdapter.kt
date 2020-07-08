package uz.mod.templatex.ui.profile.authorized.myFavorite

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ProfileItemFavoriteBinding
import uz.mod.templatex.model.local.entity.profile.ProfileFavorite

class FavoriteAdapter(private val listener: FavoriteAdapterListener) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    interface FavoriteAdapterListener {
        fun onRemoveClick(id: Int)
    }

    private var items: List<ProfileFavorite> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FavoriteViewHolder(
        ProfileItemFavoriteBinding.bind(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.profile_item_favorite, parent, false)
        ),
        listener
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) =
        holder.bind(items[position])

    fun setItems(items: List<ProfileFavorite>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    class FavoriteViewHolder(
        val binding: ProfileItemFavoriteBinding,
        val listener: FavoriteAdapterListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: ProfileFavorite) {

            binding.apply {
                item = favorite
                tvOldPrice.paintFlags = tvOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                favorite.currency[0].apply {
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
                        tvOldPrice.text = "$ $oldPrice"
                    }

                    btnClose.setOnClickListener {
                        listener.onRemoveClick(favorite.id)
                    }
                }
            }
        }
    }
}