package dto;

public class LocalidadDTO {
	
	private String nombre;
	private int idLocalidad;
	
	public LocalidadDTO(String nombre, int id)
	{
		this.nombre = nombre;
		this.idLocalidad = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getIdLocalidad() {
		return idLocalidad;
	}

	public void setIdLocalidad(int idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

}
