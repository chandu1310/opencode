package opencode.mla;

import opencode.mla.data.MLDataSource;
import opencode.mla.data.MLDataSourceConfig;
import opencode.mla.data.impl.ArrayBasedMLDataSource;
import opencode.mla.data.impl.CSVFileBasedMLDataSource;

public class SVMTest {
	public static void main(String[] args) {
		try {
			MLDataSourceConfig dataSourceConfig = new MLDataSourceConfig();
			dataSourceConfig.setReadingHeadersEnabled(true);
			MLDataSource dataSource = new CSVFileBasedMLDataSource("cal_data.csv", dataSourceConfig);
			
			SVM svm = new SVM(dataSource);
			svm.config(0.00005, 10000).build();
			
			Plotter.construct(
					"Demo Plotter", 
					"Weight-Calorie Distribution", 
					dataSource, 
					"Prediction Line",
					XYPair.with(0, svm.predict(0), 100, svm.predict(100)),
					"Weight", 
					"Calories"
				).show();
			
//			ArrayBasedMLDataSource errorsDataSource = new ArrayBasedMLDataSource(svm.getErrors(), 10);
//			Plotter.construct(
//					"SVM Error Plotting", 
//					"Error variation.", 
//					errorsDataSource,
//					"Total Error", 
//					"Y"
//				).show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
