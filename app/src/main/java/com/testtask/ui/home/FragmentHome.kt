package com.testtask.ui.home

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.testtask.R
import com.testtask.databinding.FragmentHomeBinding
import com.testtask.model.ToolbarConfig
import com.testtask.model.pojo.HomeResponse
import com.testtask.model.remote.ApiResponse
import com.testtask.ui.detail.FragmentDetail
import com.testtask.ui.main.TaskFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentHome : TaskFragment<FragmentHomeBinding, HomeViewModel>() {
    override fun getBindingVariable(): Int {
        return BR.homeFrag
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModel(): HomeViewModel {
        val vm: HomeViewModel by viewModel()
        return vm
    }

    override fun getCurrentFragment(): Fragment? {
        return this@FragmentHome
    }

    override fun showToolbar(): Boolean? {
        return true
    }

    override fun configureToolbar(): ToolbarConfig {
        return ToolbarConfig(false, "Home")
    }

    private val getCommentsResponse: Observer<ApiResponse<HomeResponse>> by lazy {
        Observer<ApiResponse<HomeResponse>> {
            if (it != null) {
                handleResponse(it)
            }
        }
    }

    var homeData: HomeResponse? = null
    private fun handleResponse(it: ApiResponse<HomeResponse>) {
        when (it.status) {
            ApiResponse.Status.LOADING -> {
                showLoading()
            }
            ApiResponse.Status.ERROR -> {
                hideLoading()
                handleError(it.error!!)
            }
            ApiResponse.Status.SUCCESS -> {
                hideLoading()
                handleSuccess(it.data)
            }

        }
    }

    private fun handleSuccess(response: HomeResponse?) {
        getViewDataBinding()!!.apply {
            data = response
            homeData = response
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (homeData == null) {
            getCommentsListing()
        }

        getViewModel().mutableLiveData.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                getViewModel().mutableLiveData.call()
                val passingString = reflectionUtils.convertPojoToString(model = data)
                val bundle = Bundle()
                bundle.putString(FragmentDetail.ITEM_DETAIL, passingString)
                val frag = setArguments(FragmentDetail(), bundle)
                replaceFragment(frag, true, true)
            }
        }
    }

    private fun getCommentsListing() {
        getViewModel().getComments(getContainerActivity())
        getViewModel().getCommentsData.observe(viewLifecycleOwner, getCommentsResponse)
    }
}