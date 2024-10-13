package com.croftk.surfista.utilities

import com.croftk.surfista.db.dao.MarineDao
import com.croftk.surfista.db.entities.GeoLocation
import com.croftk.surfista.db.entities.Marine
import com.croftk.surfista.utilities.dataDefinitions.Tempdata
import com.croftk.surfista.utilities.dataDefinitions.Wavedata
import com.croftk.surfista.utilities.dataDefinitions.Winddata

object Helpers {
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
		data: Winddata,
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
}