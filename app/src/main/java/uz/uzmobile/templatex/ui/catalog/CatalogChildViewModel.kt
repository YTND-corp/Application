package uz.uzmobile.templatex.ui.catalog

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import uz.uzmobile.templatex.model.local.entity.Catalog

class CatalogChildViewModel constructor(application: Application): AndroidViewModel(application) {
    val catalogs = MutableLiveData<ArrayList<Catalog>>()

    init {
        val list = ArrayList<Catalog>()
        list.add(Catalog(0,"Одежда"))
        list.add(Catalog(0,"Обувь"))
        list.add(Catalog(0,"Аксессуары"))
        list.add(Catalog(0,"Спорт"))
        list.add(Catalog(0,"Promotions 40%"))
        list.add(Catalog(0,"Premium"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        list.add(Catalog(0,"Красота"))
        catalogs.value = list
    }
}
