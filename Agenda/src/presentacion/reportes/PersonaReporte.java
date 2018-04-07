package presentacion.reportes;

import java.util.Date;

import dto.PersonaDTO;

public class PersonaReporte implements Comparable<PersonaReporte>{
	
	private int idPersona;
	private String nombre;
	private String telefono;
	private String calle;
	private int altura;
	private int piso;
	private String dpto;
	private int localidad;
	private String mail;
	private Date nacimiento;
	private int tipo;
	private String signoHoroscopoChino;
	
	public PersonaReporte(PersonaDTO persona,String signo)
	{
		this.setIdPersona(persona.getIdPersona());
		this.setNombre(persona.getNombre());
		this.setTelefono(persona.getTelefono());
		this.setCalle(persona.getCalle());
		this.setAltura(persona.getAltura());
		this.setPiso(persona.getPiso());
		this.setDpto(persona.getDpto());
		this.setLocalidad(persona.getLocalidad());
		this.setMail(persona.getMail());
		this.setNacimiento(persona.getNacimiento());
		this.setTipo(persona.getTipo());
		this.setSignoHoroscopoChino(signo);
	}

	public int getIdPersona() 
	{
		return idPersona;
	}

	public void setIdPersona(int idPersona) 
	{
		this.idPersona = idPersona;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getTelefono() 
	{
		return telefono;
	}

	public void setTelefono(String telefono) 
	{
		this.telefono = telefono;
	}

	public String getCalle() 
	{
		return calle;
	}

	public void setCalle(String calle) 
	{
		this.calle = calle;
	}

	public int getAltura() 
	{
		return altura;
	}

	public void setAltura(int altura) 
	{
		this.altura = altura;
	}

	public int getPiso() 
	{
		return piso;
	}

	public void setPiso(int piso) 
	{
		this.piso = piso;
	}

	public String getDpto() 
	{
		return dpto;
	}

	public void setDpto(String dpto) 
	{
		this.dpto = dpto;
	}

	public int getLocalidad() 
	{
		return localidad;
	}

	public void setLocalidad(int localidad) 
	{
		this.localidad = localidad;
	}

	public String getMail() 
	{
		return mail;
	}

	public void setMail(String mail) 
	{
		this.mail = mail;
	}

	public Date getNacimiento() 
	{
		return nacimiento;
	}

	public void setNacimiento(Date nacimiento) 
	{
		this.nacimiento = nacimiento;
	}

	public int getTipo() 
	{
		return tipo;
	}

	public void setTipo(int tipo) 
	{
		this.tipo = tipo;
	}

	public String getSignoHoroscopoChino() 
	{
		return signoHoroscopoChino;
	}

	public void setSignoHoroscopoChino(String signoHoroscopoChino) 
	{
		this.signoHoroscopoChino = signoHoroscopoChino;
	}

	@Override
	public int compareTo(PersonaReporte persona) 
	{
		return this.nacimiento.compareTo(persona.getNacimiento());
	}

}
