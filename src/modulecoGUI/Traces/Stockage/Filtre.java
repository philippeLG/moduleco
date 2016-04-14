/*
 * Filtre.java
 *
 * Created on 22 avril 2002, 22:25
 */

   package modulecoGUI.Traces.Stockage;

/**
 *
 * @author  infolab
 * @version 
 */
   import modulecoGUI.Traces.Experience;
   import modulecoGUI.Traces.Stockage.Stockage;
   import java.io.File;
   public class Filtre {
   
      protected Experience experience;
      protected Stockage stockage;    
    /** Creates new Filtre */
      public Filtre(Experience experience) {
         this.experience = experience;
         filtrer(experience.getDataFormat());
      }
      public void filtrer(String format)
      {
         if (format.equals (".csv"))
         {
            stockage = new FormatCSV(experience);
            return;
         }
         if (format.equals (".doc"))
         {
            stockage = new FormatDoc(experience);
            return;
         }
         if (format.equals (".txt"))
         {
            stockage  = new FormatTxt(experience);
            return;
         }
      }
   
      public void writeData()
      {
         this.stockage.writeData();
         experience.remove();
      }
   
      public void removeFile()
      {
         File output = new File(experience.getDataFormat());
         if (output.isFile())
            output.delete();
      }
   }       



