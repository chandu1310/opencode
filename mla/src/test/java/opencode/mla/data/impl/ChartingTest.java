package opencode.mla.data.impl;

import opencode.mla.Plotter;
import opencode.mla.XYPair;
import opencode.mla.data.MLDataSource;
import opencode.mla.data.MLDataSourceConfig;

public class ChartingTest {
	public static void main(String[] args) {
		try {
			MLDataSourceConfig dataSourceConfig = new MLDataSourceConfig();
			dataSourceConfig.setReadingHeadersEnabled(true);
			MLDataSource dataSource = new CSVFileBasedMLDataSource("test_data.csv", dataSourceConfig);
			
			Plotter.construct(
					"Demo Plotter", 
					"Weight-Calorie Distribution", 
					dataSource, 
					"Prediction Line",
					XYPair.with(9, 12, 80, 110),
					"Weight", 
					"Calories"
				).show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
