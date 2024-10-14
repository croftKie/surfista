package com.croftk.surfista.utilities.dataDefinitions

import com.fasterxml.jackson.annotation.JsonProperty

data class HourlyWavedata(
	@JsonProperty("time") val time: List<String>,
	@JsonProperty("wave_height") val wave_height: List<Float>,
	@JsonProperty("wave_direction") val wave_direction: List<Int>,
	@JsonProperty("wave_period") val wave_period: List<Float>
)
data class HourlyTempdata(
	@JsonProperty("time") val time: List<String>,
	@JsonProperty("apparent_temperature") val apparent_temperature: List<Any>,
	@JsonProperty("rain") val rain: List<Float>,
	@JsonProperty("cloud_cover") val cloud_cover: List<Int>
)
data class HourlyWinddata(
	@JsonProperty("time") val time: List<String>,
	@JsonProperty("wind_speed_10m") val wind_speed_10m: List<Any>,
	@JsonProperty("visibility") val visibility: List<Float>,
	@JsonProperty("wind_direction_10m") val wind_direction_10m: List<Int>
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
	@JsonProperty("hourly") val hourly: HourlyWavedata
)
data class Tempdata(
	@JsonProperty("latitude") val latitude: Float,
	@JsonProperty("longitude") val longitude: Float,
	@JsonProperty("generationtime_ms") val generationtime: Float,
	@JsonProperty("utc_offset_seconds") val utc_offset_seconds: Int,
	@JsonProperty("timezone") val timezone: String,
	@JsonProperty("timezone_abbreviation") val timezone_abbr: String,
	@JsonProperty("elevation") val elevation: Int,
	@JsonProperty("hourly_units") val hourly_units: Map<String, String>,
	@JsonProperty("hourly") val hourly: HourlyTempdata
)
data class Winddata(
	@JsonProperty("latitude") val latitude: Float,
	@JsonProperty("longitude") val longitude: Float,
	@JsonProperty("generationtime_ms") val generationtime: Float,
	@JsonProperty("utc_offset_seconds") val utc_offset_seconds: Int,
	@JsonProperty("timezone") val timezone: String,
	@JsonProperty("timezone_abbreviation") val timezone_abbr: String,
	@JsonProperty("elevation") val elevation: Int,
	@JsonProperty("hourly_units") val hourly_units: Map<String, String>,
	@JsonProperty("hourly") val hourly: HourlyWinddata
)