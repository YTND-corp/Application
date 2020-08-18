package uz.mod.templatex.ui.about


import android.content.Intent
import android.content.Intent.ACTION_DIAL
import android.net.Uri
import android.os.Bundle
import android.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.mod.templatex.BuildConfig
import uz.mod.templatex.R
import uz.mod.templatex.databinding.AboutFragmentBinding
import uz.mod.templatex.ui.parent.ParentFragment
import uz.mod.templatex.utils.Utils

class AboutFragment : ParentFragment() {

    val viewModel: AboutViewModel by viewModel()


    private lateinit var binding: AboutFragmentBinding

    companion object {
        fun newInstance() = AboutFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.about_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.aboutFragment -> {
                context?.let { Utils.openGooglePlay(it, BuildConfig.APPLICATION_ID) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AboutFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this@AboutFragment
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() = binding.apply {
        viewModel = this@AboutFragment.viewModel
        executePendingBindings()

        callButton.setOnClickListener {
            startActivity(Intent(ACTION_DIAL, Uri.parse("tel:${viewModel?.PHONE_NUMBER}")))
        }
    }
}