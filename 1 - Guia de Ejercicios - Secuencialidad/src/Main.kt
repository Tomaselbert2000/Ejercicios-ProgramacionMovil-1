// Guia de ejercicios Kotlin

fun main() {
    // 1 --> imprimir por consola el mensaje "Hola Mundo"
    println("Hola Mundo!")

    /*
    2 --> leer por consola el nombre y la edad de un usuario e imprimir
    un mensaje con el siguiente formato "La persona [nombre] tiene [edad] años"
     */
    print("+--- Ingresar nombre ---> ")
    val nombre = readln()
    print("+--- Ingresar edad ---> ")
    val edad = readln()
    println("La persona $nombre tiene $edad años de edad") // y aca lo imprimimos por consola

    // 3 --> dadas las horas trabajadas y el valor por hora de un empleado, determinar su sueldo
    print("+--- Calculadora de sueldos ---+\n")
    print("+--- Ingresar cantidad de horas --> ")
    val cantidad = readln().toInt()
    print("+--- Ingresar precio por hora ---> ")
    val precio = readln().toDouble()
    val total = cantidad*precio
    println("El resultado total es ---> $total")

    // 4 --> dadas las notas de dos evaluaciones de un alumno, determinar la nota promedio

    print("Promedios de alumnos\n")
    print("Ingresar nota de 1er parcial: ")
    val nota1 = readln().toDouble()
    print("Ingresar nota de 2do parcial: ")
    val nota2 = readln().toDouble()
    val promedio = (nota1+nota2)/2
    println("El promedio del alumno es $promedio\n")

    // 5 --> Dado un numero determinar si es par o no

    println("Ingresar un numero para verificar si es par o no --> ")
    val numero = readln().toInt()
    if(numero%2==0){
        print("El numero ingresado es par.\n")
    }else print("El numero ingresado es impar\n")

    // 6 --> Dado tres números determinar e informar cual es el mayor.

    print("Numeros - Mayor y menor\n")
    print("Ingresar 1er valor: ")
    val num1 = readln().toInt()
    print("Ingresar 2do valor: ")
    val num2 = readln().toInt()
    print("Ingresar 3er valor: ")
    val num3 = readln().toInt()

    // aca validamos cual de los 3 es mayor
    if(num1>num2 && num1>num3){
        print("El valor más alto es $num1\n")
    }else if(num2>num1 && num2>num3){
        print("El valor más alto es $num2\n")
    }else if(num3>num1 && num3>num2){
        print("El valor más alto es $num3\n")
    }

    // 7 --> Una farmacia vende algunos artículos sin descuento y a otros con descuento del 20%.
    //Confeccionar un programa que recibiendo el precio original y un código que indica si es o
    //no con descuento, informe el precio final (0 no aplica el descuento y 1 aplica el
    //descuento).

    println("+-- Descuentos para farmacias v1.0 --+\n")
    print("\nPor favor, ingrese el precio original del producto $ ")
    val precioOriginal = readln().toDouble()
    print("¿Debe aplicarse descuento al precio ingresado?\n1 --> Si\n0 --> No\n--> ")
    val descuento = readln().toInt()
    if(descuento == 1){
        val precioFinal = precioOriginal-(precioOriginal*0.20)
        print("El precio final a abonar es de $$precioFinal\n")
    }else
        print("El precio final a abonar es de $$precioOriginal\n")

    /*
    8.
    Un fabricante de repuestos para tractores ha descubierto que ciertos artículos
    identificados por los números de catálogo 12121 al 18081; 30012 al 45565 y 67000 al
    68000 son defectuosos. Se desea confeccionar un programa al que informándole el
    número de catálogo indique si el artículo es o no defectuoso. Los artículos del catálogo van
    desde el 1200 al 90000. Si se ingresa otro número informar “FUERA DE CATALOGO”.
     */

    println("+--- Repuestos para tractores ---+\n")
    print("Para verificar el estado de un repuesto, ingrese su código a continuacion: ")
    val nroRepuesto = readln().toInt()

    if(nroRepuesto !in 1200..90000){
        print("El codigo de repuesto proporcionado no existe.\n")
    }else if((nroRepuesto in 12121..18081) || (nroRepuesto in 30012..45565) || (nroRepuesto in 67000..68000)){
        print("Atencion: el repuesto indicado es defectuoso.\n")
    }else
        print("El repuesto ingresado no presenta fallas a la fecha.\n")

    /*
    9.
    La farmacia SALUD efectúa descuentos a sus afiliados según el importe de la compra con la
    siguiente escala:
    a. menor de $55 el descuento es del 4.5%.
    b. entre $55 y $100 el descuento es del 8%.
    c. más de $100 el descuento es del 10.5%.
     */

    println("+--- Farmacia SALUD ---+")
    print("Bienvenido. Por favor, ingrese a continuacion el monto a abonar: $ ")
    var montoOriginal = readln().toDouble()
    if(montoOriginal<55){
        val descuento = (montoOriginal*0.045)
        montoOriginal -= descuento
        print("Se ha aplicado un descuento del 4.5% en el monto ingresado, el monto final es $ $montoOriginal\n")
    }else if(montoOriginal in 55.0..100.0){
        val descuento = (montoOriginal*0.08)
        montoOriginal -= descuento
        print("Se ha aplicado un descuento del 8% sobre el monto ingresado, el monto final es $ $montoOriginal\n")
    }else if(montoOriginal>100){
        val descuento = (montoOriginal*0.105)
        montoOriginal -= descuento
        print("Se ha aplicado un descuento del 10.5% sobre el monto ingresado, el monto final es $ $montoOriginal\n")
    }
}
