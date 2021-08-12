package com.testtask.ui.detail

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.testtask.R
import com.testtask.databinding.FragmentDetailBinding
import com.testtask.model.ToolbarConfig
import com.testtask.model.pojo.HomeResponseItem
import com.testtask.ui.home.HomeViewModel
import com.testtask.ui.main.TaskFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentDetail : TaskFragment<FragmentDetailBinding, HomeViewModel>() {
    override fun getBindingVariable(): Int {
        return BR.homeDetail
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_detail
    }

    override fun getViewModel(): HomeViewModel {
        val vm: HomeViewModel by viewModel()
        return vm
    }

    override fun getCurrentFragment(): Fragment? {
        return this@FragmentDetail
    }

    override fun showToolbar(): Boolean? {
        return true
    }

    override fun configureToolbar(): ToolbarConfig? {
        return ToolbarConfig(true,"Detail")
    }
    companion object{
        const val ITEM_DETAIL = "x-item-detail"
    }
    var homeItem : HomeResponseItem ?= null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argumentString = arguments?.getString(ITEM_DETAIL,"") ?: ""
        homeItem = reflectionUtils.getStringToPojo(argumentString,HomeResponseItem::class.java)
        getViewDataBinding()!!.apply {
            detail = homeItem
        }
    }
}