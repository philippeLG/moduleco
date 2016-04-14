
/**
 * VarCalculator.java
 *
 *
 * Created: Mon Sep 18 10:24:27 2000
 *
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

   package modulecoGUI.grapheco.statManager;

   import modulecoFramework.modeleco.EWorld;
   import modulecoFramework.modeleco.EAgent;

   import java.util.Hashtable;
   import java.util.Iterator;
   import java.util.Enumeration;


/**
 * This class is usualy instanciated by the statManager. It is used to
 * calculate the Calculated Vars. It's this class who scan the agents of an
 * eworld, get some values in them, and then calculate the sum, average,
 * minimum... of an those values.
 * @see grapheco.statManager.StatManager
 * @see grapheco.statManager.CalculatedVar
 */
   public class VarCalculator
   {
    /**
     * This hashtable links Calculated Vars with their names
     */
      private Hashtable hashVar;
    /**
     * This hashtable links Calculated vars with their current value
     */
      private Hashtable hashValues;
    /**
     * This is the eWorld on which the agents are scanned for calculation
     */
      private EWorld eWorld;
   
    /**
     * This calculator is usually used by statmanager.
     * @param eWorld The eWorld on which the agents are scanned for calculation
     * @see grapheco.statManager.StatManager
     */
      public VarCalculator(EWorld eWorld)
      {
         this.eWorld = eWorld;
      
         hashVar = new Hashtable();
         hashValues = new Hashtable();
      }
   
    /**
     * It adds a Calculated Var to the Var Calculator.
     * This method is usualy called by the statmanager
     * @param var The Calculated Var to add to the var calculator
     * @see grapheco.statManager.StatManager
     */
      public void add(CalculatedVar var)
      {
        //Link the Calculated Var with its name
         hashVar.put(var.getName(), var);
        //Calculate the first iteration (iteration = 0)
        //calculate(); trop tot...dans init() apres...connexions
      }
   
   
    /**
     * Allow access to variables values.
     * @return The value of the Calculated var
     * @param varName The name of the Calculated Var we want to know the value
     */
      public double get(String varName)
      {
         Double data = (Double) hashValues.get(varName);
         if (data == null) data = new Double(0); // when not yet calculated
         return data.doubleValue();
      }
   
    /**
     * Calculate all the Calculated Vars stored in the var calculator
     */
      public void calculate()
      {
         CalculatedVar var;
      
         for (Enumeration e = hashVar.elements(); e.hasMoreElements();)
         {
            // We scan all elements of the hastable hashVar (i.e. all
            // the Calaculated Vars).
            var = (CalculatedVar) e.nextElement();
         
            try
            {
                // We look at the type of the Calculated Var
               if (var.getType() == CalculatedVar.SUM)
               {
                    // Just a temp var to store the sum
                  double sum = 0;
               
                    // Here we scan all agents
                  for (Iterator i = eWorld.iterator(); i.hasNext();)
                        // We invoke the getMethod of the
                        // Calculated var on an agent, and
                        // add it to sum
                     sum = sum + (((Double)(var.getMethod().invoke(((EAgent)i.next()), null))).doubleValue());
               
                    // Finally we store the sum in hashValues
                  hashValues.put(var.getName(), new Double(sum));
               } 
               else if (var.getType() == CalculatedVar.AVERAGE) {
                    // Quite the same as SUM
                  double sum = 0;
                  int count = 0;
               
                  for (Iterator i = eWorld.iterator(); i.hasNext();)
                  {
                     sum = sum + (((Double)(var.getMethod().invoke(((EAgent)i.next()), null))).doubleValue());
                     count++;
                  }
               
                  hashValues.put(var.getName(), new Double(sum/count));
               } 
               else if (var.getType() == CalculatedVar.NUMBER) {
                  double num = 0;
                  double count = 0;
               
                    // We scan the agents of the eWorld
                  for (Iterator i = eWorld.iterator(); i.hasNext();)
                  {
                        // We look if the value of the
                        // Calculated Var method is equal
                        // to Calculated Var attribute.
                     if ((var.getMethod().invoke(((EAgent)i.next()), null)).equals(var.getAttributes()))
                            // If it is, we increment num
                        num++;
                     count++;
                  }
               
                    // We store the proportion in percents
                    // of the number of agent with value
                    // equal to Calculated Var attribute.
                  hashValues.put(var.getName(), new Double((num/count)*100));
               } 
               else if (var.getType() == CalculatedVar.VALUE) {
                  double value = 0;
               
                    //We scan the agents in the eWorld
                  for (Iterator i = eWorld.iterator(); i.hasNext();)
                  {
                        // If the agent is equal to the
                        // Calculated Var attribute
                     if (((EAgent)i.next()).equals(var.getAttributes()))
                            // We get the value of Calculated
                            // Var getMethod in the current
                            // agent
                        value = ((Double)var.getMethod().invoke(((EAgent)i.next()), null)).doubleValue();
                  }
               
                    // And we store it in hashValues
                  hashValues.put(var.getName(), new Double(value));
               } 
               else if (var.getType() == CalculatedVar.MINIMUM) {
                    //Quite the same as SUM
                  double value = Double.MAX_VALUE;
               
                  for (Iterator i = eWorld.iterator(); i.hasNext();)
                  {
                     value = Math.min(((Double)(var.getMethod().invoke(((EAgent)i.next()), null))).doubleValue(),value);
                  }
               
                  hashValues.put(var.getName(), new Double(value));
               } 
               else if (var.getType() == CalculatedVar.MAXIMUM) {
                    //Quite the same as SUM
                  double value = Double.MIN_VALUE;
               
                  for (Iterator i = eWorld.iterator(); i.hasNext();)
                  {
                     value = Math.max(((Double)(var.getMethod().invoke(((EAgent)i.next()), null))).doubleValue(),value);
                  }
               
                  hashValues.put(var.getName(), new Double(value));
               }
            }
               catch (java.lang.reflect.InvocationTargetException ex)
               {
                  System.out.println(((this.getClass()).getPackage()).getName()+" "+ex.toString());
               }
               catch (IllegalAccessException ex)
               {
                  System.out.println(ex.toString());
               }
         }
      }
   }// VarCalculator
