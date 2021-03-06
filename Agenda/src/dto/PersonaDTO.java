package dto;

import java.sql.Date;

public class PersonaDTO 
{
	private int idPersona;
	private String nombre;
	private String telefono;
	private String calle;
	private int altura;
	private Integer piso;
	private String dpto;
	private int localidad;
	private String mail;
	private Date nacimiento;
	private int tipo;

	public PersonaDTO(int idPersona, String nombre, String telefono, String calle, int altura, Integer piso, String dpto, int localidad, String mail, Date nacimiento, int tipo)
	{
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.telefono = telefono;
		this.calle = calle;
		this.altura = altura;
		this.piso = piso;
		this.dpto = dpto;
		this.localidad = localidad;
		this.mail = mail;
		this.nacimiento = nacimiento;
		this.tipo = tipo;
	}
	
	public int getIdPersona() 
	{
		return this.idPersona;
	}

	public void setIdPersona(int idPersona) 
	{
		this.idPersona = idPersona;
	}

	public String getNombre() 
	{
		return this.nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public String getTelefono() 
	{
		return this.telefono;
	}

	public void setTelefono(String telefono) 
	{
		this.telefono = telefono;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getAltura() {
		return altura;
	}

	public void setAltura(int altura) {
		this.altura = altura;
	}

	public Integer getPiso() {
		return piso;
	}

	public void setPiso(Integer piso) {
		this.piso = piso;
	}

	public int getLocalidad() {
		return localidad;
	}

	public void setLocalidad(int localidad) {
		this.localidad = localidad;
	}

	public String getDpto() {
		return dpto;
	}

	public void setDpto(String dto) {
		this.dpto = dto;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Date getNacimiento() {
		return nacimiento;
	}

	public void setNacimiento(Date nacimiento) {
		this.nacimiento = nacimiento;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
}
