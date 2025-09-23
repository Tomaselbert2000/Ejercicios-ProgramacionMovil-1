package main.kotlin.repositories

import main.kotlin.data.TicketCollection

object TicketCollectionRepository {

    private val ticketCollections = mutableListOf<TicketCollection>()
    private val repoUsuarios = UserRepository
    private val repoMediosDePago = PaymentMethodRepository

    init {
        ticketCollections.add(
            TicketCollection(
                1L,
                1510L,
                1L, // paga con Mercado Pago
                mutableListOf(1L, 3L, 12L, 27L, 5L, 19L, 8L, 30L, 2L, 14L, 22L, 9L),
            )
        )
        ticketCollections.add(
            TicketCollection(
                2L,
                1504L,
                2L, // paga con Visa
                mutableListOf(1L, 3L, 6L, 17L, 30L, 11L, 24L, 3L, 29L, 18L, 6L, 10L)
            )
        )
        ticketCollections.add(
            TicketCollection(
                3L,
                2802L,
                3L, // paga con MasterCard
                mutableListOf(1L, 3L, 25L, 7L, 14L, 30L, 2L, 12L, 28L, 19L, 5L, 25L)
            )
        )
    }

    fun get(): List<TicketCollection> {
        return emptyList()
    }

    fun registrarNuevaColeccion(
        nuevaColeccion: TicketCollection,
        listaDeIDsDeUsuariosRegistrados: MutableList<Long>,
        listaDeIDsDeTicketsRegistrados: MutableList<Long>
    ) : Boolean{
        return !this.esDuplicado(nuevaColeccion)
                && this.validarUserID(nuevaColeccion, listaDeIDsDeUsuariosRegistrados)
                && this.validarId(nuevaColeccion)
                && this.validarListadoDeIDsTickets(nuevaColeccion, listaDeIDsDeTicketsRegistrados)
                && !this.idRepetido(nuevaColeccion)
                && this.cuentaConSaldoSuficiente(nuevaColeccion)
                && this.ticketCollections.add(nuevaColeccion)
    }

    private fun cuentaConSaldoSuficiente(nuevaColeccion: TicketCollection): Boolean {
        val saldoParaChequear = this.obtenerSaldoUsuarioAsociado(nuevaColeccion.userId, repoUsuarios) // llamamos a una funcion que mediante el repo de usuarios busca el saldo a comparar
        return saldoParaChequear >= (this.ticketCollections.size * 10000.0) +
                (((this.ticketCollections.size * 10000.0) * this.obtenerValorDeComisionAplicable(nuevaColeccion))) // y devolvemos el valor de verdad de esta comparacion
    }

    private fun obtenerValorDeComisionAplicable(nuevaColeccion: TicketCollection): Double {
        val metodoAplicable = this.repoMediosDePago.obtenerMedioDePagoPorId(nuevaColeccion.paymentId)
        return metodoAplicable?.fee ?: 0.0
    }


    private fun validarListadoDeIDsTickets(nuevaColeccion: TicketCollection, listaDeIDsDeTicketsRegistrados: MutableList<Long>): Boolean {

        /*
        Aca tenemos una funcion compacta que lo que hace es leer la lista de IDs registrados que obtiene la funcion principal como parametro.
        Esta lista contiene todos los IDs de Tickets ya registrados en el sistema, es decir, aquellos que ya estan validados y que existen

        ¿Que buscamos hacer aca?
        Validar que todos los numeros de ID de tickets que tenga una nueva coleccion efectivamente existan dentro
        de la lista de tickets registrados. Basicamente, el sistema debe comparar los numeros de ID de la nueva coleccion y asegurarse
        que ninguno de ellos apunta a un numero de id de un ticket que no exista para asi evitar errores.

        Con el metodo all se leen todos los elementos en la lista que se quiere ingresar y se los compara con la lista validada,
        para ello el metodo contains() devuelve true o false segun si ese elemento está presente (usamos el it apuntando a ese valor).
        Si en algun momento encuentra un id de ticket que no exista en el repo principal, va a retornar false y ya sabemos entonces
        que alguno de los IDs no es correcto, por lo tanto se descarta el objeto.
         */

        return nuevaColeccion.ticketCollection.all { listaDeIDsDeTicketsRegistrados.contains(it) }
    }

    private fun validarUserID(nuevaColeccion: TicketCollection, listaDeIDsRegistrados: MutableList<Long>): Boolean {
        for (item in listaDeIDsRegistrados) {
            if (nuevaColeccion.userId == item) {
                return true
            }
        }
        return false
    }

    private fun validarId(nuevaColeccion: TicketCollection): Boolean {
        return nuevaColeccion.id >= 1L
    }

    private fun idRepetido(nuevaColeccion: TicketCollection): Boolean {
        for (c in this.ticketCollections){
            if (c.id == nuevaColeccion.id){
                return true
            }
        }
        return false
    }

    private fun esDuplicado(nuevaColeccion: TicketCollection): Boolean {
        for (collection in this.ticketCollections){
            if (nuevaColeccion == collection){
                return true
            }
        }
        return false
    }

    fun obtenerTotalDeTicketsCompradosPorUserId(userIdQueBuscamos: Long): Int {
        var cantidad = 0
        for(item in this.ticketCollections){
            if (item.userId == userIdQueBuscamos){
                cantidad++
            }
        }
        return cantidad
    }

    fun obtenerMontoTotalAcumuladoPorCompras(userIdQueBuscamos: Long): Double {
        var montoTotal = 0.0
        for (item in this.ticketCollections){
            if (item.userId == userIdQueBuscamos){
                montoTotal += item.ticketCollection.size * 10000
            }
        }
        return montoTotal
    }

    fun obtenerListaDeTicketsPorId(userIdQueBuscamos: Long): MutableList<Long>? {
        for(item in this.ticketCollections){
            if (item.userId == userIdQueBuscamos){
                return item.ticketCollection
            }
        }
        return null
    }

    fun obtenerSaldoUsuarioAsociado(userIdQueBuscamos: Long, userRepo: UserRepository): Double {
        return userRepo.obtenerSaldoDeUsuario(userIdQueBuscamos)
    }


    // al igual que los demas repositorios, para cada test lo vamos a reiniciar con esta funcion

    fun reiniciarInstancia() {
        ticketCollections.clear()
        ticketCollections.add(
            TicketCollection(
                1L,
                1510L,
                1L,
                mutableListOf(1L, 3L, 12L, 27L, 5L, 19L, 8L, 30L, 2L, 14L, 22L, 9L)
            )
        )
        ticketCollections.add(
            TicketCollection(
                2L,
                1504L,
                2L,
                mutableListOf(1L, 3L, 6L, 17L, 30L, 11L, 24L, 3L, 29L, 18L, 6L, 10L)
            )
        )
        ticketCollections.add(
            TicketCollection(
                3L,
                2802L,
                3L,
                mutableListOf(1L, 3L, 25L, 7L, 14L, 30L, 2L, 12L, 28L, 19L, 5L, 25L)
            )
        )
    }
}