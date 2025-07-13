package com.yehorsk.taskly.categories.presentation.list

import com.yehorsk.taskly.categories.domain.models.Category
import com.yehorsk.taskly.categories.domain.models.CategoryMain
import com.yehorsk.taskly.core.utils.AddEditAction
import com.yehorsk.taskly.core.utils.brightColors

data class CategoryScreenUiState(
    val items: List<Category> = emptyList(),
    val itemsMain: List<CategoryMain> = emptyList(),
    val isLoading: Boolean = true,
    val selectedCategory: Category? = null,
    val title: String = "",
    val color: Long = brightColors[0],
    val showAddCategoryDialog: Boolean = false,
    val action: AddEditAction = AddEditAction.ADD
)
