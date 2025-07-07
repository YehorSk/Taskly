package com.yehorsk.taskly.todos.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.domain.models.ToDo
import com.yehorsk.taskly.todos.domain.repository.ToDoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainListScreenViewModel(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private var cachedCategories = emptyList<CategorySummary>()
    private var cachedToDos = emptyList<ToDo>()
    private var todoJob: Job? = null

    private val _state = MutableStateFlow(MainListScreenUiState())
    val state = _state
        .onStart {
            if(cachedCategories.isEmpty()){
                fetchCategories()
            }
            if (cachedToDos.isEmpty()){
                observeToDos()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: MainListScreenAction){
        when(action){
            MainListScreenAction.OnDeleteClicked -> TODO()
            is MainListScreenAction.OnDescriptionChanged -> onDescriptionChanged(action.description)
            is MainListScreenAction.OnDueDateChanged -> onDueDateChanged(action.dueDate)
            is MainListScreenAction.OnTitleChanged -> onTitleChanged(action.title)
            is MainListScreenAction.OnSelectedCategoryChange -> onCategoryChanged(action.category)
            is MainListScreenAction.OnAlarmChanged -> onAlarmChanged(action.alarmOn)
            is MainListScreenAction.OnIsDoneClicked -> TODO()
            MainListScreenAction.OnGoBackClicked -> {}
            MainListScreenAction.OnSaveClicked -> insertToDo()
            MainListScreenAction.OnUpdateClicked -> TODO()
            MainListScreenAction.OnHideDateTimePicker -> onHideDateTimePicker()
            MainListScreenAction.OnShowDateTimePicker -> onShowDateTimePicker()
        }
    }

    private fun insertToDo(){
        viewModelScope.launch {
            val newToDo = ToDo(
                createdAt = LocalDateTime.now(),
                title = _state.value.title,
                description = _state.value.description,
                isDone = false,
                dueDate = _state.value.dueDate,
                categoryId = _state.value.selectedCategory!!.id,
                alarmOn = _state.value.alarmOn
            )
            toDoRepository.insertTodo(newToDo)
        }
    }

    private fun observeToDos(){
        todoJob?.cancel()
        todoJob = toDoRepository
            .getTodos()
            .onEach { todos ->
                _state.update { it.copy(
                    items = todos
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchCategories(){
        viewModelScope.launch {
            val categories = toDoRepository
                .getCategorySummaries()
            _state.update { it.copy(
                categories = categories
            ) }
            cachedCategories = categories
        }
    }

    private fun onAlarmChanged(alarmOn: Boolean){
        _state.update { it.copy(
            alarmOn = alarmOn
        ) }
    }

    private fun onCategoryChanged(category: CategorySummary){
        _state.update { it.copy(
            selectedCategory = category
        ) }
    }

    private fun onTitleChanged(title: String){
        _state.update { it.copy(
            title = title
        ) }
    }

    private fun onDueDateChanged(dueDate: LocalDateTime){
        _state.update { it.copy(
            dueDate = dueDate
        ) }
    }

    private fun onDescriptionChanged(description: String){
        _state.update { it.copy(
            description = description
        ) }
    }

    private fun onShowDateTimePicker(){
        _state.update { it.copy(
            showDateTimePicker = true
        ) }
    }

    private fun onHideDateTimePicker(){
        _state.update { it.copy(
            showDateTimePicker = false
        ) }
    }

}