package org.simple.nn;
import java.util.*;

public class NeuralLink 
{
    /** attributes */

    private Node myOrigin;
    private Node myTerminal;
    private float myWeight;
    private boolean myIsWeighted;

    /** constructors */

    public NeuralLink (Node argBegin, Node argEnd)
    {
	myOrigin = argBegin;
	myTerminal = argEnd;
	myIsWeighted = false;	
	myWeight = 1.0f;
    }

    public NeuralLink (Node argBegin, Node argEnd, float argWeight)
    {
	this(argBegin, argEnd);
	myWeight = argWeight;
	myIsWeighted = true;
	myWeight = argWeight;
    }

    /** accessors */    

    public void setWeight (float argWeight)
    {
	myWeight = argWeight;
	myIsWeighted = true;
    }

    public void randWeight ()
    {
	myWeight = (float)Math.random();
	myIsWeighted = true;
    }

    public float getWeight ()
    {
	// add in an error here if there is no weight
	if (!myIsWeighted)
	    {
		System.out.println("Error:NerualLink is not a weighted link:"+this);
	    }
	
	return myWeight;
    }

    public Node getOrigin ()
    {
	return myOrigin;
    }

    public void setOrigin (Node argNode)
    {
	myOrigin = argNode;
    }

    public Node getTerminal ()
    {
	return myTerminal;
    }

    public void setTerminal (Node argNode)
    {
	myTerminal = argNode;
    }

    /** 
     * toString 
     *
     * @return String
     */

    public String toString()
    {
	String lclReturnme = new String();
	
	if (myOrigin != null)
	    {
		lclReturnme = "Origin:" + myOrigin;		
	    }

	if (myTerminal != null)
	    {
		lclReturnme += "Terminal:" + myTerminal;		
	    }

	lclReturnme += "Weight:" + myWeight;
	
	return lclReturnme;
    }
}
