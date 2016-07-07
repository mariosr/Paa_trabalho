package br.uece.paa.maxmatching;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int N = in.nextInt();

	    Grafo grafo = new Grafo(N);

        // Montagem do grafo
        for (int i = 0; i < N - 1; i++) {
            grafo.vertices[i].nome = i;
            for (int j = i + 1; j < N; j++){
                grafo.vertices[i].arestas[j] = new Aresta();
                grafo.vertices[i].arestas[j].origem = grafo.vertices[i];
                grafo.vertices[i].arestas[j].destino = grafo.vertices[j];
                grafo.vertices[i].arestas[j].peso = in.nextInt();
            }
        }

        Grasp grasp = new Grasp(grafo);
        Aresta[] solucao = grasp.computarMaxMatching(1000);

        if (Grasp.isNull(solucao)) {
            System.out.println("Nenhuma solucao encontrada\n");
            return;
        }

        System.out.println("Solucao:\n");
        for (Aresta aresta : solucao) {
            System.out.println("" + aresta.origem.nome + 1 +
                    " -> " + aresta.destino.nome + 1 + "\n");
        }
        System.out.println("Custo: " + Grasp.custoSolucao(solucao));
    }
}
