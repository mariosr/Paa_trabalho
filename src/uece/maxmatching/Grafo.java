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

}
