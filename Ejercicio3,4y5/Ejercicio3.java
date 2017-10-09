//package algo3tp3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

public class Ejercicio3 {
	
	
	public static void heuristicaGolosa(Nodo[] nodosGrafo){
		
		for(int i=0;i<nodosGrafo.length;i++){
			List<Integer> coloresPosibles= nodosGrafo[i].getColoresPosibles();
			int cantidadMinimaConflictos=Integer.MAX_VALUE;
			int cantidadMinimaConflictosPosibles=Integer.MAX_VALUE;
			int cantidadVecinosMismoColor;
			int colorResultanteParai=coloresPosibles.get(0);
			for(int color: coloresPosibles){
				cantidadVecinosMismoColor=0;
				for(Nodo sucesor: nodosGrafo[i].getSucesores()){
					if(sucesor.tieneColor() && sucesor.getColor()==color) cantidadVecinosMismoColor++;
				}
				
				if(cantidadVecinosMismoColor<cantidadMinimaConflictos){
					cantidadMinimaConflictos=cantidadVecinosMismoColor;
					colorResultanteParai=color;
					cantidadMinimaConflictosPosibles=0;// se restaura este valor
					for(Nodo sucesor: nodosGrafo[i].getSucesores() ){
						if( (! sucesor.tieneColor()) && sucesor.getColoresPosibles().contains(new Integer(color))) cantidadMinimaConflictosPosibles++;
					}
					
				}else if(cantidadVecinosMismoColor==cantidadMinimaConflictos){// en caso de empate nos fijamos en los conflictos posibles para decidir
					int cantidadConflictosPosiblesColor=0;
					for(Nodo sucesor: nodosGrafo[i].getSucesores() ){
						if( (! sucesor.tieneColor()) && sucesor.getColoresPosibles().contains(new Integer(color))) cantidadConflictosPosiblesColor++;
					}
					if(cantidadConflictosPosiblesColor<cantidadMinimaConflictosPosibles){
						cantidadMinimaConflictosPosibles=cantidadConflictosPosiblesColor;
						colorResultanteParai=color;
					}
				}
			}
			nodosGrafo[i].setColor(colorResultanteParai);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("ejercicio3");
		
		FileWriter escribidor=null;
		
		try{
			escribidor= new FileWriter("Ej3Salidas.out");				
			int tipoCaso = Integer.parseInt(args[0]);
			Nodo[] grafo;
			switch(tipoCaso){
				case 0:
					for(int n = 10; n < 300; n = n + 30){
					System.out.println("Creo Grafo numero: ");
					System.out.println(n);
					System.out.println("\n");
					grafo = new Nodo[n];
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
						int cant = 1 + rnd.nextInt(c - 2); // desde 1 hasta c -1
						boolean[] colores = new boolean[c];
						for(int a = 0; a < c; a++){
							colores[a] = false;
						}
						for(int k = 0; k < cant; k++){
							int col = rnd.nextInt(c - 1); //desde 0 hasta c -1
							if(colores[col] == false){
								ver.addColor(col);
								colores[col] = true;
							}else{
								int b = 0;
								for(b = 0; b < c; b++){
									if(colores[b] == false){
										break;
									}
								}
								ver.addColor(b);
								colores[b] = true;
							}
							ver.fijarColoresOriginales();
						}
						grafo[i] = ver;
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
							heuristicaGolosa(grafo);
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
						System.out.println(cant);
						System.out.println("\n");
						System.out.println(promedio);
						System.out.println("\n");
						promedio = promedio/cant;
						System.out.println(promedio);
						System.out.println("\n");
						System.out.println("Termine");
						escribidor.write(Long.toString(promedio));
						escribidor.write("\n");
						escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo)));
						escribidor.write("\n");
						}
						break;		 
								
				case 1:
					for(int n = 10; n < 300; n = n + 30){
						System.out.println("Creo Grafo numero: ");
						System.out.println(n);
						System.out.println("\n");
						grafo = new Nodo[n];
						Random rnd = new Random(System.currentTimeMillis());
						int m = 2*n;
						int c = n;
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
							int cant = 10;
							boolean[] colores = new boolean[c];
							for(int a = 0; a < c; a++){
								colores[a] = false;
							}
							for(int k = 0; k < cant; k++){
								int col = rnd.nextInt(c - 1); //desde 0 hasta c -1
								if(colores[col] == false){
									ver.addColor(col);
									colores[col] = true;
								}else{
									int b = 0;
									for(b = 0; b < c; b++){
										if(colores[b] == false){
											break;
										}
									}
									ver.addColor(b);
									colores[b] = true;
								}
								ver.fijarColoresOriginales();
							}
							grafo[i] = ver;
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
							}
						}
						System.out.println("MuereGrafo");
						
						
						System.out.println("heuristica");
						long[] tiempos= new long[200];
						long antes;
						long duracion;
						long prom = 0;
						for(int i = 0;i < 200;i++){
							System.gc();
							antes=System.nanoTime();
							heuristicaGolosa(grafo);
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
						System.out.println(cant);
						System.out.println("\n");
						System.out.println(promedio);
						System.out.println("\n");
						promedio = promedio/cant;
						System.out.println(promedio);
						System.out.println("\n");
						System.out.println("Termine");
						escribidor.write(Long.toString(promedio));
						escribidor.write("\n");
						escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo)));
						escribidor.write("\n");
					}
					break;
								
				case 2:
				int	n = 200;
				int m = 5000;
				for(int c = 80; c < 180; c = c + 10){
					System.out.println("Creo Grafo numero: ");
					System.out.println(n);
					System.out.println("\n");
					grafo = new Nodo[n];
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
						int cant = 1 + rnd.nextInt(c - 2); // desde 1 hasta c -1
						boolean[] colores = new boolean[c];
						for(int a = 0; a < c; a++){
							colores[a] = false;
						}
						for(int k = 0; k < cant; k++){
							int col = rnd.nextInt(c - 1); //desde 0 hasta c -1
							if(colores[col] == false){
								ver.addColor(col);
								colores[col] = true;
							}else{
								int b = 0;
								for(b = 0; b < c; b++){
									if(colores[b] == false){
										break;
									}
								}
								ver.addColor(b);
								colores[b] = true;
							}
							ver.fijarColoresOriginales();
						}
						grafo[i] = ver;
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
						}
					}
					System.out.println("MuereGrafo");
											
						System.out.println("heuristica");
						long[] tiempos= new long[200];
						long antes;
						long duracion;
						long prom = 0;
						for(int i = 0;i < 200;i++){
							System.gc();
							antes=System.nanoTime();
							heuristicaGolosa(grafo);
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
						System.out.println(cant);
						System.out.println("\n");
						System.out.println(promedio);
						System.out.println("\n");
						promedio = promedio/cant;
						System.out.println(promedio);
						System.out.println("\n");
						System.out.println("Termine");
						escribidor.write(Long.toString(promedio));
						escribidor.write("\n");
						escribidor.write("Y la cantidad de conflictos es: ");
						escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo)));
						escribidor.write("\n");
					}
					break;
					
				case 3:
					for(n = 10; n < 300; n = n + 30){   // N es la cantidad de nodos
						System.out.println("Creo Grafo numero: ");
						System.out.println(n);
						System.out.println("\n");
						grafo = new Nodo[n];
						Random rnd = new Random(System.currentTimeMillis());
						//int m = n + rnd.nextInt(n*(n-1)/3 - n); // desde n hasta (n*(n-1))/2 -1
						int c = 10;
						//~ System.out.println("Numero Aristas: ");
						//~ System.out.println(m);
						//~ System.out.println("\n");
						System.out.println("Numero Colores: ");
						System.out.println(c);
						System.out.println("\n");
						System.out.println("Empiezo a agregar colores");
						System.out.println("\n");
						//Nodo raices = new Nodo[n]; // Aca se van a guardar las raices temporales..
						//Nodo raices2 = new Nodo[n]; // Aca se van a guardar las raices temporales..
						int principioRaices = 0;
						//int cantRaices= 0;
						for(int i = 0; i < n; i++){
							System.out.println("Entro al for");
							System.out.println("\n");
							System.out.println("i es: ");
							System.out.println(i);
							System.out.println("\n");
							if(i != 0){
								for(int j = 0; j < 8; j++){
									if(i<n){
										System.out.println("Creo vecino numero: ");
										System.out.println(j+1);
										System.out.println("\n");
										Nodo ver = new Nodo(i);
										int cant = 1 + rnd.nextInt(5);
										boolean[] colores = new boolean[c];
										for(int a = 0; a < c; a++){
											colores[a] = false;
										}
										for(int k = 0; k < cant; k++){
											int col = rnd.nextInt(c - 1); //desde 0 hasta c -1
											if(colores[col] == false){
												ver.addColor(col);
												colores[col] = true;
											}else{
												int b = 0;
												for(b = 0; b < c; b++){
													if(colores[b] == false){
														break;
													}
												}
												ver.addColor(b);
												colores[b] = true;
											}
											ver.fijarColoresOriginales();
										}
										grafo[i] = ver;
										grafo[i].addSucesor(grafo[principioRaices]);
										grafo[principioRaices].addSucesor(grafo[i]);
									}
									i++;
								}
								i--;
								principioRaices++;
							}else{
								System.out.println("Creo la raiz");
								System.out.println("\n");
								//Es la primera vez que entro
								Nodo ver = new Nodo(i);
								int cant = 1 + rnd.nextInt(c - 2); // desde 1 hasta c -1
								boolean[] colores = new boolean[c];
								for(int a = 0; a < c; a++){
									colores[a] = false;
								}
								for(int k = 0; k < cant; k++){
									int col = rnd.nextInt(c - 1); //desde 0 hasta c -1
									if(colores[col] == false){
										ver.addColor(col);
										colores[col] = true;
									}else{
										int b = 0;
										for(b = 0; b < c; b++){
											if(colores[b] == false){
												break;
											}
										}
										ver.addColor(b);
										colores[b] = true;
									}
									ver.fijarColoresOriginales();
								}
								grafo[i] = ver;
							}
							System.out.println("i es: ");
							System.out.println(i);
							System.out.println("\n");
						}
						System.out.println("MuereGrafo");
						
						heuristicaGolosa(grafo);
						escribidor.write("Y la cantidad de conflictos es: ");
						escribidor.write(Long.toString(Ejercicio2.calcularCantidadConflictos(grafo)));
						escribidor.write("\n");			
					}
					break;
			}		
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
