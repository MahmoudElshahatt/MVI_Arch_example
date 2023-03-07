package com.example.mviplus

sealed class MainStates {
    //idle
    object Idle : MainStates()

    //number
    data class Number(val number: Int) : MainStates()

    //error
    data class Error(val error: String) : MainStates()
}
