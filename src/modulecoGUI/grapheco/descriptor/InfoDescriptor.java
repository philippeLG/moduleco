/**
 * Title:        Moduleco<p>
 * Description:  Je suis une classe qui représente des informations <p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author sebastien.chivoret@ensta.org
 * @version 1.2  august,5, 2002
 */
   package modulecoGUI.grapheco.descriptor;

   import java.util.ArrayList; //depuis JDK 1.2
// import java.math.BigDecimal;

   //import java.awt.Container;
   import javax.swing.JPanel;
   import javax.swing.JLabel;
// import javax.swing.JTextField ;
   import java.awt.Color;
// import java.awt.Font;
// import java.awt.GridLayout;


// import modulecoFramework.modeleco.CAgent;
   import java.io.PrintWriter; //CK 04/02


   public class InfoDescriptor extends DataDescriptor 
   
   {
      protected String title;
      protected String info;
      public InfoDescriptor(String ti ,String i,Color co){
         title=ti;
         info=i;
         this.name = info;
         this.completeName = info;
         labelForegroundColor = co;
      }
   
      public InfoDescriptor(String ti,String i){
         this(ti, i, Color.black);
      }
   
      public InfoDescriptor(String i){
         this("", i, Color.black);
      }
   
      public String getValue()
      {
         return info;
      }
      public InfoDescriptor(String i, Color co){
      
         this("", i , co);
      }
   
   
     // DP 29/06/2001
      public void makeContainer(ArrayList containerList)					
      {	
         super.makeContainer(containerList);
      
         JLabel label2 =new JLabel(info);		
         container.add(label2);
         label.setText(title);
         label.setForeground(labelForegroundColor);
         label2.setForeground(labelForegroundColor);
      }
   
      public void updateContainer(JPanel container){
         ((JLabel)container.getComponent(0)).setText(""+info);
      }	
   
      public void set() {// useless for an output only descriptor
      }      
   
      public void printCVS(PrintWriter printWriter) {
         printWriter.print(info);
         printWriter.print(',');
      
      }      
      public void printTxt(PrintWriter printWriter) {
         printWriter.print(info+" ");
      }
   }