/**
  * Created by Emilio Chica Jiménez on 13/05/2017.
  */
object OperacionesArbol{
  //Tabla que simplifica la busqueda de códigos de un caracter
  type TablaCodigo=List[(Char, List[Int])]

  /**
    * Crea un nodo a partir de un caracter de la cadena de texto
    * @param caracter
    * @param texto
    * @return
    */
  def crearNodo(caracter:Char,texto:List[Char]):Nodo={
    Hoja(caracter, texto.count( _ ==caracter))
  }

  /**
    * Ordena una lista de nodos
    * @param nodos
    * @return
    */
  def ordenarNodos(nodos:List[Nodo]):List[Nodo]={
    nodos.sortWith((nA:Nodo,nB:Nodo)=>{nA.calcularPeso()<nB.calcularPeso()})
  }

  /**
    * Comprueba si una lista de nodos tiene un solo elemento
    * @param nodos
    * @return
    */
  def singleton(nodos:List[Nodo]):Boolean={
    nodos.length==1
  }

  /**
    * Repite la función de combinar hasta que se cumpla el predicado ultimo
    * Devuelve el nodo raíz de las combinaciones de la lista pasada por parámetro
    * @param ultimo
    * @param combinar
    * @param listaNodos
    * @return
    */
  def repetir(ultimo:List[Nodo]=>Boolean, combinar:List[Nodo]=>List[Nodo])(listaNodos:List[Nodo]):List[Nodo]={
    if(ultimo(listaNodos))
      listaNodos
    else
      repetir(ultimo,combinar)(combinar(listaNodos))
  }

  /**
    * Con una lista de nodos pasada por parámetro construye un árbol
    * @param listaNodos
    * @return
    */
  def generarArbol(listaNodos:List[Nodo]):Nodo={
    repetir(singleton,combinar)(listaNodos).head
  }
  /**
    * Crea una lista ordenada con un nodo Intermedio que crea con los dos nodos con menos peso de la lista
    * @param nodos
    * @return
    */
  def combinar(nodos:List[Nodo]):List[Nodo]={
    //Cojo de la lista de nodos aquellos dos con menos peso
      val n1 = nodos.head
      val n2 = nodos.tail.head
    //Elimino de la lista los nodos
    val listaSinNodos =nodos.tail.tail
    //Creo un nodo intermedio y lo añado a la lista y la ordeno
    ordenarNodos( listaSinNodos :+ Intermedio(n1, n2, n1.obtenerCaracteres():::n2.obtenerCaracteres(), n1.calcularPeso()+n2.calcularPeso()))
  }
  /**
    * Constuye un árbol de codificación a partir de una lista de caracteres
    * @param texto
    * @return
    */
  def construirArbolCodificacion(texto:List[Char]):Nodo={
    /*PASOS A SEGUIR DEL ALGORITMO
    1. Crear un nodo hoja para cada símbolo, asociando un peso según su frecuencia de aparición e insertarlo en la lista ordenada ascendentemente.
    2. Mientras haya más de un nodo en la lista:
      1. Eliminar los dos nodos con menor probabilidad de la lista.
      2. Crear un nuevo nodo interno que enlace a los nodos anteriores, asignándole como peso la suma de los pesos de los nodos hijos.
      3. Insertar el nuevo nodo en la lista, (en el lugar que le corresponda según el peso).
    4. El nodo que quede es el nodo raíz del árbol.
    */
    //Añado los nodos a la lista
    val listaNodosOrdenados = ordenarNodos(texto.distinct.map((c)=>{crearNodo(c,texto)}))
    //Repito hasta que sólo quede un nodo que será la raíz
    repetir(singleton,combinar)(listaNodosOrdenados).head
  }

