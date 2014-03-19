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
     * fungsi untuk membuat Sparse Matrix berisi nilai nol
     *
     * @param size panjang baris dan kolom matriks
     * @return matriks berisi nilai nol
     */
    public static SparseMatrix zeros(int size) {
        return new SparseMatrix(size);
    }

    /**
     * Konstruktor Sparse Matrix
     *
     * @param A matriks dense
     */
    public SparseMatrix(double[][] A) {
        this.colSize = A[0].length;
        this.rowSize = A.length;
        P = new int[colSize + 1];
        I = new ArrayList<Integer>();
        X = new ArrayList<Double>();
        createMatrix(A);
    }

    /**
     * Konstruktor Sparse Matrix
     *
     * @param b matriks dense
     */
    public SparseMatrix(double[] b) {
        this(b.length, 1);
        for (int i = 0; i < b.length; i++) {
            setElement(i, 0, b[i]);
        }
    }

    /**
     * Konstruktor Sparse Matrix Berfungsi untuk membuat Sparse Matrix size x
     * size
     *
     * @param size ukuran matrix
     */
    public SparseMatrix(int size) {
        this.rowSize = this.colSize = size;
        P = new int[colSize + 1];
        I = new ArrayList<Integer>();
        X = new ArrayList<Double>();
        //hardcodeYeay();
    }

    /**
     * Konstruktor Sparse Matrix Berfungsi untuk membuat Sparse Matrix ukuran
     * rowSize x colSize
     *
     * @param rowSize ukuran baris pada matrix
     * @param colSize ukuran kolom pada matrix
     */
    public SparseMatrix(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        P = new int[colSize + 1];
        I = new ArrayList<Integer>();
        X = new ArrayList<Double>();
    }

    /**
     * Membuat representasi sparse matriks dari matriks dense double[][]
     *
     * @param A matrix dense double[][]
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
        P[P.length - 1] = numItemAdded;
    }

    /**
     * Mengambil elemen
     *
     * @param row index baris
     * @param col index kolom
     * @return nilai
     */
    public double getElement(int row, int col) {
        for (int i = P[col]; i < P[col + 1]; i++) {
            if (I.get(i) == row) {
                return X.get(i);
            }
        }
        return 0.0;
    }

    /**
     * Mengambil matrix kolom
     *
     * @param col index kolom
     * @return matrix kolom ke col
     */
    public SparseMatrix getColumn(int col) {
        SparseMatrix result = new SparseMatrix(this.rowSize, 1);
        result.P[1] = this.P[col + 1] - this.P[col];
        result.I = this.I.subList(this.P[col], this.P[col + 1]);
        result.X = this.X.subList(this.P[col], this.P[col + 1]);
        return result;
    }

    /**
     * Mencari nilai absolut terbesar dari satu kolom
     *
     * @param rowStart baris dimulainya pencarian
     * @param invpM invers dari matrix permutasi
     * @return index baris dimana nilai terbesar berada
     */
    public int searchMaxRowOneColumn(int rowStart, int[] invpM) {
        double max = Double.MIN_VALUE;
        int maxRow = -1;

        for (int i = P[0]; i < P[1]; i++) {
            if (invpM[I.get(i)] >= rowStart) {
                double val = Math.abs(X.get(i));
                if (val > max) {
                    max = val;
                    maxRow = I.get(i);
                }
            }
        }
        return (maxRow != -1) ? invpM[maxRow] : -1;
    }
    
    /**
     * Fungsi yang digunakan untuk melakukan permutasi baris 
     * @param pM matrix permutasi
     * @return Sparse Matrix yang telah dipermutasi 
     */
    public SparseMatrix permute(int[] pM) {
        int[] invpM = new int[pM.length];

        for (int i = 0; i < pM.length; i++) {
            invpM[pM[i]] = i; //invpM => index = value, value = index
        }
        for (int i = 0; i < colSize; i++) { // for each column
            for (int j = P[i]; j < P[i + 1]; j++) { //for each available row
                I.set(j, invpM[I.get(j)]); //I[row] = inv[I[row]]
            }
        }
        return this;
    }

    /**
     * Fungsi yang digunakan untuk menghapus elemen 
     * @param row baris 
     * @param col kolom
     */
    public void removeElement(int row, int col) {
        for (int i = P[col]; i < P[col + 1]; i++) {
            if (I.get(i) == row) {
                X.remove(i);
                I.remove(i);
                for (int j = col + 1; j < P.length; j++) {
                    P[j]--;
                }
            }
        }
    }

    /**
     * Fungsi yang digunakan untuk mengubah nilai elemen 
     * @param row baris 
     * @param col kolom
     * @param val nilai baru
     */
    public void setElement(int row, int col, double val) {
        if (Math.abs(val) < 1e-10) {
            removeElement(row, col);
            return;
        }
        for (int i = P[col]; i < P[col + 1]; i++) {
            if (I.get(i) == row) {
                X.set(i, val);
                return;
            }
        }
        I.add(P[col + 1], row);
        X.add(P[col + 1], val);
        for (int j = col + 1; j < P.length; j++) {
            P[j]++;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                sb.append(String.format("%10.5f", getElement(i, j))).append(";");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Melakukan konversi Sparse matrix menjadi dense matrix
     * @return dense matrix hasil konversi 
     */
    public double[][] toDenseMatrix() {
        double[][] result = new double[rowSize][colSize];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = getElement(i, j);
            }
        }
        return result;
    }
}
