package uz.mod.templatex.ui.paymentProvider

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.mod.templatex.databinding.ItemPaymentCardsBinding
import uz.mod.templatex.model.remote.response.PaymentProvider

class PaymentProviderAdapter(
    private var items: List<PaymentProvider>?,
    private var listener: ((item: PaymentProvider?) -> Unit)
) : RecyclerView.Adapter<PaymentProviderAdapter.ViewHolder>() {

    private var selected: PaymentProvider? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPaymentCardsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    fun setSelected(it: PaymentProvider?) {
        selected = it
        notifyDataSetChanged()
    }

    override fun getItemCount() = items?.size ?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items?.get(position))

    inner class ViewHolder(val binding: ItemPaymentCardsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(paymentProvider: PaymentProvider?): Unit = with(binding) {
            card = paymentProvider
            isSelected = paymentProvider?.name == selected?.name
            executePendingBindings()

            overlay.setOnClickListener {
                setSelected(paymentProvider)
                listener.invoke(paymentProvider)
            }
        }
    }

}