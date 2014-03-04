/**
 * UT_Adaline.java
 *
 * created 12/31/2003
 * 
 * @description adaline test
 * @author mtshomsky
 * @author fyao
 * 
 */


import java.awt.Point;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Vector;
import java.util.*;
import java.text.NumberFormat;
import org.simple.nn.*;


/**
 * Contains the graphics for representing a neural net nodes.
 */
public class AdalineGUI extends JFrame implements Observer
{
   private BufferedImage gImage;
   private Graphics2D gS;
   private boolean firstTime = true;

   private JFrame thisFrame;

   private JPanel drawPNL;
   private JPanel buttonsPNL;
   private JButton getNodesFromFileBTN;
   private JButton generateBTN;
   private JButton saveBTN;
   private JButton trainBTN;
   private JButton textDumpBTN;

   private JPanel dataPNL;
   private JPanel dataBTNPNL;
   private JButton addTrainingSetBTN;
   private JButton testInputsBTN;
   private JButton removeTrainingSetBTN;
   private JList dataList;
   private JScrollPane scrollPane;


   private JTextField [] inputsTXF;
   private JTextField [] outputsTXF;
   private List testInputs;
   private List testOutputs;

   private boolean imageUpdateNeeded = true;
   private final int APP_WIDTH =  1000;
   private final int APP_HEIGHT = 700;
   private final int DRAW_PANEL_WIDTH = APP_WIDTH;
   private final int DRAW_PANEL_HEIGHT = 500;
	private final int DATA_PANEL_WIDTH = APP_WIDTH;
	private final int DATA_PANEL_HEIGHT = 200;
	private final int SCROLL_PANEL_WIDTH = 200;
	private final int SCROLL_PANEL_HEIGHT = 100;

   private final int NODE_WIDTH = 25;
   private final int NODE_HEIGHT = 25;
   private final int NODE_SPACING = 130;
   private final int ARROW_LENGTH = NODE_SPACING;
   private final int ARROW_HEIGHT_OFFSET = NODE_HEIGHT/2;

   private final int DATA_TABLE_X_OFFSET = DRAW_PANEL_WIDTH-310;
   private final int DATA_TABLE_Y_OFFSET = 50;
   private final int DATA_TEXT_HEIGHT = 15;
   private final Color [] COLOR_ARRAY = {Color.black, Color.blue, Color.black,
   												  Color.blue, Color.black, Color.blue,
   												  Color.black, Color.gray};
	private int iterations = 100;
	private int numInputs;
	private int numOutputs;
   private AdalineNetwork net;

   public AdalineGUI() {
      super();
      thisFrame = this;
      setTitle("Adaline Analyzer");

      setLayout(new BorderLayout());
      setSize(APP_WIDTH,APP_HEIGHT);

		MyActionListener myactionlistener = new MyActionListener();

      drawPNL = new DrawPNL();
      drawPNL.setLayout(null);
      drawPNL.setSize(DRAW_PANEL_WIDTH,DRAW_PANEL_HEIGHT);
      drawPNL.setVisible(true);
      getNodesFromFileBTN = new JButton("Load");
      getNodesFromFileBTN.addActionListener(myactionlistener);
      generateBTN = new JButton("Generate");
      generateBTN.addActionListener(myactionlistener);
      saveBTN = new JButton("Save");
      saveBTN.addActionListener(myactionlistener);
      trainBTN = new JButton("Train");
      trainBTN.addActionListener(myactionlistener);
      textDumpBTN = new JButton("Dump Text");
      textDumpBTN.addActionListener(myactionlistener);

      buttonsPNL = new JPanel(new FlowLayout());
      buttonsPNL.add(getNodesFromFileBTN);
      buttonsPNL.add(generateBTN);
      buttonsPNL.add(saveBTN);
      buttonsPNL.add(trainBTN);
      buttonsPNL.add(textDumpBTN);

      dataPNL = new JPanel(new FlowLayout());
      dataPNL.setSize(DATA_PANEL_WIDTH,DATA_PANEL_HEIGHT);
      dataPNL.setVisible(true);
      dataBTNPNL = new JPanel(new FlowLayout());
      scrollPane = new JScrollPane();
      scrollPane.setBounds (0, 0, SCROLL_PANEL_WIDTH, SCROLL_PANEL_HEIGHT);
		scrollPane.getViewport().setViewSize(new Dimension());
		dataPNL.add(scrollPane);

      addTrainingSetBTN = new JButton("Add Training Set");
      addTrainingSetBTN.addActionListener(myactionlistener);
      removeTrainingSetBTN = new JButton("Remove Selected Set");
      removeTrainingSetBTN.addActionListener(myactionlistener);

      testInputsBTN = new JButton("Test Selected Set");
      testInputsBTN.addActionListener(myactionlistener);
      dataBTNPNL.add(addTrainingSetBTN);
      dataBTNPNL.add(removeTrainingSetBTN);
      dataBTNPNL.add(testInputsBTN);

      add(drawPNL, BorderLayout.CENTER);
      add(buttonsPNL, BorderLayout.NORTH);
      add(dataPNL, BorderLayout.SOUTH);
      setVisible(true);
   }

