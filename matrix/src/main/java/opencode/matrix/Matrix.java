package opencode.matrix;

import java.io.OutputStream;
import java.io.Serializable;

import opencode.matrix.exceptions.ImpossibleOperationError;
import opencode.matrix.impl.MatrixArithmeticImpl;

/**
 * Principal interface which models a 2-Dimensional Matrix structure.
 * The operations are divided in to various categories and are implemented in relevant impl classes.
 * For more details:
 * - Check {@link MatrixArithmeticImpl} for arithmetic operations implementation.
 * 
 * Also check {@link MatrixFactory} to know how to get instances of Matrix.
 * 
 * @author Chandu
 */
public interface Matrix extends Serializable {
	//IDENTITY OPERATIONS
	public Matrix setName(String name);
	public String getName();

	
	//DATA INFO AND MANAGEMENT OPERATIONS
	public int rows();
	public int columns();
	public double[] row(int rowNumber);
	public double[] col(int colNumber);
	public double valueAt(int rowNumber, int colNumber) throws ImpossibleOperationError;
	public Matrix create(String name, int rows, int cols);
	public Matrix load(double[][] values);
	public Matrix copy(String name);
	public Matrix copy();
	
	//SCALAR OPERATIONS
	public Matrix add(double value);
	public Matrix add(double value, String resultName);
	public Matrix substract(double value);
	public Matrix substract(double value, String resultName);
	public Matrix multiply(double value);
	public Matrix multiply(double value, String resultName);
	public Matrix divide(double value);
	public Matrix divide(double value, String resultName);
	
	//ARITHMETIC OPERATIONS
	public Matrix add(Matrix toBeAddedMatrix);
	public Matrix add(Matrix toBeAddedMatrix, String resultName);
	public Matrix substract(Matrix toBeAddedMatrix);
	public Matrix substract(Matrix toBeAddedMatrix, String resultName);
	public Matrix multiply(Matrix toBeAddedMatrix);
	public Matrix multiply(Matrix toBeAddedMatrix, String resultName);
	public Matrix print(OutputStream out);
	public Matrix print();
	
	//MATRIX OPERATIONS
	public Matrix transpose();
	public Matrix transform(Transformer function);
	public Matrix slice(int lastRow, int lastColumn, String resultName) throws ImpossibleOperationError;
	public Matrix slice(int startRow, int startColumn, int lastRow, int lastColumn, String resultName) throws ImpossibleOperationError;
	public Matrix merge(Matrix b, boolean isHorizontal) throws ImpossibleOperationError;
	public Matrix merge(Matrix b, boolean isHorizontal, String resultName) throws ImpossibleOperationError;
}
