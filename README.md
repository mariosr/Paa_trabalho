
A minha estratégia seria escolher esse vértice aleatório, encontrar uma diagonal e usar essa diagonal para particionar o polígono ao meio, gerando dois novos polígonos menores e assim eu chamaria recursivamente a função para ir particionando até chegar ao caso base, ou seja, quando o polígono tiver 3 vértices (triângulo), então eu calcularia a área desse triangulo e salvaria em uma estrutura (por exemplo, uma matriz) para ao final verificar qual a menor área registrada, consequentemente a menor área seria o menor triângulo do polígono.

OBS: Olhar figura inducao.png