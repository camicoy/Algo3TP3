//package algo3tp3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;

public class Ejercicio2 {
	
	private static int minimaCantidadConflictos;
	private static int[] coloresOptimos;
	private static int[] coloresSinNingunaPoda;

	//////////////////////////////////////////////////////////////////////////////////
	//version de backtraking sin podas
	public static boolean tieneColoreoAuxSinPodas(Nodo[] nodosGrafo, int numNodo){
		
		if (numNodo==nodosGrafo.length){//caso base
			
			// retorno true si es una solucion valida
			
			for(int i=0;i<nodosGrafo.length;i++){
				int colorActual=nodosGrafo[i].getColor();
				for(Nodo sucesor:nodosGrafo[i].getSucesores()){
					if(sucesor.getColor()==colorActual) return false;
				}
			}
			return true;
		}
		
		//si quedan dos colores por nodo aplico el ejercicio1, caso base
		
		/*boolean aplicoEj1=true;
		for(int i=0;i<nodosGrafo.length;i++){
			if(nodosGrafo[i].getColoresPosibles().size() != 2){
				aplicoEj1=false;
				break;
			}
		}
		if(aplicoEj1){
			System.out.println("aplicoEj1");
			return (! Ejercicio1.noSePuedePintar(nodosGrafo));
		}
	*/	
		Nodo nodoActual=nodosGrafo[numNodo]; 
		for(int color:nodoActual.getColoresPosibles()){
			
			nodoActual.setColor(color);	
			if (tieneColoreoAuxSinPodas(nodosGrafo, numNodo+1)) {// tiene una minima poda que es quedarse con una solucion si ya la encontro
				return true;			
			}
		}
		// en caso de que ninguno de los colores sirva para ese nodo, retornamos falso
		return false;
	}
	
		//la version sin podas
		public static boolean tieneColoreoSinPodas(Nodo[] nodosGrafo){
			return tieneColoreoAuxSinPodas(nodosGrafo, 0);
		}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
		//////////////////////////////////////////////////////////////////////////////////
		//version de backtraking sin ninguna podas
		public static boolean tieneColoreoAuxSinNingunaPodas(Nodo[] nodosGrafo, int numNodo){
			
			if (numNodo==nodosGrafo.length){//caso base
				
				// retorno true si es una solucion valida
				
				for(int i=0;i<nodosGrafo.length;i++){
					int colorActual=nodosGrafo[i].getColor();
					for(Nodo sucesor:nodosGrafo[i].getSucesores()){
						if(sucesor.getColor()==colorActual) return false;
					}
				}
				for(int i=0;i<coloresSinNingunaPoda.length;i++){
					coloresSinNingunaPoda[i]=nodosGrafo[i].getColor();
				}
				return true;
			}
			
			Nodo nodoActual=nodosGrafo[numNodo];
		//	System.out.println("nodoActual "+nodoActual.getId());
			boolean[] sonSolucionesPosible= new boolean[nodoActual.getColoresPosibles().size()];
			Arrays.fill(sonSolucionesPosible, false);
			//Iterator<Integer> itColores=nodoActual.getColoresPosibles().iterator();
			int ind=0;
			for(int color:nodoActual.getColoresPosibles()){
			//	System.out.println("color " +color);
				
				nodoActual.setColor(color);	
				if (tieneColoreoAuxSinNingunaPodas(nodosGrafo, numNodo+1)) {
					sonSolucionesPosible[ind]=true;
				}else{
					sonSolucionesPosible[ind]=false;
				}
				ind++;
			}
			boolean res=sonSolucionesPosible[0];
			for(int i=1;i<sonSolucionesPosible.length;i++){
				res= (res || sonSolucionesPosible[i]);
			}
		//	System.out.println(res);
			return res;
		}
		
			//la version sin ninguna podas
			public static boolean tieneColoreoSinNingunaPodas(Nodo[] nodosGrafo){
				return tieneColoreoAuxSinNingunaPodas(nodosGrafo, 0);
			}
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	//indica si se puede pintar de ese color,es decir si todos los sucesores tienen otro color(no hay conflictos)
	public static boolean sePuedePintar(Nodo[] nodosGrafo, int numNodo, int color){
		for(Nodo sucesor: nodosGrafo[numNodo].getSucesores()){
			if(sucesor.tieneColor() && (sucesor.getColor()==color)) return false;
		}
		return true;
	}
	
	//esta funcion se usara en main con numNodo inicial de 0, pintara los nodos de cierto color
	public static boolean tieneColoreoAuxPodas(Nodo[] nodosGrafo, int numNodo){
		
		if (numNodo==nodosGrafo.length) return true;//caso base
		
		//si quedan dos colores por nodo aplico el ejercicio1, caso base
		
		boolean aplicoEj1=true;
		for(int i=0;i<nodosGrafo.length;i++){
			if(nodosGrafo[i].getColoresPosibles().size() != 2){
				aplicoEj1=false;
				break;
			}
		}
		if(aplicoEj1){
			System.out.println("aplicoEj1");
			return (! Ejercicio1.noSePuedePintar(nodosGrafo));
		}
													
		Nodo nodoActual=nodosGrafo[numNodo];
		// Creo un i iterador para la lista de coleres actual del nodo 
		Iterator<Integer> it = nodoActual.getColoresPosibles().iterator();
		while(it.hasNext()){
			Integer color= it.next();
			if(sePuedePintar(nodosGrafo, numNodo, color)){
				nodoActual.setColor(color);	
				//nodoActual.removeColor(color);
				it.remove();
				if (tieneColoreoAuxPodas(nodosGrafo, numNodo+1)) {
					nodoActual.restaurarColoresPosibles();   //NOSE SI VA, POR LAS DUADAS SI
					return true;			
				}
			}
		
		}
	/*	for(Integer color:nodoActual.getColoresPosibles()){
			
			if(sePuedePintar(nodosGrafo, numNodo, color)){
				nodoActual.setColor(color);	
				//nodoActual.removeColor(color);
				(nodoActual.getColoresPosibles()).remove(color);
				if (tieneColoreoAuxPodas(nodosGrafo, numNodo+1)) {
					nodoActual.restaurarColoresPosibles();   //NOSE SI VA, POR LAS DUADAS SI
					return true;			
				}
			}
		}
	*/	
		// en caso de que ninguno de los colores sirva para ese nodo, retornamos falso
		nodoActual.restaurarColoresPosibles();
		return false;
	}
	
