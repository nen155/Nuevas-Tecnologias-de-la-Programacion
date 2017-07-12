import OperacionesArbol._
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

/**
  * Created by Emilio Chica Jiménez on 14/05/2017.
  */
@RunWith(classOf[JUnitRunner])
class TestOperacionesArbol extends FunSuite {

  trait TestSets {

    //Prueba inicial
    val pruebaInicial = "AAAAAAAABBBCDEFGH".toList

    // Mensaje secreto a decodificar
    val mensajeSecretoFrances: List[Int] = List(0, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 0, 1, 0,
      0, 1, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1,
      0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1)

    val codigoHuffmanFrances: Nodo = Intermedio(
      Intermedio(
        Intermedio(
          Hoja('s', 121895),
          Intermedio(
            Hoja('d', 56269),
            Intermedio(
              Intermedio(
                Intermedio(
                  Hoja('x', 5928),
                  Hoja('j', 8351),
                  List('x', 'j'), 14279),
                Hoja('f', 16351),
                List('x', 'j', 'f'), 30630),
              Intermedio(
                Intermedio(
                  Intermedio(
                    Intermedio(
                      Hoja('z', 2093),
                      Intermedio(
                        Hoja('k', 745),
                        Hoja('w', 1747),
                        List('k', 'w'), 2492),
                      List('z', 'k', 'w'), 4585),
                    Hoja('y', 4725),
                    List('z', 'k', 'w', 'y'), 9310),
                  Hoja('h', 11298),
                  List('z', 'k', 'w', 'y', 'h'), 20608),
                Hoja('q', 20889),
                List('z', 'k', 'w', 'y', 'h', 'q'), 41497),
              List('x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 72127),
            List('d', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 128396),
          List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q'), 250291),
        Intermedio(
          Intermedio(
            Hoja('o', 82762),
            Hoja('l', 83668),
            List('o', 'l'), 166430),
          Intermedio(
            Intermedio(
              Hoja('m', 45521),
              Hoja('p', 46335),
              List('m', 'p'), 91856),
            Hoja('u', 96785),
            List('m', 'p', 'u'),
            188641),
          List('o', 'l', 'm', 'p', 'u'), 355071),
        List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q', 'o', 'l', 'm', 'p', 'u'), 605362),
      Intermedio(
        Intermedio(
          Intermedio(
            Hoja('r', 100500),
            Intermedio(
              Hoja('c', 50003),
              Intermedio(
                Hoja('v', 24975),
                Intermedio(
                  Hoja('g', 13288),
                  Hoja('b', 13822),
                  List('g', 'b'), 27110),
                List('v', 'g', 'b'), 52085),
              List('c', 'v', 'g', 'b'), 102088),
            List('r', 'c', 'v', 'g', 'b'), 202588),
          Intermedio(
            Hoja('n', 108812),
            Hoja('t', 111103),
            List('n', 't'), 219915),
          List('r', 'c', 'v', 'g', 'b', 'n', 't'), 422503),
        Intermedio(
          Hoja('e', 225947),
          Intermedio(
            Hoja('i', 115465),
            Hoja('a', 117110),
            List('i', 'a'), 232575),
          List('e', 'i', 'a'), 458522),
        List('r', 'c', 'v', 'g', 'b', 'n', 't', 'e', 'i', 'a'), 881025),
      List('s', 'd', 'x', 'j', 'f', 'z', 'k', 'w', 'y', 'h', 'q', 'o', 'l', 'm', 'p', 'u', 'r', 'c', 'v', 'g', 'b', 'n', 't', 'e', 'i', 'a'), 1486387)
  }


  test("El texto de la prueba inicial coincide con el arbol") {
    new TestSets {
      val arbolCodificacion =construirArbolCodificacion(pruebaInicial)
      println(construirArbolCodificacion(pruebaInicial))
      //Un método sencillo aunque no exacto de comprobar que obtengo el resultado correcto
      assert(arbolCodificacion.toString=="Intermedio(Hoja(A,8),Intermedio(Intermedio(Intermedio(Hoja(C,1),Hoja(D,1),List(C, D),2),Intermedio(Hoja(E,1),Hoja(F,1),List(E, F),2),List(C, D, E, F),4),Intermedio(Intermedio(Hoja(G,1),Hoja(H,1),List(G, H),2),Hoja(B,3),List(G, H, B),5),List(C, D, E, F, G, H, B),9),List(A, C, D, E, F, G, H, B),17)","No coinciden!")
    }
  }
  test("La tabla de codificacion coincide") {
    new TestSets {
      val tabla =convertirArbolTabla(construirArbolCodificacion(pruebaInicial))
      println(convertirArbolTabla(construirArbolCodificacion(pruebaInicial)))
      //Un método sencillo aunque no exacto de comprobar que obtengo el resultado correcto
      assert(tabla.toString=="List((A,List(0)), (C,List(1, 0, 0, 0)), (D,List(1, 0, 0, 1)), (E,List(1, 0, 1, 0)), (F,List(1, 0, 1, 1)), (G,List(1, 1, 0, 0)), (H,List(1, 1, 0, 1)), (B,List(1, 1, 1)))","No coinciden!")

    }
  }
  test("La codificación lenta del nodo francés es correcta"){
    new TestSets {
      val codificado =codificacion(codigoHuffmanFrances,"huffmanestcool".toList)
      println(codificacion(codigoHuffmanFrances,"huffmanestcool".toList))
      //Hago el zip de una lista con respecto a la otra para obtener (Int,Int)
      //Compruebo cada elemento coincide con el otro, sino fuera así el contador sería >0
      assert(codificado.zip(mensajeSecretoFrances).count{case (x,y) => x != y}==0,"No coinciden!")
    }
  }
  test("La codificación rápida del nodo francés es correcta"){
    new TestSets {
      val tablaHuffman:TablaCodigo = convertirArbolTabla(codigoHuffmanFrances)
      val codificado =codificacionRapida(codigoHuffmanFrances,"huffmanestcool",tablaHuffman)
      println(codificacionRapida(codigoHuffmanFrances,"huffmanestcool",tablaHuffman))
      //Hago el zip de una lista con respecto a la otra para obtener (Int,Int)
      //Compruebo cada elemento coincide con el otro, sino fuera así el contador sería >0
      assert(codificado.zip(mensajeSecretoFrances).count{case (x,y) => x != y}==0,"No coinciden!")
    }
  }
  test("La decodificacion  del arbol francés es correcta"){
    new TestSets {
      val codificado =decodificar(codigoHuffmanFrances,mensajeSecretoFrances)
      println(decodificar(codigoHuffmanFrances,mensajeSecretoFrances))
      //Compruebo si ha descodificado bien
      assert(codificado.toString=="huffmanestcool","No coinciden!")

    }
  }
}
