package uz.mod.templatex.ui.profile.authorized.myAddresses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemAddressBinding
import uz.mod.templatex.model.local.entity.profile.ProfileAddress

class ProfileMyAddressesAdapter(private val listener: MyAddressesAdapterListener) :
    RecyclerView.Adapter<ProfileMyAddressesAdapter.ViewHolder>() {

    private var items: List<ProfileAddress> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(items: List<ProfileAddress>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(address: ProfileAddress) {
            binding.apply {
                item = address
                executePendingBindings()

                btnEdit.setOnClickListener {
                    listener.toEditMyAddress(address.id)
                }
            }
        }
    }

    interface MyAddressesAdapterListener {
        fun toEditMyAddress(addressId: Int)
    }
}