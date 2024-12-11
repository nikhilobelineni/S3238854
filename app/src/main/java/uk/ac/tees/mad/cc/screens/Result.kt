package uk.ac.tees.mad.cc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import uk.ac.tees.mad.cc.R
import uk.ac.tees.mad.cc.currencyList
import uk.ac.tees.mad.cc.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Result(vm: AppViewModel, navController: NavHostController, currency: String?) {

    var currentCurrency by remember {
        mutableStateOf("EUR")
    }

    val showList = remember {
        mutableStateOf(false)
    }

    val price = currency

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0201D4))
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Column(Modifier.padding(16.dp)) {
                Text(
                    text = "Result",
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = currentCurrency, fontFamily = poppins, fontSize = 20.sp, modifier = Modifier.clickable {
                    showList.value = true
                }, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Icon(
                        painter = painterResource(when (currentCurrency) {
                            "USD" -> R.drawable.us_dollar
                            "GBP" -> R.drawable.gbp
                            "EUR" -> R.drawable.eurologo
                            "JPY" -> R.drawable.yen
                            "AUD" -> R.drawable.money
                            "CAD" -> R.drawable.canadian_dollar_logo
                            "CHF" -> R.drawable.chf
                            "CNY" -> R.drawable.head
                            "INR" -> R.drawable.rupee
                            "BRL" -> R.drawable.brazilian_real
                            else -> {
                                R.drawable.euro
                            }
                        }),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = currency.toString())
                }
            }
        }
        if (showList.value) {
            AlertDialog(onDismissRequest = { showList.value = false }) {
                Column {
                    for (item in currencyList) {
                        Row(modifier = Modifier
                            .padding(4.dp)
                            .clickable {
                                when (item.name) {
                                    "United States Dollar" -> {
                                        currentCurrency = "USD"
                                    }

                                    "United Kingdom Pound" -> {
                                        currentCurrency = "GBP"
                                    }

                                    "Euro" -> {
                                        currentCurrency = "EUR"
                                    }

                                    "Japanese Yen" -> {
                                        currentCurrency = "JPY"
                                    }

                                    "Australian Dollar" -> {
                                        currentCurrency = "AUD"
                                    }

                                    "Canadian Dollar" -> {
                                        currentCurrency = "CAD"
                                    }

                                    "Swiss Franc" -> {
                                        currentCurrency = "CHF"
                                    }

                                    "Chinese Yuan" -> {
                                        currentCurrency = "CNY"
                                    }

                                    "Indian Rupee" -> {
                                        currentCurrency = "INR"
                                    }

                                    "Brazilian Real" -> {
                                        currentCurrency = "BRL"
                                    }

                                    else -> {
                                        currentCurrency = "INR"
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
            }
        }
    }
}