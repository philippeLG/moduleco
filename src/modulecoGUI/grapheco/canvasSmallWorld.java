/* Source File Name:   SmallWorld.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author damien.iggiotti@enst-bretagne.fr, corrected Denis.Phan@enst-bretagne.fr
 * created june, 23, 2002 
 * @version 1.2  august,5, 2002
 */

package modulecoGUI.grapheco;

import java.awt.Color;
// import java.awt.Canvas;
// import java.awt.Dimension;
// import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Arc2D;
// import java.awt.geom.Ellipse2D.Float;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.BasicStroke;

import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.Cursor; // DP 17/10/2003 Egalement modifié : CBufferedCanvas

import java.awt.MenuItem;

import java.util.ArrayList;
import java.util.Iterator;

import modulecoFramework.modeleco.EWorld;
import modulecoFramework.modeleco.CAgent;
import modulecoFramework.modeleco.EAgent;

import modulecoFramework.medium.Medium;
import modulecoFramework.medium.NeighbourMedium;

/*********************************************************************************************************/

public class canvasSmallWorld
	extends CBufferedCanvas
	implements CAgentRepresentation {
	/**
	 * The name of this CAgentRepresentationContainer
	 */
	public String name;

	protected EWorld eWorld;

	protected char state[][];

	protected MenuItem menuItemEditAgent, menuItemEditAgentLinks;
	// DP 17/10/2003
	protected MenuItem menuItemEditWorld;

	protected Color c[][];
	protected Color colorBack;
	protected final Color fondcanvas = Color.lightGray;

	protected int size;
	protected int radius;
	protected int littleradius;
	protected int diameter;
	protected int littlediameter;
	protected int OldAgentID, selectedAgentID; // DP 17/10/2003 

	static double margin = 16.0;
	static double TWO_PI = Math.PI * 2;

	protected Shape agentCircle, linkArc1, linkArc, line;

	protected BasicStroke widePen, narrowPen;
	protected Cursor canvasCrosshairCursor, canvasHandCursor;

	/*********************************************************************************************************/

	public canvasSmallWorld() {

		super();
		setColors();
		setBackground(fondcanvas);

		menuItemEditAgent = new MenuItem("Edit Agent");
		menuItemEditAgentLinks = new MenuItem("Edit AgentLinks");
		// DP 17/10/2003
		menuItemEditWorld = new MenuItem("Edit World");
		menuItemEditAgent.addActionListener(this);
		menuItemEditAgentLinks.addActionListener(this); // DP 17/10/2003
		menuItemEditWorld.addActionListener(this);

		colorBack = Color.lightGray;
		this.setBackground(colorBack);

		agentCircle = new Ellipse2D.Double();
		linkArc = new Arc2D.Double(0);
		linkArc1 = new Arc2D.Double(0);
		line = new Line2D.Double();
		widePen =
			new BasicStroke(
				2.0f,
				BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_ROUND);
		narrowPen =
			new BasicStroke(
				1.0f,
				BasicStroke.CAP_SQUARE,
				BasicStroke.JOIN_ROUND);
		//		 Static Access to static Field
		canvasCrosshairCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		canvasHandCursor = new Cursor(Cursor.HAND_CURSOR);
		this.setCursor(canvasCrosshairCursor);
		//System.out.println("canvasSmallWorld_Enabled");	
	}

	/*********************************************************************************************************/

	public void setCAgent(CAgent cAgent) {
		this.eWorld = (EWorld) cAgent;
		size =
			(new java.lang.Double(Math.sqrt(eWorld.getAgentSetSize()))).intValue();

		state = new char[size * size][2];
		for (int i = 0; i < size; i++) {
			state[i][0] =
				(((Boolean) ((CAgent) eWorld.get(i)).getState()).booleanValue()
					? '\000'
					: '\001');
			state[i][1] = state[i][0];
		}
	}

	/*********************************************************************************************************/

	protected void setColors() {
		c = new Color[2][2];
		c[0][0] = Color.blue.brighter().brighter().brighter();
		c[1][1] = Color.red.darker();
		c[1][0] = Color.yellow;
		c[0][1] = Color.green;
	}

	/*********************************************************************************************************/

	protected void drawFirstTime(Graphics g) {
		draw(g);
	}

	/*********************************************************************************************************/

	public void draw(Graphics g) {
		if (g != null) {
			Graphics2D g2D = (Graphics2D) g;
			Medium[] agentMedium;
			ArrayList neighbours;
			EAgent eagt;

			double gHeight = (new Integer(getHeight())).doubleValue();
			double gWidth = (new Integer(getWidth())).doubleValue();
			double littleradius = 22 - 2 * size;
			double littlediameter = 2 * littleradius;
			double diameter =
				Math.min(gHeight, gWidth) - margin * 2 - littlediameter;
			double radius = diameter / 2;
			double beta, l1, l2;
			int N = size * size;
			int indicateur, indicateurMax, k;
			double w2, h2;

			/******************************************************************/

			g2D.clearRect(0, 0, getWidth(), getHeight());
			g2D.translate(gWidth / 2, gHeight / 2);
			g2D.setColor(Color.black.darker().darker());
			g2D.setStroke(widePen);

			/******************************************************************/

			// dessin des liens :

			/******************************************************************/

			((Arc2D.Double) linkArc1).setFrame(
				-radius,
				-radius,
				diameter,
				diameter);
			double x1 = radius;
			double y1 = 0;
			double x2 =
				radius * Math.cos(TWO_PI / (new Integer(N)).doubleValue());
			double y2 =
				radius * Math.sin(-TWO_PI / (new Integer(N)).doubleValue());
			((Arc2D.Double) linkArc1).setAngles(x1, y1, x2, y2);

			for (int i = 0; i < N; i++) {
				agentMedium = ((CAgent) eWorld.get(i)).getMediums();
				neighbours = ((NeighbourMedium) agentMedium[0]).getNeighbours();
				// k=0; test arcs
				for (Iterator j = neighbours.iterator(); j.hasNext();) {
					eagt = ((EAgent) j.next());
					k = eagt.getAgentID();
					//test arcs
					/*
					if ((i ==0 && k==0 )||(i == 0 && k==1 )){
					   if (i ==0 && k==0 ) k=1;
					   if (i ==0 && k==1 ) k=N-1;
					}
					else
					   k=i; // pas de liens
							*/
					indicateur = (k - i + N) % N;
					//System.out.println("agent :"+i+" ind = "+indicateur);
					indicateurMax = N / 4;

					if (indicateur <= indicateurMax) {
						beta =
							(new Integer(indicateur)).doubleValue()
								* Math.PI
								/ (new Integer(N)).doubleValue();
					} else if (indicateur >= N - indicateurMax) {
						beta =
							- (new Integer(N - indicateur)).doubleValue()
								* Math.PI
								/ (new Integer(N)).doubleValue();
					} else {
						beta = 0;
					}

					l1 = Math.abs(radius * Math.cos(beta));
					l2 = Math.abs(radius * Math.sin(beta));

					// dessine les arcs de voisinage pour indMax > ind > (N -indMax)
					// ( si ind <> 1 ou ind <>N-1 ?)
					((Arc2D.Double) linkArc).setArcByCenter(
						l1,
						0,
						l2,
						90,
						180,
						0);

					if ((indicateur <= indicateurMax)
						|| (indicateur >= N - indicateurMax)) {

						if (indicateur == 1) {
							//System.out.println("indic Interne = 1");
							g2D.rotate(TWO_PI / (new Integer(N)).doubleValue());
							g2D.draw(linkArc1);
							g2D.rotate(
								-TWO_PI / (new Integer(N)).doubleValue());
						} else if (indicateur == N - 1) {
							//System.out.println("indic =N- 1");
							g2D.draw(linkArc1);
						} else {
							g2D.rotate(beta);
							g2D.draw(linkArc);
							// dessine les arcs de taile sup à 1
							g2D.rotate(-beta);
						}
					} else {
						//w2 = radius * Math.cos((i-k)*TWO_PI/(new Integer(N)).doubleValue());
						w2 =
							radius
								* Math.cos(
									indicateur
										* TWO_PI
										/ (new Integer(N)).doubleValue());
						//h2 =(i-k) / Math.abs(i-k)* radius * Math.sin((i-k)*TWO_PI/(new Integer(N)).doubleValue());
						h2 =
							radius
								* Math.sin(
									indicateur
										* TWO_PI
										/ (new Integer(N)).doubleValue());
						((Line2D.Double) line).setLine(radius, 0, w2, h2);
						g2D.draw(line);
					}
					/*   
					  w2 = radius * Math.cos((i-k)*TWO_PI/(new Integer(N)).doubleValue());
					  h2 =(i-k) / Math.abs(i-k)* radius * Math.sin((i-k)*TWO_PI/(new Integer(N)).doubleValue());
					*/
				}
				g2D.rotate(TWO_PI / (new Integer(N)).doubleValue());
			}

			/******************************************************************/

			// dessin des agents

			/******************************************************************/

			((Ellipse2D.Double) agentCircle).setFrameFromCenter(
				radius,
				0,
				radius + littleradius,
				littleradius);

			for (int i = 0; i < N; i++) {
				DrawAgent(i, g2D);
			}

			/******************************************************************/

			// repositionnement dans le coin inferieur gauche de l'ecran 
			// pour re-initialiser l'affichage et ne pas creer de decalage

			/******************************************************************/

			g2D.translate(-gWidth / 2, -gHeight / 2);

		}
	}

	public void DrawAgent(int agentID, Graphics2D g2D) {
		int i = agentID;
		state[i][0] = state[i][1];
		state[i][1] =
			(((Boolean) ((CAgent) eWorld.get(i)).getState()).booleanValue()
				? '\000'
				: '\001');
		g2D.setColor(c[state[i][0]][state[i][1]]);

		g2D.draw(agentCircle);
		g2D.fill(agentCircle);
		g2D.rotate(TWO_PI / (new Integer(size * size)).doubleValue());

	}

	/*********************************************************************************************************/

	public void mouseClicked(MouseEvent e) {
		//System.out.println("mouseClicked !");
		// caracteristiques de la zone d'affichage

		double littleradius = 22 - 2 * size;
		double diameter =
			Math.min(dimension.width, dimension.height)
				- margin * 2
				- littleradius * 2;
		int N = size * size;
		int compteur = 0;
		int agentID;

		// changement d'origine

		e.translatePoint(-dimension.width / 2, -dimension.height / 2);

		// coordonnees cartesiennes

		int abscisse = e.getX();
		int ordonnee = -e.getY();

		// coordonnees polaires

		double rayon = Math.sqrt(Math.pow(abscisse, 2) + Math.pow(ordonnee, 2));
		double alpha = Math.atan2(ordonnee, abscisse);
		double alphazero = 2 * littleradius / diameter;

		// agent ID

		while (Math.abs(alpha) / alpha * alpha
			- compteur * TWO_PI / (new Integer(N)).doubleValue()
			>= Math.PI / (new Integer(N)).doubleValue()) {
			compteur++;
		}
		if (alpha <= Math.PI / (new Integer(N)).doubleValue()) {
			agentID = compteur;
		} else {
			agentID = N - compteur;
		}

		if ((e.getModifiers() & InputEvent.BUTTON3_MASK)
			== InputEvent.BUTTON3_MASK) {
			if ((rayon > diameter / 2 - littleradius)
				&& (rayon < diameter / 2 + littleradius)
				&& (alpha
					<= Math.abs(alpha)
						/ alpha
						* compteur
						* TWO_PI
						/ (new Integer(N)).doubleValue()
						+ alphazero)
				&& (alpha
					>= Math.abs(alpha)
						/ alpha
						* compteur
						* TWO_PI
						/ (new Integer(N)).doubleValue()
						- alphazero)) {
				//System.out.println("EditAgent : "+ agentID);
				selectedAgentID = agentID;
				popupMenu.add(menuItemEditAgent);
				popupMenu.add(menuItemEditWorld);
				if (eWorld.getNeighbourSelected() != "World")
					popupMenu.add(menuItemEditAgentLinks);
			} else {
				popupMenu.remove(menuItemEditAgent);
				popupMenu.remove(menuItemEditWorld);
				if (menuItemEditAgentLinks != null)
					popupMenu.remove(menuItemEditAgentLinks);
			}

			super.mouseClicked(e);
		} else {
			if ((rayon > diameter / 2 - littleradius)
				&& (rayon < diameter / 2 + littleradius)
				&& (alpha
					<= Math.abs(alpha)
						/ alpha
						* compteur
						* TWO_PI
						/ (new Integer(N)).doubleValue()
						+ alphazero)
				&& (alpha
					>= Math.abs(alpha)
						/ alpha
						* compteur
						* TWO_PI
						/ (new Integer(N)).doubleValue()
						- alphazero))
				 ((EAgent) eWorld.get(agentID)).inverseState();
			this.repaint();
			//System.out.println("canvasSmallWorld.repaint() !");
		}
	}

	public void mouseMoved(MouseEvent e) //// DP 17/10/2003 A OPTIMISER

	{
		//System.out.println("mouseMoved !");	
		// caracteristiques de la zone d'affichage

		double littleradius = 22 - 2 * size;
		double diameter =
			Math.min(dimension.width, dimension.height)
				- margin * 2
				- littleradius * 2;
		int N = size * size;
		int compteur = 0;
		int localAgentID;

		// changement d'origine

		e.translatePoint(-dimension.width / 2, -dimension.height / 2);

		// coordonnees cartesiennes

		int abscisse = e.getX();
		int ordonnee = -e.getY();

		// coordonnees polaires

		double rayon = Math.sqrt(Math.pow(abscisse, 2) + Math.pow(ordonnee, 2));
		double alpha = Math.atan2(ordonnee, abscisse);
		double alphazero = 2 * littleradius / diameter;

		// agent ID

		while (Math.abs(alpha) / alpha * alpha
			- compteur * TWO_PI / (new Integer(N)).doubleValue()
			>= Math.PI / (new Integer(N)).doubleValue()) {
			compteur++;
		}
		if (alpha <= Math.PI / (new Integer(N)).doubleValue()) {
			localAgentID = compteur;
		} else {
			localAgentID = N - compteur;
		}

		//if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK)

		/*
		{
		   if ((rayon > diameter / 2 - littleradius)&&(rayon < diameter / 2 + littleradius)&&(alpha <= Math.abs(alpha) / alpha * compteur * TWO_PI/(new Integer(N)).doubleValue() + alphazero)&&(alpha >= Math.abs(alpha) / alpha * compteur * TWO_PI/(new Integer(N)).doubleValue() - alphazero))
		   
		   {popupMenu.add(menuItemEditAgent);
		      popupMenu.add(menuItemEditWorld);}
		   
		   else 
		   
		   {popupMenu.remove(menuItemEditAgent);
		      popupMenu.remove(menuItemEditWorld);}
		
		   super.mouseClicked(e);
		}
		else
		{
		*/
		if ((rayon > diameter / 2 - littleradius)
			&& (rayon < diameter / 2 + littleradius)
			&& (alpha
				<= Math.abs(alpha)
					/ alpha
					* compteur
					* TWO_PI
					/ (new Integer(N)).doubleValue()
					+ alphazero)
			&& (alpha
				>= Math.abs(alpha)
					/ alpha
					* compteur
					* TWO_PI
					/ (new Integer(N)).doubleValue()
					- alphazero)) {
			if (OldAgentID != localAgentID) {
				System.out.println("Agent  :" + localAgentID);
				OldAgentID = localAgentID;
			}
			this.setCursor(canvasHandCursor);
			this.setToolTipText((new Integer(localAgentID)).toString());
			//{((CAgent)eWorld.get(agentID)).inverseState();
			//this.repaint();
		} else {
			this.setCursor(canvasCrosshairCursor);
		}
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(menuItemEditAgent)) {
			centralControl.editAgent(selectedAgentID);
		} else if (
			e.getSource().equals(menuItemEditAgentLinks)) { // DP 17/10/2003
			centralControl.editAgentLinks(selectedAgentID);
		} else if (e.getSource().equals(menuItemEditWorld)) {
			//VERIFIER
			; //eWorld.edit(centralControl);
		} else {
			super.actionPerformed(e);
		}
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