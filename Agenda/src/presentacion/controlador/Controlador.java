package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.omg.CORBA.LocalObject;

import modelo.Agenda;
import presentacion.reportes.ReporteAgenda;
import presentacion.vista.VentanaLocalidad;
import presentacion.vista.VentanaPersona;
import presentacion.vista.VentanaTipoDeContacto;
import presentacion.vista.Vista;
import dto.LocalidadDTO;
import dto.PersonaDTO;
import dto.TipoDeContactoDTO;

public class Controlador implements ActionListener, MouseListener 
{
		private Vista vista;
		private List<PersonaDTO> personas_en_tabla;
		private List<LocalidadDTO> localidades;
		private List<TipoDeContactoDTO> tiposDeContacto;
		private VentanaPersona ventanaPersona;
		private VentanaLocalidad ventanaLocalidad;
		private VentanaTipoDeContacto ventanaTipoDeContacto;
		private Agenda agenda;
		private CalculadoraHoroscopoChino calculadoraHoroscopoChino;
		private boolean seAgregaNueva;
		
		public Controlador(Vista vista, Agenda agenda)
		{
			this.vista = vista;
			this.vista.getBtnAgregar().addActionListener(this);
			this.vista.getBtnEditar().addActionListener(this);
			this.vista.getBtnBorrar().addActionListener(this); 
			this.vista.getBtnReporte().addActionListener(this);
			this.vista.getBtnLocalidades().addActionListener(this);
			this.vista.getBtnTiposDecontacto().addActionListener(this);
			this.vista.getTablaPersonas().addMouseListener(this);
			
			this.agenda = agenda;
			this.seAgregaNueva = false;
			this.personas_en_tabla = null;
			this.localidades = null;
			this.tiposDeContacto = null;
		}
		
		public void inicializar()
		{
			this.llenarTabla();
			this.vista.show();
		}
		
		private void llenarTabla()
		{
			this.vista.getModelPersonas().setRowCount(0); //Para vaciar la tabla
			this.vista.getModelPersonas().setColumnCount(0);
			this.vista.getModelPersonas().setColumnIdentifiers(this.vista.getNombreColumnas());
			this.personas_en_tabla = agenda.obtenerPersonas();
			this.cargarDatos();

			for (int i = 0; i < this.personas_en_tabla.size(); i ++)
			{
				Object[] fila = {this.personas_en_tabla.get(i).getNombre(), 
								 this.personas_en_tabla.get(i).getTelefono(),
								 this.personas_en_tabla.get(i).getCalle(),
								 this.personas_en_tabla.get(i).getAltura(),
								 this.personas_en_tabla.get(i).getPiso(),
								 this.personas_en_tabla.get(i).getDpto(),
								 this.personas_en_tabla.get(i).getLocalidad(),
								 this.personas_en_tabla.get(i).getMail(),
								 this.personas_en_tabla.get(i).getCumpleaños(),
								 this.personas_en_tabla.get(i).getTipo()};
				this.vista.getModelPersonas().addRow(fila);
			}			
		}
		
