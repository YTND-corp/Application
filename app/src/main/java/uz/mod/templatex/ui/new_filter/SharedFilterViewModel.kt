package uz.mod.templatex.ui.new_filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.mod.templatex.R
import uz.mod.templatex.model.local.entity.Filter
import uz.mod.templatex.model.repository.ProductRepository

class SharedFilterViewModel (application: Application) : AndroidViewModel(application){
    init {
        Companion.application = application
    }
    fun onCategorySelected(catId : Int){
        activeFilter = SelectedFitlerDto()
    }

    var activeFilter  : SelectedFitlerDto = SelectedFitlerDto()

    companion object{
        private lateinit var application : Application
    }

    data class SelectedFitlerDto(
        var sort : Sort= Sort.PopularSort(),
        val brands : List<Int> = emptyList(),
        val attributes : Map<Int,List<Int>> = emptyMap()
    ) {
        companion object{
            const val SORT_POPULAR = "popular"
            const val SORT_NEW = "new"
            const val SORT_PRICE_ASC = "price_asc"
            const val SORT_PRICE_DESC = "price_desc"
            const val SORT_DISCOUNTs = "discounts"
            const val SORT_DISCOUNTs_NEW = "discounts_new"

            sealed class Sort(val key:String,val stringResId : Int){
                val name = application.resources.getString(stringResId)

                class PopularSort : Sort(SORT_POPULAR, R.string.sort_popular)
                class NewSort : Sort(SORT_NEW, R.string.sort_new)
                class PriceAscSort : Sort(SORT_PRICE_ASC, R.string.sort_price_asc)
                class PriceDescSort : Sort(SORT_PRICE_DESC, R.string.sort_price_desc)
            }

        }
    }
}

