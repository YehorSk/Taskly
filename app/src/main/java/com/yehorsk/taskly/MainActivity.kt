package com.yehorsk.taskly

import android.Manifest
import android.app.LocaleManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.LocaleList
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getContextForLanguage
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.yehorsk.taskly.core.navigation.NavigationRoot
import com.yehorsk.taskly.ui.theme.TasklyTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val themeViewModel: ThemeViewModel by viewModel()

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(getContextForLanguage(newBase))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()
        enableEdgeToEdge()
        setContent {

            val darkMode by themeViewModel.darkMode.collectAsStateWithLifecycle()
            val language by themeViewModel.language.collectAsStateWithLifecycle()

            val navController = rememberNavController()

            setLocale(language, this)

            TasklyTheme(
                isDarkMode = darkMode
            ) {
                Surface {
                    NavigationRoot(
                        navController = navController
                    )
                }
            }
        }
    }

    private fun setLocale(language: String, context: Context) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            context.getSystemService(LocaleManager::class.java)
                .applicationLocales = LocaleList.forLanguageTags(language)
        }else{
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(language))
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

