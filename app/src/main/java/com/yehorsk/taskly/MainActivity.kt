package com.yehorsk.taskly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.yehorsk.taskly.core.navigation.NavigationRoot
import com.yehorsk.taskly.ui.theme.TasklyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TasklyTheme {
                NavigationRoot(
                    navController = rememberNavController()
                )
            }
        }
    }
}

