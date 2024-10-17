package com.croftk.surfista.utilities

import com.croftk.surfista.db.dao.MarineDao
import com.croftk.surfista.db.dao.TempDao
import com.croftk.surfista.db.dao.WindDao
import com.croftk.surfista.db.entities.GeoLocation
import com.croftk.surfista.db.entities.Marine
import com.croftk.surfista.db.entities.Temperature
import com.croftk.surfista.db.entities.Wind
import com.croftk.surfista.utilities.dataDefinitions.Tempdata
import com.croftk.surfista.utilities.dataDefinitions.Wavedata
import com.croftk.surfista.utilities.dataDefinitions.Winddata

object Helpers {
	fun getAverage(list: String): Float{
		var total = 0.0f
		list.split(",").forEach { it ->
			total += it.toFloat()
		}
		return total / list.split(",").size
	}
	fun cleanGeoAddress(name: String): String{
		val split = name.split(",")
		var cleanedName = ""
		if (split.size > 2){
			val cleaned = listOf<String>(split[0], split[split.size - 2], split[split.size - 1])
			cleanedName = cleaned.toString()
		} else {
			cleanedName = name.toString()
		}
		return cleanedName
	}
	fun storeFetchedData(
		data: Wavedata,
		item: GeoLocation,
		MarineDao: MarineDao,
	){
		val timeChunked = data.hourly.time.chunked(24)
		val whChunked = data.hourly.wave_height.chunked(24)
		val wpChunked = data.hourly.wave_period.chunked(24)
		val wdChunked = data.hourly.wave_direction.chunked(24)

		for (i in 1..timeChunked.size){
			MarineDao.insertMarineData(
				Marine(
				id = timeChunked[i - 1][0].substring(0, 10),
				name = item.name,
				lat = item.lat.toDouble(),
				lon = item.lon.toDouble(),
				time = timeChunked[i - 1].toString()
					.replace("[", "")
					.replace("]", ""),
				wave_height = whChunked[i - 1].toString()
					.replace("[", "")
					.replace("]", ""),
				wave_period = wpChunked[i - 1].toString()
					.replace("[", "")
					.replace("]", ""),
				wave_direction = wdChunked[i - 1].toString()
					.replace("[", "")
					.replace("]", "")
			)
			)
		}
	}
	fun storeFetchedData(
		data: Tempdata,
		item: GeoLocation,
		TempDao: TempDao,
	){
		for (i in 1..data.hourly.time.chunked(24).size){
			TempDao.insertTempData(
				Temperature(
					id = data.hourly.time.chunked(24)[i - 1][0].substring(0, 10),
					name = item.name,
					lat = item.lat.toDouble(),
					lon = item.lon.toDouble(),
					time = data.hourly.time.chunked(24)[i - 1].toString()
						.replace("[", "")
						.replace("]", ""),
					temperature = data.hourly.apparent_temperature.chunked(24)[i - 1].toString()
						.replace("[", "")
						.replace("]", ""),
					rain = data.hourly.rain.chunked(24)[i - 1].toString()
						.replace("[", "")
						.replace("]", ""),
					cloud_cover = data.hourly.cloud_cover.chunked(24)[i - 1].toString()
						.replace("[", "")
						.replace("]", "")
				)
			)
		}
	}
	fun storeFetchedData(
		data: Winddata,
		item: GeoLocation,
		WindDao: WindDao,
	){
		for (i in 1..data.hourly.time.chunked(24).size){
			WindDao.insertWindData(
				Wind(
					id = data.hourly.time.chunked(24)[i - 1][0].substring(0, 10),
					name = item.name,
					lat = item.lat.toDouble(),
					lon = item.lon.toDouble(),
					time = data.hourly.time.chunked(24)[i - 1].toString()
						.replace("[", "")
						.replace("]", ""),
					visibility = data.hourly.visibility.chunked(24)[i - 1].toString()
						.replace("[", "")
						.replace("]", ""),
					wind_speed = data.hourly.wind_speed_10m.chunked(24)[i - 1].toString()
						.replace("[", "")
						.replace("]", ""),
					wind_direction = data.hourly.wind_direction_10m.chunked(24)[i - 1].toString()
						.replace("[", "")
						.replace("]", "")
				)
			)
		}
	}
}