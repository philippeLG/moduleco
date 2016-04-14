/*
 * AgentParameter.java
 *
 * Created on 25 avril 2002, 15:16
 */

   package modulecoGUI.Traces.grapheco;

/**
 *
 * @author  infolab
 * @version 
 */
   import javax.swing.JPanel;
   import javax.swing.JList;
   import javax.swing.JRadioButton;
   import javax.swing.border.TitledBorder;
   import javax.swing.JScrollPane;
   import javax.swing.JButton;
   import java.awt.event.ActionEvent;
   import java.awt.event.ActionListener;
   import javax.swing.JFrame;

   import java.util.Vector;
   import java.util.ArrayList;
   import java.util.Iterator;
   import modulecoFramework.modeleco.EWorld;
   import modulecoFramework.modeleco.EAgent;

   public class AgentFrame extends JFrame implements ActionListener{
   
      protected JList agentList,selectedList;
      protected ArrayList agentSelected;
      protected EWorld eWorld;
      protected JPanel agentParameterPanel;
      protected JRadioButton allAgents;
      protected JButton aAdd,aAddAll, aRemove, aRemoveAll;
      protected JButton ok, cancel;
      protected AgentParameter agentParameter;
   
    /** Creates new AgentParameter */
      public AgentFrame(EWorld eWorld,AgentParameter agentParameter) {
         super ("Agents");
         this.eWorld = eWorld;
         this.agentParameter = agentParameter;
         agentSelected = new ArrayList();
         initComponents();
         setSize (260,350);
         show();
      }
   
      public void initComponents()
      {
         buildAgentPanel();
         buildButtonPanel();
      }
   
      public void buildAgentPanel()
      {
         agentParameterPanel = new JPanel();
         agentParameterPanel.setLayout(null);
         agentParameterPanel.setBorder(new TitledBorder("Agents"));
      
         allAgents = new JRadioButton("All");
         allAgents.setSelected(true);
         allAgents.setBounds(10, 20, 39, 25);
         agentParameterPanel.add(allAgents);
      
         agentList = new JList();
         JScrollPane scroll = new JScrollPane(agentList);
         scroll.setBounds(10, 50,80, 180);
         agentParameterPanel.add(scroll);
      
         selectedList = new JList();
         JScrollPane scroll1 = new JScrollPane(selectedList);
         scroll1.setBounds(160, 50,80, 180);
         agentParameterPanel.add(scroll1);
      
         Vector agentids = new Vector();
         for (Iterator i = this.eWorld.iterator();i.hasNext();)
            agentids.addElement(new Integer(((EAgent)i.next()).agentID));
         agentList.setListData(agentids);
      
         initSelectedList();
      
         aAdd = new JButton(">");
         aAdd.addActionListener(this);
         aAdd.setBounds(100, 80, 50, 27);
         aAddAll = new JButton(">>");
         aAddAll.addActionListener(this);
         aAddAll.setBounds(100, 110,50, 27);
         aRemove = new JButton("<");
         aRemove.addActionListener(this);
         aRemove.setBounds(100, 140,50, 27);
         aRemoveAll = new JButton("<<");
         aRemoveAll.addActionListener(this);
         aRemoveAll.setBounds(100, 170,50, 27);
         agentParameterPanel.add(aAdd);
         agentParameterPanel.add(aAddAll);
         agentParameterPanel.add(aRemove);
         agentParameterPanel.add(aRemoveAll);
      
         agentParameterPanel.setBounds(0,10,250,250);
         getContentPane().setLayout(null);
         getContentPane().add(agentParameterPanel);
      }
      public void buildButtonPanel()
      {
         ok = new JButton("Ok");
         ok.addActionListener(this);
         cancel = new JButton ("Cancel");
         cancel.addActionListener(this);
         JPanel buttonPanel = new JPanel();
         buttonPanel.setBorder(new TitledBorder(""));
         buttonPanel.setLayout(null);
         ok.setBounds(20, 10, 80, 27);
         cancel.setBounds(100,10,80,27);
         buttonPanel.add(ok);
         buttonPanel.add(cancel);
         getContentPane().add(buttonPanel);
         buttonPanel.setBounds(20,270,200,50);
      }
   
      public void initSelectedList()
      {
         Vector v = new Vector();
         for (Iterator i = agentParameter.getSelectedAgents().iterator();i.hasNext();)
            v.addElement(i.next());
         selectedList.setListData(v);
      }
   
      public JPanel getPanel()
      {
         return this.agentParameterPanel;
      }
   
      public void refresh(EWorld eWorld, AgentParameter agentParameter)
      {
         this.eWorld = eWorld;
         this.agentParameter =agentParameter;
         selectedList.setListData(new Vector());
         Vector agentids = new Vector();
         for (Iterator i = this.eWorld.iterator();i.hasNext();)
            agentids.addElement(new Integer(((EAgent)i.next()).agentID));
         agentList.setListData(agentids);
         initSelectedList();
      }
   
      public void variableSelected(JList listInit, JList listEnd)
      {
         Object[] initSelectedValues = listInit.getSelectedValues();
         Vector initValues = getListValues(listInit);
         Vector endValues = getListValues(listEnd);
      
         for (int i=0; i < initSelectedValues.length; i++)
         {   
            if (!endValues.contains(initSelectedValues[i]))
            {     endValues.addElement(initSelectedValues[i]);}
         
            if (initValues.contains(initSelectedValues[i]))
            {    initValues.remove(initSelectedValues[i]);}
         }
      
         listInit.setListData(initValues);
         listEnd.setListData(endValues);
      
      }
   
      public Vector getListValues(JList list)
      {
         Vector values = new Vector();
         if (list.getModel().getSize() > 0)
         {
            int index = list.getSelectedIndex();
            for(int i = 0; i < list.getModel().getSize(); i++) 
            {
               list.setSelectedIndex(i);
               values.addElement(list.getSelectedValue());
            }
            list.setSelectedIndex(index);
         }
         return values;
      }
      public ArrayList getSelectedAgentIds()
      {
         ArrayList selectedAgent = new ArrayList();
         Vector v = getListValues(selectedList);
         for (Iterator i = v.iterator(); i.hasNext();)
            selectedAgent.add((Integer)i.next());
         return selectedAgent;
      }
   
      public void getSelectedAgents()
      {
         agentParameter.setAgents(this.getSelectedAgentIds());
      }
      public void variableAllSelected(JList listInit, JList listEnd)
      {
         Vector initValues = getListValues(listInit);
         Vector endValues = getListValues(listEnd);
      
         for (int i=0; i < initValues.size(); i++)
         {
            if (!endValues.contains(initValues.get(i)))
               endValues.addElement(initValues.get(i)); 
         }
         initValues.clear();     
         listInit.setListData(initValues);
         listEnd.setListData(endValues);
      
      }
   
      public void actionPerformed(ActionEvent actionEvent) {
         if (actionEvent.getSource().equals(aAdd))
         {  variableSelected(agentList,selectedList); 
            return;}
         if (actionEvent.getSource().equals(aAddAll))
         { variableAllSelected( agentList,selectedList); 
            return;}
         if (actionEvent.getSource().equals(aRemove))
         { variableSelected (selectedList, agentList);  
            return;}
         if (actionEvent.getSource().equals(aRemoveAll))
         { variableAllSelected (selectedList, agentList);  
            return;}
         if (actionEvent.getSource().equals(ok))
         {
            getSelectedAgents();
            dispose();	
         }
         if (actionEvent.getSource().equals(cancel))
         {
            dispose();
         }
      }
   
   }
