package uece.maxmatching;

import java.util.ArrayList;
import java.util.List;

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
	 * @return A melhor solucao como um vetor de arestas
	 */
	public Aresta[] computarMaxMatching(int maxIteracoes) {
		EmparelhamentoMaximo emparelhamentos = new EmparelhamentoPorOrdenacao();
		Aresta[] melhorSolucao = null;
		Aresta[] solucao;

		for (int i = 0; i < maxIteracoes; i++) {
			solucao = emparelhamentos.construir(grafo);
			solucao = buscaLocal(solucao);
			melhorSolucao = atualizarSolucao(solucao, melhorSolucao, i);
			grafo.desemparelhar();
		}

		return melhorSolucao;
	}

	private Aresta[] buscaLocal(Aresta[] solucao) {
		Vizinhanca vizinhanca = new Vizinhanca(grafo);

		Aresta[] melhor = vizinhanca.melhorVizinhoTriplo(solucao);

		return melhor;
	}

	/**
	 * Retorna a melhor de duas solucoes.
	 */
	private Aresta[] atualizarSolucao(Aresta[] solucao1, Aresta[] solucao2, int iteracao) {
		int custo1 = custoSolucao(solucao1);
		int custo2 = custoSolucao(solucao2);

		if (custo1 < custo2) {
			System.out.println("Custo: " + custo1);
			return solucao1;
		}
		return solucao2;
	}

	/**
	 * Calcula o custo das arestas de uma solucao
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

	static boolean isNull(Object o) {
		return o == null;
	}
}
