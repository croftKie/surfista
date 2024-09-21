package com.croftk.surfista.utilities.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

	fun getInstance(): Retrofit {
		return Retrofit.Builder().baseUrl("https://marine-api.open-meteo.com/v1/")
			.addConverterFactory(GsonConverterFactory.create())
			// we need to add converter factory to
			// convert JSON object to Java object
			.build()
	}
}