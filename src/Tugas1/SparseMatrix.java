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
    
    public SparseMatrix(int colSize) {
        this.colSize = colSize;
        P = new int[colSize+1];
        I = new ArrayList<Integer>();
        X = new ArrayList<Double>();
        hardcodeYeay();
    }
    
    /**
     * Membuat representasi sparse matriks dari matriks double[][]
     * @param A representasi double[][]
     */
    private void createMatrix(double[][] A) {
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
    
   public int getIndex(int row, int col) {
       for (int i = P[col]; i < P[col+1]; i++) {
           if (I.get(i) == row) {
               return i;
           }
       }
       return -1;
   }
    
    public int searchMaxRow(int col) {
        double max = Double.MIN_VALUE;
        int row = -1;
        for (int i = col; i < colSize; i++) {
            double val = Math.abs(getElement(i, col));
            if (val > max && val > 0.0) {
                max = val;
                row = i;
            }
        }
        return row;
    }
    
    public void swapElement(int row1, int row2) {
        //System.out.println(this);
        for (int i = 0; i < colSize; i++) {
            for (int j = P[i]; j < P[i+1]; j++) {
                if (I.get(j) == row1) {
                    I.set(j, row2);
                } else if (I.get(j) == row2) {
                    I.set(j, row1);
                }
            }
        }
        //System.out.println(this);
    }

    private void hardcodeYeay() {
        for (int i = 0; i < colSize; i++) {
            for (int j = i; j < colSize; j++) {
                I.add(j);
            }
        }
        P[0] = 0;
        for (int i = 1, kol = colSize, tmp = 0; kol > 0; kol--, i++) {
            tmp += kol;
            P[i] = tmp;
        }
    }
    
    public void insert(double val) {
        X.add(val);
    }

    public void removeElement(int row, int col) {
        for (int i = P[col]; i < P[col+1]; i++) {
            if (I.get(i) == row) {
                X.remove(i);
                I.remove(i);
                for (int j = col+1; j < P.length; j++) {
                    P[j]--;
                }
            }
        }
    }

    public void updateElement(int row, int col, double val) {
        for (int i = P[col]; i < P[col+1]; i++) {
            if (I.get(i) == row) {
                X.set(i, val);
				return;
            }
        }
		//TO-DO
		//Seandainya nilai awalnya 0, artinya tidak ada pada array I
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

    public void printMatrices() {
        System.out.print("P = ");
        for (int p: P) {
            System.out.print(p + "; ");
        }
        System.out.print("\nI = ");

        for (Integer i : I) {
            System.out.print(i.intValue() + "; ");
        }
        System.out.print("\nX = ");
		
		for (Double i : X) {
            System.out.print(i.doubleValue() + "; ");
        }
        System.out.println("\n");
    }
}
