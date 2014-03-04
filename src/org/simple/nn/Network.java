package org.simple.nn;
import java.util.ArrayList;

public abstract class Network extends Neuron {
    
    /** 
     * Attributes     
     */
    protected ArrayList myLayers;


    /**
     * Operation
     *
     * @return Arraylist , the layers of nodes 
     */
    public ArrayList getLayers() {return myLayers;}
        
    /**
     * Operation
     *
     * @return 
     */
    public abstract void create (int argLayers);
    
    /**
     * setLearningRate
     *
     * @return float
     */
    public abstract void setLearningRate (float argRate);
    
}

