package com.yehorsk.taskly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yehorsk.taskly.core.navigation.NavigationRoot
import com.yehorsk.taskly.core.navigation.Route
import com.yehorsk.taskly.core.presentation.components.BottomBar
import com.yehorsk.taskly.ui.theme.TasklyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TasklyTheme {
                Scaffold(
                    bottomBar = {
                        BottomBar(
                            navController = navController
                        )
                    },
                    floatingActionButton = {
                        val navBackStackEntry = navController.currentBackStackEntryAsState().value
                        when (navBackStackEntry?.destination?.route) {
                            Route.ToDos.route -> {
                                FloatingActionButton(onClick = { navController.navigate(Route.AddTodo.route) }) {
                                    Icon(Icons.Default.Add, contentDescription = null)
                                }
                            }
                            else -> {}
                        }
                    }
                ) { padding ->
                    NavigationRoot(
                        modifier = Modifier.padding(padding),
                        navController = navController
                    )
                }
            }
        }
    }
}

