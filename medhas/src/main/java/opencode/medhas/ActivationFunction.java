package opencode.medhas;

import java.io.Serializable;

import opencode.matrix.Matrix;
import opencode.matrix.Transformer;

/**
 * @author Chandra Shekar Chennamsetty
 */
public final class ActivationFunction implements Serializable{
	
	private static final long serialVersionUID = -4912964096118119569L;
	private String name;
	private Transformer _function;
	private Transformer _differential;
	
	private ActivationFunction(String name, Transformer _function, Transformer _differential){
		this.name = name;
		this._function = _function;
		this._differential = _differential;
	}
	
	public Transformer get(){
		return _function;
	}
	
	public Transformer getDifferential(){
		return _differential;
	}
	
	public void apply(Matrix a){
		a.transform(_function);
	}
	
	public double apply(double d){
		return _function.apply(d);
	}	
	
	public void differential(Matrix a){
		a.transform(_differential);
	}
	
	public double differential(double d){
		return _differential.apply(d);
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	final public static ActivationFunction LINEAR = new ActivationFunction(
			"Linear Function",
			(x) -> {
				return x;
			},
			(x) -> {
				return x;
			});
	
	final public static ActivationFunction SIGMOID = new ActivationFunction(
			"Sigmoid Function",
			(x) -> {
				  return 1.0 / (1 + Math.exp(-1.0 * x));
				// 1 / (1+(e^(-1*x)))
//				return Math.pow(1+Math.exp(-1*x), -1);
			},
			(x) -> {
				// f(x)(1-f(x)) = e^x / (1+e^x)^2
				return Math.exp(x) / Math.pow( (1+Math.exp(x)) , 2);
			});
	
}
