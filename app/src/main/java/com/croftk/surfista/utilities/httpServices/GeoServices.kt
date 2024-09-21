package com.croftk.surfista.utilities.httpServices

import com.croftk.surfista.utilities.dataDefinitions.Geodata
import com.croftk.surfista.utilities.httpInterfaces.GeoApi
import com.croftk.surfista.utilities.retrofit.RetrofitInstance

object GeoServices {

	private fun GeoApi(): GeoApi{
		return RetrofitInstance.getInstance().create(GeoApi::class.java)
	}

	suspend fun fetchGeoData(): List<Geodata>? {
		val result = GeoApi().getGeoData()
		return result.body()
	}
}