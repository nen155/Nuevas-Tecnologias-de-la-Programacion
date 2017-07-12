import org.junit._
import org.junit.Test
import org.junit.Assert._

/**
  * Created by Emilio Chica Jiménez on 14/04/2017.
  */
class MainTest {
  @Before
  @throws[Exception]
  def setUp(): Unit = {
  }

  @org.junit.Test
  @throws[Exception]
  def testbusquedaBinariaRecursiva(): Unit = {
    val lista = List(1, 2, 4, 5, 6, 8, 9, 25,31)
    //Compruebo que se encuentra en la posicion 0
    assertEquals("No busca bien",0,Main.busquedaBinariaRecursiva(lista,1,(x:Int,y:Int)=>
      if(x<y) 1
      else if(x>y) -1
      else 0))
    //Compruebo que no esta
    assertEquals("No busca bien",-1,Main.busquedaBinariaRecursiva(lista,0,(x:Int,y:Int)=>
      if(x<y) 1
      else if(x>y) -1
      else 0))
    val listaString = List("Access","Excel","Outlook","Word")
    //Compruebo el método con Strings
    assertEquals("No busca bien",1,Main.busquedaBinariaRecursiva(listaString,"Excel",(x:String,y:String)=>
      x.compareTo(y)))
    //Compruebo el método con Strings
    assertEquals("No busca bien",-1,Main.busquedaBinariaRecursiva(listaString,"Note",(x:String,y:String)=>
      x.compareTo(y)))

  }

  @Test
  @throws[Exception]
  def testchequearBalance(): Unit = {
    //Listas balanceadas
    val lista1 = "(if (a ¿ b) (b/a) else (a/(b*b)))"
    val  lista2 = "(ccc(ccc)cc((ccc(c))))"
    //Listas no balanaceadas
    val lista3 = "(if (a ¿ b) b/a) else (a/(b*b)))"
    val lista4 = "(ccc(ccccc((ccc(c))))"
    val lista5= "())()())"
    val lista6 = "())("
    //Compruebo listas balanceadas
    assertTrue("No cuenta bien los parentesis",Main.chequearBalance(lista1.toCharArray.toList))
    assertTrue("No cuenta bien los parentesis",Main.chequearBalance(lista2.toCharArray.toList))
    //Compruebo listas no balanceadas
    assertFalse("No cuenta bien los parentesis",Main.chequearBalance(lista3.toCharArray.toList))
    assertFalse("No cuenta bien los parentesis",Main.chequearBalance(lista4.toCharArray.toList))
    assertFalse("No cuenta bien los parentesis",Main.chequearBalance(lista5.toCharArray.toList))
    assertFalse("No cuenta bien los parentesis",Main.chequearBalance(lista6.toCharArray.toList))
  }

  @Test
  @throws[Exception]
  def testcontarCambiosPosibles(): Unit = {
    val cantidad = 4
    val monedas = List(1, 2)
    assertEquals("No cuenta bien los cambios posibles",3,Main.contarCambiosPosibles(cantidad,monedas))
  }

  @Test
  @throws[Exception]
  def testcalcularValorTrianguloPascal(): Unit = {
   assertEquals("No calcula bien el triangulo de pascal",3003,Main.calcularValorTrianguloPascal(10,15))
  }
}