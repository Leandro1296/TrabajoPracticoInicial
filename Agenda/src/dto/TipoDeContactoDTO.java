package dto;

public class TipoDeContactoDTO {
	
	private String tipo;
	private int idTipoDeContacto;
	
	public TipoDeContactoDTO(int id, String tipo)
	{
		this.tipo = tipo;
		this.idTipoDeContacto = id;
	}
	
	public String getTipo() 
	{
		return tipo;
	}
	public void setTipo(String tipo) 
	{
		this.tipo = tipo;
	}

	public int getIdTipoDeContacto() {
		return idTipoDeContacto;
	}

	public void setIdTipoDeContacto(int idTipoDeContacto) {
		this.idTipoDeContacto = idTipoDeContacto;
	}
	
	public String toString(){return this.tipo;}
}
