package uz.mod.templatex.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.FavoriteItemBinding
import uz.mod.templatex.model.local.entity.Favorite
import uz.mod.templatex.ui.product.ProductFragmentDirections

class FavoriteAdapter(private var listener: (id: Int, isFavorite: Boolean) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var items: List<Favorite> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: List<Favorite>?) {
        items = it?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: FavoriteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            binding.apply {
                item = favorite
                executePendingBindings()

                chbFavorite.setOnCheckedChangeListener { compoundButton, _ ->
                    if (compoundButton.isPressed) {
                        listener.invoke(favorite.id, !favorite.isFavorite)
                    }
                }
                
                root.setOnClickListener {
                    it.findNavController().navigate(ProductFragmentDirections.actionGlobalProductFragment(favorite.id))
                }
            }
        }
    }
}