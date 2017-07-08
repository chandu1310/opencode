package opencode.matrix;

@FunctionalInterface
public interface Combiner {
	double apply(double[] value);
	
	public static final Combiner AGGREGATOR = new Combiner(){
		@Override
		public double apply(double[] value) {
			double sum = 0;
			if(value!=null && value.length>0){
				for(double d: value)
					sum += d;
			}
			return sum;
		}
	};
}
