package opencode.matrix;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import opencode.matrix.exceptions.ImpossibleOperationError;
import opencode.matrix.util.Helper;

public class BaseImpl implements Matrix {

	private static final long serialVersionUID = -1254925964735647619L;

	protected String _name;
	
	protected double[][] _data = null;
	
	public boolean isZeroMatrix() {
		for(int i=0; i< rows(); i++)
			for(int j=0; j<columns(); j++){
				if(_data[i][j]!=0)
					return false;
			}
		return true;
	}
	
	public boolean isEqualTo(Matrix m) {
		if(this.isReplaceableWith(m) && this.substract(m).isZeroMatrix())
				return true;
		else
			return false;
	}
	
	public boolean isReplaceableWith(Matrix m) {
		if(this.rows()==m.rows() && this.columns()==m.columns())
			return true;
		return false;
	}
	
	public void initialize(Initializer func) {
		for(int i=0; i < _data.length; i++){
			for(int j=0; j < _data[i].length; j++){
				_data[i][j] = func.apply(i, j);
			}
		}
	}
		
	final public String getName(){
		return this._name;
	}
	
	final public Matrix setName(String name){
		this._name = name;
		return this;
	}
	
	final public int rows(){
		return this._data.length;
	}

	final public int columns(){
		return this._data[0].length;
	}

	//Accepts [1, n]
	final public double[] row(int rowNumber){
		if(rowNumber<1 || rowNumber > _data.length){
			throw new ImpossibleOperationError(String.format(MessageTemplates.INVALID_ROW, rowNumber));
		}
		return this._data[rowNumber-1];
	}

	final public double[] col(int colNumber){
		int colSize = row(1).length;
		if(colNumber<1 || colNumber > colSize){
			throw new ImpossibleOperationError(String.format(MessageTemplates.INVALID_COLUMN, colNumber));
		}
		
		double[] colData = new double[this._data.length]; 
		for(int i=0; i<_data.length; i++){
			colData[i] = _data[i][colNumber-1];
		}
		return colData;
	}

	public double[][] valuesAsArray() {
		return this._data;
	}
	
	public double valueAt(int rowNumber, int colNumber) throws ImpossibleOperationError {
		if(rowNumber < 0 || rowNumber >= _data.length || colNumber < 0 || colNumber >= _data[0].length){
			throw new ImpossibleOperationError(String.format(MessageTemplates.INVALID_ROW_COLUMN, rowNumber, colNumber));
		}
		return this._data[rowNumber][colNumber];
	}
	
	final public Matrix load(double[][] values){
		if(_data.length!=values.length || _data[0].length!=values[0].length){
			throw new ImpossibleOperationError(MessageTemplates.UNMATCHING_MATRIX_SIZE);
		}
		this._data = values;
		return this;
	}	

	final public Matrix replace(double[][] values){
		if(values==null || values.length==0){
			throw new ImpossibleOperationError(String.format(MessageTemplates.UNACCEPTABLE_MATRIX_SIZE, 0, 0));
		}
		this._data = values;
		return this;
	}	
	
	final public Matrix replace(Matrix m){
		if(m==null || m.rows()==0 || m.columns()==0){
			throw new ImpossibleOperationError(String.format(MessageTemplates.UNACCEPTABLE_MATRIX_SIZE, 0, 0));
		}
		
		this._data = m.valuesAsArray();
		return this;
	}	

	public Matrix load(Matrix m) {
		this.load(Helper.createCopy(m.valuesAsArray()));
		return null;
	}

	public Matrix add(double value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix add(double value, String resultName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix substract(double value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix substract(double value, String resultName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix multiply(double value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix multiply(double value, String resultName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix divide(double value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix divide(double value, String resultName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix add(Matrix toBeAddedMatrix) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix add(Matrix toBeAddedMatrix, String resultName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix substract(Matrix toBeAddedMatrix) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix substract(Matrix toBeAddedMatrix, String resultName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix multiply(Matrix toBeAddedMatrix) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix multiply(Matrix toBeAddedMatrix, String resultName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix multiplyPositionally(Matrix toBeMultipliedMatrix){
		return null;
	}
	
	public Matrix multiplyPositionally(Matrix toBeMultipliedMatrix, String resultName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	final public Matrix print(OutputStream out){
		if(MatrixConfig.DISPLAY_LOG){
			PrintStream ps;
			if(!(out instanceof PrintStream)){
				ps = new PrintStream(out);
			}else{
				ps = (PrintStream)out;
			}
			
			ps.print("\n"+this._name+": \n");
			for(int i=0; i<_data.length; i++){
				for(int j=0; j<_data[i].length; j++){
					ps.printf("%.16f ", _data[i][j]);
				}
				ps.print("\n");
			}
			ps.flush();
		}
		return this;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(double[] w: _data){
			sb.append("\n   "+Arrays.toString(w));
		}
		return sb.toString();
	}
	
	final public Matrix print() {
		if(MatrixConfig.DISPLAY_LOG)
			print(System.out);
		
		return this;
	}

	public Matrix transform(Transformer function) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix create(String name, int rows, int cols) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix copy(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix copy() {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix transpose() {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix slice(int lastRow, int lastColumn, String resultName) throws ImpossibleOperationError {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix slice(int startRow, int startColumn, int lastRow, int lastColumn, String resultName)
			throws ImpossibleOperationError {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix merge(Matrix b, boolean isHorizontal) throws ImpossibleOperationError {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix merge(Matrix b, boolean isHorizontal, String resultName) throws ImpossibleOperationError {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix combineIntoColumnMatrix(Combiner function) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Matrix combineIntoRowMatrix(Combiner function) {
		// TODO Auto-generated method stub
		return null;
	}
}
