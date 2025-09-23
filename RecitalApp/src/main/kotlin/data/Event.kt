package main.kotlin.data

data class Event(
    val id: Long,
    var date: String,
    val time: String,
    val location: String,
    val artist: String,
    val image: String
)
{
    override fun toString(): String {
        return "ID de Evento: $id, Fecha: $date', Hora: $time', Ubicacion: $location', Artista invitado: $artist', Imagen: $image')"
    }
}
