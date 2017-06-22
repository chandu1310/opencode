package opencode.mla.data;

public abstract class MLDataSourceEntryProcessor {
	final public Object _processRow(long rowNumber, String rowData, MLDataSourceConfig dataSourceConfig){
		MLDataSourceEntry entry = null;
		String[] dataRow = processRow(rowNumber, rowData, dataSourceConfig);
		entry = new MLDataSourceEntry(rowNumber, dataRow);
		return entry;
	}
	
	abstract public String[] processRow(long rowNumber, String rowData, MLDataSourceConfig dataSourceConfig); 
}
