package uk.ac.tees.mad.cc.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import uk.ac.tees.mad.cc.AppViewModel
import uk.ac.tees.mad.cc.ui.theme.poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun History(navController: NavHostController, vm: AppViewModel) {
    val history = vm.currencyHistory.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft, contentDescription = "back",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .size(30.dp)
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Text(
                        text = "Conversion History",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            })
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn {
                items(history.value) { item ->
                    historyView(
                        fromCurrency = item.fromCurrency,
                        toCurrency = item.toCurrency,
                        amount = item.amount.toString(),
                        result = item.result.toString(),
                        time = item.date,
                        onDelete = {
                            vm.deleteHistory(item)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun historyView(
    fromCurrency: String,
    toCurrency: String,
    amount: String,
    result: String,
    time: String,
    onDelete: () -> Unit
) {
    val gradientBrush = Brush.verticalGradient(
        colors = listOf(Color.Blue, Color.Cyan)
    )

    // State for swipeable gesture
    val swipeableState = rememberSwipeableState(0)
    val swipeableAnchors = mapOf(0f to 0, -300f to 1) // Adjust -300f based on the swipe distance you want

    // Detect swipe completion to trigger deletion
    LaunchedEffect(swipeableState.offset.value) {
        if (swipeableState.offset.value < -300f) { // Adjust -300f based on the swipe distance you want
            onDelete()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .graphicsLayer(translationX = swipeableState.offset.value)
            .swipeable(
                state = swipeableState,
                anchors = swipeableAnchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            ),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        shape = CardDefaults.shape
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradientBrush)
            ) {
                Column(Modifier.padding(4.dp)) {
                    Text(
                        text = "From : $fromCurrency",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        color = Color.White
                    )
                    Text(
                        text = amount,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(Modifier.padding(4.dp)) {
                    Text(
                        text = "To : $toCurrency",
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        color = Color.White
                    )
                    Text(
                        text = result,
                        fontFamily = poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp
                    )
                }
            }
            Text(text = "Time : $time", modifier = Modifier.padding(4.dp), fontWeight = FontWeight.SemiBold)
        }
    }
}
