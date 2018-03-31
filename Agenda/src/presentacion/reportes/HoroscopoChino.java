package presentacion.reportes;

public class HoroscopoChino 
{
	private int resto;
	private int a�o;
	
	public HoroscopoChino()
	{
		resto = 0;
		a�o = 0;
	}
	
	public String calcularSigno(String fecha){
		a�o = Integer.parseInt(fecha.substring(0, 4));//1234-45-45
		resto = a�o % 12;
		   switch (resto) 
		   {
		      case 0:  return "Mono";
		      case 1:  return "Gallo";
		      case 2:  return "Perro";
		      case 3:  return "Cerdo";
		      case 4:  return "Rata";
		      case 5:  return "Buey";
		      case 6:  return "Tigre";
		      case 7:  return "Conejo";
		      case 8:  return "Dragon";
		      case 9:  return "Serpiente";
		      case 10: return "Caballo";
		      case 11: return "Cabra";
		      default: break;
		   }
		return null;
	}
	
}
