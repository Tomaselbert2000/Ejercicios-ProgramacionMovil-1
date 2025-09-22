package main.kotlin.data

import main.kotlin.repositories.TicketsRepository
import main.kotlin.repositories.UserRepository

data class TicketCollection(
    val id: Long,
    val userId: Long,
    val ticketCollection: MutableList<Long>,
    val repoDeUsuarios: UserRepository = UserRepository,
    val repoDeTicketsRegistrados: TicketsRepository = TicketsRepository
){
    init {
        val montoDeCompra = calcularTotalDeCompra()
        val usuarioAsociado = this.obtenerUsuarioAsociadoALaCompra()
        usuarioAsociado?.descontarMontoDeCompra(montoDeCompra)
    }

    fun calcularTotalDeCompra(): Double {
        return this.ticketCollection.size * 10000.0
    }

    fun obtenerUsuarioAsociadoALaCompra(): User?{
        return this.repoDeUsuarios.buscarUsuarioPorID(this.userId)
    }
}
