/*
 * @(#)Canevas.java	1.3 23-Feb-04
 */
package models.ising2;


import java.awt.Color;
/**
 * Re-define the colors of the agents in the bottom panel (<em>Canevas</em>),
 * depending on their state
 * 
 * @author denis.phan@enst-bretagne.fr
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.2, 05-Aug-02
 * @version 1.3, 23-Feb-04
 * @see models.emptyModel.World
 */
public class Canevas extends modulecoGUI.grapheco.Canevas {
	   
	   /**
	   * this class overide the agent's two dimensional representation(Canevas)
	   * in a discret choice monopoly market
	   * @ see modulecoGUI.grapheco.Canevas
	   */
	      
	         public Canevas()	         {
	            super();
	         }
	      
	         protected void setColors() {
	            c = new Color[2][2];
	            /*
	         	* customer
	         	*/
	            c[0][0] = Color.black ;   
	            /*
	         	* non customer
	         	*/
	            c[1][1] = Color.white ;	 
	            /*
	         	* new customer
	         	*/
	            c[1][0] = Color.gray.darker(); 
	            /*
	         	* ex customer
	         	*/
	            c[0][1] = Color.gray.darker().darker(); 
	         }
	      }
