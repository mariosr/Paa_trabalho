package uece.maxmatching;

import java.util.Arrays;

/**
 * Created by jeffrodrigo on 06/07/16.
 *
 * Representa a aresta de um grafo
 */
public class Aresta implements  Comparable<Aresta>{
    public final int peso;
    public Status status = Status.LIVRE;
    public final Vertice origem;
    public final Vertice destino;
    
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

    public Aresta(Vertice origem, Vertice destino, int peso) {
        this.peso = peso;
        this.origem = origem;
        this.destino = destino;
    }
    
    public void trocaStatus(){
    	if(status == Status.LIVRE) status = Status.SATURADO;
    	else if(status == Status.SATURADO) status = Status.LIVRE;
    }

    boolean emparelhada() {
        return this.status == Status.SATURADO;
    }
    @Override
    public int compareTo(Aresta aresta) {
        return this.peso - aresta.peso;
    }

    public void emparelhar() {
        this.status = Status.SATURADO;
        this.origem.emparelhar();
        this.destino.emparelhar();
    }

    public boolean temArestaEmparelhada() {
        if (this.origem.emparelhado()) return true;
        if (this.destino.emparelhado()) return true;
        return false;
    }
}
