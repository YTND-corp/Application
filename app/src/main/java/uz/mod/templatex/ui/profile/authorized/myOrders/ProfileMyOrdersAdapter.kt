package uz.mod.templatex.ui.profile.authorized.myOrders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.R
import uz.mod.templatex.databinding.ItemMyOrderBinding
import uz.mod.templatex.model.local.entity.profile.ProfileOrder
import uz.mod.templatex.model.local.entity.profile.StateType

class ProfileMyOrdersAdapter(
    private val listener: MyOrdersAdapterListener
) : RecyclerView.Adapter<ProfileMyOrdersAdapter.ViewHolder>() {

    private var items: List<ProfileOrder> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemMyOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(items: List<ProfileOrder>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        val binding: ItemMyOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: ProfileOrder): Unit = with(binding) {
            item = order
            executePendingBindings()

            when (order.state?.type) {
                StateType.INFO -> ivState.setBackgroundResource(R.drawable.my_orders_indicator_blue)
                StateType.WARNING -> ivState.setBackgroundResource(R.drawable.my_orders_indicator_yellow)
                StateType.SUCCESS -> ivState.setBackgroundResource(R.drawable.my_orders_indicator_green)
                StateType.DANGER -> ivState.setBackgroundResource(R.drawable.my_orders_indicator_red)
            }

            root.setOnClickListener {
                listener.toMyOrder(order.id)
            }
        }
    }
}

interface MyOrdersAdapterListener {
    fun toMyOrder(orderId: Int)
}