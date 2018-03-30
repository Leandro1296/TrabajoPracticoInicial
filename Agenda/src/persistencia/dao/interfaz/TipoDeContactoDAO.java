package persistencia.dao.interfaz;

import java.util.List;

import dto.TipoDeContactoDTO;


public interface TipoDeContactoDAO {
	

	public boolean insert(TipoDeContactoDTO tipoDeContacto);

	public boolean delete(TipoDeContactoDTO tipoDeContacto_a_eliminar);

	public boolean edit(TipoDeContactoDTO tipoDeContacto_a_editar);
	
	public List<TipoDeContactoDTO> readAll();

	public TipoDeContactoDTO select(int tipoDeContacto);

}
