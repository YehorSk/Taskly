package com.yehorsk.taskly.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.isUnspecified


@Composable
fun AutoResizedText(
    text: String,
    style: TextStyle,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Unspecified,
    color: Color = style.color,
    fontWeight: FontWeight = style.fontWeight ?: FontWeight.Light,
    textDecoration: TextDecoration = TextDecoration.None,
    maxLines: Int = 1
){
    var resizedTextStyle by remember {
        mutableStateOf(style)
    }

    var shouldDraw by remember {
        mutableStateOf(false)
    }

    val defaultFontSize = MaterialTheme.typography.bodyMedium.fontSize

    Text(
        text = text,
        color = color,
        textAlign = textAlign,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        maxLines = maxLines,
        modifier = modifier
            .drawWithContent {
                if(shouldDraw){
                    drawContent()
                }
            },
        softWrap = false,
        style = resizedTextStyle,
        onTextLayout = { result ->
            if(result.didOverflowWidth){
                if(style.fontSize.isUnspecified){
                    resizedTextStyle = resizedTextStyle.copy(
                        fontSize = defaultFontSize
                    )
                }
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )
            }else{
                shouldDraw = true
            }
        }
    )
}