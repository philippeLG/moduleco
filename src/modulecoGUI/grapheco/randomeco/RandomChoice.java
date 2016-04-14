//DEPRECATED ??
/**
 * RandomChoice.java
 * METHODE NON UTILISEE A REVOIR
 *
 * Created: Thu Aug 24 18:05:48 2000
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

   package modulecoGUI.grapheco.randomeco;

   import java.awt.Choice;

   import java.awt.event.ItemListener;
   import java.awt.event.ItemEvent;

   //import modulecoGUI.grapheco.EAdditionalPanel;

   //import modulecoGUI.grapheco.EPanel;

   import modulecoFramework.modeleco.randomeco.CRandomDouble;

   public class RandomChoice extends Choice implements ItemListener
   {    
      protected String description;
      protected RandomGraphicEditor randomGraphicEditor;
   
      public RandomChoice(String description)
      {
         randomGraphicEditor = null;
      
         this.description = description;
      
         this.addItemListener(this);
      
         this.add("Default");
         this.add("JavaRandom");
         this.add("JavaGaussian");
      }
   
      public CRandomDouble getRandom()
      {
         if (randomGraphicEditor != null)
            return randomGraphicEditor.getRandom();
         return loadRandom(getSelectedItem());
      }
   
      public void itemStateChanged(ItemEvent e)
      {
         if (randomGraphicEditor != null)
    //        ((EPanel)eWorldEditor.getParent()).removeAdditionalPanel((EAdditionalPanel)randomGraphicEditor);
      
         randomGraphicEditor = loadRandomGraphicEditor(getSelectedItem(), description);
         if (randomGraphicEditor == null)
         {
      //      ((EPanel)eWorldEditor.getParent()).validate();
            return;
         }
      
         randomGraphicEditor.setDescription(description);
      
        // ((EPanel)eWorldEditor.getParent()).addAdditionalPanel((EAdditionalPanel)randomGraphicEditor, (EAdditionalPanel) getParent());
      }
   
      public RandomGraphicEditor loadRandomGraphicEditor(String className, String description)
      {
         RandomGraphicEditor rge = null;
      
         try
         {
            rge = (RandomGraphicEditor) Class.forName("modulecoGUI.grapheco.randomeco." + className + "Editor").newInstance();
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
               rge = null;
            }
      
         return rge;
      }
   
      public CRandomDouble loadRandom(String className)
      {
         CRandomDouble random = null;
      
         try
         {
            random = (CRandomDouble) Class.forName("modulecoFramework.modeleco.randomeco." + className).newInstance();
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
               random = null;
            }
      
         return random;
      }
   
   } // RandomChoice
