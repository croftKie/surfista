package com.croftk.surfista.utilities.httpServices

import com.croftk.surfista.utilities.dataDefinitions.Geodata
import com.croftk.surfista.utilities.httpInterfaces.GeoApi
import com.croftk.surfista.utilities.retrofit.RetrofitInstance

object GeoServices {

	private fun GeoApi(): GeoApi{
		return RetrofitInstance.getInstance("https://geocode.maps.co/").create(GeoApi::class.java)
	}

	suspend fun fetchGeoData(location: String, key: String): List<Geodata>? {
		val result = GeoApi().getGeoData(location, key)
		return result.body()
	}
}