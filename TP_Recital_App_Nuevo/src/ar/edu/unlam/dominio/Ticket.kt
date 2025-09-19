package ar.edu.unlam.dominio

class Ticket (
    val id: Long,
    val eventId: Long,
    var quantity : Int, // cantidad de tickets comprados
    val section : String, // sector ubicado dentro del estadio
    val precio: Double = 10000.0 // precio de cada asiento indicado en la consigna
){


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ticket

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}