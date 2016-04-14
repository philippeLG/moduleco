/** Source File Name:   models.bak.Canevas.java
* Copyright:    Copyright (c)enst-bretagne
* @author camille.monge@enst-bretagne.fr, revised denis.phan@enst-bretagne.fr
* @version 1.4  February, 2004
*/

package models.bak;

import java.awt.Color;
import java.awt.Graphics;

public class Canevas extends modulecoGUI.grapheco.Canevas {
	protected double state[][]; // copy of the agent's state
	protected Color c[];
	protected int width;
	protected int height;

	public Canevas() {
		super();
	}
	/**
	 * method setColors() overides modulecoGUI.grapheco.Canevas.setColor()
	 * because specific color initialization in drawPoint(.)
	 */
	protected void setColors() {
	}
	/**
	 * overides modulecoGUI.grapheco.Canevas#setGridSize()
	 * with double value (instead of int value) for the agent's state
	 * @see modulecoGUI.grapheco.Canevas#setGridSize() 
	 */
	public void setGridSize() { // eWorld just beeing created
		size = (new Double(Math.sqrt(eWorld.getAgentSetSize()))).intValue();
		this.height = dimension.height;
		this.width = dimension.width;

		state = new double[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				state[j][i] = ((Agent) eWorld.get(j * size + i)).getViability();
			}
	}
	/**
	 * 
	 * @see modulecoGUI.grapheco.Canevas#drawPoint(java.awt.Graphics, int, int, int, int, int, int)
	 */
	protected void drawPoint(
		Graphics g,
		int i,
		int j,
		int a,
		int b,
		int x,
		int y) {
		int V, R;
		if (eWorld != null) {
			int i1, j1;
			state[j][i] = ((Agent) eWorld.get(j * size + i)).getViability();

			V = (int) (-25 * Math.round(10 * state[j][i]));
			V += 251;
			R = ((Agent) eWorld.get(j * size + i)).Life;
			R = 10 * R + 1;
			if (R > 255)
				R = 255;
			g.setColor(new Color(R, V, 0));

			//g.setColor(c[(int)Math.round(10*state[j][i])]);
			i1 = i * a;
			j1 = j * b;
			if (size < 40)
				// dessine un secteur circulaire
				// x,y, width, hauteur,debut arc, fin arc)
				g.fillArc(i1 + 1 + x / 2, j1 + 1 + y / 2, a - 2, b - 2, 0, 360);
			else
				g.fillRect(i1 + 1 + x / 2, j1 + 1 + y / 2, a, b);
			//x,y,width, hauteur
		}
	}
	/**
	 * 
	 */
	protected void draw(Graphics g) {
		int a, b, x, y;
		int i, j;

		if (g != null) {
			a = width / (size);
			x = width - a * (size);
			b = height / (size);
			y = height - b * (size);

			g.setColor(fondcanvas);
			g.fill3DRect(0, 0, width - 1, height - 1, true);

			if (size < 40) {
				g.setColor(Color.black);
				for (int k = 0; k < (size); k++)
					g.drawLine(
						x / 2,
						k * b + y / 2,
						width - x / 2,
						k * b + y / 2);
				for (int l = 0; l < (size); l++)
					g.drawLine(
						l * a + x / 2,
						y / 2,
						l * a + x / 2,
						height - y / 2);
			}
			for (i = 0; i < size; i++)
				for (j = 0; j < size; j++) {
					drawPoint(g, i, j, a, b, x, y);
				}
		}
	}

}
