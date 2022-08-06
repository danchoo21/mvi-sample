package com.danchoo.composetemplate.presentation.main.sample


object SampleContract {

    /**
     * MVI의 M : Model
     *
     * view의 상태이기 때문에 ViewState로 정의해 줬다.
     * 편의를 위해 default 값을 정해준다.
     */
    data class SampleViewState(
        val text: String = "Click Button",
        val dummyState: String = "dummyState"
    )

    /**
     * 1회성 이벤트
     */
    sealed class SampleSideEffect {
        object CheckButtonClick : SampleSideEffect()
        data class ButtonClickComplete(val message: String) : SampleSideEffect()
    }

    /**
     * MVI의 I : Intent
     * ViewModel로 전달하는 Action or Command or Event
     */
    sealed class SampleIntent {
        object OnClickButton : SampleIntent()
    }

}