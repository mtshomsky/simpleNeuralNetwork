package org.simple.nn;
/** 
 * AdalineNode.java
 * 
 * created 12/28/2003
 * 
 * @author mtshomsky
 * 
 */

import java.util.*;

public class AdalineNode extends Node {


  /** Node ID */
    private String myNodeID;
    private int mutationCounter;


   /** Expected Value */
    private float myExpectedValue;
    public float getExpectedValue() {return myExpectedValue;}
    public void setExpectedValue(float argExpected) {myExpectedValue = argExpected;}
     

  /** 
   * Default Constructor 
   *
   */
    public AdalineNode () 
    {
	this("AdalineNode");
    }


  /** 
   * Naming Constructor 
   *
   */
    public AdalineNode (String argString) 
    {
	this.myNodeID = argString;
	this.myInConnections = new ArrayList();
	this.myOutConnections = new ArrayList();		
	this.myOutWeights = new ArrayList();	
	this.myTransferFunction = new TransferFunction(TFConstants.sigmoid);
	this.mutationCounter = 0;
    }

  /**
   * setTransferFunction
   *
   * @param argTransferFunction
   * 
   */
    public void setTransferFunction ( int argFunction ) 
    {	
	myTransferFunction = new TransferFunction(argFunction);
    }
  

  /**
   * connectNode
   *
   * @param argNode
   * @return void 
   */
    public void connectNode ( Node argNode )
    {
	NeuralLink lcl = new NeuralLink(this, argNode);

	myInConnections.add(lcl);
    }


  /**
   * disconnectNode
   *
   * @param argNode
   * @return void
   */
    public void disconnectNode ( Node argNode )
    {
    }

  /**
   * getInputNode
   *
   * @return ArrayList
   */
    public ArrayList getInputNode (  )
    {
	return myInConnections;
    }


  /**
   * argInputNodeList
   *
   * @param argInputNodeList
   * @return void
   */
    public void setInputNode ( ArrayList argInputNodeList )
    {
	myInConnections = argInputNodeList;
    }


  /**
   * getOutputNodes
   *
   * @return ArrayList
   */
    public ArrayList getOutputNodes (  )
    {
	return myOutConnections;
    }


    /**
     * setOutputNodes
     *
     * @param argOutputNodeList
     * @return void 
     */
    public void setOutputNodes ( ArrayList argOutputNodeList )
    {
	myOutConnections = argOutputNodeList;
    }  


    /**
     * getInputNodes
     *
     * @return ArrayList
     */
    public ArrayList getInputNodes (  )
    {
	return myInConnections;
    }

    /**
     * setInputNodes
     *
     * @param argInputNodeList
     * @return void 
     */
    public void setInputNodes ( ArrayList argInputNodeList )
    {
	myInConnections = argInputNodeList;
    }  

     /**
      * Operation getTrainingPattern
      * 
      * @description sets the training pattern
      * @param ArrayList 
      */
     public ArrayList getTrainingPattern ()	    
     {
 	 return this.myTrainingPattern;
     }
     
     /**
      * Operation setTrainingPattern
      * 
      * @description sets the training pattern
      * @param ArrayList 
      */
     public String getTrainingPatternString ()	    
     {
 	 String s = "Expected:"+this.myTrainingPattern;
 	 return s;
     }

    /** 
     * getWeightToOutputNode
     * 
     * @param argNode
     * @return float Weight to node
     */
     public float getWeightToOutputNode (int argNodeIndex)
     {
     	NeuralLink lclLink; // null link
     	// Get the appropriate connection
     	lclLink = (NeuralLink) myOutConnections.get(argNodeIndex);
     	    	
     	return lclLink.getWeight();
     }
    
    /** 
     * getWeightToInputNode
     * 
     * @param argNode
     * @return float Weight to node
     */
    public float getWeightToInputNode (int argNodeIndex)
    {
     	NeuralLink lclLink; // null link
     	// Get the appropriate connection
     	lclLink = (NeuralLink) myInConnections.get(argNodeIndex);
     	    	
     	return lclLink.getWeight();
    }

    /** 
     * getNodeID
     *
     * @return String
     */
    public String getNodeID ()
    {
	return myNodeID;
    }

    /** 
     * setNodeID
     *
     * @return String
     */
    public void setNodeID (String argString)
    {
	myNodeID = argString;
    }

