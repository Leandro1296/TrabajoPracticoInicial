package modelo;

import java.util.List;

import dto.LocalidadDTO;
import dto.PersonaDTO;
import dto.TipoDeContactoDTO;
import persistencia.dao.interfaz.DAOAbstractFactory;
import persistencia.dao.interfaz.LocalidadDAO;
import persistencia.dao.interfaz.PersonaDAO;
import persistencia.dao.interfaz.TipoDeContactoDAO;


public class Agenda 
{
	private PersonaDAO persona;	
	private LocalidadDAO localidad;
	private TipoDeContactoDAO tipoDeContacto;
	
	public Agenda(DAOAbstractFactory metodo_persistencia)
	{
		this.persona = metodo_persistencia.createPersonaDAO();
		this.localidad = metodo_persistencia.createLocalidadDAO();
		this.tipoDeContacto = metodo_persistencia.createTipoContactoDAO();
	}
	
	public void agregarPersona(PersonaDTO nuevaPersona)
	{
		this.persona.insert(nuevaPersona);
	}
	
	public void borrarPersona(PersonaDTO persona_a_eliminar) 
	{
		this.persona.delete(persona_a_eliminar);
	}
	
	public void modificarPersona(PersonaDTO persona_a_modificar) 
	{
		this.persona.edit(persona_a_modificar);
	}
	
	public List<PersonaDTO> obtenerPersonas()
	{
		return this.persona.readAll();		
	}
	
	public void agregarLocalidad(LocalidadDTO nuevaLocalidad)
	{
		this.localidad.insert(nuevaLocalidad);
	}
	
	public void borrarLocalidad(LocalidadDTO localidad_a_eliminar) 
	{
		this.localidad.delete(localidad_a_eliminar);
	}
	
	public void modificarLocalidad(LocalidadDTO localidad_a_modificar) 
	{
		this.localidad.edit(localidad_a_modificar);
	}
	
	public LocalidadDTO obtenerLocalidad(int localidad){
		return this.localidad.select(localidad);
	}
	
	public List<LocalidadDTO> obtenerLocalidades()
	{
		return this.localidad.readAll();
	}
	
	public void agregarTipoDeContacto(TipoDeContactoDTO nuevaTipoDeContacto)
	{
		this.tipoDeContacto.insert(nuevaTipoDeContacto);
	}
	
	public void borrarTipoDeContacto(TipoDeContactoDTO tipoDeContacto_a_eliminar) 
	{
		this.tipoDeContacto.delete(tipoDeContacto_a_eliminar);
	}
	
	public void modificarTipoDeContacto(TipoDeContactoDTO tipoDeContacto_a_modificar) 
	{
		this.tipoDeContacto.edit(tipoDeContacto_a_modificar);
	}
	
	public TipoDeContactoDTO obtenerTipoDeContacto(int tipoDeContacto){
		return this.tipoDeContacto.select(tipoDeContacto);
	}
	
	public List<TipoDeContactoDTO> obtenerTiposDeContacto()
	{
		return this.tipoDeContacto.readAll();
	}
	
}
