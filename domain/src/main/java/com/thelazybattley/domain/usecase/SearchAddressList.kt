package com.thelazybattley.domain.usecase

import android.location.Address

interface SearchAddressList {

    suspend operator fun invoke(name: String, maxResult: Int) : Result<List<Address>>

}
