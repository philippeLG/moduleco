/**
 * ExtendedButtonGroup.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author Jacques.Ferber modified by denis.phan@enst-bretagne.fr
 * created mai 2003
 * @version 1.3  august 2003
 */


   package modulecoGUI.grapheco;

   import javax.swing.AbstractButton;
   // import javax.swing.ButtonModel;
   import javax.swing.ButtonGroup;
   import java.util.Enumeration;

   public class ExtendedButtonGroup extends ButtonGroup {
   
      public ExtendedButtonGroup(){
      
         super();
      }
   
      public int getSelectedIndex(){
         Enumeration e = this.getElements(); 
          //System.out.println("ExtendedButtonGroup.getSelectedIndex()- Enumeration ="+e);
         AbstractButton b=null;
         int index;
         for (int i=0; e.hasMoreElements(); i++) {  
            b = (AbstractButton)e.nextElement();
             //System.out.println("ExtendedButtonGroup() - index = "+i+" nom = "+b.getText());
            if (b!=null) 
               if (b.isSelected()) 
                  return i;
         }
         return -1;
      }
   
      public String getName() {
         Enumeration e = this.getElements(); 
         String name = " ";
         AbstractButton b=null;
         for (int i=0 ; e.hasMoreElements(); i++){
            b = (AbstractButton)e.nextElement();
            if (b!=null) 
               if (b.isSelected()) name = b.getText();
         }
         return name ;
      }
   }