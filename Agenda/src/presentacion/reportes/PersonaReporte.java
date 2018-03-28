package presentacion.reportes;

import dto.PersonaDTO;

public class PersonaReporte {
	
	private int idPersona;
	private String nombre;
	private String telefono;
	private String calle;
	private int altura;
	private int piso;
	private String dpto;
	private String localidad;
	private String mail;
	private String cumpleaños;
	private String tipo;
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
		this.setCumpleaños(persona.getCumpleaños());
		this.setTipo(persona.getTipo());
		this.setSignoHoroscopoChino(signo);
	}

	public int getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
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

	public int getPiso() {
		return piso;
	}

	public void setPiso(int piso) {
		this.piso = piso;
	}

	public String getDpto() {
		return dpto;
	}

	public void setDpto(String dpto) {
		this.dpto = dpto;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getCumpleaños() {
		return cumpleaños;
	}

	public void setCumpleaños(String cumpleaños) {
		this.cumpleaños = cumpleaños;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getSignoHoroscopoChino() {
		return signoHoroscopoChino;
	}

	public void setSignoHoroscopoChino(String signoHoroscopoChino) {
		this.signoHoroscopoChino = signoHoroscopoChino;
	}
	

}
