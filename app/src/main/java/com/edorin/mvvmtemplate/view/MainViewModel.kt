package com.edorin.mvvmtemplate.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edorin.litunyi.coroutines.RxViewModel

class MainViewModel : RxViewModel() {

    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> get() = _state

    /** Triggers **/

    fun startLoad() {
        _state.value = MainState(isLoading = true)
    }

    fun endLoad() {
        _state.value = MainState(isLoading = false)
    }

}

data class MainState(val isLoading: Boolean = false)