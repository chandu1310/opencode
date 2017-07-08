package opencode.mla.data;

public abstract class MLDataSourceEntryProcessor {
	final public Object _processRow(long rowNumber, String rowData, MLDataSourceConfig dataSourceConfig, boolean asHeader){
		
		String[] dataRow = processRow(rowNumber, rowData, dataSourceConfig);
		
		if(asHeader){
			MLDataSourceHeader header = new MLDataSourceHeader();
			header.setHeaders(dataRow);
			header.setHeaderCount(dataRow.length);
			return header;
		}else
			return new MLDataSourceEntry(rowNumber, dataRow);
	}
	
	abstract public String[] processRow(long rowNumber, String rowData, MLDataSourceConfig dataSourceConfig); 
}
