/**
 * Title: Medium
 * Description:  I represent communication components. I am in relation with CAgents that I know
 * thanks to attach and detach operations.
 * I am abstract and my concrete subclasses have to describe what kind of interaction I implement.
 * Subclasses can extend my interface (adding new operations) and must redefine commit(), and the way
 * I am connected to CAgents throught attach() and detach().
 * Know subclasses : (TO BE DEFINED)
 * Note : I implements Serializable to allow models saving.
 * @author Antoine.Beugnard@enst-bretagne.fr
 * created on april 2001
 * @version 1.2  august,5, 2002
 */

   package modulecoFramework.medium;

   import java.io.Serializable;

   import java.util.ArrayList;
   import java.util.Hashtable;
   import java.util.Enumeration;

   import modulecoFramework.modeleco.CAgent;

   public abstract class Medium implements Serializable {
   
    /**
     * Connection with role
     * @param agent : agent to be attached
     * @param role  : role of the agent (ie administrator, user, neighbour, ...)
     */
   
     /**
     * reference refTable , keys : role , values : ArrayList of connected agents
     */
      protected Hashtable refTable = new Hashtable();
      protected ArrayList data = new ArrayList(); // the current state of the medium
   
      public void attach(CAgent agent, String role) {
         ArrayList  listAgents;
         if (refTable.containsKey(role)) {
            listAgents = (ArrayList) (refTable.get(role));
         
         }
         else {
            listAgents = new ArrayList();
         }
         listAgents.add(agent);
         refTable.put(role, listAgents);
         data.add(agent);
         //System.out.println(refTable.get(role)); 
      	// reserve a cell in data for that agent
      }
   
    /**
     * Connection without role (CHECK, MAY BE USELESS)
     * @param agent : agent to be connected , default role = "agent"
     */
      public void attach(CAgent agent) {
         this.attach(agent, "agent");
      }
   
    /**
     * disconnection agent+role
     * @param agent : agent to be disconnected
     * @param role  : role of the agent give the list from wich the agent has to be disconnected
     */
      public void detach(CAgent agent, String role) {
         ArrayList  listAgents;
         int i;
         if (refTable.containsKey(role)) {
            listAgents = (ArrayList) refTable.get(role);
            i = listAgents.indexOf(agent);
            listAgents.remove(i);
            data.remove(i);
            refTable.put(role, listAgents);
         }
      }
   
    /**
     * disconnection of an agent whatever its role is (CHECK, MAY BE USELESS)
     * @param agent : agent to be detached
     */
      public void detach(CAgent agent){
         String key;
         ArrayList  listAgents;
         for (Enumeration e = refTable.keys(); e.hasMoreElements() ;) {
            key = (String) e.nextElement();
            this.detach(agent, key);
         }
      }
   
    /**
     * test if an agent is connected to a specific list
     * @param agent
     * @param role
     */
      public boolean isConnected(CAgent agent, String role) {
         if (refTable.containsKey(role)) {
            return ((ArrayList) refTable.get(role)).contains(agent);
         }
         else {
            return false;
         }
      }
   
    /**
     * test if an agent is connected in any list
     * @param agent
     */
      public boolean isConnected(CAgent agent) {
         for (Enumeration e = refTable.keys(); e.hasMoreElements() ;)
            if ( ((ArrayList) refTable.get((String) e.nextElement())).contains(agent) ) {
               return true;
            }
         return false;
      }
   
   
   
      public void init() {
      }
   
    /**
     * return the ArrayList of agents playing a given role, 
     * if the role is not defined, returns an empty list.
     * @param role
     */
      public ArrayList getAgentsForRole(String role) {
         if (refTable.containsKey(role)) {
            return (ArrayList) refTable.get(role);
         }
         else {
            return new ArrayList();
         }
      }
   
      public void clear(){ // Ajoute AB-DP 19/09/2001 -revision des voisinages
         refTable=new Hashtable();
         data=new ArrayList();
      }
   
   
   
   }