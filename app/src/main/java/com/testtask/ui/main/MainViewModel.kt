package com.testtask.ui.main

import android.content.Context
import com.testtask.model.repo.TaskRepository
import com.testtask.ui.base.BaseViewModel

class MainViewModel(private val repo: TaskRepository, val ctx: Context) :
    BaseViewModel() {
}