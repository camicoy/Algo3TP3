//package algo3tp3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

//import Nodo.class;
//f a optimizar(minimizar) es cantidad de conflictos(adyacents del mismo color)

public class Ejercicio4 {
	
	public static void mejorarSiPosible(Nodo[] nodosGrafo,int conflictosInicial ){
		boolean huboMejora=false;
		int conflictosDiferencia=Integer.MAX_VALUE;
		
		busqueda:
		for(int i=0;i<nodosGrafo.length;i++){  // Se hace n veces * O(c) * O(n) => n*c*n 
			int colorOriginalNodo= nodosGrafo[i].getColor(); // O(1)
			for(int color : nodosGrafo[i].getColoresPosibles()){ // Se hace coloresPosibles veces * O(n) => n*c
				//~ nodosGrafo[i].setColor(color); //O(1)
				//~ cantConflic=Ejercicio2.calcularCantidadConflictos(nodosGrafo);  // No se, calcular o desp sacar de cuando hagan complejidad del 2
				//~ if(cantConflic<conflictosInicial){ //O(1)
					//~ conflictosInicial=cantConflic; //O(1)
					//~ huboMejora=true; //O(1)
					//~ break busqueda; // O(1)
				//~ }
				conflictosDiferencia = calcularCantidadConflictos(nodosGrafo,i,color); // O(n) 
				if(conflictosDiferencia > 0){
					//Entonces, el color "nuevo" genera menos conflictos, entonces debo cambiarlo!!
					conflictosInicial = conflictosInicial - conflictosDiferencia; // Le saco la diferencia de conflictos, que tenia el anterior
																				  // color, con el nuevo!! Es la forma de actualizar la cantidad
																				  // de conflictos totales, sin tener que volver a calcularlo.
					huboMejora = true;
					nodosGrafo[i].setColor(color); // O(1)
				}
			}
			if(huboMejora){
				break busqueda;
			}
			//nodosGrafo[i].setColor(colorOriginalNodo);//O(1)
		}
		if(huboMejora){ // O(1)
			//huboMejora=false;
			mejorarSiPosible(nodosGrafo,conflictosInicial );
		}
		/* Entonces complejidad total, es de O(1) + O(n*c*n) del algoritmo, pero es recursivo,
		 * y como mucho se va a llamar conflictoInicial veces, suponiendo que cada vez que corre el algoritmo se arregla 1 solo conflicto, 
		 * entonces la complejidad total del algoritmo es O(m*n*n*c) ya que conflictoInicial se puede
		 * acotar por m, ya que como mucho, un nodo esta conectado con todos los otros nodos, y tiene un conflicto con cada uno */
		
		//System.out.println("salimos");
		return;
		
	}
	
	
	

	public static void heuristicaBusquedaLocal1(Nodo[] nodosGrafo){
		//primero coloreamos aleatoriamente 
		List<Integer> coloresPosibles;
		Random aleatorio=new Random(5);// las semmilla esta fijada en 5, se puede cambiar, para obtener otra aleatoriedad
		int numColorAPintar;
		int colorAPintar;
		for(int i=0;i<nodosGrafo.length;i++){ // Se corre n veces,* O(1) => O(n)
			coloresPosibles=nodosGrafo[i].getColoresPosibles(); //O(1)
			numColorAPintar=aleatorio.nextInt(coloresPosibles.size()); // O(1)
			colorAPintar=coloresPosibles.get(numColorAPintar); //O(1)
			nodosGrafo[i].setColor(colorAPintar); //O (1)
		}
		
		//elegimos una solucion vecina con menor f que el actual
		
		int conflictosInicial=Ejercicio2.calcularCantidadConflictos(nodosGrafo); //O(n*n)
		mejorarSiPosible(nodosGrafo,conflictosInicial); //O(m*n*n*c)
		/* Entonces la complejidad total del algoritmo es, O(m*n*n*c) ya que 
		 * O(n)<= O(n*n) <=O(m*n*n*c) con m > 0 && c >0, que son necesarios sino no habria ningun coloreo posible.*/
		
	}
////////////////////////////////////////////////////////////////////////////////////////
	
	
	public static void heuristicaBusquedaLocal2(Nodo[] nodosGrafo){
		
		//nuevamente primero se pinta en forma aleatoria, una tecnica GRAPS, es pintarlo primero con heuristica golosa, y despues aplicar la local
		//primero coloreamos aleatoriamente 
		List<Integer> coloresPosibles;
		Random aleatorio=new Random(5);// las semmilla esta fijada en 5, se puede cambiar, para obtener otra aleatoriedad
		int numColorAPintar;
		int colorAPintar;
		for(int i=0;i<nodosGrafo.length;i++){ // Se corre n veces * O(1) =>  O(n)
			coloresPosibles=nodosGrafo[i].getColoresPosibles(); //O(1)
			numColorAPintar=aleatorio.nextInt(coloresPosibles.size()); // O(1)
 			colorAPintar=coloresPosibles.get(numColorAPintar); // O(1)
			nodosGrafo[i].setColor(colorAPintar); // O(1)
		}
		
		int conflictosInicial=Ejercicio2.calcularCantidadConflictos(nodosGrafo);//O(n*n)
		//~ System.out.println("entramos");
		mejorarSiPosible2(nodosGrafo,conflictosInicial); //O()
		
		
	}
	
