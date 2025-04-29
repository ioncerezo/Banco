/******************************************************************************************************************
 *
 * Nombre: Ion Cerezo Valero
 * Fecha: 20/03/2025
 * Modulo: Programacion.
 * UD: UD8 Herencia e interfaces.
 * Tarea: Tarea Evaluacion. Realiza un programa en Java.
 *Autoevaluacion: https://docs.google.com/document/d/1tXVRQ2J6-DBQXpRBFTAcvjTghCzOB6nE/edit?usp=sharing&ouid=111797469793076501298&rtpof=true&sd=true
 *
 * Descripcion del programa:
 * 	Definicion de la clase abstracta Cuenta
 * 	Gestionara el titular y el saldo de una cuenta
 * 	Implementa la interfaz Comparable para ordenar por DNI y saldo
 *
 *******************************************************************************************************************/


abstract class Cuenta implements Comparable<Cuenta> {


	/**
	 * Atributos
	 *  - Titular de la cuenta (protegido para acceso desde subclases).
	 *  - Saldo actual de la cuenta (protegido para acceso desde subclases).
	 *  - Comisión fija aplicada en operaciones de retirada (2 euros).
	 *
	 */
	protected Cliente titular;
	protected double saldo;
	protected static final int COMISION_RETIRADA = 2;

	/**
	 * Constructor de cuenta.
	 * Crea una copia del titular para garantizar inmutabilidad.
	 *
	 * @param titular Titular de la cuenta
	 * @param saldo   Saldo inicial de la cuenta
	 */
	public Cuenta(Cliente titular, double saldo) {
		this.titular = new Cliente(titular);
		this.saldo = saldo;
	}

	/**
	 * Devuelve una copia del titular para evitar modificaciones externas.
	 *
	 * @return Copia del objeto Cliente titular
	 */
	public Cliente getTitular() {
		return new Cliente(titular);
	}

	/**
	 * Representación textual de la cuenta.
	 * Formato: "NombreClase: DNI (Nombre) Saldo XXXX.XX".
	 *
	 * @return Cadena formateada con los datos de la cuenta
	 */
	@Override
	public String toString() {
		return String.format("%-15s: %-20s Saldo%8.2f",
				this.getClass().getSimpleName(), titular, saldo);
	}

	/**
	 * Compara si dos cuentas tienen el mismo titular por DNI.
	 *
	 * @param objeto Objeto a comparar
	 * @return true si es una cuenta con mismo DNI de titular
	 */
	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof Cuenta){
			Cuenta otraCuenta = (Cuenta) objeto;
			return otraCuenta.mismoDni(titular.getDni());
		}
		return false;
	}

	/**
	 * Verifica si el DNI del titular coincide con uno dado.
	 *
	 * @param dni DNI a comparar
	 * @return true si los DNI coinciden
	 */
	public boolean mismoDni(String dni) {
		return titular.getDni().equals(dni);
	}

	/**
	 * Implementación de Comparable para ordenación compuesta.
	 * 1. Ordena por DNI ascendente
	 * 2. Si DNI coincide, ordena por saldo descendente
	 *
	 * @param otraCuenta Cuenta con la que comparar
	 * @return Valor negativo, cero o positivo según orden
	 */
	@Override
	public int compareTo(Cuenta otraCuenta) {
		int comparacionDNI = titular.getDni().compareTo(otraCuenta.titular.getDni());

		//Si son iguales (comparacionDNI != 0) , devuelve el valor de la comparación del saldo
		return (comparacionDNI != 0)
				? comparacionDNI
				: Double.compare(otraCuenta.saldo, this.saldo);
	}

	/**
	 * Metodo abstracto para retirar fondos.
	 *
	 * @param cantidad Cantidad a retirar
	 * @return true si la operación fue exitosa
	 */
	public abstract boolean retirada(double cantidad);

	/**
	 * Metodo abstracto para ingresar fondos.
	 *
	 * @param cantidad Cantidad a ingresar
	 */
	public abstract void ingreso(double cantidad);
}