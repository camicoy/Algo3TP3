//package algo3tp3;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Nodo {
	
	private int id;// de 0 a n-1
	private int color;
	private boolean tieneColor;
	private List<Nodo> sucesores;
	private List<Integer> coloresPosibles;
	private Set<Integer> coloresOriginales;
	
	public Nodo(int id) {
		// TODO Auto-generated constructor stub		
		this.id=id;
		color=0;
		tieneColor=false;
		sucesores=new LinkedList<Nodo>();
		coloresPosibles= new LinkedList<Integer>();
		coloresOriginales= new HashSet<Integer>();
	}
	
	public void fijarColoresOriginales(){
		coloresOriginales.addAll(coloresPosibles);
	}
	
	public void restaurarColoresPosibles(){
		coloresPosibles.clear();
		coloresPosibles.addAll(coloresOriginales);
	}
	
	public void addColor(int color){
		coloresPosibles.add(color);
	}
	
	public void removeColor(int color){
		coloresPosibles.remove(new Integer(color));
	}
	
	public void addSucesor(Nodo n){
		sucesores.add(n);
	}

	public int getId() {
		return id;
	}

	public int getColor() {
		if(tieneColor) return color;
		System.err.println("no tiene color");
		return -1;
	}

	public void setColor(int color) {
		tieneColor=true;
		this.color = color;
	}

	public boolean tieneColor() {
		return tieneColor;
	}

	public List<Nodo> getSucesores() {
		return sucesores;
	}
	
	public List<Integer> getColoresPosibles() {
		return coloresPosibles;
	}


}