	public void	update(Observable o, Object arg)
	{
		 imageUpdateNeeded = true;
		 drawPNL.repaint();
	}

   class DrawPNL extends JPanel
   {
		 public void paint(Graphics g)
		 {
      	  Graphics2D g2 = (Graphics2D) g;

			  if(imageUpdateNeeded)
			  {
		  			// resize to the correct dimensions
					setSize(DRAW_PANEL_WIDTH,DRAW_PANEL_HEIGHT);
					gImage = (BufferedImage)createImage(DRAW_PANEL_WIDTH,DRAW_PANEL_HEIGHT);  // bufferedImage to work with
					gS = gImage.createGraphics();              // graphics interface of image

					Font curFont = gS.getFont();
					gS.setFont(new Font(curFont.getFontName(), curFont.getStyle(), 10));

					int xOffset = 150;
					int yOffset = 50;

					if(net != null)
					{
						NumberFormat nf = NumberFormat.getInstance();
						nf.setMaximumFractionDigits(4);

						int colorIndex = 0;
						int dataCounter = 0;

						for(int layer = 0, numLayers = net.getNumLayers(); layer < numLayers; ++layer)
						{
							System.out.println("layer: "+layer);
							int nodeOffsetX = xOffset+((layer+1)*(NODE_WIDTH));

							List nodes = net.getNodesAtLayer(layer);
							System.out.println("numNodes: "+nodes.size());
							List nextNodes = net.getNodesAtLayer(layer+1);

							for(int nodeIndex = 0, numNodes = nodes.size(); nodeIndex < numNodes; ++nodeIndex)
							{
								Color tmpColor;
								AdalineNode currentNode = (AdalineNode)nodes.get(nodeIndex);

								int arrowStartX = nodeOffsetX+(layer*ARROW_LENGTH);
								int arrowStartY = yOffset+(nodeIndex*(NODE_HEIGHT+NODE_SPACING)+ARROW_HEIGHT_OFFSET);
								int arrowEndX = nodeOffsetX+((layer+1)*(ARROW_LENGTH));

								// input layer
								if(layer == 0)
								{
									// draw input values
									gS.drawString(currentNode.getValue()+"INP->",
										xOffset/2+(layer*(NODE_WIDTH+NODE_SPACING)), yOffset+(nodeIndex*(NODE_HEIGHT+NODE_SPACING)));
								}


								gS.setColor(COLOR_ARRAY[colorIndex]);

								colorIndex++;
								if(colorIndex >= COLOR_ARRAY.length)
								{
									colorIndex = 0;
								}

								// draw node itself
								gS.fillOval(xOffset+(layer*(NODE_WIDTH+NODE_SPACING)), yOffset+(nodeIndex*(NODE_HEIGHT+NODE_SPACING)), NODE_WIDTH, NODE_HEIGHT);
								tmpColor = gS.getColor();
								gS.setColor(COLOR_ARRAY[colorIndex]);
								gS.drawString("("+currentNode.getNodeID()+")",
									xOffset+(layer*(NODE_WIDTH+NODE_SPACING))+NODE_WIDTH/2, yOffset+(nodeIndex*(NODE_HEIGHT+NODE_SPACING))+NODE_HEIGHT/2);
								gS.setColor(tmpColor);


								if(nextNodes != null)
								{
									for(int nextNodeIndex = 0, nextNumNodes = nextNodes.size();
										 nextNodeIndex < nextNumNodes; ++nextNodeIndex)
									{
										int arrowEndY = yOffset+(nextNodeIndex*(NODE_HEIGHT+NODE_SPACING))+ARROW_HEIGHT_OFFSET;

										// draw Weights to nodes, draw arrows
										gS.drawLine(arrowStartX, arrowStartY,
										            arrowEndX, arrowEndY);

										AdalineNode nextNode = (AdalineNode)nextNodes.get(nextNodeIndex);

										//if(layer == (numLayers-1))
										if(layer == layer)
										{
											//mike
											// don't know how u get your weights
											//Double weight = currentNode.To(nextNode);
											//Double weight = new Double(17.0);
											//double weight_d = (double) currentNode.getWeightToOutputNode(nextNodeIndex);
											Double weight = new Double((double) currentNode.getWeightToOutputNode(nextNodeIndex));

											System.out.println(currentNode.getNodeID()+":weight="+weight+":"+nextNodeIndex+"\n");

											int weightX = arrowStartX;
											int weightY = arrowStartY;

											if(weightY != arrowEndY)
											{

												if(arrowEndY > arrowStartY)
												{
													weightY = arrowStartY+(arrowEndY - arrowStartY)/2;
													weightX += (arrowEndX-arrowStartX)/2;
												}
												else
												{
													weightY = arrowStartY-((arrowStartY - arrowEndY)/2);
												}
											}

											gS.drawString("W("+nodeIndex+", "+nextNodeIndex+")= "+
															  nf.format(weight.doubleValue()),
															  weightX, weightY);

											gS.drawString("W("+currentNode.getNodeID()+", "+nextNode.getNodeID()+")= "+
															  nf.format(weight.doubleValue()),
															  DATA_TABLE_X_OFFSET, DATA_TABLE_Y_OFFSET+(dataCounter*DATA_TEXT_HEIGHT));
											dataCounter++;
										}
									}
								}

								//output layer
								if(layer == (numLayers-1))
								{
									
									gS.drawString("EXPECTED->"+currentNode.getTrainingPatternString(),
										xOffset*3/2+(layer*(NODE_WIDTH+NODE_SPACING)), 
										yOffset+(nodeIndex*(NODE_HEIGHT+NODE_SPACING)));

									//mike
									// don't know how you get your actual output values
									// draw actual output values
									gS.drawString("ACTUAL->"+nf.format(currentNode.getValue()),
										xOffset*3/2+(layer*(NODE_WIDTH+NODE_SPACING)), 
										yOffset*3/2+(nodeIndex*(NODE_HEIGHT+NODE_SPACING))+NODE_HEIGHT);
								}
							}
						}
				   }

				 imageUpdateNeeded = false;
			  }
			   g2.drawImage(gImage,0,0,DRAW_PANEL_WIDTH,DRAW_PANEL_HEIGHT,this);
		 }


