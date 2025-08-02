@file:OptIn(ExperimentalMaterial3Api::class)

package com.yehorsk.taskly.core.presentation.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
    showGoBack: Boolean = true,
    actions: @Composable() () -> Unit = {}
){
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
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
        },
        actions = {
            actions()
        },
        navigationIcon = {
            if (showGoBack) {
                Icon(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(40.dp)
                        .clickable { onGoBack() },
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Preview(
    backgroundColor = 0xFFFFFFFF,
    showBackground = true
)
@Composable
fun TitleNavBarPreview(){
    TasklyTheme {
        Column(
            modifier = Modifier
                .height(200.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleNavBar(
                title = R.string.categories,
                onGoBack = {},
                showGoBack = true,
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Category,
                            contentDescription = "Localized description"
                        )
                    }
                }
            )
        }
    }
}