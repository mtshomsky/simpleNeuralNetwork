package org.simple.nn;
/** 
 * AdalineNetwork.java
 * 
 * created 12/28/2003
 *
 * @author mtshomsky
 * 
 */

import java.util.*;

public class AdalineNetwork extends Network 
{

    /** 
     * Attributes     
     */
    private boolean myIsCreated;
    private int myNodesPerLayer;
    private int myNumInputNodes;
    private int myNumOutputNodes;

    /**
     * Constructors
     */   

    /** Default */
    public AdalineNetwork ()
    {
	// set the nodes per layer to 1
	this(1);
    }

    /** @Param argNodesPerLayer */
    public AdalineNetwork (int argNodesPerLayer)
    {
	myLayers = new ArrayList();
	myIsCreated = false;
	myNodesPerLayer = argNodesPerLayer;
	myNumInputNodes = myNumOutputNodes = myNodesPerLayer;	
    }

    /** @Param argInputNodes, argNodesPerHiddenLayers, argOutputNodes  */
    public AdalineNetwork (int argInputNodes, 
			   int argNodesPerHiddenLayers, 			   
			   int argOutputNodes)
    {
	myLayers = new ArrayList();
	myIsCreated = false;
	myNumInputNodes = argInputNodes;
	myNodesPerLayer = argNodesPerHiddenLayers;
	myNumOutputNodes = argOutputNodes;
    }
    
    /**
     * getNumLayers
     *
     * @return int Layers
     */    
    public int getNumLayers ()
    {
 	// return the first layer
 	return (int)myLayers.size();
    }

    /**
     * getNodesAtLayer
     *
     * @return ArrayList inputNodes
     */    
    public ArrayList getNodesAtLayer (int argLayer)
    {
	
        if (argLayer < getNumLayers())
	    {    	
		// return the first layer
		return (ArrayList) myLayers.get(argLayer);
	    } 
        else 
	    {
		return null;
	    }
        
    }

    /**
     * getInputNodes
     *
     * @return ArrayList inputNodes
     */    
    public ArrayList getInputNodes ()
    {
	// return the first layer
	return (ArrayList) myLayers.get(0);
    }

    /**
     * getOutputNodes
     *
     * @return ArrayList inputNodes
     */    
    public ArrayList getOutputNodes ()
    {
	// get the last layer and correct for cardinality
	int lclLastLayer = myLayers.size() - 1;
	
	return (ArrayList) myLayers.get(lclLastLayer);
    }
        
    

    /**
     * create_layers
     * 
     * create many layers of Adaline Nodes 
     * 
     * @param int the amount of layers of nodes
     * 
     */
    public void create_layers (int argLayers)
    {
	ArrayList lclOneLayer;
	AdalineNode lclNode;
	

	if (!this.myIsCreated)
	    {
		this.myIsCreated = true;
				
		// setup the input layer
		myLayers.add(new ArrayList());
		lclOneLayer = (ArrayList) myLayers.get(0);
					
		for (int lclI=0; lclI<myNumInputNodes; lclI++)
		    {
			lclNode = new AdalineNode("Ada_in_0_"+lclI);	
			lclNode.setInput();
			lclOneLayer.add(lclNode);
		    }
		
		// setup the hidden layers
		for (int lclLayer=1; lclLayer<argLayers-1; lclLayer++)
		    {		
			myLayers.add(new ArrayList());
			lclOneLayer = (ArrayList) myLayers.get(lclLayer);
			
			
			for (int lclI=0; lclI<myNodesPerLayer; lclI++)
			    {
			        lclNode = new AdalineNode("Ada_"+lclLayer+
							  "_"+lclI);
				lclNode.setHidden();
				lclOneLayer.add(lclNode);
			    }
			
		    }
		
		// setup the output layer       	
		myLayers.add(new ArrayList());
		int lclLastLayer = myLayers.size() - 1;
		lclOneLayer = (ArrayList) myLayers.get(lclLastLayer);
		
		for (int lclI=0; lclI<myNumOutputNodes; lclI++)
		    {
			lclNode = new AdalineNode("Ada_out_"+lclLastLayer+
						  "_"+lclI);
			lclNode.setOutput();
			lclOneLayer.add(lclNode);
		    }
	    }
    }


    /**
     * link_layers
     * 
     * @param none (uses myLayers which is internal to AdalineNetwork)
     * 
     */
    public void link_layers ()
    {
	LinkTopology fullyConnected= new LinkTopology(this.myLayers);
	fullyConnected.fully_connected_link_layers();	
    }

    /**
     * create
     * 
     * Note: Necessary for extending Network
     * 
     * create many layers of Adaline Nodes
     * 
     */
    public void create (int argLayers)
    {
	this.create_layers (argLayers);
	this.link_layers();	
    }

