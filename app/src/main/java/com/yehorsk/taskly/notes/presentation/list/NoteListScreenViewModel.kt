package com.yehorsk.taskly.notes.presentation.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NoteListScreenViewModel: ViewModel() {

    private val _state = MutableStateFlow(NoteListScreenUiState())
    val state = _state.asStateFlow()

    fun onAction(action: NoteListScreenAction){
        when(action){
            is NoteListScreenAction.OnDescriptionChanged -> TODO()
            is NoteListScreenAction.OnTitleChanged -> TODO()
            NoteListScreenAction.OnGoBackClicked -> {}
            NoteListScreenAction.OnSaveClicked -> TODO()
            NoteListScreenAction.OnUpdateClicked -> TODO()
        }
    }

}