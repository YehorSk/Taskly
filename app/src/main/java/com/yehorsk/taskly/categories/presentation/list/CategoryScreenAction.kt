package com.yehorsk.taskly.categories.presentation.list

import com.yehorsk.taskly.categories.domain.models.Category

sealed interface CategoryScreenAction {

    data object OnCategoryUpdate: CategoryScreenAction
    data object OnCategorySave: CategoryScreenAction
    data object OnCategoryDelete: CategoryScreenAction
    data object ShowCategoryDialog: CategoryScreenAction
    data object HideCategoryDialog: CategoryScreenAction
    data class OnCategoryClick(val category: Category): CategoryScreenAction
    data class OnCategoryTitleChange(val title: String): CategoryScreenAction
    data class OnCategoryColorChange(val color: Long): CategoryScreenAction

}