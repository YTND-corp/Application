package uz.uzmobile.templatex.ui.catalog

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class CatalogPagerAdapter(fa: FragmentManager, private val pages: Array<String>) : FragmentStatePagerAdapter(fa, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment = CatalogChildFragment.newInstance(position)

    override fun getCount(): Int = pages.size

    override fun getPageTitle(position: Int): CharSequence? = pages[position]
}