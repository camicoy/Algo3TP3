//package algo3tp3;
/**
 * 
 */



public class Pair<T1, T2> {

	private T1 first;
	
	private T2 second;
	
	
	public Pair(T1 primero, T2 segundo) {
		
		first= primero;
		
		second=segundo;
	}
	
	public T1 getFirst(){
		
		return first;
		
	}
	
	public T2 getSecond(){
		
		return second;
		
	}
	
	public boolean equals(Pair<T1, T2> obj){
		return(obj.getFirst().equals(first) && obj.getSecond().equals(second) );
	}
	
	
	
	

}
