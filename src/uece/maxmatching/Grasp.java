package uece.maxmatching;

/**
 * Created by jeffrodrigo on 06/07/16.
 *
 * Classe que implementa a meta-heuristica GRASP para o problema 1-matching.
 */

class Grasp {
    private final Grafo grafo;

    Grasp(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Implementa a meta-heuristica GRASP para o problema 1-matching
     * @param maxIteracoes Numero de iteracoes que o algoritmo executara
     * @return A melhor solucao como um vetor de arestas
     */
    public Aresta[] computarMaxMatching(int maxIteracoes) {
        Aresta[] melhorSolucao = null;
        Aresta[] solucao;

        for (int i = 0; i < maxIteracoes; i++) {
            solucao = construirSolucao();
            solucao = buscaLocal(solucao);
            melhorSolucao = atualizarSolucao(solucao, melhorSolucao);
        }

        return melhorSolucao;
    }

    /**
     * Constroi emparelhamento maximo (ou maximal?) a partir de abordagem
     * gulosa com caracteristicas aleatorias
     *
     * @return Solucao gerada como vetor de arestas
     */
    private Aresta[] construirSolucao() {
    	
    	// emparelhamento perfeito, todos os vertices estão no grafo
    	// enquanto existir caminho de aumento ainda nao foi achado o max matching

    	
        return null;
    }

    private Aresta[] buscaLocal(Aresta[] solucao) {
        if (isNull(solucao)){
            return null;
        }

        Aresta[] melhorSolucao = solucao;
        int menorCusto = custoSolucao(solucao);

        for (int i = 0; i < solucao.length - 1; i++) {
            for (int j = i+1; j < solucao.length; j++) {
                Aresta[] permutacao = permutaIJ(solucao, i, j);

                int custoPermutacao = custoSolucao(permutacao);
                if (custoPermutacao < menorCusto) {
                    menorCusto = custoPermutacao;
                    melhorSolucao = permutacao;
                }
            }
        }

        return melhorSolucao;
    }

    /**
     * Retorna a melhor de duas solucoes.
     *
     * @param solucao1 Solucao como vetor de arestas
     * @param solucao2 Solucao como vetor de arestas
     *
     * @return A melhor das duas solucoes como um vetor de arestas
     */
    private Aresta[] atualizarSolucao(Aresta[] solucao1, Aresta[] solucao2) {
        if (custoSolucao(solucao2) < custoSolucao(solucao1))
            return solucao2;
        return solucao1;
    }


    /**
     * Permuta os vertices de duas arestas vizinhas.
     *
     * Une a origem de uma aresta com a origem da aresta vizinha,
     * e une o destino de uma aresta com o destino da aresta vizinha.
     *
     * Ou seja,
     *                   i  i+1               i   i+1
     * transforma {..., ab, cd, ...} em {..., ac, bd, ...}
     *
     * @param solucao Solucao que tera arestas perturbadas
     * @param i posicao da aresta cujos verticies serao permutados com os da aresta vizinha
     * @return nova solucao gerada a partir da permutacao dos vertices duas arestas vizinhas
     */
    private Aresta[] permuta1(Aresta[] solucao, int i){
        Aresta a, b;
        a = this.grafo.vertices[solucao[i].origem.nome].arestas[solucao[i + 1].origem.nome];
        b = this.grafo.vertices[solucao[i].destino.nome].arestas[solucao[i + 1].destino.nome];

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

    /**
     * Permuta os vertices de duas arestas I e J.
     *
     * Une a origem da aresta I com a origem da aresta J,
     * e une o destino da aresta I com o destino da aresta J.
     *
     * Ou seja,
     *                   i        j                 i        j
     * transforma {..., ab, ..., cd, ...} em {..., ac, ..., bd, ...}
     *
     * @param solucao Solucao que tera arestas perturbadas
     * @param i posicao da aresta I
     * @param j posicao da aresta J
     * @return nova solucao gerada a partir da permutacao dos vertices das duas arestas
     */
    private Aresta[] permutaIJ(Aresta[] solucao, int i, int j) {
        Aresta a, b;

        a = this.grafo.vertices[solucao[i].origem.nome].arestas[solucao[j].origem.nome];
        b = this.grafo.vertices[solucao[i].destino.nome].arestas[solucao[j].destino.nome];

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
     * Calcula o custo das arestas de uma solucao
     * @param solucao Solucao como vetor de arestas
     * @return custo total
     */
    public static int custoSolucao(Aresta[] solucao) {
        int custo = 0;

        if (isNull(solucao))
            return 999999;

        for (Aresta aresta : solucao) {
            custo += aresta.peso;
        }
        return custo;
    }

    /**
     * Verifica se um objeto eh null
     * @param o Objeto
     * @return true se o objeto eh null, false caso contrario
     */
    static boolean isNull(Object o) {
        return o == null;
    }
}
