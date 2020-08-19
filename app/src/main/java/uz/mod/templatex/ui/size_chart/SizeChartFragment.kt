package uz.mod.templatex.ui.size_chart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.size_chart_fragment.*
import uz.mod.templatex.R
import uz.mod.templatex.ui.parent.ParentFragment

class SizeChartFragment : ParentFragment() {
    val args: SizeChartFragmentArgs by navArgs()

    override fun getLayoutID(): Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.size_chart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.loadDataWithBaseURL(null, args.sizeChart, "text/html", "utf-8", null)
    }
}