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
}
