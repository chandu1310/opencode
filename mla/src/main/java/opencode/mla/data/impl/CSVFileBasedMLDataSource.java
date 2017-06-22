package opencode.mla.data.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringUtils;

import opencode.mla.data.MLDataSource;
import opencode.mla.data.MLDataSourceConfig;
import opencode.mla.data.MLDataSourceEntry;
import opencode.mla.data.MLDataSourceException;
import opencode.mla.data.MLDataSourceHeader;
import opencode.mla.data.MLDataSourceUtils;

public final class CSVFileBasedMLDataSource implements MLDataSource {
	private static final Logger logger = Logger.getLogger(CSVFileBasedMLDataSource.class.getName());
	private MLDataSourceConfig dataSourceConfig;
	private String fileName;
	private BufferedReader fileReader;
	private MLDataSourceHeader header;
	
	private long readCount = 0;

	public CSVFileBasedMLDataSource(String fileName, MLDataSourceConfig dataSourceConfig) throws MLDataSourceException {
		this.fileName = fileName;
		this.dataSourceConfig = dataSourceConfig;
		init();
	}

	@Override
	public MLDataSource init() throws MLDataSourceException {
		try {
			logger.log(Level.FINE, "Initializing CSV Datasource with file: " + fileName);
			File dataFile = MLDataSourceUtils.getFile(this.fileName);
			this.fileReader = new BufferedReader(new FileReader(dataFile));
			logger.log(Level.FINE, "Done.");

			if(this.fileReader!=null && this.dataSourceConfig.isReadingHeadersEnabled() && this.dataSourceConfig.getHeaderProcessor()!=null){
				logger.log(Level.FINE, "Reading header information.");
				String headerRowData = this.fileReader.readLine();
				Object headerInfoObj = this.dataSourceConfig.getHeaderProcessor().processRow(0, headerRowData, this.dataSourceConfig);
				if(headerInfoObj != null && headerInfoObj instanceof MLDataSourceHeader){
					this.header = (MLDataSourceHeader)headerInfoObj;
				}else{
					throw new MLDataSourceException("Failed to get MLDataSourceHeader object from rowdata: "+headerRowData);
				}
				logger.log(Level.FINE, "Done.");
			}
		} catch (FileNotFoundException e) {
			throw new MLDataSourceException("Data file :"+this.fileName+" is not found for initiating the data source.", e);
		} catch (IOException e) {
			throw new MLDataSourceException("Reading of data file :"+this.fileName+" failed.", e);		
		}
		this.readCount = 0;
		return this;
	}

	@Override
	public int getHeaderCount() {
		if(this.header != null)
			return this.header.getHeaderCount();
		else
			return 0;
	}

	@Override
	public String[] getHeaders() {
		if(this.header != null && this.header.getHeaders() != null)
			return this.header.getHeaders();
		else
			return null;
	}

	@Override
	public long getEntriesCount() {
		if(this.header != null)
			return this.header.getEntriesCount();
		else
			return -1;
	}

	@Override
	public long getReadCount() {
		return this.readCount;
	}

	private String[] currentRow;
	
	@Override
	public boolean hasNext() throws MLDataSourceException {
		this.currentRow = getNext();
		if(this.currentRow == null)
			return false;
		
		return true;
	}

	@Override
	public String[] getNext() throws MLDataSourceException {
		if(this.currentRow != null){
			String[] currentRow = this.currentRow;
			this.currentRow = null;
			return currentRow;
		}
		
		String[] row = new String[0];
		if(this.fileReader != null) {
			String rowData;
			try {
				rowData = this.fileReader.readLine();
				if(rowData == null){
					row = null;  // Reached end of the file.
				}else if(StringUtils.isNotBlank(rowData)) {
					Object rowObj = this.dataSourceConfig.getEntryProcessor()._processRow(this.readCount, rowData, dataSourceConfig);
					if(rowObj != null && rowObj instanceof MLDataSourceEntry){
						row = ((MLDataSourceEntry)rowObj).getData();
						this.readCount++;
					}
				}
			} catch (IOException e) {
				throw new MLDataSourceException("Failed to perform a read operation from underlying file of datasource.", e);
			}
		}else{
			throw new MLDataSourceException("Data source is not initialized. Invoke init() method before using it.");
		}
		return row;
	}
	
	@Override
	public double[] getNextAsNumbers() throws MLDataSourceException {
		double[] dataAsNum = null;
		String[] dataAsStr = getNext();
		if(dataAsStr!=null){
			dataAsNum = new double[dataAsStr.length];
			for(int i=0; i<dataAsStr.length; i++){
				dataAsNum[i] = Double.parseDouble(dataAsStr[i]);
			}
		}
		return dataAsNum;
	}
	
	@Override
	public MLDataSource reset() throws MLDataSourceException {
		try{
			this.fileReader.close();
			init();
			return this;
		}catch(Exception er){
			throw new MLDataSourceException("Failed to reset the datasource due to an exception.", er);
		}
	}
	
	@Override
	public MLDataSource forward() throws MLDataSourceException {
		if(hasNext())
			getNext();
		return this;
	}
	
	@Override
	public MLDataSource forwardTillEnd() throws MLDataSourceException {
		while(hasNext()){
			getNext();
		}
		return this;
	}
}
