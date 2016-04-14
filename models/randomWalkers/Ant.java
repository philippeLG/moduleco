/*
 * class randomWalkers.Ant.java
 *
 * Created on 25 avril 2001, 16:41
 * @author jerome.schapman@enst-bretagne.fr, gregory.gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004    
 */

   package models.randomWalkers;

   import java.util.ArrayList;
 
   import modulecoFramework.modeleco.mobility.MobileAgent;
   import modulecoFramework.modeleco.mobility.EPlace;
// import modulecoFramework.modeleco.mobility.Move;
// import modulecoFramework.modeleco.mobility.move.RandomMove;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;
   import modulecoFramework.modeleco.exceptions.AlreadyUsedPlaceException;

   public class Ant extends MobileAgent
   {
   
      protected CRandomDouble random;
      protected Place initialPlace;
   
      public Ant(Place p) {
         place = p;
         world = p.getWorld();
      }
   
      public void init() {
      
      //        random = ((Place) place).random;
         initialPlace = (Place) place;
      }
   
      public void compute() {
         super.move(); 
      }
   
   
      public  boolean canAccept(EPlace p) {
         return true;
      }
   
      public Object getState() {
         return new Boolean(true);
      }
   
      public void getInfo() {
      }
   
      public ArrayList getDescriptors() {
         return new ArrayList();
      }
   
      public void futurGo(Place p) {
         try {
         
            p.futurReceive(this);
            p.antHaveToMove();
         
            ((Place) place).futurLeave();
            ((Place) place).antHaveToMove();
            ((Place) place).computed();
         }
            catch (AlreadyUsedPlaceException e) {
               e.printStackTrace();
            }
      }
   
      public void commit() {
      }
   
      public double distanceFromBeginning () {
         int beginningLine, beginningColumn;
         int currentLine, currentColumn;
         double distance;
         int size = world.getLength();
         currentLine = calculateLine(place.getAgentID());
         beginningLine = calculateLine(initialPlace.getAgentID());
         currentColumn = calculateColumn(place.getAgentID());
         beginningColumn = calculateColumn(initialPlace.getAgentID());
      
         distance = java.lang.Math.sqrt(java.lang.Math.pow(currentLine - beginningLine , 2) + java.lang.Math.pow(currentColumn - beginningColumn , 2));
      //        System.out.println("Agent dans la colonne : " + currentColumn + " et dans la ligne : " + currentLine + ".");
      //        System.out.println("Distance : " + distance + ".");
         return (distance*50)/size;
      }
   
      protected int calculateColumn(int index) {
         int size = world.getLength();
         return (index % size);
      }
   
      protected int calculateLine(int index) {
      //        int capacity = world.getCapacity();
         int size = world.getLength();
         return (index / size);
      }
   }
