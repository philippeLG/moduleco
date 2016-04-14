/**
 * Title:        EDialog<p>
 * Description: Je permets de demander une valeur à la fois à l'utilisateur.
 * Cette valeur est disponible grace à la méthode getValue().
 * Une séquence d'utilisation classique est :
 * <code>
 * EDialog ed = new EDialog("Donnée ?", "Contrainte", "Defaut");
 * ed.setVisible(true);
 * if (ed.getValue != null) { do something }
 * </code>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr
 * REVISED BY Denis.@enst-bretagne.fr july 2003
 * VOIR DOUBLONS AVEC EWarningDialog.java
 * created on may, 2000
 * @version 1.2  august,5, 2002
 */

   package modulecoGUI.grapheco;

   import javax.swing.JLabel;
   import javax.swing.JDialog;
   import javax.swing.JTextField;
   import javax.swing.JButton;

   public class EDialog extends JDialog 
   //utilisé par modulecoFramework.modeleco.ZoneSelector.BoundedRandom
   {
   // Variables declaration - do not modify
      private JLabel label1,label2;
      private JTextField textField1;
      private JButton button1, button2;
      private String n, c, v, value;
   // End of variables declaration
      public EDialog(String name, String contr, String defaultValue) 
      {
         super (new java.awt.Frame(), true);
         n = name; c = contr; v = defaultValue;
         initComponents ();
         pack ();
      }
   
      private void initComponents () 
      {
         label1 = new JLabel ();
         label2 = new JLabel ();
         textField1 = new JTextField ();
         button1 = new JButton ();
         button2 = new JButton ();
         setLayout (new java.awt.BorderLayout ());
         setResizable (false);
         setModal (true);
         addWindowListener (
                              new java.awt.event.WindowAdapter () 
                              {
                                 public void windowClosing (java.awt.event.WindowEvent evt) {
                                    closeDialog ();
                                 }
                              }
                           );
      
         label1.setBackground (new java.awt.Color (204, 204, 204));
         label1.setText (n);
         label1.setForeground (java.awt.Color.black);
         label1.setHorizontalAlignment (JLabel.CENTER);
         label1.setFont (new java.awt.Font ("Dialog", 0, 14));
      
      
         add (label1, java.awt.BorderLayout.NORTH);
      
         label2.setBackground (new java.awt.Color (204, 204, 204));
         label2.setText (c);
         label2.setForeground (java.awt.Color.black);
         label2.setFont (new java.awt.Font ("Dialog", 0, 11));
      
      
         add (label2, java.awt.BorderLayout.SOUTH);
      
         textField1.setBackground (new java.awt.Color (204, 204, 204));
         textField1.setText (v);
         textField1.setForeground (java.awt.Color.black);
         textField1.setFont (new java.awt.Font ("Dialog", 0, 11));
      
      
         add (textField1, java.awt.BorderLayout.CENTER);
      
         button1.setBackground (new java.awt.Color (204, 204, 204));
         button1.setForeground (java.awt.Color.black);
         button1.setText("OK");
         button1.setFont (new java.awt.Font ("Dialog", 0, 11));
         button1.addActionListener (
                                 new java.awt.event.ActionListener () 
                                 {
                                    public void actionPerformed (java.awt.event.ActionEvent evt) {
                                       okActionPerformed (evt);
                                    }
                                 }
                              );
      
         add (button1, java.awt.BorderLayout.EAST);
      
         button2.setBackground (new java.awt.Color (204, 204, 204));
         button2.setForeground (java.awt.Color.black);
         button2.setText ("cancel");
         button2.setFont (new java.awt.Font ("Dialog", 0, 11));
         button2.addActionListener (
                                 new java.awt.event.ActionListener () 
                                 {
                                    public void actionPerformed (java.awt.event.ActionEvent evt) {
                                       cancelActionPerformed (evt);
                                    }
                                 }
                              );
      
      
         add (button2, java.awt.BorderLayout.WEST);
      
      }
   
      protected void okActionPerformed (java.awt.event.ActionEvent evt) 
      {
         value = textField1.getText();
         closeDialog();
      }
   
      protected void cancelActionPerformed (java.awt.event.ActionEvent evt) 
      {
         value = null;
         closeDialog();
      }
   
   /** Closes the dialog */
      protected void closeDialog() 
      {
         setVisible (false);
         dispose ();
      }
   
      public String getValue() 
      {
         return value;
      }
   
   
   }
