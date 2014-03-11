/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tugas1_lama;

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
    
    public double[] hitungLU(double[][] A, double[] b){
        init(A,b);
        return this.b;
    }
    
    public SparseMatrix fowardElimination(){
        int size = sm.size;
        SparseMatrix L = new SparseMatrix(size);
        Node[] lItr = new Node[size];
        Node[] aItr = new Node[size];
        for (int i = 0; i < size; i++) {
            L.matrixRow[i] = new Node();
            lItr[i] = L.matrixRow[i];
            aItr[i] = sm.matrixRow[i];
        }
                        
        for (int i = 0; i < size; i++) {
            Node itrTmpItoJ = null;
            for (int j = i+1; j < size; j++) {
                Node tmp = new Node();
                tmp.col = aItr[j].col;
                tmp.value = aItr[j].value/aItr[i].value;
                lItr[j] = tmp;
                                
                //mbagi
                Node aItrDivisor = aItr[i];
                Node aItrNumerator = aItr[j];
                // aItrNumerator.value = 0;
                // aItrNumerator = aItrNumerator.next;
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
        
        for (int i = 0; i < size; i++) {
            Node identity = new Node();
            identity.value = 1;
            identity.col = i;
            lItr[i].next = identity;
        }        
        return L;
    }
    
	public SparseMatrix forwardSubstitutionPivot() throws Exception {
		int size = sm.size;
        SparseMatrix L = new SparseMatrix(size); //Matrix L
		int[] P = new int[size]; //Matrix permutasi
		Node[] lItr = new Node[size];
		for (int i = 0; i < size; i++) {
			P[i] = i;
            //L.matrixRow[i] = new Node();
            lItr[i] = L.matrixRow[i];
        }
		
		for (int kol = 0; kol < (size - 1); kol++) {
			//Cari baris dengan nilai terbesar pada kolom [kol]
			int maxRow = sm.searchMaxValueRow(kol);
			if (maxRow == -1) throw new Exception("Singular Matrix!");
			//Swap
			P[kol] ^= P[maxRow];
			P[maxRow] ^= P[kol];
			P[kol] ^= P[maxRow];
			Node tmp = sm.matrixRow[kol];
			sm.matrixRow[kol] = sm.matrixRow[maxRow];
			sm.matrixRow[maxRow] = tmp;
			
			//memulai mencari nilai matrix L di bawah diagonal kolom ke-kol
			for (int baris = kol + 1; baris < size; baris++) {
				lItr[baris] = L.matrixRow[baris];
//                                while (lItr[baris].col < kol) {
//                                    if (lItr[baris].next == null) {
//                                        Node newL = new Node();
//                                        newL.col = kol;
//                                        lItr[baris].next = newL;
//                                    }
//                                    lItr[baris] = lItr[baris].next;
//                                }
				Node aIJ = sm.matrixRow[baris];
				Node aKJ = sm.matrixRow[kol];
				Node aKK = sm.matrixRow[kol];
				if (aIJ.col == kol) {
					while (aKK.col < kol) {
						aKK = aKK.next;
					}
					Node nodeL = new Node();
					nodeL.value = aIJ.value/aKK.value;
					nodeL.col = sm.matrixRow[baris].col;
                                        //dibawah ini masih aneh
                                        if (lItr[baris] == null) {
                                            L.matrixRow[baris] = nodeL;
                                        } else {
                                            while (lItr[baris].col < kol) {
                                                lItr[baris] = lItr[baris].next;
                                                if (lItr[baris].next == null) {
                                                    break;
                                                }
                                            }
                                            lItr[baris] = nodeL;
                                        }
                                        //diatas ini masih aneh
				
				//Terapkan rumus A(i,j) = A(i,j) - L(i,k)*A(k,j);
					while(aIJ != null) {
						if (aIJ.col == aKJ.col) {
							aIJ.value = aIJ.value - nodeL.value*aKJ.value;
							aIJ = aIJ.next;
							aKJ = aKJ.next;
						} else if (aIJ.col > aKJ.col) {
							aIJ.value = (-nodeL.value*aKJ.value);
							aKJ = aKJ.next;
						} else {
							aIJ = aIJ.next;
						}
					}
				}
			}
			
		}
		return L;
	}
                
    
    public void backwardSubs(){
        
    }
}