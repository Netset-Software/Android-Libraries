package com.testtask.ui.main

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.testtask.BR
import com.testtask.R
import com.testtask.databinding.ActivityMainBinding
import com.testtask.model.ToolbarConfig
import com.testtask.model.appinterface.AppNavigationInterface
import com.testtask.ui.base.BaseActivity
import com.testtask.ui.home.FragmentHome
import com.testtask.utils.DialogUtil
import com.testtask.utils.hide
import com.testtask.utils.show
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), AppNavigationInterface {

    var binding: ActivityMainBinding? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainViewModel {
        val vm: MainViewModel by viewModel()
        return vm
    }

    override fun getBindingVariable(): Int {
        return BR.home
    }

    var fragmentTransaction: FragmentTransaction? = null
    override fun replaceFragment(fragment: Fragment, addToStack: Boolean, showAnimation: Boolean) {
        GlobalScope.launch {
            mCurrentFrg0 = fragment
            fragmentTransaction = supportFragmentManager.beginTransaction()
            if (addToStack) {
                fragmentTransaction!!.addToBackStack(fragment::class.java.simpleName)
            }
            if (showAnimation) {
                fragmentTransaction!!.setCustomAnimations(
                        R.anim.enter,
                        R.anim.exit,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                )
            }
            fragmentTransaction!!.replace(
                    binding!!.flContainer.id,
                    mCurrentFrg0!!,
                    fragment::class.java.simpleName
            ).commit()
        }
    }

    override fun setArguments(fragment: Fragment, bundle: Bundle): Fragment {
        fragment.arguments = bundle
        return fragment
    }


    override fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    private var mDialogLoader: Dialog? = null
    override fun showLoader() {
        runOnUiThread {
            hideLoader()
            mDialogLoader = DialogUtil.setLoadingDialog(this@MainActivity);
        }
    }

    override fun hideLoader() {
        runOnUiThread {
            if (mDialogLoader != null && mDialogLoader!!.isShowing) {
                mDialogLoader?.dismiss()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(FragmentHome(), true, false)
        binding = getViewDataBinding() as ActivityMainBinding
        binding!!.apply {
            ivBack.setOnClickListener {
                onBackPressed()
            }
        }
    }

    fun isToolbarVisible(showToolbar: Boolean) {
        if (showToolbar) {
            binding?.toolbar?.show()
        } else {
            binding?.toolbar?.hide()
        }
    }

    fun configureToolbar(configureToolbar: ToolbarConfig?) {
        binding!!.apply {
            if (configureToolbar != null) {
                toolbar?.show()
                if (configureToolbar.showBack) {
                    ivBack.show()
                } else {
                    ivBack.hide()
                }

                tvHeading.text = configureToolbar.headingName ?: ""
            } else {
                toolbar?.hide()
            }
        }
    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (mCurrentFrg0 is FragmentHome) {
            if (doubleBackToExitPressedOnce) {
                finish()
            }
            this.doubleBackToExitPressedOnce = true

            DialogUtil.showExitWarning(binding!!.toolbar.flContainer, this)

            Handler(Looper.getMainLooper()).postDelayed({
                doubleBackToExitPressedOnce = false
            }, 2000)
        } else {
            onBackStackChanged()
        }
    }
}