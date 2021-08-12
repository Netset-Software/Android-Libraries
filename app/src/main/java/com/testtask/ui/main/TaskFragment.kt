package com.testtask.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.testtask.model.appinterface.FragmentView
import com.testtask.model.remote.ApiResponse
import com.testtask.model.remote.ReflectionUtil
import com.testtask.ui.base.BaseFragment
import com.testtask.ui.home.FragmentHome
import org.json.JSONObject
import org.koin.android.ext.android.inject


abstract class TaskFragment<T : ViewDataBinding, V : ViewModel> : BaseFragment<T, V>(),
        FragmentView {

    private var containerActivity: MainActivity? = null
    val reflectionUtils: ReflectionUtil by inject()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.containerActivity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        try {
            containerActivity?.isToolbarVisible(showToolbar()!!)
            containerActivity?.setCurrentFrag(getCurrentFragment()!!)
            containerActivity?.configureToolbar(configureToolbar())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getContainerActivity(): MainActivity {
        return containerActivity!!
    }


    fun showToast(message: String) {
        this.containerActivity?.showToast(message)
    }

    fun replaceFragment(frg0: Fragment, isBack: Boolean, showAnim: Boolean) {
        containerActivity!!.replaceFragment(frg0, isBack, showAnim)
    }

    fun setArguments(mFragment: Fragment, mBundle: Bundle): Fragment {
        return containerActivity?.setArguments(mFragment, mBundle)!!
    }


    fun showLoading(){
        getContainerActivity().showLoader()
    }
    fun hideLoading(){
        getContainerActivity().hideLoader()
    }
    fun handleError(data: ApiResponse.ApiError) {
        var message = ""
        try {
            message = JSONObject(data.errorBody)?.optString("message")
            showToast(message)
        } catch (e: Exception) {
            e.printStackTrace()
            message = data.message
            showToast(message)
        }
    }
}