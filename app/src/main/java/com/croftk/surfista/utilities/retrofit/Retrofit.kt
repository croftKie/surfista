package com.croftk.surfista.utilities.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

	fun getInstance(BASE_URL: String): Retrofit {
		return Retrofit.Builder().baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			// we need to add converter factory to
			// convert JSON object to Java object
			.build()
	}
}