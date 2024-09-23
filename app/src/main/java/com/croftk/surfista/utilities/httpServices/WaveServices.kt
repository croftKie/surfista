package com.croftk.surfista.utilities.httpServices

import com.croftk.surfista.utilities.dataDefinitions.Wavedata
import com.croftk.surfista.utilities.httpInterfaces.WaveApi
import com.croftk.surfista.utilities.retrofit.RetrofitInstance

object WaveServices {

	private fun WaveApi(): WaveApi{
		return RetrofitInstance.getInstance("https://marine-api.open-meteo.com/v1/").create(WaveApi::class.java)
	}

	suspend fun fetchWaveData(latitude: String, longitude: String): Wavedata? {
		val result = WaveApi().getWaveData(latitude, longitude)
		return result.body()
	}
}