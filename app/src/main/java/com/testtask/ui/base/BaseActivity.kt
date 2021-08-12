package com.testtask.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import java.util.*

abstract class BaseActivity<T : ViewDataBinding, V : ViewModel> : AppCompatActivity(),
        FragmentManager.OnBackStackChangedListener {

    /**
     * Abstract methods for fetching data
     * */
    @LayoutRes
    abstract fun getLayoutId(): Int
    abstract fun getViewModel(): V
    abstract fun getBindingVariable(): Int

    private var mViewModel: V? = null

    var mCurrentFrg0: Fragment? = null
    private var mViewDataBinding: T? = null

    fun getViewDataBinding(): ViewDataBinding {
        return mViewDataBinding!!
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performDataBinding()
    }

    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        this@BaseActivity.mViewModel = mViewModel ?: getViewModel()
        mViewDataBinding!!.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding!!.executePendingBindings()
    }

    fun setCurrentFrag(frag: Fragment) {

        this@BaseActivity.mCurrentFrg0 = frag
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        try {
            val view = currentFocus
            if (view != null && (ev.action == MotionEvent.ACTION_UP
                        || ev.action == MotionEvent.ACTION_MOVE)
                && view is EditText && !view.javaClass.name.startsWith("android.webkit.")
            ) {
                val scrcoords = IntArray(2)
                view.getLocationOnScreen(scrcoords)
                val x = ev.rawX + view.getLeft() - scrcoords[0]
                val y = ev.rawY + view.getTop() - scrcoords[1]
                if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) (Objects.requireNonNull(
                    this.getSystemService(
                        INPUT_METHOD_SERVICE
                    )
                ) as InputMethodManager).hideSoftInputFromWindow(
                    this.window.decorView.applicationWindowToken, 0
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackStackChanged() {
        val localFragmentManager = supportFragmentManager
        val i = localFragmentManager.backStackEntryCount
        if (i == 1 || i == 0) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCurrentFrg0?.onActivityResult(requestCode, resultCode, data)
    }

}