	public static int calcularCantidadConflictos(Nodo[] nodosGrafo, int i, int color){
		int cantConflictosOriginal = 0,cantConflictosColor=0;//O(1)
		for(Nodo sucesores: nodosGrafo[i].getSucesores()){ // se corre n veces como mucho, => O(n)* O(1) => O(n)
			if(sucesores.tieneColor() && sucesores.getColor() ==nodosGrafo[i].getColor()) cantConflictosOriginal++; //O(1)
			if(sucesores.tieneColor() && sucesores.getColor() == color) cantConflictosColor++; //O(1)
		}
		//~ if(cantConflictosColor < cantConflictosOriginal){
			//~ return true;
		//~ }else{
			//~ return false;
		//~ }
		return cantConflictosOriginal - cantConflictosColor;	// Si esto es positivo, quiere decir que el color "nuevo" tiene menos conflictos
																// Entonces, vamos a devolver esta diferencia, y vamos a cambiar el color.
		// La complejidad del algoritmo es O(n)
	}
	
	
	public static void mejorarSiPosible2(Nodo[] nodosGrafo,int conflictosInicial ){
		boolean huboMejora=false;
		int cantConflic=Integer.MAX_VALUE;
		
		//tomamos LOS vecinos con mayor cantidad de vecinos del mismo color
		
		Set<Nodo> nodosMayorCantidadConflictos= new HashSet<>();
		
		nodosMayorCantidadConflictos.add(nodosGrafo[0]);
		
		int cantidadVecinosMismoColor=0;
		int colorActual= nodosGrafo[0].getColor();
		for(Nodo sucesor: nodosGrafo[0].getSucesores()){ // Esto se hace n veces como mucho, que seria cuando el nodo esta conectado con todos los nodos
			if(sucesor.getColor()==colorActual) cantidadVecinosMismoColor++; // O(1)
		}
		
		int mayorCantidadVecinosConflictos=cantidadVecinosMismoColor;
	
		for(int i=1;i<nodosGrafo.length;i++){ // Esto se hace como mucho n veces * O(n) => O(n*n)
			cantidadVecinosMismoColor=0;
			colorActual= nodosGrafo[i].getColor();
			for(Nodo sucesor: nodosGrafo[i].getSucesores()){ //Esto se hace como mucho n veces (acotado como antes, como mucho esta conectado con todos) *O(1) => O(n)
				if(sucesor.tieneColor() && sucesor.getColor()==colorActual) cantidadVecinosMismoColor++;
			}
			
			if(cantidadVecinosMismoColor == mayorCantidadVecinosConflictos){ // O(1)
				nodosMayorCantidadConflictos.add(nodosGrafo[i]);// O(1)
			}else if(cantidadVecinosMismoColor > mayorCantidadVecinosConflictos){// O(1)
				mayorCantidadVecinosConflictos=cantidadVecinosMismoColor;// O(1)
				nodosMayorCantidadConflictos.clear();// O(1)
				nodosMayorCantidadConflictos.add(nodosGrafo[i]);// O(1)
			}	
		}
		
		//Hasta aca, la complejidad del algoritmo es de n*n!! COMPLEJIDAD!
		
		//una vez que  tenemos LOs nodos con mayor cantidad de conflictos, los vecinos son los posibles intercambios de un color en alguno de estos
		
		//buscamos una solucion mejor que la q tenemos, nos quedamos con la primera q encontramos(la otra heuristica1 tambien)
		
		//osea buscamos un coloreo con la menor cantidad de conflictos
		
		busqueda:
		for(Nodo nodoActual: nodosMayorCantidadConflictos){ //Esto se hace como mucho n veces, que seria si el grafo es completo, 
															//y todos los nodos contienen el mismo color, o la misma cantidad de conflictos 
															//* n* O(n*c) => O(n*n*c)
			for(int color : nodoActual.getColoresPosibles()){ // Se hace coloresPosibles veces * O(n) => n*c
				int conflictosDiferencia = calcularCantidadConflictos(nodosGrafo,nodoActual.getId(),color); // O(n) 
				if(conflictosDiferencia > 0){
					//Entonces, el color "nuevo" genera menos conflictos, entonces debo cambiarlo!!
					conflictosInicial = conflictosInicial - conflictosDiferencia; // Le saco la diferencia de conflictos, que tenia el anterior
																				  // color, con el nuevo!! Es la forma de actualizar la cantidad
																				  // de conflictos totales, sin tener que volver a calcularlo.
					huboMejora = true;
					nodoActual.setColor(color); // O(1)
				}
			}
			if(huboMejora){
				break;
			}
		}

		if(huboMejora){// O(1)
			mejorarSiPosible2(nodosGrafo,conflictosInicial );
		}
		/* Entonces el calculo de complejidad total del algoritmo es de m* (O(n*n*c + n*n)) 
		 * => O(m*n*n*c) */
		
		//~ System.out.println("salimos2");
		return;
		
	}
	
	
/////////////////////////////////////////////////////////////////////////////////////////	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		
		System.out.println("ejercicio4");
		
		FileInputStream entrada =null;  
		BufferedReader reader =null;
		FileWriter escribidor=null;
		//FileWriter escribidor2=null;
		
