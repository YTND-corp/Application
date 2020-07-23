package uz.mod.templatex.ui.products

import android.app.Application
import androidx.lifecycle.*
import kotlinx.android.synthetic.main.products_fragment.view.*
import uz.mod.templatex.R
import uz.mod.templatex.model.local.entity.Filter
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.remote.network.Resource
import uz.mod.templatex.model.repository.ProductRepository
import uz.mod.templatex.ui.new_filter.SharedFilterViewModel
import uz.mod.templatex.utils.AbsentLiveData

class ProductsViewModel constructor(application: Application, val repository: ProductRepository) :
    AndroidViewModel(application) {

    var categoryId: Int = 0
    var page: Int = 1
    var totalCount = ""

    var filterParams : SharedFilterViewModel.SelectedFitlerDto = SharedFilterViewModel.SelectedFitlerDto()
    var title = MutableLiveData<String>()

    private val request = MutableLiveData<Boolean>()
    val response = Transformations.switchMap(request) {
        repository.getProducts(categoryId, filterParams, page)
    }

    val filter = Transformations.map(repository.getFilters()) {
        if (!it.isNullOrEmpty()) it.first() else null
    }

    val total = Transformations.map(filter) {
        application.getString(R.string.products_subtitle,it?.pagination?.total.toString())
    }

    fun setArgs(args: ProductsFragmentArgs) {
        categoryId = args.id
        title.value = args.title
        refresh()
    }

    fun refresh() {
        page = 1
        request.value = true
    }

    fun favoriteToggle(id: Int) = repository.favoriteToggle(id)

    fun loadMore() {
        page++
        request.value = true
    }
}