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

    Vertice(int nArestas) {
        arestas = new Aresta[nArestas];
    }

    Vertice(int nome, int nArestas) {
        this.nome = nome;
        arestas = new Aresta[nArestas];
    }

    int retornaIndice() {
        return nome;
    }
    
    public void trocaStatus(){
    	if(status == Status.LIVRE) status = Status.SATURADO;
    	else if(status == Status.SATURADO) status = Status.LIVRE;
    }
    
    public Aresta getArestaSaturada(){
    	for (int i = 0; i < arestas.length; i++) {
			if(arestas[i].status == Status.SATURADO){
				return arestas[i];
			}
		}
    	//� um vertice livre
    	return null;
    }
    
    public Status getStatus(){
    	return status;
    }

    public boolean emparelhado() {
        return this.status == Status.SATURADO;
    }

    public void emparelhar() {
        this.status = Status.SATURADO;
    }
}
