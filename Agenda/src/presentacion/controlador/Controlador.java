package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextField;

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
								 this.agenda.obtenerLocalidad(this.personas_en_tabla.get(i).getLocalidad()).getNombre(),
								 this.personas_en_tabla.get(i).getMail(),
								 this.personas_en_tabla.get(i).getCumpleaños(),
								 this.agenda.obtenerTipoDeContacto(this.personas_en_tabla.get(i).getTipo()).getTipo()};
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
				abrirVentanaLocalidades();
			}
			else if(e.getSource() == this.vista.getBtnTiposDecontacto())
			{				
				abrirVentanaTiposDeContacto();
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
			else if(this.ventanaLocalidad != null && e.getSource() == this.ventanaLocalidad.getBtnEliminar()) 
			{
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
			else if(e.getSource() == this.ventanaTipoDeContacto.getBtnEliminar()) 
			{
				borrarTipoDeContacto();
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) 
		{
			if(e.getSource() == vista.getTablaPersonas())
			{
				if(unaFilaSeleccionada())
				{ 
					setBotonHabilitado(this.vista.getBtnEditar(), true); 
				}
				else
				{ 
					setBotonHabilitado(this.vista.getBtnEditar(),false); 
				}
			}
			else if (this.ventanaLocalidad != null && e.getSource() == this.ventanaLocalidad.getJList())
			{
				LocalidadDTO localidad = this.ventanaLocalidad.getJList().getSelectedValue();
				if (localidad != null) 
				{ 
					setTxtField(this.ventanaLocalidad.getTxtNombre(),localidad.getNombre()); 
				} 
				else
				{ 
					setTxtField(this.ventanaLocalidad.getTxtNombre(),""); 
				}
			}
			
			else if (e.getSource() == this.ventanaTipoDeContacto.getJList())
			{
				TipoDeContactoDTO tipoDeContacto = this.ventanaTipoDeContacto.getJList().getSelectedValue();
				if (tipoDeContacto != null) 
				{ 
					setTxtField(this.ventanaTipoDeContacto.getTxtNombre(), tipoDeContacto.getTipo()); 
				}
				else
				{ 
					setTxtField(this.ventanaTipoDeContacto.getTxtNombre(), ""); 
				}
			}
		}
		
		private void abrirVentanaPersona() 
		{
			this.ventanaPersona = new VentanaPersona(this);
			cargarCmBoxes();
		}
		
		public void cargarCmBoxes()
		{
			cargarCmBoxLocalidades();
			cargarCmBoxTipo();
		}

		private void cargarCmBoxLocalidades() {
			for(LocalidadDTO localidad : localidades)
			{	
				this.ventanaPersona.getCmbxLocalidad().addItem(localidad);
			}
		}

		private void cargarCmBoxTipo() {
			for(TipoDeContactoDTO tipo : tiposDeContacto)
			{	
				this.ventanaPersona.getCmbxTipoDeContacto().addItem(tipo);
			}
		}
	
		private LocalidadDTO valorcmbxLocalidades()
		{
			return (LocalidadDTO)this.ventanaPersona.getCmbxLocalidad().getSelectedItem();
		}
		
		private TipoDeContactoDTO valorcmbxTiposDeContacto()
		{
			return (TipoDeContactoDTO)this.ventanaPersona.getCmbxTipoDeContacto().getSelectedItem();
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
			setComboBoxLocalidad(personaSeleccionada);
			setTxtField(this.ventanaPersona.getTxtMail(), personaSeleccionada.getMail());
			setTxtField(this.ventanaPersona.getTxtCumpleaños(), personaSeleccionada.getCumpleaños().toString());
			setComboBoxTipoDeContacto(personaSeleccionada);
		}
		
		private void abrirVentanaLocalidades() {
			this.ventanaLocalidad = VentanaLocalidad.getVentana(this);
			cargarLocalidadesEnLista();
			this.ventanaLocalidad.getJList().addMouseListener(this);
		}

		private void abrirVentanaTiposDeContacto() {
			this.ventanaTipoDeContacto = VentanaTipoDeContacto.getVentana(this);
			cargarTiposDeContactoEnLista();
			this.ventanaTipoDeContacto.getJList().addMouseListener(this);
		}
		
		private void agregarNuevaPersona() 
		{
			PersonaDTO nuevaPersona = new PersonaDTO(0,this.ventanaPersona.getTxtNombre().getText(), 
													   this.ventanaPersona.getTxtTelefono().getText(),
													   this.ventanaPersona.getTxtCalle().getText(),
									  Integer.parseInt(this.ventanaPersona.getTxtAltura().getText()),
					   			      Integer.parseInt(this.ventanaPersona.getTxtPiso().getText()),
													   this.ventanaPersona.getTxtDepartamento().getText(),
													   this.valorcmbxLocalidades().getIdLocalidad(),
													   this.ventanaPersona.getTxtMail().getText(),
													   this.getDate(this.ventanaPersona.getTxtCumpleaños().getText()),
													   this.valorcmbxTiposDeContacto().getIdTipoDeContacto());
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
			personaSeleccionada.setLocalidad( valorcmbxLocalidades().getIdLocalidad());
			personaSeleccionada.setMail(this.ventanaPersona.getTxtMail().getText());
			personaSeleccionada.setCumpleaños(getDate(this.ventanaPersona.getTxtCumpleaños().getText()));
			personaSeleccionada.setTipo(valorcmbxTiposDeContacto().getIdTipoDeContacto());
			this.agenda.modificarPersona(personaSeleccionada);
		}
		
		private void agregarNuevaLocalidad() {
			LocalidadDTO nuevaLocalidad = new LocalidadDTO(0 , this.ventanaLocalidad.getTxtNombre().getText());
			this.agenda.agregarLocalidad(nuevaLocalidad);
			this.cargarDatos();
			cargarLocalidadesEnLista();
			setTxtField(this.ventanaLocalidad.getTxtNombre(),"");
		}

		private void borrarLocalidad() {
			LocalidadDTO localidad = this.ventanaLocalidad.getJList().getSelectedValue();
			if (localidad != null) {
				this.agenda.borrarLocalidad(this.localidades.get(buscarLocalidad(localidad.getNombre())));
				this.cargarDatos();
				cargarLocalidadesEnLista();
				setTxtField(this.ventanaLocalidad.getTxtNombre(),"");
			}
		}

		private void modificarLocalidad() {
			LocalidadDTO localidad = this.ventanaLocalidad.getJList().getSelectedValue();	 
			if (localidad != null) {
				LocalidadDTO localidad_a_modificar= this.localidades.get(buscarLocalidad(localidad.getNombre()));
				localidad_a_modificar.setNombre(this.ventanaLocalidad.getTxtNombre().getText());
				this.agenda.modificarLocalidad(localidad_a_modificar);
				this.cargarDatos();
				this.cargarLocalidadesEnLista();
				this.setTxtField(this.ventanaLocalidad.getTxtNombre(),"");
				this.llenarTabla();
			} else {
			
			}
		}
		
		private void cargarLocalidadesEnLista() 
		{
			this.ventanaLocalidad.getModelo().clear();
			List<LocalidadDTO> localidadesOB = this.agenda.obtenerLocalidades();
			for (LocalidadDTO localidad : localidadesOB){
				this.ventanaLocalidad.getModelo().addElement(localidad);
			}
			System.out.println(this.ventanaLocalidad.getModelo().getSize());
			this.ventanaLocalidad.getJList().setModel(this.ventanaLocalidad.getModelo());
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
		
		private void agregarTipoDeContacto() {
			TipoDeContactoDTO nuevoTipoDeContacto = new TipoDeContactoDTO(0, this.ventanaTipoDeContacto.getTxtNombre().getText());
			this.agenda.agregarTipoDeContacto(nuevoTipoDeContacto);
			this.cargarDatos();
			this.cargarTiposDeContactoEnLista();
			this.setTxtField(this.ventanaTipoDeContacto.getTxtNombre(),"");
		}
		
		private void borrarTipoDeContacto() {
			TipoDeContactoDTO tipoDeContacto = this.ventanaTipoDeContacto.getJList().getSelectedValue();
			if (tipoDeContacto != null) {
				this.agenda.borrarTipoDeContacto(this.tiposDeContacto.get(buscar(tipoDeContacto.getTipo())));
				this.cargarDatos();
				this.cargarTiposDeContactoEnLista();
				this.setTxtField(this.ventanaTipoDeContacto.getTxtNombre(),"");
			}
		}

		private void modificarTipoDeContacto() {
			TipoDeContactoDTO tipoDeContacto = this.ventanaTipoDeContacto.getJList().getSelectedValue();	 
			if (tipoDeContacto != null) {
				TipoDeContactoDTO tipoDeContacto_a_modificar= this.tiposDeContacto.get(buscar(tipoDeContacto.getTipo()));
				tipoDeContacto_a_modificar.setTipo(this.ventanaTipoDeContacto.getTxtNombre().getText());
				this.agenda.modificarTipoDeContacto(tipoDeContacto_a_modificar);
				this.cargarDatos();
				this.cargarTiposDeContactoEnLista();
				this.setTxtField(this.ventanaTipoDeContacto.getTxtNombre(),"");
				this.llenarTabla();
			} else {
			
			}
		}

		private void cargarTiposDeContactoEnLista() 
		{
			this.ventanaTipoDeContacto.getModelo().clear();
			List<TipoDeContactoDTO> TipoDeContactoesOB = this.agenda.obtenerTiposDeContacto();
			for (TipoDeContactoDTO tipoDeContacto : TipoDeContactoesOB){
				this.ventanaTipoDeContacto.getModelo().addElement(tipoDeContacto);
			}
			this.ventanaTipoDeContacto.getJList().setModel(this.ventanaTipoDeContacto.getModelo());
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
		
		public void cargarDatos()
		{
			this.localidades = agenda.obtenerLocalidades();
			this.tiposDeContacto = agenda.obtenerTiposDeContacto();
		}
		
		private java.sql.Date getDate(String fecha)
		{
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = format.parse(fecha);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			return sqlDate;
		}
		
		private boolean unaFilaSeleccionada() 
		{
			return this.vista.getTablaPersonas().getSelectedRow() > -1 && this.vista.getTablaPersonas().getSelectedRowCount() == 1;
		}
		
		private void setTxtField(JTextField txtField, String value) 
		{
			txtField.setText(value);
		}
		
		private void setComboBoxTipoDeContacto(PersonaDTO personaSeleccionada) {
			
			int tipo = 0;
			for(int i = 0;i< this.ventanaPersona.getCmbxTipoDeContacto().getItemCount();i++){
				if(this.ventanaPersona.getCmbxTipoDeContacto().getItemAt(i).getIdTipoDeContacto() == personaSeleccionada.getTipo())
						{tipo = i;}
			}
			this.ventanaPersona.getCmbxTipoDeContacto().setSelectedIndex(tipo);
		}

		private void setComboBoxLocalidad(PersonaDTO personaSeleccionada) {
			int localidad = 0;
			for(int i = 0;i< this.ventanaPersona.getCmbxLocalidad().getItemCount();i++){
				if(this.ventanaPersona.getCmbxLocalidad().getItemAt(i).getIdLocalidad() == personaSeleccionada.getLocalidad())
						{localidad = i;}
			}
			this.ventanaPersona.getCmbxLocalidad().setSelectedIndex(localidad);
		}
		
		private void setBotonHabilitado(JButton jButton, boolean b) 
		{
			jButton.setEnabled(b);
		}
		
		private void actualizar() 
		{
			this.llenarTabla();
			setBotonHabilitado(this.vista.getBtnEditar(),false);
		}

		private void cerrarVentana() 
		{
			this.ventanaPersona.dispose();
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
