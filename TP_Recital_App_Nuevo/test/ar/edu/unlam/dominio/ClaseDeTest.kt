package ar.edu.unlam.dominio

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test

class ClaseDeTest {

    lateinit var gestor : Gestion

    lateinit var usuario1 : Usuario
    lateinit var usuario2 : Usuario

    lateinit var evento1: Evento
    lateinit var evento2 : Evento

    lateinit var ticket1 : Ticket
    lateinit var ticket2 : Ticket

    lateinit var compraTicket1: CompraTicket
    lateinit var compraTicket2 : CompraTicket

    @Before
    fun inicializarObjetos() {
        this.gestor = Gestion()
        this.usuario1 = Usuario(
            id = 1,
            nickname = "tomas123",
            password = "tomas456",
            name = "Tomas Gabriel",
            surname = "Elbert",
            createdDate = "2025-09-18",
            money = 255500.0
        )
        this.usuario2 = Usuario(
            id = 1,
            nickname = "tomas123",
            password = "ABC123",
            name = "Juan",
            surname = "Martinez",
            createdDate = "2025-09-22",
            money = 0.0
        )
        this.evento1 = Evento(
            eventId = 1,
            date = "2025-09-22",
            time = "19:30",
            location = "Gonzalez Catan",
            artist = "Duki",
            image = "Imagen",
            availableSeats = 100
        )
        this.evento2 = Evento(
            eventId = 1,
            date = "2025-09-28",
            time = "12:30",
            location = "San Justo",
            artist = "Khea",
            image = "Imagen",
            availableSeats = 100
        )
        this.ticket1 = Ticket(
            id = 1,
            eventId = 1,
            section = "Platea baja",
            quantity = 3
        )
        this.ticket2 = Ticket(
            id = 2,
            eventId = 3,
            section = "Palco",
            quantity = 5
        )
        this.compraTicket1 = CompraTicket(
            id = 1,
            userId = this.usuario1.id,
            ticketCollection = mutableListOf(this.ticket1.id)
        )
        this.compraTicket2 = CompraTicket(
            id = 1,
            userId = this.usuario1.id,
            ticketCollection = mutableListOf(this.ticket1.id, this.ticket2.id)
        )
    }

    @Test
    fun dadoQueExisteUnGestorDeEventosSiRegistroUnNuevoUsuarioObtengoTrue(){
        val fueAgregado = this.gestor.registrarUsuario(this.usuario1)
        assertTrue(fueAgregado)
    }

    @Test
    fun dadoQueNoPuedoTenerUsuariosDuplicadosAlIntentarRegistrar2VecesElMismoAl2doIntentoNoLoRegistra(){
        val fueAgregado = this.gestor.registrarUsuario(this.usuario1)
        val fueAgregadoDeNuevo = this.gestor.registrarUsuario(this.usuario1)
        assertTrue(fueAgregado)
        assertFalse(fueAgregadoDeNuevo)
    }

    @Test
    fun dadoQueExisteUnGestorDeEventosSiRegistroUnEventoElSizeDeLaListaDeEventosDevuelve1(){
        this.gestor.registrarEvento(this.evento1)
        val cantidadDeEventosEsperada = 1
        val cantidadDeEventosObtenida = this.gestor.listaEventos.size
        assertEquals(cantidadDeEventosEsperada, cantidadDeEventosObtenida)
    }

    @Test
    fun dadoQueNoPuedoTenerUsuariosDuplicadosSiCreo2UsuariosConDatosIgualesAl2doNoLoRegistra(){
        val fueAgregado1 = this.gestor.registrarUsuario(this.usuario1)
        val fueAgregado2 = this.gestor.registrarUsuario(this.usuario2)
        assertTrue(fueAgregado1)
        assertFalse(fueAgregado2)
    }

    @Test
    fun dadoQueExisteUnGestorDeEventosSiRegistroUnEventoObtengoTrue(){
        val eventoRegistrado = this.gestor.registrarEvento(this.evento1)
        assertTrue(eventoRegistrado)
    }

