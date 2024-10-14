package com.croftk.surfista.utilities.httpInterfaces

import com.croftk.surfista.utilities.dataDefinitions.Winddata
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WindApi {
	@GET("forecast")
	suspend fun getWindData(
		@Query("latitude") latitude: String,
		@Query("longitude") longitude: String,
		@Query("hourly") hourly: String = "wind_speed_10m,visibility,wind_direction_10m"
	): Response<Winddata>
}