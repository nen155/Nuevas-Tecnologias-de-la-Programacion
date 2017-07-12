package test;

import listado.Departamento;
import listado.Division;
import listado.Listado;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Práctica 1 NTP
 * @author Emilio Chica Jiménez
 */
public class ListadoTest {
    private static Listado listado;

    /**
     * Codigo a ejecutar antes de realizar las llamadas a los métodos
     * de la clase; incluso antes de la propia instanciación de la
     * clase. Por eso el método debe ser estatico
     */
    @BeforeClass
    public static void inicializacion() {
        System.out.println("Metodo inicializacion conjunto pruebas");
        // Se genera el listado de empleados
        try {
            listado = new Listado("data/datos.txt");
        }catch(IOException e){
            System.out.println("Error en lectura de archivo de datos");
        };

        // Una vez disponibles los empleados se leen las listas
        // de asignaciones de empleados a cada grupo de las diferentes
        // asignaturas consideradas
        try {
            listado.cargarArchivoAsignacionDivision("data/asignacionDIVNA.txt");
            listado.cargarArchivoAsignacionDivision("data/asignacionDIVID.txt");
            listado.cargarArchivoAsignacionDivision("data/asignacionDIVSW.txt");
            listado.cargarArchivoAsignacionDivision("data/asignacionDIVHW.txt");
            listado.cargarArchivoAsignacionDivision("data/asignacionDIVSER.txt");
            listado.cargarArchivoAsignacionDepartamento("data/asignacionDEPNA.txt");
            listado.cargarArchivoAsignacionDepartamento("data/asignacionDEPSB.txt");
            listado.cargarArchivoAsignacionDepartamento("data/asignacionDEPSM.txt");
            listado.cargarArchivoAsignacionDepartamento("data/asignacionDEPSA.txt");
        } catch (IOException e) {
            System.out.println("Error en lectura de archivos de asignacion");
        }
        System.out.println();
    }

    /**
     * Test para comprobar que se ha leido de forma correcta la
     * informacion de los empleados (al menos que el listado contiene
     * datos de 100 empleados)
     * @throws Exception
     */
    @Test
    public void testConstruccionListado() throws Exception{
        assert(listado.obtenerNumeroEmpleados() == 1000);
    }

    /**
     * Test del procedimiento de asignacion de grupos procesando
     * los archivos de asignacion. Tambien implica la prueba de
     * busqueda de empleados sin grupo asignado en alguna asignatura
     * @throws Exception
     */
    @Test
    public void testCargarArchivosAsignacion() throws Exception {
        // Se obtienen los empleados no asignados a cada asignatura
        // y se comprueba su valor
        assert(listado.buscarEmpleadosSinDepartamento(Division.DIVNA).size() == 49);
        assert(listado.buscarEmpleadosSinDepartamento(Division.DIVID).size() == 54);
        assert(listado.buscarEmpleadosSinDepartamento(Division.DIVSW).size() == 42);
        assert(listado.buscarEmpleadosSinDepartamento(Division.DIVHW).size() == 44);
        assert(listado.buscarEmpleadosSinDepartamento(Division.DIVSER).size() == 49);
    }

    /**
     * Prueba para el procedimiento de conteo de grupos para cada una
     * de las asignaturas
     */
    @Test
    public void testObtenerContadoresDepartamentos(){
        // Se obtienen los contadores para la asignatura ES
        Map<Departamento, Long> contadores = listado.obtenerContadoresDepartamento(
                Division.DIVSER);
        contadores.keySet().stream().forEach(key -> System.out.println(
                key.toString() + "- " + contadores.get(key)));

        // Se comprueba que los valores son DEPNA = 49, DEPSB = 48, DEPSM = 53, DEPSA = 41
        List<Long> contadoresReferencia=Arrays.asList(49L,48L,53L,41L); //NO COINCIDE EL CONTEO 48,48,53,41 !!!AHORA CON LA ASIGNACION ESTA SI COINCIDE!!!
        Long contadoresCalculados[]=new Long[4];
        assertTrue(contadores.values().containsAll(contadoresReferencia));
    }

