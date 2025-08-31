fun main() {

    // Guia de ejercicios - Loops

    // 1. Mostrar en pantalla los numeros pares comprendidos entre 100 y 200

    for(i in 100..200){
        if(i%2 == 0){
            println(i)
        }
    }

    /*
    2.
    Confeccionar un programa para calcular la suma de los primeros N números naturales.
    Ejemplo: Si N es 5, el resultado seria 15 (1 + 2 + 3 + 4 + 5 = 15)
     */

    println("Suma de numeros naturales\n")
    print("Por favor ingresar un numero entero: ")
    val numero = readln().toInt()
    var sumatoria = 0 // creamos el acumulador para la sumatoria, tiene que ser de tipo var porque lo vamos a modificar
    for(i in 1..numero){ // ciclamos tantas veces como elementos haya que sumar al acumulador
        sumatoria += i
    }
    print("El resultado de sumar todos los numeros consecutivos desde 1 hasta $numero es $sumatoria")

    /*
    3.
    Calcular el factorial de un número ingresado por teclado. El factorial se calcula como el
    producto de todos los enteros positivos desde 1 hasta el número. En matemática el
    factorial se expresa con el símbolo !. Por ejemplo, el factorial de 5 es 120 ya que 5! = 1 x 2
    x 3 x 4 x 5 =120
     */

    println("\nCalculo de factoriales\n")
    print("Por favor ingresar un numero entero: ")
    val valor = readln().toInt()
    var factorial = 1
    for(i in 1..numero){
        factorial*=i
    }
    print("El resultado del factorial de $valor es $factorial")

    /*
    4.
    Dado un numero N, Imprimir N veces “Hola mundo”. Realizarlo de 3 formas distintas.
    Ejemplo: Si N es 10, se deberá imprimir 10 veces “Hola mundo”
     */

    // Forma 1
    println("\nImprimir mensajes usando do-while\n")
    var cantidadVecesDoWhile:Int? = null // primero declaramos como null la variable para la cantidad
    do {
        println("Ingresar un numero N para imprimir un mensaje ")
        cantidadVecesDoWhile = readln().toInt() // le pedimos al usuario un numero aca
        if(cantidadVecesDoWhile<=0){ // validamos si es menor o igual a cero
            println("El valor ingresado no puede ser menor o igual a cero, intente nuevamente.") // si lo es, se muestra en pantalla la advertencia
        }
    }while (cantidadVecesDoWhile <=0) // mientras sea menor o igual a cero, el ciclo sigue corriendo

    for(i in 1..cantidadVecesDoWhile){ // una vez tenemos el valor, lo usamos para limitar el rango
        println("Hello World!") // y mostramos en pantalla el mensaje
    }

    // Forma 2
    println("\nImprimir mensajes usando For\n")
    var cantidadVecesFor: Int?
    do {
        println("Ingresar un numero N para imprimir un mensaje ")
        cantidadVecesFor = readln().toInt()
        if(cantidadVecesFor<=0){
            println("El valor ingresado no puede ser menor o igual a cero, intente nuevamente.")
        }
    }while (cantidadVecesFor <=0)
    for(i in 1..cantidadVecesFor){
        println("Hello World!")
    }

    // Forma 3
    println("\nImprimir mensajes usando Repeat\n")
    var cantidadVecesRepeat: Int?
    do {
        println("Ingresar un numero N para imprimir un mensaje ")
        cantidadVecesRepeat = readln().toInt()
        if(cantidadVecesRepeat<=0){
            println("El valor ingresado no puede ser menor o igual a cero, intente nuevamente.")
        }
    }while (cantidadVecesRepeat<=0)

    repeat(cantidadVecesRepeat){
        println("Hello World!")
    }

    /*
    5.
    Realizar un programa que muestre por pantalla las tablas de multiplicar del 1 al 9 de la
    siguiente forma:
    1  2  3  4  5  6  7  8  9
    2  4  6  8 10 12 14 16 18
    3  6  9 12 15 18 21 24 27
    4  8 12 16 20 24 28 32 36
    5 10 15 20 25 30 35 40 45
    6 12 18 24 30 36 42 48 54
    7 14 21 28 35 42 49 56 63
    8 16 24 32 40 48 56 64 72
    9 18 27 36 45 54 63 72 81
     */
    println("+--- Tablas de multiplicar ---+\n")
    var tabla = 1 // con este valor controlamos la tabla que se debe mostrar
    while(tabla<=10){ // abrimos un while que ciclara una vez por cada tabla
        for(i in 1..10){ // para cada tabla, recorremos todos los resultados
            print("" + tabla*i + " ")
        }
        println() // agregamos un salto de linea
        tabla++ // se incrementa aca para continuar con la siguiente tabla
    }

    /*
    6.
    Leer una serie de números hasta que el usuario ingrese 0 (cero) y determinar cuál es el
    mayor. (Nota: Todos los números deben ser mayor a 0 (cero), en caso de ser menor,
    ignorarlo).
     */

    /*
    7.
    Realizar un programa que muestre un menú en pantalla con las opciones:
    1. Sumar
    2. Restar
    3. Multiplicar
    4. Dividir
    5. Salir
    El usuario debe seleccionar una opción y a continuación el programa debe solicitar el
    ingreso de 2 números enteros. Una vez ingresados realizar la operación correspondiente e
    informar el resultado. En caso de que el valor no se encuentre entre 1 y 5 informar un
    mensaje de error.
     */

    println("+--- Calculadora Basica v1.0 ---+\n")
    var opcionSeleccionada: Int?
    println("""
        --- Menu Principal ---
        ----------------------
        1. Sumar
        2. Restar
        3. Multiplicar
        4. Dividir
        5. Salir del programa
        ----------------------
    """.trimIndent())

    do {
        println("Ingresar una opcion para continuar: ")
        opcionSeleccionada = readln().toInt()
        if(opcionSeleccionada !in 1..5){
            println("El valor ingresado no corresponde con una opcion del menu. Intente nuevamente.\n")
        }
    }while (opcionSeleccionada !in 1..5)

    println("Ingresar un valor: ")
    val num1 = readln().toInt()
    println("Ingresar un valor: ")
    val num2 = readln().toInt()
    // una vez seleccionada la opcion del menu, pasamos al bloque de IFs
    if(opcionSeleccionada == 1){
        val suma = num1 + num2
        println("El resultado de la suma es --> $suma")
    }else if(opcionSeleccionada == 2){
        val resta = num1 - num2
        println("El resultado de la resta es --> $resta")
    }else if(opcionSeleccionada == 3){
        val producto = num1 * num2
        println("El resultado del producto es --> $producto")
    }else if(opcionSeleccionada == 4){
        if(num2 != 0){
            val cociente = num1 / num2
            println("El resultado del cociente es --> $cociente")
        }else
            println("No es posible dividir por cero.")
    }else {
        println("--- Programa finalizado por el usuario ---")
    }
}