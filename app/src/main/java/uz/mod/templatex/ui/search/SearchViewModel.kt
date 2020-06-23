package uz.mod.templatex.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SearchViewModel constructor(application: Application) : AndroidViewModel(application) {

    var query = MutableLiveData<String>()
    val isQuery = MutableLiveData<Boolean>()

    fun search(query: String) {
        val isQuery = query.isNotEmpty()
        if (isQuery) this.query.postValue(query)

        this.isQuery.postValue(isQuery)

        // TODO API call implementation
    }
}