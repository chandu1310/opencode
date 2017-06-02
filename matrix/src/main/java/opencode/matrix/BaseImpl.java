package opencode.matrix;

import java.io.OutputStream;

import opencode.matrix.exceptions.ImpossibleOperationError;

public class BaseImpl implements Matrix {

	private static final long serialVersionUID = -1254925964735647619L;

	public Matrix setName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int rows() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int columns() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double[] row(int rowNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public double[] col(int colNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix load(double[][] values) {
		// TODO Auto-generated method stub
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

	public Matrix print(OutputStream out) {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix print() {
		// TODO Auto-generated method stub
		return null;
	}

	public Matrix transform(Transformer function) {
		// TODO Auto-generated method stub
		return null;
	}

	public double valueAt(int rowNumber, int colNumber) throws ImpossibleOperationError {
		// TODO Auto-generated method stub
		return 0;
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

}
