package main.kotlin

import main.kotlin.data.TicketCollection
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

                print("Ingrese el ID del evento: ")
                val eventId = readln().toLong()

                print("Ingrese la cantidad de entradas: ")
                val cantidad = readln().toInt()

                println("Seleccione medio de pago:")
                println("1. Visa")
                println("2. MasterCard")
                println("3. MercadoPago")
                val opcionMedio = readln().toLong()
                val medio = repoMediosDePago.obtenerMedioDePagoPorId(opcionMedio)
                /*{
                    Visa()
                } else if (opcionMedio == 2) {
                    MasterCard()
                } else {
                    MercadoPago()
                }*/

                try {
                    val nuevaColeccion = TicketCollection(1, eventId, 5, medio)
                    val monto = compraService.comprarEntrada(loggedUser, eventId, cantidad, medio)
                    println("Compra realizada con éxito. Total pagado: " + monto)
                    println("Saldo restante: " + loggedUser.money)
                } catch (e: Exception) {
                    println("Error en la compra: " + e.message)
                }
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