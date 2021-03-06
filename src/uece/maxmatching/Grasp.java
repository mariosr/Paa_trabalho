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

		Aresta[] melhorSolucao = solucao;
		int menorCusto = custoSolucao(solucao);

		for (int i = 0; i < solucao.length - 1; i++) {
			for (int j = i + 1; j < solucao.length; j++) {
				Aresta[] permutacao = permutaIJ(solucao, i, j);

				int custoPermutacao = custoSolucao(permutacao);
				if (custoPermutacao < menorCusto) {
					menorCusto = custoPermutacao;
					melhorSolucao = permutacao;
				}
			}
		}

		/*
		for (int i = 0; i < solucao.length - 1; i++) {
			for (int j = i + 1; j < solucao.length; j++) {
				Aresta[] permutacao = permutaIJCruzado(solucao, i, j);

				int custoPermutacao = custoSolucao(permutacao);
				if (custoPermutacao < menorCusto) {
					menorCusto = custoPermutacao;
					melhorSolucao = permutacao;
				}
			}
		}
		*/


		return melhorSolucao;
	}

	private Aresta[] permutaIJCruzado(Aresta[] solucao, int i, int j) {
		Aresta a, b;

		a = Grafo.retornaAresta(this.grafo, solucao[i].origem.retornaIndice(),
				solucao[j].destino.retornaIndice());

		b = Grafo.retornaAresta(this.grafo, solucao[i].destino.retornaIndice(),
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
		if (custoSolucao(solucao2) < custoSolucao(solucao1)) {
			return solucao2;
		}
		return solucao1;
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
	private Aresta[] permuta1(Aresta[] solucao, int i) {
		Aresta a, b;

		a = Grafo.retornaAresta(this.grafo, solucao[i].origem.retornaIndice(),
				solucao[i + 1].origem.retornaIndice());

		b = Grafo.retornaAresta(this.grafo, solucao[i].destino.retornaIndice(),
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
	 */
	private Aresta[] permutaIJ(Aresta[] solucao, int i, int j) {
		Aresta a, b;

		a = Grafo.retornaAresta(this.grafo, solucao[i].origem.retornaIndice(),
				solucao[j].origem.retornaIndice());

		b = Grafo.retornaAresta(this.grafo, solucao[i].destino.retornaIndice(),
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
