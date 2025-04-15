package com.example.expensemad411.network

import com.example.expensemad411.models.Currency
import retrofit2.http.GET


interface CurrencyApiService {
    @GET("latest")
    suspend fun getCurrencyRates(): List<Currency>
}