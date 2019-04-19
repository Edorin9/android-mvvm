package com.edorin.mvvmtemplate.view.register

import androidx.lifecycle.LiveData
import com.edorin.litunyi.coroutines.RxViewModel
import com.edorin.litunyi.coroutines.SchedulerProvider
import com.edorin.litunyi.coroutines.SingleLiveEvent
import com.edorin.litunyi.coroutines.with
import com.edorin.mvvmtemplate.domain.ColorsRepository
import io.reactivex.rxkotlin.subscribeBy

class RegisterViewModel(
    private val colorsRepository: ColorsRepository,
    private val schedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val _event = SingleLiveEvent<RegisterEvent>()
    val event: LiveData<RegisterEvent> get() = _event

    fun register(email: String, password: String) {
        _event.value = RegisterEvent.Pending
        launch {
            colorsRepository.register(email, password).with(schedulerProvider)
                .subscribeBy(
                    onComplete = { _event.value = RegisterEvent.RegisterSuccess },
                    onError = { error -> _event.value = RegisterEvent.RegisterFailed(error) })
        }
    }
}

sealed class RegisterEvent {
    object Pending : RegisterEvent()
    object RegisterSuccess : RegisterEvent()
    data class RegisterFailed(val error: Throwable) : RegisterEvent()
}