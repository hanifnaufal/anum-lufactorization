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
    SparseMatrix b;
	int[] pM;
    
    public void init(double[][] A, double[] b) {
        this.A = new SparseMatrix(A);
        this.U = SparseMatrix.zeros(A.length);
        this.L = SparseMatrix.eye(A.length);
        this.b = new SparseMatrix(b);
		pM = new int[A.length];
		for (int i = 0; i < A.length; i++) {
			pM[i] = i;
		}
    }
    
    public double[] hitungLU(double[][] A, double[] b){
        init(A, b);
        leftLooking();
        this.b.permute(pM);
        SparseMatrix y = forwardElimination(L, this.b);
        double[] x = backwardSubstitution(U,y);
        return x;
    }

	public void leftLooking() {
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
			for (int i = 0; i <= j; i++) {
				U.setElement(i, j, y.getElement(i, 0));
            }
			//insert to matrix L
			for (int i = j+1; i<A.colSize;i++) {
					L.setElement(i, j, y.getElement(i, 0) / y.getElement(j, 0));
			}
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
        double[] x = new double[y.rowSize];
        for(int i=0; i<y.rowSize; i++){
            x[i]=y.getElement(i,0);
        }
        for(int i=U.colSize-1; i >= 0; i--){
            x[i]= x[i] / U.getElement(i,i);
            for(int j=i-1; j >= 0; j--){
                x[j]=x[j] - U.getElement(j,i)*x[i];
            }
        }
        return x;

    }

}
