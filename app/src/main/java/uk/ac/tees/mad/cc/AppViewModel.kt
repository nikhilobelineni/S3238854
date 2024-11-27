package uk.ac.tees.mad.cc

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.ac.tees.mad.cc.data.CurrencyApiService
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val currencyApiService: CurrencyApiService
) : ViewModel() {

    init {
        fetchLatestRates()
    }
    fun fetchLatestRates() {
        Log.d("AppViewModel", "Fetching latest rates...")
        viewModelScope.launch {
            try {
                val response = currencyApiService.getLatestRates(
                    apiKey = "cur_live_5sd2N8xnqJyI8ozbrxWkHqrbzHeFvMlSEANNMEtM",
                    currencies = "USD,EUR,GBP,JPY,AUD,CAD,CHF,CNY,INR,BRL"
                )
                Log.d("AppViewModel", "Response: $response")
            } catch (e: Exception) {
                // Handle the error here
                Log.e("AppViewModel", "Error fetching latest rates", e)
            }
        }
    }
}