   /**
     * Operation setTrainingPattern
     * 
     * @description sets the training pattern
     * @param ArrayList 
     */
    public void setTrainingPattern (ArrayList argPattern)	    
    {
	this.myTrainingPattern = argPattern;
    }
    

  /**
   * Operation
   *
   * @description evaluate inputs and apply to the outputs
   * @return void
   */
    public void evaluate ()
    {
	if (!this.isInput)
	    {
		// local node element
		float lclWeight;
		float lclSum = 0;
		NeuralLink lclLink;
		AdalineNode lclOrigin;
		
		// loop through each input element
		for (int lclI=0, lclSize=myInConnections.size(); lclI < lclSize; lclI++)
		{
		    // get link
		    lclLink = (NeuralLink) myInConnections.get(lclI);
		    
		    // get origin
		    lclOrigin = (AdalineNode) lclLink.getOrigin();
		    		    
		    /* 
		       get the weight from the origin, because
		       for adaline nodes we just use the input links 
		       to link back to the origin
		    */
		    lclWeight = lclLink.getWeight();
		    
		    // multiply origin value and weight and add to sum
		    lclSum += lclWeight * lclOrigin.getValue();
		}
		
		// apply sum to transfer function and set the value 
		// of this function
		
		this.setValue(this.myTransferFunction.evaluate(lclSum));
	    }
    }

 /**
   * Operation
   *
   * @description evaluate inputs and apply to the outputs
   * @return float
   */
    public float getActivation ()
    {
	float lclSum = 0;

	if (!this.isInput)
	    {
		// local node element
		float lclWeight;
		NeuralLink lclLink;
		AdalineNode lclOrigin;
		
		// loop through each input element
		for (int lclI=0, lclSize=myInConnections.size(); lclI < lclSize; lclI++)
		{
		    // get link
		    lclLink = (NeuralLink) myInConnections.get(lclI);
		    
		    // get origin
		    lclOrigin = (AdalineNode) lclLink.getOrigin();
		    		    
		    // get weight from the origin to this node
		    lclWeight = lclLink.getWeight();
		    
		    // multiply origin value and weight and add to sum
		    lclSum += lclWeight * lclOrigin.getValue();
		}
	    }

	return(this.myTransferFunction.evaluate(lclSum));
    }

 /**
   * Operation
   *
   * @description evaluate inputs and apply to the outputs
   * @return float
   */
    public float getActivationPrime ()
    {
	float lclSum = 0.0f;
	float returnme =0.0f;

	if (!this.isInput)
	    {
		// local node element
		float lclWeight;
		NeuralLink lclLink;
		AdalineNode lclOrigin;
		
		// loop through each input element
		for (int lclI=0, lclSize=myInConnections.size(); lclI < lclSize; lclI++)
		{
		    // get link
		    lclLink = (NeuralLink) myInConnections.get(lclI);

		    // get weight from the origin to this node
		    lclWeight = lclLink.getWeight();
		    		    
		    // get origin
		    lclOrigin = (AdalineNode) lclLink.getOrigin();
		    		    
		    // multiply origin value and weight and add to sum
		    lclSum += lclWeight * lclOrigin.getValue();
		}
		
		returnme = (this.myTransferFunction.derivative(lclSum));
	    }
	else {
	    returnme = 1.0f;
	}        

	return returnme;
    }


