package presentacion.controlador;

import javax.swing.JOptionPane;

public class Dialogo 
{
		public static void mensaje(String mensaje, String titulo)
		{
			JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
		}
		
		public static String input(String referencia, String titulo)
		{
			return JOptionPane.showInputDialog( null, referencia, titulo,  JOptionPane.QUESTION_MESSAGE);
		}

		public static int confimarcion(String pregunta, String titulo)
		{
			return JOptionPane.showConfirmDialog(null, pregunta, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		}
		
		public static void error(String mensaje, String titulo)
		{
			JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
		}
}
