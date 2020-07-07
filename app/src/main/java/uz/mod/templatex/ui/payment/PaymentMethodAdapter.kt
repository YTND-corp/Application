package uz.mod.templatex.ui.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.PaymentMethodItemBinding
import uz.mod.templatex.model.remote.response.PaymentMethod

class PaymentMethodAdapter(private var listener: ((item: PaymentMethod)-> Unit)? = null) :
    RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder>() {
    private var items: List<PaymentMethod> = arrayListOf()
    private var selected: PaymentMethod? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            PaymentMethodItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun setItems(it: List<PaymentMethod>?) {
        items = it?: arrayListOf()
    }

    fun setSelected(it: PaymentMethod?) {
        selected = it
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: PaymentMethodItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(carrier: PaymentMethod) {
            binding.apply {
                item = carrier.name
                isSelected = carrier.name == selected?.name
                executePendingBindings()

                binding.root.setOnClickListener {
                    listener?.invoke(carrier)
                }
            }
        }
    }
}