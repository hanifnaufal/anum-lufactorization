/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas1;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Anum A07
 */
public class SparseMatrix {
    public int rowSize;
    public int colSize;
    public int[] P;
    public List<Integer> I;
    public List<Double> X;


    /**
     * fungsi untuk membuat Sparse Matrix identitas
     * @param size panjang baris dan kolom matriks
     * @return matriks indentitas
     */
    public static SparseMatrix eye(int size){
        SparseMatrix result = new SparseMatrix(size);
        for(int i = 0; i< size; i++){
            result.P[i] = i;
            result.I.add(i);
            result.X.add(1.0);
        }
        result.P[size] = size;
        return result;
    }

    public static SparseMatrix zeros(int size){
        return new SparseMatrix(size);
    }


    public SparseMatrix(double[][] A) {
        this.colSize = A[0].length;
		this.rowSize = A.length;
        P = new int[colSize+1];
        I = new ArrayList<Integer>();
        X = new ArrayList<Double>();
        createMatrix(A);
    }
    
    public SparseMatrix(int size) {
        this.rowSize = this.colSize = size;
        P = new int[colSize+1];
        I = new ArrayList<Integer>();
        X = new ArrayList<Double>();
        //hardcodeYeay();
    }

    public SparseMatrix(int rowSize, int colSize){
        this.rowSize = rowSize; this.colSize = colSize;
        P = new int[colSize+1];
        I = new ArrayList<Integer>();
        X = new ArrayList<Double>();
    }

    
    /**
     * Membuat representasi sparse matriks dari matriks double[][]
     * @param A representasi double[][]
     */
    private void createMatrix(double[][] A) {
        int numItemAdded = 0;
        for (int i = 0; i < A[0].length; i++) {
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


    public SparseMatrix getColumn(int col){
        SparseMatrix result = new SparseMatrix(this.rowSize,1);
        result.P[1] = this.P[col+1]-this.P[col];

        for(int i = this.P[col]; i < this.P[col+1]; i++){
            result.I.add(this.I.get(i));
            result.X.add(this.X.get(i));
        }
        return result;
    }
    
   public int getIndex(int row, int col) {
       for (int i = P[col]; i < P[col+1]; i++) {
           if (I.get(i) == row) {
               return i;
           }
       }
       return -1;
   }
    
    public int searchMaxRow(int row) {
        double max = Double.MIN_VALUE;
        int maxRow = -1;
        for (int i = row; i < rowSize; i++) {
            double val = Math.abs(getElement(i, 0));
            if (val > max && val >= 0.0) {
                max = val;
                maxRow = i;
            }
        }
        return maxRow;
    }
    
    public void swapElement(int row1, int row2, int n) {
        //System.out.println(this);
        for (int i = 0; i < n; i++) {
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

    public void swapElement(int row1, int row2){
        swapElement(row1,row2,colSize);
    }

	//TO-DO: searching for better permutation algorithm
	public void permute(int[] pM) {
		//for each element in permutation matrix
		for (int k = 0; k < pM.length; k++) {
			// for each column
			for (int i = 0; i < colSize; i++) {
				//for each available row
				for (int j = P[i]; j < P[i+1]; j++) {
					//search for idx that satisfy I[idx] = pM[k]
					//set I in that idx to k
					if (j == pM[k]) {
						I.set(j, k);
						break;
					}
				}
			}
		}
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
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                sb.append(String.format("%10g",getElement(i,j))).append(";");
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
            System.out.print(i + "; ");
        }
        System.out.print("\nX = ");
		
		for (Double i : X) {
            System.out.print(i + "; ");
        }
        System.out.println("\n");
    }
}
