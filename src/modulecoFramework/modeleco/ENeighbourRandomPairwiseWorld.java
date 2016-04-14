/**
 * Title:        Modeleco<p>
 * Description:  
 * Copyright : (c)enst-bretagne
 * @author Denis.Phan@enst-bretagne.fr
 * created may 2000
 * @version 1.2  august,5, 2002
 * @version 1.4.2 june 2004  
 */
package modulecoFramework.modeleco;

import java.util.ArrayList;
// import java.util.Iterator;
import java.lang.reflect.Constructor;

import modulecoFramework.modeleco.randomeco.CRandomDouble;

import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
// import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
// import modulecoGUI.grapheco.descriptor.InfoDescriptor;

public abstract class ENeighbourRandomPairwiseWorld
	extends ENeighbourWorld
	implements StructuredNeighbourWorld {
	protected CRandomDouble random;
	protected long seed;
	//protected int nLinks, nNodes, purcentLinks, removedLinks;
	protected String random_s;
	private static String randomPath = "modulecoFramework.modeleco.randomeco.";
	private static boolean RPTest = true;
	ArrayList neighbours, chosen, chosenID, secondID;
	public ENeighbourRandomPairwiseWorld(int length) {
		super(length);
	}

	public void populate() {
		madeNewEworldClone();
	}

	public void initAll() {
		super.initAll();
	}
	/**
	 * At each time step, method inoked by SimulationControl.progress() Commit
	 * the Cagent state change. Dynamic reduilding of links by rebuildLinks()
     *  This final method cannnot be redefine
	 * @see modulecoFramework.modeleco.SimulationControl.progress()
	 * @see modulecoFramework.modeleco.EWorld
	 */
	public final void commitAll() { 
		super.commitAll();
		rebuildLinks();
	}
	/**
	 * dynamic links to be rebuilt at each step
	 */
	public void rebuildLinks() {
		madeNewEworldClone();
		connectAll(); // Ajoute AB-DP 19/09/2001 -revision des voisinages
	}
	public void buildRandomLinks() {

	}

	public void madeNewEworldClone() {

		/*
		* method invoked by World.populate() and World.commit() 
		*/
		if (chosen != null)
			chosen.clear();
		if (chosenID != null)
			chosenID.clear();
		else
			chosenID = new ArrayList();
		if (secondID != null)
			secondID.clear();
		else
			secondID = new ArrayList();

		chosen = (ArrayList) this.getAgents().clone();
		for (int i = 0; i < agentSetSize; i++) {
			chosenID.add(new Integer(i));
			secondID.add(new Integer(0));
		}
		//System.out.println("madeNewEworldClone()");
	}

	public ArrayList updateNeighbourhood(
		ArrayList neighbours,
		int index) { // Ajoute DP 19/09/2001 -revision des voisinages

		int alea, indexNeighbour;
		//neighbours.add((EAgent) world.get(index)); // modifié DP le 10/07/2002
		if (chosen.size() > 0) {
			;
			if ((chosenID.get(0)).equals(new Integer(index))) {
				chosen.remove(0);
				chosenID.remove(0);
				int test = 0;
				if (chosenID.size() > 1) {
					do {

						alea = (int) (random.getDouble() * (agentSetSize - 1));
						//(chosenID.size()-1)
						//Math.random() DP 5/08/2002
						test++;
						if (test > chosenID.size())
							alea = ((Integer) chosenID.get(0)).intValue();
					} while (!chosenID.contains(new Integer(alea)));
				} else
					alea = ((Integer) chosenID.get(0)).intValue();
				;
				indexNeighbour = alea;
				//((Integer)chosenID.get(alea)).intValue();

				secondID.add(index, new Integer(indexNeighbour));
				EAgent second = (EAgent) worldMother.get(indexNeighbour);
				neighbours.add(second);
				chosenID.remove(new Integer(indexNeighbour));
				//System.out.println("chosenID.remove(indexNeighbour) : "+chosenID.contains(new Integer(indexNeighbour)));
				chosen.remove(second);
				//System.out.println("chosen.remove(second) : "+chosen.contains(second));
			} else {
				indexNeighbour = secondID.indexOf(new Integer(index));
				neighbours.add((EAgent) worldMother.get(indexNeighbour));
				secondID.add(index, new Integer(indexNeighbour));
			}
		} else {
			indexNeighbour = secondID.indexOf(new Integer(index));
			neighbours.add((EAgent) worldMother.get(indexNeighbour));
			secondID.add(index, new Integer(indexNeighbour));
		}

		//System.out.println("Agent : "+((EAgent)neighbours.get(0)).getAgentID()+" Voisin : "+((EAgent)neighbours.get(1)).getAgentID());
		return neighbours;
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(
			new LongDataDescriptor(
				this,
				"PairwiseSeed",
				"seed",
				seed,
				true,
				3));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"PairwiseRandom",
				"random_s",
				new String[] { "Default", "JavaRandom" },
				random_s,
				true));

		return descriptors;
	}

	public void setSeed(long d) {
		seed = d;
		//System.out.println("Seed : "+seed);
		//setRandom_s(random_s); //sinon on cree un random a chaque fois...
		//On ne peut modifier la graine qu'au debut d'une simulation, 
		//pas en cours de simulation.
	}

	public void setRandom_s(String s) {

		random_s = s;
		try {
			//System.out.println("setRandom_s");
			Constructor constructor =
				Class.forName(randomPath + random_s).getConstructor(
					new Class[] { long.class });
			random =
				(CRandomDouble) constructor.newInstance(
					new Object[] { new Long(seed)});
		} catch (Exception e) {

			System.out.println(e.toString());
		}

	}

	public void setDefaultValuesAll() {
		super.setDefaultValuesAll();
		//declarations obligatoires
		RandomGeneratorsetDefaultValues();
		// fin declarations obligatoires
	}
	public void RandomGeneratorsetDefaultValues() {
		seed = 5;
		setRandom_s("JavaRandom");
	}

	public boolean randomPairwiseTest() {
		//boolean RPTest = vsname.equals("modulecoFramework.modeleco.zone.RandomPairwise");
		return RPTest;
	}

}
