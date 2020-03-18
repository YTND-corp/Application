package uz.uzmobile.templatex.ui.profile

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.ProfileFragmentBinding
import uz.uzmobile.templatex.extension.drawable
import uz.uzmobile.templatex.model.local.entity.ProfileItem


class ProfileFragment : Fragment() {

    val viewModel: ProfileViewModel by viewModel()

    private val binding by lazy { ProfileFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.profile_fragment, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settingsFragment -> true;
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@ProfileFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@ProfileFragment.viewModel
            executePendingBindings()

            signIn.setOnClickListener {
                findNavController().navigate(R.id.action_global_sign_in_graph)
            }

            signUp.setOnClickListener {
                findNavController().navigate(R.id.action_global_sign_up_graph)
            }
        }
    }
}