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
		private Validador validador;
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
			this.validador = new Validador();
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
								 this.getDateString(this.personas_en_tabla.get(i).getCumpleaños()),
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
				generarReporte();				
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
				if(verificarCampos())
				{
					if(seAgregaNueva)
					{ 
						agregarNuevaPersona(); 
					}
					else
					{ 
						modificarPersona(); 
					}
					actualizar();
					cerrarVentana();
				}
			}
			
			else if(this.ventanaLocalidad != null && e.getSource() == this.ventanaLocalidad.getBtnAgregar()) 
			{
				if(esLocalidadValida())
				{
					agregarNuevaLocalidad();	
				}
			}
			
			else if (this.ventanaLocalidad != null && e.getSource() == this.ventanaLocalidad.getBtnModificar()) 
			{
				if(esLocalidadValida())
				{
					modificarLocalidad();
				}
			} 
			else if(this.ventanaLocalidad != null && e.getSource() == this.ventanaLocalidad.getBtnEliminar()) 
			{
				borrarLocalidad();
			}
			
			else if(e.getSource() == this.ventanaTipoDeContacto.getBtnAgregar()) 
			{
				if(esTipoDeContactoValido())
				{
					agregarTipoDeContacto();
				}
			}
			
			else if (e.getSource() == this.ventanaTipoDeContacto.getBtnModificar()) 
			{
				if(esTipoDeContactoValido())
				{
					modificarTipoDeContacto();
				}
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
		
		//***********************METODOS DE VENTANAS********************//
		
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
			setTxtField(this.ventanaPersona.getTxtCumpleaños(), getDateString(personaSeleccionada.getCumpleaños()));
			setComboBoxTipoDeContacto(personaSeleccionada);
		}
		
		private void abrirVentanaLocalidades() 
		{
			this.ventanaLocalidad = VentanaLocalidad.getVentana(this);
			cargarLocalidadesEnLista();
			this.ventanaLocalidad.getJList().addMouseListener(this);
		}

		private void abrirVentanaTiposDeContacto() 
		{
			this.ventanaTipoDeContacto = VentanaTipoDeContacto.getVentana(this);
			cargarTiposDeContactoEnLista();
			this.ventanaTipoDeContacto.getJList().addMouseListener(this);
		}
		
		private void generarReporte() 
		{
			ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
			reporte.mostrar();
		}
		
		//*************************ABM PERSONA******************************//
		
		private void agregarNuevaPersona() 
		{
			PersonaDTO nuevaPersona = new PersonaDTO(0,this.ventanaPersona.getTxtNombre().getText(), 
					this.ventanaPersona.getTxtTelefono().getText(),
					this.ventanaPersona.getTxtCalle().getText(),
					Integer.parseInt(this.ventanaPersona.getTxtAltura().getText()),
					this.valorEntero(this.ventanaPersona.getTxtPiso().getText()),
					this.ventanaPersona.getTxtDepartamento().getText(),
					this.valorcmbxLocalidades().getIdLocalidad(),
					this.ventanaPersona.getTxtMail().getText(),
					this.getDate(this.ventanaPersona.getTxtCumpleaños().getText()),
					this.valorcmbxTiposDeContacto().getIdTipoDeContacto());
			this.agenda.agregarPersona(nuevaPersona);
			Dialogo.mensaje("El contacto se agrego exitosamente", "Nueva Persona");
		}
				
		private void borrarPersonas() 
		{
			int[] filas_seleccionadas = this.vista.getTablaPersonas().getSelectedRows();
			if(filas_seleccionadas.length == 0)
			{
				Dialogo.error("No hay ninguna persona seleccionada", "Error al borrar personas");
			}
			else
			{
				for (int fila:filas_seleccionadas)
				{
					this.agenda.borrarPersona(this.personas_en_tabla.get(fila));
				}
				Dialogo.mensaje("Borrado exitoso", "Borrar personas");
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
			Dialogo.mensaje("El contacto se modifico exitosamente", "Modicacion de Contacto");
		}
		
		//*************************ABM LOCALIDAD******************************//
		
		private void agregarNuevaLocalidad() 
		{
			LocalidadDTO nuevaLocalidad = new LocalidadDTO(0 , this.ventanaLocalidad.getTxtNombre().getText());
			this.agenda.agregarLocalidad(nuevaLocalidad);
			Dialogo.mensaje("La localidad se agrego con exito", "Agregar localidad");
			this.cargarDatos();
			cargarLocalidadesEnLista();
			setTxtField(this.ventanaLocalidad.getTxtNombre(),"");
		}

		private void borrarLocalidad() 
		{
			LocalidadDTO localidad = this.ventanaLocalidad.getJList().getSelectedValue();
			if (localidad != null) 
			{	
				System.out.println(this.agenda.estaUsada(localidad));
				if(!this.agenda.estaUsada(localidad))
				{
					this.agenda.borrarLocalidad(this.localidades.get(buscarLocalidad(localidad.getNombre())));
					this.cargarDatos();
					cargarLocalidadesEnLista();
					setTxtField(this.ventanaLocalidad.getTxtNombre(),"");
					Dialogo.mensaje("La localidad se borro con exito", "Borrar localidad");
				}
				else
				{
					Dialogo.error("La localidad esta en uso", "Error");
				}
			}
			else 
			{
				Dialogo.error("No hay una localidad seleccionada", "Error");
			}
		}

		private void modificarLocalidad()
		{
			LocalidadDTO localidad = this.ventanaLocalidad.getJList().getSelectedValue();	 
			if (localidad != null) 
			{
				LocalidadDTO localidad_a_modificar= this.localidades.get(buscarLocalidad(localidad.getNombre()));
				localidad_a_modificar.setNombre(this.ventanaLocalidad.getTxtNombre().getText());
				this.agenda.modificarLocalidad(localidad_a_modificar);
				Dialogo.mensaje("La localidad se modifico con exito", "Modificar localidad");
				this.cargarDatos();
				this.cargarLocalidadesEnLista();
				this.setTxtField(this.ventanaLocalidad.getTxtNombre(),"");
				this.llenarTabla();
			} 
			else 
			{
				Dialogo.error("No hay una localidad seleccionada", "Error");
			}
		}
		
		private void cargarLocalidadesEnLista() 
		{
			this.ventanaLocalidad.getModelo().clear();
			List<LocalidadDTO> localidadesOB = this.agenda.obtenerLocalidades();
			for (LocalidadDTO localidad : localidadesOB){
				this.ventanaLocalidad.getModelo().addElement(localidad);
			}
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
		
		//*************************ABM TIPO DE CONTACTO******************************//
		
		private void agregarTipoDeContacto() {
			TipoDeContactoDTO nuevoTipoDeContacto = new TipoDeContactoDTO(0, this.ventanaTipoDeContacto.getTxtNombre().getText());
			this.agenda.agregarTipoDeContacto(nuevoTipoDeContacto);
			Dialogo.mensaje("La etiqueta se agrego con exito", "Agregar Etiqueta");
			this.cargarDatos();
			this.cargarTiposDeContactoEnLista();
			this.setTxtField(this.ventanaTipoDeContacto.getTxtNombre(),"");
		}
		
		private void borrarTipoDeContacto() {
			TipoDeContactoDTO tipoDeContacto = this.ventanaTipoDeContacto.getJList().getSelectedValue();
			if (tipoDeContacto != null) {
				if(!this.agenda.estaUsado(tipoDeContacto))
				{
					this.agenda.borrarTipoDeContacto(this.tiposDeContacto.get(buscar(tipoDeContacto.getTipo())));
					Dialogo.mensaje("Borrado con exito", "Borrar Etiqueta");
					this.cargarDatos();
					this.cargarTiposDeContactoEnLista();
					this.setTxtField(this.ventanaTipoDeContacto.getTxtNombre(),"");
				}
				else
				{
					Dialogo.error("La Etiqueta esta en uso", "Error");
				}
			}
			else
			{
				Dialogo.error("No hay una etiqueta seleccianada", "Error");
			}
		}

		private void modificarTipoDeContacto() 
		{
			TipoDeContactoDTO tipoDeContacto = this.ventanaTipoDeContacto.getJList().getSelectedValue();	 
			if (tipoDeContacto != null) 
			{
				TipoDeContactoDTO tipoDeContacto_a_modificar= this.tiposDeContacto.get(buscar(tipoDeContacto.getTipo()));
				tipoDeContacto_a_modificar.setTipo(this.ventanaTipoDeContacto.getTxtNombre().getText());
				this.agenda.modificarTipoDeContacto(tipoDeContacto_a_modificar);
				Dialogo.mensaje("La etiqueta se modifico con exito", "Modificar Etiqueta");
				this.cargarDatos();
				this.cargarTiposDeContactoEnLista();
				this.setTxtField(this.ventanaTipoDeContacto.getTxtNombre(),"");
				this.llenarTabla();
			}
			else
			{
				Dialogo.error("No hay una etiqueta seleccianada", "Error");
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
		
		private Integer valorEntero(String text) 
		{
			if(estaVacio(text)){ return 0; }
			else{ return Integer.parseInt(text); }
		}

//*************************VALIDACIONES VENTANA PERSONA******************************//		
		
		private boolean verificarCampos()
		{
			boolean camposValidos = true;
			String nombre = this.ventanaPersona.getTxtNombre().getText();
			String telefono = this.ventanaPersona.getTxtTelefono().getText();
			String calle = this.ventanaPersona.getTxtCalle().getText();
			String altura = this.ventanaPersona.getTxtAltura().getText();
			String piso = this.ventanaPersona.getTxtPiso().getText();
			String mail = this.ventanaPersona.getTxtMail().getText();
			String cumpleaños = this.ventanaPersona.getTxtCumpleaños().getText();

			if(estaVacio(nombre)||estaVacio(telefono)||estaVacio(calle)||
			   estaVacio(altura)||estaVacio(mail)||estaVacio(cumpleaños))
			{
				Dialogo.error("Faltan completar campos", "Campos obligatorios vacios");
				camposValidos = false;
			}
			else if(!validador.nombresValido(nombre))
			{
				Dialogo.error("Nombre y Apellidos invalidos", "Error");
				camposValidos = false;
			}
			else if(!validador.telefonoValido(telefono))
			{
				Dialogo.error("Numero de telefono invalido. Debe ser (11xxxxxxxx o 15xxxxxxxx )", "Error");
				camposValidos = false;
			}
			else if(!validador.nombreValido(calle))
			{
				Dialogo.error("Nombre de calle invalido", "Error");
				camposValidos = false;
			}
			else if(!validador.alturaValida(altura))
			{
				Dialogo.error("La altura debe ser un numero", "Error");
				camposValidos = false;
			}
			else if(!validador.pisoValido(piso))
			{
				Dialogo.error("No se admite letras en el campo 'Piso'", "Error");
				camposValidos = false;
			}
			else if(!validador.mailValido(mail))
			{
				Dialogo.error("Direccion de mail incorrecta. Debe ser (nombre@dominio.com)", "Error");
				camposValidos = false;
			}
			else if(!validador.fechaValida(cumpleaños))
			{
				System.out.println(cumpleaños);
				Dialogo.error("La fecha ingresada es incorrecta. Debe ser (dd/mm/yyyy)", "Error");
				camposValidos = false;
			}
			else
			{
				camposValidos = true;
			}
			return camposValidos;
		}
		
		public boolean estaVacio(String valor)
		{
			return valor.trim().equals("");
		}

		//*************************VALIDACIONES VENTANA LOCALIDAD******************************//
		
		private boolean esLocalidadValida() 
		{
			boolean esValido = true;
			String localidad = this.ventanaLocalidad.getTxtNombre().getText();
			if(estaVacio(localidad) || !validador.nombreValido(localidad))
			{
				Dialogo.error("Por favor, ingrese una localidad valida", "Error");
				esValido = false;
			}
			else if (estaEnLista(localidad))
			{
				Dialogo.error("La localidad ya exite", "Error");
				esValido = false;
			}
			else
			{
				esValido = true;
			}
			return esValido;
		}
		
		private boolean estaEnLista(String localidad) 
		{
			return buscarLocalidad(localidad) >= 0;
		}
		
		//*************************VALIDACIONES VENTANA TIPO DE CONTACTO******************************//
		
		private boolean esTipoDeContactoValido() 
		{
			boolean esValido = true;
			String tipoDeContacto = this.ventanaTipoDeContacto.getTxtNombre().getText();
			if(estaVacio(tipoDeContacto) || !validador.nombreValido(tipoDeContacto))
			{
				Dialogo.error("Por favor, ingrese una etiqueta valida", "Error al agregar etiqueta");
				esValido = false;
			}
			else if (estaTipoDeContactoEnLista(tipoDeContacto))
			{
				Dialogo.error("La etiqueta ya exite", "Error al agregar etiqueta");
				esValido = false;
			}
			else
			{
				esValido = true;
			}
			return esValido;
		}
		
		private boolean estaTipoDeContactoEnLista(String tipoDeContacto) 
		{
			return buscar(tipoDeContacto) >= 0;
		}
		
		public void cargarDatos()
		{
			this.localidades = agenda.obtenerLocalidades();
			this.tiposDeContacto = agenda.obtenerTiposDeContacto();
		}
		
		//**************METODOS PARA FORMATO DE FECHAS*****************//
		
		private java.sql.Date getDate(String fecha)
		{
			DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			Date date = null;
			try {
				date = format.parse(fecha);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			return sqlDate;
		}
		
		private String getDateString(java.sql.Date fecha)
		{
			DateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
			return formato.format(fecha);
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
