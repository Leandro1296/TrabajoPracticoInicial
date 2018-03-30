package presentacion.vista;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import dto.TipoDeContactoDTO;
import presentacion.controlador.Controlador;

public class VentanaTipoDeContacto extends JDialog
{
	
	private static final long serialVersionUID = 1L;
	private static VentanaTipoDeContacto instancia;
	private JButton btnAgregar, btnModificar, btnEliminar;
	private JLabel lblTipo;
	private JList<TipoDeContactoDTO> listaNombres;
	private DefaultListModel<TipoDeContactoDTO> modelo;
	private JScrollPane scrollLista;
	private JTextField txtNombre;
	private Controlador controlador;

	public static VentanaTipoDeContacto getVentana(Controlador controlador){
		if (instancia == null)
			return new VentanaTipoDeContacto(controlador);
		else
			return instancia;
	}
	
	private VentanaTipoDeContacto(Controlador controlador) {
		super();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.controlador = controlador;
		inicializar();
	}

	private void inicializar() {
		inicializarVentana();
		inicializarBotones();
		inicializarLabels();
		inicializarTxtFields();
		inicializarLista();
		inicializarScroll();
		this.setVisible(true);
	}

	private void inicializarVentana() {
		this.setBounds(500, 250, 451, 319);
		this.setTitle("Etiquetas");
		this.getContentPane().setLayout(null);
	}

	private void inicializarLista() {
		modelo = new DefaultListModel<TipoDeContactoDTO>();
		listaNombres = new JList<TipoDeContactoDTO>(modelo);
		listaNombres.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void inicializarScroll() {
		scrollLista = new JScrollPane();
		scrollLista.setBounds(25, 45, 383, 180);
		scrollLista.setViewportView(listaNombres);
		this.getContentPane().add(scrollLista);
	}

	private void inicializarBotones() {
		botonAgregar();
		botonEliminar();
		botonModificar();
	}
	
	private void botonAgregar() {
		btnAgregar = new JButton();
		btnAgregar.setText("Agregar");
		btnAgregar.setBounds(25, 236, 117, 33);
		btnAgregar.addActionListener(controlador);
		this.getContentPane().add(btnAgregar);
	}

	private void botonModificar() {
		btnModificar = new JButton("Modificar");
		btnModificar.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnModificar.setVerticalTextPosition(SwingConstants.CENTER);
		btnModificar.setBounds(297, 236, 111, 33);
		btnModificar.addActionListener(controlador);
		this.getContentPane().add(btnModificar);
	}

	private void botonEliminar() {
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setBounds(163, 236, 117, 33);
		btnEliminar.addActionListener(controlador);
		this.getContentPane().add(btnEliminar);
	}
	
	private void inicializarLabels() {
		lblTipo = new JLabel();
		lblTipo.setVerticalAlignment(SwingConstants.TOP);
		lblTipo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTipo.setText("Tipo: ");
		lblTipo.setBounds(39, 14, 56, 23);
		this.getContentPane().add(lblTipo);
	}

	private void inicializarTxtFields() {
		txtNombre = new JTextField();
		txtNombre.setBounds(96, 10, 312, 23);
		this.getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
	}
	
	public JButton getBtnAgregar(){return this.btnAgregar;}
	
	public JButton getBtnEliminar(){return this.btnEliminar;}
	
	public JButton getBtnModificar(){return this.btnModificar;}
	
	public JList<TipoDeContactoDTO> getJList(){return this.listaNombres;}
	
	public DefaultListModel<TipoDeContactoDTO> getModelo(){return this.modelo;}
	
	public JTextField getTxtNombre(){return this.txtNombre;}
	
	public JScrollPane getScrollPane(){return this.scrollLista;}
	
}
