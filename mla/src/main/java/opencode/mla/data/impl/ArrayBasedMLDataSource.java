package opencode.mla.data.impl;

import opencode.mla.data.MLDataSource;
import opencode.mla.data.MLDataSourceException;

public class ArrayBasedMLDataSource implements MLDataSource{
	private double[][] data;
	private int currentPosition = 0;
	
	public ArrayBasedMLDataSource(double[] x, double y) {
		this.data = new double[x.length][2];
		
		for(int i=0; i<x.length; i++){
			this.data[i][0] = x[i];
			this.data[i][1] = y;
		}
	}

	public ArrayBasedMLDataSource(double x, double[] y) {
		this.data = new double[y.length][2];
		for(int i=0; i<y.length; i++){
			this.data[i][0] = x;
			this.data[i][1] = y[i];
		}
	}

	public ArrayBasedMLDataSource(double[] x, double[] y) {
		this.data = new double[x.length][2];
		for(int i=0; i<x.length; i++){
			this.data[i][0] = x[i];
			this.data[i][1] = y[i];
		}
	}

	public ArrayBasedMLDataSource(double[][] xy) {
		this.data = xy;
	}

	
	@Override
	public MLDataSource init() throws MLDataSourceException {
		return this;
	}

	@Override
	public int getHeaderCount() {
		return 2;
	}

	@Override
	public String[] getHeaders() {
		return new String[2];
	}

	@Override
	public long getEntriesCount() {
		return this.data.length;
	}

	@Override
	public long getReadCount() {
		return this.currentPosition;
	}

	@Override
	public boolean hasNext() throws MLDataSourceException {
		return currentPosition<this.data.length;
	}

	@Override
	public String[] getNext() throws MLDataSourceException {
		return new String[]{
					String.valueOf(this.data[currentPosition][0]), 
					String.valueOf(this.data[currentPosition][1])
				};
	}

	@Override
	public double[] getNextAsNumbers() throws MLDataSourceException {
		if(hasNext()){
			this.currentPosition++;
			return this.data[currentPosition-1];
		}else{
			throw new MLDataSourceException("Invalid operation. No more data to read.");
		}
	}

	@Override
	public MLDataSource reset() throws MLDataSourceException {
		this.currentPosition = 0;
		return this;
	}

	@Override
	public MLDataSource forward() throws MLDataSourceException {
		this.currentPosition++;
		return this;
	}

	@Override
	public MLDataSource forwardTillEnd() throws MLDataSourceException {
		this.currentPosition = this.data.length;
		return this;
	}
}
