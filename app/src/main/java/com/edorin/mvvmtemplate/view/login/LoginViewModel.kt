package com.edorin.mvvmtemplate.view.login

import androidx.lifecycle.LiveData
import com.edorin.litunyi.coroutines.RxViewModel
import com.edorin.litunyi.coroutines.SchedulerProvider
import com.edorin.litunyi.coroutines.SingleLiveEvent
import com.edorin.litunyi.coroutines.with
import com.edorin.mvvmtemplate.domain.ColorsRepository
import io.reactivex.rxkotlin.subscribeBy

class LoginViewModel(
    private val colorsRepository: ColorsRepository,
    private val schedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val _event = SingleLiveEvent<LoginEvent>()
    val event: LiveData<LoginEvent> get() = _event

    fun login(email: String, password: String) {
        _event.value = LoginEvent.Pending
        launch {
            colorsRepository.login(email, password).with(schedulerProvider)
                .subscribeBy(
                    onComplete = { _event.value = LoginEvent.LoginSuccess },
                    onError = { error -> _event.value = LoginEvent.LoginFailed(error) })
        }
    }

}

sealed class LoginEvent {
    object Pending : LoginEvent()
    object LoginSuccess : LoginEvent()
    data class LoginFailed(val error: Throwable) : LoginEvent()
}