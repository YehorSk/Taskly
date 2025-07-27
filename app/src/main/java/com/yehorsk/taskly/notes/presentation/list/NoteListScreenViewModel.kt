package com.yehorsk.taskly.notes.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.taskly.core.domain.onSuccess
import com.yehorsk.taskly.core.utils.AddEditAction
import com.yehorsk.taskly.core.utils.brightColors
import com.yehorsk.taskly.notes.data.database.models.CheckItem
import com.yehorsk.taskly.notes.domain.models.Note
import com.yehorsk.taskly.notes.domain.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class NoteListScreenViewModel(
    private val noteRepository: NoteRepository
): ViewModel() {

    private val cachedNotes = emptyList<Note>()
    private var noteJob: Job? = null

    private val _state = MutableStateFlow(NoteListScreenUiState())
    val state = _state
        .onStart {
            if(cachedNotes.isEmpty()){
                observeNotes()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: NoteListScreenAction){
        when(action){
            is NoteListScreenAction.OnGetNoteById -> getNoteById(action.id)
            is NoteListScreenAction.OnDescriptionChanged -> onDescriptionChanged(action.description)
            is NoteListScreenAction.OnTitleChanged -> onTitleChanged(action.title)
            is NoteListScreenAction.OnColorChange -> onColorChanged(action.color)
            is NoteListScreenAction.OnCheckItemAdded -> onCheckItemAdded()
            is NoteListScreenAction.OnCheckItemDeleted -> onCheckItemDeleted(action.item)
            is NoteListScreenAction.OnCheckItemUpdated -> onCheckItemUpdated(action.item)
            NoteListScreenAction.OnGoBackClicked -> {}
            NoteListScreenAction.OnSaveClicked -> onSaveClicked()
            NoteListScreenAction.OnUpdateClicked -> onUpdateClicked()
            NoteListScreenAction.OnAddNewNoteClicked -> onAddNewClicked()
            NoteListScreenAction.OnFABClicked -> {}
            is NoteListScreenAction.OnItemClick -> {}
        }
    }

    private fun onAddNewClicked() {
        _state.update { it.copy(
            isLoading = false,
            currentNote = null,
            title = "",
            description = "",
            color = brightColors[0],
            checkItems = emptyList(),
            action = AddEditAction.ADD,
        ) }
    }

    private fun getNoteById(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true
            ) }
            noteRepository.getNoteById(id.toString())
                .onSuccess { note ->
                    _state.update { it.copy(
                        currentNote = note,
                        title = note.title,
                        description = note.description ?: "",
                        color = note.color,
                        checkItems = note.checkItems ?: emptyList(),
                        action = AddEditAction.EDIT,
                        isLoading = false
                    ) }
                }
        }
    }

    private fun observeNotes(){
        noteJob?.cancel()
        noteJob = noteRepository
            .getAllNotes()
            .onEach { notes ->
                _state.update { it.copy(
                    items = notes
                ) }
            }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    private fun onUpdateClicked() {
        viewModelScope.launch {
            val updatedItem = Note(
                id = _state.value.currentNote!!.id,
                createdAt = _state.value.currentNote!!.createdAt,
                title = _state.value.title,
                description = _state.value.description,
                checkItems = _state.value.checkItems,
                color = _state.value.color
            )
            noteRepository.updateNote(updatedItem)
        }
    }


    private fun onSaveClicked() {
        viewModelScope.launch {
            val newItem = Note(
                id = 0,
                createdAt = LocalDateTime.now(),
                title = _state.value.title,
                description = _state.value.description,
                checkItems = _state.value.checkItems,
                color = _state.value.color
            )
            noteRepository.insertNote(newItem)
        }
    }

    private fun onCheckItemUpdated(item: CheckItem){
        _state.update { currentState ->
            currentState.copy(
                checkItems = currentState.checkItems.map {
                    if (it.id == item.id) item else it
                }
            )
        }
    }

    private fun onCheckItemDeleted(item: CheckItem){
        _state.update { currentState ->
            currentState.copy(
                checkItems = currentState.checkItems.filterNot { it.id == item.id }
            )
        }
    }

    private fun onCheckItemAdded(){
        val item = CheckItem(
            id = UUID.randomUUID().toString(),
            name = "",
            isDone = false,
        )
        _state.update { it.copy(checkItems = it.checkItems + item) }
    }

    private fun onColorChanged(color: Long){
        _state.update { it.copy(
            color = color
        ) }
    }

    private fun onDescriptionChanged(description: String){
        _state.update { it.copy(
            description = description
        ) }
    }

    private fun onTitleChanged(title: String){
        _state.update { it.copy(
            title = title
        ) }
    }

}