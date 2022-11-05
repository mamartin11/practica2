import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ProcesarEmpleados {

    static List<Empleado> empleados;

    static void cargarArchivo() throws IOException{
        Pattern pattern = Pattern.compile(";");
        String fileName = "empleado.csv";
        try (Stream<String> lines = Files.lines(Path.of(fileName))){
            empleados = lines.map(line -> {
                String[] arr = pattern.split(line);
                return new Empleado(arr[0], arr[1], arr[2],Double.parseDouble(arr[3]), arr[4] );
            }).collect(Collectors.toList());
        }
    }

    static Predicate<Empleado> Rsalario=
            e -> (e.getSalario() >= 4000 && e.getSalario() <= 6000);
    static void mostrarEmpleadosRango() {
        System.out.println("LOS EMPLEADOS QUE GANAN ENTRE $4000-$6000 SON: ");
        empleados.stream()
                .filter(Rsalario)
                .sorted(Comparator.comparing(Empleado::getSalario))
                .forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }

    static void empleadosDepartamento(){
        System.out.println("LOS EMPLEADOS POR DEPARTAMENTO SON:");
        Map<String, List<Empleado>> agrupadoPorDepartamento =
                empleados.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento));
        agrupadoPorDepartamento.forEach(
                (departamento, empleadosEnDepartamento) ->
                {
                    System.out.println(departamento);
                    empleadosEnDepartamento.forEach(
                            empleado -> System.out.printf(" %s%n", empleado));
                }
        );
        System.out.println("---------------------------------------------------------------------------------------------------------");
    }

    static void cantidadEmpleadosDepartamento(){
        System.out.println("LA CANTIDAD DE EMPLEADOS POR DEPARTAMENTO ES: ");
        Map<String, Long> conteoEmpleadosPorDepartamento =
                empleados.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento,
                                TreeMap::new, Collectors.counting()));
        conteoEmpleadosPorDepartamento.forEach(
                (departamento, conteo) -> System.out.printf(
                        "%s tiene %d empleado(s)%n", departamento, conteo));
        System.out.println("---------------------------------------------------------------------------------------------------------");

    }
    static void sumaNominaDepartamento(){
        System.out.println("LA SUMA DE NÓMINA POR DEPARTAMENTO ES: ");
        Map<String, List<Empleado>> agrupadoPorDepartamento =
                empleados.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento));
        agrupadoPorDepartamento.forEach(
                (departamento, empleadosEnDepartamento) ->
                {
                    System.out.println(departamento);
                    System.out.println(empleadosEnDepartamento.stream().mapToDouble(Empleado::getSalario).sum());
                }
        );
        System.out.println("---------------------------------------------------------------------------------------------------------");

    }


    static void empleadoGanaMasDepartamento() {
        System.out.println("Los EMPLEADOS QUE GANAN MÁS DE CADA DEPARTAMENTO SON: ");
        Map<String, List<Empleado>> agrupadoPorDepartamento =
                empleados.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento));
        agrupadoPorDepartamento.forEach(
                (departamento, empleadosEnDepartamento) ->
                {
                    System.out.println(departamento);
                    System.out.println(empleadosEnDepartamento.stream().mapToDouble(Empleado::getSalario).max().getAsDouble());
                }
        );
        System.out.println("---------------------------------------------------------------------------------------------------------");

    }
    static Predicate<Empleado> Maxsalario=
            e -> (e.getSalario() == empleados.stream().mapToDouble(Empleado::getSalario).max().getAsDouble());
    static void empleadoGanaMas(){

        System.out.println("EL EMPLEADO QUE GANA MÁS ES: ");
                empleados.stream().filter(Maxsalario).forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------------------------------------");

    }
    static Predicate<Empleado> Minsalario=
            e -> (e.getSalario() == empleados.stream().mapToDouble(Empleado::getSalario).min().getAsDouble());

    static void empleadoGanaMenos(){
        System.out.println("EL EMPLEADO QUE GANA MENOS ES: ");
        empleados.stream().filter(Minsalario).forEach(System.out::println);
        System.out.println("---------------------------------------------------------------------------------------------------------");

    }

    static void promediosalarioDepartamento(){
        System.out.println("EL PROMEDIO DE SALARIO POR DEPARTAMENTO ES: ");
        Map<String, List<Empleado>> agrupadoPorDepartamento =
                empleados.stream()
                        .collect(Collectors.groupingBy(Empleado::getDepartamento));
        agrupadoPorDepartamento.forEach(
                (departamento, empleadosEnDepartamento) ->
                {
                    System.out.println(departamento);
                    System.out.println(empleadosEnDepartamento.stream().mapToDouble(Empleado::getSalario).average().getAsDouble());
                }
        );
        System.out.println("---------------------------------------------------------------------------------------------------------");

    }

    static void promedioSalario(){
        System.out.printf("EL PROMEDIO DE SALARIO DE LOS EMPLEADOS ESs: %.2f%n",
                empleados.stream()
                        .mapToDouble(Empleado::getSalario)
                        .average()
                        .getAsDouble());
        System.out.println("---------------------------------------------------------------------------------------------------------");

    }

    static void nomina(){
        System.out.printf("LA NÓMINA TOTAL ES: %.2f%n",
                empleados.stream()
                        .mapToDouble(Empleado::getSalario)
                        .sum());
        System.out.println("---------------------------------------------------------------------------------------------------------");

    }




    public static void main(String[] args) throws IOException {
        cargarArchivo();
        mostrarEmpleadosRango();
        empleadosDepartamento();
        cantidadEmpleadosDepartamento();
        sumaNominaDepartamento();
        empleadoGanaMasDepartamento();
        promediosalarioDepartamento();
        empleadoGanaMas();
        empleadoGanaMenos();
        promedioSalario();
        nomina();
    }

}
