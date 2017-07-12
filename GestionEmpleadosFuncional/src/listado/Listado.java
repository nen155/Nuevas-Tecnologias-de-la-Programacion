package listado;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Emilio Chica Jiménez on 06/03/2017.
 * @author Emilio Chica Jiménez
 */
public class Listado {
    private Map<String, Empleado> lista;
    private Division division = null;
    private Departamento departamento =null;
    private int contDEPNA =0,contDIVNA=0;
    public Listado(String archivo) throws IOException {
        // Se leen las lineas del archivo
        Stream<String> lineas = Files.lines(Paths.get(archivo));
        lista = new HashMap<>();
        lineas.map(this::crearEmpleado).forEach(empleado -> lista.put(empleado.obtenerDNI(),empleado));
    }

    private Empleado crearEmpleado(String linea){
        Empleado empleado=null;
        // Se define el patron para las comas que hacen de separadores
        Pattern pattern = Pattern.compile("(,)");
        // Se procesa la linea
        List<String> infos = pattern.splitAsStream(linea).collect(Collectors.toList());
        if(infos.size()>3) {
            empleado = new Empleado(infos.get(0), infos.get(1), infos.get(2), infos.get(3));
        }
        return empleado;
    }

    public void cargarArchivoAsignacionDivision(String archivo) throws IOException {
        Stream<String> lineas = Files.lines(Paths.get(archivo));

        lineas.forEach(linea -> {
            if(isNumeric(linea))
                lista.get(linea).asignarDivision(division);
            else if(linea.compareTo(" ")!=0){
                division = Division.valueOf(linea);
            }
        });
    }

    public void cargarArchivoAsignacionDepartamento(String archivo) throws IOException {
        Stream<String> lineas = Files.lines(Paths.get(archivo));
        lineas.forEach(linea -> {
            if(isNumeric(linea))
                lista.get(linea).asignarDepartamento(departamento);
            else if(linea.compareTo(" ")!=0){
                departamento = Departamento.valueOf(linea);
            }
        });
    }

    @Override
    public String toString() {
        return "Listado{" +
                "lista=" + lista.values() +
                '}';
    }
    /**
     * Asigna los empleados con DIVNA equitativamente entre los demás
     * OPCIONAL
     */
    public void repartoAsignacionDIVNAaDivisiones(){

        lista.values().stream().filter(
                (valor)->{
                    return valor.obtenerDivision()==Division.DIVNA;
                }
        ).forEach(valor->{

                    switch (contDIVNA){
                        case 0:
                            valor.asignarDivision(Division.DIVHW);
                            contDIVNA++;
                            break;
                        case 1:
                            valor.asignarDivision(Division.DIVID);
                            contDIVNA++;
                            break;
                        case 2:
                            valor.asignarDivision(Division.DIVSER);
                            contDIVNA++;
                            break;
                        case 3:
                            valor.asignarDivision(Division.DIVSW);
                            contDIVNA =0;
                            break;
                    }
                }
        );
    }

    /**
     * Asigna los empleados con DEPNA equitativamente entre los demás
     * OPCIONAL
     */
    public void repartoAsignacionNAaDepartamentos(){

        lista.values().stream().filter(
                (valor)->{
                    return valor.obtenerDepartamento()==Departamento.DEPNA;
                }
        ).forEach(valor->{
                    switch (contDEPNA){
                        case 0:
                            valor.asignarDepartamento(Departamento.DEPSA);
                            contDEPNA++;
                            break;
                        case 1:
                            valor.asignarDepartamento(Departamento.DEPSB);
                            contDEPNA++;
                            break;
                        case 2:
                            valor.asignarDepartamento(Departamento.DEPSM);
                            contDEPNA =0;
                            break;
                    }
                }
        );
    }
    public int obtenerNumeroEmpleados() {
        return lista.size();
    }

    /**
     * Metodo para buscar todos los empleados no asignados a departamento
     * que pertenezcan a una determinada division
     *
     * @param divisionObjetivo division de interes
     * @return lista de empleados sin departamento asignado
     */
    public List<Empleado> buscarEmpleadosSinDepartamento(Division divisionObjetivo) {
        return lista.values().stream()
                .filter(x->{
                    return x.obtenerDivision()!=null && x.obtenerDepartamento()!=null && x.obtenerDivision().compareTo(divisionObjetivo)==0 && x.obtenerDepartamento().compareTo(Departamento.DEPNA)==0;
                }).collect(Collectors.toList());

    }

    public Map<Departamento, Long> obtenerContadoresDepartamento(Division division) {

        return lista.values().stream().filter(x->{
            return x.obtenerDivision()!=null && x.obtenerDivision().compareTo(division)==0;
        })
                //Compruebo que no sea null por si no están asignados
                .filter(empleado -> {return empleado.obtenerDepartamento()!=null && empleado.obtenerDivision()!=null;})
                //Los agrupo por departamento para el conteo
                .collect(Collectors.groupingBy(Empleado::obtenerDepartamento, TreeMap::new,Collectors.counting()));
    }

