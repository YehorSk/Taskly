package com.yehorsk.taskly.core.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Categories: Route

}