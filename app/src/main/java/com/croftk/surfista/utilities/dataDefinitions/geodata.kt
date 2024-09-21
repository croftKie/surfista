package com.croftk.surfista.utilities.dataDefinitions

import com.fasterxml.jackson.annotation.JsonProperty

data class Geodata(
	@JsonProperty("place_id") val place_id: Int,
	@JsonProperty("licence") val licence: String,
	@JsonProperty("osm_type") val osm_type: String,
	@JsonProperty("osm_id") val osm_id: Int,
	@JsonProperty("boundingbox") val boundingbox: List<String>,
	@JsonProperty("lat") val lat: String,
	@JsonProperty("lon") val lon: String,
	@JsonProperty("display_name") val display_name: String,
	@JsonProperty("class") val class_prop: String,
	@JsonProperty("type") val type: String,
	@JsonProperty("importance") val importance: String
)