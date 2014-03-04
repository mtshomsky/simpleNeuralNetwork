/**
 * UT_Adaline.java
 *
 * created 12/31/2003
 * 
 * @description adaline test
 * @author mtshomsky
 * 
 */

import java.util.ArrayList;

import org.simple.nn.AdalineNetwork;
import org.simple.nn.AdalineNode;
import org.simple.nn.NeuralLink;

class UT_Adaline
{

    private static void testOneNode()
    {
	AdalineNode oneNode = new AdalineNode("oneNode");
	AdalineNode terminalNode = new AdalineNode("oneNode");

	ArrayList lclInputNodes = new ArrayList();
	lclInputNodes.add(new NeuralLink (oneNode, terminalNode, 0.5f));
	lclInputNodes.add(new NeuralLink (oneNode, terminalNode, 0.5f));	
	
	oneNode.setInputNode(lclInputNodes);
	oneNode.evaluate();

	System.out.println("Testing one " + oneNode.getNodeID() + " node.");
	System.out.println(oneNode.getValue());	
    }

    private static void testNetwork()
    {
	AdalineNetwork lclNetwork = new AdalineNetwork(1,3,1);
	lclNetwork.create(3);
	System.out.println("testing AdalineNetwork 1-3-1\n"+lclNetwork.toString());

	lclNetwork = new AdalineNetwork(4);
	lclNetwork.create(4);
	System.out.println("testing AdalineNetwork 4-4-4\n"+lclNetwork.toString());
    }

    private static void testTraining()
    {
	AdalineNetwork lclNetwork = new AdalineNetwork(2,2,1);
	lclNetwork.create(3);

	// create
	System.out.println("testing AdalineNetwork training 2-4-1 (init)\n"+lclNetwork.toString());	

	// set input pattern
    ArrayList inputPattern = new ArrayList();
	inputPattern.add(new Float(0.17));
	inputPattern.add(new Float(0.71));

	// set output pattern
	ArrayList outputPattern = new ArrayList();
	outputPattern.add(new Float(0.33));

	//set the training pattern
	ArrayList trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	lclNetwork.setTrainingPattern(trainingPattern);

	// train
	lclNetwork.train();
	System.out.println("testing AdalineNetwork training 2-4-1(trained)\n"+lclNetwork.toString());

	for (int lclI=0; lclI<1000; lclI++)
	    {
		lclNetwork.train();

		//trained
		System.out.println("testing AdalineNetwork training 2-4-1(inprogress)\n"+lclNetwork.toString()); 

	    }


	//trained
	System.out.println("testing AdalineNetwork training 2-4-1(final)\n"+lclNetwork.toString()); 

	// evaluate
	lclNetwork.evaluate();
	System.out.println("testing AdalineNetwork training 2-4-1(evaled)\n"+lclNetwork.toString());	
    }

    private static void testXOR1()
    {
	AdalineNetwork lclNetwork = new AdalineNetwork(2,4,1);
	lclNetwork.create(3);

	// create
	System.out.println("testing AdalineNetwork training 2-4-1 (init)\n"+lclNetwork.toString());	


	ArrayList xorPattern = new ArrayList();


	// 0 0 => false

	// set input pattern
        ArrayList inputPattern = new ArrayList();
	inputPattern.add(new Float(0.0));
	inputPattern.add(new Float(0.0));

	// set output pattern
	ArrayList outputPattern = new ArrayList();
	outputPattern.add(new Float(0));

	//set the training pattern
	ArrayList trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);


	// 0 1 => true

	// set input pattern
        inputPattern = new ArrayList();
	inputPattern.add(new Float(0.0));
	inputPattern.add(new Float(1.0));

	// set output pattern
	outputPattern = new ArrayList();
	outputPattern.add(new Float(1.0));

