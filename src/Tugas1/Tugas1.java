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
                        {0, 5, 3, 0},
                        {1, 3, 3, 1}};
        double[] b = {5, 6, 8, 8};

        SparseSolver ss = new SparseSolver();
        ss.hitungLU(A, b);
        //ss.findMatrixL();
		System.out.println(ss.U);
		System.out.println(ss.L);
//        SparseMatrix sm = new SparseMatrix(3);
        //sm.swapElement(0, 3);
//        sm.insert(1);
//        sm.insert(3);
//        sm.insert(3);
//        sm.insert(1);
//        sm.insert(3);
//        sm.insert(1);
//        System.out.println(sm.toString());
//		sm.printMatrices();
//		sm.removeElement(1, 1);
//        System.out.println(sm.toString());
//		sm.printMatrices();
        //sm.printAllArray();
    }
}
