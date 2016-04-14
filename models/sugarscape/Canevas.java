 /* Source File Name:   Canevas.java
  * Copyright:    Copyright (c)enst-bretagne
  * @author denis.phan@enst-bretagne.fr frederic.falempin@enst-bretagne.fr, Jerome.Schapman@enst-bretagne.fr
 * @version 1.2  august,5, 2002
* @version 1.4. february 2004    
  */

   package models.sugarscape;

   import java.awt.Color;
   import java.awt.Graphics;

   import modulecoFramework.modeleco.EWorld;
   import modulecoFramework.modeleco.CAgent;
   import modulecoGUI.grapheco.ControlPanel;

   public class Canevas extends modulecoGUI.grapheco.Canevas
   {
      protected ControlPanel control;
      protected EWorld world;
   
      protected int state[][]; // copie de l'etat
   
    //  protected Color c[];
      protected Place ag;
      protected int produceMax;
      protected int prod;
      protected int size = 1;// evite une division par zero (a nettoyer)
      protected int largeur;
      protected int hauteur;
      protected final Color fondcanvas = Color.white;
   
      public Canevas()
      {
        // choix des couleurs associées à la révision des statégies
         setColors();
         setBackground(fondcanvas);
      }
   
      public void setCAgent(CAgent world)
      {
         this.world = (EWorld) world;
         setGridSize();
      }
   
      protected void setColors()
      {
        //      c = new Color[5];
        //      c[0] = Color.lightGray;
        //      c[1] = Color.red;
        //      c[2] = Color.blue;
        //      c[3] = Color.green;
        //      c[4] = Color.darkGray;
      }
   
      public void setControl(ControlPanel ctrl)
      {
         control = ctrl;
      }
   
      public void setGridSize()
      {  // world just beeing created
         size = (new Double(Math.sqrt(world.getAgentSetSize()))).intValue();
      
         state = new int [size][size];
         for(int i = 0; i < size ; i++)
            for(int j = 0; j < size ; j++)
            {
               state[j][i] = ((Place) world.get(j*size+i)).getActualState();
            }
      }
   
      protected void drawPoint(Graphics g, int i, int j,int a, int b, int x, int y)
      {
         if (world != null)
         {
            int i1,j1;
            ag =(Place) world.get(j*size+i);
            state[j][i] = ag.getActualState();
         //       produceMax = ((World) world).produceMax;
            prod = (int) (255-(255*ag.getProduction())/10);
            //	  g.setColor(c[state[j][i]]);
            i1 = i * a;
            j1 = j * b;
            if(size<40) {
                // dessine un secteur circulaire
                // x,y, largeur, hauteur,debut arc, fin arc)
               if (state[j][i] == 1) {
                  g.setColor(new Color(255,255,prod));
                  g.fillArc(i1 + 1 + x/2, j1 + 1 + y/2, a - 2, b - 2, 0, 360);
               } 
               else if (state[j][i] == 2) {
                  g.setColor(new Color(255,0,0));
                  g.fillArc(i1 + 3 + x/2, j1 + 3 + y/2, a - 6, b - 6, 0, 360);
               } 
               else if (state[j][i] == 3) {
                  g.setColor(new Color(255,255,prod));
                  g.fillArc(i1 + 1 + x/2, j1 + 1 + y/2, a - 2, b - 2, 0, 360);
                  g.setColor(new Color(255,0,0));
                  g.fillArc(i1 + 3 + x/2, j1 + 3 + y/2, a - 6, b - 6, 0, 360);
               }
            } 
            else {
               if ((state[j][i] == 2) || (state[j][i] == 3))
                  g.setColor(new Color(255,0,0));
               else if (state[j][i] == 1) 
                  g.setColor(new Color(255,255,prod));
               else
                  g.setColor(fondcanvas);
            
               g.fillRect(i1 + 1 + x/2, j1 + 1 + y/2, a, b);
            }
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
   }
