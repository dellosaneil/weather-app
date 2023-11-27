package com.thelazybattley.data.di

import com.thelazybattley.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val originalUrl = original.url
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter(name = "appid", value = BuildConfig.API_KEY)
            .build()
        val requestBuilder = original.newBuilder()
            .url(url = newUrl)
        val request = requestBuilder.build()
        return chain.proceed(request = request)
    }
}
