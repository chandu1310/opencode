package opencode.mla.data;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public final class MLDataSourceConfig {

	// By default no headers expected. If true, expects a headerProcessor.
	private boolean readingHeadersEnabled = false;

	private boolean skippingCorruptDataEnabled = true;

	// Used only when skipCorruptData = false
	private String substitutionForMissingDataEnabled = "";

	// Header Row Processor
	private MLDataSourceEntryProcessor headerProcessor = new MLDataSourceEntryProcessor() {
		private Splitter rowDataSplitter = Splitter.on(',').trimResults();

		@Override
		public String[] processRow(long rowNumber, String rowData, MLDataSourceConfig dataSourceConfig) {	
			return Iterables.toArray(rowDataSplitter.split(rowData), String.class);
		}
	};

	// Data Row Processor
	private MLDataSourceEntryProcessor entryProcessor = new MLDataSourceEntryProcessor() {
		private Splitter rowDataSplitter = Splitter.on(',').trimResults();

		@Override
		public String[] processRow(long rowNumber, String rowData, MLDataSourceConfig dataSourceConfig) {	
			return Iterables.toArray(rowDataSplitter.split(rowData), String.class);
		}
	};

	public boolean isReadingHeadersEnabled() {
		return readingHeadersEnabled;
	}

	public void setReadingHeadersEnabled(boolean readingHeadersEnabled) {
		this.readingHeadersEnabled = readingHeadersEnabled;
	}

	public boolean isSkippingCorruptDataEnabled() {
		return skippingCorruptDataEnabled;
	}

	public void setSkippingCorruptDataEnabled(boolean skippingCorruptDataEnabled) {
		this.skippingCorruptDataEnabled = skippingCorruptDataEnabled;
	}

	public String getSubstitutionForMissingDataEnabled() {
		return substitutionForMissingDataEnabled;
	}

	public void setSubstitutionForMissingDataEnabled(String substitutionForMissingDataEnabled) {
		this.substitutionForMissingDataEnabled = substitutionForMissingDataEnabled;
	}

	public MLDataSourceEntryProcessor getHeaderProcessor() {
		return headerProcessor;
	}

	public void setHeaderProcessor(MLDataSourceEntryProcessor headerProcessor) {
		this.headerProcessor = headerProcessor;
	}

	public MLDataSourceEntryProcessor getEntryProcessor() {
		return entryProcessor;
	}

	public void setEntryProcessor(MLDataSourceEntryProcessor entryProcessor) {
		this.entryProcessor = entryProcessor;
	}
}
