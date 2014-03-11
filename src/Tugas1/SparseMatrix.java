/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas1;

import java.util.ArrayList;

/**
 *
 * @author Anum A07
 */
public class SparseMatrix {
    int colSize;
    int[] P;
    ArrayList<Integer> I;
    ArrayList<Double> X;
    
    public SparseMatrix(double[][] A) {
        this.colSize = A[0].length;
        P = new int[colSize+1];
        I = new ArrayList<Integer>();
        X = new ArrayList<Double>();
        createMatrix(A);
    }
    
    public void createMatrix(double[][] A) {
        int numItemAdded = 0;
        for (int i = 0; i < A.length; i++) {
            P[i] = numItemAdded;
            for (int j = 0; j < A.length; j++) {
                if (A[j][i] != 0.0) {
                    I.add(j);
                    X.add(A[j][i]);
                    numItemAdded++;
                }
            }
        }
        P[P.length-1] = numItemAdded;
    }
    
    public double getElement(int row, int col) {
        for (int i = P[col]; i < P[col+1]; i++) {
            if (I.get(i) == row) {
                return X.get(i);
            }
        }
        return 0.0;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < colSize; i++) {
            for (int j = 0; j < colSize; j++) {
                sb.append(getElement(i,j)).append("; ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
