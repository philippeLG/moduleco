/**
 * ModulecoTextField.java
 *
 *
 * Created: Tue Aug 29 22:23:57 2000
 *
 * @author legoff@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */

   package modulecoGUI.grapheco;

   import javax.swing.JTextField;
   //import java.awt.TextField;
   import java.awt.Font;
   import java.awt.Color;

   //import java.awt.event.TextListener;
   import java.awt.event.TextEvent;
   import java.awt.event.ActionListener;
   import java.awt.event.ActionEvent;
   import java.awt.event.FocusListener;
   import java.awt.event.FocusEvent;

   public class ModulecoTextField extends JTextField implements ActionListener, FocusListener 
   //, TextListener
   {
      public static int INTEGER = 0;
      public static int DOUBLE = 1;
   
      public static int ODD = 2;
      public static int EVEN = 3;
   
      protected String name;
      protected String string;
      protected String oldString;
   
      protected int type;
      protected int min;
      protected int max;
   
      protected EWorldEditor worldEditorPanel;
      protected Font font = new Font("Dialog", 0, 12);
      protected Font smallfont = new Font("Dialog", 0, 11);
   
      public ModulecoTextField(String defaultString, int type, int min, int max)  // tmp for compatibility
      
      {
         super(defaultString);
      
         string = defaultString;
      
         //addTextListener(this);
         addActionListener(this);
         addFocusListener(this);
      
         setForeground(Color.black);
      
         this.type = type;
         this.max = max;
         this.min = min;
         Font fontType = font;
         super.setFont(fontType);
      
      }
   
      public ModulecoTextField(String defaultString, int type, int min, int max, Font fontType )  // tmp for compatibility
      
      {
         super(defaultString);
      
         string = defaultString;
      
         //addTextListener(this);
         addActionListener(this);
         addFocusListener(this);
      
         setForeground(Color.black);
      
         this.type = type;
         this.max = max;
         this.min = min;
         super.setFont(fontType);
      }
   
   
   
    /* prepare the textfield for update */
      public void setUpdateMode(String newName, EWorldEditor wep ) {
         this.worldEditorPanel = wep;
         this.name             = newName;
      }
   
      public void setType (int newType) {
         this.type = newType;
      }
   
      public void textValueChanged(TextEvent e)
      {
         process();
      }
   
      public String superGetText()
      {
         return super.getText();
      }
   
      public String getText()
      {
         oldString = string;
      
         modify();
      
         string = super.getText();
      
         return string;
      }
   
      public void actionPerformed(ActionEvent e)
      {	
         string = oldString;
         process();
         if (worldEditorPanel != null)    // tmp for compatibility
            worldEditorPanel.update(this.name);
      }
   
      public void focusGained(FocusEvent e)
      {
         if (!(super.getBackground().equals(Color.red)))
            super.setBackground(Color.green);
      }
   
      public void focusLost(FocusEvent e)
      {
         if (!(super.getBackground().equals(Color.red)))
            super.setBackground(Color.white);
      }
   
      public void modify()
      {
         if (type == INTEGER)
         {
            try
            {
               int value = Integer.parseInt(superGetText());
               if (value < min)
                  super.setText((new Integer(min)).toString());
               if (value > max)
                  super.setText((new Integer(max)).toString());
            }
               catch (NumberFormatException ex)
               {
                  super.setText(string);
               }
         }
         if (type == DOUBLE)
         {
            try
            {
               double value = Double.parseDouble(superGetText());
               if (value < min)
                  super.setText((new Integer(min)).toString());
               if (value > max)
                  super.setText((new Integer(max)).toString());
            }
               catch (NumberFormatException ex)
               {
                  super.setText(string);
               }
         }
         if (type == ODD)
         {
            try 
            {
               double value = Double.parseDouble(superGetText());
               if (value < min)
                  super.setText((new Integer(min)).toString());
            }
               catch (NumberFormatException ex)
               {
                  super.setText(string);
               }
         }
      
         if (type == EVEN)
         {
            try 
            {
               double value = Double.parseDouble(superGetText());
               if (value < min)
                  super.setText((new Integer(min)).toString());
            }
               catch (NumberFormatException ex)
               {
                  super.setText(string);
               }
         }
      }
   
      public void process()
      {	
         if (type == INTEGER)
         {
            try
            {
               int value = Integer.parseInt(superGetText());
               if ((value >= min) && (value <= max))
                  super.setBackground(Color.white);
               else
                  super.setBackground(Color.red);
            }
               catch (NumberFormatException ex)
               {
                  super.setBackground(Color.red);
               }
         }
         if (type == DOUBLE)
         {
            try
            {
               double value = Double.parseDouble(superGetText());
               if ((value >= min) && (value <= max))
                  super.setBackground(Color.white);
               else
                  super.setBackground(Color.red);
            }
               catch (NumberFormatException ex)
               {
                  super.setBackground(Color.red);
               }
         }
         if (type == ODD)
         {
            try 
            {
               int value = Integer.parseInt(superGetText());
               if (value % 2 == 0) 
                  super.setBackground(Color.red);
            }
               catch (NumberFormatException ex)
               {
                  super.setText(string);
               }
         }
         if (type == EVEN)
         {
            try 
            {
               int value = Integer.parseInt(superGetText());
               if (value % 2 != 0) 
                  super.setBackground(Color.red);
            }
               catch (NumberFormatException ex)
               {
                  super.setText(string);
               }
         }
      
      }
   
   } // ModulecoTextField
