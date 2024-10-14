package lib;

import java.util.Scanner;
import java.util.Locale;

public class RegresiBerganda {
    public static void RegresiLinierBerganda(String[] args) {
        Locale.setDefault(Locale.US); // Set Locale ke US agar input desimal menggunakan titik (.)
        
        Scanner scanner = new Scanner(System.in);
        System.out.print("Jumlah peubah x = ");
        int n = scanner.nextInt(); 
        System.out.print("Jumlah sampel = ");
        int m = scanner.nextInt(); 
        
        double[][] x = new double[n][m];
        double[] y = new double[m];
        
        // Input nilai x dan y
        for (int j = 0; j < m; j++) {
            System.out.println("Masukkan " + n + " nilai x untuk sampel ke-" + (j + 1) + ":");
            for (int i = 0; i < n; i++) {
                x[i][j] = scanner.nextDouble();
            }
            System.out.println("Masukkan nilai y untuk sampel ke-" + (j + 1) + ":");
            y[j] = scanner.nextDouble();
        }

        double[] arrayFirstRow = new double[n+2];
        double[][] matrixNextRow = new double[n][n+2];

        // Array berisi baris pertama Normal Estimation Equation
        arrayFirstRow[0] = n;
        for (int i = 1; i <= n; i++) {
            double sumX = 0;
            for (int j = 0; j < m; j++){
                sumX = sumX + x[i-1][j];
            arrayFirstRow[i] = sumX;
            }
        }
        double sumY = 0;
        for (int i = 0; i < m; i++){
            sumY = sumY + y[i];
        arrayFirstRow[n+1] = sumY;
        }

        // Matrix berisi baris kedua hingga terakhir dari Normal Estimation Equation
        for (int i = 0; i < n; i++){ // Kolom pertama
            matrixNextRow[i][0] = arrayFirstRow[i+1];
        }
        for (int i = 0; i < n; i++) { 
            for (int j = 1; j < (n+1); j++){ // Kolom kedua hingga kedua terakhir
                double sumXX = 0;
                for (int k = 0; k < m; k++){
                    sumXX = sumXX + (x[i][k] * x[j-1][k]);
                }
                matrixNextRow[i][j] = sumXX;
            }
            double sumXY = 0; // Kolom terakhir
            for (int k = 0; k < m; k++){
                sumXY = sumXY + (x[i][k] * y[k]);
            }
            matrixNextRow[i][n+1] = sumXY;
        }

        // Matrix augmented
        Matrix matrixAugmented = new Matrix(n+1, n+2);
        for (int i = 0; i < n + 2; i++) {
            matrixAugmented.setElMT(0, i, arrayFirstRow[i]);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 2; j++) {
                matrixAugmented.setElMT(i+1, j, matrixNextRow[i][j]);
            }
        }
        System.out.println("Matriks Augmented:");
        matrixAugmented.printMatrix();
        
        // Solusi
        double[] solutions = SPL.getSolution(matrixAugmented);
        for (int i = 0; i < solutions.length; i++) {
            System.out.printf("B%d = %.2f%n", i, solutions[i]);
        }
        // Test case:
        // X1 = 10, 16, 20, 26; X2 = 8, 10, 16, 22; Y = 20, 30, 40, 36
        scanner.close();
    }
}