package com.github.khaledakassem.photo_weather.common.extensions

fun <T>List<T>.remove(position: Int)  = this.filter { it != this[position] }