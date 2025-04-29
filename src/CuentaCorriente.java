/******************************************************************************************************************
 *
 * Nombre: Ion Cerezo Valero
 * Fecha: 20/03/2025
 * Modulo: Programacion.
 * UD: UD8 Herencia e interfaces.
 * Tarea: Tarea Evaluacion. Realiza un programa en Java.
 * Autoevaluacion: https://docs.google.com/document/d/1tXVRQ2J6-DBQXpRBFTAcvjTghCzOB6nE/edit?usp=sharing&ouid=111797469793076501298&rtpof=true&sd=true
 *
 * Descripcion del programa:
 * 	Definicion de la clase CuentaCorriente
 * 	Subclase de Cuenta con funcionalidades específicas para cuentas corrientes
 *
 *******************************************************************************************************************/

/**
 * Clase que representa una cuenta corriente.
 * Extiende la clase Cuenta e implementa lógica específica para retiros con comisión
 * después de 3 operaciones mensuales y gestión de ingresos con comisión fija.
 */
public class CuentaCorriente extends Cuenta {

    /**
     * Atributos
     * - Número máximo de retiradas sin comisión (3 primeras retiradas).
     * - Comisión aplicada en cada ingreso (1 euro).
     * - Contador de retiradas realizadas en el mes actual.
     */
    public static final int RETIRADAS_SIN_COMISION = 3;
    public static final int COMISION_INGRESO = 1;
    private int numeroRetiradas;

    /**
     * Constructor de cuenta corriente.
     * Inicializa el contador de retiradas a 0.
     *
     * @param titular         Titular de la cuenta.
     * @param depositoInicial Depósito inicial para abrir la cuenta.
     */
    public CuentaCorriente(Cliente titular, double depositoInicial) {
        super(titular, depositoInicial);
        numeroRetiradas = 0;
    }

    /**
     * Reinicia el contador de retiradas mensuales a 0.
     */
    public void reseteaNumeroRetiradas() {
        numeroRetiradas = 0;
    }

    /**
     * Sobrescribe el metodo retirada de la clase padre.
     * Aplica comisión de 2€ a partir de la cuarta retirada.
     * No permite saldos negativos ni cantidades inválidas.
     *
     * @param cantidad Cantidad a retirar.
     * @return true si la operación es exitosa, false si no hay saldo suficiente o cantidad inválida.
     */
    @Override
    public boolean retirada(double cantidad) {
        if (cantidad > saldo || cantidad < 1) {
            return false;
        }

        numeroRetiradas++;

        if (numeroRetiradas > RETIRADAS_SIN_COMISION) {
            saldo -= cantidad + COMISION_RETIRADA;
        } else {
            saldo -= cantidad;
        }

        return true;
    }

    /**
     * Sobrescribe el metodo ingreso de la clase padre.
     * Aplica una comisión de 1€ por cada ingreso.
     *
     * @param cantidad Cantidad a ingresar (debe ser positiva).
     */
    @Override
    public void ingreso(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad - COMISION_INGRESO;
        }
    }
}