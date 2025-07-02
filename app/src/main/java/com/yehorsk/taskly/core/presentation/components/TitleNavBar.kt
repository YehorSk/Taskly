package com.yehorsk.taskly.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.yehorsk.taskly.ui.theme.TasklyTheme
import com.yehorsk.taskly.R

@Composable
fun TitleNavBar(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    @StringRes title: Int = R.string.go_back,
    showGoBack: Boolean = true
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        if (showGoBack) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 10.dp)
                    .size(40.dp)
                    .clickable { onGoBack() },
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }
        Text(
            text = stringResource(title),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = LocalTextStyle.current.merge(
                TextStyle(
                    lineHeightStyle = LineHeightStyle(
                        alignment = LineHeightStyle.Alignment.Center,
                        trim = LineHeightStyle.Trim.None
                    ),
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ),
                    lineHeight = 3.2.em,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            )
        )
    }
}

@Preview
@Composable
fun TitleNavBarPreview(){
    TasklyTheme {
        TitleNavBar(
            title = R.string.categories,
            onGoBack = {},
            showGoBack = true
        )
    }
}