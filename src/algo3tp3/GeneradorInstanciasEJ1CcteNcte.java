//package algo3tp3;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;


public class GeneradorInstanciasEJ1CcteNcte {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("instanias m");
		final int c=100;
		final int n=10000;
		final  int M=20000;
		FileWriter[] archivos=new FileWriter[M/2000];
		Random cantColores= new Random(53);
		Random color=new Random(54);
		Random nodoArista=new Random(56);


		try {
			int m=0;
			for(int i=0;i<(M/2000);i++){
				//la linea inicial
				archivos[i] = new FileWriter("Ej1InstanciasM"+ m +".in");
				archivos[i].write(Integer.toString(n));
				archivos[i].write(" ");
				archivos[i].write(Integer.toString(m));
				archivos[i].write(" ");
				archivos[i].write(Integer.toString(c));
				archivos[i].write("\n");
				//los colores de nodos
				for(int j=0;j<n;j++){
					int cantidadColores= cantColores.nextInt(2) +1;
					archivos[i].write(Integer.toString(cantidadColores));
					archivos[i].write(" ");
					if(cantidadColores==1){
						archivos[i].write(Integer.toString(color.nextInt(c)));
						archivos[i].write("\n");
					}else{
						Set<Integer> dosColores= new HashSet<Integer>();
						
						while(dosColores.size() !=2){
							Integer aAgregar= color.nextInt(c);
							dosColores.add(aAgregar);
						}
					//	System.out.println("colores");
						Iterator<Integer> itCol= dosColores.iterator();
						archivos[i].write(Integer.toString(itCol.next()));
						archivos[i].write(" ");
						archivos[i].write(Integer.toString(itCol.next()));
						archivos[i].write("\n");
					}
			//		System.out.println("otro n");

				}
	//			System.out.println("aristas");

				Set<Pair<Integer,Integer >> nodosAristas= new HashSet<Pair<Integer,Integer >>();
				
				while(true){
					Integer aAgregar1= nodoArista.nextInt(n);
					Integer aAgregar2= nodoArista.nextInt(n);
					Pair<Integer,Integer > aAgregar= new Pair<>(aAgregar1,aAgregar2);
					nodosAristas.add(aAgregar);
					if(nodosAristas.size()>=2*m) break;
				}
				Iterator<Pair<Integer,Integer >> itAris= nodosAristas.iterator();

				//aristas
				for(int k=0;k<m;k++){
					//System.out.println("m");
					Pair<Integer,Integer > agre= itAris.next();
					archivos[i].write(Integer.toString(agre.getFirst()));
					archivos[i].write(" ");
					archivos[i].write(Integer.toString(agre.getSecond()));
					archivos[i].write("\n");
				}
				
				System.out.println("un archivoooooooooooooooooooooooooooooo");

				m+=2000;
				archivos[i].close();
			}
			
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
