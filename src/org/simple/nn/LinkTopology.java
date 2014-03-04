package org.simple.nn;
/**
 * LinkTopology.java
 *
 * @description Provides different ways to link nodes by passing in layers of nodes
 * 
 **/

import java.util.ArrayList;

public class LinkTopology 
{

    /** 
     * Attributes 
     */
    private ArrayList myLayers;
    

    /**
     * Default Constructor
     * 
     * @param ArrayList of Layers with Neurons
     */    
    public LinkTopology (ArrayList argLayers)
    {
	// set myLayers to point to the object being passed in
	this.myLayers = argLayers;
    }


    /**
     * Fully Connected Layers 
     *
     */
    public void fully_connected_link_layers ()
    {	
	ArrayList lclInputNodes = new ArrayList();
	ArrayList lclOutputNodes = new ArrayList();
	ArrayList lclTempLayerForwardA, lclTempLayerForwardB;
	AdalineNode lclTempNodeA = new AdalineNode();
	AdalineNode lclTempNodeB = new AdalineNode();
	NeuralLink lclNL;

	// link all layers inbetween output and input
    	for (int lclLayerI=0; lclLayerI<=myLayers.size()-2; lclLayerI++)
	    {		
		System.out.println("linking layers:"+lclLayerI);

		lclTempLayerForwardA = (ArrayList)myLayers.get(lclLayerI);
		lclTempLayerForwardB = (ArrayList)myLayers.get(lclLayerI+1);

		for (int lclNodeA=0; lclNodeA<lclTempLayerForwardA.size(); lclNodeA++)
		    {
			lclTempNodeA = (AdalineNode)lclTempLayerForwardA.get(lclNodeA);

			lclOutputNodes = new ArrayList();

			for (int lclNodeB=0; lclNodeB<lclTempLayerForwardB.size(); lclNodeB++)
			    {
				lclTempNodeB = (AdalineNode)lclTempLayerForwardB.get(lclNodeB);

				// retrieve list of input nodes for current node
				lclInputNodes = lclTempNodeB.getInputNodes();

				// !! Basically the problem here is, output weights 
				// !! aren't being set or the input weights aren't being set.
				// !! What should be happening is output nodes and input nodes
				// !! should share the same weight
				/*
				//create the output link from node A to node B	
				lclOutputNodes.add(new NeuralLink (lclTempNodeA, lclTempNodeB, 0.5f));
				
				// Record that node B links to node A by setting node B's input node
 				lclInputNodes.add(new NeuralLink (lclTempNodeB, lclTempNodeA));
				*/

				//create the output link from node A to node B	
				lclNL = new NeuralLink (lclTempNodeA, lclTempNodeB);
				lclNL.randWeight();
				
				// add the output link
				lclOutputNodes.add(lclNL);
				
				// Record that node A links to node B by setting node B's input
				// to the output of nodeA
 				lclInputNodes.add(lclNL);

				// set the updated list of inputs for current NodeB
				lclTempNodeB.setInputNodes(lclInputNodes);
			    }

			// set the updated list of outputs for current NodeA
			lclTempNodeA.setOutputNodes(lclOutputNodes);			
		    }
	    }
    }
}
