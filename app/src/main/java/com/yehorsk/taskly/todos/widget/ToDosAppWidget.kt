package com.yehorsk.taskly.todos.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
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
import androidx.glance.appwidget.lazy.items
import androidx.glance.layout.padding
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

class ToDosAppWidgetReceiver: GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget
        get() = ToDosAppWidget()

}

class ToDosAppWidget : GlanceAppWidget(), KoinComponent {

    companion object {
        private val SMALL_SQUARE = DpSize(110.dp, 110.dp) // 2x2
        private val HORIZONTAL_RECTANGLE = DpSize(200.dp, 110.dp) // 3x2
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

    override suspend fun provideGlance(
        context: Context,
        id: GlanceId
    ) {

        provideContent {
            val coroutineScope = rememberCoroutineScope()
            val todos by todosRepository.getTodos(dates = listOf(LocalDate.now())).collectAsState(emptyList())

            GlanceTheme {
                MyContent(
                    todos,
                    onDoneClicked = { todo ->
                        coroutineScope.launch {
                            todosRepository.onDone(todo)
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun MyContent(
        todos: List<ToDo> = emptyList(),
        onDoneClicked: (ToDo) -> Unit
    ) {
        val size = LocalSize.current
        Scaffold(
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
                        text = "Today",
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
                LazyColumn(
                    modifier = GlanceModifier
                        .background(GlanceTheme.colors.background)
                        .fillMaxSize()
                        .cornerRadius(8.dp)
                        .background(Color.White),
                ) {
                    items(todos){ item ->
                        Row(
                            modifier = GlanceModifier
                                .background(GlanceTheme.colors.background)
                            ,
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
                                        .fillMaxWidth()
                                        .background(GlanceTheme.colors.background),
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
                                        .fillMaxWidth()
                                        .background(GlanceTheme.colors.background),
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
            }
        )
    }

}