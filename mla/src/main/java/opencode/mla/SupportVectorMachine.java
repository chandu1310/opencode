package opencode.mla;

import opencode.mla.data.MLDataSourceConfig;
import opencode.mla.data.MLDataSourceException;
import opencode.mla.data.impl.CSVFileBasedMLDataSource;

public class SupportVectorMachine {
	private CSVFileBasedMLDataSource dataSource;
	private double slope=0, yIntercept=0;
	
	public SupportVectorMachine(String dataFileName) throws MLDataSourceException {
		MLDataSourceConfig dataSourceConfig = new MLDataSourceConfig();
		this.dataSource = new CSVFileBasedMLDataSource(dataFileName, dataSourceConfig);
	}
	
	public void optimize(long numberOfIterations) throws MLAException {
		for(long i=0; i<numberOfIterations; i++){
			try {
				stepGradient();
			} catch (Exception e) {
				throw new MLAException("Unable to complete optimization due to an exception.", e);
			}
		}
	}
	
	public double predict(double x){
		return getFunctionValue(this.slope, this.yIntercept, x);
	}
	
	public double findErrorForGivenState() throws MLAException{
		try{
			double totalError = 0;
			this.dataSource.reset();
			while (this.dataSource.hasNext()) {
				double[] data = dataSource.getNextAsNumbers();
				totalError += Math.pow(
						(data[1] - getFunctionValue(this.slope, this.yIntercept, data[0]))
						, 2); 
			}
			return totalError / this.dataSource.getReadCount();
		}catch (MLDataSourceException e) {
			throw new MLAException("Unable to determine total error due to an exception.", e);
		}
	}
	
	private void stepGradient() throws MLAException {
		try{
			double slopeGradient = 0, yInterceptGradient = 0;
			this.dataSource.forwardTillEnd();
			long n = this.dataSource.getReadCount();
			
			while (this.dataSource.hasNext()) {
				double[] data = this.dataSource.getNextAsNumbers();
				double delta = data[1] - getFunctionValue(this.slope, this.yIntercept, data[0]);
				slopeGradient += (-2*data[0]*delta)/n;  // Derivative of error function (y-y')^2 wrt slope
				yInterceptGradient += (-2*delta)/n;     // Derivative of error function (y-y')^2 wrt yIntercept
			}
			
			this.slope = this.slope - slopeGradient;
			this.yIntercept = this.yIntercept - yInterceptGradient;
		}catch (MLDataSourceException e) {
			throw new MLAException("Unable to determine gradient due to an exception.", e);
		}
	}
	
	private double getFunctionValue(double slope, double yIntercept, double x){
		return (slope * x) + yIntercept;
	}
}
