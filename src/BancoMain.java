import java.util.*;
import java.io.*;
import java.util.regex.*;

public class BancoMain {

    /**
     * Enumeración que define las opciones disponibles en el menú del programa.
     */
    public enum Opciones {
        FIN("Fin del programa"),
        MOSTRAR("Mostrar cuentas ordenadas"),
        INTERESES("Pagar intereses"),
        TRANSFERENCIA("Realizar transferencia"),
        BORRAR("Eliminar cliente");

        private final String texto;

        /**
         * Constructor del enum.
         *
         * @param texto Descripción textual de la opción.
         */
        Opciones(String texto) {
            this.texto = texto;
        }

        /**
         * Devuelve la descripción textual de la opción.
         *
         * @return Texto asociado a la opción.
         */
        public String getTexto() {
            return this.texto;
        }
    }

    /**
     * Metodo principal donde comienza la ejecución del programa.
     * Carga los datos del banco desde un fichero, muestra un menú interactivo y realiza operaciones según la opción elegida.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este programa).
     */
    public static void main(String[] args) {

        Scanner leerTeclado = new Scanner(System.in);

        presentacion();

        // Creamos un objeto del banco y cargamos los datos de las cuentas desde un fichero
        Banco unBanco = new Banco();
        cargarCuentas(unBanco, leerTeclado);

        // La opcion debe ser del tipo enum que hemos definido
        Opciones opcion;
        do {
            // Con el entero que hemos introducido, obtenemos la opcion correspondiente
            // Opciones.values() devuelve un array con todas las opciones y con el int
            // elegimos el elemento concreto que nos interesa
            int indiceEnum = elegirOpcion(leerTeclado);
            opcion = Opciones.values()[indiceEnum];
            switch (opcion) {
                case FIN:
                    System.out.println(opcion.getTexto());
                    break;
                case MOSTRAR:
                    unBanco.ordenarCuentas();
                    System.out.println(unBanco);
                    break;
                case INTERESES:
                    pagarIntereses(unBanco, leerTeclado);
                    break;
                case TRANSFERENCIA:
                    realizarTranferencia(unBanco, leerTeclado);
                    break;
                case BORRAR:
                    eliminarCliente(unBanco, leerTeclado);
                    break;
            }
        } while (opcion != Opciones.FIN);
    }

    /**
     * Muestra una presentación inicial del programa.
     */
    public static void presentacion() {
        System.out.println("Este programa permite trabajar con las cuentas de un banco");
        System.out.println("Utiliza las clase Cliente, Cuenta, CuentaAhorro, CuentaCorriente y Banco");
        System.out.println("Lee las cuentas iniciales del fichero cuentas.txt");
        System.out.println("Mediante un menu permite elegir diferentes opciones");
        System.out.println("Al final guarda las cuentas en el fichero cuentas.txt");
        System.out.println();
    }

    /**
     * Muestra el menú de opciones, lee la opción elegida por el usuario y la devuelve.
     *
     * @param consola Scanner utilizado para leer datos desde el teclado.
     * @return Índice de la opción elegida por el usuario.
     */
    public static int elegirOpcion(Scanner consola) {
        System.out.println("\n******* MENU *******");

        // Recorremos el enum para mostrar las opciones
        // Sirve para cualquier menu, independientemente de sus opciones
        int numOpciones = Opciones.values().length;
        for (int i = 1; i < numOpciones; i++) {
            Opciones unaOpcion = Opciones.values()[i];
            System.out.println(i + ". " + unaOpcion.getTexto());
        }
        System.out.println("Elige tu opcion (0 para acabar): ");
        return consola.nextInt();
    }

