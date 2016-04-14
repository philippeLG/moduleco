/**

 * Title:        ItemComponent<p>
 * Description:  Je suis un composant d'affichage et de saisie interactive
 * Copyright:    Copyright (c)enst-bretagne
 * @author Denis.phan@enst-bretagne.fr
 *  november,30, 2000
 * @version 1.2  august,5, 2002
 */



   package modulecoGUI.grapheco;



   import java.awt.Label;

   import java.awt.TextField ;

   import java.awt.Color;

   import java.awt.Font;

   import java.awt.GridLayout;

   import java.awt.event.ActionEvent;

   import java.awt.event.ActionListener;





   public class ItemComponent extends java.awt.Container
   
   {
   
      public Label ItemComponentLabel ;
   
      public TextField ItemComponentTextField ;
   
      protected String textLabel;
   
      protected EAgentEditor editor;
   
      protected int index=0 ;  
   
   // par défaut : Editable(false) & textLabel=""
   
      public ItemComponent(EAgentEditor eae){
      
         editor = eae;
      
         textLabel="";
      
         buildComponent();
      
         setEditable(false);
      
      }
   
   
   
   // par défaut : Editable(false)
   
      public ItemComponent(String textLabel, EAgentEditor eae){
      
         editor = eae;
      
         this.textLabel=textLabel;
      
         buildComponent();
      
         setEditable(false);
      
      }
   
   
   
      public ItemComponent(String textLabel,boolean editable, EAgentEditor eae){
      
         editor = eae;
      
         this.textLabel=textLabel;
      
         buildComponent();
      
         setEditable(editable);
      
      }
   
   
   
      public void buildComponent() {
      
         this.setName("itemComponent");
      
         setLayout(new GridLayout(1,2));
      
         ItemComponentLabel = new Label (textLabel);  
      
         ItemComponentTextField= new TextField ();
      
         ItemComponentLabel.setAlignment (Label.CENTER);
      
      
      
         ItemComponentTextField.addActionListener (  
                              
                              
                              
                                 new ActionListener () {  
                                 
                                 
                                 
                                    public void actionPerformed (ActionEvent evt) {  
                                    
                                       //editor.TextFieldChanged (index);  
                                    
                                    }  
                                 
                                 }  
                              
                              );
      
         add(ItemComponentLabel);
      
         add(ItemComponentTextField);
      
      }	
   
   
   
      public void setLabelText(String textLabel) {
      
         ItemComponentLabel.setText (textLabel); 
      
      }
   
   
   
      public void setTextFieldText(String textTextField) {
      
         ItemComponentTextField.setText (textTextField); 
      
      }
   
   
   
      public String getTextFieldText() {
      
      
      
         return ItemComponentTextField.getText(); 
      
      }
   
      public void setBackground(Color colorBackground){
      
         ItemComponentLabel.setBackground (colorBackground);
      
         ItemComponentTextField.setBackground (colorBackground);
      
      }
   
   
   
      public void setBackground(Color colorBackgroundLabel, Color colorBackgroundTextField){
      
         ItemComponentLabel.setBackground (colorBackgroundLabel);
      
         ItemComponentTextField.setBackground (colorBackgroundTextField);
      
      }
   
   
   
      public void setForeground(Color colorForeground){
      
      
      
         ItemComponentLabel.setForeground (colorForeground);
      
         ItemComponentTextField.setForeground (colorForeground);
      
      }
   
   
   
   
   
      public void setForeground(Color colorForegroundLabel, Color colorForegroundTextField){
      
      
      
         ItemComponentLabel.setForeground (colorForegroundLabel);
      
         ItemComponentTextField.setForeground (colorForegroundTextField);
      
      }
   
   
   
      public void setFont(Font font){
      
         ItemComponentLabel.setFont (font);  
      
         ItemComponentTextField.setFont (font); 
      
      }
   
   
   
      public void setFont(Font fontLabel, Font fontTextField){
      
         ItemComponentLabel.setFont (fontLabel);  
      
         ItemComponentTextField.setFont (fontTextField); 
      
      }	
   
      public void setEditable (boolean bo){
      
         ItemComponentTextField.setEditable(bo);
      
      }
   
   
   
   }