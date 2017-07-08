package opencode.medhas;

import java.text.NumberFormat;

import org.junit.Test;

import opencode.matrix.MatrixConfig;

public class NeuralNetworkTest {

	@Test
	public void trainingDemo() {
		MatrixConfig.DISPLAY_LOG = false;
		NeuralNetworkConfig.DISPLAY_LOG = false;

		NeuralNetwork2 nn = new NeuralNetwork2(0.7, 0.9);

		nn.connectLayer(new NeuralLayer(2, NeuralLayerType.INPUT))
				.connectLayer(new NeuralLayer(3, NeuralLayerType.HIDDEN, ActivationFunction.SIGMOID))
				.connectLayer(new NeuralLayer(1, NeuralLayerType.OUTPUT));

		nn.initNetwork();

		//System.out.println(nn);

		double xorInput[][] = { { 0.0, 0.0 }, { 1.0, 0.0 }, { 0.0, 1.0 }, { 1.0, 1.0 } };
		// double xorInput[][] = { { 0.0, 0.0 }};

		double xorIdeal[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 } };
		// double xorIdeal[][] = { { 0.0 }};

		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMinimumFractionDigits(4);

		for (int i = 0; i < 50; i++) {
			double error = nn.train(xorInput, xorIdeal);
			System.out.println("Trial #" + i + ",Error:" + percentFormat.format(error));
		}

		System.out.println("Recall:");

		for (int i = 0; i < xorInput.length; i++) {
			for (int j = 0; j < xorInput[0].length; j++) {
				System.out.print(xorInput[i][j] + ":");
			}

			double out[] = nn.predict(xorInput[i]);
			System.out.println("=" + out[0]);
		}
	}

}
