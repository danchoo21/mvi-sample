package com.danchoo.composetemplate.presentation.main.test

import android.util.Log
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {

    init {
        Log.d("viewModel", "TestViewModel init")
    }

    fun getTestText(): String {
        return "test"
    }
}