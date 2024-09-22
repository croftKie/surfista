package com.croftk.surfista.utilities

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
}