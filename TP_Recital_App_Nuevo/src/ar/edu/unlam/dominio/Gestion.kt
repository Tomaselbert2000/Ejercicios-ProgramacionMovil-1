package ar.edu.unlam.dominio

import java.util.HashSet

class Gestion(){

    val listaUsuario: HashSet<Usuario> = HashSet()
    val listaEventos: HashSet<Evento> = HashSet()
    val listaTicket : HashSet<Ticket> = HashSet()
    val listaComprasRealizadas : HashSet<CompraTicket> = HashSet()

    fun registrarUsuario(usuario: Usuario): Boolean {
        return this.listaUsuario.add(usuario)
    }

    fun registrarEvento(evento: Evento) : Boolean{
        return this.listaEventos.add(evento)
    }

    fun registrarTicket(ticket: Ticket) : Boolean{
        return this.listaTicket.add(ticket)
    }

    fun registrarCompraTicket(
        compraTicket: CompraTicket, evento: Evento, quantity: Int, precioTotal: Double
    ) : Boolean{
        val listaSinDuplicados = compraTicket.ticketCollection.distinct()

        /*
        Con la linea anterior creamos una coleccion temporal dentro de la funcion
        que contenga solo los elementos NO duplicados dentro de la coleccion original,
        la cual en este caso es ticketCollection.
        Luego compara el size de ambas, si tienen el mismo size(), indica que todos
        los IDs contenidos que se hayan ingresado mediante la lista son distintos, y
        procede a guardar la compra en la lista de compras.
        Si fuesen distintos, indica que distinct() filtró elementos duplicados por
        lo tanto esa instancia de compra no es valida y retorna false
        Basicamente validamos que no se ingresen tickets repetidos dentro de una
        misma instancia de compra.
         */

        if(listaSinDuplicados.size == compraTicket.ticketCollection.size && this.verificarRequisitosDeCompra(this.buscarUsuario(compraTicket.userId), evento, quantity, precioTotal)){
            this.realizarTransaccion(this.buscarUsuario(compraTicket.userId), evento, quantity, precioTotal)
            return this.listaComprasRealizadas.add(compraTicket)
        }
        return false
    }

    fun realizarTransaccion(
        usuario: Usuario,
        evento: Evento,
        quantity: Int,
        precioTotal: Double
    ) : Boolean{
        return this.verificarSaldoSuficiente(usuario.money, precioTotal)
                && this.actualizarAsientosDisponibles(evento, quantity)
                && this.actualizarSaldo(usuario, precioTotal)
    }

    fun actualizarSaldo(usuario: Usuario, precioTotal: Double) : Boolean{
        for (user in this.listaUsuario){
            if (user == usuario) {
                user.money -= precioTotal
                return true
            }
        }
        return false
    }

    fun iniciarSesion(nickname: String, password: String) : Boolean{

        /*
        Con el metodo find() primero se busca un elemento dentro de la coleccion
        que tenga el nickname dado en la condicion. Podria ser null, por lo tanto
        es necesario agregar el operador ?.
        Si no fuese null, entonces accede a su atributo password y lo compara con
        el que le pasamos como parametro, y si son iguales, retornara true, de otro
        modo, retornara false.
         */

        return this.listaUsuario.find { it.nickname == nickname }?.password == password
    }

    fun actualizarAsientosDisponibles(evento: Evento, quantity: Int) : Boolean{
        for (e in this.listaEventos){
            if (e == evento){
                e.availableSeats -= quantity
                return true
            }
        }
        return false
    }

    fun buscarEvento(eventId: Long): Evento {
        return this.listaEventos.find { it.eventId == eventId }!!
    }

    fun buscarUsuario(id: Long): Usuario {
        return this.listaUsuario.find { it.id == id }!!
    }

    fun verificarSaldoSuficiente(saldoUsuario: Double, precio: Double): Boolean {
        return saldoUsuario >= precio
    }

    fun verificarAsientosSuficientes(availableSeats: Int, quantity: Int) : Boolean{
        return availableSeats >= quantity
    }

    fun buscarTicket(id: Long): Ticket? {
        return this.listaTicket.find { it.id == id }
    }

    // para hacer que el metodo que valida las compras sea mas corto, separamos responsabilidades entre varias funciones mas simples

    fun verificarRequisitosDeCompra(usuario: Usuario, evento: Evento, quantity: Int, precioTotal: Double) : Boolean {
        return this.verificarSaldoSuficiente(usuario.money, precioTotal) && this.verificarAsientosSuficientes(evento.availableSeats, quantity)
    }

    fun obtenerHistorialDeCompras(usuario: Usuario): MutableList<CompraTicket> {
        val historialDeCompras = mutableListOf<CompraTicket>()
        for(compra in this.listaComprasRealizadas){
            if(compra.userId == usuario.id){
                historialDeCompras.add(compra)
            }
        }
        return historialDeCompras
    }

    fun obtenerListaDeTicketsAsociados(usuario: Usuario): MutableList<Long> {
        val listaDeTicketsAsociadosAlUsuario = mutableListOf<Long>()
        for (compra in this.listaComprasRealizadas){
            if (compra.userId == usuario.id){
                for (item in compra.ticketCollection){
                    listaDeTicketsAsociadosAlUsuario.add(item)
                }
            }
        }
        return listaDeTicketsAsociadosAlUsuario
    }
}