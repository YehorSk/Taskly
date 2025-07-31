package com.yehorsk.taskly.notes.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.taskly.R
import com.yehorsk.taskly.core.domain.onSuccess
import com.yehorsk.taskly.core.utils.AddEditAction
import com.yehorsk.taskly.core.utils.UiText
import com.yehorsk.taskly.core.utils.brightColors
import com.yehorsk.taskly.notes.data.database.models.CheckItem
import com.yehorsk.taskly.notes.domain.models.Note
import com.yehorsk.taskly.notes.domain.repository.NoteRepository
import com.yehorsk.taskly.todos.domain.models.ToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
            is NoteListScreenAction.OnNoteCheckItemClick -> onNoteCheckItemClick(action.note, action.checkItem)
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
            .groupByRelativeDate()
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

    private fun onNoteCheckItemClick(
        note: Note,
        checkItem: CheckItem
    ) {
        viewModelScope.launch {
            val updatedCheckItems = note.checkItems?.map {
                if (it.id == checkItem.id) {
                    checkItem.copy(isDone = checkItem.isDone)
                } else it
            }
            val updatedNote = note.copy(checkItems = updatedCheckItems)
            Timber.d("onNoteCheckItemClick $updatedNote")
            noteRepository.updateNote(updatedNote)
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

    private fun Flow<List<Note>>.groupByRelativeDate(): Flow<Map<UiText, List<Note>>> {
        val formatter = DateTimeFormatter.ofPattern("dd MMM")
        val today = LocalDate.now()
        return map{ notes ->
            notes
                .groupBy { todo ->
                    LocalDateTime.ofInstant(
                        todo.createdAt.atZone(ZoneId.systemDefault()).toInstant(),
                        ZoneId.systemDefault()
                    )
                }
                .mapValues { (_, notes) ->
                    notes.sortedBy { it.createdAt }
                }
                .toSortedMap(compareBy { it })
                .mapKeys { (dateTime, _) ->
                    val date = dateTime.toLocalDate()
                    when(date){
                        today -> UiText.StringResource(R.string.today)
                        today.plusDays(1) -> UiText.StringResource(R.string.tomorrow)
                        today.minusDays(1) -> UiText.StringResource(R.string.yesterday)
                        else -> UiText.DynamicString(date.format(formatter))
                    }
                }
        }
    }

}