 /* Source File Name:   Graphique.java
 * Copyright:    Copyright (c)enst-bretagne
 * re definition of grapheco/Graphique 
 * @author philippe LeGoff, Denis.Phan@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004   
 */

   package models.twoPartTarifCompetition;

   import java.awt.Color;

   import modulecoGUI.grapheco.graphix.Trace;

   public class Graphique extends modulecoGUI.grapheco.graphix.Graphique
   {
      protected int NbFirm;
      //protected  WorldEditorPanel worldEditorPanel;
      public Graphique()
      {
         //DisplayTrace();
      }
   /*
   * Method instancied by grapheco.graphix.Graphique.setCAgent() and not by this.constructor,
   * because eWorld must to be known previouly by the class Graphique in order to get the Number
   * of Competitors from the eWorld instance, world (by the way of the cast:(World)eWorld)
   */
      public void DisplayTrace() {
         NbFirm = ((World)eWorld).getNbCompetitors();
         Color[] colorArray = new Color[] {Color.black,Color.blue,Color.red,Color.green,Color.magenta,Color.darkGray,Color.cyan,Color.yellow,Color.gray,Color.pink};
         this.addTrace(new Trace("Total Suscribers", colorArray[0], "Competitor0"), true);
         for(int i=1; i<(NbFirm+1);i++){
            this.addTrace(new Trace("Suscribers Competitor "+i, colorArray[i], "Competitor"+i), true);
         }
      }
   }