    /**
     * setLearningRate
     *
     * @return float
     */
    public void setLearningRate (float argRate)
    {
	ArrayList lclTempNodes;
	AdalineNode lclNode;

	if (this.myIsCreated)
	    {

		for (int lclLayerI=0; lclLayerI<=myLayers.size()-2; lclLayerI++)
		    {		
			lclTempNodes = (ArrayList)myLayers.get(lclLayerI);
	 
			
			for (int lclNodeI=0; lclNodeI<lclTempNodes.size(); lclNodeI++)
			    {
				lclNode = (AdalineNode)lclTempNodes.get(lclNodeI);

				lclNode.setLearningRate(argRate);			       
			    }
		    }		
	    }	
    }

    
    /**
     * Operation
     * 
     * Note: Necessary for extending Network
     * 
     * 
     * @description evaluate inputs and apply to outputs
     * @return void
     */
    public void evaluate ()	    
    {
	ArrayList lclTempLayer;
	AdalineNode lclTempNode;

	for (int lclLayerI=0; lclLayerI<myLayers.size(); lclLayerI++)
	    {		
		lclTempLayer = (ArrayList)myLayers.get(lclLayerI);

		for (int lclNodeI=0; lclNodeI<lclTempLayer.size(); lclNodeI++)
		    {
			lclTempNode = (AdalineNode)lclTempLayer.get(lclNodeI);
			lclTempNode.evaluate();
		    }
	    }
	
    }

    /** 
     *  trainNtimes
     *  @description Train the adaloine network multiple times
     *  
     * @param int argN
     */
    public void trainNtimes (int argN)
    {
    	for (int i=0; i<argN; i++)
    	{
    		this.train();
    	}
    
    }
  
   /**
     * Operation
     *
     * @description Train the adaline network
     *              Determine the error at the output layer,
     *              Then work back through each layer and train.
     * 
     * @return void
     */
    public void train ()
    {
	ArrayList lclTempLayer;
	AdalineNode lclTempNode;
	ArrayList lclTempPattern;
	ArrayList lclNodePattern;
	int lclLayerI = 0;
	Float lclNodeValue;
	Float lclExpectedValue;

	if (myTrainingPattern != null)
	    {

		/** set training inputs */

		// set the input pattern
		lclTempPattern = new ArrayList((ArrayList)myTrainingPattern.get(0));
		//lclTempPattern.add(myTrainingPattern.get(0));//inputs
		lclTempLayer = (ArrayList)myLayers.get(0);//set to the input layer 

		for (int lclNodeI=0; lclNodeI<lclTempLayer.size(); lclNodeI++)
		    {							
			// set all nodes to the input pattern
			lclTempNode = (AdalineNode)lclTempLayer.get(lclNodeI);
			lclNodeValue = (Float)lclTempPattern.get(lclNodeI);
			lclTempNode.setValue(lclNodeValue.floatValue());
			
			// !!dbg
			//System.out.println("network: node input pattern:"+lclNodeValue.floatValue());
		    }
		
		/** determine error at output */

		//set to the last layer (output layer)
		lclLayerI=myLayers.size()-1; 
		lclTempLayer = (ArrayList)myLayers.get(lclLayerI);		

		//get the output pattern
		lclTempPattern = new ArrayList((ArrayList)myTrainingPattern.get(1));		

		for (int lclNodeI=0; lclNodeI<lclTempLayer.size(); lclNodeI++)
		    {	
			// evaluate this network first
			this.evaluate();

			// get the expected value for a node
			lclExpectedValue = new Float(0.0f);
			lclExpectedValue = (Float)lclTempPattern.get(lclNodeI);
			lclNodePattern = new ArrayList();			
			lclNodePattern.add(lclExpectedValue);

			// get the node object
			lclTempNode = (AdalineNode)lclTempLayer.get(lclNodeI);
			
			// set the expected value and train
			lclTempNode.setTrainingPattern(lclNodePattern);
			lclTempNode.train();
			
			// !!dbg
			/*System.out.println(
					   "network:trained output node:"+
					   ":a="+ lclTempNode.getActivation() +
					   ":::o="+lclTempNode.getValue()+
					   ":e="+lclExpectedValue.floatValue()+
					   ":err="+lclTempNode.getError()
					   );*/
		    }
	       		
		/** train remaining nodes starting at the layer right before the output layer*/
		for (lclLayerI=myLayers.size()-2; lclLayerI>=0; lclLayerI--)
		    {							
			lclTempLayer = (ArrayList)myLayers.get(lclLayerI);
			
			for (int lclNodeI=0; lclNodeI<lclTempLayer.size(); lclNodeI++)
			    {	

				// evaluate this network first
				this.evaluate();
			
				lclTempNode = (AdalineNode)lclTempLayer.get(lclNodeI);
				lclTempNode.train();

				// !!dbg
				//System.out.println("network:trained hidden node:"+lclTempNode);
			    }
		    }
	    }
	else
	    {
		System.out.println("Network Train Error: no pattern");
	    }
    }

