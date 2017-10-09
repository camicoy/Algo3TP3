import java.util.Random;
import java.lang.Boolean;


public class generadorInstancias {
	
	private Nodo[] casoAleatorio(){
		Nodo[] grafo;
		for(int n = 10; n < 50; n = n + 5){
			grafo = new Nodo[n];
			Random rnd = new Random(n);
			int m = n + rnd.nextInt(n*(n-1)/2 - n); // desde n hasta (n*(n-1))/2 -1
			int c = 2 + rnd.nextInt(n - 3); // desde 2 hasta n -1
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
						colores[col] = true;
					}
					ver.fijarColoresOriginales();
				}
				grafo[i] = ver;
			}
			for(int j = 0; j < m; j++){
				Random rnd1 = new Random(j);
				int u = rnd1.nextInt(n - 1); //hasta n -1
				int v = rnd1.nextInt(n - 1); //hasta n -1
				while(u == v){
					rnd1.nextInt(n - 1); // hasta n -1
				}
				boolean esta = false;
				for(Nodo sucesor: grafo[u].getSucesores()){
					if(sucesor.getId() == v){
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
		}
		return grafo;
	}

	private Nodo[] casoVariableN(){
		Nodo[] grafo;
		for(int n = 10; n < 50; n = n + 5){
			Random rnd = new Random(n);
			grafo = new Nodo[n];
			int m = 2*n;
			int c = n/2;
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
						colores[col] = true;
					}
					ver.fijarColoresOriginales();
				}
				grafo[i] = ver;
			}
			for(int j = 0; j < m; j++){
				Random rnd1 = new Random(j);
				int u = rnd1.nextInt(n - 1); //hasta n -1
				int v = rnd1.nextInt(n - 1); //hasta n -1
				while(u == v){
					v = rnd1.nextInt(n - 1); // hasta n -1
				}
				boolean esta = false;
				for(Nodo sucesor: grafo[u].getSucesores()){
					if(sucesor.getId() == v){
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
		}
		return grafo;
	}

	private Nodo[] casoVariableM(){
		Nodo[] grafo;
		int n = 40;
		int c = 20;
		for(int m = 40; m < 780; m = m + 40){
			Random rnd = new Random(m);
			grafo = new Nodo[n];
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
						colores[col] = true;
					}
					ver.fijarColoresOriginales();
				}
				grafo[i] = ver;
			}
			for(int j = 0; j < m; j++){
				Random rnd1 = new Random(j);
				int u = rnd1.nextInt(n - 1); //hasta n -1
				int v = rnd1.nextInt(n - 1); //hasta n -1
				while(u == v){
					v = rnd1.nextInt(n - 1); // hasta n -1
				}
				boolean esta = false;
				for(Nodo sucesor: grafo[u].getSucesores()){
					if(sucesor.getId() == v){
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
		}
		return grafo;
	}

	private Nodo[] casoVariableC(){
		Nodo[] grafo;
		int n = 60;
		int m = 300;
		for(int c = 20; c < 60; c = c + 3){
			Random rnd = new Random(c);
			grafo = new Nodo[n];
			for(int i = 0; i < n; i++){
				Nodo ver = new Nodo(i);
				int cant = 1 + rnd.nextInt(c - 2); // desde 1 hasta c -1
				boolean[] colores = new boolean[c];
				for(int a = 0; a < c; a++){
					colores[a] = false;
				}
				for(int k = 0; k < cant; k++){
					int col = rnd.nextInt(c - 1); //desde 0 hasta c - 1
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
						colores[col] = true;
					}
					ver.fijarColoresOriginales();
				}
				grafo[i] = ver;
			}
			for(int j = 0; j < m; j++){
				Random rnd1 = new Random(j);
				int u = rnd1.nextInt(n - 1); //hasta n -1
				int v = rnd1.nextInt(n - 1); //hasta n -1
				while(u == v){
					v = rnd1.nextInt(n - 1); // hasta n -1
				}
				boolean esta = false;
				for(Nodo sucesor: grafo[u].getSucesores()){
					if(sucesor.getId() == v){
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
		}
		return grafo;
	}
	
	public static void main(String[] args) {
		System.out.println("Tiempos");
	}
}