    public Map<Division, Map<Departamento, Long>> obtenerContadoresDivisionDepartamento() {
        Map<Division, Map<Departamento, Long>> divisionTreeMapMap = lista.values().stream()
                //Compruebo que no sea null por si no están asignados
                .filter(empleado -> {return empleado.obtenerDivision()!=null && empleado.obtenerDepartamento()!=null;})
                //Los agrupo por division y los vuelvo a agrupar por departamento para el conteo
                .collect(Collectors.groupingBy(Empleado::obtenerDivision,
                        Collectors.groupingBy(Empleado::obtenerDepartamento, HashMap::new, Collectors.counting())
                )
                );
        return divisionTreeMapMap;

    }



    /**
     * Metodo para buscar los empleados sin division asignada: es decir,
     * en el dato miembro division tendran el valor DIVNA
     */
    public List<Empleado> buscarEmpleadosSinDivision() {
        return lista.values().stream()
                .filter(x->{
                    return x.obtenerDivision()!=null && x.obtenerDivision().compareTo(Division.DIVNA)==0;
                }).collect(Collectors.toList());

    }

    /**
     * Metodo para buscar empleados con division asignada (no es DIVNA)
     * pero sin departamento: el valor del dato miembro departamento es
     * (DEPNA)
     */
    public List<Empleado> buscarEmpleadosConDivisionSinDepartamento() {
        return lista.values().stream()
                .filter(x->{
                    return x.obtenerDepartamento()!=null && x.obtenerDivision()!=null && x.obtenerDivision().compareTo(Division.DIVNA)!=0 && x.obtenerDepartamento().compareTo(Departamento.DEPNA)==0;
                }).collect(Collectors.toList());
    }

    /**
     * Metodo para determinar si hay dnis repetidos
     *
     * @return
     */
    public boolean hayDnisRepetidos() {
        //Saco un listado con los dnis sin repetir
        Stream<String> distintos = lista.values().stream().map(Empleado::obtenerDNI).distinct();
        //Saco otro listado con los dnis repetidos
        Stream<String> grupo = lista.values().stream().map(Empleado::obtenerDNI);
        //Compruebo si hay repetidos
        return distintos.count()!=grupo.count();
    }

    /**
     * Metodo para obtener una lista de dnis repetidos, junto con la
     * lista de trabajadores asociados a cada dni repetido (en caso de
     * haberlos)
     */
    public Map<String, List<Empleado>> obtenerDnisRepetidos() {
        //Obtengo la lista agrupada por DNI y obtengo el conteo para ver cuales se repiten
        TreeMap<String, Long> collect = lista.values().stream().collect(Collectors.groupingBy(Empleado::obtenerDNI, TreeMap::new, Collectors.counting()));

        Map<String, List<Empleado>> dnisRepetidos = lista.values().stream()
                .filter(empleado -> {
                    //Compruebo si contiene el DNI la lista de conteo
            if (collect.containsKey(empleado.obtenerDNI()))
                return collect.get(empleado.obtenerDNI()) > 1; //Si lo tiene compruebo si campo value es >1 por lo tanto ha contado mas de 1
            else
                return false; //No está en la colección por lo tanto el conteo es 0 y no se repite
        }).collect(Collectors.groupingBy(Empleado::obtenerDNI));
        return dnisRepetidos;
    }

    /**
     * Metodo para determinar si hay correos repetidos
     */
    public boolean hayCorreosRepetidos() {
        //Saco un listado con los correos sin repetir
        Stream<String> distintos = lista.values().stream().map(Empleado::obtenerCorreo).distinct();
        //Saco otro listado con los correos repetidos
        Stream<String> grupo = lista.values().stream().map(Empleado::obtenerCorreo);
        //Compruebo si hay repetidos
        return distintos.count()!= grupo.count();
    }

    /**
     * Metodo para obtener una lista de correos repetidos, junto con la
     * lista de trabajadores asociados a cada dni repetido (en caso de
     * haberlos)
     */
    public Map<String, List<Empleado>> obtenerCorreosRepetidos() {
        //Obtengo la lista agrupada por correo y obtengo el conteo para ver cuales se repiten
        TreeMap<String, Long> collect = lista.values().stream().collect(Collectors.groupingBy(Empleado::obtenerCorreo, TreeMap::new, Collectors.counting()));

        Map<String, List<Empleado>> correosRepetidos = lista.values().stream().filter(empleado -> {
            //Compruebo si contiene el correo la lista de conteo
            if (collect.containsKey(empleado.obtenerCorreo()))
                return collect.get(empleado.obtenerCorreo()) > 1;//Si lo tiene compruebo si campo value es >1 por lo tanto ha contado mas de 1
            else
                return false;//No está en la colección por lo tanto el conteo es 0 y no se repite
        }).collect(Collectors.groupingBy(Empleado::obtenerCorreo));

        return correosRepetidos;
    }

    private boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

}
