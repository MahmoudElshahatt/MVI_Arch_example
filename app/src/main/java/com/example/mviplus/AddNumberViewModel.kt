package com.example.mviplus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AddNumberViewModel : ViewModel() {
    val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)

    private val _viewStates = MutableStateFlow<MainStates>(MainStates.Idle)
    val viewStates: StateFlow<MainStates>
        get() = _viewStates

    private var number: Int = 0

    init {
        processIntent()
    }

    //process
    private fun processIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when (it) {
                    MainIntent.AddNumberIntent -> addNumber()
                }
            }
        }
    }

    //reduce
    private fun addNumber() {
        viewModelScope.launch {
            _viewStates.value = try {
                MainStates.Number(number++)
            } catch (e: Exception) {
                MainStates.Error(e.message!!)
            }
        }
    }
}