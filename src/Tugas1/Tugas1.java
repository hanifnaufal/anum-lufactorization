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
        double[][] A = {{4.5,0,3.2,0},
                        {3.1,2.9,0,0.9},
                        {0,1.7,3,0},
                        {3.5,0.4,0,1}};
        //SparseSolver ss = new SparseSolver();
        //ss.hitungLU(A, null);
        //ss.findMatrixL();
        SparseMatrix sm = new SparseMatrix(3);
        //sm.swapElement(0, 3);
        sm.insert(1);
        sm.insert(3);
        sm.insert(3);
        sm.insert(1);
        sm.insert(3);
        sm.insert(1);
        System.out.println(sm.toString());
        //sm.printAllArray();
    }
}
