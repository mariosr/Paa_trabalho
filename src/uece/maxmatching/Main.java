package uece.maxmatching;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
    	
    	System.out.println("Digite o numero de vertices e os pesos das arestas");
	    Grafo grafo = Main.lerGrafo();

        Grasp grasp = new Grasp(grafo);
        Aresta[] solucao = grasp.computarMaxMatching(10000);

        if (Grasp.isNull(solucao)) {
            System.out.println("Nenhuma solucao encontrada!!!\n");
            return;
        }

        System.out.println("Solucao:\n");
        for (Aresta aresta : solucao) {
            int origem = aresta.origem.nome + 1;
            int destino = aresta.destino.nome + 1;
            int peso = aresta.peso;
            System.out.println(origem + "->" + destino + "(" + peso + ")\n");
        }
        System.out.println("Custo: " + Grasp.custoSolucao(solucao));
    }

    public static Grafo lerGrafo() {
        Scanner in = new Scanner(System.in);
        int numVertices = in.nextInt();
        Grafo grafo = new Grafo(numVertices);

        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++){
                grafo.vertices[i].arestas[j] = new Aresta(grafo.vertices[i], grafo.vertices[j], in.nextInt());
            }
            grafo.vertices[i].arestas[i] = new Aresta(grafo.vertices[i], grafo.vertices[i], 0);
        }

        return grafo;
    }
}
