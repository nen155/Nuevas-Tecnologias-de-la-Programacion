package listado;

/**
 * Created by Emilio Chica Jiménez on 06/03/2017.
 * @author Emilio Chica Jiménez
 */
public class Empleado {

    // Dato miembro para almacenar el nombre
    private String nombre;

    // Dato miembro para almacenar los apellidos
    private String apellidos;

    // Dato miembro para almacenar el dni
    private String dni;

    // Dato miembro para almacenar el correo
    private String correo;

    // Dato miembro para almacenar el departamento
    private Departamento departamento;

    // Dato miembro para almacenar la division
    private Division division;

    // Constructor
    public Empleado(String dni,String nombre, String apellidos , String correo) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.correo = correo;
        this.departamento = Departamento.DEPNA;
        this.division = Division.DIVNA;
    }

    // Metodo para obtener el valor del dato miembro departamento
    public Departamento obtenerDepartamento() {
        return departamento;
    }
    // Metodo para obtener el valor del dato miembro departamento
    public void asignarDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    // Metodo para obtener el valor del dato miembro division
    public Division obtenerDivision() {
        return division;
    }
    // Metodo para asignar el valor del dato miembro division
    public void asignarDivision(Division division) {
        this.division = division;
    }

    // Metodo para asignar el nombre
    public void asignarNombre(String nombre) {
        this.nombre = nombre;
    }

    // Metodo para acceder al nombre
    public String obtenerNombre() {
        return nombre;
    }

    // Metodo para asignar el apellido
    public void asignarApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    // Metodo para obtener el primer apellido
    public String obtenerApellidos() {
        return apellidos;
    }

    // Metodo para asignar el valor de dni
    public void asignarDNI(String dni) {
        this.dni = dni;
    }

    // Metodo para obtener el dni
    public String obtenerDNI() {
        return dni;
    }

    // Metodo par asignar el departamento
    public void asignarDepartamento(String departamento) {
        this.correo = departamento;
    }

    // Metodo para obteber el valor del dato miembro correo
    public String obtenerCorreo() {
        return correo;
    }

    // Recupera nombre y primer apellido
    public String obtenerNombreApellido() {
        return String.format("%s %s", obtenerNombre(), obtenerApellidos());
    }

    // Metodo toString
    @Override
    public String toString() {
        return String.format("%-8s %-8s %-8s   %s   %s",
                obtenerNombre(), obtenerApellidos(), obtenerDNI(),
                obtenerDepartamento(),obtenerDivision());
    }
}
