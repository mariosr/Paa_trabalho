package uece.maxmatching;

import java.util.ArrayList;
import java.util.Random;

public class RandomicoHomogeneo {
	
	private Vertice vertices[];
	private ArrayList<Integer> lista = new ArrayList<Integer>();   
	
	public RandomicoHomogeneo(Vertice vertices[]){
		this.vertices = vertices;
	}
	
	public int gerarNumero(){
		
		Random rand = new Random();     
          
        int numero = -1;  
        int tamanho = vertices.length;
        
        while(lista.size() < tamanho){  
            numero = rand.nextInt(tamanho) + 1;  
            if(lista.contains(numero) == false){  
                System.out.println(numero);  
                lista.add(numero);  
            }  
        }  
        
        return numero; 
	}
	
}
