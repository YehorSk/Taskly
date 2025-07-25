package com.yehorsk.taskly.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route: String) {

    @Serializable
    object Categories: Route(route = "CATEGORIES")

    @Serializable
    data object ToDos: Route(route = "TODOS")

    @Serializable
    data class AddEditTodo(val id: String? = null): Route(route = "ADD_EDIT_TODO")

    @Serializable
    data object Notes: Route("NOTES")

    @Serializable
    data class AddEditNote(val id: String? = null): Route(route = "ADD_EDIT_NOTE")

    @Serializable
    data object Profile: Route("PROFILE")

}