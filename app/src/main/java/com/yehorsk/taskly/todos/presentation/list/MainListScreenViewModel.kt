package com.yehorsk.taskly.todos.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class MainListScreenViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainListScreenUiState())
    val state = _state
        .onStart {

        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: MainListScreenAction){
        when(action){
            MainListScreenAction.OnDeleteClicked -> TODO()
            is MainListScreenAction.OnDescriptionChanged -> TODO()
            is MainListScreenAction.OnDueDateChanged -> TODO()
            MainListScreenAction.OnGoBackClicked -> TODO()
            is MainListScreenAction.OnIsDoneClicked -> TODO()
            MainListScreenAction.OnSaveClicked -> TODO()
            is MainListScreenAction.OnTitleChanged -> TODO()
            MainListScreenAction.OnUpdateClicked -> TODO()
        }
    }

    private fun onDescriptionChanged(description: String){
        _state.update { it.copy(
            description = description
        ) }
    }


}