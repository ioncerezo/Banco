import java.util.*;

public class Banco {

     /*
     *  Atributos
     * 	- ArrayList que almacena todas las cuentas gestionadas por el banco.
     * 	  Puede contener objetos de tipo CuentaCorriente o CuentaAhorro.
     *  - Interés aplicado a las cuentas de ahorro.
     */
    private final ArrayList<Cuenta> cuentas;
    private double interesAhorro;

    /**
     * Constructor de la clase Banco.
     * Inicializa el ArrayList de cuentas y el interés de ahorro.
     */
    public Banco() {
        cuentas = new ArrayList<>();
        interesAhorro = 0;
    }

    /**
     * Lee las cuentas desde un fichero y las guarda en el ArrayList.
     * En el fichero hay cuentas de la clase CuentaAhorro y otras de la clase CuentaCorriente.
     * Usa diferentes constructores para crear cada una de ellas.
     *
     *  El fichero debe tener el formato:
     *  DNI NombreCliente SaldoInicial TipoCuenta
     *  Ejemplo: 12345678A Alice 1000.50 C
     *
     * @param leerDatos Scanner conectado al fichero que se va a leer.
     * @return Número de cuentas leídas del fichero.
     */
    public int cargarFichero(Scanner leerDatos) {
        int cont = 0;
        while (leerDatos.hasNextLine()){

            String dni = leerDatos.next();
            String nombreCliente = leerDatos.next();
            Cliente unCliente = new Cliente(dni, nombreCliente);

            double depositoInicial = Double.parseDouble(leerDatos.next());
            char tipoDeCuenta = leerDatos.next().charAt(0);

            switch (tipoDeCuenta){
                case 'A':
                    cuentas.add(new CuentaAhorro(unCliente, depositoInicial));
                    cont++;
                    break;
                case 'C':
                    cuentas.add(new CuentaCorriente(unCliente, depositoInicial));
                    cont++;
                    break;
                default:
                    System.out.printf("%s \nCuenta erronea: %s en la linea %d.", dni, tipoDeCuenta, cont + 1);
                    break;
            }
        }
        return cont;
    }

    /**
     * Establece el interés aplicado a las cuentas de ahorro.
     *
     * @param interesAhorro Interés en porcentaje que se aplicará a las cuentas de ahorro.
     */
    public void setInteresAhorro(double interesAhorro) {
        this.interesAhorro = interesAhorro;
    }

    /**
     * Genera una representación formateada del estado del banco para su visualización.
     * Incluye:
     * - El título principal "B A N C O" centrado en un ancho de 25 caracteres.
     * - La tasa de interés aplicable a las cuentas de ahorro, expresada con un decimal y el símbolo '%'.
     * - Un listado detallado de todas las cuentas gestionadas por el banco
     *   utilizando el metodo `toString()` de cada cuenta.
     *
     * @return Una cadena (String) que representa el estado completo del banco, incluyendo intereses y datos de las cuentas.
     */
    @Override
    public String toString() {
        String formato = "";
        formato += String.format("%25s\n", "B A N C O");
        formato += String.format("Intereses de las cuentas de ahorro: %.1f%% \n\n", interesAhorro);

        for (Cuenta cuenta : cuentas){
            formato += cuenta.toString() + "\n";
        }
        return formato;
    }

    /**
     * Ordena las cuentas según los criterios definidos en el metodo compareTo de la interfaz Comparable.
     * Utiliza Collections.sort para ordenar el ArrayList de cuentas.
     */
    public void ordenarCuentas() {
        Collections.sort(cuentas);
    }

