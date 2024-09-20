package com.croftk.surfista.utilities.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
	private const val BASE_URL = ""

	val okHttpClient = OkHttpClient()
		.newBuilder()
		.addInterceptor(RequestInterceptor)
		.build()

	fun getClient(): Retrofit =
		Retrofit.Builder()
			.client(okHttpClient)
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()

}

object RequestInterceptor : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val request = chain.request()
		println("Outgoing request to ${request.url()}")
		return chain.proceed(request)
	}
}