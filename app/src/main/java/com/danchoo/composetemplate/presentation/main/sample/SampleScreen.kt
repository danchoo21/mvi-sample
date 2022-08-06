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


/**
 * MVI의 V : View
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: SampleViewModel = viewModel()
) {

    /**
     * viewState 를
     */
    val viewState = viewModel.viewState.value

    /**
     * LaunchedEffect key1을 Unit 으로 설정하여 한번만 생성이 되도록 한다.
     */
    LaunchedEffect(key1 = Unit) {
        /**
         * viewState 받아오기 위하여 subscribeToIntent를 호출해 준다.
         */
        viewModel.subscribeToIntent()

        /**
         * sideEffect 를 collect 하여 1회성 이벤트를 처리해 준다.
         */
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
                /**
                 * SampleIntent.OnClickButton viewModel에 전달
                 */
                viewModel.setIntent(SampleIntent.OnClickButton)
            }) {
                Text(text = "Button")
            }
        }
    }
}