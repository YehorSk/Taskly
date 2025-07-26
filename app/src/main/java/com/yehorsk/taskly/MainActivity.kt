package com.yehorsk.taskly

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yehorsk.taskly.core.navigation.NavigationRoot
import com.yehorsk.taskly.core.navigation.Route
import com.yehorsk.taskly.core.presentation.components.BottomBar
import com.yehorsk.taskly.ui.theme.TasklyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
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
                                FloatingActionButton(onClick = { navController.navigate(Route.AddEditTodo()) }) {
                                    Icon(Icons.Default.Add, contentDescription = null)
                                }
                            }
                            Route.Notes.route -> {
                                FloatingActionButton(onClick = { navController.navigate(Route.AddEditNote()) }) {
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

    private fun requestNotificationPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if(!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }
}

