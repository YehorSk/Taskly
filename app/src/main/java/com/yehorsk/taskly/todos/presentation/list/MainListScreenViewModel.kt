package com.yehorsk.taskly.todos.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.taskly.core.domain.onSuccess
import com.yehorsk.taskly.core.utils.AddEditAction
import com.yehorsk.taskly.core.utils.UiText
import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.todos.domain.models.ToDo
import com.yehorsk.taskly.todos.domain.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
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
import com.yehorsk.taskly.R

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
            is MainListScreenAction.OnGetToDoById -> getToDoById(action.id)
            is MainListScreenAction.OnDescriptionChanged -> onDescriptionChanged(action.description)
            is MainListScreenAction.OnDueDateChanged -> onDueDateChanged(action.dueDate)
            is MainListScreenAction.OnTitleChanged -> onTitleChanged(action.title)
            is MainListScreenAction.OnSelectedCategoryChange -> onCategoryChanged(action.category)
            is MainListScreenAction.OnAlarmChanged -> onAlarmChanged(action.alarmOn)
            is MainListScreenAction.OnSelectedDateChanged -> onSelectedDateClick(action.date)
            is MainListScreenAction.OnIsDoneClicked -> onDoneClicked(action.todo)
            is MainListScreenAction.OnItemClick -> {}
            MainListScreenAction.OnSaveClicked -> insertToDo()
            MainListScreenAction.OnUpdateClicked -> updateToDo()
            MainListScreenAction.OnDeleteClicked -> deleteToDo()
            MainListScreenAction.OnHideDateTimePicker -> onHideDateTimePicker()
            MainListScreenAction.OnShowDateTimePicker -> onShowDateTimePicker()
            MainListScreenAction.OnAddNewToDoClicked -> onAddNewToDoClicked()
            MainListScreenAction.OnGoBackClicked -> {}
            MainListScreenAction.OnFullCalendarClicked -> {
                _state.update { it.copy(
                    openFullCalendar = !_state.value.openFullCalendar
                ) }
            }
        }
    }

    private fun onAddNewToDoClicked() {
        _state.update {
            it.copy(
                isLoading = false,
                currentToDo = null,
                title = "",
                description = "",
                selectedCategory = null,
                dueDate = LocalDate.now().atTime(23, 59),
                alarmOn = false,
                action = AddEditAction.ADD
            )
        }
    }

    init {
        viewModelScope.launch {
            _state.map { it.selectedDates }
                .distinctUntilChanged()
                .collectLatest { selectedDate ->
                    observeToDos()
                }
        }
    }

    private fun getToDoById(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = true
            ) }
            toDoRepository.getToDoById(id.toString())
                .onSuccess { todo ->
                    _state.update { it.copy(
                        currentToDo = todo,
                        title = todo.title,
                        description = todo.description ?: "",
                        selectedCategory = todo.categoryId,
                        dueDate = todo.dueDate!!,
                        alarmOn = todo.alarmOn,
                        action = AddEditAction.EDIT,
                        isLoading = false
                    ) }
                }
        }
    }

    private fun onDoneClicked(todo: ToDo) {
        viewModelScope.launch {
            toDoRepository.onDone(todo)
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
                categoryId = _state.value.selectedCategory!!,
                alarmOn = _state.value.alarmOn
            )
            toDoRepository.insertTodo(newToDo)
        }
    }

    private fun deleteToDo(){
        viewModelScope.launch {
            toDoRepository.deleteTodo(_state.value.currentToDo!!)
        }
    }

    private fun updateToDo(){
        viewModelScope.launch {
            val updateToDo = ToDo(
                id = _state.value.currentToDo!!.id,
                createdAt = LocalDateTime.now(),
                title = _state.value.title,
                description = _state.value.description,
                isDone = false,
                dueDate = _state.value.dueDate,
                categoryId = _state.value.selectedCategory!!,
                alarmOn = _state.value.alarmOn
            )
            toDoRepository.updateTodo(updateToDo)
        }
    }

    private fun observeToDos(){
        todoJob?.cancel()
        todoJob = toDoRepository
            .getTodos(dates = _state.value.selectedDates)
            .groupByRelativeDate()
            .onEach { todos ->
                _state.update { it.copy(
                    items = todos
                ) }
            }
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    private fun Flow<List<ToDo>>.groupByRelativeDate(): Flow<Map<UiText, List<ToDo>>> {
        val formatter = DateTimeFormatter.ofPattern("dd MMM")
        val today = LocalDate.now()
        return map{ todos ->
            todos
                .groupBy { todo ->
                    LocalDateTime.ofInstant(
                        todo.dueDate!!.atZone(ZoneId.systemDefault()).toInstant(),
                        ZoneId.systemDefault()
                    )
                }
                .mapValues { (_, todos) ->
                    todos.sortedBy { it.dueDate }
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


    private fun onSelectedDateClick(date: LocalDate) {
        _state.update {
            val newDates = if (date in it.selectedDates) {
                it.selectedDates - date
            } else {
                it.selectedDates + date
            }
            it.copy(selectedDates = newDates)
        }
    }

    private fun onAlarmChanged(alarmOn: Boolean){
        _state.update { it.copy(
            alarmOn = alarmOn
        ) }
    }

    private fun onCategoryChanged(category: Int){
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
        Timber.d("Date $dueDate")
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