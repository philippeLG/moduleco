 /** Source File Name:   ModulecoHelpBrowser.java
 * @author denis.phan@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 * Know Bugs : pourquoi faut il compiler 2 fois (EXCEPTION_ACCESS_VIOLATION occurred at PC=0x6d205a8d)
 * at sun.awt.font.NativeFontWrapper.getFamilyNameByIndex(Native Method)
 * Pourquoi  EmptyStackException avec ViabilityWages ?
 */
   package modulecoGUI.help;

   import java.io.IOException;
   import java.io.File;
   import java.util.Stack;
   //import java.util.EmptyStackException;

   import javax.swing.JTextField;
   import javax.swing.JButton;
   import javax.swing.JEditorPane;
   import javax.swing.JFrame;
   import javax.swing.JPanel;
   import javax.swing.JLabel;
   import javax.swing.JScrollPane;
   import javax.swing.JMenu;
   import javax.swing.JMenuBar;
   import javax.swing.JMenuItem;
// import java.awt.Font;
   import javax.swing.event.HyperlinkListener ;
   import javax.swing.event.HyperlinkEvent;
   import javax.swing.JCheckBoxMenuItem ;

   import java.awt.event.ActionListener;
   import java.awt.event.ActionEvent;

// import java.awt.event.WindowAdapter;
// import java.awt.event.WindowEvent;
   import java.awt.Container;

// import java.net.URL ;

