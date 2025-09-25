package main.kotlin

import main.kotlin.data.PaymentMethod
import main.kotlin.data.Ticket
import main.kotlin.data.TicketCollection
import main.kotlin.data.User
import main.kotlin.repositories.EventRepository
import main.kotlin.repositories.PaymentMethodRepository
import main.kotlin.repositories.TicketCollectionRepository
import main.kotlin.repositories.TicketsRepository
import main.kotlin.repositories.UserRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue
import kotlin.random.Random

val repoUsuarios : UserRepository = UserRepository
val repoEventos : EventRepository = EventRepository
val repoTickets : TicketsRepository = TicketsRepository
val repoMediosDePago : PaymentMethodRepository = PaymentMethodRepository
val repoTicketCollection : TicketCollectionRepository = TicketCollectionRepository

fun main() {

    println("""
        .=== Sistema de Gestion de eventos - Ticketek ===.
        |       1. Iniciar sesion con credenciales.      |
        |             2. Crear nuevo usuario.            |
        .================================================.
    """.trimIndent())
    var opcionDeAcceso : Int?
    do {
        println("Seleccione un valor para continuar: ")
        opcionDeAcceso = readln().toInt()
        if(opcionDeAcceso !in 1 ..2){
            println("El valor ingresado no es valido. Intente nuevamente.")
        }
    }while (opcionDeAcceso !in 1..2)

    // creamos una variable loggedUser que dentro contenga el resultado de llamar a la funcion iniciarSesion() o en su defecto crearUsuarioNuevo()
    val loggedUser : User = if(opcionDeAcceso == 1){
        iniciarSesion()
    }else{
        crearUsuarioNuevo()!!
    }

    println("Bienvenido " + loggedUser.name + " " + loggedUser.surname)
    println("Saldo disponible: " + loggedUser.money)

    var opcion: Int
    do {
        println()
        println("=== MENÚ PRINCIPAL ===")
        println("1. Ver saldo")
        println("2. Comprar entradas")
        println("3. Ver mis entradas")
        println("4. Saldo total gastado.")
        println("5. Cantidad total de tickets adquirida.")
        println("6. Salir")
        print("Seleccione una opción: ")
        opcion = readln().toInt()

        when (opcion) {
            1 -> {
                println("Su saldo actual es: $" + loggedUser.money)
            }
            2 -> realizarCompra(loggedUser)
            3 -> mostrarEntradas(loggedUser)
            4 -> mostrarTotalGastado(loggedUser)
            5 -> mostrarTotalDeTicketsAdquiridos(loggedUser)
            6 -> {
                println("Saliendo del sistema...")
            }
            else -> {
                println("Opción inválida")
            }
        }
    } while (opcion != 6)
}

fun crearUsuarioNuevo(): User? {
    println(".=== Completar datos de usuario ===.")
    println("Ingresar nombre: ")
    val nombre = readln()
    println("Ingresar apellido: ")
    val apellido = readln()
    println("Ingresar nickname: ")
    val nickname = readln()
    println("Ingresar contraseña de usuario: ")
    val password = readln()

    val nuevoUsuario = User(generarIDparaUsuarioAleatorio(), nickname, password, nombre, apellido, 0.0, retornarStringFechaActual())
    return if(repoUsuarios.registrarNuevoUsuario(nuevoUsuario)){
        nuevoUsuario
    }else{
        null
    }
}

fun iniciarSesion(): User {
    var nickname : String?
    var password : String?
    var loggedUser : User?
    do {
        // Login de usuario

        println("=== LOGIN ===")
        print("Ingrese nickname: ")
        nickname = readln()
        print("Ingrese password: ")
        password = readln()

        loggedUser = repoUsuarios.login(nickname, password) // se llama al repo y se le pasan las credenciales
        if(loggedUser != null){ // primero validamos que no sea null
            if (loggedUser.usuarioBloqueado){ // revisamos aca si esta bloqueado o no
                println("El usuario se encuentra bloqueado debido a demasiados intentos de inicio de sesion fallidos.")
                loggedUser = null // en caso de estarlo, el login se redirige al principio, este objeto se vuelve a setear en null
            }else{
                println("Inicio de sesion correcto, ingresando al sistema...")
            }
        }else{
            println("Usuario o contraseña incorrectos. Intente nuevamente.")
            loggedUser = null // para cualquier user o clave incorrectos, limpiamos tambien el objeto
        }
    }while(loggedUser == null)
    return loggedUser
}

fun mostrarTotalGastado(loggedUser: User){
    println("El monto acumulado para el usuario es : $" + repoTicketCollection.obtenerMontoTotalAcumuladoPorCompras(loggedUser.id))
    println("Importante: no se incluyen comisiones por medios de pago.")
}

fun mostrarTotalDeTicketsAdquiridos(loggedUser: User){
    println("El usuario acumula en total: " + repoTicketCollection.obtenerTotalDeTicketsCompradosPorUserId(loggedUser.id) + " tickets")
}


