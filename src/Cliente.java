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
 * 	Definicion de la clase Cliente
 * 	Gestionara el DNI y el nombre de un cliente.
 *
 *******************************************************************************************************************/

public class Cliente {

	/**
	 * Atributos:
	 * - DNI del cliente (valor final, no modificable).
	 * - Nombre del cliente (valor final, no modificable).
	 */
	private final String dni;
	private final String nombre;

	/**
	 * Constructor principal de la clase.
	 * Inicializa los atributos DNI y nombre.
	 *
	 * @param dni   DNI del cliente.
	 * @param nombre Nombre del cliente.
	 */
	public Cliente(String dni, String nombre) {
		this.dni = dni;
		this.nombre = nombre;
	}

	/**
	 * Constructor de copia.
	 * Crea un nuevo cliente a partir de otro existente.
	 *
	 * @param unCliente Objeto Cliente del que se copiarán los datos.
	 */
	public Cliente(Cliente unCliente) {
		this(unCliente.dni, unCliente.nombre);
	}

	/**
	 * Devuelve el DNI del cliente.
	 *
	 * @return DNI del cliente.
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * Devuelve el nombre del cliente.
	 *
	 * @return Nombre del cliente.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sobrescribe el metodo toString para representar al cliente.
	 * Formato: "DNI (Nombre)".
	 *
	 * @return Cadena con los datos formateados del cliente.
	 */
	@Override
	public String toString() {
		return String.format("%s (%s)", dni, nombre);
	}

	/**
	 * Sobrescribe el metodo equals para comparar clientes.
	 * Dos clientes son iguales si coinciden DNI y nombre.
	 *
	 * @param objeto Objeto a comparar.
	 * @return true si el objeto es un Cliente con mismos DNI y nombre, false en caso contrario.
	 */
	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof Cliente){
			Cliente otroCliente = (Cliente) objeto;
			return otroCliente.getDni().equals(dni) && otroCliente.getNombre().equals(nombre);
		}
		return false;
	}
}