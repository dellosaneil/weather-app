package com.thelazybattley.domain.usecase.impl

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.thelazybattley.domain.usecase.SearchAddressList
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class SearchAddressListImpl @Inject constructor(
    @ApplicationContext context: Context
) : SearchAddressList {

    private val geocoder = Geocoder(context, Locale.getDefault())

    override suspend fun invoke(name: String, maxResult: Int): Result<List<Address>> {
        return try {
            geocoder.getFromLocationName(name, maxResult).orEmpty().run {
                Result.success(this)
            }
        } catch (e: Exception) {
            Result.failure(exception = e)
        }
    }
}
