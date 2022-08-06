package com.danchoo.composetemplate.presentation.main.sample

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danchoo.composetemplate.presentation.main.sample.SampleContract.SampleIntent
import com.danchoo.composetemplate.presentation.main.sample.SampleContract.SampleSideEffect
import com.danchoo.composetemplate.presentation.main.sample.SampleContract.SampleViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SampleViewModel : ViewModel() {

    private val _viewState = mutableStateOf(SampleViewState())
    val viewState: State<SampleViewState> get() = _viewState

    private val _sideEffect = Channel<SampleSideEffect>()
    val sideEffect: Flow<SampleSideEffect> get() = _sideEffect.receiveAsFlow()

    private val _intent = MutableSharedFlow<SampleIntent>()

    private var count = 0

    fun subscribeToIntent() {
        viewModelScope.launch {
            _intent.collect { handleIntent(it) }
        }
    }

    /**
     * 전달 받은 Intent 를 처리
     */
    private suspend fun handleIntent(intent: SampleIntent) {
        when (intent) {
            is SampleIntent.OnClickButton -> {
                _sideEffect.send(SampleSideEffect.CheckButtonClick)

                _viewState.value = viewState.value.copy(
                    text = "current count ${++count}"
                )

                _sideEffect.send(SampleSideEffect.ButtonClickComplete("onClick button success"))
            }
        }
    }

    /**
     * view에서 전달 받은 Intent를 sharedFlow에 전달 해준다.
     */
    fun setIntent(intent: SampleIntent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }
}