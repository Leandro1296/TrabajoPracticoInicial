package persistencia.dao.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import persistencia.conexion.Conexion;
import persistencia.dao.interfaz.PersonaDAO;
import dto.PersonaDTO;

public class PersonaDAOSQL implements PersonaDAO
{
	private static final String insert = "INSERT INTO personas(idPersona, nombre, telefono, calle, altura, piso, departamento, localidad, mail, cumpleaños, tipo) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM personas WHERE idPersona = ?";
	private static final String update = "UPDATE personas SET Nombre = ?, Telefono = ?, Calle = ?, Altura = ?, Piso = ?, Departamento = ?, Localidad = ?, Mail = ?, Cumpleaños = ?, Tipo = ?) WHERE ID = ? ";
	private static final String readall = "SELECT * FROM personas";
		
	public boolean insert(PersonaDTO persona)
	{
		PreparedStatement statement;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(insert);
			statement.setInt(1, persona.getIdPersona());
			statement.setString(2, persona.getNombre());
			statement.setString(3, persona.getTelefono());
			statement.setString(4, persona.getCalle());
			statement.setInt(5, persona.getAltura());
			statement.setInt(6, persona.getPiso());
			statement.setString(7, persona.getDpto());
			statement.setString(8, persona.getLocalidad());
			statement.setString(9, persona.getMail());
			statement.setString(10, persona.getCumpleaños());
			statement.setString(11, persona.getTipo());
			if(statement.executeUpdate() > 0) //Si se ejecutó devuelvo true
				return true;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(PersonaDTO persona_a_eliminar)
	{
		PreparedStatement statement;
		int chequeoUpdate = 0;
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(delete);
			statement.setString(1, Integer.toString(persona_a_eliminar.getIdPersona()));
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
	public boolean edit(PersonaDTO persona_a_eliminar) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public List<PersonaDTO> readAll()
	{
		PreparedStatement statement;
		ResultSet resultSet; //Guarda el resultado de la query
		ArrayList<PersonaDTO> personas = new ArrayList<PersonaDTO>();
		Conexion conexion = Conexion.getConexion();
		try 
		{
			statement = conexion.getSQLConexion().prepareStatement(readall);
			resultSet = statement.executeQuery();
			
			while(resultSet.next())
			{
				personas.add(new PersonaDTO(resultSet.getInt("idPersona"), 
											resultSet.getString("Nombre"), 
											resultSet.getString("Telefono"),
											resultSet.getString("Calle"),
											resultSet.getInt("Altura"),
											resultSet.getInt("Piso"),
											resultSet.getString("Departamento"),
											resultSet.getString("Localidad"),
											resultSet.getString("Mail"),
											resultSet.getString("Cumpleaños"),
											resultSet.getString("Tipo")));
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return personas;
	}

	
}
