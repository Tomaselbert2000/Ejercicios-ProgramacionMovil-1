package main.kotlin.test

import main.kotlin.data.Event
import main.kotlin.data.Ticket
import main.kotlin.data.TicketCollection
import main.kotlin.data.User
import main.kotlin.repositories.EventRepository
import main.kotlin.repositories.TicketCollectionRepository
import main.kotlin.repositories.TicketsRepository
import main.kotlin.repositories.UserRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ClaseDeTest {

    // dentro de esta clase se proporcionan todos los tests que sustentan las funcionalidades de este software

    // aca inicializamos las instancias que vamos a ir usando para realizar los tests
    lateinit var repoUsuarios : UserRepository
    lateinit var repoEventos : EventRepository
    lateinit var repoTickets : TicketsRepository
    lateinit var repoTicketsCollection: TicketCollectionRepository

    @Before // este metodo se ejecuta antes de comenzar cada test para proporcionar un escenario de pruebas apropiado
    fun inicializarObjetos(){

        // luego de cada prueba, los repositorios se reinician para permitir la siguiente (solo podemos tener una instancia de cada uno de ellos)
        this.repoUsuarios = UserRepository
        this.repoUsuarios.reiniciarInstancia()

        this.repoEventos = EventRepository
        this.repoEventos.reiniciarInstancia()

        this.repoTickets= TicketsRepository
        this.repoTickets.reiniciarInstancia()

        this.repoTicketsCollection = TicketCollectionRepository
        this.repoTicketsCollection.reiniciarInstancia()
    }

    // +--- Funciones de test para la clase UserRepository ---+
    @Test
    fun dadoQueExisteUnRepoDeUsuariosSiRegistroUnNuevoUsuarioObtengoTrue(){
        val usuario = User(1L, "tomasgabriel", "12345678", "Tomas", "Elbert", 32000.0, "2025/09/20") // creamos un usuario nuevo con datos ficticios
        val fueRegistrado = this.repoUsuarios.registrarNuevoUsuario(usuario) // si el usuario se registra en el repositorio el metodo tiene que retornar true
        assertTrue(fueRegistrado) // aca vamos a comparar si da bien o no
    }

    @Test
    fun dadoQueExisteUnRepoDeUsuariosSiIntentoCrearUnoConIdNegativoObtengoFalse(){
        // aca creamos un usuario que tenga un id con valor negativo
        val usuarioConIdNoValido = User(-2L, "usuarioConIdInvalido", "12345", "N/A", "N/A", 2000.0, "2025/09/20")
        val fueRegistrado = this.repoUsuarios.registrarNuevoUsuario(usuarioConIdNoValido) // esto tiene que dar false porque el sistema no deja pasar IDs negativos
        assertFalse(fueRegistrado)
    }

    @Test // con este test buscamos que no se pueda registrar dos veces un usuario que corresponda a la misma instancia en memoria
    fun dadoQueExisteUnRepoDeUsuariosSinDuplicadosSiGuardoUnRegistroDeNuevoObtengoFalse(){
        val usuario = User(1L, "tomasgabriel", "12345678", "Tomas", "Elbert", 32000.0, "2025/09/20")
        val fueRegistrado  = this.repoUsuarios.registrarNuevoUsuario(usuario) // aca lo guardamos la primera vez, tiene que dar true
        val fueRegistradoDeNuevo = this.repoUsuarios.registrarNuevoUsuario(usuario) // y aca probamos volver a registrar la misma instancia

        assertTrue(fueRegistrado) // vemos que aca si retorna true porque no encuentra ningun usuario que sea igual
        assertFalse(fueRegistradoDeNuevo) // al ejecutar el 2do registro encuentra el anterior y retornará false
    }

    /*

    Para la identificacion correcta de los usuarios,
    es vital que no se encuentren IDs ni Nicknames repetidos,
    los cuales validamos en sus respectivos tests tambien

     */

    @Test
    fun dadoQueExisteUnRepoDeUsuariosSiIntentoGuardarUnUsuarioConIdRepetidoObtengoFalse(){
        // vamos a crear dos usuarios con datos diferentes pero que hipoteticamente tienen el mismo valor de ID
        val usuario1 = User(10L, "User1", "abcd1234", "Tomas", "Elbert", 500.0, "2021/09/23")
        val usuario2 = User(10L, "1resU", "qwerty", "Juan", "Perez", 25000.0, "2021/09/24")

        // vamos a intentar guardarlos a ambos y registramos el valor de verdad que devuelven
        val primerUsuarioCreado = this.repoUsuarios.registrarNuevoUsuario(usuario1)
        val segundoUsuarioCreado = this.repoUsuarios.registrarNuevoUsuario(usuario2)

        // y comparamos que efectivamente el sistema impide repetir el id con otro usuario
        assertTrue(primerUsuarioCreado)
        assertFalse(segundoUsuarioCreado)
    }

    @Test // y aca le toca al nickname
    fun dadoQueExisteUnRepoDeUsuariosSiIntentoGuardarUnUsuarioConNicknameRepetidoObtengoFalse(){
        // en este test vamos a crear dos usuarios nuevos que entrarian en conflicto porque tienen el mismo valor como nickname

        val usuario = User(5L, "tomasgabriel", "123", "Tomas", "Elbert", 12000.0, "2025/09/20")
        val usuarioConNicknameRepetido = User(7L, "tomasgabriel", "qwerty", "Juan", "Perez", 65000.0, "2025/09/20")

        // intentamos registrarlos a ambos y nos quedamos con el boolean que devuelve cada uno en el proceso
        val usuarioRegistrado = this.repoUsuarios.registrarNuevoUsuario(usuario)
        val usuarioConNickRepetidoRegistrado = this.repoUsuarios.registrarNuevoUsuario(usuarioConNicknameRepetido)

        // y aca validamos que si un usuario se registra primero con un nickname, cualquier otro NO PUEDE hacer uso del mismo nickname
        assertTrue(usuarioRegistrado)
        assertFalse(usuarioConNickRepetidoRegistrado)
    }

    @Test
    fun dadoQueNoPuedoTenerSaldoNegativoSiRegistroUnUsuarioConSaldoMenorQueCeroObtengoFalse(){
        // aca creamos un usuario con una cantidad de saldo que el sistema no debería interpretar como invalida
        val usuarioConSaldoNegativo = User(3L, "usuarioSinSaldo", "no tengo un mango", "Tomas", "Elbert", -1.0, "2025/09/20")
        val fueRegistrado = this.repoUsuarios.registrarNuevoUsuario(usuarioConSaldoNegativo) // intentamos registrar el usuario aca
        assertFalse(fueRegistrado) // si el test corre bien, aca nos va a devolver false cuando encuentre que el saldo es menor que cero
    }

    @Test
    fun dadoQueExisteUnRepoDeUsuariosSiBuscoPorElID1510LObtengoTrue(){
        val idBuscado = 1510L // al inicializar el repositorio de usuarios, uno de ellos tiene este ID
        val usuarioEncontrado = this.repoUsuarios.buscarUsuarioPorID(idBuscado) // llamamos al metodo que itera sobre la lista de usuarios
        assertTrue(usuarioEncontrado) // si el metodo devolvio true, la busqueda de usuarios por id funciona como se espera
    }

    @Test
    fun dadoQueNoExisteUnUsuarioConID180LObtengoFalse(){
        // inverso al test anterior, forzamos a que busque un id inexistente para asegurar que retorna false
        val idBuscado = 180L
        val usuarioEncontrado = this.repoUsuarios.buscarUsuarioPorID(idBuscado)
        assertFalse(usuarioEncontrado)
    }

    @Test
    // con este test validamos que el sistema puede buscar entre todos los usuarios y devolver uno en especifico
    fun dadoQueExisteUnRepoDeUsuariosSiBuscoPorElNicknameMARTIN_ALBANESIobtengoUnUsuarioNoNulo(){
        val nicknameParaBuscar = "MARTIN_ALBANESI"
        val fueEncontradoEnElSistema = this.repoUsuarios.buscarUsuarioPorNickname(nicknameParaBuscar)
        assertNotNull(fueEncontradoEnElSistema)
    }

    @Test
    fun dadoQueExisteUnRepoDeUsuariosObtengoUnaListaDeTodosLosQueTienenUnSaldoMayorA150KyMenorOIgualA300K(){
        // tenemos dos valores ficticios de saldo que actuan como limite inferior y superior para filtrar
        val saldoMinimo = 150000.0
        val saldoMaximo = 300000.0

        // aca cuando llamemos al metodo va a devolver una lista con los usuarios que pasan el filtro
        val listaDeUsuariosFiltradosPorSaldo = this.repoUsuarios.obtenerListaUsuariosFiltradosPorSaldo(saldoMinimo, saldoMaximo)

        // cuando inicializamos el repositorio de usuarios, solo uno de ellos cumple el requisito planteado, por lo tanto
        // el size de la lista tiene que ser igual a 1 para que el test sea correcto

        val cantidadEsperada = 1
        val cantidadObtenida = listaDeUsuariosFiltradosPorSaldo.size
        assertEquals(cantidadEsperada, cantidadObtenida)
    }

    @Test
    fun dadoQueExisteUnRepoDeUsuariosSiBuscoPorFechaObtengoTodosLosQueSeRegistraronEseDia(){
        val fechaDeAlta = "2021/01/20" // elegimos una fecha de alta de algun usuario

        // con el metodo del repositorio obtenemos todos los que tengan esa fecha de alta
        val listaDeUsuariosFiltradosPorFecha = this.repoUsuarios.obtenerRegistrosPorFechaDeAlta(fechaDeAlta)

        val cantidadEsperada = 1 // dado el estado inicial del repo, tenemos un solo usuario con esa fecha
        val cantidadObtenida = listaDeUsuariosFiltradosPorFecha.size // obtenemos el size de la lista
        assertEquals(cantidadEsperada, cantidadObtenida) // si coinciden, indica que el test funciona correctamente
    }

    // +--- Funciones de test para la clase EventRepository ---+

    @Test
    fun dadoQueExisteUnRepoDeEventosSiRegistroUnEventoObtengoTrue(){
        // generamos un evento cualquiera y buscamos que la instancia se guarde en su correspondiente repositorio
        val unEvento = Event(10L, "19/11/2025", "08:30", "Estadio Unico de La Plata", "Khea", "imagePlaceHolder")
        val eventoRegistrado = this.repoEventos.registrarNuevoEvento(unEvento) // aca llamamos al metodo que se encargue de guarda el objeto
        assertTrue(eventoRegistrado) // y comparamos si el metodo devolvio true para que de verde el test
    }

    @Test
    fun dadoQueExisteUnRepoDeEventosSiRegistroUnIdNegativoObtengoFalse(){ // testeamos que los eventos no puedan tener IDs negativos
        val eventoConIdInvalido = Event(-1L, "19/11/2025", "12:30", "River Plate", "Khea", "imagePlaceHolder")
        val eventoRegistrado = this.repoEventos.registrarNuevoEvento(eventoConIdInvalido)
        assertFalse(eventoRegistrado)
    }

    @Test
    fun dadoQueExisteUnRepoDeEventosSiIntentoRegistrarDosVecesUnEventoAla2daObtengoFalse(){
        // aca vamos a usar el mismo objeto que creamos en el test anterior
        val evento = Event(10L, "19/11/2025", "08:30", "Estadio Unico de La Plata", "Khea", "imagePlaceHolder")
        val eventoRegistrado = this.repoEventos.registrarNuevoEvento(evento) // lo vamos a registrar la 1era vez, esto ya da true
        val eventoRegistradoDeNuevo = this.repoEventos.registrarNuevoEvento(evento) // aca volvemos intentar guardar la misma instancia dentro del repo

        // y confirmamos que el primero da true y el 2do da false
        assertTrue(eventoRegistrado)
        assertFalse(eventoRegistradoDeNuevo)
    }

    @Test // aca vamos a testear solo el id porque en un software real este valor actuaria como clave primaria
    fun dadoQueExisteUnRepoDeEventosSiIntentoRegistrarDosEventosConElMismoIdAl2doObtengoFalse(){
        // creamos dos eventos con distintos datos pero el mismo id
        val evento = Event(10L, "15/10/2025", "08:30", "Estadio Unico de La Plata", "Khea", "imagePlaceHolder")
        val eventoRepetido = Event(10L, "19/11/2025", "18:30", "River Plate", "Duki", "imagePlaceHolder")

        // ahora registramos los dos eventos
        val eventoRegistrado = this.repoEventos.registrarNuevoEvento(evento)
        val repetidoRegistrado = this.repoEventos.registrarNuevoEvento(eventoRepetido)

        // y aca validamos que el primero da true y el 2do falso
        assertTrue(eventoRegistrado)
        assertFalse(repetidoRegistrado)
    }

    @Test // aca testeamos que el sistema sea capaz de ubicar un evento por su Id
    fun dadoQueExisteUnRepoDeEventosSiBuscoPorId2LobtengoTrue(){
        val idBuscado = 2L // sabemos que al inicializar el repo hay un evento con id 2L
        val seEncontroEvento = this.repoEventos.buscarEventoPorId(idBuscado) // buscamos y esto debe retornar true
        assertTrue(seEncontroEvento) // y validamos que asi sea
    }

    /*
    Para el siguiente test lo que vamos a testear son los datos referentes a la ubicacion
    de un evento, fecha, hora, el artista y la imagen.
    Esto es asi porque si bien dos eventos pueden tener distinto ID, podria suceder que se
    intente ingresar un evento que tenga datos identicos a otro ya registrado
     */

    @Test
    fun dadoQueExisteUnRepoDeEventosSiIntentoRegistrarConLaMismaFechaHoraYUbicacionObtengoFalse(){
        // al momento de inicializar el repo de eventos, tenemos uno de ellos registrado para el 2025-10-02 a las 21:00" en Luna Park
        // por lo tanto el siguiente objeto debe entrar en conflicto si intenta registrarse

        val eventoConDatosRepetidos = Event(20L, "2025-10-02", "21:00", "Luna Park", "Luis Miguel", "imagePlaceHolder")
        val fueRegistrado = this.repoEventos.registrarNuevoEvento(eventoConDatosRepetidos) // intentamos registrarlo
        assertFalse(fueRegistrado) // aca validamos que el sistema no valida los datos y se rechaza, esto da false
    }

    // +--- Funciones de test para la clase TicketsRepository ---+

    @Test
    fun dadoQueExisteUnRepoDeTicketsSiRegistroUnTicketObtengoTrue(){
        val nuevoTicket = Ticket(50L, 3L, 1, "Platea") // aca creamos un nuevo ticket con datos ficticios
        val fueRegistrado = this.repoTickets.registrarNuevoTicket(nuevoTicket, this.repoEventos.obtenerListaDeIDsEventos()) // llamamos al repositorio
        assertTrue(fueRegistrado) // verificamos que lo agrego al sistema
    }

    @Test
    fun dadoQueExisteUnRepoDeTicketsSiRegistroUnoConIdNegativoObtengoFalse(){
        val nuevoTicket = Ticket(-10L, 3L, 1, "Platea") // aca creamos un ticket con un id que entre en conflicto con el sistma
        val fueRegistrado = this.repoTickets.registrarNuevoTicket(nuevoTicket, this.repoEventos.obtenerListaDeIDsEventos())
        assertFalse(fueRegistrado)
    }

    @Test
    fun dadoQueExisteUnRepoDeTicketsSiRegistroDosVecesElMismoAl2doObtengoFalse(){
        val nuevoTicket = Ticket(450L, 3L, 1, "Platea")
        val fueRegistrado = this.repoTickets.registrarNuevoTicket(nuevoTicket, this.repoEventos.obtenerListaDeIDsEventos()) // lo registramos la primera vez en el sistema
        val fueRegistradoDeNuevo = this.repoTickets.registrarNuevoTicket(nuevoTicket, this.repoEventos.obtenerListaDeIDsEventos()) // tomamos la misma instancia y la volvemos a cargar al sistema

        // y aca validamos que al 1er intento funciona y a partir del 2do intento devuelve false
        assertTrue(fueRegistrado)
        assertFalse(fueRegistradoDeNuevo)
    }

    @Test
    fun dadoQueExisteUnRepoDeTicketsSiQuieroRegistrarDosConElMismoIdAl2doObtengoFalse(){
        // creamos dos tickets con distintos datos pero el mismo id, tienen que entrar en conflicto
        val ticket = Ticket(45L, 3L, 1, "Platea")
        val ticketConIdRepetido = Ticket(45L, 1L, 5, "Platea")

        // intentamos registrar ambos
        val fueRegistrado = this.repoTickets.registrarNuevoTicket(ticket, this.repoEventos.obtenerListaDeIDsEventos())
        val repetidoRegistrado = this.repoTickets.registrarNuevoTicket(ticketConIdRepetido, this.repoEventos.obtenerListaDeIDsEventos())

        assertTrue(fueRegistrado) // al registrar este objeto el ID se va a ocupar
        assertFalse(repetidoRegistrado) // al intentar registrar esto tiene que dar false
    }

    @Test
    fun dadoQueExisteUnRepoDeTicketsSiRegistroUnTicketConUnaUbicacionInvalidaObtengoFalse(){
        val ticket = Ticket(45L, 3L, 1, "N/A") // aca creamos un ticket con un string de ubicacion que no se corresponde a ninguno en el sistema
        val fueRegistrado = this.repoTickets.registrarNuevoTicket(ticket, this.repoEventos.obtenerListaDeIDsEventos()) // probamos registrarlo
        assertFalse(fueRegistrado) // y aca validamos que el objeto no fue creado porque devolvio false
    }

    @Test
    fun dadoQueExisteUnRepoDeTicketsSiIntentoRegistrarUnTicketConCantidadMenorOIgualACeroObtengoFalse(){
        val ticketCantidadIncorrecta = Ticket(45L, 3L, -1, "Platea")
        val fueRegistrado = this.repoTickets.registrarNuevoTicket(ticketCantidadIncorrecta, this.repoEventos.obtenerListaDeIDsEventos())
        assertFalse(fueRegistrado)
    }

    @Test
    fun dadoQueExisteUnRepoDeTicketsSiIntentoRegistrarUnTicketConUnEventIdInexistenteObtengoFalse(){
        val ticketSinEventoAsociado = Ticket(45L, 8L, -1, "Platea") // por default el repo de eventos se inicializa con 7 eventos, no hay un evento con ID 8L
        val rangoDeEventosDisponibles = this.repoEventos.obtenerListaDeIDsEventos() // con este metodo retornamos una lista con los IDs de eventos que sabemos que estan cargados

        /*
        Importante: para poder implementar la busqueda de IDs de eventos es necesario que la instancia de EventRepository en uso
        informe a la capa de TicketRepository cuales son los IDs de eventos registrados para poder comparar y validar que los
        tickets nuevos que se ingresen al sistema siempre correspondan a un evento existente.
        Por tal motivo, a partir de este test, la funcion que registra los tickets deja de recibir como parametro solo el ticket,
        sino que tambien debe recibir la lista que le provee el repo de Eventos y continuar.

        Todos los metodos anteriores a este que incluyan creacion de tickets para funcionar tienen aplicado este cambio para
        evitar cualquier error de compilacion
         */

        // aca el metodo va a buscar entre los eventos si hubiese discrepancias
        val fueRegistrado = this.repoTickets.registrarNuevoTicket(ticketSinEventoAsociado, rangoDeEventosDisponibles)
        assertFalse(fueRegistrado) // y termina rechazando el objeto aca
    }

    // +--- Funciones de test para la clase TicketCollectionRepository ---+

    @Test
    fun dadoQueExisteUnRepoDeColeccionesDeTicketsSiRegistroUnNuevoObjetoObtengoTrue(){
        val nuevaColeccion = TicketCollection(5L, 1510L, mutableListOf(2L, 4L, 6L, 8L)) // aca creamos lo que seria un nuevo registro de una compra de tickets
        val fueRegistrado = this.repoTicketsCollection.registrarNuevaColeccion(
            nuevaColeccion,
            this.repoUsuarios.obtenerListaDeIDsDeUsuarios(),
            this.repoTickets.obtenerListaDeIDsDeTickets()
        )
        assertTrue(fueRegistrado)
    }

    @Test
    fun dadoQueExisteUnRepoDeColeccionesSiRegistroDosVecesElMismoObjetoAl2doObtengoFalse(){
        val coleccionRepetida = TicketCollection(5L, 1510L, mutableListOf(2L, 4L, 6L, 8L)) // aca vamos a intentar guardar dos veces esto en el sistema
        val fueRegistradoLaPrimeraVez = this.repoTicketsCollection.registrarNuevaColeccion(
            coleccionRepetida,
            this.repoUsuarios.obtenerListaDeIDsDeUsuarios(),
            this.repoTickets.obtenerListaDeIDsDeTickets()
        )
        val fueRegistradoLaSegundaVez = this.repoTicketsCollection.registrarNuevaColeccion(
            coleccionRepetida,
            this.repoUsuarios.obtenerListaDeIDsDeUsuarios(),
            this.repoTickets.obtenerListaDeIDsDeTickets()
        )

        // y aca vemos que el primero pasa y el 2do no
        assertTrue(fueRegistradoLaPrimeraVez)
        assertFalse(fueRegistradoLaSegundaVez) // al ser la misma instancia en memoria, el sistema no permite guardar el repetido y rechaza
    }

    @Test
    fun dadoQueExisteUnRepoDeColeccionesDeTicketsSiIngresoDosObjetosConMismoIdAl2doObtengoFalse(){
        // validamos que no se guarden colecciones diferentes con el mismo identificador por mas que tengan datos distintos
        val coleccion = TicketCollection(5L, 1504L, mutableListOf(1L, 3L, 6L, 8L))
        val coleccionConIdRepetido = TicketCollection(5L, 2802L, mutableListOf(2L, 4L, 6L, 8L))

        val seRegistroLaPrimera = this.repoTicketsCollection.registrarNuevaColeccion(
            coleccion,
            this.repoUsuarios.obtenerListaDeIDsDeUsuarios(),
            this.repoTickets.obtenerListaDeIDsDeTickets()
        )
        val seRegistroLaSegunda = this.repoTicketsCollection.registrarNuevaColeccion(
            coleccionConIdRepetido,
            this.repoUsuarios.obtenerListaDeIDsDeUsuarios(),
            this.repoTickets.obtenerListaDeIDsDeTickets()
        )

        assertTrue(seRegistroLaPrimera)
        assertFalse(seRegistroLaSegunda)
    }

    @Test
    fun dadoQueExisteUnRepoDeColeccionesSiIngresoUnObjetoConIdNegativoObtengoFalse(){
        // vamos a validar que el sistema no deje pasar ids con valores negativos
        val coleccionConIdNegativo = TicketCollection(-1L, 1510L, mutableListOf(1L, 4L, 6L, 8L))
        val fueRegistrado = this.repoTicketsCollection.registrarNuevaColeccion(
            coleccionConIdNegativo,
            this.repoUsuarios.obtenerListaDeIDsDeUsuarios(),
            this.repoTickets.obtenerListaDeIDsDeTickets()
        ) // intentamos guardarlo en el repo
        assertFalse(fueRegistrado) // al momento de verificar que el ID es negativo el sistema rechaza el objeto y retorna false
    }

    @Test
    fun dadoQueExisteUnRepoDeColeccionesSiIntentoGuardarUnObjetoConUserIdQueNoExisteObtengoFalse(){
        // para este test vamos a validar que el sistema chequea los IDs de usuarios existentes para no permitir guardar una coleccion enlazada a un user que no existe

        // al momento de inicializar el repo de usuarios, solo contamos con los siguientes IDs: 1510L, 1504L y 2802L
        // por lo tanto, el sistema debe validar que el parametro del userId en la coleccion nueva si se corresponde con alguno de los IDs de usuario ya registrados

        val coleccionConUserIdInexistente = TicketCollection(5L, 10L, mutableListOf(1L, 4L, 6L, 8L)) // pasamos al constructor un ID que falle

        // para poder obtener los IDs ya existentes, la capa de usuarios debe informar a la capa de colecciones
        // igual a como hicimos con la clase de eventos y tickets, es necesario un parámetro más el cual será una lista mutable de tipo Long

        // a partir de este test, se agregan en todos los anteriores el parametro con la lista de IDs para evitar errores de compilacion
        val listaDeIDsRegistrados = this.repoUsuarios.obtenerListaDeIDsDeUsuarios()
        val fueRegistrado = this.repoTicketsCollection.registrarNuevaColeccion(
            coleccionConUserIdInexistente,
            listaDeIDsRegistrados,
            this.repoTickets.obtenerListaDeIDsDeTickets()
        )
        assertFalse(fueRegistrado)
    }

    @Test
    fun dadoQueExisteUnRepoDeColeccionesSiIntentoGuardarUnaColeccionConUnIdDeTicketInexistenteObtengoFalse(){
        // para este test simulamos que si se intenta crear un objeto que dentro de la lista de IDs de tickets asociados tenga uno que no existe
        // es decir, validamos que dentro de la coleccion solo haya IDs de tickets que ya existen en el sistema

        // al inicializa el repo de tickets, el id de ticket mas alto es 30L
        val coleccionConNumeroDeTicketInexistente = TicketCollection(5L, 1510L, mutableListOf(1L, 4L, 6L, 31L))

        // para que la capa de colecciones sepa cuales IDs estan registrados, es necesario darle como parametro la lista al momento de intentar registrar un objeto
        val fueAgregado = this.repoTicketsCollection.registrarNuevaColeccion(
            coleccionConNumeroDeTicketInexistente,
            this.repoUsuarios.obtenerListaDeIDsDeUsuarios(),
            this.repoTickets.obtenerListaDeIDsDeTickets()) // aca agregamos el nuevo parametro

        assertFalse(fueAgregado) // como la lista de IDs de tickets contiene 31L, nos aseguramos que el sistema no registra este objeto de manera erronea
    }

    // +--- Funciones de test para la interaccion entre el usuario y el sistema ---+

    @Test
    fun dadoQueExisteUnUsuarioRegistradoSiIngresoComoMARTIN_ALBANESIabc4321ObtengoTrue(){
        // probamos que dado un nickname y contraseña correctos, el sistema valida que existe tal usuario y retorna true
        val nickname = "MARTIN_ALBANESI"
        val password = "abc4321"
        val sesionIniciada = this.repoUsuarios.iniciarSesion(nickname, password) // llamamos al metodo y le pasamos los datos aca
        assertTrue(sesionIniciada)
    }

    @Test
    fun dadoQueExisteUnRepoDeUsuariosSiIntentoIngresarConUnNicknameQueNoExisteObtengoFalse(){
        val nicknameQueNoExiste = "qwerty"
        val passwordQueSiExiste = "abc4321"
        val sesionIniciada = this.repoUsuarios.iniciarSesion(nicknameQueNoExiste, passwordQueSiExiste)
        assertFalse(sesionIniciada)
    }

    @Test
    fun dadoQueExisteUnRepoDeUsuariosSiFalloAlIngresarLaPasswordElContadorDeFallosDelUsuarioAumentaEnUno(){
        val nickname = "MARTIN_ALBANESI" // tenemos un nickname que funciona correctamente
        val password = "abc1234" // y aca una contraseña incorrecta para ese usuario
        this.repoUsuarios.iniciarSesion(nickname, password) // probamos iniciar sesion con estas credenciales, esto no tiene que funcionar
        val cantidadDeIntentosFallidosEsperada = 1 // esperamos que la cantidad de inicios de sesion fallidos se incremente en 1 luego de la linea anterior
        val cantidadDeIntenosFallidosObtenida = this.repoUsuarios.buscarUsuarioPorNickname(nickname)?.obtenerCantidadIniciosSesionFallidos() // obtenemos la cantidad de intentos
        assertEquals(cantidadDeIntentosFallidosEsperada, cantidadDeIntenosFallidosObtenida) // comparamos aca y obtenemos que efectivamente aumenta en 1 por cada inicio de sesion fallido
    }

    @Test
    fun dadoQueExisteUnRepoDeUsuariosConUnUsuarioRegistradoSiInicioSesionComoMARTIN_ALBANESIObtengoQueSuEstadoDeSesionEsTrue(){
        val nickname = "MARTIN_ALBANESI"
        val password = "abc4321"
        this.repoUsuarios.iniciarSesion(nickname, password) // pasamos el user y la password, esto genera que el usuario inicie sesion correctamente
        val estadoDeSesionEsperado = true // esperamos que de iniciar sesion, el estado sea true
        val estadoDeSesionObtenido = this.repoUsuarios.buscarUsuarioPorNickname("MARTIN_ALBANESI")?.obtenerEstadoDeSesion() // preguntamos el estado al objeto
        assertEquals(estadoDeSesionEsperado, estadoDeSesionObtenido) // validamos que da true y el test funciona
    }

    @Test
    fun dadoQueExisteUnRepoDeUsuariosSiIntentoIniciarSesion3VecesIncorrectamenteObtengoQueElUsuarioEstáBloqueado(){
        val nickname = "MARTIN_ALBANESI" // tenemos un nickname que si existe
        val password = "abc1234" // aca tenemos una contraseña incorrecta para ese user

        // iniciamos sesion 3 veces seguidas con una password incorrecta
        this.repoUsuarios.iniciarSesion(nickname, password)
        this.repoUsuarios.iniciarSesion(nickname, password)
        this.repoUsuarios.iniciarSesion(nickname, password)

        // y ahora vamos a preguntarle al repo de usuarios si el user con el nickname dado esta bloqueado o no
        val estadoDeBloqueoDeUsuarioEsperado = true // esperamos que luego de 3 intentos el sistema bloquee al usuario
        val estadoDeBloqueoDeUsuarioObtenido = this.repoUsuarios.buscarUsuarioPorNickname(nickname)?.obtenerEstadoDeBloqueoDeUsuario() // aca le preguntamos al objeto su valor

        assertEquals(estadoDeBloqueoDeUsuarioEsperado, estadoDeBloqueoDeUsuarioObtenido) // y finalmente obtenemos que si es true y el test pasa correctamente
    }
}