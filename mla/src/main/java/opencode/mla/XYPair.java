package opencode.mla;

public class XYPair {
	private double x1, y1, x2, y2;
	
	public static XYPair with(double x1, double y1, double x2, double y2){
		return new XYPair(x1, y1, x2, y2);
	}
	
	public XYPair(double x1, double y1, double x2, double y2){
		this.x1 = x1;
		this.y1 = y1;
		
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public XYPair(double[] x1y1, double[] x2y2) throws Error {
		if(x1y1==null || x1y1.length<2 || x2y2==null || x2y2.length<2)
			throw new Error("Insufficient data for creating XYPair");
		
		x1 = x1y1[0]; y1 = x1y1[1];
		x2 = x2y2[0]; y2 = x2y2[1];
	}
	
	public double[] getX1Y1(){
		return new double[]{x1, y1};
	}

	public double[] getX2Y2(){
		return new double[]{x2, y2};
	}

	public double getX1() {
		return x1;
	}

	public double getY1() {
		return y1;
	}

	public double getX2() {
		return x2;
	}

	public double getY2() {
		return y2;
	}
}
