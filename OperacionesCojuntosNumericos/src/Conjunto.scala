/**
  * Created by Emilio Chica Jiménez on 05/05/2017.
  */
/**
  * Clase para representar conjuntos definidos mediante una funcion
  * caracteristica (un predicado). De esta forma, se declara el tipo
  * conjunto como un predicado que recibe un entero (elemento) como
  * argumento y dvuelve un valor booleano que indica si pertenece o no
  * al conjunto
  * @param funcionCaracteristica
  * */
class Conjunto (val funcionCaracteristica: Int => Boolean) {

  /** Crea una cadena con el contenido completo del conjunto
    * @return
    * */
  override def toString():String =
  {
    val elementos = for (i <- Conjunto.LIMITE to Conjunto.LIMITE
                         if funcionCaracteristica(i))yield i
                        elementos.mkString("{", ",", "}")
  }
  /**
    * Metodo para determinar la pertenencia de un elemento al
    * conjunto
    * @param elemento
    * @return valor booleano indicando si elemento cumple
    * la funcion caracteristica o no
    **/
  def apply(elemento: Int): Boolean = {  funcionCaracteristica(elemento)  }
}
/**
  *  Objecto companion que ofrece metodos para trabajar con
  *  conjuntos
*/
object Conjunto{
  /**
    *  Limite para la iteracion necesaria algunas operaciones,
    *  entre -1000 y 1000
    *  */
  private final val LIMITE = 1000

  /**
    *  Metodo que permite crear objetos de la clase Conjunto
    *  de forma sencilla
    *  @param f
    *  @return
    *  */
  def apply(f: Int => Boolean): Conjunto = {
    new Conjunto(f)
  }

  /**
    * Creación de conjunto con un único elemento
    * @param elemento
    * @return
    */
  def conjuntoUnElemento(elemento : Int) : Conjunto={
    Conjunto((x:Int) => x == elemento)
  }

  /**
    * Unión de dos conjuntos
    * @param c1
    * @param c2
    * @return
    */
  def union(c1 : Conjunto, c2 : Conjunto) : Conjunto={
    Conjunto((x:Int) => c1(x) || c2(x))
  }

  /**
    * Intersección de dos conjuntos
    * @param c1
    * @param c2
    * @return
    */
  def interseccion(c1 : Conjunto, c2 : Conjunto) : Conjunto={
    Conjunto((x:Int) => c1(x) && c2(x))
  }

  /**
    * Diferencia entre dos conjuntos
    * @param c1
    * @param c2
    * @return
    */
  def diferencia(c1 : Conjunto, c2 : Conjunto) : Conjunto={
    Conjunto((x:Int) => c1(x) && !c2(x))
  }

  /**
    * Filtrado de conjuntos
    * @param c
    * @param predicado
    * @return
    */
  def filtrar(c : Conjunto, predicado : Int => Boolean) : Conjunto={
    Conjunto((x:Int) => predicado(x))
  }

  /**
    * Comprueba si un determinado predicado se cumple para todos los elementos del conjunto
    * @param conjunto
    * @param predicado
    * @return
    */
  def paraTodo(conjunto : Conjunto, predicado : Int => Boolean) : Boolean = {
    def iterar(elemento : Int) : Boolean = {
      //Caso base1: Si hemos llegado al limite superior significa que todos los elementos pertenecen al conjunto
      if(elemento>LIMITE)
        true
        //Caso base 2: Que un elemento no pertenezca al conjunto por lo que deberiamos pasar al siguiente
      else if (!conjunto(elemento))
        iterar(elemento+1)
        //Sino, compruebo que cumple el predicado y paso al siguiente elemento en ese caso
      else predicado(elemento) && iterar(elemento+1)
    }
    iterar(-LIMITE)
  }

  /**
    * Determina si un conjunto contiene al menos un elemento para el que se cumple un cierto predicado
    * @param c
    * @param predicado
    * @return
    */
  def existe(c : Conjunto, predicado : Int => Boolean) : Boolean ={
      // Esto significa que si hay un sólo elemento que devuelva false como el predicado va a devolver false
      // el algoritmo de paraTodo va a delvolver false pues no se cumple que sea para todos, por
      // lo que, vamos a obtener significa que hay un elemento al menos que cumple el predicado por lo tanto
      // como no se cumple para todos nos devuelve false por lo que lo niego pues es cierto que existe un elemento
      !paraTodo(c, (x:Int) => !predicado(x))
  }

  /**
    * Transforma un conjunto en otro aplicando una cierta función
    * @param c
    * @param funcion
    * @return
    */
  def map(c : Conjunto, funcion : Int => Int) : Conjunto={
    //Comprueba si para cada elemento x existe al menuos un elemento y que aplicada la funcion concreta
    //sea igual que y, se utiliza existe porque no todos los elementos tienen que ser iguales entre sí,
    //por lo que paraTodo no nos serviría
    Conjunto((x:Int) => existe(c,(y:Int)=> funcion(y)==x))
  }


}