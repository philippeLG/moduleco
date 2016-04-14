// Source File Name:   modulecoGUI.grapheco.XRepresentations.java
/** @author denis.phan@enst-bretagne.fr
* july 2001 (new class added 28 october 2001 DP)
* @version 1.2  august,5, 2002
*/


   package modulecoGUI.grapheco;

// import java.awt.Canvas;
// import java.awt.FileDialog;
// import java.awt.Color;
   import java.awt.GridBagConstraints;

   import javax.swing.JFileChooser;

   import modulecoFramework.modeleco.EWorld;
   import modulecoFramework.modeleco.CAgent;

	/**
 * This class by inheritance of CAgentRepresentationContainer, is a container of CAgentRepresentation,
 * and a CAgentRepresentation itself.
 * Each time an operation will be made on the container, the same one will be done
 * on each CAgentRepresentation contained by it.
 */
   public class XRepresentations extends CAgentRepresentationContainer
   {
   
      protected CAgentRepresentation[] aRepresentation ;
      protected CentralControl centralControl;
      protected int numComponent;
      
      public XRepresentations(){
         aRepresentation = new CAgentRepresentation[4] ;
         for(int i = 0 ; i<4; i++){
            aRepresentation[i]  = new ImageENSTB();
            addCAgentRepresentation(aRepresentation[i]);
         }
         initComponents();
         //System.out.println("XRepresentations.constructor");
      }
   
      protected void initComponents(){
      
         constraints.weighty = 1.0;
         //rightRepresentation.setSize((int)((double)getSize().width * .5), (int)((double)getSize().height * .5));
         //constraints.anchor = GridBagConstraints.NORTH;
         for(int i = 0 ; i<4; i++){
            add((java.awt.Component)aRepresentation[i],(i<2?0:1),i%2, 1, 1);
         }
      }
   
      public void setXRepresentation(CAgentRepresentation cAgentRepresentation, int numComponent)
      {
      // instancié par getPreferred<X>Representation()
         this.numComponent=numComponent;
         remove((java.awt.Component)aRepresentation[numComponent]);
      
         aRepresentation[numComponent] = cAgentRepresentation;
         //rightRepresentation.setSize((int)((double)getSize().width * .5),  getSize().height - controlPanel.getMinimumSize().height);
      
         constraints.fill = GridBagConstraints.BOTH;
         constraints.weighty = 1.0;
         this.add((java.awt.Component)aRepresentation[numComponent], (numComponent<2?0:1),numComponent%2,1,1);
         validate();
         //System.out.println("XRepresentation.setRepresentation( "+numComponent+" )");
      }
   
      public void setCAgent(CAgent cAgent)
      {
         EWorld eWorld = (EWorld)cAgent;
      
         for(int i = 0 ; i<4; i++)
         {
            setXRepresentation(centralControl.loadCAgentRepresentationClass(centralControl.getGraphicsSelector().getXRepresentationsComponents(i)),i);
            aRepresentation[i].setCAgent(cAgent);
            constraints.weighty = 0.0;
            validate();
         }
      }
      public int getNumComponent(){
         return numComponent;
      }
      public void updateImage(){
         for(int i = 0 ; i<4; i++){
            aRepresentation[i].updateImage();
            //System.out.println("XRepresentation.updateImage()");
         }
      
      }
   
   
      public JFileChooser getJFileChooser(){
      //if (fileDialog == null)
           // fileDialog = new FileDialog((Frame)ePanel.getParent());
      
         return null; //fileDialog;
      }
      public void setCentralControl(CentralControl centralControl)
      {
         this.centralControl = centralControl;
      }
   }