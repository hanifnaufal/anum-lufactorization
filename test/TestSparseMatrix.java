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
        double[][] A = {{1, 0, 0, 0},
                        {2, 1, 0, 0},
                        {0, 5, 1, 0},
                        {1, 3, 0, 1}};
        double[] B = {1,3,6,5};
        SparseMatrix a = new SparseMatrix(A);
        SparseMatrix b = new SparseMatrix(B);

        SparseSolver x = new SparseSolver();
        x.init(A,B);
        SparseMatrix res = x.forwardElimination(a,b);

        for(int i=0; i<4; i++){
            Assert.assertEquals("nilai indeks ke-"+i+" berbeda", 1.0, res.getElement(i,0), 0.000001);
        }
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
        Assert.assertArrayEquals(Ap,a.toDenseMatrix());
    }




}
