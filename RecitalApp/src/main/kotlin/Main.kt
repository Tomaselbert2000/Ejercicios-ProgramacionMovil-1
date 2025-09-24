package main.kotlin

import main.kotlin.repositories.EventRepository
import main.kotlin.repositories.PaymentMethodRepository
import main.kotlin.repositories.TicketCollectionRepository
import main.kotlin.repositories.TicketsRepository
import main.kotlin.repositories.UserRepository

val repoUsuarios : UserRepository = UserRepository
val repoEventos : EventRepository = EventRepository
val repoTickets : TicketsRepository = TicketsRepository
val repoMediosDePago : PaymentMethodRepository = PaymentMethodRepository
val repoTicketCollection : TicketCollectionRepository = TicketCollectionRepository

fun main() {

    // Login de usuario
    println("=== LOGIN ===")
    print("Ingrese nickname: ")
    val nickname = readln()
    print("Ingrese password: ")
    val password = readln()

    val loggedUser = repoUsuarios.login(nickname, password)

    if (loggedUser == null) {
        println("Error: usuario o contraseña incorrectos")
        return
    }

    println("Bienvenido " + loggedUser.name + " " + loggedUser.surname)
    println("Saldo disponible: " + loggedUser.money)

    var opcion = 0
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
            2 -> {
                println("Eventos disponibles:")
                val eventos = repoEventos.obtenerListaDeEventos()
                for (evento in eventos) {
                    println("ID: " + evento.id + " | " + evento.artist + " en " + evento.location + " el " + evento.date + " " + evento.time)
                }

                var eventId : Long ? = null
                do {
                    val listaDeIdsDeEventos = repoEventos.obtenerListaDeIDsEventos()
                    print("Ingrese el ID del evento: ")
                    eventId = readln().toLong()
                    if (!listaDeIdsDeEventos.contains(eventId)){
                        println("El ID ingresado no corresponde a un elemento de la lista de eventos. Intente nuevamente.")
                    }
                }while (!listaDeIdsDeEventos.contains(eventId))

                var cantidad : Int? = null
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
                    val opcionMedio = readln().toLong()
                    if (!listaDeIdsDeMediosDePago.contains(opcionMedio)){
                        println("El valor ingresado no corresponde a un medio de pago. Intente nuevamente.")
                    }
                }while(!listaDeIdsDeMediosDePago.contains(opcionMedio))

                /*try {
                    val nuevaColeccion = TicketCollection(1, eventId, 5, medio)
                    val monto = compraService.comprarEntrada(loggedUser, eventId, cantidad, medio)
                    println("Compra realizada con éxito. Total pagado: " + monto)
                    println("Saldo restante: " + loggedUser.money)
                } catch (e: Exception) {
                    println("Error en la compra: " + e.message)
                }*/
            }
            3 -> mostrarEntradas(loggedUser.id)
            4 -> {
                println("Saliendo del sistema...")
            }
            else -> {
                println("Opción inválida")
            }
        }
    } while (opcion != 4)
}

fun mostrarEntradas(loggedUser: Long) {
        println("=== MIS ENTRADAS ===")
        val coleccion = repoTicketCollection.buscarComprasPorId(loggedUser)
        var tieneEntradas = false

        for (ticket in coleccion) {
            val ticket = repoTickets.obtenerTicketPorId(ticket.id)
            if (ticket != null) {
                val evento = repoEventos.buscarEventoPorId(ticket.eventId)
                println(evento.toString())
                tieneEntradas = true
            }
        }
        if (!tieneEntradas) {
            println("No tiene entradas compradas")
        }
}