package uece.maxmatching;

import java.util.Random;

/**
 * Created by jeffrodrigo on 12/07/16.
 */
class EmparelhamentoPorOrdenacao implements EmparelhamentoMaximo {
    @Override
    public Aresta[] construir(Grafo grafo) {
        int maxEmparelhamentos = grafo.N / 2;
        Random gerador = new Random();
        int enesimaArestaLivre;

        int nArestasLivres = (grafo.N * (grafo.N - 1))/2;
        int tamSubconjunto;

        Aresta solucao[]  = grafo.retornaArestasOdenadas();

        int i = 0;
        // Selecionar aleatoriamente aresta no range [0, arestaFinal]
        while (maxEmparelhamentos > 0) {
            double alfa = 0.75;
            //int tamSubconjunto = (int)((1 - Math.pow(alfa, i+1)) * maxEmparelhamentos);
            //tamSubconjunto = (maxEmparelhamentos);
            //tamSubconjunto = (int)(0.1 * nArestasLivres);

            tamSubconjunto = (int)((1 - Math.pow(alfa, i+1)) * nArestasLivres);

            if (tamSubconjunto > 0)
                enesimaArestaLivre = gerador.nextInt(tamSubconjunto);
            else
                enesimaArestaLivre = 0;

            int novaAresta = proximaAresta(solucao, enesimaArestaLivre + 1);
            solucao[novaAresta].emparelhar();

            int reducao = 0;

            for (int k = 0; k < grafo.N; k++) {
                int p = solucao[novaAresta].origem.nome;
                int q = solucao[novaAresta].destino.nome;

                if (k == p) {
                    continue;
                }
                else {
                    if (!grafo.vertices[k].emparelhado()) {
                        reducao++;
                    }
                }

                if (k == q) {
                    continue;
                }
                else {
                    if (!grafo.vertices[k].emparelhado()) {
                        reducao++;
                    }
                }

            }

            nArestasLivres -= (reducao + 1);

            maxEmparelhamentos--;
            i++;
        }

        return retornaEmparelhamentos(solucao, grafo.N/2);
    }

    private int proximaAresta(Aresta[] solucao, int maxArestasLivres) {
        int pos;
        int numArestasLivres = 0;
        int ultimaAresta = solucao.length;

        for (pos = 0; pos < ultimaAresta - 1; pos++) {
            if (!solucao[pos].temArestaEmparelhada())
                numArestasLivres++;

            if (numArestasLivres >= maxArestasLivres)
                break;
        }

        return pos;
    }

    private Aresta[] retornaEmparelhamentos(Aresta[] arestas, int numArestas){
        Aresta[] saida = new Aresta[numArestas];

        int j = 0;
        for (int i = 0; i < arestas.length; i++) {
            if (arestas[i].emparelhada()) {
                saida[j] = arestas[i];

                if (saida[j].origem.nome == saida[j].destino.nome)
                    System.out.println("Adicionando aresta zerada");

                j++;

            }
        }

        return saida;
    }
}
