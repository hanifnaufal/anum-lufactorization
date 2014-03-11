/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas1;

/**
 *
 * @author Anum A07
 */
public class SparseSolver {
    SparseMatrix U;
    SparseMatrix L;
    double[] b;
    
    public void init(double[][] A, double[] b) {
        this.U = new SparseMatrix(A);
        this.L = new SparseMatrix(A.length);
        this.b = b;
    }
    
    public double[] hitungLU(double[][] A, double[] b){
        init(A,b);
        return this.b;
    }
    
	//INCOMPLETE
    public SparseMatrix findMatrixL() throws Exception {
        int colSize = U.colSize;
        int[] per = new int[colSize];
        for (int i = 1; i < per.length; i++) {
            per[i] = i;
        }

        for (int kol = 0; kol < colSize - 1; kol++) {
            int maxRow = U.searchMaxRow(kol);
            if (maxRow == -1) throw new Exception("Singular Matrix!");
            per[kol] ^= per[maxRow];
            per[maxRow] ^= per[kol];
            per[kol] ^= per[maxRow];
            U.swapElement(maxRow, kol);
            L.insert(1.0);
            for (int baris = kol + 1; baris < colSize; baris++) {
                double Lik = U.getElement(baris, kol)/U.getElement(kol, kol);
                L.insert(Lik);
                for (int k = kol; k < colSize; k++) {
					double Akj = U.getElement(kol, k);
					if (Akj == 0.0) continue;
                    double Aij = U.getElement(baris, k) - Lik*Akj;
                    if (Aij == 0.0) {
                        U.removeElement(baris, k);
                    } else {
                        U.updateElement(baris, k, Aij);
                    }
                }
            }
        }
        L.insert(1.0);
        return null;
    }
    
    public double[] forwardElimination() {
		//TO-DO
        return null;
    }
    
    public double[] backwardSubstitution() {
		//TO-DO
        return null;
    }
}
