package com.danchoo.composetemplate.presentation.main.sample


object SampleContract {

    data class SampleViewState(
        val text: String = "Click Button",
        val dummyState: String = "dummyState"
    )

    sealed class SampleSideEffect {
        object CheckButtonClick : SampleSideEffect()
        data class ButtonClickComplete(val message: String) : SampleSideEffect()
    }

    sealed class SampleIntent {
        object OnClickButton : SampleIntent()
    }

}