	//set the training pattern
	trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);

	// 1 0 => true

	// set input pattern
        inputPattern = new ArrayList();
	inputPattern.add(new Float(1.0));
	inputPattern.add(new Float(0.0));

	// set output pattern
	outputPattern = new ArrayList();
	outputPattern.add(new Float(1.0));

	//set the training pattern
	trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);


	// 1 1 => true

	// set input pattern
        inputPattern = new ArrayList();
	inputPattern.add(new Float(1.0));
	inputPattern.add(new Float(1.0));

	// set output pattern
	outputPattern = new ArrayList();
	outputPattern.add(new Float(0.0));

	//set the training pattern
	trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);

	System.out.println("test XOR pattern");
	System.out.println("testing AdalineNetwork training 2-4-1(init)\n"+lclNetwork.toString());

	for (int lclJ=0; lclJ<xorPattern.size(); lclJ++)
	    {
		trainingPattern = (ArrayList)xorPattern.get(lclJ);
		lclNetwork.setTrainingPattern(trainingPattern);
		
		for (int lclI=0; lclI<10000; lclI++)
		    {
						       			
			lclNetwork.train();
			
			//trained
			//System.out.println("testing AdalineNetwork training 2-4-1(inprogress)\n"+
			//	   lclNetwork.toString()); 
		    
		    }
	    }

	//trained
	System.out.println("testing AdalineNetwork training 2-4-1(final)\n"+lclNetwork.toString()); 

	// evaluate
	lclNetwork.evaluate();
	System.out.println("testing AdalineNetwork training 2-4-1(evaled)\n"+lclNetwork.toString());	
    }

 private static void testXOR2()
    {
	AdalineNetwork lclNetwork = new AdalineNetwork(2,4,1);
	lclNetwork.create(3);

	// create
	System.out.println("testing AdalineNetwork training 2-4-1 (init)\n"+lclNetwork.toString());	


	ArrayList xorPattern = new ArrayList();


	// 0 0 => false

	// set input pattern
        ArrayList inputPattern = new ArrayList();
	inputPattern.add(new Float(0.0));
	inputPattern.add(new Float(0.0));

	// set output pattern
	ArrayList outputPattern = new ArrayList();
	outputPattern.add(new Float(0));

	//set the training pattern
	ArrayList trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);


	// 0 1 => true

	// set input pattern
        inputPattern = new ArrayList();
	inputPattern.add(new Float(0.0));
	inputPattern.add(new Float(1.0));

	// set output pattern
	outputPattern = new ArrayList();
	outputPattern.add(new Float(1.0));

	//set the training pattern
	trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);

	// 1 0 => true

	// set input pattern
        inputPattern = new ArrayList();
	inputPattern.add(new Float(1.0));
	inputPattern.add(new Float(0.0));

	// set output pattern
	outputPattern = new ArrayList();
	outputPattern.add(new Float(1.0));

	//set the training pattern
	trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);


	// 1 1 => false

	// set input pattern
        inputPattern = new ArrayList();
	inputPattern.add(new Float(1.0));
	inputPattern.add(new Float(1.0));

	// set output pattern
	outputPattern = new ArrayList();
	outputPattern.add(new Float(0.0));

	//set the training pattern
	trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);

	System.out.println("test XOR pattern");
	System.out.println("testing AdalineNetwork training 2-4-1(init)\n"+lclNetwork.toString());
	
	
	for (int lclI=0; lclI<4000; lclI++)
	    {
		
		for (int lclJ=0; lclJ<xorPattern.size(); lclJ++)
		    {
			trainingPattern = (ArrayList)xorPattern.get(lclJ);
			lclNetwork.setTrainingPattern(trainingPattern);

			for (int lclK=0; lclK<15; lclK++)
			    {
				lclNetwork.train();
				//trained
				//System.out.println("testing AdalineNetwork training 2-4-1(inprogress)\n"+
				//	   lclNetwork.toString()); 
				
			    }
					
		    }
	    }

	//trained
	System.out.println("testing AdalineNetwork training 2-4-1(final)\n"+lclNetwork.toString()); 

	// evaluate
	lclNetwork.evaluate();
	System.out.println("testing AdalineNetwork training 2-4-1(evaled)\n"+lclNetwork.toString());	
    }


    private static void testXOR3()
    {
	AdalineNetwork lclNetwork = new AdalineNetwork(3,5,1);
	lclNetwork.create(3);

	// create
	System.out.println("testing AdalineNetwork training 3-5-1 (init)\n"+lclNetwork.toString());	


	ArrayList xorPattern = new ArrayList();


	// 0 0 => false

	// set input pattern
        ArrayList inputPattern = new ArrayList();
	inputPattern.add(new Float(1.0));
	inputPattern.add(new Float(0.0));
	inputPattern.add(new Float(0.0));

	// set output pattern
	ArrayList outputPattern = new ArrayList();
	outputPattern.add(new Float(0));

	//set the training pattern
	ArrayList trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);


	// 0 1 => true

	// set input pattern
        inputPattern = new ArrayList();
	inputPattern.add(new Float(1.0));
	inputPattern.add(new Float(0.0));
	inputPattern.add(new Float(1.0));

	// set output pattern
	outputPattern = new ArrayList();
	outputPattern.add(new Float(1.0));

	//set the training pattern
	trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);

	// 1 0 => true

	// set input pattern
        inputPattern = new ArrayList();
	inputPattern.add(new Float(1.0));
	inputPattern.add(new Float(1.0));
	inputPattern.add(new Float(0.0));

	// set output pattern
	outputPattern = new ArrayList();
	outputPattern.add(new Float(1.0));

	//set the training pattern
	trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);


	// 1 1 => true

	// set input pattern
        inputPattern = new ArrayList();
	inputPattern.add(new Float(1.0));
	inputPattern.add(new Float(1.0));
	inputPattern.add(new Float(1.0));

	// set output pattern
	outputPattern = new ArrayList();
	outputPattern.add(new Float(0.0));

	//set the training pattern
	trainingPattern = new ArrayList();
	trainingPattern.add(inputPattern);
	trainingPattern.add(outputPattern);
	
	xorPattern.add(trainingPattern);

	System.out.println("test XOR pattern");
	System.out.println("testing AdalineNetwork training 3-5-1(init)\n"+lclNetwork.toString());

	for (int lclJ=0; lclJ<xorPattern.size(); lclJ++)
	    {
		trainingPattern = (ArrayList)xorPattern.get(lclJ);
		lclNetwork.setTrainingPattern(trainingPattern);
		
		for (int lclI=0; lclI<10000; lclI++)
		    {
						       			
			lclNetwork.train();
			
			//trained
			//System.out.println("testing AdalineNetwork training 2-4-1(inprogress)\n"+
			//	   lclNetwork.toString()); 
		    
		    }
	    }

	//trained
	System.out.println("testing AdalineNetwork training 3-5-1(final)\n"+lclNetwork.toString()); 

	// evaluate
	lclNetwork.evaluate();
	System.out.println("testing AdalineNetwork training 3-5-1(evaled)\n"+lclNetwork.toString());	
    }

    public static void main(String args[])
    {
	//testOneNode();
	//testNetwork();
	testTraining();
	//testXOR1();
	//testXOR2();	
    }

}
