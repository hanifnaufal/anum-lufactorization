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
    public static void main(String... args) throws Exception{
//        SparseMatrix sm = new SparseMatrix(3, new double[][]{{1,2,1},{1,2,3},{1,0,2}});
//        System.out.println(sm);
        SparseSolver ss = new SparseSolver();
        ss.init(new double[][]{{0,4,3},
							   {1,3,1},
							   {3,4,3}}, new double[]{1,2,3});
//        System.out.println(ss.fowardElimination());
//        System.out.println(ss.sm);
//		ss.init(new double[100][100], new double[100]);
//		System.out.println(ss.forwardSubstitutionPivot());       
		//System.out.println(ss.sm.searchMaxValueRow(0)); 
		//System.out.println(ss.sm.searchMaxValueRow(1)); 
		//System.out.println(ss.sm.searchMaxValueRow(2));
		System.out.println(ss.forwardSubstitutionPivot());
    }
}
