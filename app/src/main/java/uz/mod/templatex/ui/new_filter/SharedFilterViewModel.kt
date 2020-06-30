package uz.mod.templatex.ui.new_filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uz.mod.templatex.model.local.entity.Filter
import uz.mod.templatex.model.repository.ProductRepository

class SharedFilterViewModel (application: Application) : AndroidViewModel(application){
    fun onCategorySelected(catId : Int){
        //TODO: Reset filter
    }

    var activeFilter  : SelectedFitlerDto = SelectedFitlerDto()

    data class SelectedFitlerDto(
        val sort : String= SORT_POPULAR,
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
        }
    }
}