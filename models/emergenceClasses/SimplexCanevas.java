/**
* Title:   moduleco.models.emergenceClasses.SimplexCaneva
* Reference : Axtell, Epstein, Young (2000)
* http://www.brookings.edu/es/dynamics/papers/classes/
* * Copyright:    Copyright (c)enst-bretagne
* @author denis.phan@enst-bretagne.fr
* * @version 1.4.1 april 2004  
*/

package models.emergenceClasses;

import java.awt.Polygon;
import java.awt.Color;
import java.awt.Graphics; //Polygon;

import modulecoFramework.modeleco.EWorld;
import modulecoFramework.modeleco.CAgent;
// import java.util.ArrayList; 
import java.util.Iterator;

// import modulecoGUI.grapheco.CBufferedCanvas;
// import modulecoGUI.grapheco.CAgentRepresentation;

public class SimplexCanevas extends modulecoGUI.grapheco.Canevas {

	protected EWorld world;
	public boolean tagTest;
	public boolean displayCentroid;
	protected static int msfgin = 30;
	int x1, y1, x2, y2, x3, y3;
	int xRange, yRange;
	Polygon basicSimplex;
	Polygon playLowZone;
	Polygon playMediumZone;
	Polygon playHighZone;
	protected int usedWidth, usedHeight;
	public SimplexCanevas() {
		super();
		//setSimplexZones(); //((World)world).wr.mixedNashEquilibriumTradeoff 0.3F
		displayCentroid = true;
		drawAble = true;
		// 1/3 =(double)XxPlayers / (double)XTotalPlayers; X : inter, intra x : low, medium, high  
		//System.out.println("SimplexCanevas.Constructor");
	}

	public void setCAgent(CAgent world) {
		this.world = (EWorld) world;
		//System.out.println("SimplexCanevas.setCAgent()");

	}

	protected void draw(Graphics g) {
		/*
			usedWidth = dimension.width ;
		usedHeight = dimension.height ;
			if(dimension.width >  dimension.height) usedWidth = dimension.height;
		   else usedHeight = dimension.width ;
		*/
		usedWidth = 396;
		usedHeight = 300;
		if (g != null) {

			g.setColor(Color.lightGray); //(Color.white);
			g.fill3DRect(0, 0, usedWidth, usedHeight + 60, true);
			g.setColor(Color.black);

			if (drawAble) {
				//System.out.println("SimplexCanevas.draw()drawAble = "+drawAble);
				drawSimplex(g);
				onUpdate(g);
			}
		}

	}

