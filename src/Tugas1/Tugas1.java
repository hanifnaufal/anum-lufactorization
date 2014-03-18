/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas1;

import java.io.*;
import java.util.StringTokenizer;

/**
 *
 * @author fariz.ikhwantri
 */
public class Tugas1 {
    public static void main(String[] args) throws Exception {
//        double[][] A = {{4, 0, 1, 0},
//                        {2, 1, 0, 3},
//                        {0, 5, 0, 0},
//                        {1, 0, 0, 1}};
//        double[] b = {5, 6, 8, 8};
//		double[][] B = {{3},
//                        {6},
//                        {9}};
//
//		double[][] test = {{1, 2, 3},
//						   {4, 5, 6},
//						   {7, 8, 9}};
//        SparseSolver ss = new SparseSolver();
//        double[] x = ss.hitungLU(A, b);
//		System.out.println("U =\n" + ss.U);
//		System.out.println("L =\n" + ss.L);
//        System.out.println("X =");
//        for(double a:x){
//            System.out.printf("%10.5g;",a);
//        }
//        System.out.println();
//		System.out.println(ss.printPermutationMatrix());


        BufferedReader reader= new BufferedReader(new FileReader(args[0]));
        String tmp = reader.readLine();
        StringTokenizer token = new StringTokenizer(tmp,",");
        int n = token.countTokens();
        double[][] A = new double[n][n];
        int baris = 0;
        while(tmp != null){
            token = new StringTokenizer(tmp,",");
            for(int i = 0; i<n;i++){
                A[baris][i] = Double.parseDouble(token.nextToken());
            }
            baris++;
            tmp = reader.readLine();
        }
        reader.close();

        double b[] = new double[n];
        reader= new BufferedReader(new FileReader(args[1]));
        for(int i = 0; i < n; i++){
            b[i] = Double.parseDouble(reader.readLine());
        }
        reader.close();

        long time = System.nanoTime();
        SparseSolver ss = new SparseSolver();
        double[] x = ss.hitungLU(A, b);
        System.out.println((System.nanoTime() - time)*1e-9);

        BufferedWriter writer = new BufferedWriter(new FileWriter(args[2]));
        for(int i = 0; i < n; i++){
            writer.append(x[i] + "").append("\n");
        }
        writer.close();

    }
}
