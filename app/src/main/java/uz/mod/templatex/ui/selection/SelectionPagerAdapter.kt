package uz.mod.templatex.ui.selection

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import uz.mod.templatex.model.local.entity.HomeGender
import uz.mod.templatex.ui.common.OurServiceFragment

class SelectionPagerAdapter(fa: FragmentManager, private var pages: List<HomeGender> = listOf()) : FragmentPagerAdapter(fa, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = SelectionChildFragment.newInstance(pages[position]) //OurServiceFragment()

    override fun getCount(): Int = pages.size

    override fun getPageTitle(position: Int): CharSequence? = pages[position].name
    fun setItems(data: List<HomeGender>?) {
        pages = data?: listOf()
        notifyDataSetChanged()
    }
}