package uece.maxmatching;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
    	
    	System.out.println("Digite o numero de vertices e os pesos das arestas");
        
    	Scanner in = new Scanner(System.in);

        int N = in.nextInt();

	    Grafo grafo = new Grafo(N);

        // Montagem do grafo
        for (int i = 0; i < N; i++) {
            grafo.vertices[i].nome = i;
            /*
             * Arestas que ligam cada vertice I a um vertice J
             * e aresta que liga um vertice I a ele mesmo
             */

            for (int j = i + 1; j < N; j++){
                grafo.vertices[i].arestas[j] = new Aresta(grafo.vertices[i], grafo.vertices[j], in.nextInt());
            }

            grafo.vertices[i].arestas[i] = new Aresta(grafo.vertices[i], grafo.vertices[i], 0);
        }

        Grasp grasp = new Grasp(grafo);
        Aresta[] solucao = grasp.computarMaxMatching(1000);

        if (Grasp.isNull(solucao)) {
            System.out.println("Nenhuma solucao encontrada!!!\n");
            return;
        }

        System.out.println("Solucao:\n");
        for (Aresta aresta : solucao) {
            System.out.println(aresta.origem.nome + 1 +
                    " -> " + aresta.destino.nome + 1 + "\n");
        }
        System.out.println("Custo: " + Grasp.custoSolucao(solucao));
    }
}
