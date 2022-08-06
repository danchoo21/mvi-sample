package com.danchoo.composetemplate.presentation.main.sample

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.danchoo.composetemplate.presentation.main.sample.SampleContract.SampleIntent
import com.danchoo.composetemplate.presentation.main.sample.SampleContract.SampleSideEffect
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: SampleViewModel = viewModel()
) {

    val viewState = viewModel.viewState.value

    LaunchedEffect(key1 = Unit) {
        viewModel.subscribeToIntent()
        viewModel.sideEffect
            .onEach {
                when (it) {
                    is SampleSideEffect.CheckButtonClick -> {
                        Log.d("SideEffect", "CheckButtonClick")
                    }
                    is SampleSideEffect.ButtonClickComplete -> {
                        Log.d("SideEffect", it.message)
                    }
                }
            }.collect()
    }


    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = viewState.text)

            Button(onClick = {
                viewModel.setIntent(SampleIntent.OnClickButton)
            }) {
                Text(text = "Button")
            }
        }
    }
}