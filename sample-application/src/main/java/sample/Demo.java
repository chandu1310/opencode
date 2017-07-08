package sample;

import java.util.Arrays;

import opencode.matrix.MatrixConfig;
import opencode.medhas.ActivationFunction;
import opencode.medhas.NeuralLayer;
import opencode.medhas.NeuralLayerType;
import opencode.medhas.NeuralNetwork;
import opencode.medhas.NeuralNetworkConfig;

public class Demo {
	private NeuralNetwork xorNeuralNetwork;
	
	public Demo() {
		MatrixConfig.DISPLAY_LOG = false;
		NeuralNetworkConfig.DISPLAY_LOG = false;
		this.xorNeuralNetwork = new NeuralNetwork();
		
		xorNeuralNetwork
		.connectLayer(new NeuralLayer(2, NeuralLayerType.INPUT))  // Two inputs
		.connectLayer(new NeuralLayer(3, NeuralLayerType.HIDDEN, ActivationFunction.SIGMOID))  // 10 hidden neurons
		.connectLayer(new NeuralLayer(1, NeuralLayerType.OUTPUT));  // 1 output
		
		
		System.out.println(xorNeuralNetwork);
	}
	
	public void trainForXOR(int n){
		// Training for n-times.
		for(int i=0; i<n; i++){
			xorNeuralNetwork.train(new double[]{1,1}, new double[]{0}, 1);  // input is used for training one time.
			xorNeuralNetwork.train(new double[]{0,0}, new double[]{0}, 1);  // input is used for training one time.
			xorNeuralNetwork.train(new double[]{1,0}, new double[]{1}, 1);  // input is used for training one time.
			xorNeuralNetwork.train(new double[]{0,1}, new double[]{1}, 1);  // input is used for training one time.
		}
	}
	
	public double[] predictValue(double input1, double input2){
		double[] output = xorNeuralNetwork.predict(new double[]{input1, input2});
		return output;
	}
	
	
	public static void main(String[] args) {
		Demo d = new Demo();
		d.trainForXOR(1);  // 25 iterations.
		System.out.println("1 XOR 1 =" + Arrays.toString(d.predictValue(1, 1)) );
		System.out.println("0 XOR 0 =" + Arrays.toString(d.predictValue(0, 0)) );
		System.out.println("1 XOR 0 =" + Arrays.toString(d.predictValue(1, 0)) );
		System.out.println("0 XOR 1 =" + Arrays.toString(d.predictValue(0, 1)) );
	}
}
