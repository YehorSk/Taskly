package com.yehorsk.taskly.todos.presentation.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yehorsk.taskly.todos.domain.models.CategorySummary
import com.yehorsk.taskly.ui.theme.TasklyTheme

@Composable
fun CategoryFilter(
    modifier: Modifier = Modifier,
    categories: List<CategorySummary>,
    selectedCategorySummary: List<CategorySummary>,
    onCategoryClicked: (CategorySummary) -> Unit
){
    LazyRow(
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(
            items = categories, key = { it.id }
        ){ item ->
            Card(
                modifier = Modifier
                    .width(IntrinsicSize.Min)
                    .clickable{ onCategoryClicked(item) },
                colors = CardDefaults.cardColors(
                    containerColor = if(item in selectedCategorySummary){
                                        MaterialTheme.colorScheme.primaryContainer
                                    }else{
                                        Color.LightGray
                                    }
                )
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = item.title
                )
            }
        }
    }
}

@Preview
@Composable
fun CategoryFilterPreview(){
    TasklyTheme {
        val fakeCategories = List(10){ index ->
            CategorySummary(id = index, title = "Category$index")
        }
        CategoryFilter(
            categories = fakeCategories,
            selectedCategorySummary = listOf(fakeCategories[3], fakeCategories[0])
        ) { }
    }
}