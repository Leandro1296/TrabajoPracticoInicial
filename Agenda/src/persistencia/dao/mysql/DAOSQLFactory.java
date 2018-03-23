/**
 * 
 */
package persistencia.dao.mysql;

import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.LocalidadDAO;
import persistencia.dao.interfaz.PersonaDAO;
import persistencia.dao.interfaz.TipoDeContactoDAO;

public class DAOSQLFactory implements DAOAbstractFactory 
{
	/* (non-Javadoc)
	 * @see persistencia.dao.interfaz.DAOAbstractFactory#createPersonaDAO()
	 */
	@Override
	public PersonaDAO createPersonaDAO() 
	{
				return new PersonaDAOSQL();
	}

	@Override
	public TipoDeContactoDAO createTipoContactoDAO() 
	{
		return new TipoDeContactoDAOSQL();
	}

	@Override
	public LocalidadDAO createLocalidadDAO() 
	{
		return new LocalidadDAOSQL();
	}

}
