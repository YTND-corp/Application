package uz.mod.templatex.ui.fullscreen_image

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fullscreen_image_fragment.*
import kotlinx.android.synthetic.main.fullscreen_image_fragment.view.*
import uz.mod.templatex.R
import uz.mod.templatex.ui.parent.ParentFragment

class FullScreenImageFragment : ParentFragment() {

    val args: FullScreenImageFragmentArgs by navArgs()
    var totalImageSize = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fullscreen_image_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        totalImageSize = args.images.size
        view.viewPager.adapter = ViewPagerAdapter(args.images.toList())
        view.viewPager.setCurrentItem(args.selectedPosition, false)
        view.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tvCount.text = "${position + 1} из $totalImageSize"
            }
        })

        ivClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}