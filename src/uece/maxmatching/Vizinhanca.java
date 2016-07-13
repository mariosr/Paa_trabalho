package uece.maxmatching;

/**
 * Created by jeffrodrigo on 13/07/16.
 */

public class Vizinhanca {
    private final Grafo grafo;

    Vizinhanca(Grafo grafo){
        this.grafo = grafo;
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

    public Aresta[] melhorVizinhoTriplo(Aresta[] solucao) {
        Aresta[] permutacao, melhor = solucao;
        int custoPermutacao, menorCusto = Grasp.custoSolucao(solucao);

        System.out.println("Custo inicial: " + menorCusto);

        // Permutacoes dois a dois
        for (int i = 0; i < solucao.length - 1; i++) {
            for (int j = i + 1; j < solucao.length; j++) {
                permutacao = permutacoesDuplas(solucao, i, j);

                custoPermutacao = Grasp.custoSolucao(permutacao);
                if (custoPermutacao < menorCusto) {
                    melhor = permutacao;
                    menorCusto = custoPermutacao;
                }
            }
        }

        //permutacoes tres a tres
        for (int p = 0; p < solucao.length - 2; p++) {
            for (int q = p + 1; q < solucao.length - 1; q++) {
                for (int r = q + 1; r < solucao.length; r++) {
                    permutacao = permutacoesTriplas(solucao, p, q, r);

                    custoPermutacao = Grasp.custoSolucao(permutacao);
                    if (custoPermutacao < menorCusto) {
                        melhor = permutacao;
                        menorCusto = custoPermutacao;
                    }
                }
            }
        }

        return melhor;
    }

    private Aresta[] permutacoesDuplas(Aresta[] solucao, int p, int q) {
        Aresta[] melhor = solucao;
        int menorCusto = Grasp.custoSolucao(solucao);

        Aresta[] permutacao;
        int custoPermutacao;

        permutacao = permutacaoDuplaSimples(solucao, p, q);
        custoPermutacao = Grasp.custoSolucao(permutacao);
        if (custoPermutacao < menorCusto) {
            melhor = permutacao;
            menorCusto = custoPermutacao;
        }

        permutacao = permutacaoDuplaCruzada(solucao, p, q);
        custoPermutacao = Grasp.custoSolucao(permutacao);
        if (custoPermutacao < menorCusto)
            melhor = permutacao;

        return melhor;
    }

    private Aresta[] permutacaoDuplaSimples(Aresta[] solucao, int i, int j) {
        Aresta arestaI = Grafo.retornaAresta(grafo, solucao[i].origem, solucao[j].origem);
        Aresta arestaJ = Grafo.retornaAresta(grafo, solucao[i].destino, solucao[j].destino);

        Aresta[] permutacao = new Aresta[solucao.length];
        for (int k = 0; k < solucao.length; k++)
            permutacao[k] = solucao[k];
        permutacao[i] = arestaI;
        permutacao[j] = arestaJ;

        return permutacao;
    }

    private Aresta[] permutacaoDuplaCruzada(Aresta[] solucao, int i, int j) {
        Aresta arestaI = Grafo.retornaAresta(grafo, solucao[i].origem, solucao[j].destino);
        Aresta arestaJ = Grafo.retornaAresta(grafo, solucao[i].destino, solucao[j].origem);

        Aresta[] permutacao = new Aresta[solucao.length];
        for (int k = 0; k < solucao.length; k++)
            permutacao[k] = solucao[k];
        permutacao[i] = arestaI;
        permutacao[j] = arestaJ;

        return permutacao;
    }

    private Aresta[] permutacoesTriplas(Aresta[] solucao, int p, int q, int r) {
        Aresta[] permutacao;
        Aresta[] melhor = solucao;
        int custoPermutacao, menorCusto = Grasp.custoSolucao(solucao);

        Aresta A = solucao[p];
        Aresta B = solucao[q];
        Aresta C = solucao[r];

        Vertice a, b, c, d, e, f;
        a = A.origem;
        b = A.destino;

        c = B.origem;
        d = B.destino;

        e = C.origem;
        f = C.destino;

        permutacao = montarPermutacaoTripla(solucao, p, q, r,
                a, c, b, e, d, f);
        custoPermutacao = Grasp.custoSolucao(permutacao);
        if (custoPermutacao < menorCusto) {
            melhor = permutacao;
            menorCusto = custoPermutacao;
        }

        permutacao = montarPermutacaoTripla(solucao, p, q, r,
                a, c, b, f, d, e);
        custoPermutacao = Grasp.custoSolucao(permutacao);
        if (custoPermutacao < menorCusto) {
            melhor = permutacao;
            menorCusto = custoPermutacao;
        }

        for (int i = 0; i < permutacao.length - 1; i++) {
            for (int j = i + 1; j < permutacao.length; j++) {
                permutacao = permutacoesDuplas(permutacao, i, j);

                custoPermutacao = Grasp.custoSolucao(permutacao);
                if (custoPermutacao < menorCusto) {
                    melhor = permutacao;
                    menorCusto = custoPermutacao;
                }
            }
        }



        permutacao = montarPermutacaoTripla(solucao, p, q, r,
                a, d, b, e, c, f);
        custoPermutacao = Grasp.custoSolucao(permutacao);
        if (custoPermutacao < menorCusto) {
            melhor = permutacao;
            menorCusto = custoPermutacao;
        }

        for (int i = 0; i < permutacao.length - 1; i++) {
            for (int j = i + 1; j < permutacao.length; j++) {
                permutacao = permutacoesDuplas(permutacao, i, j);

                custoPermutacao = Grasp.custoSolucao(permutacao);
                if (custoPermutacao < menorCusto) {
                    melhor = permutacao;
                    menorCusto = custoPermutacao;
                }
            }
        }



        permutacao = montarPermutacaoTripla(solucao, p, q, r,
                a, d, b, f, c, e);
        custoPermutacao = Grasp.custoSolucao(permutacao);
        if (custoPermutacao < menorCusto) {
            melhor = permutacao;
            menorCusto = custoPermutacao;
        }

        for (int i = 0; i < permutacao.length - 1; i++) {
            for (int j = i + 1; j < permutacao.length; j++) {
                permutacao = permutacoesDuplas(permutacao, i, j);

                custoPermutacao = Grasp.custoSolucao(permutacao);
                if (custoPermutacao < menorCusto) {
                    melhor = permutacao;
                    menorCusto = custoPermutacao;
                }
            }
        }



        permutacao = montarPermutacaoTripla(solucao, p, q, r,
                a, e, b, c, d, f);
        custoPermutacao = Grasp.custoSolucao(permutacao);
        if (custoPermutacao < menorCusto) {
            melhor = permutacao;
            menorCusto = custoPermutacao;
        }

        for (int i = 0; i < permutacao.length - 1; i++) {
            for (int j = i + 1; j < permutacao.length; j++) {
                permutacao = permutacoesDuplas(permutacao, i, j);

                custoPermutacao = Grasp.custoSolucao(permutacao);
                if (custoPermutacao < menorCusto) {
                    melhor = permutacao;
                    menorCusto = custoPermutacao;
                }
            }
        }



        permutacao = montarPermutacaoTripla(solucao, p, q, r,
                a, e, b, d, c, f);
        custoPermutacao = Grasp.custoSolucao(permutacao);
        if (custoPermutacao < menorCusto) {
            melhor = permutacao;
            menorCusto = custoPermutacao;
        }

        for (int i = 0; i < permutacao.length - 1; i++) {
            for (int j = i + 1; j < permutacao.length; j++) {
                permutacao = permutacoesDuplas(permutacao, i, j);

                custoPermutacao = Grasp.custoSolucao(permutacao);
                if (custoPermutacao < menorCusto) {
                    melhor = permutacao;
                    menorCusto = custoPermutacao;
                }
            }
        }



        permutacao = montarPermutacaoTripla(solucao, p, q, r,
                a, f, b, c, d, e);
        custoPermutacao = Grasp.custoSolucao(permutacao);
        if (custoPermutacao < menorCusto) {
            melhor = permutacao;
            menorCusto = custoPermutacao;
        }

        for (int i = 0; i < permutacao.length - 1; i++) {
            for (int j = i + 1; j < permutacao.length; j++) {
                permutacao = permutacoesDuplas(permutacao, i, j);

                custoPermutacao = Grasp.custoSolucao(permutacao);
                if (custoPermutacao < menorCusto) {
                    melhor = permutacao;
                    menorCusto = custoPermutacao;
                }
            }
        }



        permutacao = montarPermutacaoTripla(solucao, p, q, r,
                a, f, b, d, c, e);
        custoPermutacao = Grasp.custoSolucao(permutacao);
        if (custoPermutacao < menorCusto) {
            melhor = permutacao;
            menorCusto = custoPermutacao;
        }

        for (int i = 0; i < permutacao.length - 1; i++) {
            for (int j = i + 1; j < permutacao.length; j++) {
                permutacao = permutacoesDuplas(permutacao, i, j);

                custoPermutacao = Grasp.custoSolucao(permutacao);
                if (custoPermutacao < menorCusto) {
                    melhor = permutacao;
                    menorCusto = custoPermutacao;
                }
            }
        }


        return melhor;
    }

    private Aresta[] montarPermutacaoTripla(
            Aresta[] solucao,
            int p, int q, int r,
            Vertice origemA, Vertice destinoA,
            Vertice origemB, Vertice destinoB,
            Vertice origemC, Vertice destinoC)
    {
        // copiar solucao
        Aresta[] permutacao = new Aresta[solucao.length];
        for (int i = 0; i < solucao.length; i++)
            permutacao[i] = solucao[i];

        Aresta arestaA = Grafo.retornaAresta(grafo, origemA, destinoA);
        Aresta arestaB = Grafo.retornaAresta(grafo, origemB, destinoB);
        Aresta arestaC = Grafo.retornaAresta(grafo, origemC, destinoC);

        solucao[p] = arestaA;
        solucao[q] = arestaB;
        solucao[r] = arestaC;

        return permutacao;
    }

}
