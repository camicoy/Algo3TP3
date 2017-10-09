//package algo3tp3;

import java.util.LinkedList;
import java.util.List;

public class NodoP {
	private int id;// de 0 a 4*n-1  Semantica: cada cuatro colores tenemos las proposiciones asociadas a cada nodo, primero vienen las afirmaciones de los dos colores y luego las negaciones de los dos colores 
	private int nNodoAsociado;
	private int colorAsociado;
	private boolean esNegacion;
	private List<NodoP> sucesores;
	private int valorDeVerdad;
	private boolean marcado;
	private boolean esVacio;
	
	public NodoP(int id) {
		this.id = id;
		sucesores= new LinkedList<NodoP>();
		valorDeVerdad=2;
		marcado=false;
		esVacio=false;
	}
	
	public boolean isVacio(){
		return esVacio;
	}
	
	
	public void setVacio(){
		esVacio=true;
	}
	
	public void marcar(){
		marcado=true;
	}
	
	public void desMarcar(){
		marcado=false;
	}
	public boolean isMarcado(){
		return marcado;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getValorVerdad() {
		return valorDeVerdad;
	}
	public void setValorVerdad(int valorDeVerdad) {
		this.valorDeVerdad = valorDeVerdad;
	}
	
	public int getnNodoAsociado() {
		return nNodoAsociado;
	}
	public void setnNodoAsociado(int nNodoAsociado) {
		this.nNodoAsociado = nNodoAsociado;
	}
	public int getColorAsociado() {
		return colorAsociado;
	}
	public void setColorAsociado(int colorAsociado) {
		this.colorAsociado = colorAsociado;
	}
	public boolean isEsNegacion() {
		return esNegacion;
	}
	public void setEsNegacion(boolean esNegacion) {
		this.esNegacion = esNegacion;
	}
	public List<NodoP> getSucesores() {
		return sucesores;
	}
	public void addSucesor(NodoP sucesor) {
		sucesores.add(sucesor);
	}

}
