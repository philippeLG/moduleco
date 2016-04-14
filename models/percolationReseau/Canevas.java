 /**
  * Class percolationReseau.Canevas.java
  * Copyright:    Copyright (c)enst-bretagne
  * @author gregory.gackel@enst-bretagne.fr
* @version 1.2  august,5, 2002
* @version 1.4. february 2004    
  */

   package models.percolationReseau;

   import java.awt.Color;
   import java.awt.MenuItem;
   import java.awt.Graphics;
   import java.awt.event.MouseEvent;
   import java.awt.event.ActionEvent;
   import java.awt.event.InputEvent;
import modulecoFramework.modeleco.EAgent;
   import modulecoFramework.modeleco.EWorld;
   import modulecoFramework.modeleco.CAgent;
   import modulecoGUI.grapheco.ControlPanel;

   public class Canevas extends modulecoGUI.grapheco.Canevas
   {
      protected ControlPanel control;
      protected EWorld world;
   
      protected int state[][]; // copie de l'etat
   
    // protected Color c[];
      protected Agent ag;
      protected int link;
      protected int size = 1;// evite une division par zero (a nettoyer)
      protected int largeur;
      protected int hauteur;
      protected final Color fondcanvas = Color.white;
   
      protected MenuItem menuItemEditAgent;
   
   
      protected int xc, yd;
   
      public Canevas()
      {
        // choix des couleurs associées à la révision des statégies
         setColors();
         setBackground(fondcanvas);
      
         menuItemEditAgent = new MenuItem("Edit Agent");
      
         menuItemEditAgent.addActionListener(this);
      
         popupMenu.add(menuItemEditAgent);
      }
   
      public void setCAgent(CAgent world)
      {
         this.world = (EWorld) world;
         setGridSize();
      }
   
      protected void setColors()
      {
      /*  c = new Color[5];
      c[0] = Color.lightGray;
      c[1] = Color.green;
      c[2] = Color.red;*/
      }
   
      public void setControl(ControlPanel ctrl)
      {
         control = ctrl;
      }
   
      public void setGridSize()
      {  // world just beeing created
         size = (new Double(Math.sqrt(world.getAgentSetSize()))).intValue();
      
         state = new int [size][size];
      /*  for(int i = 0; i < size ; i++)
            for(int j = 0; j < size ; j++)
            {
                ag =(Agent) world.get(j*size+i);
                if (ag.getActualState() == "treeHere")
                    state[j][i] = 1;
                else if (ag.getActualState() == "fireHere")
                    state[j][i] = 2;
                else
                    state[j][i] = 3;
            }*/
      }
   
      protected void drawPoint(Graphics g, int i, int j,int a, int b, int x, int y)
      {
         if (world != null)
         {
            int i1,j1;
            ag =(Agent) world.get(j*size+i);
            if (ag.getActualState().equals("treeHere"))
               state[j][i] = 1;
            else if (ag.getActualState().equals("fireHere"))
               state[j][i] = 2;
            else
               state[j][i] = 3;
            link = (int) (255-(255*ag.getNumberOfLinks())/10);
            i1 = i * a;
            j1 = j * b;
            if(size<40) {
                // dessine un secteur circulaire
                // x,y, largeur, hauteur,debut arc, fin arc)
               if (state[j][i] == 1) {
                  g.setColor(new Color(0,link,0));
               } 
               else if (state[j][i] == 2) {
                  g.setColor(new Color(link,0,0));
               } 
               else if (state[j][i] == 3) {
                  g.setColor(new Color(0,255-link,0));
               } 
               else
                  g.setColor(fondcanvas);
            
               g.fillArc(i1 + 1 + x/2, j1 + 1 + y/2, a - 2, b - 2, 0, 360);
            }
            else
               if (state[j][i] == 1)
                  g.setColor(new Color(0,link,0));
               else if (state[j][i] == 2)
                  g.setColor(new Color(link,0,0));
               else if (state[j][i] == 3)
                  g.setColor(new Color(0,255-link,0));
               else
                  g.setColor(fondcanvas);
            g.fillRect(i1 + 1 + x/2, j1 + 1 + y/2, a, b);
            //x,y,largeur, hauteur
         }
      }
   
      protected void drawFirstTime(Graphics g)
      {
         draw(g);
      }
   
      protected void draw(Graphics g)
      {
         int a,b,x,y ;
         int i,j ;
      
         if(g != null)
         {
            a = dimension.width  / (size) ;
            x = dimension.width-a*(size);
            b = dimension.height /(size) ;
            y = dimension.height-b*(size);
         
            g.setColor(Color.white);
            g.fill3DRect(0, 0, dimension.width-1,  dimension.height-1, true);
         
            if(size<40)
            {
               g.setColor(Color.black);
               for(int k = 0; k < (size); k++)
                  g.drawLine(x/2, k * b + y/2, dimension.width - x/2, k *b + y/2);
               for(int l = 0; l < (size); l++)
                  g.drawLine(l*a + x/2, y/2, l * a + x/2, dimension.height - y/2);
            }
            for(i = 0; i < size ; i++)
               for(j = 0; j < size ; j++)
               {
                  drawPoint(g, i, j, a, b, x ,y );
               }
         }
      }
   
   
      public void mouseClicked(MouseEvent e)
      
      {
      
         int agentRowFill = dimension.width / size;
      
         int widthUnused = dimension.width - agentRowFill * size;
      
         int agentColumnFill = dimension.height / size;
      
         int heightUnused = dimension.height - agentColumnFill * size;
      
         xc = (e.getX() - widthUnused / 2) / agentRowFill + 1;
      
         yd = (e.getY() - heightUnused / 2) / agentColumnFill + 1;
      
      
      
         if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK)
         
         {
         
            if ((e.getX() > (widthUnused / 2)) && (e.getX() < (dimension.width - (widthUnused / 2))) && (yd>0) && (yd<size+1))
            
            
            
            {
            
               popupMenu.setLabel("Agent #" +((yd -1) * size + (xc -1)) + " (Y : "+ yd +" ; X :"+ xc + ")");
            
               popupMenu.add(menuItemEditAgent);
            
            }
            
            else
            
            {
            
               popupMenu.setLabel("");
            
               popupMenu.remove(menuItemEditAgent);
            
            }
         
         
         
            super.mouseClicked(e);
         
         }
         
         else
         
         {
         
            if ((e.getX() > (widthUnused / 2)) && (e.getX() < (dimension.width- (widthUnused / 2))) && (yd>0) && (yd<size+1))
            
            {
            
               ((EAgent)world.get((yd -1) * size + (xc -1))).inverseState();
            
               this.repaint();
            
            }
         
         }
      
      }
   
   
   
      public void actionPerformed(ActionEvent e)
      
      {
      
         if (e.getSource().equals(menuItemEditAgent))
         
            centralControl.editAgent(yd-1, xc-1);
         
         else
         
            super.actionPerformed(e);
      
      }
   }
