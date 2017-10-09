//package algo3tp3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
//import java.util.Queue;
import java.util.Stack;


public class Ejercicio1 {
	
	private static boolean noSepuedePintar=false;

	
	public static void cambiarSucesores(NodoP aCamb){
		aCamb.setValorVerdad(1);
		for(NodoP sucesor: aCamb.getSucesores()){
			if(sucesor.getValorVerdad()==0) {
				noSepuedePintar=true;
				System.out.println("no se puede pintar");
				break;
			}
			if(sucesor.getValorVerdad()==2){
				cambiarSucesores(sucesor);
			}
		}
	}

	public static boolean noSePuedePintar(Nodo[] nodosGrafo ){
		// creamos el grafo de prposiciones basado en el grafo original

		NodoP[] grafoProposiciones = new NodoP[4* nodosGrafo.length];
		
		for(int i=0;i<grafoProposiciones.length;i++){
			grafoProposiciones[i]= new NodoP(i);
			int nNodoAsociado= i/4;
			grafoProposiciones[i].setnNodoAsociado(nNodoAsociado);
			
			List<Integer> dosColores = nodosGrafo[nNodoAsociado].getColoresPosibles();
			
			if(dosColores.size()==2){
					//System.out.println("jj");
				if(i%4==0){
					grafoProposiciones[i].setColorAsociado(dosColores.get(0));
					grafoProposiciones[i].setEsNegacion(false);
				}else if(i%4==1){
					grafoProposiciones[i].setColorAsociado(dosColores.get(1));
					grafoProposiciones[i].setEsNegacion(false);
				}else if(i%4==2){
					grafoProposiciones[i].setColorAsociado(dosColores.get(0));
					grafoProposiciones[i].setEsNegacion(true);
				}else{
					grafoProposiciones[i].setColorAsociado(dosColores.get(1));
					grafoProposiciones[i].setEsNegacion(true);
				}
				
			}else{
				
				if(i%4==0){
					grafoProposiciones[i].setColorAsociado(dosColores.get(0));
					grafoProposiciones[i].setEsNegacion(false);
					grafoProposiciones[i].setValorVerdad(1);
				}else if(i%4==1){
					grafoProposiciones[i].setVacio();
				}else if(i%4==2){
					grafoProposiciones[i].setVacio();
				}else{
					grafoProposiciones[i].setVacio();
				}
			}
		}

		for(int i=0;i<grafoProposiciones.length;i++){
			if(i%4==0){
				if( !(grafoProposiciones[i].isVacio()) && !( grafoProposiciones[i+3].isVacio())) grafoProposiciones[i].addSucesor(grafoProposiciones[i+3]);
			}else if(i%4==1){
				if( !(grafoProposiciones[i].isVacio()) && !( grafoProposiciones[i+1].isVacio())) grafoProposiciones[i].addSucesor(grafoProposiciones[i+1]);
			}else if(i%4==2){
				if( !(grafoProposiciones[i].isVacio()) && !( grafoProposiciones[i-1].isVacio())) grafoProposiciones[i].addSucesor(grafoProposiciones[i-1]);
			}else{
				if( !(grafoProposiciones[i].isVacio()) && !( grafoProposiciones[i-3].isVacio())) grafoProposiciones[i].addSucesor(grafoProposiciones[i-3]);
			}
		}
		
		
		//System.out.println("10 Es negacion "+ grafoProposiciones[10].isEsNegacion() + " "+ grafoProposiciones[10].getColorAsociado());
		//continuamos agregando los sucesores basados en el grafo original

		int numNodo;
		int colorNodo;
		List<Nodo> sucesoresOriginal;
		for(int i=0;i<grafoProposiciones.length;i++){
			if(i%4==0 || i%4==1){
				numNodo=grafoProposiciones[i].getnNodoAsociado();
				colorNodo=grafoProposiciones[i].getColorAsociado();
				sucesoresOriginal=nodosGrafo[numNodo].getSucesores();
				for(Nodo sucesorOriginal:sucesoresOriginal){
					if(sucesorOriginal.getColoresPosibles().size()==2){
						if(sucesorOriginal.getColoresPosibles().get(0)==colorNodo){
							if( !(grafoProposiciones[i].isVacio()) && !( grafoProposiciones[4*sucesorOriginal.getId()+2].isVacio())) grafoProposiciones[i].addSucesor(grafoProposiciones[4*sucesorOriginal.getId()+2]);
						}
						if(sucesorOriginal.getColoresPosibles().get(1)==colorNodo){
							if( !(grafoProposiciones[i].isVacio()) && !( grafoProposiciones[4*sucesorOriginal.getId()+3].isVacio())) grafoProposiciones[i].addSucesor(grafoProposiciones[4*sucesorOriginal.getId()+3]);
						}
					}else{
						if(sucesorOriginal.getColoresPosibles().get(0)==colorNodo){
							if( !(grafoProposiciones[i].isVacio()) && !( grafoProposiciones[4*sucesorOriginal.getId()+2].isVacio())) grafoProposiciones[i].addSucesor(grafoProposiciones[4*sucesorOriginal.getId()+2]);
						}
					}
				}
			}
		}
	/*	
		NodoP primero=grafoProposiciones[0];
		System.out.print("(");
		for(NodoP suc: primero.getSucesores()){
			System.out.print(suc.getId() + " ");
		}
		System.out.print(")\n");
		
		NodoP segundo=grafoProposiciones[1];
		System.out.print("(");
		for(NodoP suc: segundo.getSucesores()){
			System.out.print(suc.getId() + " ");
		}
		System.out.print(")\n");
	*/	//aplicamos kosaraju
		
		List<Stack<NodoP>> componentesFuertementeConexas = componentesFuertementeConexas(grafoProposiciones);
		
	//	System.out.println(componentesFuertementeConexas);
/*		System.out.print("[ ");
		for(int i=0;i<componentesFuertementeConexas.size();i++){
			System.out.print("[ ");
			List<NodoP> componenteConexa=componentesFuertementeConexas.get(i);
			for(int j=0;j<componenteConexa.size();j++){
				System.out.print(componenteConexa.get(j).getId());
				System.out.print(" ");
			}
			System.out.print(" ]");
		}
		System.out.print("]\n");
	*/
		//nos fijamos si en una misma componente conexa, se encuentra una proposicion y su negacion
		
		boolean hayContradiccion=false;
//		System.out.println(grafoProposiciones[10].isEsNegacion());

		busquedaContradicion:
		for(List<NodoP> componente : componentesFuertementeConexas){
			for(NodoP nodp : componente){					
				if(grafoProposiciones[nodp.getId()].isEsNegacion()){
					for(NodoP inverso: componente){
						if(inverso.getId()==(nodp.getId()-2)){
							hayContradiccion=true;
							System.out.println("hay contradiccion\n");
							break busquedaContradicion;
						}
					}
				}else{
					for(NodoP inverso: componente){
						if(inverso.getId()==(nodp.getId()+2)){
							hayContradiccion=true;
							System.out.println("hay contradiccion2 "+inverso.getId()+" "+nodp.getId());
						//	System.out.println(nodp.isEsNegacion());
							break busquedaContradicion;
						}
					}
				}
			}
		}
		
		if (hayContradiccion) return (hayContradiccion);

		System.out.println("se puede pintar");
		
		//en caso de que no halla contradiccion, vemos si hay un camino de ¬c1 a c1, en caso de que lo halla c1 es verdadero
	
	for(int i=0;i<grafoProposiciones.length;i++){
		
		if(grafoProposiciones[i].getValorVerdad()==2 && !(grafoProposiciones[i].isVacio())){//todabia no tienen valor de verdad, y no son vacios
			if(grafoProposiciones[i].isEsNegacion()){
				
				//aplicamos bfs para saber si estan conectados Â¬c1 -> c1
				
				Queue<NodoP> cola= new LinkedList<NodoP>();
				cola.add(grafoProposiciones[i]);
				grafoProposiciones[i].marcar();
				bfs:
				while(!(cola.isEmpty())){
					NodoP w=cola.remove();
					for(NodoP z : w.getSucesores()){
						if(z.getId()==i-2){
							
							System.out.println("camino desde "+ i + " a "+z.getId());
							z.setValorVerdad(1);
							for(int j=0;j<grafoProposiciones.length;j++){
								grafoProposiciones[j].desMarcar();
							}
							break bfs;
						}	
						if(!(z.isMarcado())){
							z.marcar();
							cola.add(z);
						}
					}
				}
			}else{
				//aplicamos bfs para saber si estan conectados c1 -> Â¬c1
				
				Queue<NodoP> cola= new LinkedList<NodoP>();
				cola.add(grafoProposiciones[i]);
				grafoProposiciones[i].marcar();
				bfs:
				while(!(cola.isEmpty())){
					NodoP w=cola.remove();
					for(NodoP z : w.getSucesores()){
						if(z.getId()==i+2){
							
							System.out.println("camino desde "+ i + " a "+z.getId());
							grafoProposiciones[i].setValorVerdad(0);
							//seteamos en falso tambien los de la misma componente conexa 
							setearEnfalso:
							for(Stack<NodoP> componenteConexa : componentesFuertementeConexas){
													
								Iterator<NodoP> itComp= componenteConexa.iterator();
								while(itComp.hasNext()){
									NodoP prop=itComp.next();
									if(prop.getId()==i){
										//pongo en falso las componentes
										for(NodoP aSetearEnFalso:componenteConexa){
											aSetearEnFalso.setValorVerdad(0);
										}
										break setearEnfalso;
									}
								}	
							}
							for(int j=0;j<grafoProposiciones.length;j++){
								grafoProposiciones[j].desMarcar();
							}
							break bfs;
						}	
						if(!(z.isMarcado())){
							z.marcar();
							cola.add(z);
						}
					}
				}
			}
		}
	}
	//completamos cuatro nodos en caso de tener uno solo
	
	for(int i=0;i<grafoProposiciones.length;i++){
		if(!(grafoProposiciones[i].isVacio())){
			if(i%4==0){
				if(grafoProposiciones[i].getValorVerdad()==1){
					grafoProposiciones[i+1].setValorVerdad(0);
					grafoProposiciones[i+2].setValorVerdad(0);
					grafoProposiciones[i+3].setValorVerdad(1);
				}else if(grafoProposiciones[i].getValorVerdad()==0){
					grafoProposiciones[i+1].setValorVerdad(1);
					grafoProposiciones[i+2].setValorVerdad(1);
					grafoProposiciones[i+3].setValorVerdad(0);
				}
			}else if(i%4==1){
				if(grafoProposiciones[i].getValorVerdad()==1){
					grafoProposiciones[i-1].setValorVerdad(0);
					grafoProposiciones[i+1].setValorVerdad(1);
					grafoProposiciones[i+2].setValorVerdad(0);
				}else if(grafoProposiciones[i].getValorVerdad()==0){
					grafoProposiciones[i-1].setValorVerdad(1);
					grafoProposiciones[i+1].setValorVerdad(0);
					grafoProposiciones[i+2].setValorVerdad(1);
				}
				
			}else if(i%4==2){
				if(grafoProposiciones[i].getValorVerdad()==1){
					grafoProposiciones[i-2].setValorVerdad(0);
					grafoProposiciones[i-1].setValorVerdad(1);
					grafoProposiciones[i+1].setValorVerdad(0);
				}else if(grafoProposiciones[i].getValorVerdad()==0){
					grafoProposiciones[i-2].setValorVerdad(1);
					grafoProposiciones[i-1].setValorVerdad(0);
					grafoProposiciones[i+1].setValorVerdad(1);
				}
			}else{
				if(grafoProposiciones[i].getValorVerdad()==1){
					grafoProposiciones[i-3].setValorVerdad(1);
					grafoProposiciones[i-2].setValorVerdad(0);
					grafoProposiciones[i-1].setValorVerdad(0);
				}else if(grafoProposiciones[i].getValorVerdad()==0){
					grafoProposiciones[i-3].setValorVerdad(0);
					grafoProposiciones[i-2].setValorVerdad(1);
					grafoProposiciones[i-1].setValorVerdad(1);
				}
			}
		}
	}
	// expandimos los verdaderos

	for(int i=0;i<grafoProposiciones.length;i++){
		if(grafoProposiciones[i].getValorVerdad()==1){
			for(NodoP succ:grafoProposiciones[i].getSucesores() ){
				cambiarSucesores(succ);
			}
		}
	}
	
	if(noSepuedePintar) {
		System.out.println("acaa");
		return true;
	}
	
	//volvemos a completar los cuatro

	for(int i=0;i<grafoProposiciones.length;i++){
		if(!(grafoProposiciones[i].isVacio())){
			if(i%4==0){
				if(grafoProposiciones[i].getValorVerdad()==1){
					grafoProposiciones[i+1].setValorVerdad(0);
					grafoProposiciones[i+2].setValorVerdad(0);
					grafoProposiciones[i+3].setValorVerdad(1);
				}else if(grafoProposiciones[i].getValorVerdad()==0){
					grafoProposiciones[i+1].setValorVerdad(1);
					grafoProposiciones[i+2].setValorVerdad(1);
					grafoProposiciones[i+3].setValorVerdad(0);
				}
			}else if(i%4==1){
				if(grafoProposiciones[i].getValorVerdad()==1){
					grafoProposiciones[i-1].setValorVerdad(0);
					grafoProposiciones[i+1].setValorVerdad(1);
					grafoProposiciones[i+2].setValorVerdad(0);
				}else if(grafoProposiciones[i].getValorVerdad()==0){
					grafoProposiciones[i-1].setValorVerdad(1);
					grafoProposiciones[i+1].setValorVerdad(0);
					grafoProposiciones[i+2].setValorVerdad(1);
				}
				
			}else if(i%4==2){
				if(grafoProposiciones[i].getValorVerdad()==1){
					grafoProposiciones[i-2].setValorVerdad(0);
					grafoProposiciones[i-1].setValorVerdad(1);
					grafoProposiciones[i+1].setValorVerdad(0);
				}else if(grafoProposiciones[i].getValorVerdad()==0){
					grafoProposiciones[i-2].setValorVerdad(1);
					grafoProposiciones[i-1].setValorVerdad(0);
					grafoProposiciones[i+1].setValorVerdad(1);
				}
			}else{
				if(grafoProposiciones[i].getValorVerdad()==1){
					grafoProposiciones[i-3].setValorVerdad(1);
					grafoProposiciones[i-2].setValorVerdad(0);
					grafoProposiciones[i-1].setValorVerdad(0);
				}else if(grafoProposiciones[i].getValorVerdad()==0){
					grafoProposiciones[i-3].setValorVerdad(0);
					grafoProposiciones[i-2].setValorVerdad(1);
					grafoProposiciones[i-1].setValorVerdad(1);
				}
			}
		}
	}		
	// en caso de haber con valor 2, los ponemos con algun valor a los 4, y a su vez expandimos los verdaderos
/*	System.out.println();
	for(int i=0;i<grafoProposiciones.length;i++){
	System.out.println(grafoProposiciones[i].getValorVerdad());
}
	System.out.println();
	*/
	for(int i=0;i<grafoProposiciones.length;i++){
		if(!(grafoProposiciones[i].isVacio())){
			if(grafoProposiciones[i].getValorVerdad()==2){
				if(i%4==0){
					grafoProposiciones[i].setValorVerdad(1);
					grafoProposiciones[i+1].setValorVerdad(0);
					grafoProposiciones[i+2].setValorVerdad(0);
					grafoProposiciones[i+3].setValorVerdad(1);
					
					cambiarSucesores(grafoProposiciones[i]);
					if(noSepuedePintar) {
						System.out.println("porqq jj " + i);
						System.out.println();
						for(int j=0;j<grafoProposiciones.length;j++){
						System.out.println(grafoProposiciones[j].getValorVerdad());
						}
						System.out.println();
						return true;
					}
					
					cambiarSucesores(grafoProposiciones[i+3]);
					if(noSepuedePintar) {
						System.out.println("porqqooo " + i);
						return true;
					}
				}else if(i%4==1){
					grafoProposiciones[i-1].setValorVerdad(0);
					grafoProposiciones[i].setValorVerdad(1);
					grafoProposiciones[i+1].setValorVerdad(1);
					grafoProposiciones[i+2].setValorVerdad(0);
					cambiarSucesores(grafoProposiciones[i]);
					if(noSepuedePintar) {
						System.out.println("porqq " + i);
						return true;
					}
					
					cambiarSucesores(grafoProposiciones[i+1]);
					if(noSepuedePintar) {
						System.out.println("porqq " + i);
						return true;
					}
				}else if(i%4==2){
					grafoProposiciones[i-2].setValorVerdad(0);
					grafoProposiciones[i-1].setValorVerdad(1);
					grafoProposiciones[i].setValorVerdad(1);
					grafoProposiciones[i+1].setValorVerdad(0);
					cambiarSucesores(grafoProposiciones[i]);
					if(noSepuedePintar) {
						System.out.println("porqq " + i);
						return true;
					}
					
					cambiarSucesores(grafoProposiciones[i-1]);
					if(noSepuedePintar) {
						System.out.println("porqq " + i);
						return true;
					}
				}else{
					grafoProposiciones[i-3].setValorVerdad(1);
					grafoProposiciones[i-2].setValorVerdad(0);
					grafoProposiciones[i-1].setValorVerdad(0);
					grafoProposiciones[i].setValorVerdad(1);
					
					cambiarSucesores(grafoProposiciones[i]);
					if(noSepuedePintar) {
						System.out.println("porqq " + i);
						return true;
					}
					
					cambiarSucesores(grafoProposiciones[i-3]);
					if(noSepuedePintar) {
						System.out.println("porqq " + i);
						return true;
					}
				}
			/*	
				System.out.println();
				for(int j=0;j<grafoProposiciones.length;j++){
				System.out.println(grafoProposiciones[j].getValorVerdad());
				}
				System.out.println();
			*/	
	
				for(int h=0;h<grafoProposiciones.length;h++){
					if(!(grafoProposiciones[h].isVacio())){
						if(h%4==0){
							if(grafoProposiciones[h].getValorVerdad()==1){
								grafoProposiciones[h+1].setValorVerdad(0);
								grafoProposiciones[h+2].setValorVerdad(0);
								grafoProposiciones[h+3].setValorVerdad(1);
							}else if(grafoProposiciones[h].getValorVerdad()==0){
								grafoProposiciones[h+1].setValorVerdad(1);
								grafoProposiciones[h+2].setValorVerdad(1);
								grafoProposiciones[h+3].setValorVerdad(0);
							}
						}else if(h%4==1){
							if(grafoProposiciones[h].getValorVerdad()==1){
								grafoProposiciones[h-1].setValorVerdad(0);
								grafoProposiciones[h+1].setValorVerdad(1);
								grafoProposiciones[h+2].setValorVerdad(0);
							}else if(grafoProposiciones[h].getValorVerdad()==0){
								grafoProposiciones[h-1].setValorVerdad(1);
								grafoProposiciones[h+1].setValorVerdad(0);
								grafoProposiciones[h+2].setValorVerdad(1);
							}
							
						}else if(h%4==2){
							if(grafoProposiciones[h].getValorVerdad()==1){
								grafoProposiciones[h-2].setValorVerdad(0);
								grafoProposiciones[h-1].setValorVerdad(1);
								grafoProposiciones[h+1].setValorVerdad(0);
							}else if(grafoProposiciones[h].getValorVerdad()==0){
								grafoProposiciones[h-2].setValorVerdad(1);
								grafoProposiciones[h-1].setValorVerdad(0);
								grafoProposiciones[h+1].setValorVerdad(1);
							}
						}else{
							if(grafoProposiciones[h].getValorVerdad()==1){
								grafoProposiciones[h-3].setValorVerdad(1);
								grafoProposiciones[h-2].setValorVerdad(0);
								grafoProposiciones[h-1].setValorVerdad(0);
							}else if(grafoProposiciones[h].getValorVerdad()==0){
								grafoProposiciones[h-3].setValorVerdad(0);
								grafoProposiciones[h-2].setValorVerdad(1);
								grafoProposiciones[h-1].setValorVerdad(1);
							}
						}
					}
				}
			}
		}
	}
	/*
	System.out.println();
	for(int j=0;j<grafoProposiciones.length;j++){
	System.out.println(grafoProposiciones[j].getValorVerdad());
	}
	System.out.println();
	*/
	//pintamos los verdaderos del grafo de proposiciones
	for(int i=0;i<grafoProposiciones.length;i++){
		if(! grafoProposiciones[i].isVacio()){
			if(i%4==0){
				if(grafoProposiciones[i].getValorVerdad()==1){
					nodosGrafo[grafoProposiciones[i].getnNodoAsociado()].setColor(grafoProposiciones[i].getColorAsociado());
				}
			}
			if(i%4==1){
				if(grafoProposiciones[i].getValorVerdad()==1){
					nodosGrafo[grafoProposiciones[i].getnNodoAsociado()].setColor(grafoProposiciones[i].getColorAsociado());
				}
			}
		}
	}
	

	return (hayContradiccion);
}
//	for(int i=0;i<grafoProposiciones.length;i++){
//		if(!grafoProposiciones[i].isEsNegacion()){
//			
//			//aplicamos bfs para saber si estan conectados c1 -> Â¬c1
//			
//			Queue<NodoP> cola= new LinkedList<NodoP>();
//			cola.add(grafoProposiciones[i]);
//			grafoProposiciones[i].marcar();
//			bfs:
//			while(!(cola.isEmpty())){
//				NodoP w=cola.remove();
//				for(NodoP z : w.getSucesores()){
//					if(z.getId()==i+2){
//						
//						System.out.println("camino desde "+ i + " a "+z.getId());
//						grafoProposiciones[i].setValorVerdad(0);
//						break bfs;
//					}	
//					if(!(z.isMarcado())){
//						z.marcar();
//						cola.add(z);
//					}
//				}
//			}
//		}
//	}
		
//		for(Stack<NodoP> componenteConexa : componentesFuertementeConexas){
//			
//			boolean esVerdaderaLaComponente=false;
//
//			Iterator<NodoP> itComp= componenteConexa.iterator();
//			while(itComp.hasNext()){
//				NodoP prop=itComp.next();
//					
//				if(! (prop.isEsNegacion())){
//					int indice=prop.getId();
//					System.out.println("afirmacion");
//					prop=grafoProposiciones[indice +2 ];
//				}else{
//					System.out.println("bfs");
//				//aplicamos bfs para saber si estan conectados ¬c1 -> c1
//					Queue<NodoP> cola= new LinkedList<NodoP>();
//					cola.add(prop);
//					prop.marcar();
//					bfs:
//					while(!(cola.isEmpty())){
//						NodoP w=cola.remove();
//						for(NodoP z : w.getSucesores()){
//							if(z.getId()==prop.getId()-2){
//								
//								System.out.println("camino desde "+ prop.getId() + " a "+z.getId());
//								z.setValorVerdad(1);
//								esVerdaderaLaComponente=true;
//								break bfs;
//							}	
//							if(!(z.isMarcado())){
//								z.marcar();
//								cola.add(z);
//							}
//						}
//					}
//				}
//				
//			}
//				
//				
//				
//				System.out.println("la componente es "+ esVerdaderaLaComponente);
//				if(esVerdaderaLaComponente){
//					//falta
//					Iterator<NodoP> itComp2= componenteConexa.iterator();
//					while(itComp2.hasNext()){
//						NodoP aCambiar=itComp2.next();
//						cambiarSucesores(aCambiar);
//					}
//					
//					
//				}/*else{
//					Iterator<NodoP> itComp= componenteConexa.iterator();
//					while(itComp.hasNext()){
//						NodoP aCambiar=itComp.next();
//						aCambiar.setValorVerdad(0);
//					}
//				}*/
//		}
//		
//		/*
//		
//		for(int i=0;i<grafoProposiciones.length;i++){
//			if(!grafoProposiciones[i].isEsNegacion()){
//				
//				//aplicamos bfs para saber si estan conectados c1 -> ¬c1
//				
//				Queue<NodoP> cola= new LinkedList<NodoP>();
//				cola.add(grafoProposiciones[i]);
//				grafoProposiciones[i].marcar();
//				bfs:
//				while(!(cola.isEmpty())){
//					NodoP w=cola.remove();
//					for(NodoP z : w.getSucesores()){
//						if(z.getId()==i+2){
//							
//							System.out.println("camino desde "+ i + " a "+z.getId());
//							grafoProposiciones[i].setValorVerdad(0);
//							break bfs;
//						}	
//						if(!(z.isMarcado())){
//							z.marcar();
//							cola.add(z);
//						}
//					}
//				}
//			}
//		}
//	
//		*/
//		for(int i=0;i<grafoProposiciones.length;i++){
//			System.out.println(grafoProposiciones[i].getValorVerdad());
//		}
//
//		//pintamos los verdaderos del grafo de proposiciones
//		for(int i=0;i<grafoProposiciones.length;i++){
//			if(i%4==0){
//				if(grafoProposiciones[i].getValorVerdad()==1){
//					nodosGrafo[grafoProposiciones[i].getnNodoAsociado()].setColor(grafoProposiciones[i].getColorAsociado());
//				}
//			}
//			if(i%4==1){
//				if(grafoProposiciones[i].getValorVerdad()==1){
//					nodosGrafo[grafoProposiciones[i].getnNodoAsociado()].setColor(grafoProposiciones[i].getColorAsociado());
//				}
//			}
//		}
//		
//		return hayContradiccion;
//
//	}
	/*	
		//aplicamos bfs para pintar los nodos que todabia no tienen color(aquellos, cuyo valor de verdad es 2)
		for(int num=0; num<nodosGrafo.length;num++){
			if(!(nodosGrafo[num].tieneColor())){
				Queue<Nodo> cola= new LinkedList<Nodo>();
				cola.add(nodosGrafo[num]);
				List<Integer> coloresPosibles = nodosGrafo[num].getColoresPosibles();
				
				//elijo un color posible para pintar nodosGrafo[num]
				boolean sePudoPintar=false;
				for(int color:coloresPosibles){
					boolean sePuedePintar=Ejercicio2.sePuedePintar(nodosGrafo, num,  color);
					if(sePuedePintar){
						nodosGrafo[num].setColor(color);
						System.out.println("color es "+color);
						sePudoPintar=true;
						break;
					}
				}
				if(!sePudoPintar){
					System.err.println("no es posible pintar ese nodo");
					return (!sePudoPintar);
				}
				
				while(!(cola.isEmpty())){
					Nodo w=cola.remove();
					for(Nodo z : w.getSucesores()){	
						if(!(z.tieneColor())){
							cola.add(z);		
							coloresPosibles = z.getColoresPosibles();		
							//elijo un color posible para pintar z
							
							sePudoPintar=false;
							for(int color:coloresPosibles){
								boolean sePuedePintar=Ejercicio2.sePuedePintar(nodosGrafo, z.getId(),  color);
								if(sePuedePintar){
									z.setColor(color);
									System.out.println("color esss "+color);
									sePudoPintar=true;
									break;
								}
							}
							if(!sePudoPintar){
								System.err.println("no es posible pintar ese nodo");
								return (!sePudoPintar);
							}
						}
					}
				}
			}
		}
		*/