/**
 * This class creates and displays a PopUp containing a JEditorPane, 
 * in oder to provide Help informations in HTML form from <helpfile>.html
 **/


   public class  ModulecoHelpBrowser extends JFrame implements ActionListener
   {
      private JMenuBar menuBar;
      private JMenuItem menuItemLoad, menuItemJavaDoc, menuItemGuide, menuItemHomePage, menuItemExit ;
      protected static String directory="help";// The default directory 
      private Container contentPane ;
      private JTextField url;
      private JButton loadButton;
      private JButton backButton;
      private JEditorPane editorPane;
      private Stack urlStack = new Stack();
      private String urlString ;
      private String currentWorld, modulecoPath;
      private String[] worldNameList;
      private int maxWorld;
      private JMenu menuAbout;//Item
      private JCheckBoxMenuItem[] worldMenuItem ;
   
   //private Font font;
   /** 
   * Convenience constructor: file viewer starts out blank 
   **/
   
      public ModulecoHelpBrowser() { 
      
         this(null); }
   
   /**
   * Effective constructor.  Create a TextViewer to display the
   * specified help file
   **/
      public ModulecoHelpBrowser(String mP, String cW){
         this("file:"+File.separator+mP+"models"+File.separator+cW+File.separator+cW+".html");//utiliser un type File ?
         //System.out.println("Help browser - mp = "+mP);
         this.currentWorld = cW;
         this.modulecoPath=mP;
      
      }
   
      public ModulecoHelpBrowser(String urlString){ 
         super();  // Create the (closeable) JFrame
         this.urlString = urlString ;
         setTitle("ModulecoHelpBrowser");
         setSize(800,600);
         //font = new Font("SansSerif", Font.BOLD, 12);
         buildLastURL();
         buildURLTextFile();
         buildURLLoader();
         buildBrowser();
      // PLace tous les composants de controle dans le panneau
         JPanel panel = new JPanel();
         panel.add(backButton);
         panel.add(new JLabel("URL :"));
         panel.add(url);
         panel.add(loadButton);
         contentPane.add(panel, "North");
         urlPageLoader(urlString, null);						
      }
      public void buildURLTextFile(){
         url = new JTextField(40);
         url.setText(urlString);
      }
      public void buildURLLoader(){
      // definit le champ de texte et le bouton de chargement pour charger une URL
      
         loadButton=new JButton("Load URL");
         //loadButton.setFont(font);
         loadButton.addActionListener(
                                 new ActionListener(){ 
                                    public void actionPerformed(ActionEvent evt){
                                       urlPageLoader(urlString, evt);
                                    }
                                 });
      }
      public void buildLastURL(){
      	//definit le bouton Retour et le bouton Action
         backButton = new JButton("Last URL");
         //backButton.setFont(font);
         backButton.addActionListener(
                                 new ActionListener(){ 
                                    public void actionPerformed(ActionEvent event){ 
                                       if (urlStack.size() <= 1) 
                                          return;
                                       try
                                       {//remember URL pour le bouton retour
                                          urlStack.pop();
                                       //affiche l'URL dans un champ de texte
                                          urlString = (String) urlStack.peek();
                                          url.setText(urlString);
                                          editorPane.setPage(urlString); 
                                       }
                                          catch(IOException e){
                                             editorPane.setText("Erreur: "+e);
                                          }
                                    }
                                 });
      }
   
   //définit le paneau de modifs et l'écouteur d'hyperliens
   
      public void buildBrowser(){         
         editorPane = new JEditorPane();
         editorPane.setEditable(false);
         editorPane.addHyperlinkListener(
                                 new HyperlinkListener(){
                                    public void hyperlinkUpdate(HyperlinkEvent event){ 
                                       if (event.getEventType()
                                          == HyperlinkEvent.EventType.ACTIVATED)
                                       {
                                          try
                                          {//put the URL in the backLoad Memory
                                             urlStack.push(event.getURL().toString());
                                          // Shows the new URL in the text field
                                             url.setText(event.getURL().toString());
                                             editorPane.setPage(event.getURL());
                                          }
                                             catch(IOException e){
                                                editorPane.setText("Erreur: "+e);
                                             }
                                       }
                                    }
                                 });
      
         contentPane = getContentPane();
         contentPane.add(new JScrollPane(editorPane),"Center");
      }
      public void createJMenuBar(){
      
         menuBar = new JMenuBar();
         this.setJMenuBar(menuBar);
         JMenu menuFile = new JMenu("File");
         //menuBar.setFont(font);
         menuBar.add(menuFile);
      
         menuAbout = new JMenu("About Worlds"); //Item
         menuFile.add(menuAbout);
         //menuAbout.addActionListener(this);
         worldMenuItem = new JCheckBoxMenuItem[maxWorld];
         for (int i=0; i<maxWorld;i++){
            worldMenuItem[i]= new JCheckBoxMenuItem(worldNameList[i],((worldNameList[i]).equals(currentWorld)?true:false));           
            menuAbout.add(worldMenuItem[i]);
            worldMenuItem[i].addActionListener(this);
         }
      
         menuFile.addSeparator();
      
         menuItemJavaDoc = new JMenuItem("ModulecoJavaDoc");
         menuFile.add(menuItemJavaDoc);
         menuItemJavaDoc.addActionListener(this);
      
         menuItemGuide = new JMenuItem("Moduleco Getting started ");
         menuFile.add(menuItemGuide);
         menuItemGuide.addActionListener(this);
      
         menuFile.addSeparator();
      
         menuItemHomePage = new JMenuItem("Access Moduleco HomePage");
         menuFile.add(menuItemHomePage);
         menuItemHomePage.addActionListener(this);
      
         menuFile.addSeparator();
      
         menuItemExit = new JMenuItem("Exit");
         menuFile.add(menuItemExit);
         menuItemExit.addActionListener(this);
      }
      public void actionPerformed(ActionEvent evt) {
      
        /* if (evt.getSource() == menuAbout) { // if User clicked "Load another World" 
            //worldMenuItem.setVisible();
         	//menuAbout.setVisible();
         }*/
         if (evt.getSource() instanceof JCheckBoxMenuItem){ // UTILE ?
            for (int i = 0; i<maxWorld ; i++)
               if (evt.getSource().equals(worldMenuItem[i])){
                  worldMenuItem[i].setState(true);
                  urlString = "file:"+File.separator+modulecoPath+"models"+File.separator+worldNameList[i]+File.separator+worldNameList[i]+".html";
                  System.out.println("urlString ="+urlString);
                  urlPageLoader(urlString, null);
               }
               else{
                  worldMenuItem[i].setState(false);
               }
         }
      
         if (evt.getSource() == menuItemGuide){
            urlString = "file:"+File.separator+modulecoPath+File.separator+"ModulecoFramework"+File.separator+"help"+File.separator+"gettingStarted.html";//index > With Frames
            urlPageLoader(urlString, evt);
         
         }
      
         if (evt.getSource() == menuItemJavaDoc){
            urlString = "file:"+File.separator+modulecoPath+"ModulecoJavaDoc"+File.separator+"overview-summary.html";//index > With Frames
            urlPageLoader(urlString, evt);
         }
         if (evt.getSource() ==menuItemHomePage){
            urlString = "http://www-eco.enst-bretagne.fr/~phan/moduleco";
            urlPageLoader(urlString,evt);
         }
      
         if (evt.getSource() == menuItemExit) // if user clicked "Exit" option
            this.dispose();                  //    then close the window
      }
      public void urlPageLoader(String urlString,ActionEvent evt){
         url.setText(urlString);
         try{
            //put the URL in the backLoad Memory
            urlStack.push(url.getText());
            // Shows the new URL in the text field
            editorPane.setPage(url.getText());
         }
            catch(IOException e){
            
               editorPane.setText("Erreur : "+e);
            }
           // catch(EmptyStackException e){// UTILE ? L'ERREUR SURVIENS ELLE ICI ?
      
               //editorPane.setText("Erreur : "+e);
               //System.out.println(e.toString());
            //}
         //System.out.println("FIN urlPageLoader");
      }
   
      public void setWorldList(int maxWorld, String[] worldNameList, String currentWorld){
         this.maxWorld=maxWorld;
         this.currentWorld=currentWorld;
         this.worldNameList = new String[maxWorld];
         this.worldNameList =worldNameList;
         createJMenuBar();
      }
   }