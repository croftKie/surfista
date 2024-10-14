package com.croftk.surfista.utilities.httpInterfaces

import com.croftk.surfista.utilities.dataDefinitions.Tempdata
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TempApi {
	@GET("forecast")
	suspend fun getTempData(
		@Query("latitude") latitude: String,
		@Query("longitude") longitude: String,
		@Query("hourly") hourly: String = "apparent_temperature,rain,cloud_cover"
	): Response<Tempdata>
}