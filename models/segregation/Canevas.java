/**
 /* Source File Name:   Canevas.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002 
 * @version 1.4.2  june 2004 
 */

package models.segregation;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import modulecoFramework.modeleco.CAgent;
import modulecoFramework.modeleco.mobility.EPlace;
import modulecoFramework.modeleco.mobility.MobileAgent;

public class Canevas extends modulecoGUI.grapheco.Canevas {

	protected int state[][]; // copy of the agent's state
	protected Color c[];
	/**
	 * 
	 */
	public Canevas() {
		super();
	}
	/**
	 * 
	 */
	protected void setColors() {
		c = new Color[5];
		c[0] = Color.lightGray;
		c[1] = Color.green; // shouhd not appear...
		c[2] = Color.lightGray;
		c[3] = Color.blue;
		c[4] = Color.red;
	}
	/**
	 * 
	 */
	public void setGridSize() { // eWorld just beeing created
		size = (new Double(Math.sqrt(eWorld.getAgentSetSize()))).intValue();

		state = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				state[j][i] =
					((Place) eWorld.get(j * size + i)).getActualState();
			}
	}

	/**
	 * 
	 */
	protected void drawPoint(
		Graphics g,
		int i,
		int j,
		int a,
		int b,
		int x,
		int y) {
		if (eWorld != null) {
			int i1, j1;
			state[j][i] = ((Place) eWorld.get(j * size + i)).getActualState();
			g.setColor(c[state[j][i]]);
			i1 = i * a;
			j1 = j * b;
			if (size < 40)
				// dessine un secteur circulaire
				// x,y, largeur, hauteur,debut arc, fin arc)
				g.fillArc(i1 + 1 + x / 2, j1 + 1 + y / 2, a - 2, b - 2, 0, 360);
			else
				g.fillRect(i1 + 1 + x / 2, j1 + 1 + y / 2, a, b);
			//x,y,largeur, hauteur
		}
	}
	/**
	 * 
	 */
	protected void draw(Graphics g) {
		int a, b, x, y;
		int i, j;

		if (g != null) {
			a = dimension.width / (size);
			x = dimension.width - a * (size);
			b = dimension.height / (size);
			y = dimension.height - b * (size);

			g.setColor(Color.lightGray);
			g.fill3DRect(0, 0, dimension.width - 1, dimension.height - 1, true);

			if (size < 40) {
				g.setColor(Color.black);
				for (int k = 0; k < (size); k++)
					g.drawLine(
						x / 2,
						k * b + y / 2,
						dimension.width - x / 2,
						k * b + y / 2);
				for (int l = 0; l < (size); l++)
					g.drawLine(
						l * a + x / 2,
						y / 2,
						l * a + x / 2,
						dimension.height - y / 2);
			}
			for (i = 0; i < size; i++)
				for (j = 0; j < size; j++) {
					drawPoint(g, i, j, a, b, x, y);
				}
		}
	}

	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
	}
	/**
	 * overides modulecoGUI.grapheco.Canevas.actionPerformed(ActionEvent e)
	 * in order to edit both EPlace and EAgent on mouse clicked (june 2004)
	 * @see modulecoGUI.grapheco.Canevas
	 **/
	//DEVRA ETRE ABSTRAIT
	public void actionPerformed(ActionEvent e) {
		EPlace ep = null;
		super.actionPerformed(e);
		/**
		 * Edit Agent
		 */
		if (e.getSource().equals(menuItemEditAgent)) {
			int index =
				((int) ((yd - 1) * Math.sqrt(eWorld.getAgentSetSize())))
					+ (xc - 1);
			if (index < eWorld.size()) { // precaution inutile ?
				ep = (EPlace) eWorld.get(index);
				if (ep.getState() != null) {
					MobileAgent ma = ep.getAgent();
					int aID = ma.getAgentID();
					centralControl.editMobileAgent(aID, (CAgent) ma);
				}

			}
		}

	}
}