	//la version con podas
	public static boolean tieneColoreoPodas(Nodo[] nodosGrafo){
		return tieneColoreoAuxPodas(nodosGrafo, 0);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public static int calcularCantidadConflictos(Nodo[] nodosGrafo){
		int cantConflictos=0;
		for(int i=0;i< nodosGrafo.length;i++){
			int colorActual=nodosGrafo[i].getColor();
			for(Nodo sucesores: nodosGrafo[i].getSucesores()){
				if(sucesores.tieneColor() && sucesores.getColor()==colorActual) cantConflictos++;
			}
		}
		return (cantConflictos/2);
	}
	
	public static void cantidadConflictosOptimaAux(Nodo[] nodosGrafo, int numNodo){
		if (numNodo==nodosGrafo.length){
			int cantConflic=calcularCantidadConflictos(nodosGrafo);
			if(cantConflic<minimaCantidadConflictos){
				minimaCantidadConflictos=cantConflic;
				for(int i=0;i<nodosGrafo.length;i++){
					coloresOptimos[i]=nodosGrafo[i].getColor();
				}
			}
			return;
		}
		for(int color:nodosGrafo[numNodo].getColoresPosibles()){
			nodosGrafo[numNodo].setColor(color);
			cantidadConflictosOptimaAux(nodosGrafo,numNodo+1);
		}
	}
	
	public static void cantidadConflictosOptima(Nodo[] nodosGrafo){
		cantidadConflictosOptimaAux(nodosGrafo, 0);
	}
	
	//las version con cantidad de conflictos optima
	public static boolean tieneColoreoCantidadConflictosOptima(Nodo[] nodosGrafo){
		cantidadConflictosOptimaAux(nodosGrafo, 0);
		return (minimaCantidadConflictos==0);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("ejercicio2");
		
		FileInputStream entrada =null;  
		BufferedReader reader =null;
		FileWriter escribidor=null;
		
		try{
			entrada = new FileInputStream(args[0]);
			reader = new BufferedReader(new InputStreamReader(entrada));
			escribidor= new FileWriter("Ej2Salidas.out");				
			String line=reader.readLine();

			String[] ncm= line.split(" ");
			final int n=Integer.parseInt(ncm[0]);
			final int m=Integer.parseInt(ncm[1]);
			final int c=Integer.parseInt(ncm[2]);
			
			int tipoColoreo=Integer.parseInt(args[1]);
			
			minimaCantidadConflictos=Integer.MAX_VALUE;
			coloresOptimos=new int[n];
			coloresSinNingunaPoda=new int[n];
			
			if(n<=0 || m<0 || c<1) throw new IllegalArgumentException("Los parametros de entrada no son validos");
			
			Nodo[] nodosGrafo= new Nodo[n];
				
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
			
			for(int i=0;i<nodosGrafo.length;i++){
				nodosGrafo[i].fijarColoresOriginales();
			}
			
			//SE ENCUENTRAN COMENTADAS LAS TRES FORMAS DE COLOREARLO, LA DE SIN NINGUNA PODA PODRIA HACERCE ALGUN TEST MAS PERO LOS BASICOS LOS PASA
			
			switch(tipoColoreo){
				
				case 0:
					
					System.out.println("ConPodas");
					//la siguiente funcion colorea el grafo de ser posible
					if (tieneColoreoPodas(nodosGrafo)){
						for(int i=0;i<n;i++){
							escribidor.write(Integer.toString(nodosGrafo[i].getColor()));						
							escribidor.write("\n");
						}
					}else{
						escribidor.write("X");
						escribidor.write("\n");
						}
					break;
					
				case 1:
					
					System.out.println("SinPodas");
					//la siguiente funcion colorea el grafo de ser posible
					if (tieneColoreoSinPodas(nodosGrafo)){
						for(int i=0;i<n;i++){
							escribidor.write(Integer.toString(nodosGrafo[i].getColor()));						
							escribidor.write("\n");
						}
					}else{
						escribidor.write("X");
						escribidor.write("\n");
						}
					break;
					
				case 2:
					
					System.out.println("SinNingunaPodas");
					//la siguiente funcion colorea el grafo de ser posible
					if (tieneColoreoSinNingunaPodas(nodosGrafo)){
						for(int i=0;i<n;i++){
							escribidor.write(Integer.toString(coloresSinNingunaPoda[i]));						
							escribidor.write("\n");
						}
					}else{
						escribidor.write("X");
						escribidor.write("\n");
						}
					break;
				
				case 3:
					
					System.out.println("cantidadConflictosMenor");
					//la siguiente funcion colorea el grafo de ser posible
					if (tieneColoreoCantidadConflictosOptima(nodosGrafo)){
						for(int i=0;i<n;i++){
							escribidor.write(Integer.toString(nodosGrafo[i].getColor()));						
							escribidor.write("\n");
						}
					}else{
						escribidor.write("X");
						escribidor.write("\n");
						}
					break;
				
				default:
					System.err.println("el numero ingresado no es correcto");

				
			}
			

		}catch (FileNotFoundException e) {
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
