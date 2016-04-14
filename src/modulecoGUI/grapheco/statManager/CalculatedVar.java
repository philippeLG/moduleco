
/**
 * CalcultedVar.java
 *
 *
 * Created: Mon Sep 18 10:12:44 2000
 *
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

   package modulecoGUI.grapheco.statManager;

   import java.lang.reflect.Method;


/**
 * This class defines a variable, that is computed using a method on each agent
 * contained by an eworld. The Calculated Var, when constructed, have to be
 * added to the statmanager. The statmanager will then add it to its 
 * varcalculator. Each iteration, a value will be get on each agent,
 * and with those values, the varcalculator will calculate the value of 
 * the Calculated Var. Doing a sum of the values of agent, for example.
 * @see grapheco.statManager.StatManager
 * @see grapheco.statManager.VarCalculator
 * @see modeleco.EWorld
 */
   public class CalculatedVar
   {
    /**
     * This variable is used to tell the constructor the Calculated Var is 
     * computed with a sum of the values of agents.
     * <BR> 
     * For example :<BR><BR>
     * <CODE>new CalculatedVar("MyCalcVar", Class.forName("myModel.Agent").getMethod("getAgentVar", null), CalculatedVar.SUM, null); </CODE><BR><BR>
     * This will create a Calculated var that will be calculated scaning all
     * agents, getting the agentVar value using the getAgentVar() method 
     * (in MyModel.Agent), and making a sum of all agentVar values.
     * In this case, the last parameter should be null, and the getAgentVar
     * method must return a double.
     */
      public static int SUM = 0;
    /**
     * This variable is used to tell the constructor the Calculated Var is 
     * computed with an average of the values of agents.
     *<BR>  For example :<BR><BR>
     * <CODE> new CalculatedVar("MyCalcVar", Class.forName("myModel.Agent").getMethod("getAgentVar", null), CalculatedVar.AVERAGE, null); </CODE><BR><BR>
     * This will create a Calculated var that will be calculated scaning all
     * agents, getting the agentVar value using the getAgentVar() method 
     * (in MyModel.Agent), and making the average of all agentVar values.
     * In this case, the last parameter should be null, and the getAgentVar
     * method must return a double.
     */
      public static int AVERAGE = 1;
    /**
     * This variable is used to tell the constructor the Calculated Var is 
     * computed with a sum of the values of agents.
     * <BR> For example :<BR><BR>
     * <CODE>new CalculatedVar("MyCalcVar", Class.forName("myModel.Agent").getMethod("getAgentVar", null), CalculatedVar.NUMBER, Object)); </CODE><BR><BR>
     * This will create a Calculated var that will be computed scaning all
     * agents, getting the agentVar value using the getAgentVar() method
     * (in MyModel.Agent), and calculate the number of agent which have
     * their agentVar equal to Object. It uses the equals(Object) method.
     */
      public static int NUMBER = 2;
    /**
     * This variable is used to tell the constructor the Calculated Var is 
     * computed with a value of a single agents.
     * <BR> For example :<BR><BR>
     * <CODE>new CalculatedVar("MyCalcVar", Class.forName("myModel.Agent").getMethod("getAgentVar", null), CalculatedVar.VALUE, aAgent)); </CODE><BR><BR>
     * This will create a Calculated var that will be computed scaning all
     * agents, triing to find the agent equal to aAgent, getting the agentVar 
     * value using the getAgentVar() method (in MyModel.Agent). This will be
     * the Calculated Var value. It uses the equals(Object) method. In this
     * case, the getAgentVar method must return a double.
     */
      public static int VALUE = 3;
    /**
     * This variable is used to tell the constructor the Calculated Var is 
     * computed finding the minimum of the values of agents.
     * <BR>
     * <BR> For example :<BR><BR>
     * <CODE> new CalculatedVar("MyCalcVar", Class.forName("myModel.Agent").getMethod("getAgentVar", null), CalculatedVar.MINIMUM, null); </CODE><BR><BR>
     * This will create a Calculated var that will be calculated scaning all
     * agents, getting the agentVar value using the getAgentVar() method 
     * (in MyModel.Agent), and finding the minimum of all agentVar values.
     * In this case, the last parameter should be null, and the getAgentVar
     * method must return a double.
     */
      public static int MINIMUM = 4;
    /**
     * This variable is used to tell the constructor the Calculated Var is 
     * computed finding the maximum of the values of agents.
     * <BR>
     * <BR> For example :<BR><BR>
     * <CODE> new CalculatedVar("MyCalcVar", Class.forName("myModel.Agent").getMethod("getAgentVar", null), CalculatedVar.MAXIMUM, null); </CODE><BR><BR>
     * This will create a Calculated var that will be calculated scaning all
     * agents, getting the agentVar value using the getAgentVar() method 
     * (in MyModel.Agent), and finding the maximum of all agentVar values.
     * In this case, the last parameter should be null, and the getAgentVar
     * method must return a double.
     */
      public static int MAXIMUM = 5;
   
    /**
     * The name of the variable (the same as in the statmanager).
     * @see grapheco.statManager.StatManager
     */
      private String varName;
    /**
     * The method used to get the values used for variable computation on 
     * each agent.
     */
      private Method getMethod;
    /**
     * The type of the Calculated Var (for example CalculatedVar.SUM)
     */
      private int type;
    /**
     * Attributes of the Calculated Var. The meaning of it depends on the
     * type of the Calculated Var
     */
      private Object attributes;
   
    /**
     * Constructs a Calculated Var. Usualy used in the constructor of a world
     * this way:<BR>
     * <CODE>
     * statManager.add(new CalculatedVar(.....);
     * </CODE><BR><BR>
     * See the different kind of types to have more details and examples.
     * @param varName The name of the variable
     * @param getMethod The method used to get the values used for variable
     * computation on each agent. There can be some constraints on the return
     * type of the method. See the type description for more details.
     * @param type The type of the Calculated Var (for example CalculatedVar.SUM)
     * @param attributes Attributes of the Calculated Var. The meaning of it depends on the
     * type of the Calculated Var
     */
      public CalculatedVar(String varName, Method getMethod, int type, Object attributes)
      {
         this.varName = varName;
         this.getMethod = getMethod;
         this.type = type;
         this.attributes = attributes;
      }
   
    /**
     *@return The name of the variable.
     */
      public String getName()
      {
         return varName;
      }
    /**
     *@return The type of the variable.
     */
      public int getType()
      {
         return type;
      }
    /**
     *@return The method used to get the values used for variable computation on 
     * each agent.
     */
      public Method getMethod()
      {
         return getMethod;
      }
    /**
     *@return The attributes of the variable.
     */
      public Object getAttributes()
      {
         return attributes;
      }
   } // CalculatedVar
