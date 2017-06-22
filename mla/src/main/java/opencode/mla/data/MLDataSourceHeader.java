package opencode.mla.data;

public final class MLDataSourceHeader {
	private int headerCount;
	private String[] headers;
	private long entriesCount;

	public int getHeaderCount() {
		return headerCount;
	}

	public void setHeaderCount(int headerCount) {
		this.headerCount = headerCount;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	public long getEntriesCount() {
		return entriesCount;
	}

	public void setEntriesCount(long entriesCount) {
		this.entriesCount = entriesCount;
	}

}