    /**
     * Prueba del procedimiento general de obtencion de contadores
     * para todas las asignaturas
     * @throws Exception
     */
    @Test
    public void testObtenerContadoresDivisionDepartamento() throws Exception {
        // Se obtienen los contadores para todos los grupos
        Map<Division, Map<Departamento, Long>> contadores =
                listado.obtenerContadoresDivisionDepartamento();

        // Se comprueban los valores obtenenidos con los valores por referencia
        //Cambio el orden de los contadores pues no coincide con el orden que obtiene los agrupamientos
        List<Long> contadoresReferenciaNA =  Arrays.asList(53L,53L,49L,58L); //COINCIDE (AUNQUE NO EN ORDEN)
        List<Long> contadoresReferenciaID=Arrays.asList(42L,49L,54L,43L); //COINCIDE (AUNQUE NO EN ORDEN)
        List<Long> contadoresReferenciaHW=Arrays.asList(67L,43L,44L,62L); //COINCIDE (AUNQUE NO EN ORDEN)
        List<Long> contadoresReferenciaSW=Arrays.asList(45L,42L,52L,53L); //COINCIDE (AUNQUE NO EN ORDEN)
        List<Long> contadoresReferenciaSER=Arrays.asList(53L,48L,49L,41L); //COINCIDE (AUNQUE NO EN ORDEN)

        // Se comprueban los resultado del metodo con los de referencia
        // HE CAMBIADO LAS COMPROBACIONES PARA EVITAR QUE COMPARE EL ORDEN
        assertTrue(contadores.get(Division.DIVNA).values().containsAll(contadoresReferenciaNA)
                && contadoresReferenciaNA.containsAll(contadores.get(Division.DIVNA).values()));
        assertTrue(contadores.get(Division.DIVHW).values().containsAll(contadoresReferenciaHW)
                && contadoresReferenciaHW.containsAll(contadores.get(Division.DIVHW).values()));
        assertTrue(contadores.get(Division.DIVSW).values().containsAll(contadoresReferenciaSW)
                && contadoresReferenciaSW.containsAll(contadores.get(Division.DIVSW).values()));
        assertTrue(contadores.get(Division.DIVSER).values().containsAll(contadoresReferenciaSER)
                && contadoresReferenciaSER.containsAll(contadores.get(Division.DIVSER).values()));
        assertTrue(contadores.get(Division.DIVID).values().containsAll(contadoresReferenciaID)
                && contadoresReferenciaID.containsAll(contadores.get(Division.DIVID).values()));

        /*Long contadoresCalculados[]=new Long[4];
        assertArrayEquals(contadores.get(Division.DIVID).values().
                toArray(contadoresCalculados),contadoresReferenciaID);
        assertArrayEquals(contadores.get(Division.DIVHW).values().
                toArray(contadoresCalculados),contadoresReferenciaHW);
        assertArrayEquals(contadores.get(Division.DIVSW).values().
                toArray(contadoresCalculados),contadoresReferenciaSW);
        assertArrayEquals(contadores.get(Division.DIVSER).values().
                toArray(contadoresCalculados),contadoresReferenciaSER);*/
    }
    @Test
    public void testBuscarEmpleadosSinDivision(){
        assert(listado.buscarEmpleadosSinDivision().size() == 213);
    }
    @Test
    public void testBuscarEmpleadosConDivisionSinDepartamento(){
        //54+42+44+49 Son los datos proporcionados en el método testCargarArchivosAsignacion
        assert(listado.buscarEmpleadosConDivisionSinDepartamento().size() == 54+42+44+49);
    }
    @Test
    public void testRepartoAsignacionNAaDepartamentos(){
        //HAGO LA PARTE OPCIONAL REPARTO EQUITATIVO DE PERSONAS ASIGNADAS A NINGUN DEPARTAMENTO
        listado.repartoAsignacionNAaDepartamentos();

        System.out.println(listado.obtenerContadoresDepartamento(Division.DIVSW));
        System.out.println(listado.obtenerContadoresDepartamento(Division.DIVID));
        System.out.println(listado.obtenerContadoresDepartamento(Division.DIVHW));
        System.out.println(listado.obtenerContadoresDepartamento(Division.DIVSER));

        assert (listado.buscarEmpleadosConDivisionSinDepartamento().size()==0);

    }
    @Test
    public void testRepartoAsignacionDIVNAaDivisiones(){
        //HAGO LA PARTE OPCIONAL REPARTO EQUITATIVO DE PERSONAS ASIGNADAS A NINGUN DEPARTAMENTO
        listado.repartoAsignacionDIVNAaDivisiones();
        System.out.println(listado.obtenerContadoresDivisionDepartamento());
        assert (listado.buscarEmpleadosSinDivision().size()==0);


    }
    @Test
    public void testHayDnisRepetidos(){
        assertFalse(listado.hayDnisRepetidos());
    }
    @Test
    public void testObtenerDnisRepetidos(){
        assertTrue(listado.obtenerDnisRepetidos().size()==0);
    }
    @Test
    public void testHayCorreosRepetidos(){
        assertTrue(listado.hayCorreosRepetidos());
    }
    @Test
    public void testObtenerCorreosRepetidos(){
        assertTrue(listado.obtenerCorreosRepetidos().size()>0);
    }
}