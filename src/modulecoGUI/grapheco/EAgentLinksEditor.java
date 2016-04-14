/** Title:      modulecoGUI.grapheco.EAgentLinksEditor extends EAgent<p>
 * * Copyright:    Copyright (c)enst-bretagne
 * @author Denis.Phan@enst-bretagne.fr
 * @version 1.3  october,15, 2003  
 */

   package modulecoGUI.grapheco;

   import modulecoFramework.modeleco.CAgent;
   import modulecoFramework.modeleco.EAgentLinks;
// import modulecoFramework.modeleco.EWorld;

// import java.awt.Container;
   import javax.swing.JPanel;
   import java.awt.Component;
// import java.awt.Color;
// import java.awt.Font;
// import java.awt.Label;


   import java.util.ArrayList;
// import java.util.Iterator;

   //import java.awt.Frame;

   import modulecoGUI.grapheco.descriptor.DataDescriptor; // Revision 1 july 28 2001 DP

// import java.lang.Math;
// import java.math.BigDecimal;


// import java.awt.Graphics;

   public class EAgentLinksEditor extends EAgentEditor
   {
      public EAgentLinks ag;
      protected ArrayList linksDescriptors;
      int containerSize;
      JPanel container;
      
      public EAgentLinksEditor(CAgent ea){
         super(ea);   
         // invoqued by a CAgent : Eworld, EAgent...
         //System.out.println("EAgentLinksEditor");
         this.ag = (EAgentLinks) ea;
      }
   
      public void build(int col){ 	// invoked by modulecoFramework.modeleco.EAgent.java			
         columns=col;
         this.removeAll();
         linksDescriptors = ag.getLinksDescriptors();		//the editor gets the datas from the agent
         containerSize = linksDescriptors.size();
         if (containerSize!=0){
         
            for (int i=0;i<containerSize;i++){     
               container=((DataDescriptor) linksDescriptors.get(i)).buildContainer(containerList);
               add((Component)container,i%columns,line,1,1);
               number++;
               if (i%columns==columns-1) line++;
            }
         
            if (containerSize%columns!=0){
               for (int k=0;k<columns-containerSize%columns;k++){
                  add((Component)new JPanel(), containerSize%columns+k,line,1,1);
                  number++;
               }
               line++;
            }
            validate();
         }
      }
   
   
   
      public void update(){		//method called by the edited agent (setDataToInterface)
         System.out.println("EALE updating "+ag.getClass().getName());
         linksDescriptors=ag.getLinksDescriptors();//the data are updated
         if (linksDescriptors.size()>containerSize){
            //System.out.println("EALE updating : container update DEBUT - linksDescriptors.size() = "+linksDescriptors.size()+" containerSize = "+containerSize);
            container=((DataDescriptor) linksDescriptors.get(containerSize)).buildContainer(containerList); 
            //System.out.println("EALE updating : FIN container");
            add((Component)container,(linksDescriptors.size())%columns,containerSize+1,1,1);
            //System.out.println("EALE updating : FIN add.container");
            containerSize=containerSize+1;
            //System.out.println("EALE updating : container update FIN - linksDescriptors.size() = "+linksDescriptors.size()+" containerSize = "+containerSize);
            validate();
         }
      		// verfier ce qui se passe avec col
      
         //System.out.println("update() / linksDescriptors.size() = "+linksDescriptors.size()+" containerSize = "+containerSize);
         for (int i=0;i<linksDescriptors.size();i++){ 
            ((DataDescriptor) linksDescriptors.get(i)).updateContainer((JPanel)containerList.get(i));
         }
      }
   
   /*
      public int getLine(){
      
         return line;
      }
   
      public int getColumns(){
      
         return columns;
      }
   
      public void exitForm(java.awt.event.WindowEvent evt){
         ag.closed();
      }
   */
   
   }