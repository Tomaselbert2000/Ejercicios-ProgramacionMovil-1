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
import kotlin.math.absoluteValue
import kotlin.random.Random

val repoUsuarios : UserRepository = UserRepository
val repoEventos : EventRepository = EventRepository
val repoTickets : TicketsRepository = TicketsRepository
val repoMediosDePago : PaymentMethodRepository = PaymentMethodRepository
val repoTicketCollection : TicketCollectionRepository = TicketCollectionRepository

fun main() {

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


    println("Bienvenido " + loggedUser.name + " " + loggedUser.surname)
    println("Saldo disponible: " + loggedUser.money)

    var opcion: Int
    do {
        println()
        println("=== MENÚ PRINCIPAL ===")
        println("1. Ver saldo")
        println("2. Comprar entradas")
        println("3. Ver mis entradas")
        println("4. Salir")
        print("Seleccione una opción: ")
        opcion = readln().toInt()

        when (opcion) {
            1 -> {
                println("Su saldo actual es: " + loggedUser.money)
            }
            2 -> realizarCompra(loggedUser)
            3 -> mostrarEntradas(loggedUser)
            4 -> {
                println("Saliendo del sistema...")
            }
            else -> {
                println("Opción inválida")
            }
        }
    } while (opcion != 4)
}

fun mostrarEntradas(loggedUser: User) {

    println("=== MIS ENTRADAS ===")

    val comprasRealizadasPorElUsuario = repoTicketCollection.buscarComprasPorId(loggedUser.id)

    /*
    Obtenemos todas las colecciones con el ID del usuario, dentro de cada una de ellas
    vamos a tener una lista mutable de tipo Long con los IDs de los tickets comprados en cada instancia,
    vamos a usar esos IDs para buscar en el repositorio
     */

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
}

fun realizarCompra(loggedUser: User) {
    var medioDePagoElegido : PaymentMethod? = null
    var ticketGenerado : Ticket? = null
    var nuevaColeccionGenerada : TicketCollection? = null
    var opcionMedio: Long
    var eventId : Long ? = null
    var cantidad : Int? = null

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
    }while (!listaDeIdsDeEventos.contains(eventId))


    do {
        print("Ingrese la cantidad de entradas: ")
        cantidad = readln().toInt()
        if (cantidad <= 0){
            println("La cantidad de entradas ingresada no es valida. Intente nuevamente.")
        }
    }while (cantidad <= 0)

    do {
        val listaDeIdsDeMediosDePago = repoMediosDePago.obtenerListaDeIDs()
        println("Seleccione medio de pago:")
        println("1. Visa")
        println("2. MasterCard")
        println("3. MercadoPago")
        opcionMedio = readln().toLong()
        if (!listaDeIdsDeMediosDePago.contains(opcionMedio)){
            println("El valor ingresado no corresponde a un medio de pago. Intente nuevamente.")
        }
    }while(!listaDeIdsDeMediosDePago.contains(opcionMedio))

    // creamos la instancia del metodo de pago
    medioDePagoElegido = repoMediosDePago.obtenerMedioDePagoPorId(opcionMedio)

    // creamos el ticket en si, esto va al repositorio de tickets
    ticketGenerado = Ticket(generarIdParaTicketAleatorio(), eventId, cantidad, "Campo")

    // con esta linea lo registramos
    repoTickets.registrarNuevoTicket(ticketGenerado, repoEventos.obtenerListaDeIDsEventos())

    // luego creamos la instancia de coleccion que actua como registro de compras y enlaza al usuario con los tickets y eventos
    // tanto el ID del registro en si como el ID del nuevo ticket creado se generan de manera pseudoaleatoria
    nuevaColeccionGenerada = TicketCollection(generarIdParaColeccionAleatorio(), loggedUser.id, medioDePagoElegido?.id ?: 1L, mutableListOf(ticketGenerado.id))

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