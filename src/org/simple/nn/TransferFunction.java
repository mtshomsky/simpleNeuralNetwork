package org.simple.nn;
/**
 * TransferFunction.java 
 *
 * This class provides variety of transfer functions for 
 * use by neural networks.
 * 
 **/

//import TFConstants.*;
import java.math.*;

public class TransferFunction 
{
  
    // Current Functions Type
    private int myFunction;


    /** default constructor */
    public TransferFunction () 
    {
	myFunction = TFConstants.passthrough;
    }

    /** default constructor */
    public TransferFunction (int argFN) 
    {
	if (argFN > TFConstants.max) 
	    {
		System.out.println("Error: Transfer Function incorrectly set ("+argFN+")\n" +
				   "       it is now set to a passthrough fn (0)");
		myFunction = TFConstants.passthrough;
	    }
	else 
	    {
		myFunction = argFN;
	    }
    }


    // Locally defined Transfer Functions
    private float fnPassthrough (float argVal) 
    {
	return argVal;
    }

    private float fnStep (float argVal) 
    {
	return (1.0f / (1.0f + (float)Math.exp(-argVal)) );
    }

    private float fnSigmoid (float argVal) 
    {	
	// 1/(1+e^-x)
	return (float)(1/(1+Math.exp(-argVal)));	
    }

    // Locally defined Transfer Functions derivatives
    private float fnPassthrough_prime (float argVal) 
    {
	return argVal;
    }

    private float fnStep_prime (float argVal) 
    {
	return this.fnStep(argVal)*(1-this.fnStep(argVal));
    }

    private float fnSigmoid_prime (float argVal) 
    {

         // 1/(1+e^-x)
         double sigmoid = this.fnSigmoid(argVal);

         //e^-x(1+e^-x)^-2
         return (float)((Math.exp(-1*argVal)) * Math.pow(sigmoid,2));
    }
    
   
    // Function that chooses specified function
    public float evaluate ( float argVal )
    {

	switch (myFunction) 
	    {
	    case TFConstants.passthrough: return fnPassthrough(argVal);
	    case TFConstants.step: return this.fnStep(argVal);
	    case TFConstants.sigmoid: return this.fnSigmoid(argVal);		
	    default:return fnPassthrough(argVal);
	    } 
    }

    // Function that chooses specified function
    public float derivative ( float argVal )
    {

	switch (myFunction) 
	    {
	    case TFConstants.passthrough: return fnPassthrough_prime(argVal);
	    case TFConstants.step: return this.fnStep_prime(argVal);
	    case TFConstants.sigmoid: return this.fnSigmoid_prime(argVal);		
	    default:return fnPassthrough_prime(argVal);
	    } 
    }
}

