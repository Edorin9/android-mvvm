package com.edorin.mvvmtemplate.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edorin.litunyi.coroutines.RxViewModel
import com.edorin.litunyi.coroutines.SchedulerProvider
import com.edorin.litunyi.coroutines.SingleLiveEvent
import com.edorin.litunyi.coroutines.with
import com.edorin.mvvmtemplate.domain.ColorsRepository
import com.edorin.mvvmtemplate.domain.entity.Color
import io.reactivex.rxkotlin.subscribeBy

class DetailsViewModel(
    private val id: Int,
    private val colorsRepository: ColorsRepository,
    private val schedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val _event = SingleLiveEvent<DetailsEvent>()
    val event: LiveData<DetailsEvent> get() = _event

    private val _state = MutableLiveData<DetailsState>()
    val state: LiveData<DetailsState> get() = _state

    /** Triggers **/

    fun loadColor() {
        _event.value = DetailsEvent.Pending
        launch {
            colorsRepository.getColor(id).with(schedulerProvider)
                .subscribeBy(
                    onSuccess = { color ->
                        _event.value = DetailsEvent.LoadDetailsSuccess
                        _state.value = _state.value?.copy(color = color) ?: DetailsState(color = color)
                    },
                    onError = { error -> _event.value = DetailsEvent.LoadDetailsFailed(error) })
        }
    }

}

data class DetailsState(val color: Color)

sealed class DetailsEvent {
    object Pending : DetailsEvent()
    object LoadDetailsSuccess : DetailsEvent()
    data class LoadDetailsFailed(val error: Throwable) : DetailsEvent()
}
