import Tugas1.SparseMatrix;
import Tugas1.SparseSolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TestSparseMatrix {

    @Test
    public void testSparseMatrix(){
        double[][] A = {{4, 0, 1, 0},
                {2, 1, 0, 3},
                {0, 5, 0, 0},
                {1, 0, 0, 1}};
        SparseMatrix a = new SparseMatrix(A);
        Assert.assertTrue("Size column salah", a.colSize == 4);
        Assert.assertTrue("Size row salah", a.rowSize == 4);
        Assert.assertArrayEquals(A,a.toDenseMatrix());
    }

    @Test
    public void testForwardElimination(){
        double[][] A = {{4, 0, 0, 0},
                        {2, 1, 0, 0},
                        {0, 5, 2, 0},
                        {1, 3, 0, 1}};
        double[][] B = {{8},{6},{14},{10}};
        SparseMatrix a = new SparseMatrix(A);
        SparseMatrix b = new SparseMatrix(B);

        SparseSolver x = new SparseSolver();
        SparseMatrix res = x.forwardElimination(a,b);

        for(int i=0; i<4; i++){
            Assert.assertEquals("nilai indeks ke-"+i+" berbeda", 2.0, res.getElement(i,0), 0.000001);
        }
    }

    @Test
    public void testSwapElement(){
        double[][] A = {{4, 0, 1, 0},
                {2, 1, 0, 3},
                {0, 5, 0, 0},
                {1, 0, 0, 1}};
        double[][] Ares = {{2, 1, 0, 3},
                {4, 0, 1, 0},
                {0, 5, 0, 0},
                {1, 0, 0, 1}};
        SparseMatrix a = new SparseMatrix(A);
        a.swapElement(0,1);
        Assert.assertArrayEquals(Ares,a.toDenseMatrix());
    }

    @Test
    public void testSwapElementOne(){
        double[][] A = {{4, 0, 1, 0},
                {2, 1, 0, 3},
                {0, 5, 0, 0},
                {1, 0, 0, 1}};
        double[][] Ares = {{2, 0, 1, 0},
                {4, 1, 0, 3},
                {0, 5, 0, 0},
                {1, 0, 0, 1}};
        SparseMatrix a = new SparseMatrix(A);
        a.swapElement(0,1,1);
        Assert.assertArrayEquals(Ares,a.toDenseMatrix());
    }

    @Test
    public void testPermute(){
        double[][] A = {
                {4, 0, 1, 0},
                {2, 1, 0, 3},
                {0, 5, 0, 0},
                {1, 0, 0, 1}};
        int[] Pm = {3,1,0,2};
        double[][] Ap = {{1, 0, 0, 1},
                {2, 1, 0, 3},
                {4, 0, 1, 0},
                {0, 5, 0, 0}};
        SparseMatrix a = new SparseMatrix(A);
        a.permute(Pm);
        System.out.println(a);
        Assert.assertArrayEquals(Ap,a.toDenseMatrix());
    }




}
