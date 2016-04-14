//DEPRECATED ??
/**
 * JavaRandomEditor.java
 *
 *
 * Created: Thu Aug 24 18:34:46 2000
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

   package modulecoGUI.grapheco.randomeco;

   import java.awt.Label;
   import java.awt.TextField;
   import java.awt.GridBagConstraints;

   import modulecoFramework.modeleco.randomeco.CRandomDouble;
   import modulecoFramework.modeleco.randomeco.JavaRandom;
   import modulecoGUI.grapheco.EAdditionalPanel;

   public class JavaRandomEditor extends EAdditionalPanel implements RandomGraphicEditor 
   {    
      private TextField textFieldSeed;
   
      public JavaRandomEditor() 
      {
         super();
      
         constraints.anchor = GridBagConstraints.EAST;
         add(new Label("Seed"), 1, 0, 1, 1);
      
         textFieldSeed = new TextField();
         constraints.anchor = GridBagConstraints.WEST;
         add(textFieldSeed, 2, 0, 1, 1);
      }
   
      public void setDescription(String description)
      {
         this.add(new Label(description), 0, 0, 1, 1);
      }
   
      public CRandomDouble getRandom()
      {
         if (textFieldSeed.getText() != "")
            return new JavaRandom((long) (new Double(textFieldSeed.getText())).doubleValue());
         return new JavaRandom();
      }
   } // JavaRandomEditor
