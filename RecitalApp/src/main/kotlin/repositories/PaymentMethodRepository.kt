package main.kotlin.repositories

import main.kotlin.data.PaymentMethod

object PaymentMethodRepository {

    private val listaMediosDePago = mutableListOf<PaymentMethod>()

    init {
        listaMediosDePago.add(PaymentMethod(1L, "Mercado Pago", 0.02))
        listaMediosDePago.add(PaymentMethod(2L, "Visa", 0.01))
        listaMediosDePago.add(PaymentMethod(3L, "MasterCard", 0.03))
    }

    fun registrarNuevoMedioDePago(nuevoMedioDePago: PaymentMethod): Boolean {
        return !this.estaDuplicado(nuevoMedioDePago)
                && !this.idRepetido(nuevoMedioDePago)
                && !this.nombreRepetido(nuevoMedioDePago)
                && this.idValido(nuevoMedioDePago)
                && this.valorDeComisionValido(nuevoMedioDePago)
                && this.listaMediosDePago.add(nuevoMedioDePago)
    }

    private fun valorDeComisionValido(nuevoMedioDePago: PaymentMethod): Boolean {
        return nuevoMedioDePago.fee >= 0.0
    }

    private fun idValido(nuevoMedioDePago: PaymentMethod): Boolean {
        return nuevoMedioDePago.id >= 1L
    }

    private fun nombreRepetido(nuevoMedioDePago: PaymentMethod): Boolean {
        for(pm in listaMediosDePago) {
            if(pm.name == nuevoMedioDePago.name) {
                return true
            }
        }
        return false
    }

    private fun idRepetido(nuevoMedioDePago: PaymentMethod): Boolean {
        for (pm in listaMediosDePago) {
            if (nuevoMedioDePago.id == pm.id) {
                return true
            }
        }
        return false
    }

    private fun estaDuplicado(nuevoMedioDePago: PaymentMethod): Boolean {
        for (item in this.listaMediosDePago) {
            if(item == nuevoMedioDePago){
                return true
            }
        }
        return false
    }

    fun obtenerMedioDePagoPorId(paymentId: Long): PaymentMethod? {
        for(pm in listaMediosDePago) {
            if(pm.id == paymentId){
                return pm
            }
        }
        return null
    }

    fun reiniciarInstancia() {
        listaMediosDePago.add(PaymentMethod(1L, "Mercado Pago", 0.02))
        listaMediosDePago.add(PaymentMethod(2L, "Visa", 0.01))
        listaMediosDePago.add(PaymentMethod(3L, "MasterCard", 0.03))
    }

    fun limpiarInstancia() {
        listaMediosDePago.clear()
    }
}