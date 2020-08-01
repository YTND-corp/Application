package uz.mod.templatex.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import uz.mod.templatex.model.repository.SearchRepository

class SearchViewModel constructor(application: Application, val repository: SearchRepository) : AndroidViewModel(application) {

    var query = MutableLiveData<String>()
    var request = MutableLiveData<Boolean>()
    val shouldClearResult = MutableLiveData<Boolean>()
    val response = Transformations.switchMap(request) {
        repository.search(query.value!!)
    }

    fun search(query: String) {
        if (query.trim().isNotEmpty()) {
            this.query.value = query
            request.value = true
        } else {
            shouldClearResult.value = true
        }
    }
}