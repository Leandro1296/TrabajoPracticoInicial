package presentacion.vista;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import presentacion.controlador.Controlador;

import javax.swing.JComboBox;

public class VentanaPersona extends JFrame 
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JButton btnGuardar;
	private Controlador controlador;
	private JLabel lblNombreYApellido;
	private JLabel lblTelfono;
	private JLabel lblCalle;
	private JLabel lblAltura;
	private JLabel lblPiso;
	private JLabel lblDepartamento;
	private JLabel lblLocalidad;
	private JLabel lblMail;
	private JLabel lblCumpleaños;
	private JLabel lblTipo;
	private JTextField txtCalle;
	private JTextField txtAltura;
	private JTextField txtPiso;
	private JTextField txtDepartamento;
	private JTextField txtMail;
	private JTextField txtCumpleaños;
	private JComboBox<String> cmBxLocalidad;
	private JComboBox<String> cmBxTipoDeContacto;

	public VentanaPersona(Controlador controlador) 
	{
		super();
		this.controlador = controlador;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 345, 512);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 307, 451);
		contentPane.add(panel);
		panel.setLayout(null);
		
		labels(panel);
		txtFields(panel);
		cmBoxes(panel);
		buttons(panel);
		
		this.setVisible(true);
	}

	private void buttons(JPanel panel) {
		btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(this.controlador);
		btnGuardar.setBounds(208, 417, 89, 23);
		panel.add(btnGuardar);
	}

	private void labels(JPanel panel) {
		lblNombreYApellido = new JLabel("Nombre y apellido");
		lblNombreYApellido.setBounds(10, 11, 113, 14);
		panel.add(lblNombreYApellido);
		
		lblTelfono = new JLabel("Tel\u00E9fono");
		lblTelfono.setBounds(10, 52, 113, 14);
		panel.add(lblTelfono);
		
		lblCalle = new JLabel("Calle");
		lblCalle.setBounds(10, 90, 113, 14);
		panel.add(lblCalle);
		
		lblAltura = new JLabel("Altura");
		lblAltura.setBounds(10, 133, 44, 14);
		panel.add(lblAltura);
		
		lblPiso = new JLabel("Piso");
		lblPiso.setBounds(173, 133, 28, 14);
		panel.add(lblPiso);
		
		lblDepartamento = new JLabel("Departamento");
		lblDepartamento.setBounds(10, 178, 113, 14);
		panel.add(lblDepartamento);
		
		lblLocalidad = new JLabel("Localidad");
		lblLocalidad.setBounds(10, 229, 113, 14);
		panel.add(lblLocalidad);
		
		lblMail = new JLabel("Mail");
		lblMail.setBounds(10, 276, 113, 14);
		panel.add(lblMail);
		
		lblCumpleaños = new JLabel("Cumplea\u00F1os");
		lblCumpleaños.setBounds(10, 335, 113, 14);
		panel.add(lblCumpleaños);
		
		lblTipo = new JLabel("Tipo");
		lblTipo.setBounds(10, 391, 113, 14);
		panel.add(lblTipo);
	}

	private void txtFields(JPanel panel) {
		txtNombre = new JTextField();
		txtNombre.setBounds(133, 8, 164, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(133, 49, 164, 20);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		txtCalle = new JTextField();
		txtCalle.setColumns(10);
		txtCalle.setBounds(133, 87, 164, 20);
		panel.add(txtCalle);
		
		txtAltura = new JTextField();
		txtAltura.setColumns(10);
		txtAltura.setBounds(70, 130, 65, 20);
		panel.add(txtAltura);
		
		txtPiso = new JTextField();
		txtPiso.setColumns(10);
		txtPiso.setBounds(211, 130, 65, 20);
		panel.add(txtPiso);
		
		txtDepartamento = new JTextField();
		txtDepartamento.setColumns(10);
		txtDepartamento.setBounds(133, 175, 164, 20);
		panel.add(txtDepartamento);
		
		txtMail = new JTextField();
		txtMail.setColumns(10);
		txtMail.setBounds(133, 273, 164, 20);
		panel.add(txtMail);
		
		txtCumpleaños = new JTextField();
		txtCumpleaños.setColumns(10);
		txtCumpleaños.setBounds(133, 332, 164, 20);
		panel.add(txtCumpleaños);
	}

	private void cmBoxes(JPanel panel) {
		cmBxLocalidad = new JComboBox<>();
		cmBxLocalidad.addItem("Seleccionar Localidad...");
		cmBxLocalidad.setBounds(133, 226, 164, 20);
		panel.add(cmBxLocalidad);
		
		cmBxTipoDeContacto = new JComboBox<>();
		cmBxTipoDeContacto.addItem("Seleccionar tipo de contacto...");
		cmBxTipoDeContacto.setBounds(133, 385, 164, 20);
		panel.add(cmBxTipoDeContacto);
	}
	
	public JTextField getTxtNombre() 
	{
		return txtNombre;
	}
	
	public JTextField getTxtTelefono() 
	{
		return txtTelefono;
	}

	public JTextField getTxtCalle() 
	{
		return txtCalle;
	}
	public JTextField getTxtAltura() 
	{
		return txtAltura;
	}
	public JTextField getTxtPiso() 
	{
		return txtPiso;
	}
	public JTextField getTxtDepartamento() 
	{
		return txtDepartamento;
	}
	public JTextField getTxtMail() 
	{
		return txtMail;
	}
	public JTextField getTxtCumpleaños() 
	{
		return txtCumpleaños;
	}

	public JButton getBtnGuardar() 
	{
		return btnGuardar;
	}
	
	public JComboBox<String> getCmbxLocalidad()
	{
		return cmBxLocalidad;
	}
	
	public JComboBox<String> getCmbxTipoDeContacto()
	{
		return cmBxTipoDeContacto;
	}
}

