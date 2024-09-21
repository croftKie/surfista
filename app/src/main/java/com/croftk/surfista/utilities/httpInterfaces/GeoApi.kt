package com.croftk.surfista.utilities.httpInterfaces

import com.croftk.surfista.utilities.dataDefinitions.Geodata
import retrofit2.Response
import retrofit2.http.GET

interface GeoApi {
	@GET("search?q=santander&api_key=66ee978bf18e1248071945bjq69c4d5")
	suspend fun getGeoData(): Response<List<Geodata>>
}