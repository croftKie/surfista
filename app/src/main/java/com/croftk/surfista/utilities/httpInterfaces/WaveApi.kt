package com.croftk.surfista.utilities.httpInterfaces

import com.croftk.surfista.utilities.dataDefinitions.Wavedata
import retrofit2.Response
import retrofit2.http.GET

interface WaveApi {
	@GET("marine?latitude=54.544587&longitude=10.227487&hourly=wave_height,wave_direction,wave_period")
	suspend fun getWaveData(): Response<Wavedata>
}