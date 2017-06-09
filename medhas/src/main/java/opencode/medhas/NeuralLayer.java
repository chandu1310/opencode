package opencode.medhas;

import java.util.Random;

import opencode.matrix.Initializer;
import opencode.matrix.Matrix;
import opencode.matrix.impl.Matrix2D;

/**
 * @author Chandra Shekar Chennamsetty
 */
public final class NeuralLayer {
	private NeuralLayerType type;
	private int layerSize;
	private Matrix inputs;
	private Matrix inputDifferential;
	private Matrix values; 
	private Matrix weights;
	private ActivationFunction activationFunction;

	public String layerDetails(){
		return	"\n\n"+
				"Layer Type: "+type+"\n"+
				"Layer Size: "+layerSize+"\n"+
				"Delta Sum/Input: "+inputs+"\n"+
				"Neuron Values : "+values+"\n"+
				"Output Weights : "+weights+"\n"+
				"Activation Function: "+activationFunction;
	}
		
	public NeuralLayer(int layerSize, NeuralLayerType type, ActivationFunction activationFunction) {
		this.type = type;
		this.layerSize = layerSize;
		this.inputs = Matrix2D.instance("Inputs", 1, layerSize);
		this.inputDifferential = Matrix2D.instance("InputDifferential", 1, layerSize);
		this.values = Matrix2D.instance("Values", 1, layerSize);
		this.activationFunction = activationFunction;
	}

	public NeuralLayer(int layerSize, NeuralLayerType type) {
		this(layerSize, type, ActivationFunction.SIGMOID);
	}
	
	public void connectTo(NeuralLayer connectingToLayer){
		connectTo(connectingToLayer, new Initializer() {
			final Random r = new Random();
			@Override
			public double apply(int row, int col) {
				return r.nextDouble();
			}
		});
	}
	
	public void connectTo(NeuralLayer connectingToLayer, Initializer initializer){
		if(initializer==null){
			initializer = new Initializer() {
				final Random r = new Random();
				@Override
				public double apply(int row, int col) {
					return r.nextDouble();
				}
			};
		} 
		this.weights =  Matrix2D.instance("Weights", this.layerSize, connectingToLayer.getLayerSize());
		this.weights.initialize(initializer);
	}

	public void connectTo(NeuralLayer connectingToLayer, double[][] initialWeights){
		this.weights =  Matrix2D.instance("Weights", this.layerSize, connectingToLayer.getLayerSize());
		this.weights.load(initialWeights);
	}
		
	public void feed(Matrix newValues){
		if(this.inputs.isReplaceableWith(newValues)) {
			
			this.inputs.load(newValues);
			this.inputDifferential.load(newValues);
			
			if(this.type!=NeuralLayerType.INPUT){
				this.activationFunction.apply(newValues);
				this.activationFunction.differential(inputDifferential);
			}
			
			this.values.load(newValues);
		}else{
			throw new Error("Cannot feed unmatching input to the layer.");
		}
	}

	public Matrix getBacktrackingWeightsFactor(){
		return this.weights.transpose();
	}
	
	public Matrix getBacktrackingDifferentialFactor(){
		return this.inputDifferential;
	}

	//Applied during backtracking. 
	//Substract the product of transpose of outputvalues and delta,  from weights 
	public void adjustWeights(Matrix deltaWeights){
		this.weights = this.weights.substract(
				this.values.transpose().multiply(deltaWeights)
				);
	}
	
	public Matrix getSummation(){
		return this.values.multiply(weights);
	}
	
	public NeuralLayerType getType() {
		return type;
	}

	public int getLayerSize() {
		return layerSize;
	}

	public Matrix getInputs(){
		return inputs;
	}
	
	public Matrix getValues() {
		return values;
	}

	public Matrix getWeights() {
		return weights;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}
}