	  public static List<Stack<NodoP>> componentesFuertementeConexas(NodoP[] grafo) {
		    int n = grafo.length;
		    //marcamos todos los vertices
		    boolean[] marcados = new boolean[n];
		    Stack<NodoP> orden = new Stack<NodoP>();
		    for (int i = 0; i < n; i++)
		      if (!marcados[i])
		        dfs(grafo, marcados, orden, i);

		    //obtenemos el grafo reverso del original
		    NodoP[] grafoReverso = new NodoP[n];
		    for (int i = 0; i < n; i++){
		    	grafoReverso[i] = new NodoP(i);
		    	grafoReverso[i].setEsNegacion(grafo[i].isEsNegacion());
		    	if(grafo[i].isVacio()) grafoReverso[i].setVacio();
		    }
		    for (int i = 0; i < n; i++)
		      for (NodoP nodP : grafo[i].getSucesores())
		    	  grafoReverso[nodP.getId()].addSucesor(grafo[i]);
		    
		    List<Stack<NodoP>> componentes = new ArrayList<>();
		    Arrays.fill(marcados, false);
		    
		    while(!orden.isEmpty()){
			    NodoP nn= orden.pop();
			    if (!marcados[nn.getId()]) {
			    	Stack<NodoP> componente = new Stack<>();
			    	dfs(grafoReverso, marcados, componente, nn.getId());
			        componentes.add(componente);
			     }
		    }		   
		    return componentes;
		  }

		  static void dfs(NodoP[] grafo, boolean[] marcados, Stack<NodoP> res, int u) {
		    marcados[u] = true;
		    for (NodoP nodp : grafo[u].getSucesores())
		      if (!marcados[nodp.getId()])
		        dfs(grafo, marcados, res, nodp.getId());
		    res.push(grafo[u]);
		  }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("ejercicio1");
		
		FileInputStream entrada =null;  
		BufferedReader reader =null;
		FileWriter escribidor=null;
		
		try{
			entrada = new FileInputStream(args[0]);
			reader = new BufferedReader(new InputStreamReader(entrada));
			escribidor= new FileWriter("Ej1Salidas.out");				
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
			
			long tAntes=System.currentTimeMillis();
			boolean hayContradiccion=noSePuedePintar(nodosGrafo);
			System.out.println(System.currentTimeMillis()-tAntes);
			if(hayContradiccion){
				escribidor.write("X");
				escribidor.write("\n");
				return;
			}else{
				

				
				//escribimos el resultado del coloreo
				for(int i=0;i<n;i++){
					escribidor.write(Integer.toString(nodosGrafo[i].getColor()));						
					escribidor.write("\n");
				}
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

