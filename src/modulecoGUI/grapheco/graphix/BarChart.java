
/* Source File Name:   modulecoGUI.grapheco..graphix.BarChart.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author camille.monge@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

package modulecoGUI.grapheco.graphix;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;

import java.util.Vector;
// import java.util.Iterator; // depuis JDK 1.2.
// import java.io.PrintStream;

import modulecoFramework.modeleco.EWorld;
import modulecoFramework.modeleco.CAgent;
import modulecoGUI.grapheco.CBufferedCanvas;
import modulecoGUI.grapheco.CAgentRepresentation;
import modulecoGUI.grapheco.graphix.Graphique;
import modulecoGUI.grapheco.statManager.StatManager;
// import modulecoGUI.grapheco.CentralControl;

public class BarChart extends CBufferedCanvas implements CAgentRepresentation {
	/**
	  *The world which is represented
	  */
	public EWorld eWorld;
	/**
	 *The statmanager of the eworld represented
	 */
	protected StatManager statManager;
	/**
	*The number of iterations between two refreshes;
	*/
	protected int refresh = 1;
	protected int size;
	/**
		 * The name of this CAgentRepresentationContainer
		 */
	public String name;

	protected int Bar[];
	protected int numBar = 20;
	protected Vector value;
	protected Graphique oldRepresentation;
	protected int max = 0;
	protected int start, end;
	protected int bande;
	protected int iter = 0;
	protected int yMin = 0;
	protected int yMax = 100;
	protected int yMaxI = 100;
	protected int yInter = 25;
	protected int xMin = 0;
	protected int xBase = 0;
	protected int yBase = 0;
	protected int xMaxI = 100;
	protected int xMax = 100;
	protected int xInter = 10;
	protected int xInterI = 10;
	protected int x1, y1, x2, y2;
	protected boolean cleanGraph = false;
	protected Color colorBar;

	//protected CentralControl centralControl;

	public BarChart() {
		this(11, 10);

	}

	public BarChart(int numbar, int e) {
		super();
		//System.out.println("BarChart.Constructor()");
		int s = 0;
		//(int)Math.round(eWorld.getLength()*(numBar+1)/numBar; //?
		value = new Vector();
		//
		init(numBar, s, e);
		colorBar = Color.blue;
		//

	}

	/*
	* BarChart.init() invoque by BarChart.Constructor
	*/
	public void init(int num, int s, int e) {
		numBar = num;
		Bar = new int[numBar];
		start = s;
		end = e;
		//xMaxI=(int)Math.round(e*numBar/(numBar+1));
		//xMax=(int)Math.round(e*numBar/(numBar+1));
		xMaxI = e;
		xMax = e;
		bande = (int) Math.round((end - start) / numBar);
		xInterI = (int) Math.ceil((end - start) / numBar);
		xInter = (int) Math.ceil((end - start) / numBar);

		//DEBUGGAGE (Math.ceil() SHOULD RETURN THE CLOSEST ENTIER SUP BUT IT DIDN'T )
		if (bande == 0)
			bande = 1;
		if (xInter == 0)
			xInter = 1;
		if (xInterI == 0)
			xInterI = 1;

		resetBarChart();
		//System.out.println("BarChart.init()");
		//drawBarChart();
	}

	public void setCAgent(CAgent cAgent) {
		//System.out.println("BarChart.setCAgent");
		eWorld = (EWorld) cAgent;
		//A VERIFIER
		statManager = eWorld.getStatManager();
		this.setGridSize(eWorld.getAgentSetSize());
		// updateImage() is required here in order to display BarChart before running
		updateImage();
	}

	public void setGridSize(int gridSize) { // world just beeing created
		size = (int) Math.sqrt(gridSize);
	}

	public void resetBarChart() {
		//System.out.println("BarChart.resetBarChart()");
		for (int i = 0; i < numBar; i++)
			Bar[i] = 0;
		yMax = yMaxI;
		xMax = xMaxI;
		xInter = xInterI;
		setBackground(Color.lightGray);
		cleanGraph = true;
		repaint();
		setVisible(true);
	}

	public void buildBarChart() {
		//System.out.println("BarChart.buildBarChart()");
		//REAJUSTEMENT DU VECTEUR PAR RAPPORT A 100
		int max = 100;
		int n = value.size();
		for (int i = 0; i < n; i++)
			if (((Integer) (value.elementAt(i))).intValue() > max) {
				max = ((Integer) value.elementAt(i)).intValue();
				System.out.println("max = " + max);
			}
		Vector val = (Vector) value.clone();
		value.clear();
		for (int i = 0; i < n; i++) {
			value.addElement(
				new Integer(
					100 * ((Integer) val.elementAt(i)).intValue() / max));
			//System.out.println(value.elementAt(i));
		}

		//int seuil=0;
		for (int i = 0; i < numBar; i++)
			Bar[i] = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < numBar; j++) {
				if (((((Integer) value.elementAt(i)).intValue()) >= j * bande)
					&& ((((Integer) value.elementAt(i)).intValue())
						< (j + 1) * bande))
					Bar[j]++;
			}
		}
	}

	public void setColorBar(Color e) {

		colorBar = e;
	}
	public void drawBarChart(Graphics g) {
		//System.out.println("BarChart.drawBarChart()");
		// for(int i=0;i<numBar;i++){
		// //System.out.println("Bar["+i + "] = "+Bar[i]);
		// //System.out.println(""+(dimension.height-y2)*Bar[i]/100);
		// }

		for (int i = 0; i < numBar; i++) {
			//g.setColor(new Color(210-bande*2*i,210-bande*2*i,210-bande*2*i));
			g.setColor(colorBar); // bar color
			g.fillRect(
				xScreen(bande * i) + 1,
				yScreen(0) - (dimension.height - y2) * Bar[i] / yMax,
				(dimension.width - x1 - x2) * bande / xMax,
				(dimension.height - y2) * Bar[i] / yMax);
			g.setColor(Color.black);
			g.drawRect(
				xScreen(bande * i) + 1,
				yScreen(0) - (dimension.height - y2) * Bar[i] / yMax,
				(dimension.width - x1 - x2) * bande / xMax,
				(dimension.height - y2) * Bar[i] / yMax);

		}
		// buildBarChart();
	}

	// public void drawFirstTime(Graphics g)
	// {
	// draw(g);
	// }
	// 
	public void draw(Graphics g) {
		//System.out.println("BarChart.draw()");
		for (int i = 0; i < numBar; i++) {
			if (Bar[i] > max)
				max = Bar[i];
		}
		yMaxI = max + 1;
		yMax = max + 1;
		yInter = (int) Math.ceil(yMax / 10);
		if (yInter == 0)
			yInter = 1;

		FontMetrics fm = getFontMetrics(g.getFont());
		x1 = 0;
		y2 = fm.getHeight();
		x2 = 50;
		y1 = 10;
		int i = yMin;

		g.setColor(Color.lightGray.brighter()); // background Color
		g.fill3DRect(0, 0, dimension.width, dimension.height, true);
		g.setColor(Color.black);

		if ((yMin - yBase) % yInter == 0)
			i = yMin;
		else
			i = ((yMin - yBase) / yInter + 1) * yInter + yBase;

		while (i <= yMax) {
			if (fm.stringWidth("" + i) > x1)
				x1 = fm.stringWidth("" + i);
			g.drawString("" + i, 5, yScreen(i) + 3);
			i += yInter;
		}
		x1 += 10;
		//i = yMin;
		if ((yMin - yBase) % yInter == 0)
			i = yMin;
		else
			i = ((yMin - yBase) / yInter + 1) * yInter + yBase;
		while (i <= yMax) {
			g.drawLine(x1, yScreen(i), x1 + 3, yScreen(i));
			i += yInter;
		}
		//==========================

		if ((xMin - xBase) % xInter == 0)
			i = xMin;
		else
			i = ((xMin - xBase) / xInter + 1) * xInter + xBase;
		while (i <= xMax) {
			g.drawString("" + i, xScreen(i) + fm.stringWidth("" + i) / 2
			/*+(dimension.width-x1-x2)*bande/(2*xMax)*/
			, dimension.height - 5);
			i += xInter;
		}

		if ((xMin - xBase) % xInter == 0)
			i = xMin;
		else
			i = ((xMin - xBase) / xInter + 1) * xInter + xBase;
		while (i <= xMax) {
			g.drawLine(
				xScreen(i),
				dimension.height - y2,
				xScreen(i),
				dimension.height - y2 - 3);
			i += xInter;
		}

		g.drawLine(x1, y1, x1, dimension.height - y2);
		g.drawLine(
			x1,
			dimension.height - y2,
			dimension.width - x2,
			dimension.height - y2);
		drawBarChart(g);
	}

	public void setHauteur(int i) {

		max = i;
	}
	public void setNumBar(int i) {

		numBar = i;
	}

	private int xScreen(int x) {
		return x1 + (x - xMin) * (dimension.width - (x1 + x2)) / (xMax - xMin);
	}

	private int yScreen(int y) {
		return (dimension.height - y2)
			- (y - yMin) * (dimension.height - (y1 + y2)) / (yMax - yMin);
	}

	public CAgentRepresentation setVectorBarChart(Vector value) {
		//System.out.println("BarChart.setVectorBarChart()");
		this.value = value;
		return (this);
	}

	public CAgentRepresentation addElement(int elt) {
		//System.out.println("BarChart.addElement()");
		int newElt = elt;
		if (elt > max) {
			max = elt;
			value.addElement(new Integer(100));
			resetBarChart();
			buildBarChart();

		} else {
			newElt = 100 * elt / max;
			value.addElement(new Integer(newElt));
			// for (int j=0;j<numBar;j++) 
			// if ((newElt>=j*bande)&&(newElt<(j+1)*bande)) Bar[j]++;		
		}
		return this;
	}

	public void updateImage() {
		//System.out.println("updateImage step5r : BarChart (from XRepresentation)");

		// if(statManager.getIteration() % refresh == 0);
		value = getValue();
		buildBarChart();
		super.updateImage();
	}

	/* Inutile : activé au niveau de CBUFFERED CANEVAS
	   public void setCentralControl(CentralControl centralControl)
	   {
	      this.centralControl = centralControl;
	      //System.out.println("modulecoGUI.grapheco.BarChart.setCentralControl()");
	   }
		*/
	public Vector getValue() {
		return null;
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