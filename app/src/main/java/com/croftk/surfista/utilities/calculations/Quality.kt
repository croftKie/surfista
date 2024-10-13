package com.croftk.surfista.utilities.calculations

import kotlin.math.abs

object Quality {
	fun ByBoardSize(size: Int): Float {
		val sizeFloat = size.toFloat()
		return ((100 + sizeFloat) - 100) / 100
	}
	fun ByWindDirection(windDirect: Int, waveDirect: Int): Int{
		val diff = abs(windDirect - waveDirect) / 2
		return (diff / 180) / 100
	}
	fun ByWaveSize(waveHeight: Float, preferredWaveSize: Int): Double{
		val default = 50.0
		val diff = preferredWaveSize - waveHeight
		if (diff < 0){
			return default - (default * diff / 10)
		} else if (diff > 0){
			return default * (1 + diff / 10)
		} else {
			return default
		}
	}
	fun getScore(waveHeight: Float, preferredWaveSize: Int, windDirect: Int, waveDirect: Int, size: Int): Double{
		val defaultScore = ByWaveSize(waveHeight, preferredWaveSize)
		val windAdjustedScore = defaultScore * ByWindDirection(windDirect, waveDirect)
		val boardAdjustedScore = windAdjustedScore * ByBoardSize(size)
		return boardAdjustedScore
	}
}