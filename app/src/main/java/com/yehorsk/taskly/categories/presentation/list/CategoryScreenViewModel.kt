package com.yehorsk.taskly.categories.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yehorsk.taskly.categories.domain.models.Category
import com.yehorsk.taskly.categories.domain.repository.CategoryRepository
import com.yehorsk.taskly.core.utils.brightColors
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
import kotlin.String

class CategoryScreenViewModel(
    private val repository: CategoryRepository
): ViewModel() {

    private var cachedCategories = emptyList<Category>()
    private var categoryJob: Job? = null

    private val _state = MutableStateFlow(CategoryScreenUiState())
    val state = _state
        .onStart {
            if(cachedCategories.isEmpty()){
                observeCategories()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: CategoryScreenAction){
        when(action){
            is CategoryScreenAction.OnCategoryDelete -> deleteCategory()
            is CategoryScreenAction.OnCategoryUpdate -> updateCategory()
            is CategoryScreenAction.OnCategoryClick -> onCategorySelect(action.category)
            is CategoryScreenAction.OnCategoryTitleChange -> onCategoryTitleChange(action.title)
            is CategoryScreenAction.OnCategoryColorChange -> onCategoryColorChange(action.color)
            CategoryScreenAction.OnCategorySave -> insertCategory()
            CategoryScreenAction.HideCategoryDialog -> hideCategoryDialog()
            CategoryScreenAction.ShowCategoryDialog -> showCategoryDialog()
            CategoryScreenAction.OnInputValidate -> validateInput()
        }
    }

    private fun validateInput(): Boolean{
        return _state.value.title.isNotEmpty()
    }

    private fun onCategorySelect(category: Category){
        _state.update { it.copy(
            selectedCategory = category,
            title = category.title,
            color = category.bgColor
        ) }
    }

    private fun onCategoryTitleChange(title: String) {
        _state.update { it.copy(
            title = title
        ) }
    }

    private fun onCategoryColorChange(color: Long) {
        _state.update { it.copy(
            color = color
        ) }
    }

    private fun hideCategoryDialog() {
        _state.update { it.copy(
            showAddCategoryDialog = false
        ) }
        clearState()
    }

    private fun showCategoryDialog() {
        _state.update { it.copy(
            showAddCategoryDialog = true
        ) }
    }

    private fun observeCategories(){
        categoryJob?.cancel()
        categoryJob = repository
            .getAllCategories()
            .onEach { categories ->
                _state.update { it.copy(
                    items = categories,
                    isLoading = false
                ) }
                cachedCategories = categories
            }
            .launchIn(viewModelScope)
    }

    private fun deleteCategory() {
        viewModelScope.launch {
            repository.deleteCategory(_state.value.selectedCategory!!)
        }
    }

    private fun insertCategory() {
        viewModelScope.launch {
            repository.insertCategory(
                Category(
                    title = _state.value.title,
                    createdAt = LocalDateTime.now(),
                    bgColor = _state.value.color
                )
            )
            hideCategoryDialog()
            clearState()
        }
    }

    private fun updateCategory() {
        viewModelScope.launch {
            repository.insertCategory(
                Category(
                    id = _state.value.selectedCategory!!.id,
                    title = _state.value.title,
                    bgColor = _state.value.color,
                    createdAt = LocalDateTime.now(),                )
            )
            hideCategoryDialog()
            clearState()
        }
    }

    private fun clearState(){
        _state.update { it.copy(
            selectedCategory = null,
            title = "",
            color = brightColors[0],
        ) }
    }

}