		public void actionPerformed(ActionEvent e) 
		{		
			if(e.getSource() == this.vista.getBtnAgregar())
			{
				abrirVentanaPersona();
				seAgregaNueva = true;
			}
			
			else if(e.getSource() == this.vista.getBtnEditar())
			{
				abrirVentanaPersona();
				setFieldsVentanaPersona();
				seAgregaNueva = false;
			}
			else if(e.getSource() == this.vista.getBtnBorrar())
			{
				borrarPersonas();
				actualizar();
			}
			else if(e.getSource() == this.vista.getBtnReporte())
			{				
				ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
				reporte.mostrar();				
			}
			else if(e.getSource() == this.vista.getBtnLocalidades())
			{				
				this.ventanaLocalidad = VentanaLocalidad.getVentana(this);
				cargarLocalidadesEnLista();
				this.ventanaLocalidad.getJList().addMouseListener(this);
			}
			else if(e.getSource() == this.vista.getBtnTiposDecontacto())
			{				
				this.ventanaTipoDeContacto = VentanaTipoDeContacto.getVentana(this);
				cargarTiposDeContactoEnLista();
				this.ventanaTipoDeContacto.getJList().addMouseListener(this);
			}
			else if(this.ventanaPersona != null && e.getSource() == this.ventanaPersona.getBtnGuardar())
			{
				if(seAgregaNueva){ agregarNuevaPersona(); }
				else{ modificarPersona(); }
				actualizar();
				cerrarVentana();
			}
			
			else if(this.ventanaLocalidad != null && e.getSource() == this.ventanaLocalidad.getBtnAgregar()) 
			{
				agregarNuevaLocalidad();
			}
			
			else if (this.ventanaLocalidad != null && e.getSource() == this.ventanaLocalidad.getBtnModificar()) 
			{
				modificarLocalidad();
			} 
			else if(this.ventanaLocalidad != null && e.getSource() == this.ventanaLocalidad.getBtnEliminar()) {
				borrarLocalidad();
			}
			
			else if(e.getSource() == this.ventanaTipoDeContacto.getBtnAgregar()) 
			{
				agregarTipoDeContacto();
			}
			
			else if (e.getSource() == this.ventanaTipoDeContacto.getBtnModificar()) 
			{
				modificarTipoDeContacto();
			} 
			else if(e.getSource() == this.ventanaTipoDeContacto.getBtnEliminar()) {
				borrarTipoDeContacto();
			}
		}

		private void borrarTipoDeContacto() {
			String tipoDeContacto = this.ventanaTipoDeContacto.getJList().getSelectedValue();
			if (tipoDeContacto != null) {
				this.agenda.borrarTipoDeContacto(this.tiposDeContacto.get(buscar(tipoDeContacto)));
				this.cargarDatos();
				cargarTiposDeContactoEnLista();
			}
		}

		private void modificarTipoDeContacto() {
			String tipoDeContacto = this.ventanaTipoDeContacto.getJList().getSelectedValue();	 
			if (tipoDeContacto != null) {
				TipoDeContactoDTO tipoDeContacto_a_modificar= this.tiposDeContacto.get(buscar(tipoDeContacto));
				tipoDeContacto_a_modificar.setTipo(this.ventanaTipoDeContacto.getTxtNombre().getText());
				this.agenda.modificarTipoDeContacto(tipoDeContacto_a_modificar);
				this.cargarDatos();
				cargarTiposDeContactoEnLista();
			} else {
			
			}
		}

		private void agregarTipoDeContacto() {
			TipoDeContactoDTO nuevoTipoDeContacto = new TipoDeContactoDTO(this.ventanaTipoDeContacto.getTxtNombre().getText(),0);
			this.agenda.agregarTipoDeContacto(nuevoTipoDeContacto);
			this.cargarDatos();
			cargarTiposDeContactoEnLista();
		}

		private void borrarLocalidad() {
			String localidad = this.ventanaLocalidad.getJList().getSelectedValue();
			if (localidad != null) {
				this.agenda.borrarLocalidad(this.localidades.get(buscarLocalidad(localidad)));
				this.cargarDatos();
				cargarLocalidadesEnLista();
			}
		}

		private void modificarLocalidad() {
			String localidad = this.ventanaLocalidad.getJList().getSelectedValue();	 
			if (localidad != null) {
				LocalidadDTO localidad_a_modificar= this.localidades.get(buscarLocalidad(localidad));
				localidad_a_modificar.setNombre(this.ventanaLocalidad.getTxtNombre().getText());
				this.agenda.modificarLocalidad(localidad_a_modificar);
				this.cargarDatos();
				cargarLocalidadesEnLista();
			} else {
			
			}
		}

		private void agregarNuevaLocalidad() {
			LocalidadDTO nuevaLocalidad = new LocalidadDTO(this.ventanaLocalidad.getTxtNombre().getText(),0);
			this.agenda.agregarLocalidad(nuevaLocalidad);
			this.cargarDatos();
			cargarLocalidadesEnLista();
		}

