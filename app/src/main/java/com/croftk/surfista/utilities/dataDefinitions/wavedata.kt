package com.croftk.surfista.utilities.dataDefinitions

import com.fasterxml.jackson.annotation.JsonProperty

data class wavedata(
	@JsonProperty("latitude") val latitude: Float,
	@JsonProperty("longitude") val longitude: Float,
	@JsonProperty("generationtime_ms") val generationtime: Float,
	@JsonProperty("utc_offset_seconds") val utc_offset_seconds: Int,
	@JsonProperty("timezone") val timezone: String,
	@JsonProperty("timezone_abbreviation") val timezone_abbrv: String,
	@JsonProperty("elevation") val elevation: Int,
	@JsonProperty("hourly_units") val hourly_units: Map<String, String>,
	@JsonProperty("hourly") val hourly: Map<String, Array<Any>>
)