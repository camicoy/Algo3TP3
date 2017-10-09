import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Ej2Tiempos {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("ejercicio1 Tiempos");
		
		FileInputStream entrada =null;  
		BufferedReader reader =null;
		FileWriter escribidor=null;
		
		try{
			entrada = new FileInputStream(args[0]);
			reader = new BufferedReader(new InputStreamReader(entrada));
			escribidor= new FileWriter("Ej1Tiempos.out");				
			String line=reader.readLine();

			String[] ncm= line.split(" ");
			final int n=Integer.parseInt(ncm[0]);
			final int m=Integer.parseInt(ncm[1]);
			final int c=Integer.parseInt(ncm[2]);
			
			if(n<=0 || m<0 || c<1) throw new IllegalArgumentException("Los parametros de entrada no son validos");
			
			Nodo[] nodosGrafo= new Nodo[n];
			//armamos el grafo	
			for(int i=0;i<n;i++){
				nodosGrafo[i]= new Nodo(i);
				line= reader.readLine();
				String[] coloresGrafo= line.split(" ");
				for(int j=1;j<coloresGrafo.length;j++){
					nodosGrafo[i].addColor(Integer.parseInt(coloresGrafo[j]));
				}
			}
			
			int nodo1;
			int nodo2;
			for(int i=0;i<m;i++){
				line=reader.readLine();
				String[] arista= line.split(" ");
				nodo1= Integer.parseInt(arista[0]);
				nodo2= Integer.parseInt(arista[1]);
				nodosGrafo[nodo1].addSucesor(nodosGrafo[nodo2]);
				nodosGrafo[nodo2].addSucesor(nodosGrafo[nodo1]);
			}
			
			long antes;
			long duracion;
			long promedio=0;
			long[] tiempos= new long[30];		
				for(int v=0;v<30;v++){
					System.gc();
					antes=System.currentTimeMillis();				
					boolean hayContradiccion= Ejercicio1.noSePuedePintar(nodosGrafo);	
					duracion=System.currentTimeMillis()-antes;
					tiempos[v] = duracion;
					promedio+=duracion;
				}
				
				promedio = promedio/30;
				long desvio = 0;
				long[] var= new long[30];
				for(int j = 0;j < 30;j++){
					var[j] = tiempos[j] - promedio;
					var[j] = var[j] * var[j];
					desvio = desvio + var[j];
				}
				desvio = desvio/30;
				desvio = (long)Math.sqrt(desvio);
				long promedio2 = 0;
				int cant = 0;
				for(int k = 0;k < 30;k++){
					if(tiempos[k] < promedio-desvio){
						tiempos[k] = 0;
						cant--;
					}else if(tiempos[k] > promedio + desvio){
						tiempos[k] = 0;
						cant--;
					}
					promedio2 = promedio2 + tiempos[k];
					cant++;
				}
				System.out.println(promedio2/cant);
				escribidor.write(Double.toString((double)promedio2/cant));						
				escribidor.write("\n");
				promedio=0;
			}	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				reader.close();
				entrada.close();
				escribidor.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
