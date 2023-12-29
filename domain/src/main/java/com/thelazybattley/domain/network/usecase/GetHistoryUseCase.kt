package com.thelazybattley.domain.network.usecase

import com.thelazybattley.domain.network.schema.history.HistoryDataSchema

interface GetHistoryUseCase {

    suspend operator fun invoke(
        latitude: String,
        longitude:String,
        startDate: String,
        endDate: String
    ) : Result<HistoryDataSchema>
}
