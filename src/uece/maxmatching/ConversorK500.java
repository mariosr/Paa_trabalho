package uece.maxmatching;

/**
 * Created by jeffrodrigo on 13/07/16.
 */
public class ConversorK500 {
    public static double[][] create() {

        double [][] matrix = new double[1000][1000];

        for (int i = 0; i < matrix.length; i++) {
            // Se i é impar
            if(i%2 != 0) {

                for (int j = i + 1; j < matrix.length; j++) {
                    if(i != j) {
                        //se j é par
                        if(j%2 == 0) {
                            double denominator = Math.abs(i-j-1);
                            if(denominator > 0) {
                                matrix[i][j] = 10*(i+j)/denominator;
                                //matrix[i][j] = matrix[j][i] = 10*(i+j)/denominator;
                            }else {
                                matrix[i][j] = i+j;
                                //matrix[i][j] = matrix[j][i] = i+j;
                            }
                        }else { // se j não é par dij = "infinito"
                            matrix[i][j] = 999999;
                            //matrix[i][j] =  matrix[j][i]= 99999999;
                        }
                    }
                }

            } else {
                for (int j = i + 1; j < matrix.length; j++) {
                    if(j%2 == 0 && i != j) {
                        matrix[i][j] = 999999;
                        //matrix[i][j] = matrix[j][i] = 99999999;
                    }
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = i + 1; j < matrix.length; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }

        return matrix;
    }
}
