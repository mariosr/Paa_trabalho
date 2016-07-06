package br.uece.paa.maxmatching;

public class Main {

    public static void main(String[] args) {
        int N = 10;
        int nArestas = (N * (N - 1)) / 2;

        Vertice[] vertices = new Vertice[N];
        Aresta[] arestas = new Aresta[nArestas];

	    Grafo grafo = new Grafo(N);
        Grasp grasp = new Grasp(grafo);

        grasp.computarMaxMatching(1000);

        System.out.println("OK\n");
    }
}
