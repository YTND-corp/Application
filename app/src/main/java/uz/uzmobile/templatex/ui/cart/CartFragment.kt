package uz.uzmobile.templatex.ui.cart

import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.yanzhenjie.recyclerview.SwipeMenuItem
import org.koin.androidx.viewmodel.ext.android.viewModel
import uz.uzmobile.templatex.R
import uz.uzmobile.templatex.databinding.CartFragmentBinding
import uz.uzmobile.templatex.extension.color
import uz.uzmobile.templatex.extension.drawable
import uz.uzmobile.templatex.model.local.entity.Product


class CartFragment : Fragment() {

    val viewModel: CartViewModel by viewModel()

    private val binding by lazy { CartFragmentBinding.inflate(layoutInflater) }

    companion object {
        fun newInstance() = CartFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.lifecycleOwner = this@CartFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            viewModel = this@CartFragment.viewModel
            executePendingBindings()

            products.hasFixedSize()


            continueButton.setOnClickListener {
                findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
            }


            products.isSwipeItemMenuEnabled = true
            products.setSwipeMenuCreator { leftMenu, rightMenu, position ->
                
                val height = ViewGroup.LayoutParams.MATCH_PARENT

                val item = SwipeMenuItem(requireContext())
                    .setBackground(requireContext().drawable(R.drawable.bg_cart_menu_item_delete))
                    .setText("Delete")
                    .setTextColor(requireContext().color(R.color.whiteColor))
                    .setHeight(height)
                rightMenu.addMenuItem(item)
            }

            products.adapter =
                CartAdapter(arrayListOf(Product(1), Product(1), Product(1), Product(1)))


//            setRecyclerViewItemTouchListener()
        }
    }

    private fun setRecyclerViewItemTouchListener() {
        val itemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
//                val position = viewHolder.adapterPosition
//                binding.products.removeA(position)
//                binding.products.adapter?.notifyItemRemoved(position)
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    if (viewHolder is CartAdapter.ViewHolder) {
                        viewHolder
                    }
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.products)
    }
}
