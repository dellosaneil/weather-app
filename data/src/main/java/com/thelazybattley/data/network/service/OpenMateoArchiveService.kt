package com.thelazybattley.data.network.service

import com.thelazybattley.data.network.response.history.HistoryDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMateoArchiveService {
    @GET("archive")
    suspend fun getHistory(
        @Query("latitude") latitude: String,
        @Query("longitude") longitude: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("daily") params: String
    ): HistoryDataResponse
}