		try{
			escribidor= new FileWriter("Ej4Heu1.out");				
			//escribidor2= new FileWriter("Ej4Heu2.out");				
			//~ int numHeuristica = Integer.parseInt(args[0]);
		int tipoCaso = Integer.parseInt(args[0]);
		Nodo[] grafo;
		Nodo[] grafo2;
		Nodo[] grafo3;

		switch(tipoCaso){
			case 0:
				//~ long[] tiemposGeneral = new long[10];
				//~ long[] tiemposGeneralHeu2 = new long[10];
				for(int n = 10; n < 300; n = n + 30){
					System.out.println("Creo Grafo numero: ");
					System.out.println(n);
					System.out.println("\n");
					grafo = new Nodo[n];
					grafo2 = new Nodo[n];
					Random rnd = new Random(System.currentTimeMillis());
					int m = n + rnd.nextInt(n*(n-1)/3 - n); // desde n hasta (n*(n-1))/2 -1
					int c = 3 + rnd.nextInt(n - 4); // desde 3 hasta n -1
					System.out.println("Numero Aristas: ");
					System.out.println(m);
					System.out.println("\n");
					System.out.println("Numero Colores: ");
					System.out.println(c);
					System.out.println("\n");
					System.out.println("Empiezo a agregar colores");
					System.out.println("\n");
					for(int i = 0; i < n; i++){
						Nodo ver = new Nodo(i);
						Nodo ver2 = new Nodo(i);
						int cant = 1 + rnd.nextInt(c - 2); // desde 1 hasta c -1
						boolean[] colores = new boolean[c];
						for(int a = 0; a < c; a++){
							colores[a] = false;
						}
						for(int k = 0; k < cant; k++){
							int col = rnd.nextInt(c - 1); //desde 0 hasta c -1
							if(colores[col] == false){
								ver.addColor(col);
								ver2.addColor(col);
								colores[col] = true;
							}else{
								int b = 0;
								for(b = 0; b < c; b++){
									if(colores[b] == false){
										break;
									}
								}
								ver.addColor(b);
								ver2.addColor(b);
								colores[b] = true;
							}
							ver.fijarColoresOriginales();
							ver2.fijarColoresOriginales();
						}
						grafo[i] = ver;
						grafo2[i] = ver2;
					}
					System.out.println("Termino de agregar colores, empiezo a agregar aristas");
					System.out.println("\n");
					for(int j = 0; j < m; j++){
						Random rnd1 = new Random(System.nanoTime());
						int u = rnd1.nextInt(n - 1); //hasta n -1
						int v = rnd1.nextInt(n - 1); //hasta n -1
						//~ System.out.println(u);
						//~ System.out.println("\n");	
						//~ System.out.println(v);
						//~ System.out.println("\n");					
						while(u == v){
							v = rnd1.nextInt(n - 1); // hasta n -1
						}
						boolean esta = false;
						for(Nodo sucesor: grafo[u].getSucesores()){
							if(sucesor.getId() == v){
								rnd1= new Random();
								esta = true;
								break;
							}
						}
						if(esta){
							j--;
						}else{
							grafo[u].addSucesor(grafo[v]);
							grafo[v].addSucesor(grafo[u]);
							grafo2[u].addSucesor(grafo2[v]);
							grafo2[v].addSucesor(grafo2[u]);
						}
						//~ System.out.println("Arista numero: ");
						//~ System.out.println(j);
						//~ System.out.println("\n");
					}
					System.out.println("MuereGrafo");
										
							System.out.println("heuristica1");
							long[] tiempos= new long[200];
							long antes;
							long duracion;
							long prom = 0;
							for(int i = 0;i < 200;i++){
								System.gc();
								antes=System.nanoTime();
								heuristicaBusquedaLocal1(grafo);
								duracion=System.nanoTime()-antes;
								tiempos[i] = duracion;
								prom = prom + duracion;
							}
							prom = prom/200;
							long desvio = 0;
							long[] var= new long[200];
							for(int j = 0;j < 200;j++){
								var[j] = tiempos[j] - prom;
								var[j] = var[j] * var[j];
								desvio = desvio + var[j];
							}
							desvio = desvio/200;
							desvio = (long)Math.sqrt(desvio);
							long promedio = 0;
							int cant = 0;
							for(int k = 0;k < 200;k++){
								if(tiempos[k] < prom-desvio){
									tiempos[k] = 0;
									cant--;
								}else if(tiempos[k] > prom + desvio){
									tiempos[k] = 0;
									cant--;
								}
								promedio = promedio + tiempos[k];
								cant++;
							}
							escribidor.write("Heuristica1");
							escribidor.write("\n");
							escribidor.write("Promedio para nodos :");
							escribidor.write(Long.toString(n));
							escribidor.write(" es: ");
							escribidor.write(Long.toString(promedio));
							escribidor.write("\n");
							escribidor.write("Y la cantidad de conflictos es: ");
							escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo)));
							escribidor.write("\n");
							//~ 
							 //~ 
							 //~ //for(int i=0;i<n;i++){
								//~ //	escribidor.write(Integer.toString(grafo[i].getColor()));						
									//~ //escribidor.write("\n");
							//~ //}
							System.out.println("heuristica2");
							long[] tiempos1= new long[200];
							long antes1;
							long duracion1;
							long prom1 = 0;
							for(int i = 0;i < 200;i++){
								System.gc();
								antes1=System.nanoTime();
								heuristicaBusquedaLocal2(grafo2);
								duracion1=System.nanoTime()-antes1;
								tiempos1[i] = duracion1;
								prom1 = prom1 + duracion1;
							}
							prom1 = prom1/200;
							long desvio1 = 0;
							long[] var1= new long[200];
							for(int j = 0;j < 200;j++){
								var1[j] = tiempos1[j] - prom1;
								var1[j] = var1[j] * var1[j];
								desvio1 = desvio1 + var1[j];
							}
							desvio1 = desvio1/200;
							desvio1 = (long)Math.sqrt(desvio1);
							long promedio1 = 0;
							int cant1 = 0;
							for(int k = 0;k < 200;k++){
								if(tiempos1[k] < prom1-desvio1){
									tiempos1[k] = 0;
									cant1--;
								}else if(tiempos1[k] > prom1 + desvio1){
									tiempos1[k] = 0;
									cant1--;
								}
								promedio1 = promedio1 + tiempos1[k];
								cant1++;
							}
							
