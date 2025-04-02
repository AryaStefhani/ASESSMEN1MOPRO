package com.aryastefhani0140.miniproject1.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
    var result by remember { mutableStateOf("") } // Add this line
    var showResult by remember { mutableStateOf(false) } // Add this line
    val currencyOptions = listOf("AUD", "CNY", "EUR", "IDR", "USD", "JPY", "KRW", "SGD")
    var isFromExpanded by remember { mutableStateOf(false) }
    var isToExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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

        Button(
            onClick = {
                if (amount.isNotEmpty() && selectedFromCurrency.isNotEmpty() && selectedToCurrency.isNotEmpty()) {
                    val convertedAmount = convertCurrency(amount.toDoubleOrNull() ?: 0.0, selectedFromCurrency, selectedToCurrency)
                    result = "$amount $selectedFromCurrency = $convertedAmount $selectedToCurrency"
                    showResult = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.konversi))
        }
        if (showResult) {
            Text(result, style = MaterialTheme.typography.titleLarge)
        }

    }
}


private fun convertCurrency(amount: Double, from: String, to: String): Double {
    val rates = mapOf(
        "AUD" to mapOf(
            "CNY" to 4.55, "EUR" to 0.58, "IDR" to 10.398, "JPY" to 94.0,
            "KRW" to 924.0, "SGD" to 0.84, "USD" to 0.63
        ),
        "CNY" to mapOf(
            "AUD" to 0.22, "EUR" to 0.13, "IDR" to 2.283, "JPY" to 21.0,
            "KRW" to 203.0, "SGD" to 0.18, "USD" to 0.14
        ),
        "EUR" to mapOf(
            "AUD" to 1.73, "CNY" to 7.86, "IDR" to 17897.20, "JPY" to 162.0,
            "KRW" to 1594.0, "SGD" to 1.45, "USD" to 1.45
        ),
        "IDR" to mapOf(
            "AUD" to 0.10, "CNY" to 0.44, "EUR" to 0.06, "JPY" to 9.0,
            "KRW" to 89.0, "SGD" to 0.08, "USD" to 0.06
        ),
        "JPY" to mapOf(
            "AUD" to 0.01073, "CNY" to 0.04861, "EUR" to 0.006198, "IDR" to 111.01,
            "KRW" to 9885.0, "SGD" to 0.008998, "USD" to 0.006703
        ),
        "KRW" to mapOf(
            "AUD" to 0.001085, "CNY" to 0.004918, "EUR" to 0.0006269, "IDR" to 11.23,
            "JPY" to 0.1011, "SGD" to 0.0009103, "USD" to 0.0006780
        ),
        "SGD" to mapOf(
            "AUD" to 1.19, "CNY" to 5.40, "EUR" to 0.69, "IDR" to 12332.0,
            "JPY" to 111.0, "KRW" to 1098.0, "USD" to 0.74
        ),
        "USD" to mapOf(
            "AUD" to 1.60, "CNY" to 7.25, "EUR" to 0.93, "IDR" to 16560.0,
            "JPY" to 149.0, "KRW" to 1475.0, "SGD" to 1.34
        )
    )

    return rates[from]?.get(to)?.let { amount * it } ?: amount
}

@Preview(showBackground = true)
@Preview( uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Miniproject1Theme {
        MainScreen()
    }
}