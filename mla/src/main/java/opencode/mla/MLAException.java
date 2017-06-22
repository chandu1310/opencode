package opencode.mla;

public class MLAException extends Exception {

	private static final long serialVersionUID = -5577823861394348528L;

	public MLAException(String message) {
		super(message);
	}
	
	public MLAException(String message, Exception exception) {
		super(message, exception);
	}
}
