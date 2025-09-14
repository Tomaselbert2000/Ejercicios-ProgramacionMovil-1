class Cafetera (val capacidadMaxima: Double = 1000.0, var cantidadActual: Double = 0.0){

    fun llenarCafetera(){
        // la cafetera se llena al 100% cuando ambas capacidades son iguales
        this.cantidadActual = this.capacidadMaxima
    }

    fun servirTaza(taza : Double)=
        when {
            this.cantidadActual >= taza -> this.cantidadActual -= taza
            else -> {
                this.cantidadActual = 0.0
                println("No se pudo llenar la taza")
            }

    }

    fun vaciarCafetera(){
        this.cantidadActual = 0.0
    }

    fun agregarCafe(cantidadParaAgregar : Double) = when {
        (this.cantidadActual + cantidadParaAgregar) <= this.capacidadMaxima -> this.cantidadActual += cantidadParaAgregar
        else -> {
            this.cantidadActual = this.capacidadMaxima
        }
    }
}