							escribidor.write("Heuristica 2");
							escribidor.write("\n");
							escribidor.write("Promedio para nodos :");
							escribidor.write(Long.toString(n));
							escribidor.write(" es: ");
							escribidor.write(Long.toString(promedio1/cant1));
							escribidor.write("\n");
							escribidor.write("Y la cantidad de conflictos es: ");
							escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo2)));
							escribidor.write("\n");

							
						}
					//}
						break;	 
							
			case 1:
				for(int n = 10; n < 300; n = n + 30){
					System.out.println("Creo Grafo numero: ");
					System.out.println(n);
					System.out.println("\n");
					grafo = new Nodo[n];
					grafo2 = new Nodo[n];
					Random rnd = new Random(System.currentTimeMillis());
					int m = 2*n;
					int c = n/2;
					System.out.println("Numero Aristas: ");
					System.out.println(m);
					System.out.println("\n");
					System.out.println("Numero Colores: ");
					System.out.println(c);
					System.out.println("\n");
					System.out.println("Empiezo a agregar colores");
					System.out.println("\n");
					for(int i = 0; i < n; i++){
						Nodo ver = new Nodo(i);
						Nodo ver2 = new Nodo(i);
						int cant = 1 + rnd.nextInt(c - 2); // desde 1 hasta c -1
						boolean[] colores = new boolean[c];
						for(int a = 0; a < c; a++){
							colores[a] = false;
						}
						for(int k = 0; k < cant; k++){
							int col = rnd.nextInt(c - 1); //desde 0 hasta c -1
							if(colores[col] == false){
								ver.addColor(col);
								ver2.addColor(col);
								colores[col] = true;
							}else{
								int b = 0;
								for(b = 0; b < c; b++){
									if(colores[b] == false){
										break;
									}
								}
								ver.addColor(b);
								ver2.addColor(b);
								colores[b] = true;
							}
							ver.fijarColoresOriginales();
							ver2.fijarColoresOriginales();
						}
						grafo[i] = ver;
						grafo2[i] = ver2;
					}
					System.out.println("Termino de agregar colores, empiezo a agregar aristas");
					System.out.println("\n");
					for(int j = 0; j < m; j++){
						Random rnd1 = new Random(System.nanoTime());
						int u = rnd1.nextInt(n - 1); //hasta n -1
						int v = rnd1.nextInt(n - 1); //hasta n -1
						while(u == v){
							v = rnd1.nextInt(n - 1); // hasta n -1
						}
						boolean esta = false;
						for(Nodo sucesor: grafo[u].getSucesores()){
							if(sucesor.getId() == v){
								rnd1= new Random();
								esta = true;
								break;
							}
						}
						if(esta){
							j--;
						}else{
							grafo[u].addSucesor(grafo[v]);
							grafo[v].addSucesor(grafo[u]);
							grafo2[u].addSucesor(grafo2[v]);
							grafo2[v].addSucesor(grafo2[u]);
						}
					}
					System.out.println("MuereGrafo");
										
							System.out.println("heuristica1");
							long[] tiempos= new long[200];
							long antes;
							long duracion;
							long prom = 0;
							for(int i = 0;i < 200;i++){
								System.gc();
								antes=System.nanoTime();
								heuristicaBusquedaLocal1(grafo);
								duracion=System.nanoTime()-antes;
								tiempos[i] = duracion;
								prom = prom + duracion;
							}
							prom = prom/200;
							long desvio = 0;
							long[] var= new long[200];
							for(int j = 0;j < 200;j++){
								var[j] = tiempos[j] - prom;
								var[j] = var[j] * var[j];
								desvio = desvio + var[j];
							}
							desvio = desvio/200;
							desvio = (long)Math.sqrt(desvio);
							long promedio = 0;
							int cant = 0;
							for(int k = 0;k < 200;k++){
								if(tiempos[k] < prom-desvio){
									tiempos[k] = 0;
									cant--;
								}else if(tiempos[k] > prom + desvio){
									tiempos[k] = 0;
									cant--;
								}
								promedio = promedio + tiempos[k];
								cant++;
							}
							//promedio = promedio/cant;
							escribidor.write("Heuristica1");
							escribidor.write("\n");
							escribidor.write("Promedio para nodos :");
							escribidor.write(Long.toString(n));
							escribidor.write(" es: ");
							escribidor.write(Long.toString(promedio/cant));
							escribidor.write("\n");
							escribidor.write("Y la cantidad de conflictos es: ");
							escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo)));
							escribidor.write("\n");

						
							System.out.println("heuristica2");
							long[] tiempos1= new long[200];
							long antes1;
							long duracion1;
							long prom1 = 0;
							for(int i = 0;i < 200;i++){
								System.gc();
								antes1=System.nanoTime();
								heuristicaBusquedaLocal2(grafo2);
								duracion1=System.nanoTime()-antes1;
								tiempos1[i] = duracion1;
								prom1 = prom1 + duracion1;
							}
							prom1 = prom1/200;
							long desvio1 = 0;
							long[] var1= new long[200];
							for(int j = 0;j < 200;j++){
								var1[j] = tiempos1[j] - prom1;
								var1[j] = var1[j] * var1[j];
								desvio1 = desvio1 + var1[j];
							}
							desvio1 = desvio1/200;
							desvio1 = (long)Math.sqrt(desvio1);
							long promedio1 = 0;
							int cant1 = 0;
							for(int k = 0;k < 200;k++){
								if(tiempos1[k] < prom1-desvio1){
									tiempos1[k] = 0;
									cant1--;
								}else if(tiempos1[k] > prom1 + desvio1){
									tiempos1[k] = 0;
									cant1--;
								}
								promedio1 = promedio1 + tiempos1[k];
								cant1++;
							}
							//promedio1 = promedio1/cant1;
							escribidor.write("Heuristica2");
							escribidor.write("\n");
							escribidor.write("Promedio para nodos :");
							escribidor.write(Long.toString(n));
							escribidor.write(" es: ");
							escribidor.write(Long.toString(promedio1/cant1));
							escribidor.write("\n");
							escribidor.write("Y la cantidad de conflictos es: ");
							escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo2)));
							escribidor.write("\n");

							
						}
						break;
							
			case 2:
				int n = 200;
				int c = 100;
				for(int m = 200; m < 19900; m = m + 1970){
					System.out.println("Creo Grafo numero: ");
					System.out.println(n);
					System.out.println("\n");
					grafo = new Nodo[n];
					grafo2 = new Nodo[n];
					Random rnd = new Random(System.currentTimeMillis());
					System.out.println("Numero Aristas: ");
					System.out.println(m);
					System.out.println("\n");
					System.out.println("Numero Colores: ");
					System.out.println(c);
					System.out.println("\n");
					System.out.println("Empiezo a agregar colores");
					System.out.println("\n");
					for(int i = 0; i < n; i++){
						Nodo ver = new Nodo(i);
						Nodo ver2 = new Nodo(i);
						int cant = 1 + rnd.nextInt(c - 2); // desde 1 hasta c -1
						boolean[] colores = new boolean[c];
						for(int a = 0; a < c; a++){
							colores[a] = false;
						}
						for(int k = 0; k < cant; k++){
							int col = rnd.nextInt(c - 1); //desde 0 hasta c -1
							if(colores[col] == false){
								ver.addColor(col);
								ver2.addColor(col);
								colores[col] = true;
							}else{
								int b = 0;
								for(b = 0; b < c; b++){
									if(colores[b] == false){
										break;
									}
								}
								ver.addColor(b);
								ver2.addColor(b);
								colores[b] = true;
							}
							ver.fijarColoresOriginales();
							ver2.fijarColoresOriginales();
						}
						grafo[i] = ver;
						grafo2[i] = ver2;
					}
					System.out.println("Termino de agregar colores, empiezo a agregar aristas");
					System.out.println("\n");
					for(int j = 0; j < m; j++){
						Random rnd1 = new Random(System.nanoTime());
						int u = rnd1.nextInt(n - 1); //hasta n -1
						int v = rnd1.nextInt(n - 1); //hasta n -1
						while(u == v){
							v = rnd1.nextInt(n - 1); // hasta n -1
						}
						boolean esta = false;
						for(Nodo sucesor: grafo[u].getSucesores()){
							if(sucesor.getId() == v){
								rnd1= new Random();
								esta = true;
								break;
							}
						}
						if(esta){
							j--;
						}else{
							grafo[u].addSucesor(grafo[v]);
							grafo[v].addSucesor(grafo[u]);
							grafo2[u].addSucesor(grafo2[v]);
							grafo2[v].addSucesor(grafo2[u]);
						}
					}
					System.out.println("MuereGrafo");
							System.out.println("heuristica1");
							long[] tiempos= new long[200];
							long antes;
							long duracion;
							long prom = 0;
							for(int i = 0;i < 200;i++){
								System.gc();
								antes=System.nanoTime();
								heuristicaBusquedaLocal1(grafo);
								duracion=System.nanoTime()-antes;
								tiempos[i] = duracion;
								prom = prom + duracion;
							}
							prom = prom/200;
							long desvio = 0;
							long[] var= new long[200];
							for(int j = 0;j < 200;j++){
								var[j] = tiempos[j] - prom;
								var[j] = var[j] * var[j];
								desvio = desvio + var[j];
							}
							desvio = desvio/200;
							desvio = (long)Math.sqrt(desvio);
							long promedio = 0;
							int cant = 0;
							for(int k = 0;k < 200;k++){
								if(tiempos[k] < prom-desvio){
									tiempos[k] = 0;
									cant--;
								}else if(tiempos[k] > prom + desvio){
									tiempos[k] = 0;
									cant--;
								}
								promedio = promedio + tiempos[k];
								cant++;
							}
							//promedio = promedio/cant;
							escribidor.write("Heuristica1");
							escribidor.write("\n");
							escribidor.write("Promedio para nodos :");
							escribidor.write(Long.toString(n));
							escribidor.write(" es: ");
							escribidor.write(Long.toString(promedio/cant));
							escribidor.write("\n");
							escribidor.write("Y la cantidad de conflictos es: ");
							escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo)));
							escribidor.write("\n");

							
							
							System.out.println("heuristica2");
							long[] tiempos1= new long[200];
							long antes1;
							long duracion1;
							long prom1 = 0;
							for(int i = 0;i < 200;i++){
								System.gc();
								antes1=System.nanoTime();
								heuristicaBusquedaLocal2(grafo2);
								duracion1=System.nanoTime()-antes1;
								tiempos1[i] = duracion1;
								prom1 = prom1 + duracion1;
							}
							prom1 = prom1/200;
							long desvio1 = 0;
							long[] var1= new long[200];
							for(int j = 0;j < 200;j++){
								var1[j] = tiempos1[j] - prom1;
								var1[j] = var1[j] * var1[j];
								desvio1 = desvio1 + var1[j];
							}
							desvio1 = desvio1/200;
							desvio1 = (long)Math.sqrt(desvio1);
							long promedio1 = 0;
							int cant1 = 0;
							for(int k = 0;k < 200;k++){
								if(tiempos1[k] < prom1-desvio1){
									tiempos1[k] = 0;
									cant1--;
								}else if(tiempos1[k] > prom1 + desvio1){
									tiempos1[k] = 0;
									cant1--;
								}
								promedio1 = promedio1 + tiempos1[k];
								cant1++;
							}
							//promedio1 = promedio1/cant1;
							escribidor.write("Heuristica2");
							escribidor.write("\n");
							escribidor.write("Promedio para nodos :");
							escribidor.write(Long.toString(n));
							escribidor.write(" es: ");
							escribidor.write(Long.toString(promedio1/cant1));
							escribidor.write("\n");
							escribidor.write("Y la cantidad de conflictos es: ");
							escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo2)));
							escribidor.write("\n");

							
						
					
				}
				break;
				
				case 3:
				n = 200;
				int m = 5000;
				for(c = 80; c < 180; c = c + 10){
					System.out.println("Creo Grafo numero: ");
					System.out.println(n);
					System.out.println("\n");
					grafo = new Nodo[n];
					grafo2 = new Nodo[n];
					Random rnd = new Random(System.currentTimeMillis());
					System.out.println("Numero Aristas: ");
					System.out.println(m);
					System.out.println("\n");
					System.out.println("Numero Colores: ");
					System.out.println(c);
					System.out.println("\n");
					System.out.println("Empiezo a agregar colores");
					System.out.println("\n");
					for(int i = 0; i < n; i++){
						Nodo ver = new Nodo(i);
						Nodo ver2 = new Nodo(i);
						int cant = 1 + rnd.nextInt(c - 2); // desde 1 hasta c -1
						boolean[] colores = new boolean[c];
						for(int a = 0; a < c; a++){
							colores[a] = false;
						}
						for(int k = 0; k < cant; k++){
							int col = rnd.nextInt(c - 1); //desde 0 hasta c -1
							if(colores[col] == false){
								ver.addColor(col);
								ver2.addColor(col);
								colores[col] = true;
							}else{
								int b = 0;
								for(b = 0; b < c; b++){
									if(colores[b] == false){
										break;
									}
								}
								ver.addColor(b);
								ver2.addColor(b);
								colores[b] = true;
							}
							ver.fijarColoresOriginales();
							ver2.fijarColoresOriginales();
						}
						grafo[i] = ver;
						grafo2[i] = ver2;
					}
					System.out.println("Termino de agregar colores, empiezo a agregar aristas");
					System.out.println("\n");
					for(int j = 0; j < m; j++){
						Random rnd1 = new Random(System.nanoTime());
						int u = rnd1.nextInt(n - 1); //hasta n -1
						int v = rnd1.nextInt(n - 1); //hasta n -1
						while(u == v){
							v = rnd1.nextInt(n - 1); // hasta n -1
						}
						boolean esta = false;
						for(Nodo sucesor: grafo[u].getSucesores()){
							if(sucesor.getId() == v){
								rnd1= new Random();
								esta = true;
								break;
							}
						}
						if(esta){
							j--;
						}else{
							grafo[u].addSucesor(grafo[v]);
							grafo[v].addSucesor(grafo[u]);
							grafo2[v].addSucesor(grafo2[u]);
							grafo2[u].addSucesor(grafo2[v]);
						}
					}
					System.out.println("MuereGrafo");

							System.out.println("heuristica1");
							long[] tiempos= new long[200];
							long antes;
							long duracion;
							long prom = 0;
							for(int i = 0;i < 200;i++){
								System.gc();
								antes=System.nanoTime();
								heuristicaBusquedaLocal1(grafo);
								duracion=System.nanoTime()-antes;
								tiempos[i] = duracion;
								prom = prom + duracion;
							}
							prom = prom/200;
							long desvio = 0;
							long[] var= new long[200];
							for(int j = 0;j < 200;j++){
								var[j] = tiempos[j] - prom;
								var[j] = var[j] * var[j];
								desvio = desvio + var[j];
							}
							desvio = desvio/200;
							desvio = (long)Math.sqrt(desvio);
							long promedio = 0;
							int cant = 0;
							for(int k = 0;k < 200;k++){
								if(tiempos[k] < prom-desvio){
									tiempos[k] = 0;
									cant--;
								}else if(tiempos[k] > prom + desvio){
									tiempos[k] = 0;
									cant--;
								}
								promedio = promedio + tiempos[k];
								cant++;
							}
							//promedio = promedio/cant;
							escribidor.write("Heuristica1");
							escribidor.write("\n");
							escribidor.write("Promedio para nodos :");
							escribidor.write(Long.toString(n));
							escribidor.write(" es: ");
							escribidor.write(Long.toString(promedio/cant));
							escribidor.write("\n");
							escribidor.write("Y la cantidad de conflictos es: ");
							escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo)));
							escribidor.write("\n");


							System.out.println("heuristica2");
							long[] tiempos1= new long[200];
							long antes1;
							long duracion1;
							long prom1 = 0;
							for(int i = 0;i < 200;i++){
								System.gc();
								antes1=System.nanoTime();
								heuristicaBusquedaLocal2(grafo2);
								duracion1=System.nanoTime()-antes1;
								tiempos1[i] = duracion1;
								prom1 = prom1 + duracion1;
							}
							prom1 = prom1/200;
							long desvio1 = 0;
							long[] var1= new long[200];
							for(int j = 0;j < 200;j++){
								var1[j] = tiempos1[j] - prom1;
								var1[j] = var1[j] * var1[j];
								desvio1 = desvio1 + var1[j];
							}
							desvio1 = desvio1/200;
							desvio1 = (long)Math.sqrt(desvio1);
							long promedio1 = 0;
							int cant1 = 0;
							for(int k = 0;k < 200;k++){
								if(tiempos1[k] < prom1-desvio1){
									tiempos1[k] = 0;
									cant1--;
								}else if(tiempos1[k] > prom1 + desvio1){
									tiempos1[k] = 0;
									cant1--;
								}
								promedio1 = promedio1 + tiempos1[k];
								cant1++;
							}
							//promedio1 = promedio1/cant1;
							escribidor.write("Heuristica2");
							escribidor.write("\n");
							escribidor.write("Promedio para nodos :");
							escribidor.write(Long.toString(n));
							escribidor.write(" es: ");
							escribidor.write(Long.toString(promedio1/cant1));
							escribidor.write("\n");
							escribidor.write("Y la cantidad de conflictos es: ");
							escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo2)));
							escribidor.write("\n");


						

					
				}
				break;
				
				case 4:
					//Este es el algoritmo para comparar con el ejercicio3, vamos a usar grafos completos como familia, para probar cual 
					//resuelve mas rapido y de forma mas eficiente!!
					System.out.println("Tiempos 3 vs 4");
					for(n = 80; n < 180; n = n + 10){
						System.out.println("Creo Grafo numero: ");
						System.out.println(n);
						System.out.println("\n");
						grafo = new Nodo[n];
						grafo2 = new Nodo[n];
						grafo3 = new Nodo[n];
						Random rnd2 = new Random(System.currentTimeMillis());
						c = 3 + rnd2.nextInt(n - 4); // desde 3 hasta n -1
						Random rnd = new Random(System.currentTimeMillis());
						System.out.println("Numero Aristas: ");
						m = n*(n-1)/2;
						System.out.println(m);
						System.out.println("\n");
						System.out.println("Numero Colores: ");
						System.out.println(c);
						System.out.println("\n");
						System.out.println("Empiezo a agregar colores");
						System.out.println("\n");
						for(int i = 0; i < n; i++){
							Nodo ver = new Nodo(i);
							Nodo ver2 = new Nodo(i);
							Nodo ver3 = new Nodo(i);
							int cant = 1 + rnd.nextInt(c - 2); // desde 1 hasta c -1
							boolean[] colores = new boolean[c];
							for(int a = 0; a < c; a++){
								colores[a] = false;
							}
							for(int k = 0; k < cant; k++){
								int col = rnd.nextInt(c - 1); //desde 0 hasta c -1
								if(colores[col] == false){
									ver.addColor(col);
									ver2.addColor(col);
									ver3.addColor(col);
									colores[col] = true;
								}else{
									int b = 0;
									for(b = 0; b < c; b++){
										if(colores[b] == false){
											break;
										}
									}
									ver.addColor(b);
									ver2.addColor(b);
									ver3.addColor(b);
									colores[b] = true;
								}
								ver.fijarColoresOriginales();
								ver2.fijarColoresOriginales();
								ver3.fijarColoresOriginales();
							}
							grafo[i] = ver;
							grafo2[i] = ver2;
							grafo3[i] = ver3;
						}
						System.out.println("Termino de agregar colores, empiezo a agregar aristas");
						System.out.println("\n");
						for(int j = 0; j < n; j++){
							for(int ari = j + 1;ari < n; ari++){
								grafo[j].addSucesor(grafo[ari]);
								grafo[ari].addSucesor(grafo[j]);
								grafo2[j].addSucesor(grafo2[ari]);
								grafo2[ari].addSucesor(grafo2[j]);
								grafo3[j].addSucesor(grafo3[ari]);
								grafo3[ari].addSucesor(grafo3[j]);
							}
						}
						System.out.println("Probando");
						System.out.println("\n");
						System.out.println("MuereGrafo");

								System.out.println("heuristica1");
								long[] tiempos= new long[200];
								long antes;
								long duracion;
								long prom = 0;
								for(int i = 0;i < 200;i++){
									System.gc();
									antes=System.nanoTime();
									heuristicaBusquedaLocal1(grafo);
									duracion=System.nanoTime()-antes;
									tiempos[i] = duracion;
									prom = prom + duracion;
								}
								prom = prom/200;
								long desvio = 0;
								long[] var= new long[200];
								for(int j = 0;j < 200;j++){
									var[j] = tiempos[j] - prom;
									var[j] = var[j] * var[j];
									desvio = desvio + var[j];
								}
								desvio = desvio/200;
								desvio = (long)Math.sqrt(desvio);
								long promedio = 0;
								int cant = 0;
								for(int k = 0;k < 200;k++){
									if(tiempos[k] < prom-desvio){
										tiempos[k] = 0;
										cant--;
									}else if(tiempos[k] > prom + desvio){
										tiempos[k] = 0;
										cant--;
									}
									promedio = promedio + tiempos[k];
									cant++;
								}
								promedio = promedio/cant;
								escribidor.write("Heuristica1");
								escribidor.write("\n");
								escribidor.write("Promedio para nodos :");
								escribidor.write(Long.toString(n));
								escribidor.write(" es: ");
								escribidor.write(Long.toString(promedio/cant));
								escribidor.write("\n");
								escribidor.write("Y la cantidad de conflictos es: ");
								escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo)));
								escribidor.write("\n");


								System.out.println("heuristica2");
								long[] tiempos1= new long[200];
								long antes1;
								long duracion1;
								long prom1 = 0;
								for(int i = 0;i < 200;i++){
									System.gc();
									antes1=System.nanoTime();
									heuristicaBusquedaLocal2(grafo2);
									duracion1=System.nanoTime()-antes1;
									tiempos1[i] = duracion1;
									prom1 = prom1 + duracion1;
								}
								prom1 = prom1/200;
								long desvio1 = 0;
								long[] var1= new long[200];
								for(int j = 0;j < 200;j++){
									var1[j] = tiempos1[j] - prom1;
									var1[j] = var1[j] * var1[j];
									desvio1 = desvio1 + var1[j];
								}
								desvio1 = desvio1/200;
								desvio1 = (long)Math.sqrt(desvio1);
								long promedio1 = 0;
								int cant1 = 0;
								for(int k = 0;k < 200;k++){
									if(tiempos1[k] < prom1-desvio1){
										tiempos1[k] = 0;
										cant1--;
									}else if(tiempos1[k] > prom1 + desvio1){
										tiempos1[k] = 0;
										cant1--;
									}
									promedio1 = promedio1 + tiempos1[k];
									cant1++;
								}
								promedio1 = promedio1/cant1;
								escribidor.write("Heuristica2");
								escribidor.write("\n");
								escribidor.write("Promedio para nodos :");
								escribidor.write(Long.toString(n));
								escribidor.write(" es: ");
								escribidor.write(Long.toString(promedio1/cant1));
								escribidor.write("\n");
								escribidor.write("Y la cantidad de conflictos es: ");
								escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo2)));
								escribidor.write("\n");
								
								
								System.out.println("Heuristica Golosa");
								long[] tiempos2= new long[200];
								long antes2;
								long duracion2;
								long prom2 = 0;
								for(int i = 0;i < 200;i++){
									System.gc();
									antes2=System.nanoTime();
									Ejercicio3.heuristicaGolosa(grafo3);
									//heuristicaBusquedaLocal2(grafo2);
									duracion2=System.nanoTime()-antes2;
									tiempos2[i] = duracion2;
									prom2 = prom2 + duracion2;
								}
								prom2 = prom2/200;
								long desvio2 = 0;
								long[] var2= new long[200];
								for(int j = 0;j < 200;j++){
									var2[j] = tiempos2[j] - prom2;
									var2[j] = var2[j] * var2[j];
									desvio2 = desvio2 + var2[j];
								}
								desvio2 = desvio2/200;
								desvio2 = (long)Math.sqrt(desvio2);
								long promedio2 = 0;
								int cant2 = 0;
								for(int k = 0;k < 200;k++){
									if(tiempos2[k] < prom2-desvio2){
										tiempos2[k] = 0;
										cant2--;
									}else if(tiempos2[k] > prom2 + desvio2){
										tiempos2[k] = 0;
										cant2--;
									}
									promedio2 = promedio2 + tiempos2[k];
									cant2++;
								}
								promedio2 = promedio2/cant2;
								escribidor.write("Heuristica Golosa!");
								escribidor.write("\n");
								escribidor.write("Promedio para nodos :");
								escribidor.write(Long.toString(n));
								escribidor.write(" es: ");
								escribidor.write(Long.toString(promedio2/cant2));
								escribidor.write("\n");
								escribidor.write("Y la cantidad de conflictos es: ");
								escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo3)));
								escribidor.write("\n");

						

					
						}
					
				
			}
			//~ switch(numHeuristica){
				//~ 
				//~ case 0:
					//~ System.out.println("heuristica1");
					//~ heuristicaBusquedaLocal1(nodosGrafo);
					 //~ 
					 //~ for(int i=0;i<n;i++){
							//~ escribidor.write(Integer.toString(nodosGrafo[i].getColor()));						
							//~ escribidor.write("\n");
					//~ }
					 //~ break;
				//~ 
				//~ case 1:
					//~ System.out.println("heuristica2");
//~ 
					//~ heuristicaBusquedaLocal2(nodosGrafo);
					 //~ 
					 //~ for(int i=0;i<n;i++){
							//~ escribidor.write(Integer.toString(nodosGrafo[i].getColor()));						
							//~ escribidor.write("\n");
					//~ }
					 //~ break;
					 //~ 
				//~ default:
					//~ 
					//~ System.err.println("error en el parametro utilizado");
//~ 
			//~ 
			//~ }
				
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
				try {
					System.out.println("salida\n");
					//reader.close();
					//entrada.close();
					escribidor.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}
