 /* Source File Name:   canvasSmallWorld.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

   package models.discreteChoice;

   //import java.awt.Graphics;
   import java.awt.Color;
   /**
* this class overide the agent's one dimensional circular representation(canvasSmallWorld)
* in a discret choice monopoly market
* @ see modulecoGUI.grapheco.Canevas
* @ see modulecoGUI.grapheco.canvasSmallWorld
*/
   public class canvasSmallWorld extends modulecoGUI.grapheco.canvasSmallWorld
   {
   
      public canvasSmallWorld()
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
