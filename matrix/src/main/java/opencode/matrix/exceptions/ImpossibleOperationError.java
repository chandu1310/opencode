package opencode.matrix.exceptions;

/**
 * Core Exception class created for raising runtime exceptions that may occur during operations.
 * @author Chandu
 *
 */
public class ImpossibleOperationError extends Error {
	private static final long serialVersionUID = 1L;

	public ImpossibleOperationError(String message) {
		super(message);
	}
}
