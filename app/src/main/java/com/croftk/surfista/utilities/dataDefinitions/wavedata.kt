package com.croftk.surfista.utilities.dataDefinitions

import com.fasterxml.jackson.annotation.JsonProperty

data class Hourlydata(
	@JsonProperty("time") val time: List<String>,
	@JsonProperty("wave_height") val wave_height: List<Float>,
	@JsonProperty("wave_direction") val wave_direction: List<Int>,
	@JsonProperty("wave_period") val wave_period: List<Float>
)

data class Wavedata(
	@JsonProperty("latitude") val latitude: Float,
	@JsonProperty("longitude") val longitude: Float,
	@JsonProperty("generationtime_ms") val generationtime: Float,
	@JsonProperty("utc_offset_seconds") val utc_offset_seconds: Int,
	@JsonProperty("timezone") val timezone: String,
	@JsonProperty("timezone_abbreviation") val timezone_abbr: String,
	@JsonProperty("elevation") val elevation: Int,
	@JsonProperty("hourly_units") val hourly_units: Map<String, String>,
	@JsonProperty("hourly") val hourly: Hourlydata
)