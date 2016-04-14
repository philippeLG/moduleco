/*
 * Class.java
 *
 * Created on 7 août 2003, 21:43
 */

   package modulecoGUI.grapheco;

   import javax.swing.JPanel;
   import modulecoGUI.grapheco.CAgentRepresentation;
   import javax.swing.JLabel;
   import java.awt.Color;
   import java.awt.Font;
   import java.awt.BorderLayout;
/**
 *
 * @author  phan1
 */
   public class IntroCARPanel extends JPanel implements CAgentRepresentation {
	/**
	 * The name of this CAgentRepresentationContainer
	 */
	public String name;
      
      JLabel infoLabel;
    /** Creates a new instance of Class */
      public IntroCARPanel() {
         initComponent("Moduleco");
      }
      public IntroCARPanel(String s) {
         initComponent(s);
      }
   
      public void initComponent(String s){
         int r = 150;
         int g = 150;
         int b = 200;
         this.setBackground(new Color(r,g,b));
         infoLabel = new JLabel(s);
         infoLabel.setFont(new Font("Dialog",Font.BOLD,16));
         infoLabel.setHorizontalAlignment(JLabel.CENTER);
         infoLabel.setVerticalAlignment(JLabel.CENTER);
         infoLabel.setHorizontalAlignment(JLabel.CENTER);
         infoLabel.setForeground(Color.blue.darker().darker());
         this.add(infoLabel,BorderLayout.CENTER);  
      }
   
      public void resetImage() {
      }
   
      public void setCAgent(modulecoFramework.modeleco.CAgent cAgent) {
      }
   
      public void setCentralControl(CentralControl centralControl) {
      }
   
      public void updateImage() {
      }
	public String getName() {
		if (name != null)
			return name;
		else
			return null;
	}

	public void setName(String s) {
		this.name = s;
	}
   }
