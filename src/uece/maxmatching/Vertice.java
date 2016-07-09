package uece.maxmatching;

/**
 * Created by jeffrodrigo on 06/07/16.
 *
 * Representa o Vertice de um grafo
 */
public class Vertice {
    public int nome;
    public final Aresta[] arestas;
    public Status status = Status.LIVRE;

    Vertice(int N) {
        arestas = new Aresta[N];
    }
    
    public Aresta getArestaSaturada(){
    	for (int i = 0; i < arestas.length; i++) {
			if(arestas[i].status == Status.SATURADO){
				return arestas[i];
			}
		}
    	//é um vertice livre
    	return null;
    }
    
}
