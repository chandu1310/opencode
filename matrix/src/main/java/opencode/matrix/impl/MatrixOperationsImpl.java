package opencode.matrix.impl;

import opencode.matrix.Combiner;
import opencode.matrix.Matrix;
import opencode.matrix.MatrixFactory;
import opencode.matrix.MessageTemplates;
import opencode.matrix.Transformer;
import opencode.matrix.exceptions.ImpossibleOperationError;

/**
 * This class implements the matrix operations , defined in the interface {@link Matrix}
 * Do not use this class directly. See {@link MatrixFactory} for more detail on how to use.
 * @author Chandu
 */
public class MatrixOperationsImpl extends MatrixArithmeticImpl {

	private static final long serialVersionUID = 7110620697951945958L;

	public MatrixOperationsImpl(String name, int rows, int cols){
		super(name, rows, cols);
	}

	@Override
	public Matrix transpose() {
		double[][] transposeData = new double[columns()][rows()];
		for(int i=0; i<rows(); i++)
			for(int j=0; j<columns(); j++){
				transposeData[j][i] = this._data[i][j];
			}
//		this._data = transposeData;
		return Matrix2D.instance("Trans("+this._name+")", transposeData);
	}
	
	@Override
	public Matrix transform(Transformer function) {
		for(int i=0;i< rows(); i++)
			for(int j=0; j<columns(); j++){
				super._data[i][j] = function.apply(super._data[i][j]);
			}
		return this;
	}
	
	@Override
	public Matrix slice(int lastRow, int lastColumn, String resultName) throws ImpossibleOperationError{
		return slice(0, 0, lastRow, lastColumn, resultName);
	}
	
	@Override
	public Matrix slice(int startRow, int startColumn, int lastRow, int lastColumn, String resultName) throws ImpossibleOperationError{
		if(startRow>=0 && startColumn>=0 
		 && lastRow>0 && lastColumn>0 
		 && startRow<=rows() && startColumn<=columns() 
		 && lastRow<=rows() && lastColumn<=columns()) {
			double[][] slicedData = new double[lastRow-startRow][lastColumn-startColumn];
			for(int i=startRow; i<lastRow; i++)
				for(int j=startColumn; j<lastColumn; j++){
					slicedData[i-startRow][j-startColumn] = this._data[i][j]; 
				}
			Matrix slicledMatrix = this.create(resultName, lastRow-startRow, lastColumn-startColumn);
			slicledMatrix.load(slicedData);
			return slicledMatrix;
		}
		
		throw new ImpossibleOperationError(MessageTemplates.INVALID_SIZE_FOR_SLICING);
	}
	
	@Override
	public Matrix merge(Matrix b, boolean isHorizontal) throws ImpossibleOperationError {
		return this.merge(b, isHorizontal, getName()+"-MergedWith-"+b.getName());
	}
	
	@Override
	public Matrix merge(Matrix b, boolean isHorizontal, String resultName) throws ImpossibleOperationError {
		if( (isHorizontal && rows() != b.rows()) || (!isHorizontal && columns() != b.columns()) )
			throw new ImpossibleOperationError(MessageTemplates.INVALID_SIZE_FOR_MERGING);
		
		double[][] mergedData;
		if(isHorizontal){
			mergedData = new double[rows()][columns()+b.columns()];
		}else{
			mergedData = new double[rows()+b.rows()][columns()];
		}
		
		//Copy src first
		for(int i=0; i<rows(); i++)
			for(int j=0; j<columns(); j++){
				mergedData[i][j] = _data[i][j];
			}

		//Copy merging data.
		for(int i=0; i<b.rows(); i++)
			for(int j=0; j<b.columns(); j++){
				if(isHorizontal)
					mergedData[i][j+columns()] =  b.valueAt(i, j);
				else
					mergedData[i+rows()][j] = b.valueAt(i, j);
			}

		return this.create(resultName, mergedData.length, mergedData.length==0?0:mergedData[0].length).load(mergedData);
	}
	
	@Override
	public Matrix combineIntoRowMatrix(Combiner function) {
		double[][] d = new double[1][this.columns()];
		
		for(int i=1; i<=this.columns(); i++){
			d[0][i-1] = function.apply(this.col(i));
		}
		
		this._data = d;
		setName(getName()+":CombinedAsRow");
		return this;
	}
	
	@Override
	public Matrix combineIntoColumnMatrix(Combiner function) {
		double[][] d = new double[this.rows()][1];
		
		for(int i=1; i<=this.rows(); i++){
			d[i-1][0] = function.apply(this.row(i));
		}
		
		this._data = d;
		setName(getName()+":CombinedAsColumn");
		return this;
	}
}
