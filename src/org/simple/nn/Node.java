package org.simple.nn;
/**
 * Node.java
 * 
 * created 12/22/2003
 *
 * @author mtshomsky
 * 
 * This class contains methods useful to neural networks
 * 
 * Note:
 *   ArrayLists are used for connections because it 
 *   implements serializable
 *
 **/

import java.util.*;


public abstract class Node extends Neuron {

    /** Attributes */
    protected float myValue = 1.0f; // needs to be > 0
    protected float myError = 0.0f;
    protected float myLearningRate = 0.25f;
    protected ArrayList myInConnections;
    protected ArrayList myInWeights;
    protected ArrayList myOutConnections;
    protected ArrayList myOutWeights;
    protected TransferFunction myTransferFunction;
    protected boolean isHidden = true;
    protected boolean isInput = false;
    protected boolean isOutput = false;
    
    /**
     * getValue
     *
     * @return float
     */
    public float getValue () { return myValue; }
    
    
    /**
     * setValue
     *
     * @return float
     */
    public void setValue (float argValue) {myValue = argValue;}
    
    
    /**
     * getError
     *
     * @return float
     */
    float getError () { return myError; }

    
    /**
     * setError
     *
     * @return float
     */
    public void setError (float argError) { myError = argError; }

    /**
     * setLearningRate
     *
     * @return float
     */
    public void setLearningRate (float argRate) { myLearningRate = argRate; }

    /**
     * setHidden
     *
     * @return void
     */
    public void setHidden () 
    { 
      isHidden = true;
      isInput = false;
      isOutput = false; 
    }

    /**
     * setInput
     *
     * @return void
     */
    public void setInput () 
    { 
      isHidden = false;
      isInput = true;
      isOutput = false; 
    }

    /**
     * setOutput
     *
     * @return void
     */
    public void setOutput () 
    { 
      isHidden = false;
      isInput = false;
      isOutput = true; 
    }    
    
    /**
     * setTransferFunction
     *
     * @param argTransferFunction
     * @return 
     */
    abstract public void setTransferFunction ( int argTransferFunction );
    


    /**
     * connectNode
     *
     * @param argNode
     * @return void 
     */
    abstract public void connectNode ( Node argNode );
  
    
    
    /**
     * disconnectNode
     *
     * @param argNode
     * @return void
     */
    abstract public void disconnectNode ( Node argNode );

    
    /**
     * getInputNode
     *
     * @return ArrayList
     */
    abstract public ArrayList getInputNode (  );
    
    
    
    /**
     * argInputNodeList
     *
     * @param argInputNodeList
     * @return void
     */
    abstract public void setInputNode ( ArrayList argInputNodeList );
    


    /**
     * getOutputNodes
     *
     * @return ArrayList
     */
    abstract public ArrayList getOutputNodes (  );
    


    /**
     * setOutputNodes
     *
     * @param argOutputNodeList
     * @return void 
     */
    abstract public void setOutputNodes ( ArrayList argOutputNodeList );



    /**
     * getInputNodes
     *
     * @return ArrayList
     */
    abstract public ArrayList getInputNodes (  );


    /**
     * setInputNodes
     *
     * @param argInputNodeList
     * @return void 
     */
    abstract public void setInputNodes ( ArrayList argInputNodeList );

}