    /**
     * Operation
     * 
     * Note: Necessary for extending Network
     * 
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
     * Note: Necessary for extending Network
     * 
     *
     * @description save the current features
     * @return void
     */
    public void save (  )
    {
    }



    public String layerReport ()
    {
	String lclReturnMe = new String();
	ArrayList lclTempLayer;
	AdalineNode lclTempNode;

	for (int lclLayerI=0; lclLayerI<myLayers.size(); lclLayerI++)
	    {		
		lclTempLayer = (ArrayList)myLayers.get(lclLayerI);

		for (int lclNodeI=0; lclNodeI<lclTempLayer.size(); lclNodeI++)
		    {
			lclTempNode = (AdalineNode)lclTempLayer.get(lclNodeI);
			lclReturnMe += " (" + lclLayerI + "," + lclNodeI +")=" 
			    + lclTempNode.toString() + " ";			
		    }
		lclReturnMe += "\n";
	    }

	return lclReturnMe;		
    }

    public String valueReport ()
    {
	String lclReturnMe = new String();
	ArrayList lclTempLayer;
	AdalineNode lclTempNode;

	for (int lclLayerI=0; lclLayerI<myLayers.size(); lclLayerI++)
	    {		
		lclTempLayer = (ArrayList)myLayers.get(lclLayerI);

		for (int lclNodeI=0; lclNodeI<lclTempLayer.size(); lclNodeI++)
		    {
			lclTempNode = (AdalineNode)lclTempLayer.get(lclNodeI);
			lclReturnMe += " (" + lclLayerI + "," + lclNodeI +")=" 
			    + lclTempNode.toString() + "::"+ lclTempNode.getValue();
		    }
		lclReturnMe += "\n";
	    }

	return lclReturnMe;		
    }

  public String valueVectorsReport ()
    {
	String lclReturnMe = new String();
	ArrayList lclTempLayer;
	AdalineNode lclTempNode;

	for (int lclLayerI=0; lclLayerI<myLayers.size(); lclLayerI++)
	    {		
		lclTempLayer = (ArrayList)myLayers.get(lclLayerI);

		for (int lclNodeI=0; lclNodeI<lclTempLayer.size(); lclNodeI++)
		    {
			lclTempNode = (AdalineNode)lclTempLayer.get(lclNodeI);
			lclReturnMe += " " + lclTempNode.getValue();
		    }
		lclReturnMe += "\n";
	    }

	return lclReturnMe;		
    }



 public String linkReport ()
    {
	String lclReturnMe = new String();
	ArrayList lclTempLayer;
	AdalineNode lclTempNode;
	ArrayList lclTempInLinks;
	ArrayList lclTempOutLinks;
	NeuralLink lclLink;

	for (int lclLayerI=0; lclLayerI<myLayers.size(); lclLayerI++)
	    {		
		lclTempLayer = (ArrayList)myLayers.get(lclLayerI);

		lclReturnMe += "\nLayer:" + lclLayerI + "\n";

		for (int lclNodeI=0; lclNodeI<lclTempLayer.size(); lclNodeI++)
		    {
			lclTempNode = (AdalineNode)lclTempLayer.get(lclNodeI);
			
			lclTempOutLinks = (ArrayList)lclTempNode.getOutputNodes();
			lclTempInLinks = (ArrayList)lclTempNode.getInputNodes();
			
			lclReturnMe += "\n#outlinks:"+lclTempOutLinks.size()+"--------------\n";
			
			for (int lclLinkI=0; lclLinkI<lclTempOutLinks.size(); lclLinkI++)
			    {
				lclLink = (NeuralLink)lclTempOutLinks.get(lclLinkI);

				lclReturnMe += " ( node:" + lclNodeI +", link:"+
				               lclLinkI+") Link:" +
				               lclLink.toString() + " ";			
			    }

			lclReturnMe += "\n#inlinks:"+lclTempInLinks.size()+"--------------\n";

			for (int lclLinkI=0; lclLinkI<lclTempInLinks.size(); lclLinkI++)
			    {
				lclLink = (NeuralLink)lclTempInLinks.get(lclLinkI);

				lclReturnMe += " ( node:" + lclNodeI +", link:"+
				               lclLinkI+") Link:" +
				               lclLink.toString() + " ";			
			    }


			lclReturnMe += "\n";
		    }
	    }

	return lclReturnMe;		
    }

    /**
     * toString
     * 
     * @return String
     */
    public String toString ()	    
    {
	return "Layers:\n"+ this.layerReport() + 
	       "\n\n\nValues:\n"+ this.valueReport() + 
	       "\n\n\nLinks:\n"+ this.linkReport() +
	       "\n\n\nValue Vectors:\n"+ this.valueVectorsReport();
       
    }
    
}

