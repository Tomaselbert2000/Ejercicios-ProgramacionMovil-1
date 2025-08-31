import kotlin.random.Random
import kotlin.random.nextInt

fun main() {

    /*
    1.
    Hacer una función que reciba como parámetros requeridos el día y mes de una fecha y
    como parámetro opcional el año (su valor por defecto debe ser 2022). Imprimir un
    mensaje que diga “Hoy es [día] del mes [mes] del [año]”.
     */

    fun datePrinter(day: Int, month: Int, year: Int = 2022){ // definimos como parámetro opcional el año que tiene como default 2022
        println("Hoy es $day del mes $month del año $year") // retorna la cadena de texto concatenando las variables
        println("----------------------------------")
    }

    datePrinter(19, 11, 2000) // llamamos a la funcion y le pasamos un año
    datePrinter(1,9) // cuando imprimimos esta linea vemos que toma como año el valor por defecto ya que no se lo especificamos

    /*
    2.
    Hacer una función que calcule un número elevado al cuadrado. Un número elevado al
    cuadrado es aquel que se multiplica a si mismo dos veces. Por ejemplo 52 = 5 * 5 = 25.
    Hacer uso de funciones compactas.
     */

    // declaramos una funcion compacta
    fun cuadrado(numero : Int): Int = (numero * numero) // definimos que recibe y devuelve un Int e igualamos con el cuerpo
    println("El resultado de 12 elevado al cuadrado es: " + cuadrado(12))
    println("-----------------------------------------------------------------------")

    /*
    3.
    Hacer una función que calcule el área de un rectángulo. La función debe recibir los
    parámetros requeridos base y altura. El área de un rectángulo es el resultado de hacer el
    producto entre la base y la altura. Hacer uso de funciones compactas.
     */

    // para trabajar con decimales, la funcion la definimos para recibir y retornar con Double
    fun areaRectangulo(base : Double, altura : Double) : Double = (base*altura) // luego del = ponemos el código que iría en el cuerpo
    println("Para un rectangulo de base: 252.6 y altura: 81.12; su area es igual a : " + areaRectangulo(252.6, 81.12))
    println("-----------------------------------------------------------------------")

    /*
    4.
    En el mundo de la física, tenemos las siguientes formulas:
    o velocidad = distancia / tiempo
    o distancia = velocidad * tiempo
    o tiempo = distancia / velocidad
    Haciendo uso de funciones lambdas, realizar tres funciones para calcular:
    a. La velocidad dada una distancia y un tiempo.
    b. La distancia dada una velocidad y un tiempo.
    c. El tiempo dada una distancia y una velocidad.
     */

    // velocidad
    fun velocidad (distancia : Double, tiempo: Int) : Double = (distancia / tiempo)
    println("Dado un vehiculo que recorre 5800 metros en 120 segundos, su velocidad es de: " + velocidad(5800.0, 120) + " m/s")
    println("-----------------------------------------------------------------------")

    // distancia
    fun distancia (velocidad : Double, tiempo : Int) : Double = (velocidad * tiempo)
    println("Dado un vehiculo que se mueve a 50 m/s durante 175 segundos, recorre un total de: " + distancia(50.0, 175) + " metros")
    println("-----------------------------------------------------------------------")

    // tiempo
    fun tiempo (distancia : Double, velocidad : Double) : Double = (distancia/velocidad)
    println("Dada una distancia de 1450 metros y una velocidad de 22 m/s, el tiempo total es: " + tiempo(1450.0, 22.0) + " segundos")
    println("-----------------------------------------------------------------------")

    /*
    5.
    Mejorar el ejercicio anterior para poder resolver los cálculos en una única función de orden alto.
     */

    // funcion lambda para la velocidad
    val lambdaVelocidad = {distancia: Double, tiempo: Int -> distancia / tiempo}

    // funcion lambda para la distancia
    val lambdaDistancia = {velocidad: Double, tiempo: Int -> velocidad*tiempo}

    // funcion lambda para el tiempo
    val lambdaTiempo = {distancia: Double, velocidad: Double -> distancia/velocidad}

    /*
    6.
    Haciendo uso de funciones lambdas, realizar una función que dado un radio, calcule el
    área de un círculo. Sabemos que al área de un circulo es: A = PI * r2 siendo ‘r’ el radio.
     */
    val lambdaAreaCirculo = {radio : Double, pi : Double -> (pi*radio*radio)}
    println("Dado un circulo de radio igual a 12.6, su área es igual a: " + lambdaAreaCirculo(12.6, 3.14159))
    println("-----------------------------------------------------------------------")

    /*
    7.
    Realizar una función que, dado un mes y un año, calcule la cantidad de días de dicho mes.
    El año se utilizará en el caso de que el mes sea febrero ya que los años bisiestos tienen 29
    días en lugar de 28. Un año es bisiesto cuando: (el año es divisible por 4 y NO por 100) o
    (el año es divisible por 400).
     */

    fun calculadoraCantidadDias(month: Int, year: Int) : Int { // definimos los parámetros de la función asi como el tipo de retorno
        if(month == 2){ // primero validamos el mes ingresado
            if(year%4 == 0 && year%100 != 0 || year%400 == 0){ // si es Febrero pasamos a chequear el año
                return 29
            }else
                return 28
        }else if(month == 4 || month == 6 || month == 9 || month == 11){ // los meses con 30 dias son menos de modo que los validamos con un if
            return 30
        }else // en cualquier otro caso retorna 31
            return 31
    }

    println("Cantidad de dias 2/1900: " + calculadoraCantidadDias(2,1900))
    println("Cantidad de dias 2/2000: " + calculadoraCantidadDias(2,2000))
    println("Cantidad de dias 9/1986: " + calculadoraCantidadDias(9,1986))
    println("Cantidad de dias 7/1955: " + calculadoraCantidadDias(7,1955))
    println("-----------------------------------------------------------------------")

    /*
    8.
    Realizar una función que reciba 3 números enteros correspondientes al día, mes y año de
    una fecha y valide si la misma es correcta. En caso de que la fecha es correcta debe
    imprimir “Es correcta”, y si es incorrecta debe imprimir “No es correcta”. Para la validación
    usar la función del punto 7 que retorna la cantidad de días de un mes.
     */

    fun validacionFechas(dia: Int, mes: Int, año: Int) : String{
        if(mes in 1..12){
            if(dia<= calculadoraCantidadDias(mes,año)){
                return "Es correcta"
            }else
                return "Es incorrecta"
        }else
            return "Es incorrecta"
    }
    println("31/11/2000 --> " + validacionFechas(31, 11, 2000)) // Noviembre no puede tener 31 dias
    println("19/11/2000 --> " + validacionFechas(19, 11, 2000))
    println("29/2/2020 --> " + validacionFechas(29, 2, 2020))
    println("29/2/2021 --> " + validacionFechas(29, 2, 2021))
    println("32/12/1999 --> " + validacionFechas(32, 12, 1999))

    /*
    9.
    Realizar un juego donde se deba adivinar un numero generado de forma aleatoria entre 1
    y 100. El jugador deberá ingresar un número y por ingreso se debe validar e informar si el
    numero generado aleatoriamente es menor o mayor. El juego finaliza cuando el jugador
    acierta el número.
     */

    fun generarNumeroAleatorio(): Int{ // esta funcion debe retornar un valor entero
        return Random.nextInt(1..100) // importamos la libreria random para usar nextInt() y especificamos el rango
    }
    val numeroAleatorio = generarNumeroAleatorio() // aca generamos el valor que se debe adivinar
    // es importante hacerlo fuera del do while para no generar un nuevo cada vez que corra el bucle haciendo que sea mucho mas dificil adivinar
    do {
        println("""
            +--- Ingresar un numero para continuar ---+
        """)
        val numeroIngresado = readln().toInt()
        if(numeroIngresado == numeroAleatorio){
            println("Felicitaciones, ha adivinado el número secreto!!!")
        }else
            println("Mmm ese no es el número secreto, intenta nuevamente :)")
    }while(numeroIngresado != numeroAleatorio)
}