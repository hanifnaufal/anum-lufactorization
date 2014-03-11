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
        this.b = b;
    }
    
    public double[] hitungLU(double[][] A, double[] b){
        init(A,b);
        return this.b;
    }
    
    public SparseMatrix findMatrixL() {
        return null;
    }
    
    public double[] forwardElimination() {
        return null;
    }
    
    public double[] backwardSubstitution() {
        return null;
    }
}
