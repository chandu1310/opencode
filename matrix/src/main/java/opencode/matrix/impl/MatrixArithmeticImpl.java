package opencode.matrix.impl;

import opencode.matrix.BaseImpl;
import opencode.matrix.Matrix;
import opencode.matrix.MatrixConstants.Operation;
import opencode.matrix.MatrixFactory;
import opencode.matrix.MessageTemplates;
import opencode.matrix.exceptions.ImpossibleOperationError;

/**
 * This class implements the arithmetic operations possible on matrices, defined in the interface {@link Matrix}
 * Do not use this class directly. See {@link MatrixFactory} for more detail on how to use.
 * @author Chandu
 */
public class MatrixArithmeticImpl extends BaseImpl {

	private static final long serialVersionUID = 4738069832997138283L;
	
	
	public MatrixArithmeticImpl() {
		this("M"+System.currentTimeMillis(), 1, 1);
	}
	
	public MatrixArithmeticImpl(String name, int rows, int cols){
		this._name = name;
		this._data = new double[rows][cols];
	}

	//SCALAR OPERATIONS
	final public Matrix add(double value){
		return add(value, String.format(MessageTemplates.SCALAR_ADDED_MATRIX_NAME, this._name, value));
	}
	final public Matrix add(double value, String resultName){
		return _evaluateScalarOperation(this, Operation.SUM, value, this).setName(resultName);
	}

	final public Matrix substract(double value){
		return substract(value, String.format(MessageTemplates.SCALAR_SUBSTRACTED_MATRIX_NAME, this._name, value));
	}
	final public Matrix substract(double value, String resultName){
		return _evaluateScalarOperation(this, Operation.MINUS, value, this).setName(resultName);
	}

	final public Matrix multiply(double value){
		return multiply(value, String.format(MessageTemplates.SCALAR_PRODUCT_MATRIX_NAME, this._name, value));
	}
	final public Matrix multiply(double value, String resultName){
		return _evaluateScalarOperation(this, Operation.PRODUCT, value, this).setName(resultName);
	}

	final public Matrix divide(double value){
		if(value==0){
			throw new ImpossibleOperationError(MessageTemplates.INVALID_DIVISOR_FOROPERATION);
		}
		return divide(value, String.format(MessageTemplates.SCALAR_DIVISION_MATRIX_NAME, this._name, value));
	}
	final public Matrix divide(double value, String resultName){
		if(value==0){
			throw new ImpossibleOperationError(MessageTemplates.INVALID_DIVISOR_FOROPERATION);
		}
		return _evaluateScalarOperation(this, Operation.DIVISION, value, this).setName(resultName);
	}

	private Matrix _evaluateScalarOperation(Matrix a, Operation op, double scalar, Matrix result){
		MatrixArithmeticImpl aImpl = (MatrixArithmeticImpl)a;
		MatrixArithmeticImpl resultImpl = (MatrixArithmeticImpl)result;
		for(int i=0; i<a.rows(); i++)
			for(int j=0; j<a.columns(); j++){
				switch (op) {
				case SUM:
					resultImpl._data[i][j] = aImpl._data[i][j] + scalar;
					break;
				case MINUS:
					resultImpl._data[i][j] = aImpl._data[i][j] - scalar;
					break;
				case PRODUCT:
					resultImpl._data[i][j] = aImpl._data[i][j] * scalar;
					break;
				case DIVISION:
					resultImpl._data[i][j] = aImpl._data[i][j] / scalar;
					break;
				}
			}
		return result;
	}
	
	///////// MATRIX OPERATIONS
	
	final public Matrix add(Matrix b){
		return add(b, String.format(MessageTemplates.ADDED_MATRIX_NAME, this._name, b.getName()));
	}
	final public Matrix add(Matrix b, String resultName){
		if(rows()==b.rows() && columns()==b.columns()){
			return _evaluateSumOrSub((MatrixArithmeticImpl)b, 1, this).setName(resultName);
		}
		throw new ImpossibleOperationError(MessageTemplates.UNMATCHING_MATRIX_SIZE);
	}

	final public Matrix substract(Matrix b){
		return substract(b, String.format(MessageTemplates.SUBSTRACTED_MATRIX_NAME, this._name, b.getName()));
	}
	final public Matrix substract(Matrix b, String resultName){
		if(rows()==b.rows() && columns()==b.columns()){
			return _evaluateSumOrSub((MatrixArithmeticImpl)b, -1, this).setName(resultName);
		}
		throw new ImpossibleOperationError(MessageTemplates.UNMATCHING_MATRIX_SIZE);
	}

	private MatrixArithmeticImpl _evaluateSumOrSub(Matrix b, int operation, Matrix value){
		MatrixArithmeticImpl aImpl = (MatrixArithmeticImpl)b;
		MatrixArithmeticImpl resultImpl = (MatrixArithmeticImpl)value;
		for(int i=0; i<rows(); i++)
			for(int j=0; j<columns(); j++){
				resultImpl._data[i][j] = this._data[i][j] + (operation* aImpl._data[i][j]);
			}
		return resultImpl;
	}
	
	final public Matrix multiply(Matrix b){
		return multiply(b, String.format(MessageTemplates.PRODUCT_MATRIX_NAME, this._name, b.getName()));
	}
	final public Matrix multiply(Matrix b, String resultName){
		if(columns()==b.rows()){
			double[][] product = new double[this.rows()][b.columns()];
			
			for(int i=0; i<this.rows(); i++){
				for(int j=0; j<b.columns(); j++){
					double sum = 0;
					for(int k=0; k<b.rows(); k++){
						sum += this.valueAt(i,k) * b.valueAt(k, j);
					}
					product[i][j] = sum; 
				}
			}
			return this.replace(product).setName(resultName);
		}
		throw new ImpossibleOperationError(MessageTemplates.UNMATCHING_MATRIX_SIZE);
	}
	
	
	final public Matrix multiplyPositionally(Matrix b){
		return multiplyPositionally(b, String.format(MessageTemplates.PRODUCT_MATRIX_NAME, this._name, b.getName()));
	}
	
	@Override
	public Matrix multiplyPositionally(Matrix b, String resultName) {
		if(rows()==b.rows() && columns()==b.columns()){
			for(int i=0; i<this.rows(); i++){
				for(int j=0; j<this.columns(); j++){
					this._data[i][j] = this._data[i][j] * b.valueAt(i, j);
				}
			}		
			
			return setName(resultName);
		}
		throw new ImpossibleOperationError(MessageTemplates.UNMATCHING_MATRIX_SIZE);
	}	
}
