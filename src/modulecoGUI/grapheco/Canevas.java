/*
 * Source File Name: Canevas.java Copyright: Copyright (c)enst-bretagne @author
 * denis.phan@enst-bretagne.fr created mai, 29, 2000
 * 
 * @version 1.2 august,5, 2002
 */
package modulecoGUI.grapheco; // Level 3 Class
// extend CBufferedCanvas & use 3 encoders : gif png, pngB
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.InputEvent;
import java.awt.MenuItem;
import java.awt.Color;
import java.awt.Graphics;
import modulecoFramework.modeleco.EAgent;
import modulecoFramework.modeleco.EWorld;
import modulecoFramework.modeleco.CAgent;
public class Canevas extends CBufferedCanvas implements CAgentRepresentation {
	/**
	 * The name of this CAgentRepresentationContainer
	 */
	public String name;
	protected EWorld eWorld;
	protected char state[][][]; // copie de l'etat
	protected MenuItem menuItemEditAgent;
	protected MenuItem menuItemEditWorld;
	protected Color c[][];
	protected int size;
	protected final Color fondcanvas = Color.lightGray;
	protected int xc, yd;
	public Canevas() {
		super();
		setColors();
		setBackground(fondcanvas);
		menuItemEditAgent = new MenuItem("Edit Agent");
		menuItemEditWorld = new MenuItem("Edit World");
		menuItemEditAgent.addActionListener(this);
		menuItemEditWorld.addActionListener(this);
	}
	public void setCAgent(CAgent cAgent) {
		//System.out.println("Canevas.setCAgent()");
		this.eWorld = (EWorld) cAgent;
		setGridSize();
	}
	public void setGridSize() {
		size = (new Double(Math.sqrt(eWorld.getAgentSetSize()))).intValue();
		state = new char[size][size][2];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				state[j][i][0] = (((Boolean) ((CAgent) eWorld.get(j * size + i))
						.getState()).booleanValue() ? '\000' : '\001');
				state[j][i][1] = state[j][i][0];
				/***************************************************************
				 * (((Boolean) ((CAgent) eWorld.get(j ? '\000'
				 */
			}
		drawAble = true; // DP 20/08/2002
	}
	protected void setColors() {
		c = new Color[2][2];
		c[0][0] = Color.blue; // S1 > S1
		c[1][1] = Color.red; // S2 > S2
		c[1][0] = Color.yellow; // S2 > S1
		c[0][1] = Color.green; //green; // S1 > S2
	}
	protected void drawPoint(Graphics g, int i, int j, int a, int b, int x,
			int y) {
		if (eWorld != null) {
			int i1, j1;
			state[j][i][0] = state[j][i][1]; //on mémorise l'état précédent
			state[j][i][1] = (((Boolean) ((CAgent) eWorld.get(j * size + i))
					.getState()).booleanValue() ? '\000' : '\001');
			g.setColor(c[state[j][i][0]][state[j][i][1]]);
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
	// implémentés par certains modèles
	protected void drawFirstTime(Graphics g) {
		//System.out.println("Canevas.drawFirstTime()");
		draw(g);
	}
	protected void draw(Graphics g) {
		//System.out.println("Canevas.draw()g != null :"+(g != null));
		int widthElement, heightElement, usedWidth, usedHeight;
		//if (size!=0){//DP 20/08/2002
		if (g != null) {
			g.setColor(Color.lightGray);
			g.fill3DRect(0, 0, dimension.width - 1, dimension.height - 1, true);
			if (drawAble) { //DP 20/08/2002{
				//System.out.println("Canevas.draw()drawAble = "+drawAble);
				widthElement = dimension.width / (size);
				usedWidth = dimension.width - widthElement * (size);
				heightElement = dimension.height / (size);
				usedHeight = dimension.height - heightElement * (size);
				if (size < 40) {
					g.setColor(Color.red.darker().darker()); //black);
					for (int k = 0; k < (size); k++)
						g.drawLine(usedWidth / 2, k * heightElement
								+ usedHeight / 2, dimension.width - usedWidth
								/ 2, k * heightElement + usedHeight / 2);
					for (int l = 0; l < (size); l++)
						g.drawLine(l * widthElement + usedWidth / 2,
								usedHeight / 2, l * widthElement + usedWidth
										/ 2, dimension.height - usedHeight / 2);
				}
				for (int i = 0; i < size; i++)
					for (int j = 0; j < size; j++) {
						drawPoint(g, i, j, widthElement, heightElement,
								usedWidth, usedHeight);
					}
			} else {
				//System.out.println("Canevas.draw()drawAble = "+drawAble);
				// //test DP 20/08/2002
			}
		}
	}
	/**
	 * On mouse click
	 */
	public void mouseClicked(MouseEvent e) {
		int agentRowFill = dimension.width / size;
		int widthUnused = dimension.width - agentRowFill * size;
		int agentColumnFill = dimension.height / size;
		int heightUnused = dimension.height - agentColumnFill * size;
		xc = (e.getX() - widthUnused / 2) / agentRowFill + 1;
		yd = (e.getY() - heightUnused / 2) / agentColumnFill + 1;
		if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {
			if ((e.getX() > (widthUnused / 2))
					&& (e.getX() < (dimension.width - (widthUnused / 2)))
					&& (yd > 0) && (yd < size + 1)) {
				//popupMenu.setLabel("Agent #" +((yd -1) * size + (xc -1)) + "
				// (Y : "+ yd +" ; X :"+ xc + ")");
				popupMenu.add(menuItemEditAgent);
				popupMenu.add(menuItemEditWorld); //SC 30.05.01
			} else {
				//popupMenu.setLabel("");
				popupMenu.remove(menuItemEditAgent);
				popupMenu.remove(menuItemEditWorld); //SC 30.05.01
			}
			super.mouseClicked(e);
		} else {
			if ((e.getX() > (widthUnused / 2))
					&& (e.getX() < (dimension.width - (widthUnused / 2)))
					&& (yd > 0) && (yd < size + 1)) {
				((EAgent) eWorld.get((yd - 1) * size + (xc - 1)))
						.inverseState();
				this.repaint();
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		/**
		 * Edit Agent
		 */
		if (e.getSource().equals(menuItemEditAgent))
			centralControl.editAgent(yd - 1, xc - 1);
		/**
		 * Edit the world. CHECK
		 */
		else if (e.getSource().equals(menuItemEditWorld)) { //SC 30.05.01
			if (centralControl.editorManager.worldAEexist()) {
				System.out.println("EWorld.edit() : World already edited");
			} else {
				centralControl.editorManager.buildPopUpEditor(eWorld, "world");
			}
		} else
			/**
			 * Other
			 */
			super.actionPerformed(e);
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