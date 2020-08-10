package uz.mod.templatex.ui.fullscreen_image

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fullscreen_image_fragment.*
import kotlinx.android.synthetic.main.fullscreen_image_fragment.view.*
import uz.mod.templatex.R
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.ui.product.ProductFragment.Companion.IMAGE_POSITION
import uz.mod.templatex.utils.Event
import uz.mod.templatex.utils.extension.lazyFast
import uz.mod.templatex.utils.extension.setNavigationResult

class FullScreenImageFragment : ParentFragment() {

    private val navController by lazyFast { findNavController() }
    //val args: FullScreenImageFragmentArgs by navArgs()
    var totalImageSize = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fullscreen_image_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val images = requireArguments().getStringArrayList("images")!!
        val selectPosition = requireArguments().getInt("selectedPosition")
        totalImageSize = images.size
        view.viewPager.adapter = ViewPagerAdapter(images.toList())
        view.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                tvCount.text = "${position + 1} из $totalImageSize"
                navController.setNavigationResult(Event(position), IMAGE_POSITION)
            }
        })
        view.viewPager.setCurrentItem(selectPosition, false)

        ivClose.setOnClickListener {
            navController.popBackStack()
        }
    }
}