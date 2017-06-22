package opencode.mla.data;

public class MLDataSourceException extends Exception {
	private static final long serialVersionUID = -9119089336013326300L;

	public MLDataSourceException(String message) {
		super(message);
	}
	
	public MLDataSourceException(String message, Exception exception) {
		super(message, exception);
	}
}
