package main.kotlin.data

import main.kotlin.repositories.PaymentMethodRepository
import main.kotlin.repositories.TicketsRepository
import main.kotlin.repositories.UserRepository

data class TicketCollection(
    val id: Long,
    val userId: Long,
    val paymentId: Long,
    val ticketCollection: MutableList<Long>,
    // dado que las colecciones de ticket se relacionan tanto con el repo de tickets como con el de usuarios, deben ser accesibles desde aca
    val repoDeUsuarios: UserRepository = UserRepository,
    val repoDeTicketsRegistrados: TicketsRepository = TicketsRepository,
    val repoMediosDePago: PaymentMethodRepository = PaymentMethodRepository
){


    init {
        val usuarioAsociado = this.repoDeUsuarios.buscarUsuarioPorID(userId)
        val medioDePagoAsociado = this.repoMediosDePago.obtenerMedioDePagoPorId(paymentId)
        val valorDeCompra = this.calcularTotalDeCompra()
        val valorComision = this.calcularComisionPorMedioDePago(medioDePagoAsociado)
        usuarioAsociado?.descontarMontoDeCompra(valorDeCompra+valorComision)
    }

    fun calcularTotalDeCompra(): Double {
        return this.ticketCollection.size * 10000.0
    }

    fun calcularComisionPorMedioDePago(medioDePagoAsociado: PaymentMethod?): Double {
        return this.calcularTotalDeCompra() * (medioDePagoAsociado?.fee ?: 0.0)
    }
}
