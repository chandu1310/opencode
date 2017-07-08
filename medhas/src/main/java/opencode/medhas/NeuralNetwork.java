package opencode.medhas;

import java.util.ArrayList;
import java.util.List;

import opencode.matrix.Matrix;
import opencode.matrix.impl.Matrix2D;

/**
 * @author Chandra Shekar Chennamsetty
 */
public class NeuralNetwork {
	private List<NeuralLayer> layers = new ArrayList<NeuralLayer>();
	private double leariningRate = 1;
	
	public NeuralNetwork() {
	}

	public NeuralNetwork(double learningRate) {
		this.leariningRate = learningRate;
	}

	public NeuralNetwork connectLayer(NeuralLayer layer){
		return connectLayer(layer, null);
	}
	
	public NeuralNetwork connectLayer(NeuralLayer layer, double[][] initialWeights){
		this.layers.add(layer);
		if(this.layers.size()>1){
			this.layers.get(this.layers.size()-2).connectTo(layer, initialWeights);
		}
		return this;
	}
	
	public double[] predict(double[] input){
		Matrix newValues = Matrix2D.instance("NewValues", new double[][]{input});
		
		double[] values = null;
		
		for(NeuralLayer layer: layers){
			layer.feed(newValues);
			
			if(layer.getType() != NeuralLayerType.OUTPUT)
				newValues = layer.getSummation();
			else{
				values = layer.getValues().valuesAsArray()[0];
			}
		}
		
		return values;
	}	

	public void train(double[] input, double[] output, int numberOfIterations){
		for(int i=0; i<numberOfIterations; i++){
			train(input, output);
		}
	}

	private void train(double[] input, double[] output){
		Matrix newValues = Matrix2D.instance("NewValues", new double[][]{input});
		
		print("------------------------\n>>> Feeding\n------------------------");
				
		for(NeuralLayer layer: layers){
			layer.feed(newValues);
			
			if(layer.getType() != NeuralLayerType.OUTPUT){
				newValues = layer.getSummation();
			}
		}		
		
		print();
		
		Matrix requiredOutput = Matrix2D.instance("RequiredOutput", new double[][]{output});
		Matrix calculatedValue = layers.get(layers.size()-1).getValues();
		Matrix error = requiredOutput.substract(calculatedValue, "Error");
		Matrix delta = error.multiply(leariningRate).multiplyPositionally(layers.get(layers.size()-1).getBacktrackingDifferentialFactor(), "Delta");
		
		requiredOutput.print();
		calculatedValue.print();
		error.print();
		delta.print();
		
		print("------------------------\n>>> BackTracking\n------------------------");
		for(int n=layers.size()-1; n>=0; n--){
			NeuralLayer layer = layers.get(n);

			print(layer.getType()+
					" "+layer.getValues()+
					layer.getWeights()
					);

			
			delta.print();
			
			if(layer.getType()!=NeuralLayerType.OUTPUT){
				layer.getWeights().print();
				layer.adjustWeights(delta);				
				layer.getWeights().print();
				
				if(layer.getType()!=NeuralLayerType.INPUT){
					delta = delta.multiply(layer.getBacktrackingWeightsFactor()).multiplyPositionally(layer.getBacktrackingDifferentialFactor());
				}
			}
		}
		
		print(
				layers.get(0).getValues()
				+" "+
				layers.get(0).getWeights()
				+" "+
				delta
				);
		layers.get(0).adjustWeights(delta);
		delta.print();
	}	
	
	public void print(Object s){
		if(NeuralNetworkConfig.DISPLAY_LOG){
			System.out.println(s);
		}
	}
	
	public NeuralNetwork print(){
		if(NeuralNetworkConfig.DISPLAY_LOG){
			System.out.println(this);
		}
		return this;
	}
	
	@Override
	public String toString() {
		StringBuffer details = new StringBuffer();
		details.append(
					"NEURAL NETWORK DETAILS:\n"+
					"Total number of layers: "+layers.size()	
		);
		int i=1;
		for(NeuralLayer l: layers){
			details.append("\n\n----------- Layer "+i+"-----------");
			details.append(l.layerDetails());
			i++;
		}
		return details.toString();
	}
}
