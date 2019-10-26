package com.github.khaledakassem.photo_weather.ui.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.CallSuper
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


abstract class BaseBottomSheetDialog<VM : BaseViewModel, DB : ViewDataBinding>
    (private val mViewModelClass: Class<VM>) : BottomSheetDialogFragment(), BaseView {

    lateinit var viewModel: VM
    open lateinit var mBinding: DB

    open val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {}

        @SuppressLint("SwitchIntDef")
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_DRAGGING -> {
                }
                BottomSheetBehavior.STATE_SETTLING -> {
                }
                BottomSheetBehavior.STATE_EXPANDED -> {
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                }
                BottomSheetBehavior.STATE_HIDDEN -> {
                    dismiss()
                }
            }
        }
    }


    private fun initDataBinding(inflater: LayoutInflater) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), null, false)
        dialog?.setContentView(mBinding.root)
    }

    private fun getViewM(): VM = ViewModelProviders.of(activity!!).get(mViewModelClass)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewM()
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)

        initDataBinding(LayoutInflater.from(context))

        initViewModel(viewModel)
        initLifeCycleOwner()
        initBottomSheet()
        observeLiveDatas()
        init(null)
    }

    override fun initBottomSheet() {
        val params = (mBinding.root.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior

        if (behavior != null && behavior is BottomSheetBehavior<*>)
            behavior.setBottomSheetCallback(bottomSheetCallback)
    }

    @CallSuper
    override fun observeLiveDatas() {

    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }


    override fun initLifeCycleOwner() {
        mBinding.lifecycleOwner = this
    }


    /**
     *  You need to override this method.
     *  And you need to set viewModel to mBinding: mBinding.viewModel = viewModel
     *
     *  @param viewModel the instance of ViewModel that is related to the  activity
     */
    abstract fun initViewModel(viewModel: VM)


    override fun hideKeyboard() {
        val view = dialog?.currentFocus
        if (view != null) {
            val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }




}
