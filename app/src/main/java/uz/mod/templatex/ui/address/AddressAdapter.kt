package uz.mod.templatex.ui.address

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.DeliveryItemBinding
import uz.mod.templatex.model.remote.response.Delivery

class AddressAdapter(private var listener: ((item: Delivery) -> Unit)) :
    RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    private var items: List<Delivery> = arrayListOf()
    private var selected: Delivery? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            DeliveryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    //override fun getItemCount(): Int = items.size
    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: ViewHolder, position: Int){}// = holder.bind(items[position])

    fun setItems(it: List<Delivery>?) {
        items = it ?: arrayListOf()
    }

    fun setSelected(it: Delivery?) {
        selected = it
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: DeliveryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(delivery: Delivery) {
            binding.apply {
                item = delivery
                isSelected = delivery.id == selected?.id
                executePendingBindings()

                overlay.setOnClickListener {
                    listener.invoke(delivery)
                }
            }
        }
    }
}