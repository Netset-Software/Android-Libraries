package com.testtask.ui.home

import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.testtask.R
import com.testtask.databinding.ItemHomeBinding
import com.testtask.model.pojo.HomeResponse
import com.testtask.model.pojo.HomeResponseItem
import com.testtask.model.remote.ApiResponse
import com.testtask.model.repo.TaskRepository
import com.testtask.ui.base.BaseViewModel
import com.testtask.ui.base.adapter.RecyclerCallback
import com.testtask.ui.base.adapter.RecyclerViewGenericAdapter
import com.testtask.utils.event.SingleLiveEvent

class HomeViewModel(private val repo: TaskRepository, val ctx: Context) :
        BaseViewModel() {

    private val _getComments by lazy {
        MutableLiveData<ApiResponse<HomeResponse>>()
    }

    /*** LiveData that view observing
     * you can modify this as MediatorLiveData if you want to modify data model coming from api*/
    val getCommentsData: LiveData<ApiResponse<HomeResponse>> = _getComments


    fun getComments(mCtx: AppCompatActivity) {
        _getComments.removeObservers(mCtx)
        repo.getComments(_getComments)
    }

    val mutableLiveData = SingleLiveEvent<HomeResponseItem>()


    fun setAdapter(homeList: ArrayList<HomeResponseItem>?) : RecyclerViewGenericAdapter<HomeResponseItem,ItemHomeBinding>{
        return RecyclerViewGenericAdapter(homeList ?: arrayListOf(), R.layout.item_home, object : RecyclerCallback<ItemHomeBinding, HomeResponseItem> {
            override fun bindData(binder: ItemHomeBinding, model: HomeResponseItem, position: Int, itemView: View?) {
                binder.apply {
                    detail = model
                    root.setOnClickListener {
                        mutableLiveData.postValue(model)
                    }
                }
            }
        })
    }

    fun vowelsCount(vowelString:String?):String?{
        if (vowelString == null){
            return "---"
        }
        val vowelsList = ArrayList<Char>()

        vowelString.forEach {
            when(it){
                'a', 'e', 'i', 'o', 'u' -> vowelsList.add(it)
            }
        }
        val distinctSize = vowelsList.distinct()
        return distinctSize.size.toString()
    }

    fun getVowels(vowelString:String?):String?{
        if (vowelString == null){
            return "---"
        }
        val vowelsList = ArrayList<Char>()

        vowelString.forEach {
            when(it){
                'a', 'e', 'i', 'o', 'u' -> vowelsList.add(it)
            }
        }
        val distinctSize = vowelsList.distinct()
        return distinctSize.toString().replace("[","").replace("]","")
    }
}