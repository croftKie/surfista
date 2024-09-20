package com.croftk.surfista.utilities.httpServices

import com.croftk.surfista.utilities.httpInterfaces.WaveApi
import com.croftk.surfista.utilities.retrofit.RetrofitClient

class WaveService {
	private val retrofit = RetrofitClient.getClient()
	private val waveApi = retrofit.create(WaveApi::class.java)

	fun Response() {
		val usersResponse = waveApi.getWaveData()
			.execute()
		val successful = usersResponse.isSuccessful
		val httpStatusCode = usersResponse.code()
		val httpStatusMessage = usersResponse.message()
	}
}