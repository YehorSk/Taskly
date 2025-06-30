package com.yehorsk.taskly.categories.presentation.list

import com.yehorsk.taskly.categories.domain.models.Category
import com.yehorsk.taskly.core.utils.brightColors

data class CategoryScreenUiState(
    val items: List<Category> = emptyList(),
    val isLoading: Boolean = true,
    val selectedCategory: Category? = null,
    val title: String = "",
    val color: Long = brightColors[0],
    val showAddCategoryDialog: Boolean = false
)