	public void drawSimplex(Graphics g) {
		//int imax = 2 ;
		displayCentroid = true; //DP
		String ts;

		x1 = usedWidth / 2;
		y1 = msfgin;
		x2 = msfgin;
		y2 = usedHeight - msfgin;
		x3 = usedWidth - msfgin;
		y3 = usedHeight - msfgin;
		basicSimplex = new Polygon();
		basicSimplex.addPoint(x1, y1 + 2);
		basicSimplex.addPoint(x2 + 2, y2 - 1);
		basicSimplex.addPoint(x3 - 2, y3 - 1);
		g.drawOval(x1 - 1, y1 - 1, 2, 2);
		g.drawString("S1: high", x1 - 25, y1 - 15);
		//g.drawString(simplexFeature.getAxis1Name(), x1, y1 - 15);
		g.drawOval(x2 - 1, y2 - 1, 2, 2);
		g.drawString("S2: medium", x2 - 25, y2 + 15);
		//g.drawString(simplexFeature.getAxis2Name(), x2, y2 + 15);
		g.drawOval(x3 - 2, y3 - 2, 2, 2);
		g.drawString("S3: low", x3 - 25, y3 + 15);
		//g.drawString(simplexFeature.getAxis3Name(), x3, y3 + 15);
		xRange = x3 - x2;
		yRange = y2 - y1;
		//System.out.println("SimplexCanevas.drawSimplex() displayCentroid = "+displayCentroid);
		if (displayCentroid) {
			int i = 1;

			playLowZone = new Polygon();
			playLowZone.addPoint(
				getXPosition(((World) world).wr.sf1, null) - 1,
				getYPosition(((World) world).wr.sf1, null));
			playLowZone.addPoint(
				getXPosition(((World) world).wr.mixedNashEquilibrium, null),
				getYPosition(((World) world).wr.mixedNashEquilibrium, null));
			playLowZone.addPoint(
				getXPosition(((World) world).wr.sf3, null) + 2,
				getYPosition(((World) world).wr.sf3, null));
			playLowZone.addPoint(x1, y1 - 2);
			playMediumZone = new Polygon();
			playMediumZone.addPoint(
				getXPosition(((World) world).wr.sf1, null) - 1,
				getYPosition(((World) world).wr.sf1, null));
			playMediumZone.addPoint(
				getXPosition(((World) world).wr.mixedNashEquilibrium, null),
				getYPosition(((World) world).wr.mixedNashEquilibrium, null));
			playMediumZone.addPoint(
				getXPosition(((World) world).wr.sf2, null),
				getYPosition(((World) world).wr.sf2, null) + 2);
			playMediumZone.addPoint(x2 - 3, y2 + 2);
			playHighZone = new Polygon();
			playHighZone.addPoint(
				getXPosition(((World) world).wr.sf3, null) + 2,
				getYPosition(((World) world).wr.sf3, null));
			playHighZone.addPoint(
				getXPosition(((World) world).wr.mixedNashEquilibrium, null),
				getYPosition(((World) world).wr.mixedNashEquilibrium, null));
			playHighZone.addPoint(
				getXPosition(((World) world).wr.sf2, null),
				getYPosition(((World) world).wr.sf2, null) + 2);
			playHighZone.addPoint(x3 + 3, y3 + 2);
		}
		//System.out.println("SimplexCanevas : " + ((World) world).getTag());
		tagTest = (((World) world).getTag()).equalsIgnoreCase("no tag");
		if (tagTest) {
			g.drawString(
				"Agent's beliefs in the simplex",
				x1 - 80,
				usedWidth - 80);
		} else {
			//System.out.println("SimplexCanevas : " + name);
			if (name.equalsIgnoreCase("leftRepresentation"))
				ts = "WITHIN - tag";
			else
				ts = "BETWEEN - tag";
			g.drawString(
				"Agent's " + ts + " beliefs in the simplex",
				x1 - 110,
				usedWidth - 80);
		}

	}

	public int getXPosition(
		SimplexFeature sf,
		String s) { /**
														* XPosition = msfgin + xRange*(rateOfS3 + rateOfS1/2) 
														*/
		return (int)
			((double) xRange
				* ((double) sf.getAxisLHValue()
					+ ((double) sf.getAxisHMValue()) / 2D))
			+ x2;
	}

	public int getYPosition(
		SimplexFeature sf,
		String s) { /**
														* YPosition = msfgin + yRange*(1 - rateOfS1) 
														*/
		return (int) ((double) yRange * (1.0D - (double) sf.getAxisHMValue()))
			+ y1;
	}

	public void onUpdate(Graphics g) {
		//System.out.println("SimplexCanevas.onUpdate()  displayCentroid = "+displayCentroid);
		if (displayCentroid) {
			g.setColor(Color.blue);
			g.fillPolygon(playLowZone);
			g.setColor(Color.green);
			g.fillPolygon(playMediumZone);
			g.setColor(Color.red);
			g.fillPolygon(playHighZone);
		} else {
			g.setColor(Color.white);
			g.fillPolygon(basicSimplex);
		}
		if (tagTest)
			updateAgents(g, 0);
		else {
			if (name.equalsIgnoreCase("leftRepresentation"))
				updateAgents(g, 0);
			else
				updateAgents(g, 1);
		}
	}

	public void updateAgents(Graphics g, int j) { 
		int h = 0;
		for (Iterator i = ((World) world).simplexFeatureSet[j].iterator();
			i.hasNext();
			) {
			SimplexFeature sf = (SimplexFeature) i.next();
			if (((Agent) ((EWorld) world).get(h)).tag)
				g.setColor(Color.gray);//brighter()
			else
				g.setColor(Color. black);
			g.fillRect(getXPosition(sf, ""), getYPosition(sf, ""), 4, 4);
			h++;
		}
	}
}