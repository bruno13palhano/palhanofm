package com.bruno13palhano.palhanofm.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bruno13palhano.palhanofm.R
import com.bruno13palhano.palhanofm.ui.theme.PalhanoFMTheme

@Composable
fun ExpandedItem(
    modifier: Modifier = Modifier,
    title: String,
    broadcaster: String,
    startTime: String,
    endTime: String
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    ExpandedItemContent(
        modifier = modifier,
        title = title,
        broadcaster = broadcaster,
        startTime = startTime,
        endTime = endTime,
        expanded = expanded,
        onItemClick = { expanded = it }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExpandedItemContent(
    modifier: Modifier,
    expanded: Boolean,
    title: String,
    broadcaster: String,
    startTime: String,
    endTime: String,
    onItemClick: (expanded: Boolean) -> Unit
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { onItemClick(!expanded) },
        colors = if (expanded) {
            CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        } else {
            CardDefaults.cardColors()
        }
    ) {
        Text(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = if(expanded) 0.dp else 16.dp
                ),
            text = title,
            style = MaterialTheme.typography.titleMedium
        )

        AnimatedVisibility(expanded) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.with_tag, broadcaster),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = stringResource(id = R.string.from_tag, startTime),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    text = stringResource(id = R.string.to_tag, endTime),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ExpandedItemDynamicPreview() {
    PalhanoFMTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ExpandedItemContent(
                modifier = Modifier,
                title = "Programa da tarde",
                broadcaster = "Bruno Barbosa",
                startTime = "12:00",
                endTime = "14:59",
                expanded = true,
                onItemClick = {}
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ExpandedItemPreview() {
    PalhanoFMTheme(
        dynamicColor = false
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ExpandedItemContent(
                modifier = Modifier,
                title = "Programa da tarde",
                broadcaster = "Bruno Barbosa",
                startTime = "12:00",
                endTime = "14:59",
                expanded = true,
                onItemClick = {}
            )
        }
    }
}