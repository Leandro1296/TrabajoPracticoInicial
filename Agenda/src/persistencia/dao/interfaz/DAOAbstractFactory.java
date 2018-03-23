package persistencia.dao.interfaz;


public interface DAOAbstractFactory 
{
	public PersonaDAO createPersonaDAO();
	
	public TipoDeContactoDAO createTipoContactoDAO();
	
	public LocalidadDAO createLocalidadDAO();
	
}
