package opencode.medhas;

import java.util.Arrays;

import opencode.matrix.Initializer;
import opencode.matrix.Matrix;
import opencode.matrix.impl.Matrix2D;

/**
 * @author Chandra Shekar Chennamsetty
 */
public final class NeuralLayer {
	private NeuralLayerType type;
	
	private NeuralLayer connectingToLayer;
	
	private Matrix weights;
	
	private int layerSize;
	private Matrix summations;
	private Matrix summationsDifferential;
	private Matrix values; 
	
	private Matrix error;  // Set this to zero for each training input
	private Matrix errorDelta;
	private Matrix accumulatedThresholdDelta;
	private Matrix accumulatedWeightDelta;

	private ActivationFunction activationFunction;

	public String layerDetails(){
		return	"\n\n"+
				"Layer Type: "+type+"\n"+
				"Layer Size: "+layerSize+"\n"+
				"Delta Sum/Input: "+summations+"\n"+
				"Neuron Values : "+values+"\n"+
				"Output Weights : "+weights+"\n"+
				"Activation Function: "+activationFunction;
	}
		
	public NeuralLayer(int layerSize, NeuralLayerType type, ActivationFunction activationFunction) {
		this.type = type;
		this.layerSize = layerSize;
		this.summations = Matrix2D.instance("Inputs", 1, layerSize);
		this.summationsDifferential = Matrix2D.instance("InputDifferential", 1, layerSize);
		this.values = Matrix2D.instance("Values", 1, layerSize);		
		this.activationFunction = activationFunction;
	}

	public NeuralLayer(int layerSize, NeuralLayerType type) {
		this(layerSize, type, ActivationFunction.SIGMOID);
	}
	
	public void connectTo(NeuralLayer connectingToLayer){
		connectTo(connectingToLayer, new Initializer() {
			@Override
			public double apply(int row, int col) {
				return 0.5 - (Math.random());
			}
		});
	}
	
	public void connectTo(NeuralLayer connectingToLayer, Initializer initializer){
		if(initializer==null){
			initializer = new Initializer() {
				@Override
				public double apply(int row, int col) {
					return 0.5 - (Math.random());
				}
			};
		} 
		this.connectingToLayer = connectingToLayer;
		this.weights =  Matrix2D.instance("Weights", this.layerSize, connectingToLayer.getLayerSize());
		this.weights.initialize(initializer);
	}

	public void connectTo(NeuralLayer connectingToLayer, double[][] initialWeights){
		if(initialWeights==null){
			connectTo(connectingToLayer);
		}else{
			this.connectingToLayer = connectingToLayer;
			this.weights =  Matrix2D.instance("Weights", this.layerSize, connectingToLayer.getLayerSize());
			this.weights.load(initialWeights);
		}
	}
	
	public NeuralLayer init(){
		if(this.type!=NeuralLayerType.OUTPUT){
			this.accumulatedWeightDelta = Matrix2D.instance("AccWeightDelta", this.layerSize, connectingToLayer.getLayerSize());
			this.weightsDeltaMemory = Matrix2D.instance("WeightsDeltaMemory", this.layerSize, connectingToLayer.getLayerSize());
		}
		
		this.error = Matrix2D.instance("Error", 1, this.layerSize);
		this.errorDelta = Matrix2D.instance("ErrorDelta", 1, this.layerSize);
		this.accumulatedThresholdDelta = Matrix2D.instance("AccThresholdDelta", 1, this.layerSize);
		this.valuesDeltaMemory = Matrix2D.instance("ValuesDeltaMemory", 1, this.layerSize);
		return this;
	}
	
	public void calculateError(){
		
		if(this.type!=NeuralLayerType.OUTPUT){
			Matrix errorDeltaFromConnectingLayer = this.connectingToLayer.getErrorDelta();
			this.error = errorDeltaFromConnectingLayer.copy().multiply(this.weights.copy().transpose(), "error");
			
			double[][] th = new double[errorDeltaFromConnectingLayer.columns()][this.values.columns()];
			for(int i=0; i<errorDeltaFromConnectingLayer.columns(); i++){
				th[i] = Arrays.copyOf(this.values.row(1), this.values.row(1).length); 
			}
			
			
			for(int i=0; i<errorDeltaFromConnectingLayer.columns(); i++){
				double delta = errorDeltaFromConnectingLayer.valueAt(0, i);
				for(int j=0; j<th[0].length; j++){
					th[i][j] = th[i][j] * delta;
				}
			}
			
			Matrix ht = Matrix2D.instance("Temp", th);
			
			this.accumulatedWeightDelta
				.add(ht.transpose());
		}
		

		this.errorDelta
			.load(
				this.error
					.copy()
					.multiplyPositionally(this.summationsDifferential)					
				);
		
		this.accumulatedThresholdDelta.add(this.errorDelta);
	}
	
	private Matrix weightsDeltaMemory;
	private Matrix valuesDeltaMemory;
	public void adjust(double learnRate, double momentum){
		
		if(this.type==NeuralLayerType.OUTPUT)
			return;
		
		weightsDeltaMemory.load(
			accumulatedWeightDelta
			.multiply(learnRate)
			.add(weightsDeltaMemory.copy().multiply(momentum))
		);
		this.weights.add(weightsDeltaMemory);
		accumulatedWeightDelta.multiply(0);
		
		valuesDeltaMemory.load(
			accumulatedThresholdDelta
			.multiply(learnRate)
			.add(valuesDeltaMemory.copy().multiply(momentum))
		);
		this.values.add(valuesDeltaMemory);
		accumulatedThresholdDelta.multiply(0);
	}

	public Matrix fire(Matrix newValues){
		Matrix firedSum = this.summations.copy().multiply(0);
		if(firedSum.isReplaceableWith(newValues)) {
			firedSum.load(newValues);
			
			if(this.type!=NeuralLayerType.INPUT){
				this.activationFunction.apply(firedSum);
			}
			
			return firedSum.add(this.values);
		}else{
			throw new Error("Cannot feed unmatching input to the layer.");
		}
	}
	
	public void feed(Matrix newValues){
		if(this.summations.isReplaceableWith(newValues)) {
			
			this.summations.load(newValues);
			this.summationsDifferential.load(newValues);
			
			if(this.type!=NeuralLayerType.INPUT){
				this.activationFunction.apply(newValues);
				this.activationFunction.differential(summationsDifferential);
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
		return this.summationsDifferential;
	}

	//Applied during backtracking. 
	//Substract the product of transpose of outputvalues and delta,  from weights 
	public void adjustWeights(Matrix deltaWeights){
		this.weights = this.weights.substract(
				this.values.transpose().multiply(deltaWeights)
				);
	}
	
	public Matrix getSummation(){
		return this.values.copy().multiply(weights);
	}
	
	public NeuralLayerType getType() {
		return type;
	}

	public int getLayerSize() {
		return layerSize;
	}

	public Matrix getSummations(){
		return summations;
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

	public Matrix getError() {
		return error;
	}

	public Matrix getErrorDelta() {
		return errorDelta;
	}
}