package yuresko.skifme.authentication.model

sealed class TimerState {

    object RunTimer : TimerState()

    object StopTimer : TimerState()
}