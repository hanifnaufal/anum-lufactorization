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
        double[][] A = {{0, 0, 1, 0},
                        {2, 0, 0, 3},
                        {0, 5, 0, 0},
                        {0, 0, 0, 1}};
        double[] b = {5, 6, 8, 8};
		
        SparseSolver ss = new SparseSolver();
        double[] x = ss.hitungLU(A, b);
		System.out.println("U =\n" + ss.U);
		System.out.println("L =\n" + ss.L);
		System.out.println("P =");
		for (int p : ss.pM) {
			System.out.printf("%4d;",p);
		}
		System.out.println("\n");
        System.out.println("x =");
        for(double a:x){
            System.out.printf("%10.5f;",a);
        }
        System.out.println();


//        BufferedReader reader= new BufferedReader(new FileReader(args[0]));
//        String tmp = reader.readLine();
//        StringTokenizer token = new StringTokenizer(reader.readLine(),",");
//        int n = token.countTokens();
//        double[][] A = new double[n][n];
//        int baris = 0;
//        while(tmp != null){
//            token = new StringTokenizer(reader.readLine(),",");
//            for(int i = 0; i<n;i++){
//                A[baris][i] = Double.parseDouble(token.nextToken());
//            }
//            baris++;
//            tmp = reader.readLine();
//        }
//
//        reader.close();
//        double b[] = new double[n];
//        reader= new BufferedReader(new FileReader(args[1]));
//        for(int i = 0; i < n; i++){
//            b[i] = Double.parseDouble(reader.readLine());
//        }
//        reader.close();
//
//
//        SparseSolver ss = new SparseSolver();
//        double[] x = ss.hitungLU(A, b);
//
//        BufferedWriter writer = new BufferedWriter(new FileWriter(args[2]));
//        for(int i = 0; i < n; i++){
//            writer.append(x[i] + "").append("\n");
//        }
//
//
    }
}
