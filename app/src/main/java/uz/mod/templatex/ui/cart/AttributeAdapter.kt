package uz.mod.templatex.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.koin.ext.isInt
import uz.mod.templatex.databinding.ItemCardAttributeBinding
import uz.mod.templatex.databinding.ItemCardSaleAttributeBinding
import uz.mod.templatex.model.local.entity.AttributeCombination

class AttributeAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<AttributeCombination> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return when(viewType) {
            CartAttributeViewType.PRODUCT_ON_SALE -> AttributeSaleViewHolder(
                ItemCardSaleAttributeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else ->AttributeViewHolder(
                ItemCardAttributeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is AttributeViewHolder ->  holder.bind(items[position])
            is AttributeSaleViewHolder -> holder.bind(items[position])
        }
    }


    override fun getItemViewType(position: Int): Int {
        if (items[position].key?.split(" ")?.first()?.isInt() == true)
            return CartAttributeViewType.PRODUCT_ON_SALE
        return CartAttributeViewType.PRODUCT_IS_NOT_ON_SALE
    }

    fun setItems(items: List<AttributeCombination>?) {
        this.items = items ?: listOf()
        notifyDataSetChanged()
    }

    class AttributeViewHolder(val binding: ItemCardAttributeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AttributeCombination): Unit = with(binding) {
            this.item = item
            executePendingBindings()
        }
    }

    class AttributeSaleViewHolder(val binding: ItemCardSaleAttributeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AttributeCombination): Unit = with(binding) {
            this.item = item
            executePendingBindings()
        }
    }

    private object CartAttributeViewType {
        const val PRODUCT_ON_SALE = 0
        const val PRODUCT_IS_NOT_ON_SALE = 1
    }
}