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
    public static void main(String[] args) {
        double[][] A = {{4.5,0,3.2,0},
                        {3.1,2.9,0,0.9},
                        {0,1.7,3,0},
                        {3.5,0.4,0,1}};
        SparseMatrix sm = new SparseMatrix(A);
        System.out.println(sm.toString());
        //sm.printAllArray();
    }
}
