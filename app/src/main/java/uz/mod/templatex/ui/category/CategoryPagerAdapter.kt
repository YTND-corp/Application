package uz.mod.templatex.ui.category

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.mod.templatex.model.local.entity.CategoryGender

class CategoryPagerAdapter(fa: FragmentManager, private var pages: List<CategoryGender> = listOf()) : FragmentPagerAdapter(fa, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = CategoryChildFragment.newInstance(pages[position])

    override fun getCount(): Int = pages.size

    override fun getPageTitle(position: Int): CharSequence? = pages[position].name

    fun setItems(data: List<CategoryGender>?) {
        pages = data?: arrayListOf()
        notifyDataSetChanged()
    }
}