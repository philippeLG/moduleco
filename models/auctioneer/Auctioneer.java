/** class auctioneer.Auctioneer.java
 * Title:        Moduleco<p>
 * Description:  EAgent who represents an auctioneer
 * Copyright:    Copyright (c)enst-bretagne
 * @author philippe LeGoff, sebastien.chivoret@ensta.org revised denis.phan@enst-bretagne.fr
 * @version 1.4  February, 2004
 */

package models.auctioneer;

// import java.util.Iterator;
import java.util.ArrayList;

//import java.io.FileWriter;
//import java.io.BufferedWriter;
//import java.io.File;

import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
// import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
// import java.lang.Math;import modulecoGUI.grapheco.CentralControl;
import modulecoFramework.modeleco.EAgent;
import modulecoFramework.modeleco.EWorld;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
/**
* This class is an (extra) agent which play the role of the auctioneer
* in a walrassian pure exchange market
**/

public class Auctioneer extends EAgent {

	//protected AdditionalPanel aWep;
	protected CRandomDouble random;
	protected Market market;
	protected double p1, p2, k, E1, E2, convergence;
//	protected BufferedWriter out;
	//protected int counter=0;
	protected EWorld eWorld;
	protected boolean convergenceTest;
	public Auctioneer() {
		//System.out.println("ConstructeurCompetitorAlone");
	}
	/*   
		public void printFile(){
			try{
			   String directoryName=modulecoGUI.grapheco.CentralControl.getModulecoPathRoot() + "models"+File.separator+ "auctioneer";
			   String fileName="output.txt";
			   File output=new File(directoryName,fileName);
			   output.delete();
			   output.createNewFile();
			   if (!output.isFile()){
				  //System.out.println("Creation of "+output.getPath()+" has failed");
				  return;
			   }
			   out=new BufferedWriter(new FileWriter(output.getPath(), true));
			}
			   catch (Exception e){
				  System.out.println(e.toString());
			   }
		}
		*/

	public Object getState() {

		//System.out.println(" agent.getState() ");
		return new Integer(1);
	}
	public void getInfo() {
		/*
		   if ((((World) world).getUtility()).equals("Cobb Douglas"))
		      p1=1;
		   else
		      p1=2;
				*/
		p1 = 2;
		p2 = 1;
		//System.out.println(" Auctioneer getInfo()DEBUT");
		// voir l'origine des variables (world, additional panel..;
		// vérifier qu'elles proviennent bien de la bonne class
		try {
			this.random = ((World) world).random;
		} catch (IndexOutOfBoundsException e) {

			System.out.println(e.toString());
		}
		//System.out.println(" Auctioneer getInfo()FIN");
	}

	public void init() {

		//System.out.println("Auctioneer Init");
		market = ((World) world).getMarket();
		convergence = ((World) world).getConvergence();
		k = ((World) world).getK();
		convergenceTest = true;
	}

	public void compute() {
		//System.out.println(" Auctioneer.compute()-debut ");
		//System.out.println(" Auctioneer.compute()-fin ");
	}

	public void commit() {

		//System.out.println(" Auctioneer.commit()-debut ");
		//convergence=((World)world).getConvergence();
		E1 = market.getE1(); //agregated data (demand)
		E2 = market.getE2();
		//System.out.println(" Auctioneer E1 = "+E1+" E2 "+E2);
		p1 = p1 * (1 + k * E1 / (double) world.agentSetSize);
		//System.out.println(" Auctioneer E/N = "+E1/(double)world.capacity+"Walras Test ="+(E1*p1+E2*p2));			
		//p2=p2*(1+k*E2);	
		/*
		try {

			if (!((java.lang.Math.abs(E1) < convergence)
				& (java.lang.Math.abs(E2) < convergence))) {
				out.write("" + E1);
				out.newLine();
			} else {
				out.close();
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
*/
		if ((java.lang.Math.abs(E1) < convergence)
			& (java.lang.Math.abs(E2) < convergence)) {

			// permettre l'affichage du revenu agrégé
			if (convergenceTest)
				convergenceTest = false;
			else
				convergenceTest = true;
			System.out.println("test =" + convergenceTest);
			market.marketClose(convergenceTest);
		}

		//System.out.println(" Auctioneer.commit()-fin ");
	}

	protected void finalize() {
/*
		try {
			out.close();
		} catch (Exception e) {

			System.out.println("Impossible to close the output file");
		}
		*/
	}

	public void inverseState() {
		//if (ae != null) ae.update ();
	}

	public void setDefaultValues() { //n'est pas activé (systématiquement) ?
		System.out.println(" Auctioneer.setDefaultValues()");
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(
			new DoubleDataDescriptor(this, "p1", "p1", p1, true, 6));
		descriptors.add(
			new DoubleDataDescriptor(this, "p2", "p2", p2, false, 6));
		descriptors.add(
			new DoubleDataDescriptor(this, "E1", "E1", E1, false, 5));
		descriptors.add(
			new DoubleDataDescriptor(this, "E2", "E2", E2, false, 5));
		return descriptors;
	}

	public double getP1() {
		return p1;
	}
	public double getP2() {
		return p2;
	}

	public double getK() {
		return k;
	}
	public void setP1(double d) {
		p1 = d;
	}
	public void setP2(double d) {
		p2 = d;
	}
	public void setE1(double d) {

	}
	public void setE2(double d) {
	}

}
