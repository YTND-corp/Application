package uz.mod.templatex.ui.new_filter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import uz.mod.templatex.R
import uz.mod.templatex.model.local.entity.Filter
import uz.mod.templatex.model.repository.ProductRepository

class SharedFilterViewModel (application: Application) : AndroidViewModel(application){
    init {
        Companion.application = application
    }
    var activeFilter  : SelectedFitlerDto = SelectedFitlerDto()

    var currentFilter  : Filter? = null
    var cachedFilter : Filter? = null
    var needToReloadFeed = false

    companion object{
        private  var application : Application? = null
    }

    fun fillActiveFilter(){
        val selectedBrands = currentFilter?.brands?.filter { it.selected }?.map { it.id }?: emptyList()
        val selectedAttrs = mutableMapOf<String,List<Int>>()
        currentFilter?.attributes?.filter { it.values?.find { it.selected }!=null }?.forEach {
            selectedAttrs.put(it.slug,it.values?.filter { it.selected }?.map { it.id }?: emptyList())
        }

        activeFilter.brands = selectedBrands
        activeFilter.attributes = selectedAttrs
    }

    fun buildTemporaryData(){
        val gson = Gson()
        cachedFilter = gson.fromJson(gson.toJson(currentFilter),Filter::class.java)
    }
    fun saveTemporaryData(){
        val gson = Gson()
        currentFilter = gson.fromJson(gson.toJson(cachedFilter),Filter::class.java)
        needToReloadFeed = true
    }

    data class SelectedFitlerDto(
        var sort : Sort= Sort.PopularSort(),
        var brands : List<Int> = emptyList(),
        var attributes : Map<String,List<Int>> = emptyMap()
    ) {
        companion object{
            const val SORT_POPULAR = "popular"
            const val SORT_NEW = "new"
            const val SORT_PRICE_ASC = "price_asc"
            const val SORT_PRICE_DESC = "price_desc"
            const val SORT_DISCOUNTs = "discounts"
            const val SORT_DISCOUNTs_NEW = "discounts_new"

            sealed class Sort(val key:String,val stringResId : Int){
                val name = application?.resources?.getString(stringResId)
                override fun hashCode(): Int = key.hashCode()
                override fun equals(other: Any?): Boolean {
                    if (this === other) return true
                    if (javaClass != other?.javaClass) return false
                    if (key != (other as Sort).key) return false
                    return true
                }

                class PopularSort : Sort(SORT_POPULAR, R.string.sort_popular){
//                    override fun hashCode(): Int = 1
                }
                class NewSort : Sort(SORT_NEW, R.string.sort_new){
//                    override fun hashCode(): Int = 2
                }
                class PriceAscSort : Sort(SORT_PRICE_ASC, R.string.sort_price_asc){
//                    override fun hashCode(): Int = 3
                }
                class PriceDescSort : Sort(SORT_PRICE_DESC, R.string.sort_price_desc){
//                    override fun hashCode(): Int = 4
                }
            }

        }
    }
}

