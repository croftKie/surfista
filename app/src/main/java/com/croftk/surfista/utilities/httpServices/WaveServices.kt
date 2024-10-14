package com.croftk.surfista.utilities.httpServices

import com.croftk.surfista.utilities.dataDefinitions.Tempdata
import com.croftk.surfista.utilities.dataDefinitions.Wavedata
import com.croftk.surfista.utilities.dataDefinitions.Winddata
import com.croftk.surfista.utilities.httpInterfaces.TempApi
import com.croftk.surfista.utilities.httpInterfaces.WaveApi
import com.croftk.surfista.utilities.httpInterfaces.WindApi
import com.croftk.surfista.utilities.retrofit.RetrofitInstance

object WaveServices {

	private fun WaveApi(): WaveApi{
		return RetrofitInstance.getInstance("https://marine-api.open-meteo.com/v1/").create(WaveApi::class.java)
	}
	private fun TempApi(): TempApi{
		return RetrofitInstance.getInstance("https://api.open-meteo.com/v1/").create(TempApi::class.java)
	}
	private fun WindApi(): WindApi{
		return RetrofitInstance.getInstance("https://api.open-meteo.com/v1/").create(WindApi::class.java)
	}

	suspend fun fetchWaveData(latitude: String, longitude: String): Wavedata? {
		val result = WaveApi().getWaveData(latitude, longitude)
		return result.body()
	}
	suspend fun fetchTempData(latitude: String, longitude: String): Tempdata? {
		val result = TempApi().getTempData(latitude, longitude)
		return result.body()
	}
	suspend fun fetchWindData(latitude: String, longitude: String): Winddata? {
		val result = WindApi().getWindData(latitude, longitude)
		return result.body()
	}
}