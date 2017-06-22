package opencode.mla.data;

public interface MLDataSource {

	/*
	 * Initialize the data source from actual data supplier.
	 */
	public MLDataSource init() throws MLDataSourceException;

	/*
	 * Returns the number of headers.
	 */
	public int getHeaderCount();

	/*
	 * Return the header labels, if available, for this data source.
	 */
	public String[] getHeaders();

	/*
	 * Return the total entries count.
	 */
	public long getEntriesCount();

	/*
	 * Return the total count of entries that were read.
	 */
	public long getReadCount();

	/*
	 * Indicates if there is one more entry to fetch.
	 */
	public boolean hasNext() throws MLDataSourceException;

	/*
	 * Returns the next row from the underlying data file. Return null if there
	 * is no more data to read else array of Strings corresponding to field
	 * classes.
	 */
	public String[] getNext() throws MLDataSourceException;

	/*
	 * Returns the next row from the underlying data file. Return null if there
	 * is no more data to read else array of numbers corresponding to field
	 * classes.
	 */
	public double[] getNextAsNumbers() throws MLDataSourceException;
	
	/*
	 * Reset the datasource to be read from first.
	 */
	public MLDataSource reset()  throws MLDataSourceException ;

	/*
	 * Forward the current position of datasource reader by one record.
	 */
	public MLDataSource forward()  throws MLDataSourceException ;
	
	/*
	 * Forward the datasource reader to end of the data.
	 */
	public MLDataSource forwardTillEnd()  throws MLDataSourceException ;
}