    /**
     * Operation
     *
     * @description train a neuron
     * @return void
     */
    public void train ()
    {
	NeuralLink lclLink;
	AdalineNode lclInputNode;
	AdalineNode lclOutputNode;
	
	float lclExpectedMinusCurrent = 0;
	float lclDeltaW = 0.0f;
	float lclDeltaRule = 0.0f;
	float lclTFNode_prime = 0.0f;

	//!!dbg
	//System.out.println("node:training:"+this+":insize="+myInConnections.size() +":outsize="+
	//                    myOutConnections.size());

	if (isOutput)    	    
	    {
		//!!dbg
		//System.out.println("lclExpectedMinusCurrent="+lclExpectedMinusCurrent);

		/*
		  Traverse all connections leading into this node
		  Determine how much the weight should change and
		  Update the input weight
		*/
		
		for (int lclI = 0; lclI<myInConnections.size(); lclI++)
		    {
			// Difference between expected(training) and output		
			// (training_set - current_output) 
			//        
			lclExpectedMinusCurrent = 
			    (
			     ((Float) ((ArrayList)this.getTrainingPattern()).get(0)).floatValue()-
			     (float) (this.getActivation())
			     );

			//acquire the link and the node leading into this one
			lclLink = (NeuralLink) myInConnections.get(lclI);
			lclInputNode = (AdalineNode)lclLink.getOrigin();

			// The transfer function derivative with the current value applied
			// TF'(this.value)
			//lclTFNode_prime = this.myTransferFunction.derivative(this.getActivation());
			lclTFNode_prime = this.getActivationPrime();

			// delta_rule = TF'(value) * (training_set - current_output)
			lclDeltaRule = lclTFNode_prime *  lclExpectedMinusCurrent;

			// delta_weight = learning rate * delta_rule * connecting node
			lclDeltaW = this.myLearningRate * lclDeltaRule * lclInputNode.getValue();

			/* Bump the Weight up or down to get out of stuck undesirable configurations
			if((mutationCounter % 71) == 0)
			    {
				lclDeltaW += lclDeltaW * 0.5f;
			    }
			else if((mutationCounter % 170) == 0)
			    {
				lclDeltaW -= lclDeltaW * -0.5f;
			    }									
			++mutationCounter;
			*/

			// new input link weight = oldWeight + deltaWeight
			lclLink.setWeight(lclLink.getWeight() + lclDeltaW);

			//!!dbg
			//System.out.println("otpt_newweight="+lclLink.getWeight());
		    }

               	//set the error squared for this node
		this.setError(0.5f * (float)(Math.pow((lclExpectedMinusCurrent), 2)));  
		//this.setError(lclExpectedMinusCurrent);

	    }	
	else if (isHidden || isInput)
	    {
		
	       /** Determine the error for this node*/
	       
		float lclSumOfWeightedErrors = 0; 
		
		for (int lclJ = 0; lclJ<myOutConnections.size(); lclJ++)
		{
			/* 
                           Take the error value of each of the nodes
			   this node links to.  Multiply that by
			   the weight of the link.  Set this error to
		           that weighted sum. 
			*/

		    lclLink = (NeuralLink) myOutConnections.get(lclJ);
		    lclOutputNode = (AdalineNode) lclLink.getTerminal();

		    //System.out.println("lclsum dbg: this="+this+"terminal="+lclOutputNode);
		    
		    lclSumOfWeightedErrors += lclLink.getWeight() * 
			                      lclOutputNode.getError();
		    
		    /*dbg System.out.println("lclsum dbg: terminalerr="+lclOutputNode.getError()+
			               "weight="+lclLink.getWeight());*/
		}

		//set the error for this node
		this.setError(lclSumOfWeightedErrors);
		
		for (int lclI = 0; lclI<myInConnections.size(); lclI++)
		    {
			//acquire the link and the node leading into this one
			lclLink = (NeuralLink) myInConnections.get(lclI);
			lclInputNode = (AdalineNode)lclLink.getOrigin();
		       		       
			// delta_rule = TF'(value) * SumOfWeightedErrors;
			lclDeltaRule = lclTFNode_prime * lclSumOfWeightedErrors;

			// delta_weight = learning rate * delta_rule * connecting node
			lclDeltaW = this.myLearningRate * lclDeltaRule * lclInputNode.getValue();
			
			// new input link weight = deltaW + oldWeight
			lclLink.setWeight(lclLink.getWeight() + lclDeltaW);

			//!!dbg	 
                        /*System.out.println("\nhdin_newweight="+lclLink.getWeight()+
                                           "\n              TF'="+lclTFNode_prime+
                          "\n              lclSumOfWeightedErrors="+lclSumOfWeightedErrors+
                                           "\n              lclLinkW="+lclLink.getWeight()+
                                           "\n              deltarule="+lclDeltaRule+
                                           "\n              deltaW="+lclDeltaW);
                         */
		    }
	    }
    }
    
  /**
   * Operation
   *
   * @description load neuronal information
   * @return void
   */
    public void load (  )
    {
    }

  /**
   * Operation
   *
   * @description save the current features
   * @return void
   */
    public void save (  )
    {
    }

    /** 
     * toString();
     *
     * @return String
     */
    public String toString()
    {
	return this.getNodeID();
    }
    
    

}

