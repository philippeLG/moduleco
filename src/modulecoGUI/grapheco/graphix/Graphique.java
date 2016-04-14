 /* Source File Name:   Graphique.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002
*/
//temporal scheduling (deboggage)
//Graphique()> constructor
//addTrace() (sometimes 2 or 3 times ?)
//DisplayTrace()
//setCAgent()
// init + each time :
//draw()
//drawTraces()
// method UpdateImage() > in comment => not active ?

   package modulecoGUI.grapheco.graphix;

   import java.awt.CheckboxMenuItem;
   import java.awt.Graphics;
   import java.awt.Color;
   import java.awt.FontMetrics;

   import java.awt.event.ItemListener;
   import java.awt.event.ItemEvent;

// import java.util.Vector;
   import java.util.Iterator;
   import java.util.ArrayList;
   import java.util.Hashtable;

   import modulecoFramework.modeleco.EWorld;
   import modulecoFramework.modeleco.CAgent;

   import modulecoGUI.grapheco.CBufferedCanvas;
   import modulecoGUI.grapheco.CAgentRepresentation;
   import modulecoGUI.grapheco.EPanel;
   //import modulecoGUI.grapheco.CentralControl;

   import modulecoGUI.grapheco.statManager.StatManager;

/**
 * This class is an EWorld representation. It contains trace, that can be
 * drawed or not, depending the choice of the user.
 */
   public class Graphique extends CBufferedCanvas implements CAgentRepresentation, ItemListener
   {
    /**
     *The world which is represented
     */
      public EWorld eWorld;
    /**
     *The statmanager of the eworld represented
     */
      public EPanel epanel;
      protected StatManager statManager;
   
    /**
     * The list of all the traces
     */
      protected ArrayList arrayListTraces;
    /**
     * A hashtable that links trace with CheckBoxMenuItems to allow the user
     * choose to draw or not the trace.
     */
      protected Hashtable hashCheckBoxItems;
   
    /**
     *The number of iterations between two refreshes;
     */
      protected int refresh = 1; 
   
    /**
     * The number of trace to be represented by the Graphique.
     */
      protected int numTraces;
	/**
		 * The name of this CAgentRepresentationContainer
		 */
		public String name;
   
   
      protected int yMin = 0;
      protected int yMax = 100;
      protected int yMaxI = 100;
      protected int yInter;
      protected int xMin = 0;
      protected int xBase = 0;
      protected int xMaxI = 5;
      protected int xMax = 5;
      protected int xInter;
      protected int xInterI;
   
      protected int northInset, southInset, westInset, eastInset;
   
      //protected CentralControl centralControl;
    /**
     * Create an empty (with no trace) Graphique object.
     */
      public Graphique()
      
      {
         arrayListTraces = new ArrayList();
         hashCheckBoxItems = new Hashtable();
         //System.out.println("graphix.Graphique.constructeur");
      }
      public Graphique(CAgentRepresentation cAgentRepresentation)
      {
         this();
      //System.out.println("Graphique() "+cAgentRepresentation);
      	//this.epanel = epanel;
      }
   
    /**
     * Allow adding traces to the Graphique object.
     * @param trace The trace to be added.
     * @param selected Decide if the trace is drawed (true) or not (false) at
     * the Graphique object creation
     */
      public void addTrace(Trace trace, boolean selected)
      {
         //System.out.println("graphix.Graphique. addTrace()");
         arrayListTraces.add(trace);
      
         CheckboxMenuItem checkBoxMenuItem = new CheckboxMenuItem(trace.getName());
      
         if (selected)
            checkBoxMenuItem.setState(true);
      
         checkBoxMenuItem.addItemListener(this);
         hashCheckBoxItems.put(trace, checkBoxMenuItem);
         popupMenu.add(checkBoxMenuItem);
      }
   
      public void setCAgent(CAgent cAgent)
      {
         eWorld = (EWorld) cAgent;
         DisplayTrace();
         //A VERIFIER
         statManager = eWorld.getStatManager();
      //System.out.println("graphique.setCAgent()");
      
         this.setGridSize(eWorld.getAgentSetSize());
         drawAble =true; // DP 20/08/2002
         //System.out.println("graphix.Graphique.setCAgent()- drawAble = "+drawAble); //test DP 20/08/2002
      }
   /*
      public void resetImage() 
      {
         //System.out.println("graphix.Graphique.resetImage()");
         yMax = yMaxI;
         xMax = xMaxI;
      
         setBackground(Color.lightGray);
         repaint();
         setVisible(true);
      }
   */
    /**
     * Draws the traces on the Graphique.
     */
      public void drawTraces(Graphics g) 
      {
         //System.out.println("graphix.Graphique.drawTraces");
         for (Iterator i = arrayListTraces.iterator() ; i.hasNext();) 
         {
            Trace trace = ((Trace)i.next());
            if (((CheckboxMenuItem)hashCheckBoxItems.get(trace)).getState())
            {
               int point = 1;
               int numberOfPoints = statManager.getIteration() + 1;
            
               if (point <= numberOfPoints) 
               {		
                  g.setColor(trace.getColor());
               
                  while ((point < numberOfPoints)) 
                  {
                     if (trace.getType() == 0)
                     {
                        g.drawLine(
                                  xScreen(point - 1),
                                  yScreen((int)(statManager.get(trace.getVarName(), point - 1))),
                                  xScreen(point),
                                  yScreen((int)(statManager.get(trace.getVarName(), point)))
                                  );
                     }
                     if (trace.getType() == 1)
                     {
                        g.fillOval(
                                  xScreen(point)-1,
                                  yScreen((int)(statManager.get(trace.getVarName(), point)))-1,
                                  3,
                                  3
                                  );
                     }
                     point++;
                  }
               }
            }
         }
      }
   
      public void draw(Graphics g) 
      {  
         //System.out.println("graphix.Graphique.draw");
      // Some initialisations
      
         FontMetrics fm = getFontMetrics(g.getFont());
         southInset = fm.getHeight() + 2;
         eastInset = fm.stringWidth("" + xMax);
         northInset = fm.getHeight() + 2;
      
         g.setColor(Color.lightGray.brighter());
         g.fill3DRect(0, 0, dimension.width, dimension.height, true);
         if(drawAble){
            g.setColor(Color.black);
         
         // At first, see if the trace is in out bounds
         
         // At first, see if the trace is in out bounds
         
         // java.lang.NullPointerException for statManager is SetCAgent not previously invoqued
            try{//DP 20/08/2002
            
               int iteration = statManager.getIteration(); // just a temp variable
            
               if (iteration > xMax)
                  xMax = iteration;
            
               for (Iterator i = arrayListTraces.iterator(); i.hasNext();)
               {
                  Trace trace = ((Trace)i.next());
                  if (((CheckboxMenuItem)hashCheckBoxItems.get(trace)).getState())
                  {
                     double value = statManager.get(trace.getVarName());
                  
                     if (value > yMax)
                        yMax = (int) value;
                  }
               }
            
            // And calculation of the step between two numbers showed
            
               xInter = xMax / 5;
            
               yInter = yMax / 5;
            
            // Then we draw the numbers, to show the axis scales
            
               for (int step = yMin; step <= yMax; step += yInter) 
               {
               //Calculation of west inset
                  if (fm.stringWidth("" + step) > westInset)
                     westInset = fm.stringWidth("" + step) + 10;
               //Draw the number (the x scale)
                  g.drawString("" + step, 5, yScreen(step) + 3);
               }
            
               for(int step = yMin; step <= yMax; step += yInter)
               // The litle lines that represent the steps 
                  g.drawLine(westInset, yScreen(step), westInset + 3, yScreen(step));
            
               for(int step = xMin; step <= xMax; step += xInter)
               //Draw the number (the y scale)
                  g.drawString("" + step, xScreen(step) - fm.stringWidth("" + step)/2, dimension.height - 5);
            
               for (int step = xMin; step <= xMax; step += xInter) 
               // The litle lines that represent the steps 
                  g.drawLine(xScreen(step), dimension.height - southInset, xScreen(step), dimension.height - southInset - 3);
            
            // Finaly, we draw the two axis
            
            // y axis
               g.drawLine(westInset, northInset, westInset, dimension.height - southInset); 
            
            // x axis
               g.drawLine(westInset ,dimension.height - southInset, dimension.width - eastInset, dimension.height - southInset);
            
            // And last but not least, we draw the traces
               drawTraces(g);
            }
               catch (Exception e){//DP 20/08/2002
               
                  System.out.println("grapheco.graphix.Graphique.draw(): statManager > "+e.toString());
               }
         }
         else {
            //System.out.println("graphix.Graphique.draw()drawAble = "+drawAble); //test DP 20/08/2002
         }
      }
   
    /**
     * It translate the point x value into a reel x value (in pixels)
     * @return The reel x value (in pixels)
     * @param x The point x value
     */
      private int xScreen(int x) 
      {
         return (int)(westInset + (x - xMin) * (dimension.width - (westInset + eastInset)) / (xMax - xMin));
      }
   
    /**
     * It translate the point y value into a reel y value (in pixels)
     * @return The reel y value (in pixels)
     * @param y The point y value
     */
      private int yScreen(int y) 
      {
         return (int)((dimension.height - southInset) - (y - yMin) * (dimension.height- (northInset + southInset)) / (yMax - yMin));
      }
   /*
   * Method associated with the interface CAgentRepresentation ; 
   * invoke the parent method CBufferedCanvas.updateImage()
   */
   /*
      public void updateImage()
      {
         //System.out.println("updateImage step5r : Graphix.graphique from EPanel");
      
         if (statManager.getIteration() % refresh == 0)
            super.updateImage();
      }
   */
      public void setGridSize(int gridSize) 
      { 
      }
   
      public void itemStateChanged(ItemEvent e)
      {
         this.resetImage();
      }
      public void DisplayTrace() {
         //System.out.println("grapheco.graphix.Graphique.DisplayTrace");
      }
	public String getName() {
		if (name != null)
			return name;
		else
			return null;
	}

	public void setName(String s) {
		this.name = s;
	}
   }
