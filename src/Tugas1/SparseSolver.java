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
    SparseMatrix A;
	int[] P;
    double[] b;
    
    public void init(double[][] A, double[] b) {
        this.A = new SparseMatrix(A);
        this.U = SparseMatrix.zeros(A.length);
        this.L = SparseMatrix.zeros(A.length);
        this.b = b;
		P = new int[A.length];
		for (int i = 0; i < P.length; i++) {
			P[i] = i;			
		}
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

	private void leftLooking() {
		for (int j = 0; j < A.colSize; j++) {
			SparseMatrix Aij = A.getColumn(j);
			Aij.permute(P);
			SparseMatrix y = forwardElimination(L, A.getColumn(j));
			int maxRow = y.searchMaxRow(j);
			if (maxRow != j) {
				y.swapElement(j, maxRow);
				L.swapElement(j, maxRow, j);
				P[j] ^= P[maxRow];
				P[maxRow] ^= P[j];
				P[j] ^= P[maxRow]; 
			}			
		}
	}




    
    private double[] forwardElimination(SparseMatrix L, SparseMatrix b) {

        return null;
    }
    
    private double[] backwardSubstitution() {
		//TO-DO
        return null;
    }
}
