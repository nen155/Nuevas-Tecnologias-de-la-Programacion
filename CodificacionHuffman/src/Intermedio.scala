/**
  * Created by Emilio Chica Jiménez on 13/05/2017.
  */
case class Intermedio(nodoI: Nodo, nodoD: Nodo, listaCarateres: List[Char], peso: Int) extends Nodo{
  /**
    * Método recursivo que busca obtener el peso de un nodo Intermedio
    * @return
    */
  override def calcularPeso(): Int = {
    nodoI.calcularPeso()+nodoD.calcularPeso()
  }

  /**
    * Método recursivo que busca obtener los caracteres de un nodo Intermedio
    * @return
    */
  override def obtenerCaracteres(): List[Char] = {
    nodoI.obtenerCaracteres():::nodoD.obtenerCaracteres()
  }
}
