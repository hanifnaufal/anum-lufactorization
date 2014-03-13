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
    double[] b;
	int[] pM;
    
    public void init(double[][] A, double[] b) {
        this.A = new SparseMatrix(A);
        this.U = SparseMatrix.zeros(A.length);
        this.L = SparseMatrix.zeros(A.length);
        this.b = b;
		pM = new int[A.length];
		for (int i = 0; i < A.length; i++) {
			pM[i] = i;
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

	public void leftLooking() throws Exception {
		for (int j = 0; j < A.colSize; j++) {
			//insert diagonal
			L.insert(1.0);
			L.I.add(j);
			L.P[j+1]++;
			
			//get j-th column of A
			SparseMatrix Aij = A.getColumn(j);
			Aij.permute(pM);
			
			//solve Ly = Aj
			SparseMatrix y = forwardElimination(L, Aij);
			
			//pivoting y
			int maxRow = y.searchMaxRow(j);
			if (maxRow == -1) throw new Exception("Singular Matrix!");
			if (maxRow != j) {
				//swap L, y, and P
				y.swapElement(j, maxRow);
				L.swapElement(j, maxRow, j);
				pM[j] ^= pM[maxRow];
				pM[maxRow] ^= pM[j];
				pM[j] ^= pM[maxRow]; 
			}
			//insert to matrix U
			for (int i = 0; i < j+1; i++) {
				for (int k = U.P[j]; k < U.P[j+1]; k++) {
					if (U.I.get(k) == i) {
						U.insert(y.getElement(i, 0));
						U.I.add(i);
						U.P[j]++;
					}
				}
			}
			//insert to matrix L
			for (int i = j + 1; i < A.colSize; i++) {
				for (int k = L.P[j]; k < U.P[j+1]; k++) {
					if (L.I.get(k) == i) {
						L.insert(y.getElement(i, 0)/y.getElement(j, 0));
						L.I.add(i);
						L.P[j]++;
					}
				}
			}
		}
	}




    
    public SparseMatrix forwardElimination(SparseMatrix L, SparseMatrix b) {
        SparseMatrix result = new SparseMatrix(b.rowSize, b.colSize);
        for (int j = 0; j < result.colSize; j++) {
            result.insert(b.getElement(0, j)/L.getElement(0, j));
            result.I.add(0);
			result.P[j+1]++;
            for (int i = j+1; i < result.rowSize; i++) {
                double Lij = L.getElement(i, j);
                if (Math.abs(Lij) > 0.0) {
                    result.insert(b.getElement(i,j)-Lij*b.getElement(i-1,j));
                    result.I.add(i);
                    result.P[j+1]++;
                }
            }
        }
        return result;
    }
    
    public double[] backwardSubstitution() {
		//TO-DO
        return null;
    }
}
