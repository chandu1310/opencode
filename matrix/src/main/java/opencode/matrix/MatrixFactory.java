package opencode.matrix;

import opencode.matrix.impl.Matrix2D;

final public class MatrixFactory {
	final public static Matrix create(String name, int row, int col){
		return Matrix2D.instance(name, row, col);
	}

	final public static Matrix create(String name, double[][] values){
		return Matrix2D.instance(name, values);
	}
	
}
