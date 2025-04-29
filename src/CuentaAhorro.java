/**
 * Clase que representa una cuenta de ahorro.
 * Extiende la clase Cuenta e implementa lógica específica para retiros con comisión,
 * ingresos sin comisión y cálculo de intereses.
 */
public class CuentaAhorro extends Cuenta {

    /**
     * Atributos:
     * - Saldo mínimo permitido en la cuenta (10 euros).
     */
    public static final int SALDO_MINIMO = 10;
    public static final int DEPOSITO_BONIFICABLE = 3000;
    public static final int BONIFICACION_DEPOSITO = 100;

    /**
     * Constructor de cuenta de ahorro.
     * Si el depósito inicial supera 3000€, se añade un bonus de 100€.
     *
     * @param titular          Titular de la cuenta.
     * @param depositoInicial  Depósito inicial para abrir la cuenta.
     */
    public CuentaAhorro (Cliente titular, double depositoInicial) {
        super (titular, (depositoInicial > DEPOSITO_BONIFICABLE
                ? depositoInicial + BONIFICACION_DEPOSITO
                : depositoInicial));
    }

    /**
     * Realiza una retirada de dinero aplicando comisión.
     * Verifica que el saldo no quede por debajo del mínimo permitido (10€).
     * Aplica una comisión de 2€ por operación.
     *
     * @param cantidad Cantidad a retirar.
     * @return true si la operación es exitosa, false si no se cumple el saldo mínimo.
     */
    @Override
    public boolean retirada(double cantidad) {
        if (saldo <= SALDO_MINIMO || (saldo - cantidad - COMISION_RETIRADA) < SALDO_MINIMO) {
            return false;
        } else {
            saldo -= cantidad + COMISION_RETIRADA;
            return true;
        }
    }

    /**
     * Realiza un ingreso sin comisiones.
     * Acepta cualquier cantidad positiva.
     *
     * @param cantidad Cantidad a ingresar.
     */
    @Override
    public void ingreso(double cantidad) {
        if (cantidad > 0){
            saldo += cantidad;
        }
    }

    /**
     * Calcula y añade intereses al saldo de la cuenta.
     *
     * @param tasa Tasa de interés en porcentaje (ej: 5.5 para 5.5%).
     * @return Cantidad de intereses añadidos al saldo.
     */
    public double addInteres(double tasa) {
        // El interés se aplica sobre el saldo actual antes de añadirlo.
        double intereses = (tasa / 100) * saldo;
        saldo += intereses;

        return intereses;
    }
}