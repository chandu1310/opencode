package opencode.matrix.util;

public class Helper {
	synchronized public static double[][] createCopy(double[][] src){
		int row = 0, col = 0;
		if(src.length>0){
			row = src.length;
			col = src[0].length;
		}else{
			return new double[0][0];
		}
		
		double[][] copy = new double[row][col];
		
		for(int i=0; i<row; i++)
			for(int j=0; j<col; j++)
				copy[i][j] = src[i][j];
		
		return copy;
	}
}
