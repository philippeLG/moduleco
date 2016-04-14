 /* Source File Name:   Canevas.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 * @version 1.4  February, 2004
* @version 1.4. february 2004  
 */

   package models.discreteChoice;

   import java.awt.Color;
/**
* this class overide the agent's two dimensional representation(Canevas)
* in a discret choice monopoly market
* @ see modulecoGUI.grapheco.Canevas
*/

   public class Canevas extends modulecoGUI.grapheco.Canevas
   {
   
      public Canevas()
      {
         super();
      }
   
      protected void setColors() 
      
      {
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
