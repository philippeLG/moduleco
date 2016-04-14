/**
 * Title:        Modeleco<p>
 * Description:  
 * Copyright : (c)enst-bretagne
 * @author Denis.Phan@enst-bretagne.fr
 * created may 2000
 * @version 1.2  july,20, 2002
 * @version 1.4.2 june 2004  
 */
package modulecoFramework.modeleco;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.reflect.Constructor;

//import modulecoFramework.medium.Medium;
//import modulecoFramework.medium.MeanMedium;

import modulecoFramework.modeleco.randomeco.CRandomDouble;

import modulecoGUI.grapheco.descriptor.InfoDescriptor;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.descriptor.LongDataDescriptor;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;

public abstract class ENeighbourSmallWorld
	extends ENeighbourWorld
	implements StructuredNeighbourWorld {

	protected CRandomDouble random;
	protected long seed;
	protected int nLinks, nNodes, purcentLinks, removedLinks;
	protected String random_s;
	private static String randomPath = "modulecoFramework.modeleco.randomeco.";
	public ENeighbourSmallWorld(int length) {
		super(length);
		//System.out.println("ENeighbourSmallWorld()");
	}

	public void initAll() {
		super.initAll();
		//executed after all agent.init()
		nLinks = countLinks();
		purcentLinks = (int) ((double) nNodes / (double) nLinks * 100.0);
		rebuildLinks();
	}
	public void rebuildLinks() {
		for (int h = 0; h < nNodes; h++)
			buildRandomLinks();
	}
	public void buildRandomLinks() {

		int alea,
			indexNeighbour,
			newNeighbourID,
			alea1,
			alea2,
			nbNeighbours,
			indexTemp,
			v;
		ArrayList aNeighbours = new ArrayList();
		EAgentLinks exNeighbour, newNeighbour;
		int exNeighbourID = 0;
		boolean test;
		//int indexNeighbour = 0;
		// on tire un agent
		alea = (int) (random.getDouble() * (agentSetSize - 1));
		// on cherche cet agent
		for (Iterator i = iterator(); i.hasNext();) {
			EAgentLinks a = (EAgentLinks) i.next();
			if (a.getAgentID() == alea) {
				for (int k = 0; k < removedLinks; k++) {

					//On crée un ArrayList des voisins de cet agent
					if (aNeighbours != null)
						aNeighbours.clear();
					aNeighbours = (ArrayList) a.getNeighbours().clone(); //
					// nombre de ces voisins
					nbNeighbours = aNeighbours.size();
					//System.out.println("agentID : "+a.getAgentID()+" k = "+k+" aNeighbours.size()"+nbNeighbours);
					// on tire un lien à déplacer
					if (k > 0) {
						// avoid to remove the last added link
						v = 0;
						do {
							test = false;
							indexNeighbour =
								(int) (random.getDouble() * (nbNeighbours - 1));
							v++;
							if (indexNeighbour == nbNeighbours - 1)
								test = true;
							if (v > 1000)
								test = false;
						} while (test);
						//if (v>2) System.out.println("agentID : "+a.getAgentID()+" v = "+v);
					} else
						indexNeighbour =
							(int) (random.getDouble() * (nbNeighbours - 1));

					//exNeighbourID==((EAgent) aNeighbours.get(indexNeighbour)).getAgentID()
					// on intitule cet agent exNeighbourg 
					exNeighbour = (EAgentLinks) aNeighbours.get(indexNeighbour);
					// Catch index out of Bound exception index = O size =O

					//System.out.println("agent "+alea+" exNeighbourID = "+exNeighbourID+" K ="+k);
					// On teste l'agentID des voisins de a afin de ne pas
					// recreer un lien existant
					int[] neighbourID;
					v = 0;
					neighbourID = new int[nbNeighbours];
					for (Iterator g = aNeighbours.iterator(); g.hasNext();) {
						EAgentLinks ag = (EAgentLinks) g.next();
						neighbourID[v] = ag.getAgentID();
						v++;
					}
					// On tire l'agentID d'un nouveau voisin
					int loop = 0;
					do {
						v = 0;
						do {
							test = false;
							newNeighbourID =
								(int) (random.getDouble() * (agentSetSize - 1));
							v++;
							if (newNeighbourID == alea)
								test = true;
							if (newNeighbourID == exNeighbourID)
								test = true;
							if (v < 100)
								test = true;
							//avoid to buid a self-refereced link or to restore the last removed link
						} while (test);
						test = false;
						for (int w = 0; w < nbNeighbours; w++)
							if (neighbourID[w] == newNeighbourID)
								test = true;
						//else System.out.println("agent "+alea+" newNeighbourID= "+newNeighbourID);
						loop++;
						if (loop > 5000) {
							test = false;
							//System.out.println("loop > 5000");
						}
					}
					while (test); //verifier

					// on reléve l'agentID corresponant au lien enlevé
					// Rem cette ligne était neutralisée dans la version boguée par :
					//if (loop==1000)//System.out.println(loop+ " test ="+test);
					//Rem => exNeighbourID =0 donc on enlevais toujours le lien avec l'agent O pour l'agent a
					// Rem et le lien avec l'agent a pour l'agent 0;
					// Rem : il est curieux que cela n'ait pas fait d'erreur !!
					exNeighbourID = exNeighbour.getAgentID();
					// on enlève l'ancien lien à l'agent a
					a.neighbours.remove(exNeighbour);
					// on enlève l'ancien lien à l'agent exNeighbourg
					exNeighbour.neighbours.remove(a);
					// on cherche l'agent correspondant au nouveau lien
					exNeighbour.updateConnectivityIndex();
					for (Iterator j = iterator(); j.hasNext();) {
						newNeighbour = (EAgentLinks) j.next();
						if (newNeighbour.getAgentID() == newNeighbourID) {
							// on rajoute le nouveau lien à l'agent a
							a.neighbours.add(newNeighbour);
							a.updateConnectivityIndex();
							// on rajoute le nouveau lien à l'agent newNeighbourg
							newNeighbour.neighbours.add(a);
							newNeighbour.updateConnectivityIndex();
							//a.temp(); //mise au point
							//newNeighbour.temp(); //mise au point
						}
					}

				}

			}

		}

	}

	public int countLinks() {
		int links = 0;
		int minLinks = this.agentSetSize;
		int maxLinks = 0;

		for (Iterator k = iterator(); k.hasNext();) {
			EAgentLinks agt = (EAgentLinks) k.next();
			if (agt.neighbours.size() > maxLinks)
				maxLinks = agt.neighbours.size();
			if (agt.neighbours.size() < minLinks)
				minLinks = agt.neighbours.size();
			links = links + (agt.neighbours.size() / 2);
		}
		//System.out.println("countLinks() ; links = "+links+" Max = "+maxLinks+" Min = "+ minLinks );

		return links;
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		//descriptors.add(new IntegerDataDescriptor(this,"S1 against S1","s1AgainstS1",s1AgainstS1,true));
		descriptors.add(
			new LongDataDescriptor(this, "Seed", "seed", seed, true, 3));
		descriptors.add(
			new ChoiceDataDescriptor(
				this,
				"Random",
				"random_s",
				new String[] { "Default", "JavaRandom", "JavaGaussian" },
				random_s,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"total links",
				"nLinks",
				nLinks,
				false));
		descriptors.add(new InfoDescriptor("", ""));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"modif.nodes",
				"nNodes",
				nNodes,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"removed Links",
				"removedLinks",
				removedLinks,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"% mod.links",
				"purcentLinks",
				purcentLinks,
				false));
		descriptors.add(new InfoDescriptor("", ""));

		return descriptors;
	}
	public void setNNodes(int no) {
		nNodes = no;
	}

	public void setNLinks(int nl) {

		nLinks = nl;
	}

	public void setPurcentLinks(int pl) {
		purcentLinks = pl;
	}

	public void setRemovedLinks(int rl) {

		removedLinks = rl;
	}

	public void updateRemovedLinks(int ul) {

		removedLinks = removedLinks + ul;
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
		seed = 10;
		setRandom_s("JavaRandom");
		nNodes = 1;
		removedLinks = 1;
	}

}
