package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.TipoDeContactoDTO;
import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.TipoDeContactoDAO;

public class TipoDeContactoDAOSQL implements TipoDeContactoDAO {
	
	private static final String insert = "INSERT INTO tiposDeContacto(idTipoDeContacto, tipo) VALUES(?,?)";
	private static final String delete = "DELETE FROM tiposDeContacto WHERE idTipoDeContacto = ?";
	private static final String update = "UPDATE tiposDeContacto SET Tipo = ? WHERE idTipoDeContacto = ?";
	private static final String readall = "SELECT * FROM tiposDeContacto";
	private static final String select = "SELECT * FROM tiposDeContacto WHERE idTipoDeContacto = ?";
	private static final String isUsed = "SELECT * FROM personas WHERE tipo = ?";
	
	@Override
	public boolean insert(TipoDeContactoDTO tipoDeContacto)
	{
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, tipoDeContacto.getIdTipoDeContacto());
			statement.setString(2, tipoDeContacto.getTipo());
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
	public boolean delete(TipoDeContactoDTO tipoDeContacto_a_eliminar)
	{
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setInt(1, tipoDeContacto_a_eliminar.getIdTipoDeContacto());
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
	public boolean edit(TipoDeContactoDTO tipoDeContacto_a_editar) 
	{
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(update);
			statement.setString(1, tipoDeContacto_a_editar.getTipo());
			statement.setInt(2, tipoDeContacto_a_editar.getIdTipoDeContacto());
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
	public List<TipoDeContactoDTO> readAll() {
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<TipoDeContactoDTO> tiposDeContacto = new ArrayList<TipoDeContactoDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				tiposDeContacto.add(new TipoDeContactoDTO(resultSet.getInt("idTipoDeContacto"), resultSet.getString("Tipo")));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return tiposDeContacto;
	}
	
	public boolean isUsed(TipoDeContactoDTO tipoDeContacto) {
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try {
			statement = conexion.getSQLConexion().prepareStatement(isUsed);
			statement.setInt(1, tipoDeContacto.getIdTipoDeContacto());
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public TipoDeContactoDTO select(int tipoDeContacto_a_buscar) {
			PreparedStatement statement;
			ResultSet resultSet; //Guarda el resultado de la query
			TipoDeContactoDTO tipoDeContacto = null;
			Conexion conexion = Conexion.getConexion();
			try 
			{
				statement = conexion.getSQLConexion().prepareStatement(select);
				statement.setInt(1, tipoDeContacto_a_buscar);
				resultSet = statement.executeQuery();
				if (resultSet.next())
				{
					tipoDeContacto = new TipoDeContactoDTO(resultSet.getInt("idTipoDeContacto"), resultSet.getString("Tipo"));
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			return tipoDeContacto;
	}

}
