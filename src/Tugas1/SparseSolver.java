/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    int[] invpM;
    
    public void init(double[][] A, double[] b) {
        this.A = new SparseMatrix(A);
        this.U = SparseMatrix.zeros(A.length);
        this.L = SparseMatrix.zeros(A.length);
        this.b = new SparseMatrix(b);
		pM = new int[A.length];
		invpM = new int[A.length];
        for (int i = 0; i < A.length; i++) {
			pM[i] = i;
            invpM[pM[i]] = i;
		}
    }
    
    public double[] hitungLU(double[][] A, double[] b){
        init(A, b);
        leftLooking();
        SparseMatrix y = forwardElimination(L, this.b);
        double[] x = backwardSubstitution(U,y);
        L.permute(pM); //membenarkan L untuk dilihat
        return x;
    }

	public void leftLooking() {
        int lp = 0, up = 0; // index buat P pada L dan U
		for (int j = 0; j < A.colSize; j++) {

			//get j-th column of A
			SparseMatrix Aij = A.getColumn(j);

			//solve Ly = Aj
			SparseMatrix y = forwardElimination(L, Aij);
			
			//pivoting y
			int maxRow = y.searchMaxRowOneColumn(j,invpM);
			if (maxRow != j && maxRow > -1) {
				pM[j] ^= pM[maxRow];
				pM[maxRow] ^= pM[j];
				pM[j] ^= pM[maxRow];
                // hitung inverse p
                for (int i = 0; i < pM.length; i++)
                    invpM[pM[i]] = i; //invpM => index = value, value = index
            }


			//insert to matrix UL
            lp++;
            L.I.add(pM[j]);
            L.X.add(1.0);
            double pivotElem = y.getElement(pM[j],0);

            for (int i = y.P[0]; i<y.P[1]; i++){
                if(invpM[y.I.get(i)] <= j){
                    U.I.add(invpM[y.I.get(i)]);
                    U.X.add(y.X.get(i));
                    up++;
                }
                else{
                    L.I.add(y.I.get(i));
                    L.X.add(y.X.get(i)/pivotElem);
                    lp++;
                }
            }
            U.P[j+1] = up;
            L.P[j+1] = lp;
		}
	}
	
    public SparseMatrix forwardElimination(SparseMatrix L, SparseMatrix b) {
        // x = b
        double[] x = new double[b.rowSize];
        for(int i = b.P[0]; i< b.P[1]; i++){
            x[b.I.get(i)] = b.X.get(i);
        }

        List<Integer> Xr = reach(L,b);
        for(int k = Xr.size()-1; k>=0; k--){
            int j = Xr.get(k);
            for(int i=L.P[j]; i<L.P[j+1]; i++) if(invpM[L.I.get(i)] > j){
                x[L.I.get(i)] = x[L.I.get(i)] - L.X.get(i)*x[pM[j]];
            }
        }
        return new SparseMatrix(x);
    }

    public List<Integer> reach(SparseMatrix L, SparseMatrix b){
        boolean[] mark = new boolean[L.colSize];
        ArrayList<Integer> Xr = new ArrayList<Integer>();
        for(int i : b.I){
            if(!mark[invpM[i]]){
                dfs(invpM[i],L,mark,Xr);
            }
        }
        return Xr;
    }

    public void dfs(int j, SparseMatrix L, boolean[] mark, List<Integer> Xr){
        mark[j] = true;
        for(int i = L.P[j]; i<L.P[j+1]; i++){
            if(!mark[invpM[L.I.get(i)]]){
                dfs(invpM[L.I.get(i)],L,mark,Xr);
            }
        }
        Xr.add(j);
    }


    public double[] backwardSubstitution(SparseMatrix U, SparseMatrix y) {
        double[] x = new double[y.rowSize];
        for(int i=0; i<y.P[1]; i++){
            x[invpM[y.I.get(i)]] = y.X.get(i);
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
