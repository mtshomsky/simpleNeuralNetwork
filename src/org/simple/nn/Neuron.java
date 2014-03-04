package org.simple.nn;
import java.util.*;

public abstract class Neuron {

    protected ArrayList myTrainingPattern;

    /**
     * Operation
     *
     * @description evaluate inputs and apply to outputs
     * @return void
     */
    abstract public void evaluate (  );

    /**
     * Operation
     *
     * @description train a neuron
     * @return void
     */
    abstract public void train (  );

    /**
     * Operation
     *
     * @description load neuronal information
     * @return void
     */
    abstract public void load (  );

    /**
     * Operation
     *
     * @description save the current features
     * @return void
     */
    abstract public void save (  );


    /**
     * Operation setTrainingPattern
     * 
     * @description sets the training pattern
     * @param ArrayList 
     */
    public void setTrainingPattern (ArrayList argPattern)	    
    {
	myTrainingPattern = argPattern;
    }
    
    /**
     * Operation getTrainingPattern
     * 
     * @description sets the training pattern
     * @return ArrayList
     */
    public ArrayList getTrainingPattern ()	    
    {
	return myTrainingPattern;
    }


}



