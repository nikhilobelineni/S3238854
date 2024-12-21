package uk.ac.tees.mad.cc.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun Result(
    vm: AppViewModel,
    navController: NavHostController,
    currency: String?,
    fromCurrency: String?,
) {

    var currentCurrency by remember {
        mutableStateOf(fromCurrency)
    }
    var secondCurrency by remember {
        mutableStateOf("USD")
    }

    val showList = remember {
        mutableStateOf(false)
    }
    val showList2 = remember {
        mutableStateOf(false)
    }

    var price by remember {
        mutableStateOf(currency ?: "")
    }
    var result by remember {
        mutableStateOf("0")
    }

    val scroll = rememberScrollState()

    LaunchedEffect(key1 = price) {
        val amount = price.toDoubleOrNull()
        if (amount != null && currentCurrency != null) {
            val convertedValue = vm.convertCurrency(amount, currentCurrency!!, secondCurrency)
            result = "%.2f".format(convertedValue)
        } else {
            result = "Invalid input"
        }
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0201D4))
                .verticalScroll(scroll)
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Column(Modifier.padding(32.dp)) {
                Text(
                    text = "Conversion Result",
                    fontFamily = poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 24.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = currentCurrency!!,
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    modifier = Modifier.clickable {
                        showList.value = true
                    },
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Icon(
                        painter = painterResource(
                            when (currentCurrency) {
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
                            }
                        ),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                showList.value = true
                            }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TextField(
                        value = price, onValueChange = { price = it },
                        colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            color = Color.White,
                            fontSize = 60.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Icon(
                    painter = painterResource(id = R.drawable.swap),
                    contentDescription = "swap",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(40.dp)
                        .clickable {
                            val tempCurrency = currentCurrency
                            currentCurrency = secondCurrency
                            secondCurrency = tempCurrency!!

                            val amount = price.toDoubleOrNull()
                            if (amount != null && currentCurrency != null) {
                                val convertedValue =
                                    vm.convertCurrency(amount, currentCurrency!!, secondCurrency)
                                result = "%.2f".format(convertedValue)
                            }
                        }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = secondCurrency,
                    fontFamily = poppins,
                    fontSize = 20.sp,
                    modifier = Modifier.clickable {
                        showList2.value = true
                    },
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Icon(
                        painter = painterResource(
                            when (secondCurrency) {
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
                            }
                        ),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                showList2.value = true
                            }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TextField(
                        value = result, onValueChange = { result = it },
                        colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                            containerColor = Color.Transparent,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White
                        ),
                        textStyle = androidx.compose.ui.text.TextStyle(
                            color = Color.White,
                            fontSize = 60.sp,
                            fontFamily = poppins,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
            Button(onClick = { /*TODO*/ },
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Save to Device", color = Color.Black)
            }
        }
        if (showList.value) {
            showList2.value = false
            AlertDialog(onDismissRequest = { showList.value = false }) {
                Card {
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
        if (showList2.value) {
            showList.value = false
            AlertDialog(onDismissRequest = { showList2.value = false }) {
                Card {
                    Column {
                        for (item in currencyList) {
                            Row(modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    when (item.name) {
                                        "United States Dollar" -> {
                                            secondCurrency = "USD"
                                        }

                                        "United Kingdom Pound" -> {
                                            secondCurrency = "GBP"
                                        }

                                        "Euro" -> {
                                            secondCurrency = "EUR"
                                        }

                                        "Japanese Yen" -> {
                                            secondCurrency = "JPY"
                                        }

                                        "Australian Dollar" -> {
                                            secondCurrency = "AUD"
                                        }

                                        "Canadian Dollar" -> {
                                            secondCurrency = "CAD"
                                        }

                                        "Swiss Franc" -> {
                                            secondCurrency = "CHF"
                                        }

                                        "Chinese Yuan" -> {
                                            secondCurrency = "CNY"
                                        }

                                        "Indian Rupee" -> {
                                            secondCurrency = "INR"
                                        }

                                        "Brazilian Real" -> {
                                            secondCurrency = "BRL"
                                        }

                                        else -> {
                                            secondCurrency = "INR"
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
}