
A minha estrat�gia seria escolher esse v�rtice aleat�rio, encontrar uma diagonal e usar essa diagonal para particionar o pol�gono ao meio, gerando dois novos pol�gonos menores e assim eu chamaria recursivamente a fun��o para ir particionando at� chegar ao caso base, ou seja, quando o pol�gono tiver 3 v�rtices (tri�ngulo), ent�o eu calcularia a �rea desse triangulo e salvaria em uma estrutura (por exemplo, uma matriz) para ao final verificar qual a menor �rea registrada, consequentemente a menor �rea seria o menor tri�ngulo do pol�gono.

OBS: Olhar figura inducao.png