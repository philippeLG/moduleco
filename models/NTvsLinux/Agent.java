    /** models.NTvsLinux.World
	*@author frederic.falempin@enst-bretagne.fr, camille.monge@enst-bretagne.fr
	* @version 1.2  august,5, 2002
	* @version 1.4. february 2004   
	*/

   package models.NTvsLinux;

   import java.util.Iterator;
   import java.util.ArrayList;

   import java.lang.Math; // A SUBSTITUER PAR RANDOM !! 

   import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.modeleco.randomeco.CRandomDouble;
// import modulecoGUI.grapheco.descriptor.DataDescriptor;
   import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
   import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;

// import modulecoFramework.medium.Medium;
   import modulecoFramework.medium.NeighbourMedium;

/**
 * This class define a moduleco agent that simulate the Nicolas
 * Julien model NT versus Linux
 * The evolution of the agent is ruled by the following equation:
 *
 *<BR>
 * <B>
 * <PRE>
 *                                  th(a((xl - alpha*xw) + b(XL - beta * XW)))+1
 *  Probality(Agent adopts Linux) = --------------------------------------------
 *                                                        2
 * </PRE>
 * </B>
 * <BR>
 * - xl is the proportion of agent's neighbours adopters of Linux <BR>
 * - xw is the proportion of agent's neighbours adopters of NT <BR>
 * - XL is the proportion of agents adopters of Linux <BR>
 * - XW is the proportion of agents adopters of NT <BR>
 * - a estimate the preference for standadisation <BR>
 * - b estimate the preference for global standardisation
 * over local one <BR>
 * - alpha estimates the local influence of previous NT adoptions
 * as compared to Linux adoptions on adoption behaviours <BR>
 * - beta estimates the global influence of previous NT adoptions
 * as compared to Linux adoptions on adoption behaviours <BR>
 *
 * @author frederic.falempin@enst-bretagne.fr
 * @version  armagedon
 */
   public class Agent extends EAgent
   {
    /**
     *true if the agent have adopted linux
     * false elsewhere...
     */
      protected boolean OSAdopted;
    /**
     * True if Linux adopted next iteration
     */
      protected boolean OSToBeAdopted;
   
    /**
     * Used during the World creation
     */
      protected CRandomDouble randomInitialisation;
    /**
     * Used to calculate the evolution
     *  of the agent
     */
      protected CRandomDouble randomProbability;
   
    /**
     *  Probability the agent adopt Linux
     *  at the  begining
     */
      protected double earlyAdopt;
   
    /**
     * a estimate the preference for standadisation (a in the above equation)
     */
      protected double a;
    /**
     * b estimate the preference for global standadisation (b in the above equation)
     */
      protected double b;
    /**
     * alpha estimate the local influence of previous NT adoptions
     * as compared to Linux adoptions on adoption behaviours (alpha in the above equation)
     */
      protected double alpha;
    /**
     * beta estimate the global influence of previous NT adoptions
     * as compared to Linux adoptions on adoption behaviours (beta in the above equation)
     */
      protected double beta;
   
      public Agent () {
         super();
      }
   
    /**
     *See wheter or not the agent is an early adopter of Linux.
     * The probality of being an early adopter is earlyAdopt.
     */
      public void init()
      {
         neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
         OSAdopted = ((randomInitialisation.getDouble() <= earlyAdopt) ? true : false);
      }
   
   /**
   * @return True if the agent has adopted linux, false elsewhere...
   */
      public Object getState()
      {
         return new Boolean(OSAdopted);
      }
   
      public void commit()
      {
         OSAdopted = OSToBeAdopted;
        // if (ae != null) ae.update();
      }
   
      public void compute()
      {
         getInfo();
      	//System.out.println("mes randoms : "+randomInitialisation.getClass().getName()+randomProbability.getClass().getName());
      
         int neighbourLinuxAdopters = 0;
         double neighbourLinuxAdoptersProportion;
         double neighbourNTAdoptersProportion;
      
         for (Iterator i= neighbours.iterator();i.hasNext();)
         {
            if (((Boolean)(((Agent) i.next()).getState())).booleanValue())
            {
               neighbourLinuxAdopters++;
            }
         }
      
         neighbourLinuxAdoptersProportion = ((double)neighbourLinuxAdopters) / ((double)neighbours.size());
         neighbourNTAdoptersProportion = 1 - neighbourLinuxAdoptersProportion;
      
        //OSToBeAdopted = (randomProbability.getDouble() <= (( th(a*((1-b)*(neighbourLinuxAdoptersProportion - alpha * neighbourNTAdoptersProportion)
        //+ b*( ((World)world).getLinuxAdoptersProportion() - beta * ((World)world).getNTAdoptersProportion() ) ) ) + 1) / 2 ) ? true : OSAdopted);  // It is just the equation at the begining...
         OSToBeAdopted =((randomProbability.getDouble() <= (th(a*(neighbourLinuxAdoptersProportion - alpha * neighbourNTAdoptersProportion) + b*(((World)world).getLinuxAdoptersProportion() - beta * ((World)world).getNTAdoptersProportion())) + 1) / 2) ? true : OSAdopted);  // It is just the equation at the begining...
        //Ancienne "bonne":((randomProbability.getDouble() <= (th(a*((neighbourLinuxAdoptersProportion - alpha * neighbourNTAdoptersProportion) + b*( ((World)world).getLinuxAdoptersProportion() - beta * ((World)world).getNTAdoptersProportion())))+ 1) / 2) ? true : OSAdopted);  // It is just the equation at the begining...
      
      }
   
   
      public void getInfo()
      {
         this.a  = ((World) world).getA();
         this.b  = ((World) world).getB();
         this.alpha = ((World) world).getAlpha();
         this.beta = ((World) world).getBeta();
         this.earlyAdopt = ((World) world).getEarlyAdopt();
         this.randomInitialisation = ((World) world).getRandomInitialisation();
         this.randomProbability = ((World) world).getRandomProbability();
      }
   
   
    /**
     * Change the value of a
     * @param a The new value of a
     */
      public void setA(double a)
      {
         this.a = a;
      }
    /**
     * Change the value of b
     * @param b The new value of b
     */
      public void setB(double b)
      {
         this.b = b;
      }
    /**
     * Change the value of alpha
     * @param alpha The new value of alpha
     */
      public void setAlpha(double alpha)
      {
         this.alpha = alpha;
      }
    /**
     * Change the value of beta
     * @param beta The new value of beta
     */
      public void setBeta(double beta)
      {
         this.beta = beta;
      }
   
      public void inverseState()
      {
         OSAdopted = !OSAdopted;
         OSToBeAdopted = OSAdopted;
      }
   
    /**
     * This method is designed for an internal use.
     * @return The hyperbolical tangeant of x
     * @param x The double we need to know the hyperbolical tangant
     */
      private double th(double x) // return the hyperbolical tangant of x
      {
         return (Math.exp(x) - Math.exp(-x))/(Math.exp(x) + Math.exp(-x));
      }
   
      public ArrayList getDescriptors()
      
      {
         descriptors.clear();
         descriptors.add(new BooleanDataDescriptor(this,"OS adopted","OSAdopted",OSAdopted,true));
         descriptors.add(new BooleanDataDescriptor(this,"OS to be adopted","OSToBeAdopted",OSToBeAdopted,false));
         descriptors.add(new DoubleDataDescriptor(this,"earlyAdopt","earlyAdopt",earlyAdopt,false,6));
         descriptors.add(new DoubleDataDescriptor(this,"a","a",a,true,6));
         descriptors.add(new DoubleDataDescriptor(this,"b","b",b,true,6));
         descriptors.add(new DoubleDataDescriptor(this,"alpha","alpha",alpha,true,6));
         descriptors.add(new DoubleDataDescriptor(this,"beta","beta",beta,true,6));
         return descriptors;
      }
   
      public void inverseOSAdopted()
      
      {
         OSAdopted = !OSAdopted;
         OSToBeAdopted = OSAdopted;
      }
   }
