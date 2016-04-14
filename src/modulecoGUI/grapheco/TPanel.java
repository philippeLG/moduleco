/*
 * TPanel.java
 *
 * Created on 7 août 2003, 17:07
 */

   package modulecoGUI.grapheco;

// import java.awt.Graphics;
// import java.awt.Dimension;
// import java.awt.Color;
   import java.awt.GridBagConstraints;
   import java.awt.Component;
/**
 *
 * @author  phan1
 */
   public class TPanel extends CPanel {
   
    /** Creates a new instance of TPanel */
      public TPanel() {
         initComponents();
      }
   
      public void    initComponents(){
         javax.swing.JPanel testPanel = new javax.swing.JPanel();
         testPanel.setBackground(java.awt.Color.blue);
         constraints.anchor = GridBagConstraints.NORTH;
         this.add((Component)testPanel,0, 1, 1, 1); 
          //testPanel.setVisible( true );
          //Dimension dim = new Dimension(200, 200);
          //testPanel.setSize (dim);
         javax.swing.JPanel testPanel2 = new javax.swing.JPanel();
         testPanel2.setBackground(java.awt.Color.red);
         constraints.anchor = GridBagConstraints.NORTH;
         this.add((Component)testPanel2,1, 1, 1, 1); 
      
        //javax.swing.JPanel testPanel = new javax.swing.JPanel();
        //testPanel.setBackground(java.awt.Color.blue);
        //getContentPane().add(testPanel, BorderLayout.CENTER);
      }
   /*
   javax.swing.JPanel tePanel = new javax.swing.JPanel();
         testPanel.setBackground(java.awt.Color.blue);
         constraints.anchor = GridBagConstraints.NORTH;
          this.add((Component)testPanel,1, 1, 1, 1); // javax.swing.JLayeredPane.DEFAULT_LAYER);
          testPanel.setVisible( true );
          Dimension dim = new Dimension(3*w/4, h/2);
          testPanel.setSize (dim);
          testPanel.setLayout(new java.awt.GridLayout(1,4));
          javax.swing.JLabel testLabel = new javax.swing.JLabel("TEST");
          testLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
          testLabel.setForeground(Color.blue);
          //testLabel..setVisible(true);
          testPanel.add(testLabel);
          javax.swing.JLabel testLabel2 = new javax.swing.JLabel("TEST");
          testLabel2.setHorizontalAlignment(javax.swing.JLabel.CENTER);
          testLabel2.setForeground(Color.red);
          //testLabel..setVisible(true);
          testPanel.add(testLabel2);
          javax.swing.JTextField textField=new javax.swing.JTextField();
          textField.setText("test");
          testPanel.add(textField);
          modulecoGUI.grapheco.descriptor.InfoDescriptor ID =
          new modulecoGUI.grapheco.descriptor.InfoDescriptor("TESTDESCRIPTOR");
          java.util.ArrayList containerList = new java.util.ArrayList();
          javax.swing.JPanel container =((modulecoGUI.grapheco.descriptor.DataDescriptor) ID).buildContainer(containerList);
          testPanel.add(container);
   */
   
   }
