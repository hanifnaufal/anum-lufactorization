/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas1;

/**
 *
 * @author fariz.ikhwantri
 */
public class Tugas1 {
    public static void main(String[] args) throws Exception {
        double[][] A = {{4, 0, 1, 0},
                        {2, 1, 0, 3},
                        {0, 5, 0, 0},
                        {1, 0, 0, 1}};
        double[] b = {5, 6, 8, 8};
		double[][] B = {{3},
                        {6},
                        {9}};

		double[][] test = {{1, 2, 3},
						   {4, 5, 6},
						   {7, 8, 9}};
        SparseSolver ss = new SparseSolver();
        ss.hitungLU(A, b);
        //ss.findMatrixL();
		ss.leftLooking();
		System.out.println("U =\n" + ss.U);
		System.out.println("L =\n" + ss.L);
		System.out.println(ss.printPermutationMatrix());
//        SparseMatrix sm = new SparseMatrix(B);
//		int[] p = {2,0,1};
		//System.out.println("U = \n" + ss.U);
//		sm.permute(p);
		//System.out.println("L = \n" + ss.L);
//        double[][] I = {{1, 0, 0, 0},
//                        {2, 1, 0, 0},
//                        {1, 3, 1, 0},
//						{3, 1, 6, 1}};
//        SparseMatrix eye = new SparseMatrix(I);
//        double[][] Z = {{2},{5},{3},{1}};
//        SparseMatrix zSM = new SparseMatrix(Z);
//        SparseMatrix x = ss.forwardElimination(eye, zSM);
        //sm.swapElement(0, 3);
//        sm.insert(1);
//        sm.insert(3);
//        sm.insert(3);
//        sm.insert(1);
//        sm.insert(3);
//        sm.insert(1);
//        System.out.println(sm);
//		sm.printMatrices();
//		sm.removeElement(1, 1);
//        System.out.println(x);
//		sm.printMatrices();
        //sm.printAllArray();
    }
}