    @Test
    fun dadoQueExisteUnGestorDeEventosSiRegistroDosEventosConElMismoIdAl2doNoLoRegistra(){
        val evento1Registrado = this.gestor.registrarEvento(this.evento1)
        val evento2Registrado = this.gestor.registrarEvento(this.evento2)
        assertTrue(evento1Registrado)
        assertFalse(evento2Registrado)
    }

    @Test
    fun dadoQueExisteUnGestorDeEntradasSiRegistroUnTicketObtengoTrue(){
        val fueRegistrado = this.gestor.registrarTicket(this.ticket1)
        assertTrue(fueRegistrado)
    }

    @Test
    fun dadoQueExisteUnGestorDeEntradasSiRegistroDosTicketsConElMismoIdAl2doNoLoRegistra(){
        val fueRegistrado1 = this.gestor.registrarTicket(this.ticket1)
        val fueRegistrado2 = this.gestor.registrarTicket(this.ticket2)
        assertTrue(fueRegistrado1)
        assertTrue(fueRegistrado2)
    }

    /*@Test
    fun dadoQueExisteUnGestorDeEntradasSiUnUsuarioRealizaUnaCompraObtengoTrue(){
        this.gestor.registrarEvento(this.evento1)
        this.gestor.registrarUsuario(this.usuario1)
        this.gestor.registrarTicket(this.ticket1)
        val fueRegistrado = this.gestor.registrarCompraTicket(
            this.compraTicket1,
            this.gestor.buscarUsuario(this.usuario1.id),
            this.gestor.buscarEvento(this.ticket1.eventId),
            this.ticket1.quantity,
            this.ticket1.quantity*this.ticket1.precio
        )
        assertTrue(fueRegistrado)
    }*/

    @Test
    fun dadoQueExisteUnGestorDeEntradasSiSeRegistranDosComprasIgualesALa2daNoLaRegistra(){
        this.gestor.registrarEvento(this.evento1)
        this.gestor.registrarUsuario(this.usuario1)
        this.gestor.registrarTicket(this.ticket1)
        this.gestor.registrarTicket(this.ticket2)

        val compra1Registrada = this.gestor.registrarCompraTicket(
            this.compraTicket1,
            this.gestor.buscarEvento(this.ticket1.eventId),
            this.ticket1.quantity,
            this.ticket1.quantity*this.ticket1.precio
        )

        val compra2Registrada = this.gestor.registrarCompraTicket(
            this.compraTicket2,
            this.gestor.buscarEvento(this.ticket1.eventId),
            this.ticket1.quantity,
            this.ticket1.quantity*this.ticket1.precio
        )
        assertTrue(compra1Registrada)
        assertFalse(compra2Registrada)
    }

    @Test
    fun dadoQueExisteUnGestorDeEntradasSiIntentoIniciarSesionConUnUsuarioQueNoExisteObtengoFalse(){
        val usuarioNoRegistrado = Usuario(
            id = 1,
            nickname = "qwerty",
            password = "qwerty",
            name = "Tomas",
            surname = "Sesion",
            createdDate = "2020-04-04",
        )
        val sesionIniciada = this.gestor.iniciarSesion(usuarioNoRegistrado.nickname, usuarioNoRegistrado.password)
        assertFalse(sesionIniciada)
    }

    @Test
    fun dadoQueExisteUnUsuarioRegistradoEnElSistemaSiSeLogueaCon_tomas123_tomas456_ObtengoTrue(){
        this.gestor.registrarUsuario(this.usuario1)
        val sesionIniciada = this.gestor.iniciarSesion(this.usuario1.nickname, this.usuario1.password)
        assertTrue(sesionIniciada)
    }

    @Test
    fun dadoQueExisteUnUsuarioRegistradoConUnSaldoDe5500eIntentaComprarUnaEntradaQueVale10000ObtengoFalse(){
        this.gestor.registrarUsuario(this.usuario1)
        this.gestor.buscarUsuario(this.usuario1.id).money = 5500.0 // a fin de probar este test, vamos a usar un setter aca para verificar que no da un falso positivo
        this.gestor.registrarTicket(this.ticket1)
        val tieneSaldoSuficiente = this.gestor.verificarSaldoSuficiente(this.usuario1.money, this.ticket1.precio)
        assertFalse(tieneSaldoSuficiente) // esto aca tiene que dar false si o si
    }

