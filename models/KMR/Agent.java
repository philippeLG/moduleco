/** Modele KMR.java
 * Title:        
 * Description:
 * Copyright:    Copyright (c)enst-bretagne
 * @author Vincent.lelarge@enst-bretagne.fr denis.phan@enst-bretagne.fr
 * mars 2002
 * @version 1.2  august,5, 2002 
 */

   package models.KMR;

// import java.util.Iterator;
   import java.util.ArrayList;


   import modulecoFramework.abstractModels.EAgentGame ;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;

// import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
   import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
// import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;

   //import modulecoFramework.medium.NeighbourMedium;

   public class Agent extends EAgentGame {
     /**
     * "trembling hand" = probability threshold for to behave at random
     */
      protected double tremble;
   
      protected CRandomDouble random;
      /**
   	*
   	*/
      //Part I - Initialisation of the agent =================
   	/**
   	* COMMENTER LE CONSTRUCTEUR
   	*/
      public Agent() {
         super();
      }
   /**
   * 
   */
      public void getInfo() {
         this.random  = ((World) world).random;
      }
   /**
   *
   */
   
      public void init() {
         super.oldStrategy = ((random.getDouble() < 0.5 ) ?  true : false );
         super.init();
         //neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
         tremble = ((World)world).getTremble();	
      }
   
      public void compute() {
         oldStrategy = newStrategy;
         tremble = ((World)world).getTremble();
         double tr =random.getDouble();
         if (tr > tremble ) computeBestReply();
         else { // "trembling hand"
            if (random.getDouble() < 0.5 ) nextStrategy = true ;
            else nextStrategy = false ;
         }
      }   
   

   
      public void commit() {
         newStrategy = nextStrategy;
         hasChanged = (newStrategy != oldStrategy);
      }
   
      public Object getState() {
         //System.out.println(" agent.getState() ");
         return new Boolean(newStrategy); 
      }
   
      public void setRandom(CRandomDouble random) {
         //if (agentID==0)
            //System.out.println("agent.setRandom() ");
         this.random = random;
      }
   
   /**
   *
   */
      public ArrayList getDescriptors(){
         descriptors=super.getDescriptors();
         //descriptors.add(new IntegerDataDescriptor(this,"State","state",state,false));
         descriptors.add(new BooleanDataDescriptor(this,"Has changed","hasChanged",hasChanged,false));
         return descriptors;
      }
   }