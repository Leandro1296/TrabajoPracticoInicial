package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.LocalidadDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.LocalidadDAO;


public class LocalidadDAOSQL implements LocalidadDAO {
	
	private static final String insert = "INSERT INTO localidades(nombre) VALUES(?, ?)";
	private static final String delete = "DELETE FROM localidades WHERE nombre = ?";
	private static final String update = "UPDATE localidades SET Nombre = ? WHERE Nombre = ?";
	private static final String readall = "SELECT * FROM localidades";

	@Override
	public boolean insert(LocalidadDTO localidad) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setString(1, localidad.getNombre());
			if(statement.executeUpdate() > 0) //Si se ejecutÛ devuelvo true
				return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(LocalidadDTO localidad_a_eliminar)
	{
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, localidad_a_eliminar.getNombre());
			chequeoUpdate = statement.executeUpdate();
			if(chequeoUpdate > 0) //Si se ejecut√≥ devuelvo true
				return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean edit(LocalidadDTO localidad_a_editar) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<LocalidadDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<LocalidadDTO> localidades = new ArrayList<LocalidadDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				localidades.add(new LocalidadDTO(resultSet.getString("Nombre")));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return localidades;
	}
}
