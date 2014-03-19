/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Tugas1;

/**
 *
 * @author Anum A07
 */
public class SparseMatrix2 {

    class NodeList<T> {

        Node<T> head;

        public void add(T value) {
            if (head == null) {
                head = new Node(value);
                return;
            }
            Node tmp = head;
            while (tmp.next != null) {
                tmp = tmp.next;
            }
            tmp.next = new Node(value);
        }

        public void add(int i, T value) {
            if (i == 0) {
                Node tmp = head;
                head = new Node(value);
                head.next = tmp;
                return;
            }

            Node itr = head;
            for (int j = 0; j < i - 1; j++) {
                itr = itr.next;
            }
            Node newN = new Node(value);
            newN.next = itr.next;
            itr.next = newN;
        }

        public T get(int i) {
            Node itr = head;
            for (int j = 0; j < i; j++) {
                itr = itr.next;
            }
            return (T) itr.value;
        }

        public void set(int i, T value) {
            Node itr = head;
            for (int j = 0; j < i; j++) {
                itr = itr.next;
            }
            itr.value = value;
        }

        public void remove(int i) {
            if (i == 0) {
                head = head.next;
                return;
            }

            Node itr = head;
            for (int j = 0; j < i - 1; j++) {
                itr = itr.next;
            }
            itr.next = itr.next.next;
        }
    }

    class Node<T> {

        Node next;
        T value;

        public Node(T value) {
            this.value = value;
        }
    }

    public int rowSize;
    public int colSize;
    public int[] P;
    public NodeList<Integer> I;
    public NodeList<Double> X;

    /**
     * fungsi untuk membuat Sparse Matrix identitas
     *
     * @param size panjang baris dan kolom matriks
     * @return matriks indentitas
     */
    public static SparseMatrix2 eye(int size) {
        SparseMatrix2 result = new SparseMatrix2(size);
        for (int i = 0; i < size; i++) {
            result.P[i] = i;
            result.I.add(i);
            result.X.add(1.0);
        }
        result.P[size] = size;
        return result;
    }

    public static SparseMatrix2 zeros(int size) {
        return new SparseMatrix2(size);
    }

    public SparseMatrix2(double[][] A) {
        this.colSize = A[0].length;
        this.rowSize = A.length;
        P = new int[colSize + 1];
        I = new NodeList<Integer>();
        X = new NodeList<Double>();
        createMatrix(A);
    }

    public SparseMatrix2(double[] b) {
        this(b.length, 1);
        for (int i = 0; i < b.length; i++) {
            setElement(i, 0, b[i]);
        }
    }

    public SparseMatrix2(int size) {
        this.rowSize = this.colSize = size;
        P = new int[colSize + 1];
        I = new NodeList<Integer>();
        X = new NodeList<Double>();
    }

    public SparseMatrix2(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
        P = new int[colSize + 1];
        I = new NodeList<Integer>();
        X = new NodeList<Double>();
    }

    /**
     * Membuat representasi sparse matriks dari matriks double[][]
     *
     * @param A representasi double[][]
     */
    private void createMatrix(double[][] A) {
        int numItemAdded = 0;
        for (int i = 0; i < A[0].length; i++) {
            P[i] = numItemAdded;
            for (int j = 0; j < A.length; j++) {
                if (A[j][i] != 0.0) {
                    I.add(j);
                    X.add(A[j][i]);
                    numItemAdded++;
                }
            }
        }
        P[P.length - 1] = numItemAdded;
    }

    public double getElement(int row, int col) {
        for (int i = P[col]; i < P[col + 1]; i++) {
            if (I.get(i) == row) {
                return X.get(i);
            }
        }
        return 0.0;
    }

    public SparseMatrix2 getColumn(int col) {
        SparseMatrix2 result = new SparseMatrix2(this.rowSize, 1);
        result.P[1] = this.P[col + 1] - this.P[col];

        for (int i = this.P[col]; i < this.P[col + 1]; i++) {
            result.I.add(this.I.get(i));
            result.X.add(this.X.get(i));
        }
        return result;
    }

    public int getIndex(int row, int col) {
        for (int i = P[col]; i < P[col + 1]; i++) {
            if (I.get(i) == row) {
                return i;
            }
        }
        return -1;
    }

    public int searchMaxRowOneColumn(int rowStart) {
        double max = Double.MIN_VALUE;
        int maxRow = -1;
        for (int i = P[0]; i < P[1]; i++) {
            if (I.get(i) >= rowStart) {
                double val = Math.abs(X.get(i));
                if (val > max) {
                    max = val;
                    maxRow = I.get(i);
                }
            }
        }
        return maxRow;
    }

    public void swapElement(int row1, int row2, int n) {
        //System.out.println(this);
        for (int i = 0; i < n; i++) {
            for (int j = P[i]; j < P[i + 1]; j++) {
                if (I.get(j) == row1) {
                    I.set(j, row2);
                } else if (I.get(j) == row2) {
                    I.set(j, row1);
                }
            }
        }
        //System.out.println(this);
    }

    public void swapElement(int row1, int row2) {
        swapElement(row1, row2, colSize);
    }

    public void permute(int[] pM) {
        int[] invpM = new int[pM.length];

        for (int i = 0; i < pM.length; i++) {
            invpM[pM[i]] = i; //invpM => index = value, value = index
        }
        for (int i = 0; i < colSize; i++) { // for each column
            for (int j = P[i]; j < P[i + 1]; j++) { //for each available row
                I.set(j, invpM[I.get(j)]); //I[row] = inv[I[row]]
            }
        }
    }

    public void removeElement(int row, int col) {
        for (int i = P[col]; i < P[col + 1]; i++) {
            if (I.get(i) == row) {
                X.remove(i);
                I.remove(i);
                for (int j = col + 1; j < P.length; j++) {
                    P[j]--;
                }
            }
        }
    }

    public void setElement(int row, int col, double val) {
        if (Math.abs(val) < 1e-10) {
            removeElement(row, col);
            return;
        }
        for (int i = P[col]; i < P[col + 1]; i++) {
            if (I.get(i) == row) {
                X.set(i, val);
                return;
            }
        }
        I.add(P[col + 1], row);
        X.add(P[col + 1], val);
        for (int j = col + 1; j < P.length; j++) {
            P[j]++;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                sb.append(String.format("%10.5f", getElement(i, j))).append(";");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public double[][] toDenseMatrix() {
        double[][] result = new double[rowSize][colSize];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = getElement(i, j);
            }
        }
        return result;
    }
}
