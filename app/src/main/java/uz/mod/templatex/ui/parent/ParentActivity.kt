package uz.mod.templatex.ui.parent

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import timber.log.Timber
import uz.mod.templatex.R
import uz.mod.templatex.utils.extension.color
import uz.mod.templatex.model.remote.network.ApiError
import uz.mod.templatex.utils.LanguageHelper

open class ParentActivity : AppCompatActivity() {

    private var progressView: View? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LanguageHelper.onAttach(base))
    }

    fun showError(res: ApiError?) {
        Timber.e(res.toString())
        showWarningDialog(res?.title, res?.message ?: "")
    }

    fun showError(res: String?) {
        showWarningDialog(null, res ?: "")
    }

    fun showError(resId: Int) {
        showError(getString(resId))
    }

    fun showWarningDialog(
        title: String? = null,
        msg: String,
        positiveText: String = getString(R.string.action_ok),
        positiveClickListener: DialogInterface.OnClickListener? = null,
        negativeText: String? = null
    ) {
        val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme).setMessage(msg)
        title?.let {
            builder.setTitle(title)
        }

        builder.setPositiveButton(positiveText, positiveClickListener)
        negativeText?.let {
            builder.setNegativeButton(negativeText, null)
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun isProgressing(): Boolean {
        return  progressView?.visibility == View.VISIBLE
    }

    fun showLoading() {
        if (progressView==null) {
            progressView = layoutInflater.inflate(R.layout.dialog_progress, null, false)

            val viewLayoutParams = RelativeLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            var progressDialog = progressView?.findViewById<ProgressBar>(R.id.prograss_dialog)

//        if(isTransparent){
//                progressDialog?.elevation = 0f
//            progressDialog?.background = null
//        }else{
////                progressDialog?.elevation = resources.getDimension(R.dimen.progress_dimen)
//            progressDialog?.background = ContextCompat.getDrawable(this, R.drawable.bg_progress)
//        }

            progressView?.layoutParams = viewLayoutParams

            addContentView(progressView, viewLayoutParams)
        } else {
            progressView?.visibility = View.VISIBLE
        }
    }

    fun hideLoading() {
        progressView?.visibility = View.GONE
    }
}