fun mostrarEntradas(loggedUser: User) {

    val comprasRealizadasPorElUsuario = repoTicketCollection.buscarComprasPorId(loggedUser.id)

    /*
    Obtenemos todas las colecciones con el ID del usuario, dentro de cada una de ellas
    vamos a tener una lista mutable de tipo Long con los IDs de los tickets comprados en cada instancia,
    vamos a usar esos IDs para buscar en el repositorio
     */

    if(comprasRealizadasPorElUsuario.isNotEmpty()){
        println(".=== Mis entradas ===.")
        for(compra in comprasRealizadasPorElUsuario){ // vamos a iterar sobre cada compra
            println("=== Historial de compras de usuario ===")
            val listaDeIDsDeTicketsComprados = compra.ticketCollection // extraemos la lista mutable de cada una de las compras
            for(id in listaDeIDsDeTicketsComprados){ // ahora iteramos sobre esa lista mutable, leemos los IDs de tickets guardados
                val ticket = repoTickets.obtenerTicketPorId(id) // por cada ID obtenemos el ticket asociado desde el repositorio

                // imprimimos por pantalla los datos
                println("ID de ticket: ${ticket?.id}")
                // para los datos del evento asociado usaremos el eventId para pasarlo al repositorio de eventos y obtener los datos
                println("Evento asociado: " + repoEventos.buscarEventoPorId(ticket?.eventId ?: 0L)?.id)
                println("Artista invitado: " + repoEventos.buscarEventoPorId(ticket?.eventId ?: 0L)?.artist)
                println("Fecha: " + repoEventos.buscarEventoPorId(ticket?.eventId ?: 0L)?.date)
                println("Hora: " + repoEventos.buscarEventoPorId(ticket?.eventId ?: 0L)?.time)
                println("Cantidad de plazas adquiridas: " + ticket?.quantity) // la cantidad de asientos ya se encuentra en el ticket
                println("\n=======================================\n")
            }
        }
    }else{
        println("No se registran entradas actualmente.")
    }
}

fun realizarCompra(loggedUser: User) {
    var medioDePagoElegido : PaymentMethod?
    var ticketGenerado : Ticket?
    var opcionMedio: Long
    var eventId : Long?
    var cantidad : Int?

    println("Eventos disponibles:")
    val eventos = repoEventos.obtenerListaDeEventos()
    for (evento in eventos) {
        println("ID: " + evento.id + " | " + evento.artist + " en " + evento.location + " el " + evento.date + " " + evento.time)
    }


    do {
        val listaDeIdsDeEventos = repoEventos.obtenerListaDeIDsEventos()
        print("Ingrese el ID del evento: ")
        eventId = readln().toLong()
        if (!listaDeIdsDeEventos.contains(eventId)){
            println("El ID ingresado no corresponde a un elemento de la lista de eventos. Intente nuevamente.")
        }
    }while (!listaDeIdsDeEventos.contains(eventId)) // verificamos que el valor ingresado corresponde a algun evento


    do {
        print("Ingrese la cantidad de entradas: ")
        cantidad = readln().toInt()
        if (cantidad <= 0){
            println("La cantidad de entradas ingresada no es valida. Intente nuevamente.")
        }
    }while (cantidad <= 0) // no podemos comprar una cantidad negativa

    do {
        val listaDeIdsDeMediosDePago = repoMediosDePago.obtenerListaDeIDs()
        println("=== Seleccione medio de pago ===")
        for(pm in listaDeIdsDeMediosDePago){
            println("\nID: " + repoMediosDePago.obtenerMedioDePagoPorId(pm)?.id +
                    repoMediosDePago.obtenerMedioDePagoPorId(pm)?.name + ", se aplica una comision de: " +
                    repoMediosDePago.obtenerMedioDePagoPorId(pm)?.fee?.times(100.0) + "%")
        }
        println("Ingresar medio de pago: ")
        opcionMedio = readln().toLong()
        if (!listaDeIdsDeMediosDePago.contains(opcionMedio)){
            println("El valor ingresado no corresponde a un medio de pago. Intente nuevamente.")
        }
    }while(!listaDeIdsDeMediosDePago.contains(opcionMedio)) // validamos que el ID de medio de pago existe

    // creamos la instancia del metodo de pago
    medioDePagoElegido = repoMediosDePago.obtenerMedioDePagoPorId(opcionMedio)

    // creamos el ticket en si, esto va al repositorio de tickets
    ticketGenerado = Ticket(generarIdParaTicketAleatorio(), eventId, cantidad, "Campo")

    // con esta linea lo registramos
    repoTickets.registrarNuevoTicket(ticketGenerado, repoEventos.obtenerListaDeIDsEventos())

    // luego creamos la instancia de coleccion que actua como registro de compras y enlaza al usuario con los tickets y eventos
    // tanto el ID del registro en si como el ID del nuevo ticket creado se generan de manera pseudoaleatoria
    val nuevaColeccionGenerada =
        TicketCollection(generarIdParaColeccionAleatorio(), loggedUser.id, medioDePagoElegido?.id ?: 1L, mutableListOf(ticketGenerado.id))

    // la funcion que registra las colecciones debe devolver un true o false, y en base a ese retorno mostramos un mensaje acorde en pantalla
    if(repoTicketCollection.registrarNuevaColeccion(nuevaColeccionGenerada, repoUsuarios.obtenerListaDeIDsDeUsuarios(), repoTickets.obtenerListaDeIDsDeTickets())){
        println("\n=== Compra registrada de manera exitosa ===\n")
    }else{
        println("=== Ocurrió un error al procesar la compra. Por favor, intente nuevamente ===")
    }
}

// con .absoluteValue buscamos generar valores que esten despues del cero

fun generarIdParaTicketAleatorio(): Long {
    val idRandom = Random.nextLong().absoluteValue
    if(!repoTickets.obtenerListaDeIDsDeTickets().contains(idRandom)){
        return idRandom
    }
    return 0
}

fun generarIdParaColeccionAleatorio(): Long {
    val idRandom = Random.nextLong().absoluteValue
    if(!repoTicketCollection.obtenerListaDeIDsDeColecciones().contains(idRandom)){
        return idRandom
    }
    return 0
}

fun generarIDparaUsuarioAleatorio(): Long {
    val idRandom = Random.nextLong().absoluteValue
    if(!repoUsuarios.obtenerListaDeIDsDeUsuarios().contains(idRandom)){
        return idRandom
    }
    return 0
}

fun retornarStringFechaActual(): String {
    return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
}