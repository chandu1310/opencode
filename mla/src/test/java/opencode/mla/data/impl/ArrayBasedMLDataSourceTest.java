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

public class ArrayBasedMLDataSourceTest {
	private static final LogManager logManager = LogManager.getLogManager();
	private static final Logger logger = Logger.getLogger(ArrayBasedMLDataSourceTest.class.getName());
	static {
		try {
			logManager.readConfiguration(MLDataSourceUtils.getFileStream("mla-logging.properties"));
		} catch (IOException exception) {
			logger.log(Level.SEVERE, "Error in loading configuration", exception);
		}
	}

	@Test
	public void testDataSource1() throws MLDataSourceException {
		ArrayBasedMLDataSource dataSource = new ArrayBasedMLDataSource(
				new double[][]{
					{1, 2},
					{2, 3},
					{4.5, 5.6},
					{6.7, 7.8},
					{0.0, 2.3}
				});

		logger.info(Arrays.toString(dataSource.getHeaders()));

		while (dataSource.hasNext()) {
			logger.info(Arrays.toString(dataSource.getNextAsNumbers()));
		}

		logger.info("Total rows: " + dataSource.getReadCount());

		Assert.assertEquals(5, dataSource.getReadCount());
		
		dataSource.reset();
		
		Assert.assertEquals(0, dataSource.getReadCount());
		while (dataSource.hasNext()) {
			dataSource.getNextAsNumbers();
		}
		Assert.assertEquals(5, dataSource.getReadCount());
	}

	@Test
	public void testDataSource2() throws MLDataSourceException {
		ArrayBasedMLDataSource dataSource = new ArrayBasedMLDataSource(
				new double[]{1,2,3,4,5}, 
				1
				);

		logger.info(Arrays.toString(dataSource.getHeaders()));

		while (dataSource.hasNext()) {
			logger.info(Arrays.toString(dataSource.getNextAsNumbers()));
		}

		logger.info("Total rows: " + dataSource.getReadCount());

		Assert.assertEquals(5, dataSource.getReadCount());
		
		dataSource.reset();
		
		Assert.assertEquals(0, dataSource.getReadCount());
		while (dataSource.hasNext()) {
			dataSource.getNextAsNumbers();
		}
		Assert.assertEquals(5, dataSource.getReadCount());
	}

	@Test
	public void testDataSource3() throws MLDataSourceException {
		ArrayBasedMLDataSource dataSource = new ArrayBasedMLDataSource(
				1,
				new double[]{1,2,3,4,5}
				);

		logger.info(Arrays.toString(dataSource.getHeaders()));

		while (dataSource.hasNext()) {
			logger.info(Arrays.toString(dataSource.getNextAsNumbers()));
		}

		logger.info("Total rows: " + dataSource.getReadCount());

		Assert.assertEquals(5, dataSource.getReadCount());
		
		dataSource.reset();
		
		Assert.assertEquals(0, dataSource.getReadCount());
		while (dataSource.hasNext()) {
			dataSource.getNextAsNumbers();
		}
		Assert.assertEquals(5, dataSource.getReadCount());
	}
	
	@Test
	public void testDataSource4() throws MLDataSourceException {
		ArrayBasedMLDataSource dataSource = new ArrayBasedMLDataSource(
				new double[]{1,2,3,4,5},
				new double[]{1,2,3,4,5}
				);

		logger.info(Arrays.toString(dataSource.getHeaders()));

		while (dataSource.hasNext()) {
			logger.info(Arrays.toString(dataSource.getNextAsNumbers()));
		}

		logger.info("Total rows: " + dataSource.getReadCount());

		Assert.assertEquals(5, dataSource.getReadCount());
		
		dataSource.reset();
		
		Assert.assertEquals(0, dataSource.getReadCount());
		while (dataSource.hasNext()) {
			dataSource.getNextAsNumbers();
		}
		Assert.assertEquals(5, dataSource.getReadCount());
	}
}
