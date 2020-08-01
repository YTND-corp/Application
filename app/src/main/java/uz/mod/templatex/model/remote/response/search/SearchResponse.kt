package uz.mod.templatex.model.remote.response.search

import uz.mod.templatex.model.local.entity.Brand
import uz.mod.templatex.model.local.entity.Category
import uz.mod.templatex.model.local.entity.Product
import uz.mod.templatex.model.remote.response.ProductWrapper

class SearchResponse(val brands: BrandWrapper, val categories: CategoryWrapper, val products: ProductWrapper)

data class BrandWrapper(var data: List<Brand>)
data class CategoryWrapper(var data: List<Category>)
data class ProductWrapper(var data: List<Product>)

