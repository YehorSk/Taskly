package com.yehorsk.taskly.todos.presentation.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Today
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.yehorsk.taskly.R
import com.yehorsk.taskly.core.presentation.components.TitleNavBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainListScreen(
    modifier: Modifier = Modifier,
    viewModel: MainListScreenViewModel = koinViewModel()
){

    val tabItems = listOf(
        TabItem(
            title = stringResource(R.string.today),
            unselectedIcon = Icons.Outlined.Today,
            selectedIcon = Icons.Default.Today
        ),
        TabItem(
            title = stringResource(R.string.month),
            unselectedIcon = Icons.Outlined.CalendarMonth,
            selectedIcon = Icons.Default.CalendarMonth
        )
    )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    val pagerState = rememberPagerState {
        tabItems.size
    }
    LaunchedEffect(selectedTabIndex, pagerState.isScrollInProgress) {
        pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        if(!pagerState.isScrollInProgress){
            selectedTabIndex = pagerState.currentPage
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        TitleNavBar(
            title = R.string.my_tasks,
            onGoBack = {},
            showGoBack = false
        )
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            tabItems.forEachIndexed { index, tabItem ->
                Tab(
                    selected = (index == selectedTabIndex),
                    onClick = {
                        selectedTabIndex = index
                    },
                    text = {
                        Text(
                            text = tabItem.title
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = if(selectedTabIndex == index){
                                tabItem.selectedIcon
                            }else tabItem.unselectedIcon,
                            contentDescription = null
                        )
                    }
                )
            }
        }
        HorizontalPager(
            userScrollEnabled = false,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { index ->

            when(index){
                0 -> {
                    TodayListScreenRoot(
                        modifier = Modifier.fillMaxSize(),
                        viewModel = viewModel,
                        onItemClick = {}
                    )
                }
                1 -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = tabItems[index].title
                        )
                    }
                }
                else -> null
            }
        }
    }
}

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)