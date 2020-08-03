package uz.mod.templatex.model.remote.response.search

import uz.mod.templatex.model.local.entity.Brand
import uz.mod.templatex.model.local.entity.Product

class SearchResponse(val brands: List<Brand>, val categories: List<Brand>, val products: List<Product>)

