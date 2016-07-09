package uece.maxmatching;

/**
 * Created by jeffrodrigo on 06/07/16.
 *
 * Representa um grafo
 */
class Grafo {
    public final int N;
    public final Vertice[] vertices;

    Grafo(int ordem){
        this.N = ordem;
        this.vertices = new Vertice[this.N];

        for (int i = 0; i < this.N; i++) {
            this.vertices[i] = new Vertice(this.N);
        }
    }

    /**
     * Retorna uma aresta, dados seus vertices de origem e destino
     *
     * @param grafo
     * @param origem
     * @param destino
     * @return Aresta com a origem e destino passados por parametro
     */
    public static Aresta retornaAresta(Grafo grafo, int origem, int destino) {
        if (origem <= destino)
            return grafo.vertices[origem].arestas[destino];
        else
            return grafo.vertices[destino].arestas[origem];
    }

}
