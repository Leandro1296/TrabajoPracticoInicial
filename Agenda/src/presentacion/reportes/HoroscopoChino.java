package presentacion.reportes;

import java.util.ArrayList;
import java.util.List;

public class HoroscopoChino 
{
	private int resto;
	private int anio;
	
	public HoroscopoChino()
	{
		resto = 0;
		anio = 0;
	}
	
	public String calcularSigno(String fecha){
		anio = Integer.parseInt(fecha.substring(0, 4));//1234-45-45
		resto = anio % 12;
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
	
	public List<PersonaReporte> ordenarHoroscopoChino(List<PersonaReporte> personaHorocopoChinoList) 
	{
		List<PersonaReporte> listaOrdenada= new ArrayList<PersonaReporte>();
		for (int j = 0; j < 12; j++) 
		{
			for (int i = 0; i < personaHorocopoChinoList.size(); i++) 
			{
				String signo = personaHorocopoChinoList.get(i).getSignoHoroscopoChino();
				if (j==0 && signo.equals("Mono")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
				else if (j==1 && signo.equals("Perro")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
				else if (j==2 && signo.equals("Gallo")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
				else if (j==3 && signo.equals("Cerdo")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
				else if (j==4 && signo.equals("Rata")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
				else if (j==5 && signo.equals("Buey")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
				else if (j==6 && signo.equals("Tigre")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
				else if (j==7 && signo.equals("Conejo")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
				else if (j==8 && signo.equals("Dragon")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
				else if (j==9 && signo.equals("Serpiente")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
				else if (j==10 && signo.equals("Caballo")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
				else if (j==11 && signo.equals("Cabra")) 
				{
					listaOrdenada.add(personaHorocopoChinoList.get(i));
				}
			}
		}
		return listaOrdenada;
	}
	
}
