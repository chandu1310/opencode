package opencode.medhas;

import java.util.ArrayList;
import java.util.List;

import opencode.matrix.Matrix;
import opencode.matrix.Transformer;
import opencode.matrix.impl.Matrix2D;

/**
 * @author Chandra Shekar Chennamsetty
 */
public class NeuralNetwork2 {
	private List<NeuralLayer> layers = new ArrayList<NeuralLayer>();
	private double leariningRate = 0.5;
	private double momentum = 0.5; 
	private Matrix globalError;

	public NeuralNetwork2() {
	}

	public NeuralNetwork2(double learningRate, double momentum) {
		this.leariningRate = learningRate;
		this.momentum = momentum;
	}

	public NeuralNetwork2 connectLayer(NeuralLayer layer){
		return connectLayer(layer, null);
	}
	
	public NeuralNetwork2 connectLayer(NeuralLayer layer, double[][] initialWeights){
		this.layers.add(layer);
		if(this.layers.size()>1){
			this.layers.get(this.layers.size()-2).connectTo(layer, initialWeights);
		}
		return this;
	}
	
	public void initNetwork(){
		for(NeuralLayer nl: layers)
			nl.init();
		
		globalError = Matrix2D.instance("GlobalError", 1, this.layers.get(this.layers.size()-1).getLayerSize());
	}
	
	public double[] predict(double[] input){
		Matrix newValues = Matrix2D.instance("NewValues", new double[][]{input});
		
		double[] values = null;
		
		for(NeuralLayer layer: layers){
			Matrix firedValues = layer.fire(newValues);
			
			if(layer.getType()!=NeuralLayerType.OUTPUT){
				newValues.replace(firedValues.multiply(layer.getWeights()));
			}else{
				values = layer.getValues().valuesAsArray()[0];
			}
		}
		return values;
	}	

	private NeuralLayer getOutputLayer(){
		return this.layers.get(this.layers.size()-1);
	}
	
	public double train(double[][] inputSet, double[][] outputSet){
		globalError.multiply(0);
		for(int i=0; i<inputSet.length; i++){
			train(inputSet[i], outputSet[i]);
		}
		
		double sum = 0;
		for(int i=0; i<globalError.columns(); i++)
			sum += globalError.valueAt(0, i);
		
		double err = Math.sqrt(sum / (inputSet.length * getOutputLayer().getLayerSize()));
		return err;
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

		NeuralLayer outputLayer = layers.get(layers.size()-1);

		Matrix requiredOutput = Matrix2D.instance("RequiredOutput", new double[][]{output});
		Matrix calculatedValue = outputLayer.getValues();
		Matrix error = requiredOutput.substract(calculatedValue, "Error");
		outputLayer.getError().replace(error);
		globalError.add(error.copy().multiplyPositionally(error)); // Global Error += Error*Error
		
		requiredOutput.print();
		calculatedValue.print();
		error.print();
		
		print("------------------------\n>>> BackTracking\n------------------------");
		for(int i=layers.size()-1; i>=0; i--){  // Start with hidden ones.
			NeuralLayer layer = layers.get(i);
			layer.calculateError();
			layer.adjust(leariningRate, momentum);
		}
	}

	public void print(Object s){
		if(NeuralNetworkConfig.DISPLAY_LOG){
			System.out.println(s);
		}
	}
	
	public NeuralNetwork2 print(){
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
