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
    Node[] matrixColumn;
    int n;        
    

        
    public SparseMatrix(int n, double[][] value){
        this.n = n;
        matrixColumn = new Node[n];
        for (int i = 0; i < n; i++) {            
            matrixColumn[i] = new Node();
            Node itr = matrixColumn[i];
            for (int j = 0; j < n; j++) {
                if(value[i][j] != 0){                                        
                    Node x = new Node();
                    x.col = j;
                    x.value = value[i][j];                                        
                    itr.next = x;
                    itr = itr.next;                    
                }
            }
        }
    }
    
    public SparseMatrix(int n, Node[] matrixColumn){
        this.n = n;
        this.matrixColumn=matrixColumn;
    }
    public SparseMatrix(int n){
        this.n = n;
        this.matrixColumn=new Node[n];
    }
    
    public SparseMatrix transpose(){
        Node[] newMatrixCol = new Node[n];
        Node[] newMatrixItr = new Node[n];
        for (int i = 0; i < n; i++) {
            newMatrixCol[i] = new Node(); 
            newMatrixItr[i] = newMatrixCol[i];
        }
        
        for (int i = 0; i < n; i++) {
            Node oldMatrixItr = matrixColumn[i].next;            
            while(oldMatrixItr != null){             
                Node tmp = new Node();
                tmp.value = oldMatrixItr.value;
                tmp.col = i;                              
                
                newMatrixItr[oldMatrixItr.col].next = tmp;                
                
                newMatrixItr[oldMatrixItr.col] = newMatrixItr[oldMatrixItr.col].next;
                oldMatrixItr = oldMatrixItr.next;
            }
        }        
        return new SparseMatrix(n, newMatrixCol);
    }
    
    
    public SparseMatrix multiply(SparseMatrix sm){
        SparseMatrix A = this;
        SparseMatrix B = sm.transpose();
        SparseMatrix result = new SparseMatrix(A.n);
        
        for (int i = 0; i < A.n; i++) {
            result.matrixColumn[i] = new Node();
        }
        
        for (int i = 0; i < A.n; i++) {                        
            Node rItr = result.matrixColumn[i];                     
            
            for (int j = 0; j < B.n; j++) {
                double sum = 0;
                Node aItr = A.matrixColumn[i].next;
                Node bItr = B.matrixColumn[j].next;                                                
                while(aItr != null && bItr != null){                    
                    int aCol = aItr.col;
                    int bCol = bItr.col;
                    if(aCol == bCol){
                        sum += aItr.value * bItr.value;
                        aItr = aItr.next;
                        bItr = bItr.next;                        
                    }else if(aCol < bCol){
                        aItr = aItr.next;
                    }else{
                        bItr = bItr.next;
                    }
                }
                if(sum != 0){
                    Node tmp = new Node();
                    tmp.value = sum;
                    tmp.col = j;
                    rItr.next = tmp;                
                    rItr = rItr.next;
                }
            }                        
        }
        
        
        return result;
    }
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < matrixColumn.length; i++) {
            Node itr = matrixColumn[i];
            while(itr.next != null){
                itr = itr.next;
                sb.append(itr.value).append(";").append(itr.col).append(" ");                                
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
