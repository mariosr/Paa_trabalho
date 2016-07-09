package uece.maxmatching;

/**
 * Created by jeffrodrigo on 06/07/16.
 *
 * Representa a aresta de um grafo
 */
public class Aresta {
    public int peso;
    public Vertice origem;
    public Vertice destino;

    public Aresta(Vertice origem, Vertice destino, int peso) {
        this.peso = peso;
        this.origem = origem;
        this.destino = destino;
    }
    public Aresta(){}
}
