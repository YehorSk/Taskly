package com.yehorsk.taskly.todos.widget

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.text.Text
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.glance.Button
import androidx.glance.ImageProvider
import androidx.glance.LocalSize
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.components.CircleIconButton
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.layout.Spacer
import androidx.glance.text.FontWeight
import androidx.glance.text.TextStyle
import com.yehorsk.taskly.R
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.background
import androidx.glance.appwidget.lazy.items
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Box
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.TextDecoration
import com.yehorsk.taskly.MainActivity
import com.yehorsk.taskly.core.utils.toHourMinute
import com.yehorsk.taskly.todos.domain.models.ToDo
import com.yehorsk.taskly.todos.domain.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDate
import java.text.DecimalFormat
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yehorsk.taskly.core.navigation.ACTION_CREATE_TODO
import com.yehorsk.taskly.settings.domain.repository.SettingsRepository
import java.util.Locale

class ToDosAppWidgetReceiver: GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = ToDosAppWidget()

}

class ToDosAppWidget : GlanceAppWidget(), KoinComponent {

    companion object {
        private val SMALL_SQUARE = DpSize(110.dp, 110.dp) // 2x2
        private val HORIZONTAL_RECTANGLE = DpSize(200.dp, 110.dp) // 3x3
        private val BIG_SQUARE = DpSize(250.dp, 250.dp) // 4x4
    }

    override val sizeMode = SizeMode.Responsive(
        setOf(
            SMALL_SQUARE,
            HORIZONTAL_RECTANGLE,
            BIG_SQUARE
        )
    )

    val todosRepository : ToDoRepository by inject()

    val settingsRepository : SettingsRepository by inject()

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {

        provideContent {
            val coroutineScope = rememberCoroutineScope()
            val todos by todosRepository.getTodos(dates = listOf(LocalDate.now())).collectAsState(emptyList())
            val language by settingsRepository.languageFlow.collectAsState("en")

            val config = Configuration(context.resources.configuration)
            config.setLocale(Locale(language))
            val localizedContext = context.createConfigurationContext(config)
            val resources = localizedContext.resources

            GlanceTheme {
                MyContent(
                    todos,
                    onDoneClicked = { todo ->
                        coroutineScope.launch {
                            todosRepository.onDone(todo)
                        }
                    },
                    onAddNewClick = {
                        val intent = Intent(
                            context, MainActivity::class.java
                        ).also {
                            it.data = "https://taskly.com/add-todo/".toUri()
                            it.action = ACTION_CREATE_TODO
                        }
                        val pendingIntent = TaskStackBuilder
                            .create(context)
                            .addNextIntentWithParentStack(intent)
                            .getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
                        pendingIntent?.send()
                    },
                    resources = resources
                )
            }
        }
    }

    @Composable
    private fun MyContent(
        todos: List<ToDo> = emptyList(),
        onDoneClicked: (ToDo) -> Unit,
        onAddNewClick: () -> Unit,
        resources: Resources
    ) {
        val size = LocalSize.current
        Scaffold(
            modifier = GlanceModifier
                .fillMaxSize(),
            backgroundColor = GlanceTheme.colors.background,
            titleBar = {
                Row(
                    modifier = GlanceModifier
                        .padding(
                            horizontal = 8.dp,
                            vertical = 8.dp
                        )
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = resources.getString(R.string.today),
                        style = TextStyle(
                            color = GlanceTheme.colors.onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(GlanceModifier.defaultWeight())
                    Text(
                        text = todos.size.toString(),
                        style = TextStyle(
                            color = GlanceTheme.colors.onPrimaryContainer,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                        ),
                        maxLines = 1,
                    )
                }
            },
            horizontalPadding = 0.dp,
            content = {
                Column(
                    modifier = GlanceModifier
                        .fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = GlanceModifier
                            .fillMaxSize()
                            .defaultWeight()
                            .cornerRadius(8.dp),
                    ) {
                        items(todos){ item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                CircleIconButton(
                                    imageProvider = if(item.isDone) ImageProvider(R.drawable.baseline_radio_button_checked_24)
                                    else ImageProvider(R.drawable.baseline_radio_button_unchecked_24),
                                    onClick = { onDoneClicked(item) },
                                    contentDescription = null
                                )
                                Spacer(modifier = GlanceModifier.defaultWeight())
                                Column {
                                    Text(
                                        modifier = GlanceModifier
                                            .fillMaxWidth(),
                                        text = item.title,
                                        maxLines = 1,
                                        style = TextStyle(
                                            color = GlanceTheme.colors.onPrimaryContainer,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                        ),
                                    )
                                    Text(
                                        modifier = GlanceModifier
                                            .fillMaxWidth(),
                                        text = item.dueDate!!.toHourMinute(),
                                        maxLines = 1,
                                        style = TextStyle(
                                            color = GlanceTheme.colors.onSecondaryContainer,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp,
                                        ),
                                    )
                                }
                            }
                        }
                    }
                    if(size != SMALL_SQUARE){
                        Box(
                            modifier = GlanceModifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Button(
                                modifier = GlanceModifier
                                    .fillMaxWidth(),
                                text = resources.getString(R.string.add_task),
                                maxLines = 1,
                                style = TextStyle(
                                    color = GlanceTheme.colors.onPrimaryContainer,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                ),
                                onClick = { onAddNewClick() },
                            )
                        }
                    }
                }
            }
        )
    }

}