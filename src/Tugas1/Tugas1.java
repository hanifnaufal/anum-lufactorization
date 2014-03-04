/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tugas1;

/**
 *
 * @author hanifnaufal
 */
public class Tugas1 {
    public static void main(String... args){
//        SparseMatrix sm = new SparseMatrix(3, new double[][]{{0,0,1},{1,2,3},{1,0,2}});
//        System.out.println(sm.multiply(sm));
        SparseSolver ss = new SparseSolver();
        ss.init(new double[][]{{1,0,1},{2,2,3},{3,5,2}}, new double[]{1,2,3});
        System.out.println(ss.fowardElimination());
        System.out.println(ss.sm);
        
    }
}
