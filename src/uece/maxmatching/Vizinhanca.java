package uece.maxmatching;

/**
 * Created by jeffrodrigo on 13/07/16.
 */

public class Vizinhanca {

    /**
     * Permuta os vertices de duas arestas I e J.
     *
     * Une a origem da aresta I com a origem da aresta J, e une o destino da
     * aresta I com o destino da aresta J.
     *
     * Ou seja, transforma {..., ab, ..., cd, ...} em {..., ac, ..., bd, ...}
     *
     * @param solucao
     *            Solucao que tera arestas perturbadas
     * @param i
     *            posicao da aresta I
     * @param j
     *            posicao da aresta J
     * @return nova solucao gerada a partir da permutacao dos vertices das duas
     *         arestas
     *
     */
    public Aresta[] permutaIJ(Grafo grafo, Aresta[] solucao, int i, int j) {
        Aresta a, b;

        a = Grafo.retornaAresta(grafo, solucao[i].origem.retornaIndice(),
                solucao[j].origem.retornaIndice());

        b = Grafo.retornaAresta(grafo, solucao[i].destino.retornaIndice(),
                solucao[j].destino.retornaIndice());

		/*
		a.status = Status.SATURADO;
		b.status = Status.SATURADO;

		solucao[i].status = Status.LIVRE;
		solucao[j].status = Status.LIVRE;
		*/

        if (a.origem.nome == a.destino.nome)
            return null;
        if (b.origem.nome == b.destino.nome)
            return null;

        Aresta[] permutacao = new Aresta[solucao.length];
        for (int k = 0; k < solucao.length; k++) {
            if (k == i)
                permutacao[k] = a;
            else if (k == j)
                permutacao[k] = b;
            else
                permutacao[k] = solucao[k];
        }

        return permutacao;
    }

    public Aresta[] permutaIJCruzado(Grafo grafo, Aresta[] solucao, int i, int j) {
        Aresta a, b;

        a = Grafo.retornaAresta(grafo, solucao[i].origem.retornaIndice(),
                solucao[j].destino.retornaIndice());

        b = Grafo.retornaAresta(grafo, solucao[i].destino.retornaIndice(),
                solucao[j].origem.retornaIndice());

		/*
		a.status = Status.SATURADO;
		b.status = Status.SATURADO;

		solucao[i].status = Status.LIVRE;
		solucao[j].status = Status.LIVRE;
		*/
        Aresta[] permutacao = new Aresta[solucao.length];
        for (int k = 0; k < solucao.length; k++) {
            if (k == i)
                permutacao[k] = a;
            else if (k == j)
                permutacao[k] = b;
            else
                permutacao[k] = solucao[k];
        }

        return permutacao;
    }

    /**
     * Permuta os vertices de duas arestas vizinhas.
     *
     * Une a origem de uma aresta com a origem da aresta vizinha, e une o
     * destino de uma aresta com o destino da aresta vizinha.
     *
     * Ou seja, transforma {..., ab, cd, ...} em {..., ac, bd, ...}
     *
     * @param solucao
     *            Solucao que tera arestas perturbadas
     * @param i
     *            posicao da aresta cujos verticies serao permutados com os da
     *            aresta vizinha
     * @return nova solucao gerada a partir da permutacao dos vertices duas
     *         arestas vizinhas
     */
    public Aresta[] permuta1(Grafo grafo, Aresta[] solucao, int i) {
        Aresta a, b;

        a = Grafo.retornaAresta(grafo, solucao[i].origem.retornaIndice(),
                solucao[i + 1].origem.retornaIndice());

        b = Grafo.retornaAresta(grafo, solucao[i].destino.retornaIndice(),
                solucao[i + 1].destino.retornaIndice());

        Aresta[] permutacao = new Aresta[solucao.length];
        for (int k = 0; k < solucao.length; k++) {
            if (k == i)
                permutacao[k] = a;
            else if (k == i + 1)
                permutacao[k] = b;
            else
                permutacao[k] = solucao[k];
        }

        return permutacao;
    }

}
