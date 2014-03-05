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
public class SparseMatrix {
    Node[] matrixRow;
    int n;        
    

        
    public SparseMatrix(int n, double[][] value){
        this.n = n;
        matrixRow = new Node[n];
        for (int i = 0; i < n; i++) {            
            //matrixRow[i] = new Node();
            Node itr = matrixRow[i];
            for (int j = 0; j < n; j++) {
                if(value[i][j] != 0){
                    System.out.println(value[i][j]);
                    Node x = new Node();
                    x.col = j;
                    x.value = value[i][j];
                    if (itr == null) {
                        matrixRow[i] = x;
                    } else {                                       
                        itr.next = x;                        
                    }                
                    itr = x;   
                }
            }
        }
    }
    
    public SparseMatrix(int n, Node[] matrixRow){
        this.n = n;
        this.matrixRow=matrixRow;
    }
    public SparseMatrix(int n){
        this.n = n;
        this.matrixRow=new Node[n];
    }
    
    // public SparseMatrix transpose(){
    //     Node[] newMatrixCol = new Node[n];
    //     Node[] newMatrixItr = new Node[n];
    //     for (int i = 0; i < n; i++) {
    //         newMatrixCol[i] = new Node(); 
    //         newMatrixItr[i] = newMatrixCol[i];
    //     }
        
    //     for (int i = 0; i < n; i++) {
    //         Node oldMatrixItr = matrixRow[i].next;            
    //         while(oldMatrixItr != null){             
    //             Node tmp = new Node();
    //             tmp.value = oldMatrixItr.value;
    //             tmp.col = i;                              
                
    //             newMatrixItr[oldMatrixItr.col].next = tmp;                
                
    //             newMatrixItr[oldMatrixItr.col] = newMatrixItr[oldMatrixItr.col].next;
    //             oldMatrixItr = oldMatrixItr.next;
    //         }
    //     }        
    //     return new SparseMatrix(n, newMatrixCol);
    // }
    
    
    // public SparseMatrix multiply(SparseMatrix sm){
    //     SparseMatrix A = this;
    //     SparseMatrix B = sm.transpose();
    //     SparseMatrix result = new SparseMatrix(A.n);
        
    //     for (int i = 0; i < A.n; i++) {
    //         result.matrixRow[i] = new Node();
    //     }
        
    //     for (int i = 0; i < A.n; i++) {                        
    //         Node rItr = result.matrixRow[i];                     
            
    //         for (int j = 0; j < B.n; j++) {
    //             double sum = 0;
    //             Node aItr = A.matrixRow[i].next;
    //             Node bItr = B.matrixRow[j].next;                                                
    //             while(aItr != null && bItr != null){                    
    //                 int aCol = aItr.col;
    //                 int bCol = bItr.col;
    //                 if(aCol == bCol){
    //                     sum += aItr.value * bItr.value;
    //                     aItr = aItr.next;
    //                     bItr = bItr.next;                        
    //                 }else if(aCol < bCol){
    //                     aItr = aItr.next;
    //                 }else{
    //                     bItr = bItr.next;
    //                 }
    //             }
    //             if(sum != 0){
    //                 Node tmp = new Node();
    //                 tmp.value = sum;
    //                 tmp.col = j;
    //                 rItr.next = tmp;                
    //                 rItr = rItr.next;
    //             }
    //         }                        
    //     }
        
        
    //     return result;
    // }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrixRow.length; i++) {
            Node itr = matrixRow[i];
            while(itr != null){
                sb.append(itr.value).append(";").append(itr.col).append(" ");
                itr = itr.next;
            }
            sb.append("\n");
        }
        return sb.toString();
    }        
}

class Node {
    int col;
    double value;
    Node next;
}
