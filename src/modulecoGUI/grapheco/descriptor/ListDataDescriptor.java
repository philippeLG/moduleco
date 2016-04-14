/**
 * Title:        Moduleco<p>
 * Description:  Je suis une classe qui représente des donnees de type "integer" <p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author sebastien.chivoret@ensta.org
 * created on may, 11, 2001
 * @version 1.2  august,5, 2002
 */
   package modulecoGUI.grapheco.descriptor;

   import java.util.ArrayList; //depuis JDK 1.2

   //import java.awt.Container;
   import javax.swing.JPanel;
// import javax.swing.JLabel;
// import javax.swing.JTextField ;
// import java.awt.Color;
// import java.awt.Font;
   import java.awt.GridLayout;

   import modulecoFramework.modeleco.CAgent;
   import java.io.PrintWriter; //CK 04/02

   public class ListDataDescriptor extends DataDescriptor 
   {
      protected int value;			//the value of the data
      protected int min;			//if there is a minimum value acceptable
      protected int max;			//if there is a maximum value acceptable
      protected DataDescriptor dd;
      protected DataDescriptor[] dlist;
   
      public ListDataDescriptor(CAgent ag, String cn, String n, int v, boolean e, DataDescriptor d)
      {
         agent=ag;
         completeName=cn;
         name=n;
         value=v;
         this.min=Integer.MIN_VALUE;					
         this.max=Integer.MAX_VALUE;					
         editable=e;
         dlist=new DataDescriptor[value];
         dd=d;
      }
   
      public int getValue(){
         return value;
      }
   
      public void setValue(int i){
         value=i;
      }
   
      public void makeContainer(ArrayList containerList){	
         container.setLayout(new GridLayout(value+1,1));
      
         for (int i=0;i<value;i++){     
         
               //dlist[i]=(DataDescriptor)dd.getClass().newInstance();
            dlist[i]=dd.getClone();
         
               /*catch (java.lang.InstantiationException e){
                  System.out.println("ouille"+e.toString());}
               catch (java.lang.IllegalAccessException e){
               
                  System.out.println("ouille"+e.toString());}*/
         
            JPanel cont=dlist[i].buildContainer(containerList);	
            container.add(cont);
         }
      	//container.add(new Label("ca va?"));
         //container.validate();
      }
   
      public void updateContainer(JPanel container)
      {
         for (int i=0;i<value;i++){
            //dlist[i].updateContainer(container);		//meme erreur 08.06.01
         }
      }
      public void set(){
      }
   
      public void printCVS(PrintWriter printWriter) {
         printWriter.print(value);
         printWriter.print(',');
      }
      public void printTxt(PrintWriter printWriter) {
         printWriter.print(value+" ");
      }
   }