  /**
    * Permite decodificar una lista de enteros huffman a una cadena
    * Lo he hecho a String porque me parece más elegante que una List[Char]
    * @param nodoRaiz
    * @param lista
    * @return
    */
  def decodificar(nodoRaiz:Nodo,lista:List[Int]):String = {
      //Compruebo con match si el nodo pasado por parámetro coincide con Hoja o Intermedio
      @annotation.tailrec
      def concatenar(nodo:Nodo,lista:List[Int],cadena:String):String=  nodo match{
        case Hoja(caracter,_) => {
          //Si la lista está vacía significa que he llegado a el final y sólo me queda añadir
          //el caracter de esta hoja
          if(lista.isEmpty)
            cadena+caracter
          else
          //Vuelvo al nodo raiz para volver a empezar la busqueda con el siguiente
          //código y añado el caracter de esta hoja a la cadena
            concatenar(nodoRaiz,lista,cadena+caracter)
        }
        case Intermedio(nodoI,nodoD,listaCarateres,_)=>{
          //Por si tenemos problemas y hemos vacíado ya la lista devolvemos la cadena
          if(lista.isEmpty)
            cadena
          else {
            //Si la cabeza es un cero -->Voy al nodo de la izquierda y quito uno de los elementos del array
            if (lista.head == 0)
              concatenar(nodoI, lista.tail, cadena)
              //Sino me voy a la derecha y quito uno de los elementos del array
            else
              concatenar(nodoD, lista.tail, cadena)
          }
        }
      }
    //Comienzo la concatenación!
    concatenar(nodoRaiz,lista,"")
  }

  /**
    * Codifica una lista de caracteres a código huffman
    * @param nodoRaiz
    * @param lista
    * @return
    */
  def codificacion(nodoRaiz:Nodo,lista:List[Char]): List[Int]={
    //Compruebo con match si el nodo pasado por parámetro coincide con Hoja o Intermedio
    @annotation.tailrec
    def concatenar(nodo:Nodo,lista:List[Char],codigo:List[Int]):List[Int]=  nodo match{
      case Hoja(caracter,_) => {
        //Si la lista está vacía significa que he llegado a el final y devuelvo el código
        if(lista.isEmpty)
          codigo
        else
        //Sino vuelvo al nodo raiz para volver a buscar porque ya no tengo nada que añadir y quito un caracter de la lista
          concatenar(nodoRaiz,lista.tail,codigo)
      }
      case Intermedio(nodoI,nodoD,listaCarateres,_)=>{
        //Si la lista está vacía significa que he llegado a el final y devuelvo el código
        if(lista.isEmpty)
          codigo
        else {
          //Si el nodo izquierdo contiene el siguiente caracter añado un 0 al código
          if (nodoI.obtenerCaracteres().contains(lista.head))
            concatenar(nodoI, lista, codigo :+ 0)
          else
          //Sino añado un 1 porque estará en la parte derecha
            concatenar(nodoD, lista, codigo :+ 1)
        }
      }
    }
    //Comienzo la concatenación!
    concatenar(nodoRaiz,lista,List())
  }

  /**
    * Convierte el arbol de codificación a una tabla
    * @param arbolCodificacion
    * @return
    */
  def convertirArbolTabla(arbolCodificacion : Nodo) : TablaCodigo={

    ///Hago un flatMap de los caracteres a una variable de tipo TablaCodigo para obtener una sola lista de filas
    //con clave Char y valor List[Int]
    val tablaCodigo:TablaCodigo = arbolCodificacion.obtenerCaracteres().flatMap((c)=>{
      List((c,codificacion(arbolCodificacion,List(c))))
      })
    tablaCodigo
  }

  /**
    * Codifica un caracter con una tabla pasada por parámetro
    * @param tabla
    * @param caracter
    * @return
    */
  def codificarConTabla(tabla : TablaCodigo)(caracter : Char) : List[Int] ={
    //Busco el elemento en la tabla que sea igual al caracter como me devuelve un Option hago un get
    //porque estoy seguro de que el caracter va a encontrarse y cojo la parte 2 de la fila
    tabla.find((fila)=>{fila._1==caracter}).get._2
  }

  /**
    * A este método le he cambiado la signatura porque no tiene lógica que no tenga ya creada la
    * tabla de codificaciones antes de usarlo, para así que sea eficiente
    * @param arbolCodificacion
    * @param texto
    * @param tabla
    */
  def codificacionRapida(arbolCodificacion : Nodo, texto:String, tabla : TablaCodigo): List[Int] ={
      texto.toList.flatMap((c)=>{codificarConTabla(tabla)(c)})
  }
}
