import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import opencode.mla.Plotter;
import opencode.mla.SVM;
import opencode.mla.XYPair;
import opencode.mla.data.MLDataSource;
import opencode.mla.data.MLDataSourceConfig;
import opencode.mla.data.MLDataSourceEntryProcessor;
import opencode.mla.data.MLDataSourceUtils;
import opencode.mla.data.impl.CSVFileBasedMLDataSource;

public class EmergencyReasonPredictorSVMDemo {
	private static final LogManager logManager = LogManager.getLogManager();
	private static final Logger logger = Logger.getLogger(EmergencyReasonPredictorSVMDemo.class.getName());
	static {
		try {
			logManager.readConfiguration(MLDataSourceUtils.getFileStream("logging.properties"));
		} catch (IOException exception) {
			logger.log(Level.SEVERE, "Error in loading configuration", exception);
		}
	}

	private SVM emergencyPredictor;

	public EmergencyReasonPredictorSVMDemo() {
		try {

			final Map<String, Integer> reasons = new HashMap<String, Integer>();

			MLDataSourceConfig dataSourceConfig = new MLDataSourceConfig();
			dataSourceConfig.setEntryProcessor(new MLDataSourceEntryProcessor() {
				public String[] processRow(long rowNumber, String rowData, MLDataSourceConfig dataSourceConfig) {
					String[] values = rowData.split(",");
					double lat = Double.parseDouble(values[0]), lon = Double.parseDouble(values[1]);
					

					String reason = values[4];
					if (!reasons.containsKey(reason)) {
						reasons.put(reason, reasons.size());
					}
					int reasonCode = reasons.get(reason);

					String[] returnValues = new String[] { String.valueOf(formSingleValue(lat, lon)), String.valueOf(reasonCode) };

					return returnValues;
				}
			});

			CSVFileBasedMLDataSource dataSource = new CSVFileBasedMLDataSource("911-data-montogomery-pa.csv",
					dataSourceConfig);
			
			dataSource.reset();
			while(dataSource.hasNext())
				System.out.println(Arrays.toString(dataSource.getNextAsNumbers()));
			
			
			dataSource.reset();
			
			Plotter.construct(
					"Demo Plotter", 
					"Something Distribution", 
					dataSource, 
					"Weight", 
					"Calories"
				).show();

			
//			emergencyPredictor = new SupportVectorMachine(dataSource);
//			emergencyPredictor.optimize(8);
//			
//			//40.2978759,-75.5812935 - reason code 0 -> EMS: BACK PAINS/INJURY
//			double reasonCode = emergencyPredictor.predict(formSingleValue(40.2978759, -75.5812935));
//			logger.info(""+reasonCode);
//			
//			MLDataSourceConfig dataSourceConfig = new MLDataSourceConfig();
//			dataSourceConfig.setReadingHeadersEnabled(true);
//			MLDataSource dataSource = new CSVFileBasedMLDataSource("cal_data.csv", dataSourceConfig);

//			SVM svm = new SVM(dataSource);
//			svm.config(0.000004, 10).build();
//			//32.502345269453031,31.70700584656992
//			logger.info("32.502345269453031 = "+svm.predict(32.502345269453031));

//			Plotter.construct(
//					"Demo Plotter", 
//					"Something Distribution", 
//					svm.getDataSource(), 
//					"Prediction Line",
//					XYPair.with(10, svm.predict(10), 100, svm.predict(100)),
//					"Weight", 
//					"Calories"
//				).show();
			
		} catch (Exception e) {
			System.out.println("Seems like the demo failed.");
			e.printStackTrace();
		}
	}
	
	private double formSingleValue(double lat, double lon){
		// Refer: https://stackoverflow.com/questions/4637031/geospatial-indexing-with-redis-sinatra-for-a-facebook-app
		double locationValue = (lat + 90) * 180 + lon; 
		return locationValue/10000;
	}

	public static void main(String[] args) {
		EmergencyReasonPredictorSVMDemo app = new EmergencyReasonPredictorSVMDemo();
	}
}
