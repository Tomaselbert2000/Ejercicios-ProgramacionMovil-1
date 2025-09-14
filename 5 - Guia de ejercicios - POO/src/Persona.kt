data class Persona(private val nombre:String,
                   private var edad:Int,
                   private val dni : Long,
                   private val genero : Char,
                   private var peso : Double,
                   private var altura: Double){

    fun calcularIMC () : Int {
        val imc = (peso/(altura*altura))
        return when{
            imc < 20.0 -> -1
            imc in 20.0..25.0 -> 0
            else -> 1
        }
    }

    // aca devolvemos el resultado de comparar la edad con 18, que sera true o false
    fun esMayorDeEdad() : Boolean {
        return edad > 18
    }
}