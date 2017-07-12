/**
  * Created by Emilio Chica Jiménez on 13/05/2017.
  */
case class Hoja(caracter: Char, peso: Int) extends Nodo{
  /**
    * Devuelve el peso asignado a la hoja
    * @return
    */
  override def calcularPeso(): Int = {
    peso
  }

  /**
    * Obtiene el caracter asignado como una lista
    * @return
    */
  override def obtenerCaracteres(): List[Char] = {
    List(caracter)
  }
}
