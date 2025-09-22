package main.kotlin.data

data class Event(
    val id: Long,
    var date: String,
    val time: String,
    val location: String,
    val artist: String,
    val image: String
)
