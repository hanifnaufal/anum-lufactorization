/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tugas1;

/**
 *
 * @author hanifnaufal
 * @contributor aradeaks
 */
public class SparseMatrix {
    Node[] matrixRow;
    int size;    
    
    public SparseMatrix(int size, Node[] matrixRow){
        this.size = size;
        this.matrixRow=matrixRow;
    }
	
    public SparseMatrix(int size){
        this.size = size;
        this.matrixRow=new Node[size];
    }    
            
    public SparseMatrix(int size, double[][] value){
        this.size = size;
        matrixRow = new Node[size];
        for (int i = 0; i < size; i++) {            
            //matrixRow[i] = new Node();
            Node itr = matrixRow[i];
            for (int j = 0; j < size; j++) {
                if(value[i][j] != 0){
                    System.out.println(value[i][j]);
                    Node newNode = new Node();
                    newNode.col = j;
                    newNode.value = value[i][j];
                    if (itr == null) {
                        matrixRow[i] = newNode;
                    } else {                                       
                        itr.next = newNode;                        
                    }                
                    itr = newNode;   
                }
            }
        }
    }
    
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
	
    // public SparseMatrix transpose(){
    //     Node[] newMatrixCol = new Node[size];
    //     Node[] newMatrixItr = new Node[size];
    //     for (int i = 0; i < size; i++) {
    //         newMatrixCol[i] = new Node(); 
    //         newMatrixItr[i] = newMatrixCol[i];
    //     }
        
    //     for (int i = 0; i < size; i++) {
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
    //     return new SparseMatrix(size, newMatrixCol);
    // }
    
    
    // public SparseMatrix multiply(SparseMatrix sm){
    //     SparseMatrix A = this;
    //     SparseMatrix B = sm.transpose();
    //     SparseMatrix result = new SparseMatrix(A.size);
        
    //     for (int i = 0; i < A.size; i++) {
    //         result.matrixRow[i] = new Node();
    //     }
        
    //     for (int i = 0; i < A.size; i++) {                        
    //         Node rItr = result.matrixRow[i];                     
            
    //         for (int j = 0; j < B.size; j++) {
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
}

class Node {
    int col;
    double value;
    Node next;
}
