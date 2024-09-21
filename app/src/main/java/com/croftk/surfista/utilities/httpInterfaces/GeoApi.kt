package com.croftk.surfista.utilities.httpInterfaces

import com.croftk.surfista.utilities.dataDefinitions.Geodata
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoApi {
	@GET("search")
	suspend fun getGeoData(
		@Query("q") location: String,
		@Query("api_key") key: String): Response<List<Geodata>>
}