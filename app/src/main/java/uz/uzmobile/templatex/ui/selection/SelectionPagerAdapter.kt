package uz.uzmobile.templatex.ui.selection

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter

class SelectionPagerAdapter(fa: FragmentManager, private val pages: Array<String>) : FragmentPagerAdapter(fa, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = SelectionChildFragment.newInstance(position)

    override fun getCount(): Int = pages.size

    override fun getPageTitle(position: Int): CharSequence? = pages[position]
}