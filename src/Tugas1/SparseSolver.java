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
public class SparseSolver {
    SparseMatrix sm;
    double[] b;    

    public void init(double[][] A, double[] b) {
        this.sm = new SparseMatrix(b.length, A);
        this.b = b;
    }

    public SparseSolver() {
    }
    
    public double[] hitungLU(double[][] A, double[] b){
        init(A,b);
        
        return this.b;
    }
    
    public SparseMatrix fowardElimination(){
        int n = sm.n;
        SparseMatrix L = new SparseMatrix(n);
        Node[] lItr = new Node[n];
        Node[] aItr = new Node[n];
        for (int i = 0; i < n; i++) {
            L.matrixColumn[i] = new Node();            
            lItr[i] = L.matrixColumn[i];
            aItr[i] = sm.matrixColumn[i].next;
        }
                        
        for (int i = 0; i < n; i++) {
            Node itrTmpItoJ = null;
            for (int j = i+1; j < n; j++) {
                Node tmp = new Node();
                tmp.col = aItr[j].col;
                tmp.value = aItr[j].value/aItr[i].value;
                lItr[j].next = tmp;
                
                
                //mbagi
                Node aItrDivisor = aItr[i];
                Node aItrNumerator = aItr[j];                
                aItrNumerator.value = 0;
                aItrNumerator = aItrNumerator.next;
                aItrDivisor = aItrDivisor.next;
                while(aItrDivisor != null){
                    if(aItrNumerator.col < aItrDivisor.col){
                        aItrNumerator = aItrNumerator.next;
                    }else if(aItrNumerator.col > aItrDivisor.col){
                        aItrNumerator.value = - aItrDivisor.value*tmp.value;
                        aItrDivisor = aItrDivisor.next;
                    }else{
                        aItrNumerator.value = aItrNumerator.value - aItrDivisor.value*tmp.value;
                        aItrDivisor = aItrDivisor.next;
                        aItrNumerator = aItrNumerator.next;
                    }
                }
                
                lItr[j] = lItr[j].next;                
                aItr[j] = aItr[j].next;
                
                if(j == i+1)
                itrTmpItoJ = aItr[j];
            }            
            aItr[i] = itrTmpItoJ;
        }
        
        for (int i = 0; i < n; i++) {
            Node identity = new Node();
            identity.value = 1;
            identity.col = i;
            lItr[i].next = identity;
        }
        
        return L;
    }
    
    
    public void backwardSubs(){
        
    }
}