    /**
     * Pide al usuario el nombre de un fichero válido y carga las cuentas del fichero en el objeto Banco.
     *
     * @param unBanco   Objeto Banco donde se cargarán las cuentas.
     * @param leerDatos Scanner utilizado para leer datos desde el teclado.
     */
    public static void cargarCuentas(Banco unBanco, Scanner leerDatos) {
        int numCuentas = 0;
        // Pide un fichero valido
        File fichero;
        do {
            System.out.println("Nombre del fichero: ");
            String nombre = leerDatos.next();
            fichero = new File(nombre);
        } while (!fichero.canRead());

        Scanner leerFichero = null;
        try {
            leerFichero = new Scanner(fichero);
            numCuentas = unBanco.cargarFichero(leerFichero);
        } catch (Exception e) {
            System.out.print("Se ha producido un error");
        }
        if (leerFichero != null) {
            leerFichero.close();
        }
        System.out.println("Se han anyadido " + numCuentas + " cuentas");
    }

    /**
     * Gestiona el proceso de fin de mes: paga los intereses de las cuentas de ahorro y resetea el número de retiradas de las cuentas corrientes.
     *
     * @param unBanco     Objeto Banco sobre el que se realizarán las operaciones.
     * @param leerTeclado Scanner utilizado para leer datos desde el teclado.
     */
    public static void pagarIntereses(Banco unBanco, Scanner leerTeclado) {
        System.out.println("Es fin de mes. Vamos a pagar intereses...");
        System.out.println("Interes a pagar: ");
        double interes = leerTeclado.nextDouble();

        // Se pagan los intereses de las cuentas de ahorro
        double total = unBanco.addInteres(interes);
        System.out.print("En total se han pagado " + total + " euros");
        unBanco.setInteresAhorro(interes);

        // Se resetea el numero de retirada de dinero de las cuentas corrientes
        unBanco.reset();
    }

    /**
     * Solicita los DNIs de las cuentas origen y destino, así como la cantidad, y realiza una transferencia entre ellas.
     *
     * @param unBanco     Objeto Banco sobre el que se realizará la transferencia.
     * @param leerTeclado Scanner utilizado para leer datos desde el teclado.
     */
    public static void realizarTranferencia(Banco unBanco, Scanner leerTeclado) {
        System.out.println("CUENTA ORIGEN: ");
        String dniOrigen = leerDni(leerTeclado);
        System.out.println("CUENTA DESTINO: ");
        String dniDestino = leerDni(leerTeclado);
        System.out.println("Cantidad: ");
        double cantidad = leerTeclado.nextDouble();
        boolean resultado = unBanco.transferencia(dniOrigen, dniDestino, cantidad);
        if (resultado) {
            System.out.println("Transferencia realizada con exito");
        } else {
            System.out.println("Problemas con la transferencia");
        }
    }

    /**
     * Lee un DNI válido utilizando expresiones regulares para validar el formato.
     * El formato debe cumplir con 8 dígitos seguidos de una letra mayúscula.
     *
     * @param leerTeclado Scanner utilizado para leer datos desde el teclado.
     * @return DNI válido introducido por el usuario.
     */
    public static String leerDni(Scanner leerTeclado) {
        Matcher comparaFormato;
        String dni;
        do {
            System.out.println("Introduce DNI valido: ");
            dni = leerTeclado.next();
            Pattern formatoDni = Pattern.compile("[0-9]{8}[A-Z]");
            comparaFormato = formatoDni.matcher(dni);
        } while (!comparaFormato.matches());

        return dni;
    }

    /**
     * Solicita los datos de un cliente y elimina todas sus cuentas del banco.
     *
     * @param unBanco     Objeto Banco sobre el que se eliminarán las cuentas.
     * @param leerTeclado Scanner utilizado para leer datos desde el teclado.
     */
    public static void eliminarCliente(Banco unBanco, Scanner leerTeclado) {
        System.out.println("DATOS DEL CLIENTE ");
        String dni = leerDni(leerTeclado);
        System.out.print("Nombre del cliente: ");
        String nombre = leerTeclado.next();
        Cliente elCliente = new Cliente(dni, nombre);
        int borrados = unBanco.eliminarCliente(elCliente);
        System.out.println("Se han borrado " + borrados + " cuentas");
    }
}