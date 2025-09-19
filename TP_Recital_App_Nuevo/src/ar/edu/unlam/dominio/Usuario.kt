package ar.edu.unlam.dominio

class Usuario(
    val id: Long,
    var nickname: String,
    var password: String,
    var name: String,
    val surname: String,
    var createdDate: String, // formato "yyyy-MM--dd"
    var money: Double = 0.0 // dinero del usuario para realizar compras, por default 0.0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Usuario

        if (id != other.id) return false
        if (nickname != other.nickname) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nickname.hashCode()
        return result
    }
}