    @Test
    fun dadoQueExisteUnGestorYUnEventoRegistradoCon3AsientosSiSeIntentanComprar5AsientosObtengoFalse(){
        this.gestor.registrarEvento(this.evento1)

        // al igual que en el test anterior, tenemos que probar un caso limite, usamos un setter para los asientos y otro para el quantity en el ticket
        this.gestor.buscarEvento(this.evento1.eventId).availableSeats = 3

        // buscamos el id del ticket aca con buscarTicket()
        this.gestor.buscarTicket(this.ticket1.id)?.quantity = 5 // aca usamos una safe call con ? porque el metodo de buscar tickets puede retornar null

        this.gestor.registrarTicket(this.ticket2) // para evitar errores con los tests anteriores, vamos a registrar el ticket 2
        val tieneAsientosSuficientes = this.gestor.verificarAsientosSuficientes(this.evento1.availableSeats, this.ticket2.quantity)
        assertFalse(tieneAsientosSuficientes)
    }

    @Test
    fun dadoQueExisteUnGestorSiSeRealizaUnaTransaccionObtengoTrue(){
        this.gestor.registrarUsuario(this.usuario1)
        this.gestor.registrarEvento(this.evento1)
        this.gestor.registrarTicket(this.ticket1)

        val transaccionCorrecta = this.gestor.realizarTransaccion(this.usuario1, this.evento1, this.ticket1.quantity, this.ticket1.quantity*this.ticket1.precio)
        val cantidadAsientosEsperada = 97
        val cantidadAsientosObtenida = this.gestor.buscarEvento(this.evento1.eventId).availableSeats
        val saldoActualizadoEsperado = 225500.0
        val saldoActualizadoObtenido = this.gestor.buscarUsuario(this.usuario1.id).money

        assertTrue(transaccionCorrecta)
        assertEquals(cantidadAsientosEsperada, cantidadAsientosObtenida)
        assertEquals(saldoActualizadoEsperado, saldoActualizadoObtenido)
    }

    @Test
    fun dadoQueExisteUnGestorSiSeIntentaRealizarUnaCompraSinSaldoSuficienteObtengoFalse(){
        this.usuario1.money = 5500.0
        this.gestor.registrarUsuario(this.usuario1)
        this.gestor.registrarEvento(this.evento1)
        this.gestor.registrarTicket(this.ticket1)

        val transaccionCorrecta = this.gestor.realizarTransaccion(this.usuario1, this.evento1, this.ticket1.quantity, this.ticket1.quantity*this.ticket1.precio)
        val cantidadAsientosEsperada = 100
        val cantidadAsientosObtenida = this.gestor.buscarEvento(this.evento1.eventId).availableSeats
        val saldoActualizadoEsperado = 5500.0
        val saldoActualizadoObtenido = this.gestor.buscarUsuario(this.usuario1.id).money
        assertFalse(transaccionCorrecta)
        assertEquals(cantidadAsientosEsperada, cantidadAsientosObtenida)
        assertEquals(saldoActualizadoEsperado, saldoActualizadoObtenido)
    }

    @Test
    fun dadoQueExisteUnEventoSinAsientosDisponiblesSiIntentoComprarLaTransaccionRetornaFalse(){

        this.evento1.availableSeats = 0 // a efectos de probar este caso de borde, con el setter ponemos la cantidad de asientos en 0

        // registramos en el sistema todos los datos necesarios
        this.gestor.registrarUsuario(this.usuario1)
        this.gestor.registrarEvento(this.evento1)
        this.gestor.registrarTicket(this.ticket1)
        // aca pasamos a simular la compra
        this.gestor.registrarCompraTicket(this.compraTicket1, this.gestor.buscarEvento(this.evento1.eventId), this.ticket1.quantity, this.ticket1.quantity*this.ticket1.precio)

    }

