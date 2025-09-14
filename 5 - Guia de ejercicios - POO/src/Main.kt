import Calculadora
import java.time.LocalDate

fun main(){

    // para el ejercicio 1, creamos algunas instancias de la clase cancion
    val cancion1 = Cancion("Blinded in Chains", "Avenged Sevenfold", LocalDate.of(2005, 6, 6), 63328936)
    val cancion2 = Cancion("Homeroom", "Steve Everett", LocalDate.of(2021, 4, 30), 10072)
    val cancion3 = Cancion("Stand!", "Junior Prom", LocalDate.of(2021, 7, 8), 2959938)

    val listaCanciones = mutableListOf<Cancion>(cancion1, cancion2, cancion3)
    for (cancion in listaCanciones){
        println("Informacion de la cancion: ${cancion.mostrarInformacionDeCancion()}, ¿la cancion se considera popular? ${cancion.retornarSiEsPopular()}")
    }

    // modificamos la cancion 2 para que deje de ser popular
    cancion2.setearReproducciones(999)

    println("------------------------------------------------------------------------------")

    // y ahora volvemos a mostrar la lista para verificar los cambios
    for (cancion in listaCanciones){
        println("Informacion de la cancion: ${cancion.mostrarInformacionDeCancion()}, ¿la cancion se considera popular? ${cancion.retornarSiEsPopular()}")
    }

    println("------------------------------------------------------------------------------")
    // para el ejercicio 2 creamos instancias de cuentas
    val cuentaCorriente1 = Cuenta("Tomas Gabriel Elbert", 250000.0)
    val cuentaCorriente2 = Cuenta("Lucas Martinez")

    val listaDeCuentas = mutableListOf<Cuenta>(cuentaCorriente1, cuentaCorriente2)
    println("+--- Informacion de cuentas corrientes registradas ---+")
    for (cuenta in listaDeCuentas){
        println(cuenta.informacionDeCuenta())
        println("---------------------------------")
    }

    // agregamos dinero en ambas cuentas aca
    cuentaCorriente1.agregarCantidad(1000.0)
    cuentaCorriente2.agregarCantidad(0.0) // probamos que pasa si queremos ingresar un valor 0

    println("+--- Informacion de cuentas actualizada ---+")
    for (cuenta in listaDeCuentas){
        println(cuenta.informacionDeCuenta())
        println("---------------------------------")
    }

    cuentaCorriente1.retirarDinero(14500.0) // ejecutamos un retiro
    cuentaCorriente2.retirarDinero(14500.0) // vemos que pasa si queremos retirar de esta cuenta

    println("+--- Informacion de cuentas actualizada ---+")
    for (cuenta in listaDeCuentas){
        println(cuenta.informacionDeCuenta())
        println("---------------------------------")
    }

    println("------------------------------------------------------------------------------")
    // creamos una instancia de la clase Persona para el ejercicio 3
    val persona1 = Persona("Tomas", 24, 12345678, 'H', 80.5, 1.75 )
    println("Mostramos el resultado del toString() de la data class Persona: ${persona1.toString()}")

    println("------------------------------------------------------------------------------")
    // probamos el objeto del ejercicio 4, directamente lo llamamos, no hay que instanciarlo
    println("+--- Calculos realizados con el objeto Calculadora ---+")
    println("12 + 31 = " + Calculadora.sumar(12, 31))
    println("144-90 = " + Calculadora.restar(144, 90))
    println("1785 * 211 = " + Calculadora.producto(1785, 211))
    println("458.6 / 83.1 = " + Calculadora.division(458.6, 83.1))
    println("45 / 0.0 = " + Calculadora.division(45.0, 0.0)) // verificamos que aca devuelve null

    println("------------------------------------------------------------------------------")
    // probamos la clase cafetera del ejercicio 5
    val cafetera = Cafetera(1200.0, 0.0)

    cafetera.llenarCafetera() // llenamos la cafetera
    println("Luego de agregar una taza, la cafetera tiene ${cafetera.cantidadActual}")

    cafetera.servirTaza(3000.0) // probamos llenar más de lo que hay
    println("Luego de servir una taza, la cafetera tiene ${cafetera.cantidadActual}")

    cafetera.agregarCafe(1000.0) // llenamos buena parte de la cafetera
    println("Luego de prepara cafe, en la cafetera tenemos ${cafetera.cantidadActual}")

    cafetera.vaciarCafetera() // la vaciamos
    println("Una vez que tomamos todo el cafe, la cafetera le queda ${cafetera.cantidadActual}")

    // vemos que pasa si queremos llenar mas de lo que carga la cafetera
    cafetera.agregarCafe(2000.0)
    println("Despues de llenar la cafetera con 2000.0 cc, al final tenemos ${cafetera.cantidadActual}")

    // y probamos que pasa si queremos llenar una taza ahora
    cafetera.servirTaza(500.0)
    println("Despues de llenar una taza, nos quedan ${cafetera.cantidadActual}")
}
