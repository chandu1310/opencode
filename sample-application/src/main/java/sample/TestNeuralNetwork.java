package sample;

import java.text.NumberFormat;

public class TestNeuralNetwork {
	public static void main(String args[]) {
		double xorInput[][] = { { 0.0, 0.0 }, { 1.0, 0.0 }, { 0.0, 1.0 }, { 1.0, 1.0 } };
//		double xorInput[][] = { { 0.0, 0.0 }, { 1.0, 0.0 }};

		double xorIdeal[][] = { { 0.0 }, { 1.0 }, { 1.0 }, { 0.0 } };
//		double xorIdeal[][] = { { 0.0 }, { 1.0 }};

		System.out.println("Learn:");

		DeepNetwork network = new DeepNetwork(2, 3, 1, 0.5, 0.5);

		NumberFormat percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMinimumFractionDigits(4);

		double globalError = 1.0;
		int i=1;
		while(globalError > .20) {
			for (int j = 0; j < xorInput.length; j++) {
				network.computeOutputs(xorInput[j]);
				network.calcError(xorIdeal[j]);
				network.learn();
				network.print();
			}
			
			globalError = network.getError(xorInput.length);
			System.out.println("Trial #" + i + ",Error:" + percentFormat.format(globalError));
			i++;
		}

		System.out.println("Recall:");

		for (i = 0; i < xorInput.length; i++) {

			for (int j = 0; j < xorInput[0].length; j++) {
				System.out.print(xorInput[i][j] + ":");
			}

			double out[] = network.computeOutputs(xorInput[i]);
			System.out.println("=" + Math.round(out[0]));
		}
	}
}