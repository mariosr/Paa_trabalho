package uece.maxmatching;

import java.util.Random;

/**
 * Created by jeffrodrigo on 12/07/16.
 */
class EmparelhamentoPorOrdenacao implements EmparelhamentoMaximo {
    @Override
    public Aresta[] construir(Grafo grafo) {
        int limiteInferior = 0;
        int maxEmparelhamentos = grafo.N / 2;
        Random gerador = new Random();

        Aresta solucao[]  = grafo.retornaArestasOdenadas();

        // Selecionar aleatoriamente aresta no range [arestaInicial, arestaFinal]
        while (maxEmparelhamentos > 0) {
            int tamSubconjunto = 1 + (int)((1 - Math.pow(0.1, (limiteInferior + 1))) * maxEmparelhamentos);

            int limiteSuperior = gerador.nextInt(tamSubconjunto);

            int arestaSelecionada = proximaAresta(solucao, limiteInferior, 1 + limiteSuperior);
            solucao[arestaSelecionada].emparelhar();

            limiteInferior++;
            maxEmparelhamentos--;
        }

        return retornaEmparelhamentos(solucao, grafo.N/2);
    }

    private int proximaAresta(Aresta[] solucao, int arestaInicial, int maxArestasLivres) {
        int pos;
        int numArestasLivres = 0;
        int ultimaAresta = solucao.length;

        for (pos = arestaInicial; pos < ultimaAresta - 1; pos++) {
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
