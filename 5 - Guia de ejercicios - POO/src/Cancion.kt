import java.time.LocalDate

class Cancion (val tituloCancion: String, val nombreArtistaAutor: String, val fechaDePublicacion: LocalDate?, var recuentoReproducciones: Long){

    /*
    Definimos los valores de la clase como val para que sean de tipo inmutable.
    Es decir una cancion no modifica su nombre, artista o fecha de salida una
    vez que la misma fue creada.
    Lo unico que podría modificarse es el contador de reproducciones, a fin de
    poder incrementarlo.
     */
    fun retornarSiEsPopular(): Boolean{
        return this.recuentoReproducciones > 999
    }

    fun mostrarInformacionDeCancion(): String{ // retornamos la cadena compuesta por la concatenacion de variables
        return "${this.tituloCancion}, interpretada por ${this.nombreArtistaAutor}, lanzada en el año ${this.fechaDePublicacion?.year}"
    }

    fun setearReproducciones(cantidadReproducciones: Long){ // aca recibimos un valor y lo seteamos como cantidad de reproducciones
        this.recuentoReproducciones = cantidadReproducciones
    }
}