		private void cargarTiposDeContactoEnLista() 
		{
			this.ventanaTipoDeContacto.getModelo().clear();
			List<TipoDeContactoDTO> TipoDeContactoesOB = this.agenda.obtenerTiposDeContacto();
			for (TipoDeContactoDTO tipoDeContacto : TipoDeContactoesOB){
				this.ventanaTipoDeContacto.getModelo().addElement(tipoDeContacto.getTipo());
			}
			this.ventanaTipoDeContacto.getJList().setModel(this.ventanaTipoDeContacto.getModelo());
		}

		public void cargarDatos()
		{
			this.localidades = agenda.obtenerLocalidades();
			this.tiposDeContacto = agenda.obtenerTiposDeContacto();
		}
		
		public int buscarLocalidad(String nombre)
		{
			List<LocalidadDTO> localidadesBD = this.agenda.obtenerLocalidades();
			for(LocalidadDTO localidad: localidadesBD)
			{
				if(nombre.equals(localidad.getNombre()))
				{
					return localidadesBD.indexOf(localidad);
				}
			}
			return -1;
		}

		private int buscar(String tipoDeContacto) {
			List<TipoDeContactoDTO> TipoDeContactoBD = this.agenda.obtenerTiposDeContacto();
			for(TipoDeContactoDTO tipo: TipoDeContactoBD)
			{
				if(tipoDeContacto.equals(tipo.getTipo()))
				{
					return TipoDeContactoBD.indexOf(tipo);
				}
			}
			return -1;
		}
		
		private void agregarNuevaPersona() 
		{
			PersonaDTO nuevaPersona = new PersonaDTO(0,this.ventanaPersona.getTxtNombre().getText(), 
													   this.ventanaPersona.getTxtTelefono().getText(),
													   this.ventanaPersona.getTxtCalle().getText(),
									  Integer.parseInt(this.ventanaPersona.getTxtAltura().getText()),
					   			      Integer.parseInt(this.ventanaPersona.getTxtPiso().getText()),
													   this.ventanaPersona.getTxtDepartamento().getText(),
													   valorcmbxLocalidades(),
													   this.ventanaPersona.getTxtMail().getText(),
													   this.ventanaPersona.getTxtCumpleaños().getText(),
													   valorcmbxTiposDeContacto());
			this.agenda.agregarPersona(nuevaPersona);
		}
		
		private void borrarPersonas() 
		{
			int[] filas_seleccionadas = this.vista.getTablaPersonas().getSelectedRows();
			for (int fila:filas_seleccionadas)
			{
				this.agenda.borrarPersona(this.personas_en_tabla.get(fila));
			}
		}
		
		private void modificarPersona() 
		{
			int fila = this.vista.getTablaPersonas().getSelectedRow();
			PersonaDTO personaSeleccionada = this.personas_en_tabla.get(fila);
			System.out.println(personaSeleccionada.getIdPersona());
			personaSeleccionada.setNombre(this.ventanaPersona.getTxtNombre().getText());
			personaSeleccionada.setTelefono(this.ventanaPersona.getTxtTelefono().getText());
			personaSeleccionada.setCalle(this.ventanaPersona.getTxtCalle().getText());
			personaSeleccionada.setAltura(Integer.parseInt(this.ventanaPersona.getTxtAltura().getText()));
			personaSeleccionada.setPiso(Integer.parseInt(this.ventanaPersona.getTxtPiso().getText()));
			personaSeleccionada.setDpto(this.ventanaPersona.getTxtDepartamento().getText());
			personaSeleccionada.setLocalidad( valorcmbxLocalidades());
			personaSeleccionada.setMail(this.ventanaPersona.getTxtMail().getText());
			personaSeleccionada.setCumpleaños(this.ventanaPersona.getTxtCumpleaños().getText());
			personaSeleccionada.setTipo(valorcmbxTiposDeContacto());
			this.agenda.modificarPersona(personaSeleccionada);
		}
		
		private void abrirVentanaPersona() 
		{
			this.ventanaPersona = new VentanaPersona(this);
			cargarCmBoxes();
		}
		
