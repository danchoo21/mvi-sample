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
 * Statefull composable
 */
@Composable
fun MainScreen(
    viewModel: SampleViewModel = viewModel()
) {

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

    SampleScreenImpl(
        modifier = Modifier,
        viewState = viewState,
        onClickButton = {
            /**
             * SampleIntent.OnClickButton viewModel에 전달
             */
            viewModel.setIntent(SampleIntent.OnClickButton)
        }
    )
}

/**
 * Stateless composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SampleScreenImpl(
    modifier: Modifier,
    viewState: SampleContract.SampleViewState,
    onClickButton: () -> Unit
) {
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = viewState.title)

            Text(text = "${viewState.text} ${viewState.count}")

            Button(onClick = {
                onClickButton()
            }) {
                Text(text = viewState.buttonText)
            }
        }
    }
}