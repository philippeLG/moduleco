
/**
 * EAdditionalPanel.java
 *
 *
 * Created: Mon Aug 28 16:28:45 2000
 *
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

   package modulecoGUI.grapheco;

   public abstract class EAdditionalPanel extends CPanel 
   {
      protected EAdditionalPanel parent;
   
      public EAdditionalPanel() 
      {
         super();
         parent = null;
      }
   
      public void setParentAdditionalPanel(EAdditionalPanel parent)
      {
         this.parent = parent;
      }
   
      public EAdditionalPanel getParentAdditionalPanel()
      {
         return parent;
      }
   
   } // EAdditionalPanel