		private void cargarLocalidadesEnLista() 
		{
			this.ventanaLocalidad.getModelo().clear();
			List<LocalidadDTO> localidadesOB = this.agenda.obtenerLocalidades();
			for (LocalidadDTO localidad : localidadesOB){
				this.ventanaLocalidad.getModelo().addElement(localidad.getNombre());
			}
			this.ventanaLocalidad.getJList().setModel(this.ventanaLocalidad.getModelo());
		}
		
		private void setFieldsVentanaPersona() {
			int fila = this.vista.getTablaPersonas().getSelectedRow();
			PersonaDTO personaSeleccionada = this.personas_en_tabla.get(fila);
			setTxtField(this.ventanaPersona.getTxtNombre(), personaSeleccionada.getNombre());
			setTxtField(this.ventanaPersona.getTxtTelefono(), personaSeleccionada.getTelefono());
			setTxtField(this.ventanaPersona.getTxtCalle(), personaSeleccionada.getCalle());
			setTxtField(this.ventanaPersona.getTxtAltura(), Integer.toString(personaSeleccionada.getAltura()));
			setTxtField(this.ventanaPersona.getTxtPiso(), Integer.toString(personaSeleccionada.getPiso()));
			setTxtField(this.ventanaPersona.getTxtDepartamento(), personaSeleccionada.getDpto());
			this.ventanaPersona.getCmbxLocalidad().setSelectedItem(personaSeleccionada.getLocalidad());
			setTxtField(this.ventanaPersona.getTxtMail(), personaSeleccionada.getMail());
			setTxtField(this.ventanaPersona.getTxtCumpleaños(), personaSeleccionada.getCumpleaños());
			this.ventanaPersona.getCmbxTipoDeContacto().setSelectedItem(personaSeleccionada.getTipo());
		}
		
		public void cargarCmBoxes()
		{
			cargarCmBoxLocalidades();
			cargarCmBoxTipo();
		}

		private void cargarCmBoxLocalidades() {
			for(LocalidadDTO localidad : localidades)
			{	
				this.ventanaPersona.getCmbxLocalidad().addItem(localidad.getNombre());
			}
		}

		private void cargarCmBoxTipo() {
			for(TipoDeContactoDTO tipo : tiposDeContacto)
			{	
				this.ventanaPersona.getCmbxTipoDeContacto().addItem(tipo.getTipo());
			}
		}
	
		private String valorcmbxLocalidades()
		{
			return (String)this.ventanaPersona.getCmbxLocalidad().getSelectedItem();
		}
		
		private String valorcmbxTiposDeContacto()
		{
			return (String)this.ventanaPersona.getCmbxTipoDeContacto().getSelectedItem();
		}
		
		private boolean unaFilaSeleccionada() 
		{
			return this.vista.getTablaPersonas().getSelectedRow() > -1 && this.vista.getTablaPersonas().getSelectedRowCount() == 1;
		}
		
		private void setTxtField(JTextField txtField, String value) 
		{
			txtField.setText(value);
		}

		private void setBotonHabilitado(JButton jButton, boolean b) 
		{
			jButton.setEnabled(b);
		}
		
		private void actualizar() {
			this.llenarTabla();
			setBotonHabilitado(this.vista.getBtnEditar(),false);
		}

		private void cerrarVentana() 
		{
			this.ventanaPersona.dispose();
		}

		@Override
		public void mouseClicked(MouseEvent e) 
		{
			if(e.getSource() == vista.getTablaPersonas())
			{
				if(unaFilaSeleccionada()){ setBotonHabilitado(this.vista.getBtnEditar(), true); }
				else{ setBotonHabilitado(this.vista.getBtnEditar(),false); }
			}
			else if (this.ventanaLocalidad != null && e.getSource() == this.ventanaLocalidad.getJList()){
				String localidad = this.ventanaLocalidad.getJList().getSelectedValue();
				if (localidad != null) { this.ventanaLocalidad.getTxtNombre().setText(localidad); }
			}
			else if (e.getSource() == this.ventanaTipoDeContacto.getJList()){
				String tipoDeContacto = this.ventanaTipoDeContacto.getJList().getSelectedValue();
				if (tipoDeContacto != null) { this.ventanaTipoDeContacto.getTxtNombre().setText(tipoDeContacto); }
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

}
