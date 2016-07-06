package br.uece.paa.maxmatching;

/**
 * Created by jeffrodrigo on 06/07/16.
 */
public class Grasp {
    Grafo grafo;

    Grasp(Grafo grafo) {
        this.grafo = grafo;
    }

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

    static Aresta[] construirSolucao() {
        return null;
    }

    static Aresta[] buscaLocal(Aresta[] solucao) {
        return null;
    }

    static Aresta[] atualizarSolucao(Aresta[] solucao1, Aresta[] solucao2) {
        return null;
    }

}
