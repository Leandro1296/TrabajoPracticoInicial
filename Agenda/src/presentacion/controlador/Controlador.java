package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import modelo.Agenda;
import presentacion.reportes.ReporteAgenda;
import presentacion.vista.VentanaPersona;
import presentacion.vista.Vista;
import dto.LocalidadDTO;
import dto.PersonaDTO;
import dto.TipoDeContactoDTO;

public class Controlador implements ActionListener
{
		private Vista vista;
		private List<PersonaDTO> personas_en_tabla;
		private List<LocalidadDTO> localidades;
		private List<TipoDeContactoDTO> tiposDeContacto;
		private VentanaPersona ventanaPersona; 
		private Agenda agenda;
		
		public Controlador(Vista vista, Agenda agenda)
		{
			this.vista = vista;
			this.vista.getBtnAgregar().addActionListener(this);
			this.vista.getBtnBorrar().addActionListener(this);
			this.vista.getBtnReporte().addActionListener(this);
			this.agenda = agenda;
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
				this.ventanaPersona = new VentanaPersona(this);
				cargarCmBox();
			}
			else if(e.getSource() == this.vista.getBtnBorrar())
			{
				int[] filas_seleccionadas = this.vista.getTablaPersonas().getSelectedRows();
				for (int fila:filas_seleccionadas)
				{
					this.agenda.borrarPersona(this.personas_en_tabla.get(fila));
				}
				
				this.llenarTabla();
				
			}
			else if(e.getSource() == this.vista.getBtnReporte())
			{				
				ReporteAgenda reporte = new ReporteAgenda(agenda.obtenerPersonas());
				reporte.mostrar();				
			}
			else if(e.getSource() == this.ventanaPersona.getBtnAgregarPersona())
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
				this.llenarTabla();
				this.ventanaPersona.dispose();
			}
		}
		
		public void cargarCmBox()
		{
			this.localidades = agenda.obtenerLocalidades();
			this.tiposDeContacto = agenda.obtenerTiposDeContacto();
			for(LocalidadDTO localidad : localidades)
			{	
				this.ventanaPersona.getCmbxLocalidad().addItem(localidad.getNombre());
			}
			
			for(TipoDeContactoDTO tipo : tiposDeContacto)
			{	
				this.ventanaPersona.getCmbxTipoDeContacto().addItem(tipo.getTipo());
			}
		}
		
		public String valorcmbxLocalidades(){
			return (String)this.ventanaPersona.getCmbxLocalidad().getSelectedItem();
		}
		
		public String valorcmbxTiposDeContacto(){
			return (String)this.ventanaPersona.getCmbxTipoDeContacto().getSelectedItem();
		}
}
