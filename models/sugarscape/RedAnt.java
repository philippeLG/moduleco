/*
 * RedAnt.java
 *
 * Created on 17 mai 2001, 10:19
 * @version 1.2  august,5, 2002  
 */

   package models.sugarscape;

/**
*
 */
   public class RedAnt extends Ant {
   
    /** Creates new RedAnt */
      public RedAnt(Place p) {
         super(p);
      }
   
      public boolean isBlue() {
         return false;
      }
   
      public String toString()
      {
         return "I'm red !";
      }
   
   }
