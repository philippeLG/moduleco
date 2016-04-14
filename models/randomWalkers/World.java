/** class randomWalkers.World
 * Title:        Moduleco<p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@enst-bretagne.fr, frederic.falempin@enst-bretagne.fr, Gregory.Gackel@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 * @version 1.4. february 2004    
 */
package models.randomWalkers;
import java.util.Iterator;
import java.util.ArrayList;
import modulecoFramework.modeleco.mobility.EMobileWorld;
import modulecoFramework.modeleco.mobility.Move;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
import modulecoGUI.grapheco.descriptor.ChoiceDataDescriptor;
import modulecoGUI.grapheco.statManager.CalculatedVar;
public class World extends EMobileWorld {
	/**
	 * Initial values for the 4 basic parameters of this world <br>
	 * parameter 1: world size <br>
	 * parameter 2: neighbourhood type <br>
	 * parameter 3: active zone type <br>
	 * parameter 4: scheduler type <br>
	 */
	public static String initLength = "21";
	public static String initNeighbour = "NeighbourMoore";
	public static String initZone = "World";
	public static String initScheduler = "EarlyCommitScheduler";
	/**
	 *  
	 */
	//protected WorldEditorPanel wep;
	protected CRandomDouble random;
	protected String random_s;
	protected Move move;
	/*
	 * protected int position; protected int count;
	 */
	public World(int length) {
		super(length);
	}
	public void init() {
		try {
			statManager.add(new CalculatedVar("FromBeginning", Class.forName(
					this.pack() + ".Place").getMethod("distanceFromBeginning",
					null), CalculatedVar.SUM, null));
			//            statManager.add(new CalculatedVar("Living",
			// Class.forName(this.pack() + ".Place").getMethod("doILive", null),
			// CalculatedVar.NUMBER, new Boolean(true)));
			int tmp = (int) (5000 * random.getDouble())
					% this.getAgentSetSize();
			Place placeTmp = (Place) this.get(tmp);
			placeTmp.putAnt();
			tmp = (int) (5000 * random.getDouble()) % this.getAgentSetSize();
			placeTmp = (Place) this.get(tmp);
			placeTmp.putAnt();
			this.setMove(new MoveSimple()); //wep.getMove());
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
		} catch (NoSuchMethodException e) {
			System.out.println(e.toString());
		}
	}
	public void compute() {
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).compute();
		}
	}
	public Object getState() {
		return new Double(10);
	}
	protected void setRandom(CRandomDouble random) {
		this.random = random;
		for (Iterator i = iterator(); i.hasNext();) {
			((Place) i.next()).setRandom(random);
		}
	}
	//      public int getIndex(Place agent)
	//      {
	//         return this.indexOf(agent);
	//      }
	public void getInfo() {
		/*
		 * wep = (WorldEditorPanel)cwe; random = wep.getRandom();
		 * wep.update(this);
		 */
	}
	public void update() {
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new ChoiceDataDescriptor(this, "Random", "random_s",
				new String[]{"Default", "JavaRandom", "JavaGaussian"},
				random_s, true));
		return descriptors;
	}
	public void setRandom_s(String s) {
		random_s = s;
		try {
			random = (CRandomDouble) Class.forName(
					"modulecoFramework.modeleco.randomeco." + random_s)
					.newInstance();
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		} catch (InstantiationException e) {
			System.out.println(e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println(e.toString());
			random = null;
		}
	}
	public void setDefaultValues() {
		setRandom_s("Default");
	}
}