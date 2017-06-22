package opencode.mla.data;

public final class MLDataSourceEntry {
	private long entryNumber;
	private String[] data;

	public MLDataSourceEntry(long entryNumber, String[] data) {
		this.entryNumber = entryNumber;
		this.data = data;
	}
	
	public long getEntryNumber() {
		return entryNumber;
	}

	public String[] getData() {
		return data;
	}

}
