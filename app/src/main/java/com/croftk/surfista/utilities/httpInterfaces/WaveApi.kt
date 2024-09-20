package com.croftk.surfista.utilities.httpInterfaces

import com.croftk.surfista.utilities.dataDefinitions.wavedata
import retrofit2.Call
import retrofit2.http.GET

interface WaveApi {
	@GET("marine?latitude=54.544587&longitude=10.227487&hourly=wave_height,wave_direction,wave_period")
	fun getWaveData(): Call<List<wavedata>>
}