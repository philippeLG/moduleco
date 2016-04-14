 /* Source File Name:   Canevas.java
  * Copyright:    Copyright (c)enst-bretagne
  * @author denis.phan@enst-bretagne.fr frederic.falempin@enst-bretagne.fr, Jerome.Schapman@enst-bretagne.fr
  * @version 1.2  august,5, 2002
* @version 1.4. february 2004    
  */

   package models.randomWalkers;

   import java.awt.Graphics;
   import java.awt.Color;

   import modulecoFramework.modeleco.EWorld;
   import modulecoFramework.modeleco.CAgent;
   import modulecoGUI.grapheco.ControlPanel;

   public class Canevas extends modulecoGUI.grapheco.Canevas
   {
      protected ControlPanel control;
      protected EWorld world;
   
      protected int state[][]; // copie de l'etat
   
      protected Color c[];
      protected Place ag;
      protected int size = 1;// evite une division par zero (a nettoyer)
      protected int largeur;
      protected int hauteur;
      protected int compteur = 0;
      protected final Color fondcanvas = Color.black;
   
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
         c = new Color[2];
         c[1] = new Color(255,0,0);
         c[0] = new Color(0,0,255);
      }
   
      public void setControl(ControlPanel ctrl)
      {
         control = ctrl;
      }
   
      public void setGridSize()
      {  // world just beeing created
         size = (new Double(Math.sqrt(world.getAgentSetSize()))).intValue();
      
         state = new int [size][size];
      /*        for(int i = 0; i < size ; i++)
            for(int j = 0; j < size ; j++)
            {
                state[j][i] = ((Place) world.get(j*size+i)).getActualState();
            }
      */    }
   
      protected void drawPoint(Graphics g, int i, int j,int a, int b, int x, int y)
      {
         if (world != null)
         {
            int i1,j1;
            ag =(Place) world.get(j*size+i);
            state[j][i] = ag.getActualState();
            //       produceMax = ((World) world).produceMax;
         
         
            i1 = i * a;
            j1 = j * b;
            if(size<40) {
                // dessine un secteur circulaire
                // x,y, largeur, hauteur,debut arc, fin arc)
               if (state[j][i] == 1) {
                  g.setColor(c[compteur]);
                  g.fillArc(i1 + 1 + x/2, j1 + 1 + y/2, a - 2, b - 2, 0, 360);
                  compteur= (compteur+1)%2;
               }
               else {
                  g.setColor(fondcanvas);
                  g.fillArc(i1 + 1 + x/2, j1 + 1 + y/2, a - 2, b - 2, 0, 360);
               }
            } 
            else {
               if (state[j][i] == 1)
               { g.setColor(c[compteur]);
                  g.fillRect(i1 + 1 + x/2, j1 + 1 + y/2, a, b);
                  compteur= (compteur+1)%2;
               }
               else{
                  g.setColor(fondcanvas);
                  g.fillRect(i1 + 1 + x/2, j1 + 1 + y/2, a, b);
               }
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
         
            g.setColor(Color.black);
            g.fill3DRect(0, 0, dimension.width-1,  dimension.height-1, true);
         
            if(size<40)
            {
               g.setColor(Color.white);
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
