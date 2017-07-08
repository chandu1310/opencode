package opencode.mla;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import opencode.mla.data.MLDataSource;
import opencode.mla.data.MLDataSourceConfig;
import opencode.mla.data.MLDataSourceException;
import opencode.mla.data.impl.CSVFileBasedMLDataSource;

public class SVM {

	private static final Logger logger = Logger.getLogger(SVM.class.getName());
	private MLDataSource dataSource;
	private long dataSourceSize = 0;
	private double slope = 0, yIntercept = 0;
	
	private int numberOfIterations=1000;
	private double learningRate = 0.00005;
	
	private double[] errors = new double[0];
	
	public SVM(String dataFileName) throws MLAException {
		try{
			this.dataSource = new CSVFileBasedMLDataSource(dataFileName, new MLDataSourceConfig());
			this.dataSource.forwardTillEnd();
			this.dataSourceSize = this.dataSource.getReadCount();
			logger.info("Initialized with a datasource of size: "+this.dataSourceSize);
		}catch(Exception er){
			throw new MLAException("Unable to initialize the SVM due to an exception.", er);
		}
	}
	
	public SVM(MLDataSource dataSource) throws MLAException {
		try{
			this.dataSource = dataSource;
			this.dataSource.forwardTillEnd();
			this.dataSourceSize = this.dataSource.getReadCount();
			logger.info("Initialized with a datasource of size: "+this.dataSourceSize);
		}catch(Exception er){
			throw new MLAException("Unable to initialize the SVM due to an exception.", er);
		}
	}
	
	public SVM config(double learningRate, int numberOfIterations){
		this.learningRate = learningRate;
		this.numberOfIterations = numberOfIterations;
		return this;
	}
	
	public SVM build() throws MLDataSourceException{
		double error = totalError();
		List<Double> errorsList = new ArrayList<Double>();
		
		logger.info(
				String.format("Intial Slope=%f  Y-Intercept=%f  Total error computed: %f",
						this.slope,
						this.yIntercept,
						error));
		errorsList.add(error);
		
		for(int i=1; i<this.numberOfIterations; i++){
			stepGradient();
			error = totalError();
			logger.info(
					String.format("After %d iterations with learning rate=%f, Slope=%f  Y-Intercept=%f  Total error computed: %f",
							i,
							this.learningRate,
							this.slope,
							this.yIntercept,
							error)
					);
			errorsList.add(error);
		}
		
		this.errors = new double[errorsList.size()];
		for(int i=0; i<errorsList.size(); i++){
			this.errors[i] = errorsList.get(i);
		}
			
		return this;
	}
	
	public void stepGradient() throws MLDataSourceException{
		double dtdb=0, dtdm=0;
		this.dataSource.reset();
		while(this.dataSource.hasNext()){
			double[] data = this.dataSource.getNextAsNumbers();
			double t = data[1] - (this.slope*data[0]+this.yIntercept);  // t = y - mx+b
			dtdm += -1 * data[0] * t;  // dt/dm = -x*t
			dtdb += -1 * t;  // dt/db = -t
		}
		
		dtdm = (2*dtdm)/this.dataSource.getReadCount();
		dtdb = (2*dtdb)/this.dataSource.getReadCount();
		
		this.slope = this.slope - (learningRate * dtdm);
		this.yIntercept = this.yIntercept - (learningRate * dtdb);
	}
	
	public double totalError() throws MLDataSourceException{
		double totalError=0;
		this.dataSource.reset();
		while (this.dataSource.hasNext()) {
			double[] data = this.dataSource.getNextAsNumbers();
			totalError += Math.pow(data[1] - (this.slope*data[0]+this.yIntercept), 2);
		}
		return totalError/this.dataSource.getReadCount();
	}
	
	public double predict(double x){
		return this.slope*x+this.yIntercept;
	}
	
	public double[] getErrors(){
		return this.errors;
	}
	
	public MLDataSource getDataSource(){
		return this.dataSource;
	}
}
