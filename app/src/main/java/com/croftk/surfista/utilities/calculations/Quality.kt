package com.croftk.surfista.utilities.calculations

import kotlin.math.abs

object Quality {
	fun ByBoardSize(size: Float): Float {
		val sizeFloat = size.toFloat()
		val DEFAULTSIZE = 7.0f
		val OFFSET = 1.5f

		if (sizeFloat < DEFAULTSIZE){
			return 1.0f
		} else {
			val adjustment = (sizeFloat - DEFAULTSIZE) * OFFSET
			return ( adjustment / 10) + 1
		}
	}
	fun ByWindDirection(windDirect: Float, waveDirect: Float): Double{
		val OFFSET = 0.8

		val diff = abs(windDirect - waveDirect) / 2
		return (diff / 180.0) + OFFSET
	}
	fun ByWaveSize(waveHeight: Float, preferredWaveSize: Float): Double{
		val DEFAULT = 50.0
		val diff = (waveHeight - preferredWaveSize) * 10
		if (diff < 0){
			val adjustment = DEFAULT * diff / 10
			return DEFAULT + adjustment
		} else if (diff > 0){
			return DEFAULT * (1 + diff / 10)
		} else {
			return DEFAULT
		}
	}
	fun getScore(waveHeight: Float, preferredWaveSize: Float, windDirect: Float, waveDirect: Float, size: Float): Int{
		val defaultScore = ByWaveSize(waveHeight, preferredWaveSize)

		val windAdjustedScore = defaultScore * ByWindDirection(windDirect, waveDirect)


		val boardAdjustedScore = windAdjustedScore * ByBoardSize(size)

		if(boardAdjustedScore < 33){
			return 1
		} else if (boardAdjustedScore > 66) {
			return 3
		} else {
			return 2
		}
	}
}