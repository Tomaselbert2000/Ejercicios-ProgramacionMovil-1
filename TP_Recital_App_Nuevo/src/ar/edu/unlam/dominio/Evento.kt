package ar.edu.unlam.dominio

data class Evento (
    val eventId : Long,
    var date : String, // en formato "yyyy-MM-dd"
    var time : String, // enf formato "hh:mm"
    var location : String, // ubicacion del evento
    var artist : String,
    var image : String,
    var availableSeats : Int
){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Evento

        return eventId == other.eventId
    }

    override fun hashCode(): Int {
        return eventId.hashCode()
    }
}