    @Test
    fun dadoQueExisteUnGestorDeEntradasSiUnUsuarioRealizaUnaCompraObtengoTrue(){
        this.gestor.registrarEvento(this.evento1) // registramos primero el evento
        this.gestor.registrarUsuario(this.usuario1) // registramos el usuario que va a comprar
        this.gestor.registrarTicket(this.ticket1) // registramos el ticket y tomamos en cuenta el valor del atributo quantity

        val seRegistroLaCompra = this.gestor.registrarCompraTicket(
            this.compraTicket1,
            this.gestor.buscarEvento(this.ticket1.eventId),
            this.ticket1.quantity,
            this.compraTicket1.ticketCollection.size * this.ticket1.precio)

        this.gestor.realizarTransaccion(this.usuario1, this.evento1, this.ticket1.quantity, this.ticket1.quantity*this.ticket1.precio)
        assertTrue(seRegistroLaCompra)
    }

    @Test
    fun dadoQueExisteUnGestorDeEntradasSiUnUsuarioCompra3AsientosObtengoQueElEventoTiene97AsientosDisponibles(){
        this.gestor.registrarUsuario(this.usuario1)
        this.gestor.registrarEvento(this.evento1)
        this.gestor.registrarTicket(this.ticket1)

        this.gestor.registrarCompraTicket(
            this.compraTicket1,
            this.gestor.buscarEvento(this.ticket1.eventId),
            this.ticket1.quantity,
            this.compraTicket1.ticketCollection.size * this.ticket1.precio
            )

        val asientosEvento1Esperado = 97
        val asientosEvento1Obtenido = this.evento1.availableSeats
        assertEquals(asientosEvento1Esperado, asientosEvento1Obtenido)
    }

    @Test
    fun dadoQueExisteUnGestorYUnUsuarioRegistradoObtengoTodasLasTransaccionesDelUsuario(){

        // registramos todos los datos
        this.gestor.registrarUsuario(this.usuario1)
        this.gestor.registrarEvento(this.evento1)
        this.gestor.registrarTicket(this.ticket1)

        // aca armamos el contenido de la compra en si
        this.gestor.registrarCompraTicket(
            compraTicket = this.compraTicket1,
            evento = this.gestor.buscarEvento(this.evento1.eventId),
            quantity = this.ticket1.quantity,
            precioTotal = this.compraTicket1.ticketCollection.size * this.ticket1.precio,
        )

        val registroDeComprasDelUsuario = this.gestor.obtenerHistorialDeCompras(this.gestor.buscarUsuario(this.compraTicket1.userId))
        val cantidadComprasEsperada = 1
        val cantidadComprasObtenida = registroDeComprasDelUsuario.size
        assertEquals(cantidadComprasEsperada, cantidadComprasObtenida)
    }

    @Test
    fun dadoQueExisteUnGestorConUnUsuarioObtengoQueComproUnTicket(){
        // registramos todos los datos
        this.gestor.registrarUsuario(this.usuario1)
        this.gestor.registrarEvento(this.evento1)
        this.gestor.registrarTicket(this.ticket1)

        // aca armamos el contenido de la compra en si
        this.gestor.registrarCompraTicket(
            /*
            En la instancia de registro de compra compraTicket1 tenemos asociada una mutableList<Long> que tiene un solo elemento
            Esto indica que de validarse la compra, el size de la lista que queremos buscar tiene que tener ese tamaño
             */

            compraTicket = this.compraTicket1,
            evento = this.gestor.buscarEvento(this.evento1.eventId),
            quantity = this.ticket1.quantity,
            precioTotal = this.compraTicket1.ticketCollection.size * this.ticket1.precio,
        )

        val listaDeTicketsAsociadosAlUsuario = this.gestor.obtenerListaDeTicketsAsociados(this.gestor.buscarUsuario(this.compraTicket1.userId))
        val cantidadEsperada = 1 // esto hace referencia a la CANTIDAD DE IDs de tickets, no al ID en si
        val cantidadObtenida = listaDeTicketsAsociadosAlUsuario.size
        assertEquals(cantidadEsperada, cantidadObtenida) // validamos aca que sean iguales
    }
}