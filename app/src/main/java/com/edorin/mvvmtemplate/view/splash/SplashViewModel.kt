package com.edorin.mvvmtemplate.view.splash

import androidx.lifecycle.LiveData
import com.edorin.litunyi.coroutines.RxViewModel
import com.edorin.litunyi.coroutines.SchedulerProvider
import com.edorin.litunyi.coroutines.SingleLiveEvent
import com.edorin.litunyi.coroutines.with
import com.edorin.mvvmtemplate.domain.ColorsRepository
import io.reactivex.rxkotlin.subscribeBy

class SplashViewModel(
    private val colorsRepository: ColorsRepository,
    private val schedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val _event = SingleLiveEvent<SplashEvent>()
    val event: LiveData<SplashEvent>
        get() = _event

    /** Triggers **/

    fun mockLoad() {
        launch {
            colorsRepository.isAuthenticated.with(schedulerProvider)
                .subscribeBy(
                    onSuccess = { isAuthenticated ->
                        _event.value = if (isAuthenticated) SplashEvent.Authenticated else SplashEvent.Unauthenticated
                    },
                    onError = { _event.value = SplashEvent.Unauthenticated })
        }
    }

}

sealed class SplashEvent {
    object Authenticated : SplashEvent()
    object Unauthenticated : SplashEvent()
}