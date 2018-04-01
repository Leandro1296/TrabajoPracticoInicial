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
	
	private static final String insert = "INSERT INTO localidades(idLocalidad, nombre) VALUES(?,?)";
	private static final String delete = "DELETE FROM localidades WHERE idLocalidad = ?";
	private static final String update = "UPDATE localidades SET Nombre = ? WHERE idLocalidad = ?";
	private static final String readall = "SELECT * FROM localidades";
	private static final String select = "SELECT * FROM localidades WHERE idLocalidad = ?";
	private static final String isUsed = "SELECT * FROM personas WHERE localidad = ?";

	@Override
	public boolean insert(LocalidadDTO localidad) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, localidad.getIdLocalidad());
			statement.setString(2, localidad.getNombre());
			if(statement.executeUpdate() > 0) //Si se ejecutó devuelvo true
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
			statement.setInt(1, localidad_a_eliminar.getIdLocalidad());
			chequeoUpdate = statement.executeUpdate();
			if(chequeoUpdate > 0) //Si se ejecutÃ³ devuelvo true
				return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean edit(LocalidadDTO localidad_a_editar) 
	{
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, localidad_a_editar.getNombre());
			statement.setInt(2, localidad_a_editar.getIdLocalidad());
			if(statement.executeUpdate() > 0) //Si se ejecutó devuelvo true
				return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
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
				localidades.add(new LocalidadDTO(resultSet.getInt("idLocalidad"), resultSet.getString("Nombre")));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return localidades;
	}

	@Override
	public LocalidadDTO select(int localidad_a_buscar) {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		LocalidadDTO localidad = null;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(select);
			statement.setInt(1, localidad_a_buscar);
			resultSet = statement.executeQuery();
			if (resultSet.next())
			{
				localidad = new LocalidadDTO(resultSet.getInt("idLocalidad"), resultSet.getString("Nombre"));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return localidad;
	}
	
	public boolean isUsed(LocalidadDTO localidad) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(isUsed);
			statement.setInt(1, localidad.getIdLocalidad());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) 
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
