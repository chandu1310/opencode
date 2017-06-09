package opencode.medhas;

import org.junit.Test;

import opencode.matrix.MatrixConfig;

public class NeuralNetworkTest {
	
	@Test
	public void trainingDemo() {
		MatrixConfig.DISPLAY_LOG = false;
		NeuralNetworkConfig.DISPLAY_LOG = false;
		
		NeuralNetwork nn = new NeuralNetwork();
		
		nn
		.connectLayer(new NeuralLayer(2, NeuralLayerType.INPUT))
		.connectLayer(new NeuralLayer(30, NeuralLayerType.HIDDEN, ActivationFunction.SIGMOID))
		.connectLayer(new NeuralLayer(2, NeuralLayerType.OUTPUT));

		System.out.println(nn);
		
		nn.train(new double[]{1,1}, new double[]{0, 0}, 50);
		
		System.out.println(nn);
	}
	
}
