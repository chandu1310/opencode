package opencode.matrix.impl;

import opencode.matrix.Matrix;
import opencode.matrix.MatrixFactory;
import opencode.matrix.MessageTemplates;
import opencode.matrix.exceptions.ImpossibleOperationError;
import opencode.matrix.util.Helper;

/**
 * This class is the entry point for {@link MatrixFactory}. 
 * It hides all the classes in the implementation hierarchy.
 * It inherits static methods "create" needed for creating {@link Matrix} instances.
 * Check {@link MatrixFactory} for more details on how to get a {@link Matrix} instance.
 * @author Chandu
 */
final public class Matrix2D extends MatrixOperationsImpl {
	
	private static final long serialVersionUID = -6794749877667422417L;

	private Matrix2D(String name, int rows, int cols) {
		super(name, rows, cols);
	}

	@Override
	public Matrix create(String name, int rows, int cols){
		if(rows==0 || cols==0){
			throw new ImpossibleOperationError(String.format(MessageTemplates.UNACCEPTABLE_MATRIX_SIZE, rows, cols));
		}
		return new Matrix2D(name, rows, cols); 
	}

	@Override
	public Matrix copy() {
		return this.copy("CopyOf"+this.getName());
	}
	
	@Override
	public Matrix copy(String name) {
		Matrix _copy = 	create(this._name, this.rows(), this.columns()).load(Helper.createCopy(this._data));
		return _copy.setName(name);
	}
	
	public static Matrix instance(String name, int rows, int cols){
		if(rows==0 || cols==0){
			throw new ImpossibleOperationError(String.format(MessageTemplates.UNACCEPTABLE_MATRIX_SIZE, rows, cols));
		}
		return new Matrix2D(name, rows, cols); 
	}

	public static Matrix instance(String name, double[][] values){
		if(values.length==0 || values[0].length==0){
			throw new ImpossibleOperationError(String.format(MessageTemplates.UNACCEPTABLE_MATRIX_SIZE, values.length, values[0].length));
		}
		
		Matrix2D m = new Matrix2D(name, values.length, values[0].length);
		m._data = values;
		return m; 
	}
}
