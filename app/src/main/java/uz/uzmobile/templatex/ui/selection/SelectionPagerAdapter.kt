package uz.uzmobile.templatex.ui.selection

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.uzmobile.templatex.model.local.entity.HomeGender

class SelectionPagerAdapter(fa: FragmentManager, private var pages: List<HomeGender> = listOf()) : FragmentPagerAdapter(fa, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = SelectionChildFragment.newInstance(pages[position])

    override fun getCount(): Int = pages.size

    override fun getPageTitle(position: Int): CharSequence? = pages[position].name
    fun setItems(data: List<HomeGender>?) {
        pages = data?: listOf()
        notifyDataSetChanged()
    }
}