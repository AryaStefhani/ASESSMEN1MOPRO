package com.aryastefhani0140.miniproject1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aryastefhani0140.miniproject1.R
import com.aryastefhani0140.miniproject1.ui.theme.Miniproject1Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ){
            innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var amount by remember { mutableStateOf("") }
    var selectedFromCurrency by remember { mutableStateOf("") }
    var selectedToCurrency by remember { mutableStateOf("") }
    val currencyOptions = listOf("AUD", "CNY", "EUR", "IDR", "USD", "JPY", "KRW", "SGD")

    var isFromExpanded by remember { mutableStateOf(false) }
    var isToExpanded by remember { mutableStateOf(false) }

    Column (
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
   ){
        Text(text = stringResource(R.string.app_description))

        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(stringResource(R.string.jumlah_uang)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenuBox(
            expanded = isFromExpanded,
            onExpandedChange = { isFromExpanded = it }
        ) {
            TextField(
                value = selectedFromCurrency,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.mata_uang_asal)) },
                modifier = Modifier.fillMaxWidth().clickable { isFromExpanded = true }
            )
            ExposedDropdownMenu(
                expanded = isFromExpanded,
                onDismissRequest = { isFromExpanded = false }
            ) {
                currencyOptions.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency) },
                        onClick = {
                            selectedFromCurrency = currency
                            isFromExpanded = false
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = isToExpanded,
            onExpandedChange = { isToExpanded = it }
        ) {
            TextField(
                value = selectedToCurrency,
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.mata_uang_tujuan)) },
                modifier = Modifier.fillMaxWidth().clickable { isToExpanded = true }
            )
            ExposedDropdownMenu(
                expanded = isToExpanded,
                onDismissRequest = { isToExpanded = false }
            ) {
                currencyOptions.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency) },
                        onClick = {
                            selectedToCurrency = currency
                            isToExpanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview( uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Miniproject1Theme {
        MainScreen()
    }
}