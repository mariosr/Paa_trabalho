package uece.maxmatching;

import java.util.ArrayList;
import java.util.Random;

public class RandomicoHomogeneo {
	
	private Vertice vertices[];
	private ArrayList<Integer> lista = new ArrayList<Integer>();   
	
	public RandomicoHomogeneo(Grafo grafo){
		this.vertices = grafo.vertices;
	}
	
	public Boolean ehLivre(int position){
		
		if(vertices[position].status == Status.LIVRE){
			return true;
		}else return false;
		
	}
	
	public int gerarNumero(){
		
		Random rand = new Random();     
          
        int numero = -1;  
        int tamanho = vertices.length - 1;
        
        while(lista.size() < tamanho){  
            numero = rand.nextInt(tamanho) + 1;  
            if(lista.contains(numero) == false && ehLivre(numero) == true){  
                	System.out.println(numero);  
                	lista.add(numero);
            }  
        }  
        
        return numero; 
	}
	
}
