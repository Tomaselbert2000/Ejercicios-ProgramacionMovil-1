package main.kotlin.repositories

import main.kotlin.data.User

object UserRepository {

    private val users = mutableListOf<User>()

    init {
        users.add(User(1504L, "MARTIN_ALBANESI", "abc4321", "Martin", "Albanesi", 3500000.50, "2024/05/13"))
        users.add(User(2802L, "Fran25", "contraseña123", "Franco German", "Mazafra", 200000.50, "2021/01/20"))
        users.add(User(1510L, "jonaURAN", "@12345", "Jonatan", "Uran", 120000.0, "2018/04/15"))
    }

    fun login(): User? {
        return null
    }

    fun registrarNuevoUsuario(usuarioNuevo: User): Boolean{
        return this.validarId(usuarioNuevo) &&
                !this.estaDuplicado(usuarioNuevo) &&
                this.saldoInicialValido(usuarioNuevo) &&
                users.add(usuarioNuevo)
    }

    private fun validarId(usuarioNuevo: User): Boolean {
        return usuarioNuevo.id >= 1L
    }

    private fun saldoInicialValido(usuarioNuevo: User) : Boolean{
        return usuarioNuevo.money >= 0.0
    }

    private fun estaDuplicado(usuario : User): Boolean{
        for(u in users){
            if(u == usuario || u.id == usuario.id || u.nickname == usuario.nickname){
                return true
            }
        }
        return false
    }

    fun obtenerRegistrosPorFechaDeAlta(fechaDeAlta: String): MutableList<User> {
        val listaDeUsuarios = mutableListOf<User>()
        for(user in users){
            if(user.createdDate == fechaDeAlta){
                listaDeUsuarios.add(user)
            }
        }
        return listaDeUsuarios
    }

    fun buscarUsuarioPorID(idBuscado: Long) : Boolean{
        return users.find { it.id == idBuscado } != null
    }

    // dado que el repositorio de usuarios es de tipo Object, no podemos tener mas que una sola instancia
    // lo cual lleva a posibles errores o inconsistencias al momento de correr sus respectivos tests
    // con este metodo reiniciamos la instancia desde dentro para poder testear correctamente

    fun reiniciarInstancia() {
        users.clear()
        users.add(User(1504L, "MARTIN_ALBANESI", "abc4321", "Martin", "Albanesi", 3500000.50, "2024/05/13"))
        users.add(User(2802L, "Fran25", "contraseña123", "Franco German", "Mazafra", 200000.50, "2021/01/20"))
        users.add(User(1510L, "jonaURAN", "@12345", "Jonatan", "Uran", 120000.0, "2018/04/15"))
    }

    fun obtenerListaUsuariosFiltradosPorSaldo(saldoMinimo: Double, saldoMaximo: Double): MutableList<User> {
        val listaDeUsuariosFiltradosPorSaldo = mutableListOf<User>()
        for(user in users){
            if (user.money in saldoMinimo .. saldoMaximo){
                listaDeUsuariosFiltradosPorSaldo.add(user)
            }
        }
        return listaDeUsuariosFiltradosPorSaldo
    }
}