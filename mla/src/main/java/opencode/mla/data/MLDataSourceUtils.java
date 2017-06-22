package opencode.mla.data;

import java.io.File;
import java.io.InputStream;

public final class MLDataSourceUtils {
	
	/*
	 * Utility method for getting reference to file on class path.
	 */
	final public static File getFile(String fileName) {
		ClassLoader classLoader = MLDataSourceUtils.class.getClassLoader();
		File dataFile = new File(classLoader.getResource(fileName).getFile());
		return dataFile;
	}
	
	/*
	 * Utility method for getting stream to file on class path.
	 */
	final public static InputStream getFileStream(String fileName) {
		ClassLoader classLoader = MLDataSourceUtils.class.getClassLoader();
		return classLoader.getResourceAsStream(fileName);
	}

}
