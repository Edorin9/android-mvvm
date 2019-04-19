package com.edorin.mvvmtemplate.view.colors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.edorin.litunyi.coroutines.RxViewModel
import com.edorin.litunyi.coroutines.SchedulerProvider
import com.edorin.litunyi.coroutines.SingleLiveEvent
import com.edorin.litunyi.coroutines.with
import com.edorin.mvvmtemplate.domain.ColorsRepository
import com.edorin.mvvmtemplate.domain.entity.Color
import io.reactivex.rxkotlin.subscribeBy

class ColorsViewModel(
    private val colorsRepository: ColorsRepository,
    private val schedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val _state = MutableLiveData<ColorsState>()
    val state: LiveData<ColorsState> get() = _state

    private val _event = SingleLiveEvent<ColorsEvent>()
    val event: LiveData<ColorsEvent> get() = _event

    fun setTitleLastClicked(id: Int) {
        val title = _state.value?.colors?.first { it.id == id }?.name ?: "Colors"
        _state.value = _state.value?.copy(title = title)
    }

    /** Triggers **/

    fun loadColors() {
        _event.value = ColorsEvent.Pending
        launch {
            colorsRepository.getColors().with(schedulerProvider)
                .subscribeBy(
                    onSuccess = { colors ->
                        _event.value = ColorsEvent.LoadColorsSuccess
                        _state.value = _state.value?.copy(colors = colors) ?: ColorsState(colors = colors)
                    },
                    onError = { error -> _event.value = ColorsEvent.LoadColorsFailed(error) })
        }
    }

}

data class ColorsState(
    val title: String? = "Colors",
    val colors: List<Color> = emptyList())

sealed class ColorsEvent {
    object Pending : ColorsEvent()
    object LoadColorsSuccess : ColorsEvent()
    data class LoadColorsFailed(val error: Throwable) : ColorsEvent()
}
