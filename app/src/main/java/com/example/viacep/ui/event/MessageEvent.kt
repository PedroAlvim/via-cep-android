package com.example.viacep.ui.event

sealed class MessageEvent {
    object ShowEmptyCep : MessageEvent()
    object ShowLoadingCep : MessageEvent()
    object ShowFindAddress : MessageEvent()
    object ShowNotFindAddress : MessageEvent()
    object ShowErrorFindAddress : MessageEvent()

}