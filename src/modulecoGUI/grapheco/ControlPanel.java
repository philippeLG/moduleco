/**
 * ControlPanel.java
 * Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, denis.phan@enst-bretagne.fr
 * @version 1.2  August 2002
 * @version 1.4  February 2004
 * @version 1.5  June 2004
 */
package modulecoGUI.grapheco;
import java.util.Arrays;
import java.io.File;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import modulecoFramework.utils.ExistingModels;
import modulecoFramework.utils.ExistingNeighbourhoods;
import modulecoGUI.help.BrowserControl;
/**
 * Manage the GUI.
 */
public class ControlPanel extends CPanel
		implements
			ItemListener,
			ActionListener {
	protected CentralControl centralControl;
	protected PermanentControlPanelBar permanentControlPanelBar;
	protected EWorldEditor eWorldEditor;
	protected ChoiceWorldPanel choiceWorldPanel;
	protected ChoiceNeighbourPanel choiceNeighbourPanel;
	protected ChoiceZonePanel choiceZonePanel;
	protected ExistingModels modelsList;
	protected ExistingNeighbourhoods neighbourhoodsList;
	//protected CPIterations labelIter;
	protected JMenuBar menuBar;
	public JMenuItem menuItemLoad, menuItemSave, menuItemPrintStats,
			menuItemPrintWindow, menuItemAboutThisWorld, menuItemJavaDoc,
			menuItemModulecoHomePage, menuItemAboutModuleco, menuItemModeles,
			menuExit;
	public JCheckBoxMenuItem checkWhereWorldEditor, checkMenuDisplayXGraphics,
			checkMenuRecord, checkMenuRecordQuick;
	protected JCheckBoxMenuItem checkMenuWorldAlone, checkMenuFourWorld,
			checkMenuEarlyScheduler, checkMenuLateScheduler,
			checkMenuExtraAgentScheduler, checkMenuDisplayTorusWorld,
			checkMenuDisplayCircleWorld, checkMenuDisplayGraphic, checkMenuYY,
			checkMenuDisplayWorldSelection, checkMenuDisplayNeighbour,
			checkMenuDisplayZone, checkMenuStatManagerEnabled, checkMenuTest,
			checkWorldEditor;
	protected JCheckBoxMenuItem checkMenuWorld[], checkMenuNeighbour[],
			checkMenuZone[];
	protected ExtendedButtonGroup neighbourGroup, worldGroup, zoneGroup;
	/**
	 * The list of available worlds and neighbourhoods
	 */
	protected String[] worldName, neighbourName;
	/**
	 * Current world, neighbourhood and zone
	 */
	protected String worldSelected, previousWorldSelected, neighbourSelected,
			zoneSelected;
	protected int noModels, noNeighbourhoods, worldSelectIndex,
			neighbourSelectIndex, zoneSelectIndex;
	/**
	 * Static variables
	 */
	private static String modulecoPathRoot = CentralControl
			.getModulecoPathRoot();
	private static String webResources = CentralControl.getWebResources();
	/**
	 * maximum size for dispay world as a circle
	 */
	private int maxCircleSize = 10;
	/**
	 * Constructor
	 * 
	 * @param centralControl
	 */
	public ControlPanel(CentralControl centralControl) {
		this.centralControl = centralControl;
		/**
		 * Get from the JAR file the list of existing models.
		 */
		previousWorldSelected = null;
		modelsList = new ExistingModels();
		noModels = modelsList.getNoModels();
		worldName = new String[noModels + 1];
		worldName[0] = "No Model Selected";
		for (int i = 1; i < (noModels + 1); i++)
			worldName[i] = modelsList.getName(i - 1);
		/**
		 * Sort the list of available Worlds
		 */
		Arrays.sort(worldName, 1, noModels + 1);
		/**
		 * Get from the JAR file the list of existing neighbourhoods.
		 */
		neighbourhoodsList = new ExistingNeighbourhoods();
		noNeighbourhoods = neighbourhoodsList.getNoNeighbourhoods();
		neighbourName = new String[noNeighbourhoods + 2];
		for (int i = 0; i < noNeighbourhoods; i++)
			neighbourName[i] = neighbourhoodsList.getName(i);
		/**
		 * Fill the 3 dropdown lists world, neighbour and zone.
		 */
		checkMenuWorld = new JCheckBoxMenuItem[noModels + 1];
		checkMenuNeighbour = new JCheckBoxMenuItem[noNeighbourhoods + 1];
		checkMenuZone = new JCheckBoxMenuItem[noNeighbourhoods + 1];
		/**
		 * Build the Option Bar, containing the dropdown lists, etc.
		 */
		BuildOptionBar();
		/**
		 * Build the Menu Bar, i.e. the main bar at the top.
		 */
		menuBar = new JMenuBar();
		BuildMenuFile();
		BuildMenuWorld();
		BuildMenuNeighbour();
		BuildMenuZone();
		BuildMenuScheduler();
		BuildMenuDisplay();
		//BuildMenuStatManager();
		BuildMenuData();
		BuildMenuHelp();
		/**
		 * Disable irrelevant buttons and sub-menus, as long as no world is
		 * selected.
		 */
		enableButtons(false);
	}
	//	************************************************************************
	//	Build Menus
	//	************************************************************************
	/**
	 * Build the option bar in the top panel, containing the buttons "Create",
	 * "Start", "Stop" and "MStep", the world size, time step and the drop down
	 * lists
	 */
	protected void BuildOptionBar() {
		/**
		 * Objects are pushed to the bottom left (SOUTHWEST)
		 */
		constraints.anchor = GridBagConstraints.SOUTHWEST;
		/**
		 * The margins separating objects (top, left, botom, right)
		 */
		constraints.insets = new java.awt.Insets(1, 1, 1, 1);
		/**
		 * Build the 4 buttons and the world size
		 */
		constraints.fill = GridBagConstraints.NONE;
		permanentControlPanelBar = new PermanentControlPanelBar(centralControl,
				this);
		add(permanentControlPanelBar, 0, 0, 1, 1);
		/**
		 * Ojects are resized horizontally (HORIZONTAL)
		 */
		constraints.fill = GridBagConstraints.HORIZONTAL;
		/**
		 * Build the drop down list of the "World"
		 */
		choiceWorldPanel = new ChoiceWorldPanel(this, permanentControlPanelBar);
		add(choiceWorldPanel, 4, 0, 1, 1);
		/**
		 * Build the drop down list of the "Neighbour"
		 */
		choiceNeighbourPanel = new ChoiceNeighbourPanel(this);
		choiceNeighbourPanel.setVisible(false);
		add(choiceNeighbourPanel, 3, 0, 1, 1);
		/**
		 * Build the drop down list of the "Zone"
		 */
		choiceZonePanel = new ChoiceZonePanel(this);
		choiceZonePanel.setVisible(false);
		add(choiceZonePanel, 2, 0, 1, 1);
	}
	/**
	 * Build the top menu "File"
	 *  
	 */
	protected void BuildMenuFile() {
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		/**
		 * Load World
		 */
		menuItemLoad = new JMenuItem("Load World");
		menuFile.add(menuItemLoad);
		menuItemLoad.addActionListener(this);
		/**
		 * Save World
		 */
		menuItemSave = new JMenuItem("Save World");
		menuFile.add(menuItemSave);
		menuItemSave.addActionListener(this);
		/**
		 * Print Stats
		 */
		menuItemPrintStats = new JMenuItem("Print stats");
		menuFile.add(menuItemPrintStats);
		menuItemPrintStats.addActionListener(this);
		/**
		 * Print Window
		 */
		//menuItemPrintWindow = new JMenuItem("Print Window");
		//menuFile.add(menuItemPrintWindow);
		//menuItemPrintWindow.addActionListener(this);
		/**
		 * Exit Moduleco
		 */
		menuExit = new JMenuItem("Exit Moduleco");
		menuFile.add(menuExit);
		menuExit.addActionListener(this);
	}
	/**
	 * Build the top menu "World"
	 *  
	 */
	protected void BuildMenuWorld() {
		ItemListener worldListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				worldSelectIndex = worldGroup.getSelectedIndex();
				if (worldSelectIndex > 0) {
					worldSelected = worldGroup.getName();
					menuWorldSelected();
				}
			}
		}; // END ItemListener
		worldGroup = new ExtendedButtonGroup();
		JMenu menuWorld = new JMenu("World");
		/**
		 * Add the menu "world" to the top panel.
		 * <p>
		 * Comment the following line to hide it.
		 */
		//menuBar.add(menuWorld);
		int choiceWorldIndex = choiceWorldPanel.getSelectedIndex();
		for (int i = 0; i < (noModels + 1); i++) {
			boolean testWorldSelected = (choiceWorldIndex == i ? true : false);
			checkMenuWorld[i] = new JCheckBoxMenuItem(worldName[i],
					testWorldSelected);
			menuWorld.add(checkMenuWorld[i]);
			checkMenuWorld[i].addItemListener(worldListener);
			worldGroup.add(checkMenuWorld[i]);
		}
	}
	/**
	 * Build the top menu "Neighbour"
	 *  
	 */
	protected void BuildMenuNeighbour() {
		ItemListener neighbourListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				neighbourSelectIndex = neighbourGroup.getSelectedIndex();
				if (neighbourSelectIndex > 0) {
					neighbourSelected = neighbourGroup.getName();
					MenuNeighbourSelected();
				}
			}
		}; // END ItemListener
		neighbourGroup = new ExtendedButtonGroup();
		JMenu menuNeighbour = new JMenu("Neighbour");
		/**
		 * Add the menu "Neighbour" to the top panel.
		 * <p>
		 * Comment the following line to hide it.
		 */
		//menuBar.add(menuNeighbour);
		int choiceNeighbourIndex = choiceNeighbourPanel.getSelectedIndex();
		for (int i = 0; i < (noNeighbourhoods + 1); i++) {
			boolean testNeighbourSelected = (choiceNeighbourIndex == i
					? true
					: false);
			checkMenuNeighbour[i] = new JCheckBoxMenuItem(neighbourName[i],
					testNeighbourSelected);
			menuNeighbour.add(checkMenuNeighbour[i]);
			checkMenuNeighbour[i].addItemListener(neighbourListener);
			neighbourGroup.add(checkMenuNeighbour[i]);
		}
	}
	/**
	 * Build the top menu "Active Zone"
	 *  
	 */
	protected void BuildMenuZone() {
		/**
		 * Listener
		 */
		ItemListener zoneListener = new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				/**
				 * two events : checkOut, checkIn
				 */
				zoneSelectIndex = zoneGroup.getSelectedIndex();
				if (zoneSelectIndex > 0) {
					zoneSelected = zoneGroup.getName();
					MenuZoneSelected();
				}
			}
		};
		/**
		 * Build the menu items
		 */
		zoneGroup = new ExtendedButtonGroup();
		JMenu menuZone = new JMenu("Active Zone");
		/**
		 * Add the menu "Active Zone" to the top panel.
		 * <p>
		 * Comment the following line to hide it.
		 */
		//menuBar.add(menuZone);
		int choiceZoneIndex = choiceZonePanel.getSelectedIndex();
		for (int i = 0; i < noNeighbourhoods; i++) {
			boolean testZoneSelected = (choiceZoneIndex == i ? true : false);
			checkMenuZone[i] = new JCheckBoxMenuItem(neighbourName[i],
					testZoneSelected);
			menuZone.add(checkMenuZone[i]);
			checkMenuZone[i].addItemListener(zoneListener);
			zoneGroup.add(checkMenuZone[i]);
		}
	}
	/**
	 * Build the top menu "Scheduler"
	 *  
	 */
	protected void BuildMenuScheduler() {
		/**
		 * Add the menu Scheduler
		 */
		JMenu menuScheduler = new JMenu("Scheduler");
		menuBar.add(menuScheduler);
		/**
		 * EarlyCommitScheduler
		 */
		checkMenuEarlyScheduler = new JCheckBoxMenuItem("Early Commit", false);
		menuScheduler.add(checkMenuEarlyScheduler);
		checkMenuEarlyScheduler.addItemListener(this);
		checkMenuEarlyScheduler.setSelected(false);
		/**
		 * LateCommitScheduler
		 */
		checkMenuLateScheduler = new JCheckBoxMenuItem("Late Commit", false);
		menuScheduler.add(checkMenuLateScheduler);
		checkMenuLateScheduler.addItemListener(this);
		checkMenuLateScheduler.setSelected(false);
		/**
		 * ExtraAgentScheduleFirst. To add more than the default
		 * LateCommitScheduler or EarlyCommitScheduler, we need first to change
		 * the way to set the schduler. At the moment, it's a boolean: True for
		 * LateCommitScheduler, False for EarlyCommitScheduler.
		 */
		checkMenuExtraAgentScheduler = new JCheckBoxMenuItem(
				"ExtraAgentcheduleFirst", false);
		menuScheduler.add(checkMenuExtraAgentScheduler);
		checkMenuExtraAgentScheduler.addItemListener(this);
		checkMenuExtraAgentScheduler.setSelected(false);
	}
	/**
	 * Build the top menu "Display"
	 *  
	 */
	protected void BuildMenuDisplay() {
		JMenu menuDisplay = new JMenu("Display");
		menuBar.add(menuDisplay);
		/**
		 * Display or hide the drop-down lists <br>
		 * <ul>
		 * <li>World</li>
		 * <li>Neighbourhood</li>
		 * <li>Active Zone</li>
		 * </ul>
		 */
		/**
		 * World
		 */
		checkMenuDisplayWorldSelection = new JCheckBoxMenuItem(
				"Display World Selection", true);
		//menuDisplay.add(checkMenuDisplayWorldSelection);
		/**
		 * Neighbourhood
		 */
		checkMenuDisplayWorldSelection.addItemListener(this);
		checkMenuDisplayNeighbour = new JCheckBoxMenuItem("Neighbourhood", true);
		menuDisplay.add(checkMenuDisplayNeighbour);
		checkMenuDisplayNeighbour.addActionListener(this);
		checkMenuDisplayNeighbour.setSelected(false);
		/**
		 * Active Zone
		 */
		checkMenuDisplayNeighbour.addItemListener(this);
		checkMenuDisplayZone = new JCheckBoxMenuItem("Active Zone", true);
		menuDisplay.add(checkMenuDisplayZone);
		checkMenuDisplayZone.addItemListener(this);
		checkMenuDisplayZone.setSelected(false);
		/**
		 * Torus, Circle, etc.
		 */
		//Debut Revision DP 12/07/2002
		final ExtendedButtonGroup displayWorldGroup = new ExtendedButtonGroup();
		checkMenuDisplayTorusWorld = new JCheckBoxMenuItem(
				"Display Torus World", true);
		checkMenuDisplayCircleWorld = new JCheckBoxMenuItem(
				"Display Circle World", false);
		displayWorldGroup.add(checkMenuDisplayTorusWorld);
		displayWorldGroup.add(checkMenuDisplayCircleWorld);
		ItemListener displayWorldListener = new ItemListener() {
			//Voire
			public void itemStateChanged(ItemEvent e) {
				// MenuDisplay mise au point DP 14/09 Revise DP 12/07/2002
				if (e.getSource().equals(checkMenuDisplayTorusWorld)) {
					//System.out.println("controlPanel.checkMenuDisplayTorusWorld
					// = "+checkMenuDisplayTorusWorld.getState());
					if (checkMenuDisplayTorusWorld.isSelected()) {
						checkMenuDisplayCircleWorld.setSelected(false);
						setDisplayTorusWorld(); //centralControl.
						//System.out.println("controlPanel.checkMenuDisplayTorusWorld
						// DispalayTorus =
						// "+checkMenuDisplayTorusWorld.getState());
					} else {
						checkMenuDisplayCircleWorld.setSelected(true);
						setDisplayCircleWorld(); //centralControl.
						//System.out.println("controlPanel.checkMenuDisplayTorusWorld
						// - DispalayCircle =
						// "+checkMenuDisplayCircleWorld.getState());
					}
				}
				if (e.getSource().equals(checkMenuDisplayCircleWorld)) {
					//System.out.println("controlPanel.checkMenuDisplayCircleWorld
					// = "+checkMenuDisplayCircleWorld.getState());
					if (checkMenuDisplayCircleWorld.isSelected()) {
						checkMenuDisplayTorusWorld.setSelected(false);
						setDisplayCircleWorld(); //centralControl.
						//System.out.println("controlPanel.checkMenuDisplayCircleWorld
						// - DispalayCircle =
						// "+checkMenuDisplayCircleWorld.getState());
					} else {
						checkMenuDisplayTorusWorld.setSelected(true);
						setDisplayTorusWorld(); //centralControl.
						//System.out.println("controlPanel.checkMenuDisplayCircleWorld
						// DispalayTorus =
						// "+checkMenuDisplayTorusWorld.getState());
					}
				} // fin Revision DP 12/07/2002
			}
		}; // END LISTENER
		menuDisplay.add(checkMenuDisplayTorusWorld);
		checkMenuDisplayTorusWorld.addItemListener(displayWorldListener);
		//centralControl.setDisplayWorld(); mauvais positionnement dans
		// l'initialisation
		menuDisplay.add(checkMenuDisplayCircleWorld);
		checkMenuDisplayCircleWorld.addItemListener(displayWorldListener);
		//Debut Revision DP 12/07/2002
		//checkMenuDisplayGraphic = new JCheckBoxMenuItem("Display Graphic",
		// true);
		//menuDisplay.add(checkMenuDisplayGraphic);
		//checkMenuDisplayGraphic.addItemListener(this);
		//centralControl.setDisplayGraphic(); mauvais positionnement dans
		// l'initialisation
		checkMenuDisplayXGraphics = new JCheckBoxMenuItem(
				"Display X/Single Graphics", false);
		menuDisplay.add(checkMenuDisplayXGraphics);
		checkMenuDisplayXGraphics.addItemListener(this);
		//checkMenuDisplayXGraphics.setEnabled(centralControl.getDisplayXGraphicsEnabled());
		// mauvais positionnement dans l'initialisation
		checkMenuYY = new JCheckBoxMenuItem("Display YY", false);
		menuDisplay.add(checkMenuYY);
		checkMenuYY.addItemListener(this);
		/**
		 * World Editor
		 */
		checkWorldEditor = new JCheckBoxMenuItem("World Editor(to be changed)",
				true);
		//menuDisplay.add(checkWorldEditor);
		checkWorldEditor.addItemListener(this);
		checkWhereWorldEditor = new JCheckBoxMenuItem("World Editor", true);
		//menuDisplay.add(checkWhereWorldEditor);
		checkWhereWorldEditor.addItemListener(this);
	}
	/**
	 * Build the top menu "StatManager"
	 *  
	 */
	protected void BuildMenuStatManager() {
		JMenu menuStatManager = new JMenu("StatManager");
		menuBar.add(menuStatManager);
		checkMenuStatManagerEnabled = new JCheckBoxMenuItem(
				"StatManager Enabled", true);
		menuStatManager.add(checkMenuStatManagerEnabled);
		checkMenuStatManagerEnabled.addItemListener(this);
		centralControl.setStatManagerState(true);
		checkMenuTest = new JCheckBoxMenuItem("TestTemp", false);
		menuStatManager.add(checkMenuTest);
		checkMenuTest.addItemListener(this);
	}
	/**
	 * Build the top menu "Data"
	 *  
	 */
	protected void BuildMenuData() {
		/**
		 * Build the menu itself
		 */
		JMenu menuData = new JMenu("Data");
		menuBar.add(menuData);
		/**
		 * Add the "Record quick" submenu.
		 * <p>
		 * Record quick let you record all your simulation in a file.
		 */
		checkMenuRecordQuick = new JCheckBoxMenuItem("Record quick", false);
		checkMenuRecordQuick.addItemListener(this);
		menuData.add(checkMenuRecordQuick);
		/**
		 * Add the "Record" submenu.
		 * <p>
		 * Record let you record some specific variables of your simulation in a
		 * file.
		 */
		checkMenuRecord = new JCheckBoxMenuItem("Record advanced", false);
		checkMenuRecord.addItemListener(this);
		menuData.add(checkMenuRecord);
		/**
		 * Add the "Graphic" submenu.
		 * <p>
		 * It manages the statManager. If Graphic is off, then Record is off
		 * too, since it also depends on the statManager.
		 * <p>
		 * Switching the statManager to off dramatically improve the performance
		 * in time.
		 */
		checkMenuStatManagerEnabled = new JCheckBoxMenuItem("Graphic", true);
		checkMenuStatManagerEnabled.addItemListener(this);
		menuData.add(checkMenuStatManagerEnabled);
		centralControl.setStatManagerState(true);
		//checkMenuDisplayGraphic = new JCheckBoxMenuItem("Display Graphic",
		// true);
		//checkMenuDisplayGraphic.addItemListener(this);
		//menuData.add(checkMenuDisplayGraphic);
	}
	/**
	 * Build the top menu "Help"
	 *  
	 */
	protected void BuildMenuHelp() {
		/**
		 * Build the menu
		 */
		JMenu menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		/**
		 * Sub-menu "About this world"
		 * <p>
		 * Enabled only if a world is selected.
		 */
		menuItemAboutThisWorld = new JMenuItem("About this World");
		menuItemAboutThisWorld.addActionListener(this);
		menuHelp.add(menuItemAboutThisWorld);
		/**
		 * Sub-menu "Javadoc"
		 */
		menuItemJavaDoc = new JMenuItem("Javadoc"); // ajoute Revision
		menuItemJavaDoc.addActionListener(this);
		menuHelp.add(menuItemJavaDoc);
		/**
		 * Sub-menu "Moduleco HomePage"
		 */
		menuItemModulecoHomePage = new JMenuItem("Moduleco HomePage"); //ajouté
		menuItemModulecoHomePage.addActionListener(this);
		menuHelp.add(menuItemModulecoHomePage);
		/**
		 * Sub-menu "About Moduleco"
		 */
		menuItemAboutModuleco = new JMenuItem("About Moduleco");
		menuItemAboutModuleco.addActionListener(this);
		menuHelp.add(menuItemAboutModuleco);
	}
	/*
	 * Method invoqued by the method EPanel.setMenuBar(), which excecute :
	 * {((Frame)getParent()).setMenuBar(controlPanel.getMenuBar());} This method
	 * execute in return the method of the (parent of ModulecoAppli) class Frame :
	 * Frame.setMenuBar(menuBar) This late method sets the JMenu bar for this
	 * frame to the JMenu bar instancied in Class ControlPanel.
	 */
	public JMenuBar getMenuBar() {
		return menuBar;
	}
	//	************************************************************************
	//	End build Menus
	//	************************************************************************
	/**
	 * Get the size of the world (length) defined in the GUI.
	 */
	protected int getChosenSize() {
		return (Integer.valueOf(permanentControlPanelBar.textFieldSize
				.getText())).intValue();
	}
	/**
	 * @param model
	 * @return
	 */
	public EWorldEditor loadEWorldEditor(String model) {
		EWorldEditor ewe = null;
		try {
			ewe = (EWorldEditor) Class.forName(
					"models." + model + ".WorldEditorPanel").newInstance();
		} catch (IllegalAccessException e) {
			System.out.println("ControlPanel.loadEWorldEditor() : "
					+ e.toString());
		} catch (InstantiationException e) {
			System.out.println("ControlPanel.loadEWorldEditor() : "
					+ e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println("The file models." + model
					+ ".WorldEditorPanel.java doesn't exist");
			System.out.println("ControlPanel.loadEWorldEditor() : "
					+ e.toString());
			ewe = new PanelSud();
		}
		return ewe;
	}
	/**
	 * method CreateWorld disable the current world and create a new one by
	 * invoking centralcontrol.create(-) method invoked by : this.WorldSelect()
	 * from Listener : this.menuWorld.checkMenuWorld.ItemListener() and Listener :
	 * ChoiceWorldPanel.ChoiceWorld.ItemListener() by method : models.
	 * <model>.Autorun.restart(), by method : PermanentControlPanelBar by method :
	 * modeleco.EWorld
	 */
	public void CreateWorld() {
		if (worldSelectIndex > 0) {
			/**
			 * Create the new world.
			 */
			centralControl.create(previousWorldSelected, worldSelected,
					neighbourSelected, (checkMenuLateScheduler.getState()
							? "LateCommitScheduler"
							: "EarlyCommitScheduler"), zoneSelected);
			previousWorldSelected = worldSelected;
		} else {
			/**
			 * disable some buttons and sub-menus whether no world is selected
			 */
			enableButtons(false);
		}
		//centralControl.FillDesktop();
		/**
		 * Get the new step number.
		 */
		updateIter();
		/**
		 * Reset the record flag to false to stop recording data
		 */
		checkMenuRecordQuick.setEnabled(true);
		checkMenuRecordQuick.setSelected(false);
		checkMenuRecord.setEnabled(true);
		checkMenuRecord.setSelected(false);
	}
	//	************************************************************************
	//	World Selection
	//	************************************************************************
	/**
	 * Action performed when a world is selected in the dropdown list.
	 */
	protected void choiceWorldPanelSelected() {
		worldSelectIndex = choiceWorldPanel.getSelectedIndex();
		if (worldGroup.getSelectedIndex() != worldSelectIndex) {
			checkMenuWorld[worldSelectIndex].setSelected(true);
			//enableButtons(false);
			WorldSelect();
		}
	}
	/**
	 * Action performed when a world is selected in the menu.
	 */
	protected void menuWorldSelected() {
		if (choiceWorldPanel.getSelectedIndex() != worldSelectIndex) {
			choiceWorldPanel.setSelectedIndex(worldSelectIndex);
			//enableButtons(false);
			WorldSelect();
		}
	}
	/**
	 * Create the world selected in the dropdown list or the menu.
	 */
	protected void WorldSelect() {
		//System.out.println("ControlPanel.WorldSelect()");
		if (worldSelectIndex > 0) {
			/**
			 * Create the world.
			 */
			CreateWorld();
			/**
			 * Update the GUI.
			 */
			adhocInit();
		}
	}
	public void enableButtons(boolean isEnabled) {
		//System.out.println("enableButtons isEnabled = "+ isEnabled);
		/**
		 * Permanent Control Bar (buttons "Create, Start, MStep and Stop"),
		 * World Size and Step text fields.
		 */
		permanentControlPanelBar.buttonCreate.setEnabled(isEnabled);
		permanentControlPanelBar.buttonStart.setEnabled(isEnabled);
		permanentControlPanelBar.buttonMStep.setEnabled(isEnabled);
		permanentControlPanelBar.buttonStop.setEnabled(isEnabled);
		permanentControlPanelBar.textFieldSize.setEnabled(isEnabled);
		/**
		 * Menu File
		 */
		menuItemLoad.setEnabled(isEnabled);
		menuItemSave.setEnabled(isEnabled);
		menuItemPrintStats.setEnabled(isEnabled);
		//menuItemPrintWindow.setEnabled(isEnabled);
		/**
		 * Menu Scheduler
		 */
		checkMenuEarlyScheduler.setEnabled(isEnabled);
		checkMenuLateScheduler.setEnabled(isEnabled);
		checkMenuExtraAgentScheduler.setEnabled(isEnabled);
		/**
		 * Menu Display
		 */
		checkMenuDisplayNeighbour.setEnabled(isEnabled);
		checkMenuDisplayZone.setEnabled(isEnabled);
		checkMenuDisplayXGraphics.setEnabled(isEnabled);
		checkMenuYY.setEnabled(isEnabled);
		/**
		 * Get the size of the world defined in the GUI, and decide whether the
		 * agents should be represented on a circle or on a square lattice.
		 */
		checkMenuDisplayTorusWorld.setEnabled(isEnabled);
		if (isEnabled) {
			int chosenSize = (Integer.valueOf(centralControl.modelParameters
					.getStringLength())).intValue();
			boolean oversize = (chosenSize > maxCircleSize);
			//System.out.println(" enableButtons - ChosenSize = "+ chosenSize+"
			// > maxCircleSize = " +maxCircleSize+" : "+oversize);
			if (oversize) {
				setCircleWorldDesabled();
			} else
				checkMenuDisplayCircleWorld.setEnabled(true);
		} else {
			checkMenuDisplayTorusWorld.setEnabled(false);
			checkMenuDisplayCircleWorld.setEnabled(false);
		}
		/**
		 * Menu Data
		 */
		checkMenuRecordQuick.setEnabled(isEnabled);
		checkMenuRecord.setEnabled(isEnabled);
		checkMenuStatManagerEnabled.setEnabled(isEnabled);
		/**
		 * Menu Help
		 */
		menuItemAboutThisWorld.setEnabled(isEnabled);
	}
	/**
	 * Update the GUI once a world is selected.
	 */
	public void adhocInit() {
		/**
		 * Get the initialisations parameters of the world, i.e. its length,
		 * neighbour, zone and time scheduler.
		 */
		String length = centralControl.modelParameters.getStringLength();
		String neighbourSelected = centralControl.modelParameters
				.getNeighbourhood();
		String zoneSelected = centralControl.modelParameters.getZone();
		String scheduler = centralControl.modelParameters.getTimeScheduler();
		/**
		 * Set the length text field.
		 */
		permanentControlPanelBar.textFieldSize.setText(length);
		/**
		 * Set the neighbour index.
		 */
		for (int i = 0; i < noNeighbourhoods; i++)
			if (neighbourName[i].compareTo(neighbourSelected) == 0)
				neighbourSelectIndex = i;
		choiceNeighbourPanel.setSelectedIndex(neighbourSelectIndex);
		checkMenuNeighbour[neighbourSelectIndex].setSelected(true);
		/**
		 * Set the zone index.
		 */
		for (int i = 0; i < noNeighbourhoods; i++)
			if (neighbourName[i].compareTo(zoneSelected) == 0)
				zoneSelectIndex = i;
		choiceZonePanel.setSelectedIndex(zoneSelectIndex);
		checkMenuZone[zoneSelectIndex].setSelected(true);
		/**
		 * Set the time scheduler
		 */
		boolean testLateScheduler = (scheduler == "LateCommitScheduler"
				? true
				: false);
		checkMenuLateScheduler.setSelected(testLateScheduler);
		if (testLateScheduler)
			checkMenuEarlyScheduler.setSelected(false);
		else
			checkMenuEarlyScheduler.setSelected(true);
	}
	/**
	 * méthode activée depuis CentralControl permet d'initialiser le
	 * eWorldEditor on evite ainsi une exception null pointer en ne mettant pas
	 * Wordselect() dans le constructeur du Controle Panel
	 * 
	 * @param pos
	 */
	public void selectInitWorld(int pos) {
		System.out.println("controlPanel.selectInitWorld(" + pos + ")");
		checkMenuWorld[pos].setSelected(true);
		worldSelectIndex = worldGroup.getSelectedIndex();
		worldSelected = worldGroup.getName();
		System.out.println("[controlPanel.selectInitWorld()] " + pos
				+ " + worldSelected : " + worldSelected
				+ " worldSelectIndex : " + worldSelectIndex);
		choiceWorldPanel.itemStateChanged(new ItemEvent(
				choiceWorldPanel.choiceWorld, ItemEvent.ITEM_STATE_CHANGED,
				"1", ItemEvent.SELECTED));
		//(String) choiceWorldPanel.choiceWorld.getItemAt(pos);
		//System.out.println("Select :"+WorldSelected());
	}
	//	************************************************************************
	//	End World Selection
	//	************************************************************************
	//	************************************************************************
	//	Neighbour & Zone Selection
	//	************************************************************************
	/**
	 *  
	 */
	protected void ChoiceNeighbourPanelSelected() {
		neighbourSelectIndex = choiceNeighbourPanel.getSelectedIndex();
		//neighbourSelected = choiceNeighbourPanel.getChoiceNeighbourName();
		if (neighbourGroup.getSelectedIndex() != neighbourSelectIndex) {
			//System.out.println("controlPanel.choiceNeighbourPanelSelected()");
			checkMenuNeighbour[neighbourSelectIndex].setSelected(true);
			//DisableProcess();//Central control.stop() verifie qu'il existe
			// bie qu'il existe bien un monde actif
			NeighbourSelect();
		}
	}
	/**
	 * 
	 *  
	 */
	protected void MenuNeighbourSelected() {
		if (choiceNeighbourPanel.getSelectedIndex() != neighbourSelectIndex) {
			//System.out.println("controlPanel.menuNeighbourSelected)");
			choiceNeighbourPanel.setSelectedIndex(neighbourSelectIndex);
			//DisableProcess();//Central control.stop() verifie qu'il existe
			// bie qu'il existe bien un monde actif
			NeighbourSelect();
		}
	}
	/**
	 * 
	 *  
	 */
	protected void NeighbourSelect() {
		/*
		 * // specific initialisations if (NeighbourSelected ==
		 * "RandomPairwise") {
		 * permanentControlPanelBar.textFieldSize.setType(ModulecoTextField.EVEN);
		 * permanentControlPanelBar.textFieldSize.setText("20"); }
		 */
		permanentControlPanelBar.textFieldSize
				.setType(ModulecoTextField.INTEGER);
	}
	/**
	 * activation Zone Selection
	 */
	protected void ChoiceZonePanelSelected() {
		zoneSelectIndex = choiceZonePanel.getSelectedIndex();
		zoneSelected = choiceZonePanel.getChoiceZoneName();
		if (zoneGroup.getSelectedIndex() != zoneSelectIndex) {
			//System.out.println("controlPanel.choiceZonePanelSelected() -
			// neighbourSelected :"+neighbourSelected);
			checkMenuZone[zoneSelectIndex].setSelected(true);
			//DisableProcess();//Central control.stop() verifie qu'il existe
			// bie qu'il existe bien un monde actif
			//ZoneSelect();
		}
	}
	/**
	 * Check that the zone really used and the one selected in the GUI are the
	 * same.
	 *  
	 */
	protected void MenuZoneSelected() {
		if (choiceZonePanel.getSelectedIndex() != zoneSelectIndex) {
			choiceZonePanel.setSelectedIndex(zoneSelectIndex);
		}
	}
	//	************************************************************************
	//	End Neighbour & Zone Selection
	//	************************************************************************
	public void itemStateChanged(ItemEvent e) {
		/**
		 * Menu Scheduler
		 */
		if (e.getSource().equals(checkMenuLateScheduler)) {
			if (checkMenuLateScheduler.isSelected()) {
				checkMenuEarlyScheduler.setSelected(false);
			}
		}
		if (e.getSource().equals(checkMenuEarlyScheduler)) {
			if (checkMenuEarlyScheduler.isSelected()) {
				checkMenuLateScheduler.setSelected(false);
			}
		}
		if (e.getSource().equals(checkMenuExtraAgentScheduler)) {
			if (checkMenuExtraAgentScheduler.isSelected()) {
				checkMenuExtraAgentScheduler.setSelected(false);
			} else {
				checkMenuExtraAgentScheduler.setSelected(true);
			}
		}
		/**
		 * Menu Display
		 */
		if (e.getSource().equals(checkMenuDisplayWorldSelection)) {
			if (checkMenuDisplayWorldSelection.isSelected())
				choiceWorldPanel.setVisible(true);
			else
				choiceWorldPanel.setVisible(false);
			validate();
		}
		if (e.getSource().equals(checkMenuDisplayNeighbour)) {
			if (checkMenuDisplayNeighbour.isSelected())
				choiceNeighbourPanel.setVisible(true);
			else
				choiceNeighbourPanel.setVisible(false);
			validate();
		}
		if (e.getSource().equals(checkMenuDisplayZone)) {
			if (checkMenuDisplayZone.isSelected())
				choiceZonePanel.setVisible(true);
			else
				choiceZonePanel.setVisible(false);
			validate();
		}
		if (e.getSource().equals(checkMenuDisplayXGraphics)) {
			centralControl.setDisplayXGraphics(checkMenuDisplayXGraphics
					.getState());
		}
		if (e.getSource().equals(checkMenuYY)) {
			System.out.println("menuDisplayYY" + checkMenuYY.getState());
		}
		if (e.getSource().equals(checkWhereWorldEditor)) //SC 01.06.01
		{
			centralControl.displayWorldEditor(checkWhereWorldEditor.getState());
			validate();
		}
		/**
		 * Menu Data > Record quick
		 */
		if (e.getSource().equals(checkMenuRecordQuick)) {
			/**
			 * If the new state is true, record variables
			 * <p>
			 * Else, stop to collect the trace.
			 */
			if (checkMenuRecordQuick.getState()) {
				centralControl.recordExperience(false);
				//checkMenuRecord.setSelected(true);
				checkMenuRecord.setEnabled(false);
			} else {
				centralControl.stopCollectTrace();
				checkMenuRecord.setEnabled(true);
			}
		}
		/**
		 * Menu Data > Record advanced
		 */
		if (e.getSource().equals(checkMenuRecord)) {
			/**
			 * If the new state is true, select variables to store
			 * <p>
			 * Else, stop to collect the trace.
			 */
			if (checkMenuRecord.getState()) {
				centralControl.recordExperience(true);
				checkMenuRecordQuick.setEnabled(false);
			} else {
				centralControl.stopCollectTrace();
				checkMenuRecordQuick.setEnabled(true);
			}
		}
		/**
		 * Menu Data > Graph
		 */
		if (e.getSource().equals(checkMenuStatManagerEnabled)) {
			/**
			 * Display the Graphic (and re-start the statManager) if true
			 * <p>
			 * Else, freeze it.
			 */
			if (checkMenuStatManagerEnabled.getState()) {
				centralControl.setStatManagerState(true);
				checkMenuRecordQuick.setEnabled(true);
				checkMenuRecord.setEnabled(true);
			} else {
				centralControl.setStatManagerState(false);
				checkMenuRecordQuick.setEnabled(false);
				checkMenuRecord.setEnabled(false);
				centralControl.setDisplayGraphic();
			}
		}
	}
	public void actionPerformed(ActionEvent e) {
		/**
		 * Menu File
		 */
		if (e.getSource() == menuItemLoad)
			centralControl.loadWorld();
		if (e.getSource() == menuItemSave)
			centralControl.saveWorld();
		if (e.getSource() == menuItemPrintStats)
			centralControl.printoutStats();
		if (e.getSource() == menuExit) {
			System.out.println("Bye bye");
			System.exit(0);
		}
		/**
		 * Menu Help > About this world.
		 */
		if (e.getSource() == menuItemAboutThisWorld) {
			/**
			 * Get the model name. <br>
			 * e.g. currentWorld = GCMG
			 */
			String currentWorld = choiceWorldPanel.getChoiceWorldName();
			/**
			 * Get the path to the help for this model. <br>
			 * e.g. helpPath = docs/modulecoDoc/GCMG
			 */
			String helpPath = "docs" + File.separator + "modulecoDoc"
					+ File.separator + currentWorld;
			/**
			 * Display the help in a browser, either locally or on the Web.
			 */
			getHelp(helpPath);
		}
		/**
		 * Menu Help > Moduleco Javadoc
		 *  
		 */
		if (e.getSource() == menuItemJavaDoc) {
			String helpPath = "docs" + File.separator + "javadoc";
			getHelp(helpPath);
		}
		/**
		 * Menu Help > Moduleco HomePage
		 */
		if (e.getSource() == menuItemModulecoHomePage) {
			String urlString = "http://www-eco.enst-bretagne.fr/~phan/moduleco";
			BrowserControl.displayURL(urlString);
		}
		/**
		 * Menu Help > About Moduleco
		 */
		if (e.getSource() == menuItemAboutModuleco)
			System.out.println("Moduleco Version 1.4");
	}
	/**
	 * Try to find some contextual help locally, and then on the Web.
	 *  
	 */
	public void getHelp(String helpPath) {
		/**
		 * Define the path to the ressource locally.
		 */
		String filename = modulecoPathRoot + helpPath;
		File file = new File(filename);
		/**
		 * If the file exists locally and is readable, then open it in a
		 * browser.
		 */
		if (file.exists() && file.canRead() && file.isFile()) {
			BrowserControl.displayURL("file://" + filename);
		}
		/**
		 * Else, go and get it on the Web.
		 */
		else {
			String url = webResources + helpPath;
			BrowserControl.displayURL(url);
		}
	}
	/**
	 * Get the step number from the SimulationControl, and display it.
	 */
	public void updateIter() {
		permanentControlPanelBar.textFieldStep.setText((new Integer(
				centralControl.getIter()).toString().trim()));
	}
	public void paint(java.awt.Graphics g) {
		super.paint(g);
		g.setColor(java.awt.Color.lightGray);
		//g.fill3DRect(0, 0, getSize().width - 1, buttonCreate.getBounds().y -
		// 5, true);
		//g.fill3DRect(0, buttonCreate.getBounds().y - 5, getSize().width - 1,
		// getSize().height - 1 - (buttonCreate.getBounds().y - 5), true);
	}
	public PermanentControlPanelBar getPermanentControlPanelBar() {
		return permanentControlPanelBar;
	}
	//modifié le 19/04/02 CK
	public String getWorldSelected() {
		return worldSelected;
	}
	//modifié le 19/04/02 CK
	public String getNeighbourSelected() {
		return neighbourSelected;
	}
	//modifié le 19/04/02 CK
	public String getZoneSelected() {
		return zoneSelected;
	}
	public String getSchedulerType() {
		if (checkMenuLateScheduler.getState())
			return new String("Late Scheduler");
		else
			return new String("Early Scheduler");
	}
	public String getWorldSize() {
		return permanentControlPanelBar.textFieldSize.getText();
	}
	public boolean getExtraAgentCommit() {
		return checkMenuExtraAgentScheduler.getState();
	}
	public EWorldEditor getEWorldEditor() {
		return eWorldEditor;
	}
	// ajoute DP 12/07/2002
	//================= Circle Versus Torus World ================
	/**
	 * Left Representation : Torus World invoked by :
	 * controlPanel.displayWorldListener.itemStateChanged()
	 */
	protected void setDisplayTorusWorld() {
		//System.out.println("controlPanel.setDisplayTorusWorld()");
		centralControl.getEPanel().setDisplayTorusWorld();
	}
	/**
	 * Left Representation : Circle World (eWorld.size= <64) invoked by method :
	 * centralControl.smallWorldHadHocInit
	 */
	public void setDisplayCircleWorld() {
		//System.out.println("controlPanel.setDisplayCircleWorld()");
		centralControl.getEPanel().setDisplayCircleWorld();
	}
	/**
	 * invoked by EWorld, centralControl.create()
	 */
	public void setCircleWorldDesabled() {
		/*
		 * invoked by method : centralControl.create()
		 */
		checkMenuDisplayCircleWorld.setEnabled(false);
		checkMenuDisplayCircleWorld.setSelected(false);
		checkMenuDisplayTorusWorld.setSelected(true);
		setDisplayTorusWorld();
		//System.out.println("controlPanel.setCircleWorldDesabled()");
		//centralControl.DisplayCircleWorld="+checkMenuDisplayCircleWorld.getState());
	}
	/**
	 * invoqued by CentralControl.updateMenuDisplayXGraphics()
	 * 
	 * @see modulecoGUI.grapheco.CentralControl.updateMenuDisplayXGraphics()
	 *      CentralControl.updateMenuDisplayXGraphics() invoqued itself by :
	 *      CentralControl.create()
	 */
	public void setMenuDisplayXGraphicsEnabled(boolean test) {
		checkMenuDisplayXGraphics.setEnabled(test);
		if (test) {
			checkMenuDisplayXGraphics.setSelected(test);
			//centralControl.updateDisplayXGraphics(); double emploi cf
			// updateMenuDisplayXGraphics
		}
	}
	// EPanel ==============================
	// TESTER l'utilité
	public void addAdditionalPanel(EAdditionalPanel eAdditionalPanel) {
		((EPanel) getParent()).addAdditionalPanel(eAdditionalPanel);
	}
	//SC 31.05.01
	public void removeAdditionalPanel(EAdditionalPanel eAdditionalPanel) {
		((EPanel) getParent()).removeAdditionalPanel(eAdditionalPanel);
	}
}