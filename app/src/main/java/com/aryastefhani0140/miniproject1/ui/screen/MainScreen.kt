package com.aryastefhani0140.miniproject1.ui.screen

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aryastefhani0140.miniproject1.R
import com.aryastefhani0140.miniproject1.Screen
import com.aryastefhani0140.miniproject1.model.Flag
import com.aryastefhani0140.miniproject1.ui.theme.Miniproject1Theme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun CurrencyCarousel(modifier: Modifier = Modifier) {
    val currencyOptions = listOf(
        Flag("USD", R.drawable.usd_1),
        Flag("IDR", R.drawable.idr_1),
        Flag("EUR", R.drawable.eur_1),
        Flag("JPY", R.drawable.jpy_1),
        Flag("AUD", R.drawable.aud_1),
        Flag("CNY", R.drawable.cny_1),
        Flag("KRW", R.drawable.krw_1),
        Flag("SGD", R.drawable.sgd_1)
    )

    var currentCurrencyIndex by remember { mutableIntStateOf(0) }
    val currentCurrency = currencyOptions[currentCurrencyIndex]

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = currentCurrency.imageResId),
            contentDescription = "${currentCurrency.code} currency",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(165.dp)
        )
        Text(
            text = currentCurrency.code,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        Button(
            onClick = {
                currentCurrencyIndex = (currentCurrencyIndex + 1) % currencyOptions.size
            },
            modifier = Modifier.width(200.dp).padding(top = 4.dp)
        ) {
            Text(text = stringResource(R.string.lanjut))
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.About.route) }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = stringResource(R.string.tentang_aplikasi)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ScreenContent(Modifier.padding(innerPadding))
    }
}

