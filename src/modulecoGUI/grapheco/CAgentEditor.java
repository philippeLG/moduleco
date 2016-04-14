/**
 * Title:        CAgentEditor<p>
 * Description:  Je suis une classe abstraite que les différents modèles doivent dériver dans leur
 * package respectifs pour me spécialiser. @see{dp.WordEditor, dp.AgentEditor, life.AgentEditor]...<p>
 * ATTENTION: Mes sous classes doivent s'appeler EAgentEditor {@see grapheco.CentralControl}<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr Denis.Phan@enst-bretagne.fr
 * created may 2000
* @version 1.2  august,5, 2002
* @version 1.4  February 2004
 */

   package modulecoGUI.grapheco;

   import java.awt.event.WindowEvent;

// import modulecoFramework.modeleco.CAgent;

   public interface CAgentEditor
   {
   
   /** Creates new form AgentEditor */
   
   /** Define the CentralControl for to update */
      public abstract void setControl(CentralControl cp); 
   /** Build the Container and the components*/
      public abstract void build() ;
   
   /** Close the Editor : specific at Frame */
   
      public abstract void exitForm(WindowEvent evt); 
   
   /**
    * Mes sous-classes doivent me redefinir en fonction des champs à manipuler...
    */
      public abstract void update();
   }
