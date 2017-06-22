package opencode.mla.data.impl;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.Test;

import opencode.mla.data.MLDataSourceConfig;
import opencode.mla.data.MLDataSourceException;
import opencode.mla.data.MLDataSourceUtils;

public class CSVFileBasedMLDataSourceTest {
	private static final LogManager logManager = LogManager.getLogManager();
	private static final Logger logger = Logger.getLogger(CSVFileBasedMLDataSourceTest.class.getName());
	static {
		try {
			logManager.readConfiguration(MLDataSourceUtils.getFileStream("mla-logging.properties"));
		} catch (IOException exception) {
			logger.log(Level.SEVERE, "Error in loading configuration", exception);
		}
	}

	private String dataFileName = "data.csv";

	@Test
	public void testDataSource() throws MLDataSourceException {
		MLDataSourceConfig dataSourceConfig = new MLDataSourceConfig();
		CSVFileBasedMLDataSource dataSource = new CSVFileBasedMLDataSource(dataFileName, dataSourceConfig);

		logger.info(Arrays.toString(dataSource.getHeaders()));

		while (dataSource.hasNext()) {
			logger.info(Arrays.toString(dataSource.getNextAsNumbers()));
		}

		logger.info("Total rows: " + dataSource.getReadCount());

		Assert.assertEquals(100, dataSource.getReadCount());
		
		dataSource.reset();
		
		Assert.assertEquals(0, dataSource.getReadCount());
		while (dataSource.hasNext()) {
			dataSource.getNextAsNumbers();
		}
		Assert.assertEquals(100, dataSource.getReadCount());

	}

}
