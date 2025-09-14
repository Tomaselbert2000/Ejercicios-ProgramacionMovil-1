class Cuenta (val titularCuenta: String, var cantidad : Double = 0.0){ // definimos que por default la cantidad sea 0.0

    fun agregarCantidad(cantidad: Double){
        if(cantidad > 0) {
            this.cantidad += cantidad
        }
        else{
            println("La cantidad a añadir no puede ser menor o igual a cero.")
        }
    }

    fun retirarDinero(cantidadRetiro: Double){
        if(this.cantidad - cantidadRetiro >= 0.0){
            this.cantidad -= cantidadRetiro
        }else{
            this.cantidad = 0.0
        }
    }
    // aca directamente creamos una funcion compacta, en una sola linea, asignando con el = al String que queremos devolver
    fun informacionDeCuenta(): String = "Nombre de titular: ${this.titularCuenta}\nFondos actuales $: ${this.cantidad}"
}