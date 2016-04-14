/**
 * Title:        CPanel<p>
 * Description:  Je suis une classe abstraite <p>
 * qui supporte les contraintes de mise page d'un Panel<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Denis.Phan@enst-bretagne.fr
 * @version 1.3  august,5, 2003
 */

   package modulecoGUI.grapheco;

   import java.awt.GridBagConstraints;
   import java.awt.GridBagLayout;
   import java.awt.Component;
   import java.awt.Color;
   import java.awt.Font;

   import javax.swing.JComponent;
   import javax.swing.JPanel;
   import java.awt.Dimension;


/**
 * This abstract class can be inherited to have a panel with a layout easy
 * to manage.
 */
   public abstract class CPanel extends JPanel
   {
      protected GridBagConstraints constraints;
      protected GridBagLayout gridBagLayout;
   
      protected Font font = new Font("Dialog", 0, 12);
      protected Font smallfont = new Font("Dialog", 0, 11);
      protected Color colorBackground = Color.lightGray ;
      protected Color colorForeground = Color.black ;
   
      protected Dimension dimension;
   
      public  CPanel()
      {
         setForeground(colorForeground);
         setFont(font);
      
         gridBagLayout = new GridBagLayout();
         setLayout(gridBagLayout);
         constraints = new GridBagConstraints();
         constraints.weightx=1.0;
         constraints.weighty=1.0;
         constraints.anchor = GridBagConstraints.WEST;
         constraints.fill=GridBagConstraints.BOTH;
      }
   
   
      public void add(Component c, int x, int y, int wd, int hg)
      {
      
         constraints.gridx = x;
         constraints.gridy = y;
         constraints.gridwidth = wd;
         constraints.gridheight = hg;
         //setLayer(c, PALETTE_LAYER.intValue());
         add(c, constraints);
      }
      public void addLayered(JComponent c, int x, int y, int wd, int hg,  int layer)
      {
         //setLayer(c, layer);
         add((Component) c, x, y, wd, hg);
      }
      /*
       public void addInternalFrame(Component c, int x, int y, int wd, int hg)
       desktop.add(c); //javax.swing.JLayeredPane.DEFAULT_LAYER);  
       c.setVisible( true );
         //c.setSize(d);
         //c.setLocation (0, 0);
       add((Component) c, x, y, wd, hg)
      {
   */
      public GridBagConstraints getConstraints(){
         return constraints;
      }
      /*
      public void paint(java.awt.Graphics g)
      {
         g.setColor(java.awt.Color.lightGray);
         g.fill3DRect(0, 0, getSize().width - 1, getSize().height - 1, true);
         super. paint(g);
      }
       */
   }
