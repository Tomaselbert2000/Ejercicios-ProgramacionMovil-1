import java.util.Locale.getDefault
import kotlin.random.Random
import kotlin.random.nextInt

fun main(){

    // 1. Dada una lista de nombres, imprimir todos sus elementos

    // creamos una lista de nombres
    val listaNombres = mutableListOf("Tomas", "Gabriel", "Maxi", "Ariel", "Sebastian", "Ignacio", "Lautaro", "Luciano", "Martin", "Hernan")
    for(i in listaNombres){
        println(i)
    }
    println("---------------------------------------------------------------------------------")

    // 2. Dada una lista de 10 numeros enteros, determinar el promedio de ellos.
    val listaNumeros = mutableListOf<Int>()
    for(i in 1..10){ // cargamos la lista con numeros al azar
        listaNumeros.add(Random.nextInt(1..999))
    }
    println("Dada la siguiente lista de numeros enteros: $listaNumeros")
    var contador = 0
    var acumulador = 0
    for(i in listaNumeros){ // y ahora calculamos el promedio de todos ellos
        contador++
        acumulador+= i
    }
    println("El promedio de todos los valores almacenados en la lista de numeros es: " + (acumulador/contador))
    println("---------------------------------------------------------------------------------")

    /*
    3.
    Un comercio de alimentos desea un programa que calcule las ganancias del día. Realizar
    un programa que lee por consola el precio y la cantidad de unidades vendidas hasta que se
    ingrese el precio 0. Imprimir cada ganancia y determinar las ganancias totales del día.
     */

    val listaGanancias = mutableListOf<Double>() // creamos una lista para ir guardando los resultados
    println("+--- Calculadora de ganancias ---+")
    var precio : Double?
    var cantidadVendida : Int?
    do {
        println("Ingresar cantidad de productos vendidos: ")
        cantidadVendida = readln().toInt()
        println("Ingresar precio unitario: ")
        precio = readln().toDouble()
        listaGanancias.add(cantidadVendida*precio)
        println("La venta ingresada representa una ganancia de $" + (cantidadVendida*precio))
    }while(precio!=0.0)
    var gananciaTotal = 0.0
    for (i in listaGanancias){ // mostramos los resultados
        gananciaTotal+=i
    }
    println("La recaudacion total es $$gananciaTotal")
    println("---------------------------------------------------------------------------------")

    /*
    4.
    Modelar el “Club de los No Homeros”. Se trata de un bar muy particular donde no se
    admiten clientes con el mismo nombre. Ya que “Homero” tiene control sobre los clientes
    que admite, su bar debe permitir registrar nuevos clientes (solo su nombre y apellido), y
    presentar a sus clientes ordenados alfabéticamente.
     */
    val listaClientes = mutableListOf<String>() // creamos una lista para los nombres
    var nombre: String?
    var apellido: String?
    println("+--- Ingresar datos de clientes ---+\nPara finalizar la carga, ingresar un nombre igual a 'Salir'")
    do{
        println("Ingresar nombre: ")
        nombre = readln() // primero pedimos un nombre
        if(nombre.lowercase(getDefault()) == "salir"){ // aca lo pasamos a minusculas
            println("Carga de datos finalizada.")
            break // en caso de ingresar "salir" cortamos directamente la iteracion actual y omitimos lo que sigue
        }else
            println("Ingresar apellido: ") // si se ingresa otro nombre, pedimos el apellido
            apellido = readln()
            listaClientes.add("$nombre $apellido") // concatenamos ambos valores y los guardamos en el map

    }while(nombre != "salir")
    println("Lista de clientes ordenada alfabeticamente: " + listaClientes.sorted()) // mostramos la lista ordenada alfabeticamente
    println("---------------------------------------------------------------------------------")

    /*
    5.
    En un deposito se desea implementar un sistema que permita la gestión de stock de
    productos. Cada producto será representado con un código único, que se utiliza para
    conocer sus unidades disponibles en stock.
    El sistema permitirá: -
        Consultar stock disponible de un producto - -
        Ingreso de nuevo stock de unidades: El usuario deberá ingresar el código
        del producto y la cantidad de unidades nuevas que ingresan. Si el código
        no existe, se agrega el nuevo producto con las unidades indicadas; si el
        código existe, se actualiza el stock.
        Retiro de unidades en stock: El usuario deberá ingresar el código del
        producto y la cantidad de unidades a retirar. El sistema deberá validar y
        actualizar el stock e informar el stock actualizado.
     */
    println("+--- Sistema de gestion de deposito ---+")
    val mapaProductos = mutableMapOf<Int, Int>() // creamos un mapa con claves y valores enteros
    var opcionSeleccionada : Int? // esta variable va representa la opcion que elige el usuario
    do { // abrimos un primer do while para ejecutar el menu

        println("""
        +--- 1. Ingresar productos al sistema. ---+
        |--- 2. Retirar productos del sistema. ---|
        |--- 3. Ver productos en stock actual. ---|
        |--- 4.       Salir del sistema.       ---|
        +--- Seleccionar opcion para continuar ---+
    """)

        opcionSeleccionada = readln().toInt()
        if(opcionSeleccionada !in 1..4){ // evaluamos si la opcion ingresada se sale del rango del menu
            println("El valor ingresado no corresponde con una opcion del menu, intente nuevamente.")
        }

        if(opcionSeleccionada == 1){
            println("## Ingresar codigo de producto ##")
            val codigoProducto = readln().toInt()
            println("## Ingresar cantidad de unidades ##")
            val cantidadIngresada = readln().toInt()
            if(mapaProductos[codigoProducto] != null){ // validamos que no sea null
                mapaProductos[codigoProducto] = mapaProductos[codigoProducto]?.plus(cantidadIngresada) as Int // sumamos y casteamos a Int
            }else if(mapaProductos[codigoProducto] == null){
                mapaProductos[codigoProducto] = cantidadIngresada
            }else
                println("Se produjo un error al procesar la operacion")
        }else if(opcionSeleccionada == 2){
            println("## Ingresar el codigo de producto a retirar ##")
            val codigoProducto = readln().toInt()
            println("## Cantidad de unidades a retirar ##")
            val cantidadRetiro = readln().toInt()

            if(mapaProductos[codigoProducto] != null){ // validamos que el objeto no sea nulo
                if(mapaProductos[codigoProducto]!! >= cantidadRetiro){ // luego verificamos que el stock sea igual o mayor a la cantidad ingresada
                    mapaProductos[codigoProducto] = mapaProductos[codigoProducto]?.minus(cantidadRetiro) as Int // si lo es, descontamos la cantidad
                }else
                    println("La cantidad ingresada supera al stock actual registrado.")
            }else
                println("Se produjo un error al procesar la operacion")
        }else if(opcionSeleccionada == 3){
            if(mapaProductos.isNotEmpty()){ // con isNotEmpty validamos si el mapa fue cargado o no (si el size() es dsitinto a 0)
                for (producto in mapaProductos){
                    println("Codigo de producto: ${producto.key}")
                    println("Stock actual: ${producto.value}")
                    println("-------------------------------")
                }
            }else
                println("+--- No hay productos cargados en el sistema por el momento ---+")
        }else if(opcionSeleccionada == 4){
            println("+--- Programa finalizado por el usuario ---+")
        }
    }while(opcionSeleccionada != 4) // si el usuario ingresa 4 el programa termina
    println("---------------------------------------------------------------------------------")

    /*
    6.
    Una empresa registra las ventas realizadas a lo largo de un día de trabajo en una lista de
    montos. Implementar una función que devuelva los montos mayores a un monto N,
    siendo N un valor ingresado por el usuario.
     */
    println("### Verificacion de montos de ventas ###")
    val listaVentas = mutableListOf<Double>() // dado que trabajamos con montos creamos una lista de tipo Double
    var montoIngresado: Double?
    println("Ingresar montos de venta a continuacion. Para finalizar carga, ingresar un monto negativo.")
    do {
        print("### Ingresar monto --> $")
        montoIngresado = readln().toDouble()
        listaVentas.add(montoIngresado)
        if(montoIngresado <=0.0){
            println("### Carga de datos finalizada ###")
        }
    }while(montoIngresado >=1.0)

    // aca creamos una funcion que toma como valores de entrada una lista (la lista de montos creada antes) y el valor minimo sobre el cual se va a filtrar
    fun filtrarPorMonto(lista : MutableList<Double>, montoMinimo: Double): MutableList<Double> {
        val listaMontosFiltrados = mutableListOf<Double>() // retorna otra lista, tambien de double, compuesta por los valores que pasan el filtro
        for (monto in lista){
            if(monto>= montoMinimo){ // validamos que el monto sea igual o mayor aca
                listaMontosFiltrados.add(monto) // y agregamos a la lista que se debe retornar
            }
        }
        return listaMontosFiltrados
    }
    var montoMinimo : Double
    do {
        print("### Ingresar monto minimo para filtrar resultados--> $")
        montoMinimo = readln().toDouble()
        if(montoMinimo<=0){
            println("El valor ingresado como filtro no puede ser menor o igual a cero, intente nuevamente.")
        }
    }while (montoMinimo <=0)
    println("Ventas iguales o mayores a $${montoMinimo}")
    println(filtrarPorMonto(listaVentas, montoMinimo))
    println("---------------------------------------------------------------------------------")

    /*
    7.
    Dada una lista de nombres, crear otra lista con aquellos nombres cuyo tamaño sea mayor
    a 5 y empiecen con la letra ‘A’.
     */
    println("### Filtro de nombres por longitud e inicial ###")
    val listaDeNombresParaFiltrar = mutableListOf("Tomas", "Gabriel", "Ana", "Ayelen", "Bart", "Lisa", "Lautaro", "Abel", "Ignacio", "Ramon", "Agustin", "Brian")
    val listaDeNombresFiltrada = mutableListOf<String>()
    println("+--- Lista de nombres registrados ---+")
    for (nombre in listaDeNombresParaFiltrar){
        println(nombre)
    }
    println("+--- A continuacion se filtran aquellos nombres con longitud igual o mayor a 5 e inicial 'A' ---+")
    for (nombre in listaDeNombresParaFiltrar){
        if(nombre.startsWith('A') && nombre.length >= 5){
            listaDeNombresFiltrada.add(nombre)
        }
    }
    println("Los nombres que pasan el filtro especificado son los siguientes: ")
    for(nombre in listaDeNombresFiltrada){
        println(nombre)
    }
    println("---------------------------------------------------------------------------------")

    /*
    8.
    Realizar un programa que le permita a un usuario elegir los filtros que desea aplicarle a
    una lista de nombres. Una vez elegidos los filtros, aplicarlos e imprimir por pantalla la lista
    final filtrada. Los filtros a elegir son:  - - -
        Empieza con una letra especifica: Si el usuario elige esta opción deberá
        indicar una letra.
        Termina con una letra especifica: Si el usuario elige esta opción deberá
        indicar una letra.
        Longitud del nombre: Si el usuario elige esta opción deberá indicar la
        longitud expresada como número.
     */

    println("### Filtrado de nombres segun criterios de usuario ###")
    val nuevaListaNombres = mutableListOf("Agustin", "Marcos", "Jose", "Oscar", "Martina", "Camila", "Ernesto", "Rodrigo", "Pablo", "David", "Lautaro", "Otto", "Ana")
    println("A continuacion se muestran los nombres registrados en la lista.")
    for (nombre in nuevaListaNombres){
        println("* $nombre")
    }
    var filtroElegido : Int
    do {
        println("""
            ---------------------------------------------------------------
            -- Seleccionar un filtro para generar la lista de resultados --
            # Por letra de inicio, ingresar 1.
            # Por letra de terminación, ingresar 2.
            # Por longitud del nombre, ingresar 3.
            ---------------------------------------------------------------
        """.trimIndent())
        filtroElegido = readln().toInt()
        if(filtroElegido !in 1..3){ // mensaje en caso de ingresar una opcion incorrecta
            println("El valor ingresado no corresponde con un filtro, por favor, intente nuevamente.")
        }
    }while (filtroElegido !in 1..3)

    // para modularizar mas el codigo, cada filtro tiene asociada una funcion que ejecute esa tarea

    fun filtrarPorInicial(listaNombres : MutableList<String>, inicial : Char): MutableList<String>{ // como valores de entrada recibe lista e inicial
        val resultados = mutableListOf<String>() // crea una lista vacia para los resultados
        for(nombre in listaNombres){
            if(nombre.startsWith(inicial)){ // comparamos la inicial de cada nombre con la que se haya seleccionado
                resultados.add(nombre) // si hay coincidencia, se guarda ese nombre en la nueva lista
            }
        }
        return resultados
    }

    // esta funcion es igual a la anterior solo que usa endsWith en lugar de startsWith
    fun filtrarPorTerminacion(listaNombres : MutableList<String>, final : Char) : MutableList<String>{
        val resultados = mutableListOf<String>()
        for(nombre in listaNombres){
            if(nombre.endsWith(final)){
                resultados.add(nombre)
            }
        }
        return resultados
    }

    fun filtrarPorLongitud(listaNombres : MutableList<String>, longitud : Int) : MutableList<String>{
        val resultados = mutableListOf<String>()
        for(nombre in listaNombres){
            if(nombre.length >= longitud){ // comparamos la longitud de cada nombre con el valor especificado
                resultados.add(nombre) // si es igual o mayor, se asigna a la lista de resultados
            }
        }
        return resultados
    }

    when (filtroElegido) {
        1 -> {
            var inicial: Char?
            do {
                print("Seleccionar letra inicial para filtrar: ")
                inicial = readln().uppercase(getDefault())[0] // aislamos el primer caracter del string
                if (inicial.code !in 65..90) { // evaluamos el valor ASCII del caracter ingresado
                    println("El caracter ingresado no corresponde a una letra, por favor intente nuevamente.")
                }
            } while (inicial.code !in 65..90) // mientras no se identifique la inicial dentro de una posible letra, el ciclo sigue corriendo
            println("Resultados del filtro: ")
            val listaResultados = filtrarPorInicial(nuevaListaNombres, inicial)
            for (nombre in listaResultados) {
                println(nombre)
            }
        }
        2 -> {
            var final: Char?
            do {
                println("Seleccionar letra de terminación para filtrar: ")
                final = readln().uppercase(getDefault())[0]
                if (final.code !in 65..90) {
                    println("El caracter ingresado no corresponde a una letra, por favor intente nuevamente.")
                }
            } while (final.code !in 65..90)
            println("Resultados del filtro: ")
            val listaResultados = filtrarPorTerminacion(nuevaListaNombres, final)
            for (nombre in listaResultados) {
                println(nombre)
            }
        }
        else -> {
            var longitud: Int
            do {
                println("Ingresar la longitud minima del nombre para filtrar: ")
                longitud = readln().toInt()
                if(longitud<= 0){
                    println("La longitud no puede ser igual o menor a 0. Por favor, intente nuevamente.")
                }
            } while (longitud <= 0)
            println("Resultados del filtro: ")
            val listaResultados = filtrarPorLongitud(nuevaListaNombres, longitud)
            for (nombre in listaResultados) {
                println(nombre)
            }
        }
    }
}