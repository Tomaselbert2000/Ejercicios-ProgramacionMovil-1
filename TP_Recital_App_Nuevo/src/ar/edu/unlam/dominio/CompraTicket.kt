package ar.edu.unlam.dominio

class CompraTicket (
    val id: Long,
    val userId: Long,
    var ticketCollection : MutableList<Long> // listado de IDs de los tickets comprados por el usuario
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CompraTicket

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}