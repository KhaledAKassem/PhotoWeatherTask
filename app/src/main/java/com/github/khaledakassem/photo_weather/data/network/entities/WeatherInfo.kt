package com.github.khaledakassem.photo_weather.data.network.entities


import com.google.gson.annotations.SerializedName

data class WeatherInfo(
    @SerializedName("base")
    val base: String,
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("cod")
    val cod: Double,
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Double,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String,
    @SerializedName("rain")
    val rain: Rain,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Double,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
) {
    data class Clouds(
        @SerializedName("all")
        val all: Double
    )

    data class Coord(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lon")
        val lon: Double
    )

    data class Main(
        @SerializedName("grnd_level")
        val grndLevel: Double,
        @SerializedName("humidity")
        val humidity: Double,
        @SerializedName("pressure")
        val pressure: Double,
        @SerializedName("sea_level")
        val seaLevel: Double,
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("temp_max")
        val tempMax: Double,
        @SerializedName("temp_min")
        val tempMin: Double
    )

    data class Rain(
        @SerializedName("3h")
        val h: Double
    )

    data class Sys(
        @SerializedName("country")
        val country: String,
        @SerializedName("sunrise")
        val sunrise: Double,
        @SerializedName("sunset")
        val sunset: Double
    )

    data class Weather(
        @SerializedName("description")
        val description: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("main")
        val main: String
    )

    data class Wind(
        @SerializedName("deg")
        val deg: Double,
        @SerializedName("speed")
        val speed: Double
    )
}