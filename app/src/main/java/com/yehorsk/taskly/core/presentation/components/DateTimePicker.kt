package com.yehorsk.taskly.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.ui.theme.TasklyTheme
import network.chaintech.kmp_date_time_picker.ui.datetimepicker.WheelDateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import com.yehorsk.taskly.R
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import network.chaintech.kmp_date_time_picker.utils.now
import java.time.LocalDateTime

@Composable
fun DateTimePicker(
    showDatePicker: Boolean,
    startDate: LocalDateTime?,
    @StringRes title: Int,
    onDateChangeListener: (snappedDate: LocalDateTime) -> Unit = {},
    onDismiss: () -> Unit = {},
){
    val monthNames = listOf(
        stringResource(R.string.january),
        stringResource(R.string.february),
        stringResource(R.string.march),
        stringResource(R.string.april),
        stringResource(R.string.may),
        stringResource(R.string.june),
        stringResource(R.string.july),
        stringResource(R.string.august),
        stringResource(R.string.september),
        stringResource(R.string.october),
        stringResource(R.string.november),
        stringResource(R.string.december)
    )
    WheelDateTimePickerView(
        startDate = (startDate ?: LocalDateTime.now()).toKotlinLocalDateTime(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 22.dp, bottom = 22.dp),
        showDatePicker = showDatePicker,
        minDate = kotlinx.datetime.LocalDateTime.now(),
        height = 170.dp,
        containerColor = MaterialTheme.colorScheme.background,
        title = stringResource(title),
        customMonthNames = monthNames,
        dateTimePickerView = DateTimePickerView.DIALOG_VIEW,
        titleStyle = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.onSurface
        ),
        doneLabelStyle = TextStyle(
            fontWeight = FontWeight(600),
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.onSurface
        ),
        defaultDateTextStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface
        ),
        selectedDateTextStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface
        ),
        dragHandle = {
            HorizontalDivider(
                modifier = Modifier.padding(top = 8.dp).width(50.dp).clip(CircleShape),
                thickness = 4.dp,
                color = Color(0xFFE8E4E4)
            )
        },
        rowCount = 3,
        onDoneClick = {
            onDateChangeListener(it.toJavaLocalDateTime())
            onDismiss()
        },
        onDismiss = {
            onDismiss()
        }
    )
}

@Preview
@Composable
fun DateTimePickerPreview(){
    TasklyTheme {
        DateTimePicker(
            showDatePicker = true,
            title = R.string.due_date,
            onDateChangeListener = {},
            startDate = LocalDateTime.now(),
            onDismiss = {}
        )
    }
}

@Preview
@Composable
fun DateTimePickerDarkPreview(){
    TasklyTheme(
        isDarkMode = true
    ) {
        DateTimePicker(
            showDatePicker = true,
            title = R.string.due_date,
            onDateChangeListener = {},
            startDate = LocalDateTime.now(),
            onDismiss = {}
        )
    }
}