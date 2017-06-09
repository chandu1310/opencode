package opencode.matrix;

public class MessageTemplates {
	public static final String INVALID_ROW = "Invalid row(%d) requested.";
	public static final String INVALID_COLUMN = "Invalid row(%d) requested.";
	public static final String INVALID_ROW_COLUMN = "Invalid row(%d) and column(%d) provided for operation.";
	public static final String UNACCEPTABLE_MATRIX_SIZE = "Invalid size provided as row(%d) and col(%d).";
	public static final String UNMATCHING_MATRIX_SIZE = "Unmatching size of data being used for operation.";
	public static final String INVALID_DIVISOR_FOROPERATION = "Invalid divisor value provided for divide operation.";
	public static final String INVALID_SIZE_FOR_SLICING = "Slicing cannot be done based on values provided.";
	public static final String INVALID_SIZE_FOR_MERGING = "Merging cannot be done based on values provided.";
	
	public static final String SCALAR_ADDED_MATRIX_NAME = "(%s+%f)";
	public static final String SCALAR_SUBSTRACTED_MATRIX_NAME = "(%s-%f)";
	public static final String SCALAR_PRODUCT_MATRIX_NAME = "(%s*%f)";
	public static final String SCALAR_DIVISION_MATRIX_NAME = "(%s/%f)";

	public static final String ADDED_MATRIX_NAME = "(%s+%s)";
	public static final String SUBSTRACTED_MATRIX_NAME = "(%s-%s)";
	public static final String PRODUCT_MATRIX_NAME = "(%s*%s)";
	public static final String POSITIONAL_PRODUCT_MATRIX_NAME = "(%s-*-%s)";
}
