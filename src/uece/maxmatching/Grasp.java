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
	private Vertice vertices[];
	private RandomicoHomogeneo random;
	private List<Aresta> caminhoAumentante = new ArrayList<Aresta>();

	Grasp(Grafo grafo) {
		this.grafo = grafo;
		this.vertices = grafo.vertices;
		this.random = new RandomicoHomogeneo(this.grafo);
	}

	/**
	 * Implementa a meta-heuristica GRASP para o problema 1-matching
	 * 
	 * @param maxIteracoes
	 *            Numero de iteracoes que o algoritmo executara
	 * @return A melhor solucao como um vetor de arestas
	 */
	public Aresta[] computarMaxMatching(int maxIteracoes) {
		EmparelhamentoMaximo emparelhamentos = new EmparelhamentoPorOrdenacao();
		Aresta[] melhorSolucao = null;
		Aresta[] solucao;

		for (int i = 0; i < maxIteracoes; i++) {
			solucao = emparelhamentos.construir(grafo);
			solucao = buscaLocal(solucao);
			melhorSolucao = atualizarSolucao(solucao, melhorSolucao);
			grafo.desemparelhar();
		}

		return melhorSolucao;
	}

	/**
	 * Constroi emparelhamento maximo (ou maximal?) a partir de abordagem gulosa
	 * com caracteristicas aleatorias
	 *
	 * @return Solucao gerada como vetor de arestas
	 */
	private Aresta[] construirSolucao() {

		// emparelhamento perfeito, todos os vertices est�o no grafo
		// enquanto existir caminho de aumento ainda nao foi achado o max
		// matching

		int position = random.gerarNumero();
		
		if(position != -1){
			List<Aresta> result = acharCaminhoAumentante(vertices[0], null);
		}
		
		// ache um vertice livre randomico
		
		
		return null;
	}

	public Boolean ehImpar(List<Aresta> l) {

		int tamanho = l.size();
		if (tamanho % 2 == 0)
			return false;
		else
			return true;
	}

	public List<Aresta> acharCaminhoAumentante(Vertice v, Aresta a) {

		if (caminhoAumentante.size() == 0 && v != null) {

			for (int k = 0; k < v.arestas.length; k++) {
				Aresta aresta = v.arestas[k];

				if (aresta.getStatus() == Status.LIVRE) {
					caminhoAumentante.add(aresta);
				}
			}
		} else if (a != null) {
			caminhoAumentante.add(a);
		}

		// ache um vertice livre e parta dele

		int ultimaPosicao = caminhoAumentante.size() - 1;
		Aresta arestasAdjacentes[] = caminhoAumentante.get(ultimaPosicao).destino.arestas;
		Aresta aresta;

		for (int i = 0; i < arestasAdjacentes.length; i++) {
			aresta = arestasAdjacentes[i];
			
			if (aresta != null && aresta.getStatus() != caminhoAumentante.get(ultimaPosicao).status) {
				acharCaminhoAumentante(null, aresta);
			}
		}

		if (caminhoAumentante.get(0).status == Status.LIVRE	
				&& caminhoAumentante.get(ultimaPosicao).status == Status.LIVRE
				&& ehImpar(caminhoAumentante)){
			
			inserirCaminhoAumento();
			
			return caminhoAumentante;
		} else return null;
	}

	public void inserirCaminhoAumento(){
		for (Aresta aresta : caminhoAumentante) {
			Vertice origem = aresta.origem;
			Vertice destino = aresta.destino;
			
			int x = grafo.retornaPosicaoOrigemAresta(grafo, origem);
			int y = grafo.retornaPosicaoDestinoAresta(grafo, destino);
			Aresta newEdge = grafo.retornaAresta(grafo, x, y);
			newEdge.trocaStatus();
			grafo.vertices[x].arestas[y] = newEdge;
			
			//modificando o status dos vertices tambem
			aresta.origem.trocaStatus();
			aresta.destino.trocaStatus();
		}
	}
	
	private Aresta[] buscaLocal(Aresta[] solucao) {
		if (isNull(solucao)) {
			return null;
		}

		Vizinhanca vizinhanca = new Vizinhanca();

		Aresta[] melhorSolucao = solucao;
		int menorCusto = custoSolucao(solucao);

		for (int i = 0; i < solucao.length - 1; i++) {
			for (int j = i + 1; j < solucao.length; j++) {
				Aresta[] permutacao = vizinhanca.permutaIJ(this.grafo, solucao, i, j);

				int custoPermutacao = custoSolucao(permutacao);
				if (custoPermutacao < menorCusto) {

					menorCusto = custoPermutacao;
					melhorSolucao = permutacao;

					//System.out.println("Melhora: " + menorCusto);
				}

				permutacao = vizinhanca.permutaIJCruzado(this.grafo, solucao, i, j);
				custoPermutacao = custoSolucao(permutacao);
				if (custoPermutacao < menorCusto) {
					menorCusto = custoPermutacao;
					melhorSolucao = permutacao;

					//System.out.println("Melhora cruzada: " + menorCusto);
				}
			}
		}

		return melhorSolucao;
	}

	/**
	 * TODO otimizar
	 * Retorna a melhor de duas solucoes.
	 *
	 * @param solucao1
	 *            Solucao como vetor de arestas
	 * @param solucao2
	 *            Solucao como vetor de arestas
	 *
	 * @return A melhor das duas solucoes como um vetor de arestas
	 */
	private Aresta[] atualizarSolucao(Aresta[] solucao1, Aresta[] solucao2) {
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
	 * 
	 * @param solucao
	 *            Solucao como vetor de arestas
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

	static boolean isNull(Object o) {
		return o == null;
	}
}
