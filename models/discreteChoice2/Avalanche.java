/*
 * @(#)Avalanche.java	1.1 21-Jun-04
 */
   package models.discreteChoice2;
/**
 * Regroupe les variables et methodes pour l'"avalanche" du modele d'ising
 * 
 * 
 * @author Gilles Daniel (gilles@cs.man.ac.uk)
 * @version 1.0, 23-Feb-04
 * @version 1.1, 09-Mar-04
 * @see models.emptyModel.World
 */


           
   public class Avalanche { 
           
   /**
    * 
    */
   
      public int avalanche; // avalanche
      public int avalanchePrev;
      protected int nbstabilites;
      protected int cyclestabilite;
      public int Sumomega;
      public int SumomegaPrev;
      private int NumberOfCall;
      private World world;
   
              
      public Avalanche(World wolrd) {
              
         System.out.println("[Avalanche()]");
      
         this.avalanche=0;
         this.avalanchePrev=0;
         this.nbstabilites=0;
         this.Sumomega=0;
         this.SumomegaPrev=0;
         this.NumberOfCall=0;
         this.world=wolrd;
         this.cyclestabilite=world.agentSetSize;
      
      /**
       * <ul>
       * <li>"Price" is the name to display on right-click of the right
       * panel (<em>Graphique</em>)
       * <li>"statPrice" is the exact name used by the <em>statManager</em>
       * </ul>
       */
      }
   
              
      public boolean Est_Stable (int modifs, boolean est_parallele) {
              
         if( est_parallele||(this.cyclestabilite<1) ) {
            cyclestabilite=world.agentSetSize;
            Sumomega+=modifs*2;													// Sans le *2, c'est faux
            avalanche = (int)Math.abs((Sumomega - SumomegaPrev) / 2.0);			//	NON INITIALISE !
            if (avalanche == 0) {
               if(est_parallele)
                  return true;
               else
                  this.nbstabilites++;
            } 
            else {
               this.nbstabilites = 0;
            }
            if (this.nbstabilites > 10)
               return true;
            this.avalanchePrev = this.avalanche;
            this.SumomegaPrev=this.Sumomega;
         } 
         else {
            Sumomega+=modifs;
            this.cyclestabilite--;
         }
         return false;
      }
   
              
      public double Get_avalanche() {
              
         return this.avalanche;
      }
   
   }