package uk.ac.tees.mad.cc.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uk.ac.tees.mad.cc.model.CurrencyResponse

interface CurrencyApiService {
    @GET("latest")
    suspend fun getLatestRates(
        @Query("apikey") apiKey: String,
        @Query("currencies") currencies: String
    ): CurrencyResponse
}