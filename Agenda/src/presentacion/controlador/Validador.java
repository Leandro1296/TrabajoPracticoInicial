package presentacion.controlador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validador 
{
	public static final String expMail = "\\w+@\\w+(\\.\\w+)+";                // ejemplo@ejemplo.com
	public static final String expFecha = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4}"; // dd//mm/yyyy
	public static final String expTelefono = "1[15]\\d{8}";			 		   // 15xxxxxxxx o 11xxxxxxxx
	public static final String expAltura = "\\d{1,4}";			       		   // 1321
	public static final String expNombres = "^[A-Z][a-z]+((\\s[A-Z]((\\.)?)([a-z]+)?)+$)"; // ??????????? ?????????
	public static final String expNombre =  "^[A-Z][a-z]+((\\s[A-Z]((\\.)?)([a-z]+)?)+$)|^[A-Z][a-z]+"; //????? tambien toma un nombre solo
	public static final String expNumeros = "\\d{0,2}";						   // xx

	public boolean mailValido(String mailInput)
	{
		return verificar(mailInput,expMail);
	}
	
	public boolean nombresValido(String nombreInput)
	{
		return verificar(nombreInput,expNombres);
	}
	
	/**
	 *Idem a nombresValido. Admite un solo nombre. 
	 */
	public boolean nombreValido(String nombreInput)
	{
		return verificar(nombreInput,expNombre);
	}
	
	public boolean telefonoValido(String telefonoInput)
	{
		return verificar(telefonoInput,expTelefono);
	}
	
	public boolean alturaValida(String alturaInput)
	{
		return verificar(alturaInput,expAltura);
	}
	
	public boolean fechaValida(String fechaInput)
	{
		return verificar(fechaInput,expFecha);
	}
	
	public boolean pisoValido(String numeroInput)
	{
		return verificar(numeroInput,expNumeros);
	}
	
	private boolean verificar(String input, String expresion) 
	{
		Pattern pattern = Pattern.compile(expresion);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}
	
	public static void main(String []args)
	{
		Validador validador = new Validador();
		String dsa = "  Leandro Gonzalez";
		System.out.println("Nombre y apellido: " + validador.nombreValido(" Leandro Gonzalez") );
		System.out.println(validador.nombreValido(dsa.trim()));
		System.out.println(dsa.trim());
		System.out.println("Telefono: " + validador.telefonoValido(" 1123564578"));
		System.out.println("Calle: " + validador.nombreValido(" Bombona"));
		System.out.println("Altura: " + validador.alturaValida("386"));
		System.out.println("Piso: " + validador.pisoValido(""));
		System.out.println("Departamento: " + validador.nombreValido("") );
		System.out.println("Localidad: " + validador.nombreValido(" Jose C. Paz"));
		System.out.println("Mail: " + validador.mailValido(" leo_pa96@hotmail.com"));
		System.out.println("Cumpleaños: " + validador.fechaValida(" 09/02/1996"));
		System.out.println("Tipo: " + validador.nombreValido(" Universidad"));
		System.out.println("Fin de prueba.");
		
	}
}
