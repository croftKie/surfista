package com.croftk.surfista.utilities.httpServices

import com.croftk.surfista.utilities.dataDefinitions.Wavedata
import com.croftk.surfista.utilities.httpInterfaces.WaveApi
import com.croftk.surfista.utilities.retrofit.RetrofitInstance

object WaveServices {

	private fun WaveApi(): WaveApi{
		return RetrofitInstance.getInstance().create(WaveApi::class.java)
	}

	suspend fun fetchWaveData(): Wavedata? {
		val result = WaveApi().getWaveData()
		return result.body()
	}
}