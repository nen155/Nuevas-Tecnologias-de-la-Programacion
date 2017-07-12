
/**
  * Created by Emilio Chica Jiménez on 14/04/2017.
  */
object Main {

  /**
    * Metodo main: en realidad no es necesario porque el desarrollo
    * deberia guiarse por los tests de prueba
    *
    * @param args
    */
  def main(args: Array[String]) {
    println("................... Triangulo de Pascal ...................")

    // Se muestran 10 filas del trinagulo de Pascal
    for (row <- 0 to 10) {
      // Se muestran 10 10 columnas
      for (col <- 0 to row)
        print(calcularValorTrianguloPascal(col, row) + " ")

      // Salto de linea final para mejorar la presentacion
      println()
    }

    // Se muestra el valor que debe ocupar la columna 5 en la fila 10
    println(calcularValorTrianguloPascal(10, 15))
    println(calcularValorTrianguloPascal(0, 0))
    val cantidad = 8
    val monedas = List(1, 2)
    println(contarCambiosPosibles(cantidad, monedas))

    val l = List(1, 2, 4, 5, 6, 8, 9, 25,31)

    val resultado = busquedaBinariaRecursiva(l,0,(x:Int,y:Int)=>
      if(x<y) 1
      else if(x>y) -1
      else 0)

    println(resultado)

  }

  /**
    * Ejercicio 1: funcion para generar el triangulo de Pascal
    *
    * @param columna
    * @param fila
    * @return
    */
  def calcularValorTrianguloPascal(columna: Int, fila: Int): Int = {
    //Caso base 1 cuando estoy en el inicio del triangulo ->1 2 1
    if (columna == 0)
      1
    //Caso base 2 cuando llego al final del triangulo 1 2 ->1
    else if (columna == fila)
      1
    else
    //La fila superior y el elemento anterior a la columna que estoy y el elemento de la fila anterior y la misma columna
      calcularValorTrianguloPascal(columna - 1, fila - 1) + calcularValorTrianguloPascal(columna, fila - 1)
  }

  /**
    * Ejercicio 2: funcion para chequear el balance de parentesis
    *
    * @param cadena cadena a analizar
    * @return valor booleano con el resultado de la operacion
    */
  def chequearBalance(cadena: List[Char]): Boolean = {
    @annotation.tailrec
    def iterar(cadena: List[Char], abiertos: Int): Boolean = {
      //Condición para saber si hemos vaciado la cadena,
      // si tuviesemos parentesis abiertos despues de eso delvolverá false
      if (cadena.isEmpty)
        abiertos == 0
      else
      // Añadimos uno a abiertos porque hemos encontrado uno abierto
        if (cadena.head == '(') iterar(cadena.tail, abiertos + 1)
      else
      // Quitamos uno de cerrados si cumple la condición de que no había previamente abiertos,
      // sino devuelvo false
      if (cadena.head == ')') abiertos > 0 && iterar(cadena.tail, abiertos - 1)
      else
      // Sino es parentesis reduzco la cadena y quito la cabeza
        iterar(cadena.tail, abiertos)
    }

    //Empiezo a iterar por la cadena con 0 parentesis abiertos
    iterar(cadena, 0)
  }

  /**
    * Ejercicio 3: funcion para determinar las posibles formas de devolver el
    * cambio de una determinada cantidad con un conjunto de monedas
    *
    * @param cantidad
    * @param monedas
    * @return contador de numero de vueltas posibles
    */
  def contarCambiosPosibles(cantidad: Int, monedas: List[Int]): Int = {
    //Caso base 1 si ya no tengo monedas
    if (cantidad == 0)
      1
    //Caso base 2 si he quitado de más a la cantidad de monedas
    else if (cantidad < 0)
      0
    //Caso base 3 si he terminado de contar y me queda 1
    else if (monedas.isEmpty && cantidad >= 1)
      0
    else
    // Si aun tengo monedas que contar llamo quitando el valor de la cabeza de monedas a la cantidad y sumandole lo que viene por la cola
      contarCambiosPosibles(cantidad - monedas.head, monedas) + contarCambiosPosibles(cantidad, monedas.tail)
  }

  /**
    * Hace una busqueda binaria recursiva por la cola para cualquier tipo de valores
    * Devuelve la posición del elemento en el array sino lo encuentra -1
    * @param array Debe estar ordenado previamente
    * @param valor
    * @param comparar
    * @tparam A
    * @return
    */
  def busquedaBinariaRecursiva[A](array: List[A], valor: A, comparar: (A, A) => Int): Int = {
    @annotation.tailrec
    def buscar(inicio: Int, fin: Int):Int = {
      if (inicio < fin) {
        //Calculo el valor medio
        val medio: Int = (inicio / 2) + (fin / 2)
        //Comparo si el valor medio coincide
        val resComparar: Int = comparar(array(medio), valor)
        //Si son iguales es el valor buscado
        if(resComparar==0)
          medio
        else
        //Si el valor es menor que el medio,significa que está en la mitad inferior menos el valor central
        if (resComparar < 0)
          buscar(inicio, medio - 1)
        //Si el valor es mayor que el medio,significa que está en la mitad superior menos el valor central
        else
          buscar(medio + 1, fin)
      }
        //Si el inicio y fin coinciden compruebo si el valor es el de la intersección
      else if(inicio==fin && comparar(array(inicio), valor)==0)
        inicio
      else
      //Sino el valor no se encuentra en la lista
        -1

    }
    //Llamada inicial al método
    buscar(0,array.size-1)
  }
}
