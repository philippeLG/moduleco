/**
 * MoveChoice.java
 * Methode Non utilisee au 05/2004
 * Created: Thu Aug 24 18:05:48 2000
 *
 * @author frederic.falempin@enst-bretagne.fr
* @version 1.1  july,10, 2001
 */

   package modulecoFramework.modeleco.mobility;

   import java.awt.Choice;

   import modulecoFramework.modeleco.mobility.move.RandomMove;


   public class MoveChoice extends Choice{
   
   
      public MoveChoice(){      
         this.add("NearMove");
         this.add("NorthMove");
         this.add("SouthMove");
         this.add("RandomMove");
      
      }
   
   
   
      public Move getMove()
      
      {
      
         return loadMove(getSelectedItem());
      
      }
   
   
   
      public Move loadMove(String className)
      
      {
      
         Move move = null;
      
      
      
         try
         
         {
            move = (Move) (Class.forName(this.pack() + ".move." + className).newInstance());
         
         }
         
            catch (IllegalAccessException e)
            
            {
            
               System.out.println(e.toString());
            
            }
         
            catch (InstantiationException e)
            
            {
            
               System.out.println(e.toString());
            
            }
         
            catch (ClassNotFoundException e)
            
            {
            
               move = new RandomMove();
            
            }
      
      
      
         return move;
      
      }
   
      public String pack()
      
      {
      
         String n = this.getClass().getName();
      
         return n.substring(0, n.lastIndexOf('.'));
      
      }
   
   
   } // MoveChoice

