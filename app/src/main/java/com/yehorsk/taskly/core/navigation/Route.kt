package com.yehorsk.taskly.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route: String) {

    @Serializable
    object Categories: Route(route = "CATEGORIES")

    @Serializable
    data class CategoryDetails(val id: String): Route(route = "CATEGORY_DETAILS")

    @Serializable
    data object ToDos: Route(route = "TODOS")

    @Serializable
    data class ToDoDetails(val id: String): Route(route = "TODO_DETAILS")

    @Serializable
    data object AddTodo: Route(route = "ADD_EDIT_TODO")

    @Serializable
    data class EditTodo(val id: String? = null): Route(route = "ADD_EDIT_TODO")

    @Serializable
    data object Notes: Route("NOTES")

    @Serializable
    data object Profile: Route("PROFILE")

}