@Composable
fun CurrencyItem(flag: Flag) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp) // Jarak sedikit diperbesar
    ) {
        Image(
            painter = painterResource(id = flag.imageResId),
            contentDescription = null,
            modifier = Modifier.size(28.dp).clip(CircleShape)
        )
        Text(
            text = flag.code,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ScreenContent(modifier: Modifier = Modifier) {
    var amount by remember { mutableStateOf("") }
    var selectedFromCurrency by remember { mutableStateOf<Flag?>(null) }
    var selectedToCurrency by remember { mutableStateOf<Flag?>(null) }
    var result by remember { mutableStateOf("") }
    var showResult by remember { mutableStateOf(false) }

    var amountError by remember { mutableStateOf<String?>(null) }
    var fromCurrencyError by remember { mutableStateOf<String?>(null) }
    var toCurrencyError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current

    val errorEmptyAmount = stringResource(R.string.empty_amount)
    val errorInvalidFormat = stringResource(R.string.invalid_format)
    val errorCurrencyFrom = stringResource(R.string.currency_from)
    val errorCurrencyTo = stringResource(R.string.currency_to)

    val currencyOptions = listOf(
        Flag("AUD", R.drawable.flag_aud),
        Flag("CNY", R.drawable.flag_cny),
        Flag("EUR", R.drawable.flag_eur),
        Flag("IDR", R.drawable.flag_idr),
        Flag("USD", R.drawable.flag_usd),
        Flag("JPY", R.drawable.flag_jpy),
        Flag("KRW", R.drawable.flag_krw),
        Flag("SGD", R.drawable.flag_sgd)
    )

    var isFromExpanded by remember { mutableStateOf(false) }
    var isToExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(R.string.app_description),
            modifier = Modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        CurrencyCarousel()
        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = amount,
                    onValueChange = {
                        amountError = null
                        if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                            amount = it
                        }
                    },
                    label = { Text(stringResource(R.string.jumlah_uang)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    isError = amountError != null,
                    supportingText = {
                        amountError?.let {
                            Text(text = it, color = MaterialTheme.colorScheme.error)
                        }
                    }
                )

                Column {
                    Text(
                        text = stringResource(R.string.mata_uang_asal),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { isFromExpanded = true }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                selectedFromCurrency?.let {
                                    Image(
                                        painter = painterResource(id = it.imageResId),
                                        contentDescription = null,
                                        modifier = Modifier.size(28.dp).clip(CircleShape)
                                    )
                                }
                                Text(
                                    text = selectedFromCurrency?.code ?: stringResource(R.string.mata_uang_asal),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Dropdown"
                            )
                        }
                    }

                    if (fromCurrencyError != null) {
                        Text(
                            text = fromCurrencyError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = isFromExpanded,
                        onDismissRequest = { isFromExpanded = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        currencyOptions.forEach { currency ->
                            DropdownMenuItem(
                                text = { CurrencyItem(currency) },
                                onClick = {
                                    selectedFromCurrency = currency
                                    isFromExpanded = false
                                    fromCurrencyError = null
                                }
                            )
                        }
                    }
                }

                Column {
                    Text(
                        text = stringResource(R.string.mata_uang_tujuan),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    OutlinedCard(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { isToExpanded = true }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                selectedToCurrency?.let {
                                    Image(
                                        painter = painterResource(id = it.imageResId),
                                        contentDescription = null,
                                        modifier = Modifier.size(28.dp).clip(CircleShape)
                                    )
                                }
                                Text(
                                    text = selectedToCurrency?.code ?: stringResource(R.string.mata_uang_tujuan),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Dropdown"
                            )
                        }
                    }

                    if (toCurrencyError != null) {
                        Text(
                            text = toCurrencyError!!,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = isToExpanded,
                        onDismissRequest = { isToExpanded = false },
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        currencyOptions.forEach { currency ->
                            DropdownMenuItem(
                                text = { CurrencyItem(currency) },
                                onClick = {
                                    selectedToCurrency = currency
                                    isToExpanded = false
                                    toCurrencyError = null
                                }
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            amountError = null
                            fromCurrencyError = null
                            toCurrencyError = null

                            var isValid = true

                            if (amount.isEmpty()) {
                                amountError = errorEmptyAmount
                                isValid = false
                            }
                            else if (amount.contains(',')) {
                                amountError = errorInvalidFormat
                                isValid = false
                            }

                            if (selectedFromCurrency == null) {
                                fromCurrencyError = errorCurrencyFrom
                                isValid = false
                            }

                            if (selectedToCurrency == null) {
                                toCurrencyError = errorCurrencyTo
                                isValid = false
                            }

                            if (isValid) {
                                val inputAmount = amount.toDoubleOrNull() ?: 0.0
                                val convertedAmount = convertCurrency(
                                    inputAmount,
                                    selectedFromCurrency!!.code,
                                    selectedToCurrency!!.code
                                )

                                val formattedInputAmount = formatCurrencyValue(inputAmount, selectedFromCurrency!!.code)
                                val formattedConvertedAmount = formatCurrencyValue(convertedAmount, selectedToCurrency!!.code)

                                result = "$formattedInputAmount ${selectedFromCurrency!!.code} = $formattedConvertedAmount ${selectedToCurrency!!.code}"
                                showResult = true
                            } else {
                                showResult = false
                            }
                        },
                        modifier = Modifier.width(220.dp).height(48.dp)
                    ) {
                        Text(stringResource(R.string.konversi))
                    }
                }
            }
        }

        if (showResult) {
            Card(
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = result,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = { shareResult(context, result) },
                        modifier = Modifier.width(160.dp).height(48.dp)
                    ) {
                        Text(stringResource(R.string.bagikan))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

private fun formatCurrencyValue(value: Double, currencyCode: String): String {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault())

    if (currencyCode == "IDR" || currencyCode == "KRW" || currencyCode == "JPY") {
        formatter.maximumFractionDigits = 0
    } else {
        formatter.maximumFractionDigits = 2
    }
    return formatter.format(value)
}

private fun convertCurrency(amount: Double, from: String, to: String): Double {
    if (from == to) return amount

    val rates = mapOf(
        "AUD" to mapOf(
            "CNY" to 4.55, "EUR" to 0.58, "IDR" to 10398.0,
            "JPY" to 94.0, "KRW" to 924.0, "SGD" to 0.84, "USD" to 0.63
        ),
        "CNY" to mapOf(
            "AUD" to 0.22, "EUR" to 0.13, "IDR" to 2283.0,
            "JPY" to 21.0, "KRW" to 203.0, "SGD" to 0.18, "USD" to 0.14
        ),
        "EUR" to mapOf(
            "AUD" to 1.73, "CNY" to 7.86, "IDR" to 17897.20,
            "JPY" to 162.0, "KRW" to 1594.0, "SGD" to 1.45, "USD" to 1.45
        ),
        "IDR" to mapOf(
            "AUD" to 0.0001, "CNY" to 0.00044, "EUR" to 0.000056,
            "JPY" to 0.009, "KRW" to 0.089, "SGD" to 0.000081, "USD" to 0.00006
        ),
        "JPY" to mapOf(
            "AUD" to 0.01073, "CNY" to 0.04861, "EUR" to 0.006198,
            "IDR" to 111.01, "KRW" to 9.885, "SGD" to 0.008998, "USD" to 0.006703
        ),
        "KRW" to mapOf(
            "AUD" to 0.001085, "CNY" to 0.004918, "EUR" to 0.0006269,
            "IDR" to 11.23, "JPY" to 0.1011, "SGD" to 0.0009103, "USD" to 0.0006780
        ),
        "SGD" to mapOf(
            "AUD" to 1.19, "CNY" to 5.40, "EUR" to 0.69,
            "IDR" to 12332.0, "JPY" to 111.0, "KRW" to 1098.0, "USD" to 0.74
        ),
        "USD" to mapOf(
            "AUD" to 1.60, "CNY" to 7.25, "EUR" to 0.93,
            "IDR" to 16560.0, "JPY" to 149.0, "KRW" to 1475.0, "SGD" to 1.34
        )
    )

    val directRate = rates[from]?.get(to)
    if (directRate != null) {
        return amount * directRate
    }

    val toUsdRate = rates[from]?.get("USD")
    val fromUsdRate = rates["USD"]?.get(to)

    if (toUsdRate != null && fromUsdRate != null) {
        return amount * toUsdRate * fromUsdRate
    }

    return amount
}

private fun shareResult(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    Miniproject1Theme {
        MainScreen(rememberNavController())
    }
}