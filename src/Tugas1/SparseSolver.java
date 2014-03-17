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
        this.L = SparseMatrix.eye(A.length);
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
            int maxRow = U.searchMaxRowOneColumn(kol);
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
                        U.setElement(baris, k, Aij);
                    }
                }
            }
        }
        L.insert(1.0);
        return null;
    }

	public void leftLooking() throws Exception {
		for (int j = 0; j < A.colSize; j++) {			
			//get j-th column of A
			SparseMatrix Aij = A.getColumn(j);
			Aij.permute(pM);
			
			//solve Ly = Aj
			SparseMatrix y = forwardElimination(L, Aij);
			
			//pivoting y
			int maxRow = y.searchMaxRowOneColumn(j);
			if (maxRow != j && maxRow > -1) {
				//swap L, y, and P
				y.swapElement(j, maxRow);
                L.swapElement(j, maxRow, j);
				pM[j] ^= pM[maxRow];
				pM[maxRow] ^= pM[j];
				pM[j] ^= pM[maxRow]; 
			}
			
			//insert to matrix U
			for (int i = 0; i < j+1; i++) {
				U.setElement(i, j, y.getElement(i, 0));
            }
			//insert to matrix L
			int n = 0;
			for (int k = L.P[j]+1, i = j+1; k < L.P[j]+L.colSize-j; k++, n++, i++) {
					L.X.add(k, y.getElement(i, 0)/y.getElement(j, 0));
					L.I.add(k, i);
			}
			for (int k = j+1; k < L.P.length; k++)
				L.P[k] += n;
		}
	}
	
    public SparseMatrix forwardElimination(SparseMatrix L, SparseMatrix b) {
        SparseMatrix x = new SparseMatrix(b.rowSize, b.colSize);
        for(int i=0; i<b.rowSize; i++){
            x.setElement(i,0,b.getElement(i,0));
        }
        for(int i=0; i<L.colSize; i++){
            x.setElement(i,0,x.getElement(i,0)/L.getElement(i,i));
            for(int j=i+1; j<b.rowSize; j++){
                x.setElement(j,0,x.getElement(j,0)-L.getElement(j,i)*x.getElement(i,0));
            }
        }
        return x;
    }
    
    public double[] backwardSubstitution(SparseMatrix U, SparseMatrix y) {
		double [] x = new double[y.rowSize];

        //for(int i=L.colSize - 1; i>=0; i++){
        //    x.setElement(i,0,x.getElement(i,0)/U.getElement(i,i));
        //    for(int j=i+1; j<y.rowSize; j++){
        //        x.setElement(j,0,x.getElement(j,0)-U.getElement(j,i)*x.getElement(i,0));
        //    }
        //}
        return null;

    }
	
	public String printPermutationMatrix() {
		String str = "P = [ ";
		for(int i : pM) {
			str += i + " ";
		}
		str += "]";
		return str;
	}
}
