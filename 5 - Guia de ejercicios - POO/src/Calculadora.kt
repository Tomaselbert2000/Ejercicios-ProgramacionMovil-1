object Calculadora {

    // funcion suma
    fun sumar(num1: Int, num2 : Int):Int{
        return num1+num2
    }

    // funcion resta
    fun restar(num1: Int, num2 : Int):Int{
        return num1-num2
    }

    //funcion producto
    fun producto(num1: Int, num2 : Int):Int{
        return num1*num2
    }

    // funcion division
    fun division(num1: Double, num2 : Double): Double? = if (num2 != 0.0){
        num1/num2
    }else{
        null
    }
}