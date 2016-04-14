 /* Source File Name:   discreteChoice.World.java
 * Copyright:    Copyright (c)enst-bretagne
 * @author : Denis.Phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

   package models.discreteChoice ;
   import modulecoFramework.modeleco.SimulationControl;
   import modulecoGUI.grapheco.CentralControl;
   import modulecoGUI.grapheco.CBufferedCanvas;
   //import modulecoFramework.modeleco.EWorld;

   public class Autorun {
      //protected EWorld eWorld ;
      protected World world ;
      protected CentralControl centralControl;
      protected SimulationControl simulationControl;
      public Autorun(CentralControl control){
         //System.out.println("discreteChoice is Autorun !");
         centralControl =control;
      }
   
   /*
      public void setEWorld(EWorld world){
      
         eWorld = world;
      }   
   	*/           
      public void setWorld(World w){
         //System.out.println("discreteChoice.Autorun.setWorld()");
         world = w;
      }
   
      public void restart(){
      
         //long seed2 = world.getSeed2();
         //seed2=seed2+5;
         //world.setSeed2(seed2);
         //System.out.println("Seed2 : "+seed2);
         world.descriptors=world.getDescriptors();
         simulationControl = world.simulationControl;
         //centralControl.simulationStop();
         simulationControl.stop();
      
         ((CBufferedCanvas)centralControl.getEPanel().leftRepresentation).setDrawAble(false);
      
         //centralControl.updatePermanentControlPanelBar(); //DP 20/08/2002
         /*
         try {java.lang.Thread.sleep(25000L);
         }
            catch (InterruptedException e){
            } 
      		*/
         centralControl.getEPanel().getControlPanel().CreateWorld();
         //((CBufferedCanvas)centralControl.getEPanel().leftRepresentation).setDrawAble(true);
         //centralControl.getEPanel().getControlPanel().getPermanentControlPanelBar().buttonStart.setEnabled(true);
         //centralControl.simulationStart();
         simulationControl.start();
         centralControl.getEPanel().getControlPanel().getPermanentControlPanelBar().buttonCreate.setEnabled(false);
         centralControl.getEPanel().getControlPanel().getPermanentControlPanelBar().buttonStop.setEnabled(true);
         centralControl.getEPanel().getControlPanel().getPermanentControlPanelBar().buttonStart.setEnabled(false);
         centralControl.getEPanel().getControlPanel().getPermanentControlPanelBar().buttonMStep.setEnabled(false);
      
      }
   }
