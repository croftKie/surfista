package com.croftk.surfista.utilities.httpInterfaces

import com.croftk.surfista.utilities.dataDefinitions.Wavedata
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WaveApi {
	@GET("marine")
	suspend fun getWaveData(
		@Query("latitude") latitude: String,
		@Query("longitude") longitude: String,
		@Query("hourly") hourly: String = "wave_height,wave_direction,wave_period"
	): Response<Wavedata>
}