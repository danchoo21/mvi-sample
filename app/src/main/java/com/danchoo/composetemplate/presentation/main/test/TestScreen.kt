package com.danchoo.composetemplate.presentation.main.test

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun MainScreen(
    viewModel: TestViewModel = viewModel()
) {

    Text(text = viewModel.getTestText())
}