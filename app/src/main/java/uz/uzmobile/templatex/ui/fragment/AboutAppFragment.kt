package uz.uzmobile.templatex.ui.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.AboutAppFragmentBinding
import uz.uzmobile.templatex.viewModel.AboutAppViewModel

class AboutAppFragment private constructor(): Fragment() {

    val viewModel: AboutAppViewModel by viewModel()

    lateinit var binding: AboutAppFragmentBinding

    companion object {
        fun newInstance() = AboutAppFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.about_app_fragment, container, false)
        binding.lifecycleOwner = this@AboutAppFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@AboutAppFragment.viewModel
            executePendingBindings()

//            tvWeb.setOnClickListener {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://${getString(R.string.about_web)}")))
//            }
//
//            tvEmail.setOnClickListener {
//                startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${getString(R.string.about_email)}")))
//            }
//
//            tvFacebook.setOnClickListener {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/UzAuto-682312378796441")))
//            }
//
//            tvTelegram.setOnClickListener {
//                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/uzauto_official")))
//            }
//
//            tvPhone.setOnClickListener {
//                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${getString(R.string.about_phone)}")))
//            }
        }
    }
}