    /**
     * Busca una cuenta por el DNI del titular.
     *
     * @param dniCuenta DNI del titular de la cuenta que se desea buscar.
     * @return Índice de la primera cuenta encontrada con el DNI indicado. Devuelve -1 si no se encuentra.
     */
    private int buscarCuenta(String dniCuenta) {
        for (int i = 0; i < cuentas.size(); i++) {
            Cuenta unaCuenta = cuentas.get(i); // Obtenemos la cuenta que queremos comprobar
            if (unaCuenta.mismoDni(dniCuenta)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Ingresa una cantidad en la cuenta con el DNI indicado.
     * Busca el DNI en el ArrayList y realiza el ingreso si lo encuentra.
     *
     * @param idCuenta DNI del titular de la cuenta en la que se quiere hacer el ingreso.
     * @param cantidad Cantidad en euros que se quiere ingresar.
     * @return true si el ingreso se realizó correctamente, false en caso contrario.
     */
    private boolean ingreso(String idCuenta, double cantidad) {
        int indexCuenta = buscarCuenta(idCuenta);

        if (indexCuenta != -1) {
            Cuenta cuentaAIngresar = cuentas.get(indexCuenta);
            cuentaAIngresar.ingreso(cantidad);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retira una cantidad de la cuenta con el DNI indicado.
     * Busca el DNI en el ArrayList y trata de realizar la retirada si lo encuentra.
     *
     * @param idCuenta DNI del titular de la cuenta de la que se quiere retirar dinero.
     * @param cantidad Cantidad en euros que se quiere retirar.
     * @return true si la retirada se realizó correctamente, false en caso contrario.
     */
    private boolean retirada(String idCuenta, double cantidad) {
        int indexCuenta = buscarCuenta(idCuenta);

        if (indexCuenta != -1) {
            Cuenta cuentaARetirar = cuentas.get(indexCuenta);
            return cuentaARetirar.retirada(cantidad);
        } else {
            return false;
        }
    }

    /**
     * Realiza una transferencia entre dos cuentas.
     * Utiliza los métodos ingreso y retirada para realizar la operación.
     *
     * @param dniOrigen  DNI del titular de la cuenta de origen.
     * @param dniDestino DNI del titular de la cuenta de destino.
     * @param cantidad   Cantidad en euros que se quiere transferir.
     * @return true si la transferencia se realizó correctamente, false en caso contrario.
     */
    public boolean transferencia(String dniOrigen, String dniDestino, double cantidad) {
        //Devulve false si alguno de los dni no exite en el Array
        if (buscarCuenta(dniOrigen) == -1 || buscarCuenta(dniDestino) == -1) { return false;}

        if (retirada(dniOrigen, cantidad)){
            return ingreso(dniDestino, cantidad);
        }

        return false;
    }

    /**
     * Paga el interés indicado a todas las cuentas de ahorro.
     * Identifica las cuentas de ahorro y aplica el interés utilizando el metodo addInteres.
     *
     * @param interes Interés en porcentaje que se aplicará a las cuentas de ahorro.
     * @return Total de intereses pagados a todas las cuentas de ahorro.
     */
    public double addInteres(double interes) {
        double total = 0;
        for (Cuenta cuenta : cuentas){
            if (cuenta instanceof CuentaAhorro){
                CuentaAhorro cuentaCasteada = (CuentaAhorro) cuenta;
                total += cuentaCasteada.addInteres(interes);
            }
        }
        return total;
    }

    /**
     * Resetea el número de retiradas de todas las cuentas corrientes.
     * Identifica las cuentas corrientes y utiliza el metodo reseteaNumeroRetiradas.
     *
     * @return Número de cuentas corrientes cuyo contador de retiradas ha sido reseteado.
     */
    public int reset() {
        int total = 0;
        for (Cuenta cuenta : cuentas){
            if (cuenta instanceof CuentaCorriente){
                CuentaCorriente cuentaCasteada = (CuentaCorriente) cuenta;
                cuentaCasteada.reseteaNumeroRetiradas();
                total++;
            }
        }

        return total;
    }

    /**
     * Elimina todas las cuentas asociadas a un cliente específico.
     * U
     *
     * @param elCliente Datos del cliente cuyas cuentas se desean eliminar.
     * @return Número total de cuentas eliminadas.
     */
    public int eliminarCliente(Cliente elCliente) {
        Iterator<Cuenta> it = cuentas.iterator();
        int cont = 0;

        while (it.hasNext()) {
            Cliente clienteAEliminar = it.next().getTitular();
            if (clienteAEliminar.equals(elCliente)) {
                it.remove();
                cont++;
            }
        }

        return cont;
    }
}