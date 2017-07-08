package opencode.matrix;

import org.junit.BeforeClass;
import org.junit.Test;

import opencode.matrix.impl.Matrix2D;

public class MatrixTest {
	@BeforeClass
	public static void init(){
		MatrixConfig.DISPLAY_LOG = true;
	}
	
	@Test
	public void testMatrixMultiplication(){
		Matrix a = Matrix2D.instance("A", new double[][]{{1,2},{3,4}});
		a.print();
		a.multiply(a);
		a.print();
	}
	
	@Test
	public void testCombineIntoRowMatrix(){
		Matrix a = Matrix2D.instance("A", new double[][]{{1,2},{3,4}});
		a.print();
		a.combineIntoRowMatrix(Combiner.AGGREGATOR);
		a.print();
	}
	
	@Test
	public void testCombineIntoColMatrix(){
		Matrix a = Matrix2D.instance("A", new double[][]{{1,2},{3,4}});
		a.print();
		a.combineIntoColumnMatrix(Combiner.AGGREGATOR);
		a.print();
	}
	
}