		 public void update(Graphics g) {}
	}

   class MyActionListener implements ActionListener
   {
		 public void actionPerformed(ActionEvent e)
		 {
			 boolean updateData = false;
			 boolean newData = false;
			  Object object = e.getSource();

				// load net saved in a file
			  if (object == getNodesFromFileBTN)
			  {
					System.out.println("Get from File!");

					JFileChooser chooser = new JFileChooser(".");
					int returnVal = chooser.showOpenDialog(thisFrame);
					if(returnVal == JFileChooser.APPROVE_OPTION) {
						String filename = chooser.getSelectedFile().getName();
						System.out.println("You chose to open this file: " +
							  filename);

					 	if(net != null)
					 	{
							//net.deleteObservers();
						}

						//you don't support this yet.
						//net = AdalineNetwork.load(filename);
						testInputs = new ArrayList();
						testOutputs = new ArrayList();
						//net.addObserver((Observer)thisFrame);
						imageUpdateNeeded = true;
						drawPNL.repaint();
			  	  }
			  	  newData = true;
			  }
			  //generate test object
			  else if(object == generateBTN)
			  {
					net = new AdalineNetwork(2,2,1);
         			net.create(3);

						testInputs = new ArrayList();
						testOutputs = new ArrayList();

						//net.addObserver((Observer)thisFrame);

						newData = true;
			  }
			  //save to file
			  else if(object == saveBTN)
			  {
				  //Utility.saveToFile(net, "saveTest.net");
			  }
			  // train net
			  else if(object == trainBTN)
			  {
				  if(net != null && net.getNumLayers() > 1 && testInputs.size() > 0)
				  {
						for(int j =0;j< iterations; j++) {
							  for(int i = 0, numPatterns = testInputs.size(); i < numPatterns; ++i)
							  {
									ArrayList tp = new ArrayList();

									tp.add(new ArrayList((List)testInputs.get(i)));
									tp.add(new ArrayList((List)testOutputs.get(i)));
									net.setTrainingPattern(tp);

									net.trainNtimes(3000);
							  }

				   	}
				  }
			  }
			  // dump network as text
			  else if(object == textDumpBTN)
			  {
				  System.out.println("Neural Network\n"+net.toString()); 
			  }
			  
			  // add training set
			  else if(object == addTrainingSetBTN)
			  {
				  if(testInputs == null)
				  		testInputs = new ArrayList();
				  if(testOutputs == null)
						testOutputs = new ArrayList();

				  if(inputsTXF != null && numInputs > 0)
				  {
					  Float [] inputs = new Float[numInputs];
					  for(int i = 0; i<numInputs; ++i)
					  {
						  try
						  {
								inputs[i] = new Float(inputsTXF[i].getText());
						  }
						  catch(Exception ex)
						  {
							  inputs[i] = new Float(0.0);
							  ex.printStackTrace();
						  }
					  }

					  testInputs.add(Arrays.asList(inputs));
				  }

				  if(outputsTXF != null && numOutputs > 0)
				  {
					  Float [] outputs = new Float[numOutputs];
					  for(int i = 0; i<numOutputs; ++i)
					  {
						  try
						  {
								outputs[i] = new Float(outputsTXF[i].getText());
						  }
						  catch(Exception ex)
						  {
							  outputs[i] = new Float(0.0);
							  ex.printStackTrace();
						  }
					  }
					  testOutputs.add(Arrays.asList(outputs));
				  }
				  updateData = true;
			  }
			  // remove training set
			  else if(object == removeTrainingSetBTN)
			  {
				  int testIndex = dataList.getSelectedIndex();

				  if(testIndex >= 0)
				  {
					  testOutputs.remove(testIndex);
					  testInputs.remove(testIndex);
					  updateData = true;
				  }

			  }
			  // test a single inputs
			  // mike, you don't implement this, so all i can do is
			  // evaluate.
			  else if(object == testInputsBTN)
			  {
				  if(net != null && net.getNumLayers() > 1)
				  {
					  if(inputsTXF != null && numInputs > 0)
					  {
						  int testIndex = dataList.getSelectedIndex();

						  if(testIndex >= 0)
						  {
							  net.evaluate();
						  }
						}

				 }
			  }

			  // update GUI
			  if((updateData || newData) && net != null && net.getNumLayers() > 1)
			  {
				  List inputNodes = net.getNodesAtLayer(0);
				  List outputNodes = net.getNodesAtLayer(net.getNumLayers()-1);
				  numInputs = inputNodes.size();
				  numOutputs = outputNodes.size();

				  if(newData)
				  {
					  dataPNL.removeAll();

					  if(numInputs > 0)
					  {
						  inputsTXF =  new JTextField[numInputs];
						  for(int i = 0; i < numInputs; ++i)
						  {
							  AdalineNode node = (AdalineNode)inputNodes.get(i);
							  inputsTXF[i] = new JTextField();
							  inputsTXF[i].setText(""+node.getValue());
							  dataPNL.add(new JLabel("Input("+node.getNodeID()+"): "));
							  dataPNL.add(inputsTXF[i]);
						  }
					  }

					  if(numOutputs > 0)
					  {
						  outputsTXF =  new JTextField[numOutputs];
						  for(int i = 0; i < numOutputs; ++i)
						  {
							  AdalineNode node = (AdalineNode)outputNodes.get(i);
							  outputsTXF[i] = new JTextField();
							  outputsTXF[i].setText(""+node.getValue());
							  dataPNL.add(new JLabel("Target Output("+node.getNodeID()+"): "));
							  dataPNL.add(outputsTXF[i]);
						  }
					  }

				  	  dataPNL.add(dataBTNPNL);
				  }

				  String [] testLists = new String[testInputs.size()];

					for(int j = 0, numTrainingSet = testInputs.size(); j < numTrainingSet; ++j)
					{
						testLists[j] = "";

						Float inputArray[] = (Float [])((List)testInputs.get(j)).toArray();
						Float outputArray[] = (Float [])((List)testOutputs.get(j)).toArray();

						for(int k = 0; k < numInputs; ++k)
						{
							testLists[j]  = testLists[j] + " "+inputArray[k];
						}

						testLists[j] = testLists[j] + " : ";

						for(int k = 0; k < numOutputs; ++k)
						{
							testLists[j] = testLists[j]+" "+ outputArray[k];
						}
					}

				  dataList = new JList(testLists);
				  scrollPane.getViewport().setView(dataList);

				  scrollPane.setMinimumSize(new Dimension(SCROLL_PANEL_WIDTH, SCROLL_PANEL_HEIGHT));
				  scrollPane.setPreferredSize(new Dimension(SCROLL_PANEL_WIDTH, SCROLL_PANEL_HEIGHT));
				  scrollPane.setMaximumSize(new Dimension(SCROLL_PANEL_WIDTH, SCROLL_PANEL_HEIGHT));

				  if(testLists.length > 0)
				  {
					  	dataList.setSelectedIndex(0);
  				  }

 				  dataPNL.add(scrollPane);
				  dataPNL.setVisible(true);

				  dataPNL.getParent().repaint();
				  dataPNL.repaint();
				  thisFrame.validate();
				  updateData = false;
				  newData = false;

			  }


				imageUpdateNeeded = true;
				drawPNL.repaint();

		 }
	}

	public static void main(String args[])
	{
		 AdalineGUI n = new AdalineGUI();
	}
}
