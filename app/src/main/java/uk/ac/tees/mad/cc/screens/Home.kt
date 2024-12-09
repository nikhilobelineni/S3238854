package uk.ac.tees.mad.cc.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.cc.AppViewModel
import uk.ac.tees.mad.cc.currencyList
import uk.ac.tees.mad.cc.ui.theme.poppins

@Composable
fun Home(vm: AppViewModel, navController: NavHostController) {
    vm.fetchLatestRates()
    val rates by vm.currencyRates.collectAsState()
    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("INR") }
    var toCurrency by remember { mutableStateOf("USD") }
    var convertedAmount by remember { mutableStateOf<Double?>(null) }

    Scaffold { iv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(iv)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = "Hello",
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                androidx.compose.material3.Icon(
                    imageVector = Icons.Rounded.Person, contentDescription = "person",
                    modifier = Modifier.size(40.dp)
                )

            }
            Text(
                text = "Currency Converter",
                fontFamily = poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 25.sp,
                modifier = Modifier.padding(8.dp)
            )
            LazyColumn(modifier = Modifier.padding(4.dp)) {
                items(currencyList) { item ->
                    Row(modifier = Modifier
                        .padding(4.dp)
                        .clickable {
                            when (item.name) {
                                "United States Dollar" -> {
                                    fromCurrency = "USD"
                                }

                                "United Kingdom Pound" -> {
                                    fromCurrency = "GBP"
                                }

                                "Euro" -> {
                                    fromCurrency = "EUR"
                                }

                                "Japanese Yen" -> {
                                    fromCurrency = "JPY"
                                }

                                "Australian Dollar" -> {
                                    fromCurrency = "AUD"
                                }

                                "Canadian Dollar" -> {
                                    fromCurrency = "CAD"
                                }

                                "Swiss Franc" -> {
                                    fromCurrency = "CHF"
                                }

                                "Chinese Yuan" -> {
                                    fromCurrency = "CNY"
                                }

                                "Indian Rupee" -> {
                                    fromCurrency = "INR"
                                }

                                "Brazilian Real" -> {
                                    fromCurrency = "BRL"
                                }

                                else -> {
                                    fromCurrency = "INR"
                                }
                            }
                        }) {
                        Image(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.name,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = item.name,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                TextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") }
                )
                Button(onClick = {
                    val amountValue = amount.toDoubleOrNull()
                    if (amountValue != null) {
                        convertedAmount = vm.convertCurrency(amountValue, fromCurrency, toCurrency)
                    }
                }) {
                    Text("Convert")
                }
                if (convertedAmount != null) {
                    Text("Converted Value: $convertedAmount $toCurrency")
                } else if (rates == null) {
                    Text("Loading rates...")
                }
            }
        }
    }
}