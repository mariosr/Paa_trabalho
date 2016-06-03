package uece.questao1;

public class Triangulacao {
	
	private static int matriz[][] = new int[6][6];

	private static double matriz2[][] = new double[6][6];
	
	private static String verticesT = "";
	private static Double verticesTArea = 0.0;
	
	public static void triangulacaoMinima(int verticesX[], int a, int b){
		
		int n = verticesX.length;
		//System.out.println(n);
		//caso base
		if(n == 3 || (b-a) < 3){
		// tem zero diagonais e um triangulo, retorne a area do poligono
		//System.out.println("chegou no caso base");
			
		matriz2[a][b] = calculaAreaTriangulo(verticesX,a,b);		
		
		}else if(n >= 4){
		
			int verticesAux[] = getVectorFromUntil(verticesX, a, b);
			
				for (int k = a; k < b; k++) {
					int vDiagonal = selecionaDiagonal(verticesAux,k);
				
					if(matriz2[k][vDiagonal] != 0.0){
						System.out.println("ja tem matriz["+k +"]["+vDiagonal+"];");
					}else{

						System.out.println("triangulacao("+k+","+vDiagonal+");");
						System.out.println("triangulacao("+vDiagonal+","+k+");");
						
						triangulacaoMinima(verticesAux, k, vDiagonal);
						triangulacaoMinima(verticesAux, vDiagonal, k);
					}
			}			
		}
	}
	public static int[] getVectorFromUntil(int vet1[], int a, int b){
		
		int tamanhoNovoVetor = (b-a)+1;
		int vet2[] = null;
		int contador = 0;
		
		if(tamanhoNovoVetor > 0){
			vet2 = new int[tamanhoNovoVetor];
			//Arrays.fill(vet2, 0);
			for (int i = a; i <= b; i++) {
				vet2[contador] = vet1[i];
				contador++;
			}
		}
		
		return vet2;
	}

	public static double calculaAreaTriangulo(int vertices[], int x, int y){
		
		double dAB,dBC,dAC, semip,perimetro;
		int a=0,b=0,c=0;
		double area = 0.0;
		
		if(x < y){
			a = vertices[x];
			b = vertices[x+1];
			c = vertices[y];
		}else if(y==0){
			a = vertices[x];
			b = vertices[x+1];
			c = vertices[y];			
		}else{
			a = vertices[x];
			b = vertices[0];
			c = vertices[y];	
		}
		
		dAB = matriz[a][b];
		dBC = matriz[b][c];
		dAC = matriz[a][c];
		
		//condicao existencia do triangulo a > b + c == false
		if((dAB >= (dBC + dAC)) || (dBC>= (dAB + dAC)) || (dAC >= (dBC + dAB))){
			System.out.println("Esse triangulo nao satisfaz a condicao de existencia...");
			return 0.0;
		}else{
			perimetro=dAB+dBC+dAC;
			semip=perimetro*0.5;
			area=Math.sqrt(semip*(semip-dAB)*(semip-dBC)*(semip-dAC));
			
			System.out.println("area: " +area);
			if(verticesTArea == 0.0 || verticesTArea > area){
				verticesTArea = area;
				verticesT = a + " - "+b +" - " +c;
			}	
		}
		return area;
	}
	
	public static int selecionaDiagonal(int vertices[], int a){
		
		//int adj1 = a + 1;
		int adj2 = a - 1;
		if(adj2 < 0) adj2 = vertices.length - 1;
		if(adj2 == 0) {
			return vertices.length - 1;
		}
		return adj2 - 1;
	}
	
	public static void main(String args[]) {
		
	    matriz[1][1] = 0;
		matriz[1][2] = 3;
		matriz[1][3] = 5;
		matriz[1][4] = 4;
		matriz[1][5] = 4;
		
		matriz[2][1] = 3;
		matriz[2][2] = 0;
		matriz[2][3] = 4;
		matriz[2][4] = 5;
		matriz[2][5] = 5;

		matriz[3][1] = 5;
		matriz[3][2] = 4;
		matriz[3][3] = 0;
		matriz[3][4] = 4;
		matriz[3][5] = 4;
		
		matriz[4][1] = 4;
		matriz[4][2] = 5;
		matriz[4][3] = 4;
		matriz[4][4] = 0;
		matriz[4][5] = 4;
		
		matriz[5][1] = 3;
		matriz[5][2] = 5;
		matriz[5][3] = 4;
		matriz[5][4] = 4;
		matriz[5][5] = 0;

		int n = 5;
		int vertices[] = new int[n];
		
		//nomeando os vertices
		for (int x = 0; x < n; x++) {
			vertices[x] = x + 1; 
		}
		
		triangulacaoMinima(vertices, 0, 4);
		
		System.out.println("----MENOR ÁREA---- \n Vértices: "+verticesT + " \n"
				+ " Valor Área = " +verticesTArea);
		
	}

}
