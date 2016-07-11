package uece.maxmatching;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		Aresta[] melhorSolucao = null;
		Aresta[] solucao;

		for (int i = 0; i < maxIteracoes; i++) {
			solucao = construirSolucao2();

			if (isNull(solucao))
				continue;

			solucao = buscaLocal(solucao);
			melhorSolucao = atualizarSolucao(solucao, melhorSolucao);
			grafo.desemparelhar();
		}

		return melhorSolucao;
	}

	private Aresta[] construirSolucao2() {
		int arestaInicial = 0;
		int arestaFinal = 0;
		int emparelhamentos = 0;
		int maxEmparelhamentos = grafo.N / 2;
		Random gerador = new Random();

		Aresta solucao[]  = grafo.retornaArestasOdenadas();
		int numArestasRestantes = solucao.length;
		int emparelhamentosRestantes = maxEmparelhamentos;

		// Selecionar aleatoriamente aresta no range [arestaInicial, arestaFinal]
		while (emparelhamentos < maxEmparelhamentos) {
			int tamSubconjunto = 1 + (int)(0.30 * emparelhamentosRestantes);

			int num = gerador.nextInt(tamSubconjunto);

			arestaFinal = proximaAresta(solucao, arestaInicial, 1 + num);

			solucao[arestaFinal].emparelhar();
			emparelhamentos++;

			arestaInicial++;
			emparelhamentosRestantes--;
		}


		return retornaEmparelhamentos(solucao);
	}

	private int proximaAresta(Aresta[] solucao, int arestaInicial, int maxArestasLivres) {
		int posicao;
		int numArestasLivres = 0;
		int arestasContabilizadas = 0;

		for (posicao = arestaInicial; posicao < solucao.length; posicao++) {
			if (!solucao[posicao].temArestaEmparelhada()) {
				numArestasLivres++;
			}

			if (numArestasLivres >= maxArestasLivres)
				break;
		}

		if (posicao == solucao.length)
			return posicao - 1;
		return posicao;
	}

	private Aresta[] retornaEmparelhamentos(Aresta[] arestas){
		Aresta[] saida = new Aresta[grafo.N/2];

		int j = 0;
		for (int i = 0; i < arestas.length; i++) {
			if (arestas[i].emparelhada()){
				saida[j] = arestas[i];
				j++;
			}
		}

		return saida;
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
		if (custoSolucao(solucao2) < custoSolucao(solucao1))
			return solucao2;
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

		a.status = Status.SATURADO;
		b.status = Status.SATURADO;

		solucao[i].status = Status.LIVRE;
		solucao[j].status = Status.LIVRE;

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
