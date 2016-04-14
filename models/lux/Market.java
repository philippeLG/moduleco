/**
 * Title: medium.Market.java
 * Description: medium concept applied to economic market
 * @author Philippe.legoff@enst-bretagne.fr
 * @version 1.2  august,5, 2002  
 */
package models.lux;
import modulecoFramework.modeleco.EAgent;
import modulecoFramework.modeleco.EWorld;
import modulecoFramework.medium.Medium;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
import java.util.Vector;
import java.io.BufferedWriter;
/**
 * 
 */
public class Market extends Medium {
	protected EWorld eWorld;
	protected double xopt, xpess, xfond;
	protected Vector P, T, Pf;
	protected double t; // Duration of the quickest event among agents
	protected double E; // Excess Demand
	//Gilles
	protected double v3; // Transition Rate for Pf to change value
	//Gilles
	protected EAgent agent;
	protected int[] future_state;
	protected int N;
	protected CRandomDouble random;
	protected double beta, mu, deltaP;
	//protected Auctioneer auctioneer;
	protected BufferedWriter out;
	public Market() {
		super();
		//System.out.println("market() -- START");
		T = new Vector();
		P = new Vector();
		Pf = new Vector();
		//System.out.println("market() -- END");
	}
	public void setEWorld(EWorld e) {
		eWorld = e;
	}
	public void init() {
		//System.out.println("market.init() -- START");
		T.add(new Double(0));
		P.add(new Double(100));
		Pf.add(new Double(100));
		E = 0;
		t = java.lang.Double.POSITIVE_INFINITY;
		N = ((World) eWorld).getAgentSetSize();
		this.random = ((World) eWorld).random;
		getValues();
		//System.out.println("market.init() - END");
	}
	public void getValues() {
		xopt = ((World) eWorld).getXopt();
		xpess = ((World) eWorld).getXpess();
		xfond = ((World) eWorld).getXfond();
		beta = ((World) eWorld).getBeta();
		mu = ((World) eWorld).getMu();
		deltaP = ((World) eWorld).getDeltaP();
		v3 = ((World) eWorld).getV3();
	}
	/**
	 * For each agent ...
	 */
	public void analyze(EAgent ag, double d, int[] s, double e) {
		// Compute the Excess Demand
		// The Excess Demand is the sum of the Demand of all agents
		E += e;
		// If the current agent is the quickest, he is selected
		// t is the smallest duration of all agents
		// t will be compared, later, to the duration of P and Pf to change
		if (d < t) {
			agent = ag;
			t = d;
			future_state = s;
			//System.out.println("futur : "+future_state[0]+"
			// "+future_state[1]+" "+future_state[2]);
		}
	}
	/**
	 * WARNING: Contrary to Boitout et al., the variations of Pf is in parallel
	 * of the other 8 events, i.e. the 6 possible swap between OPT, PESS and
	 * FUND and the 2 possible variations of P.
	 * 
	 * Here, newPf is called at each time step of the simulation, in World
	 */
	public void newPf() {
		//Gilles
		double t1 = realisation(v3); // Waiting time for Pf to increase
		double t2 = realisation(v3); // Waiting time for Pf to decrease
		//if (t1<t2)
		if (java.lang.Math.random() < 0.5)
			Pf.add(new Double(getPf() + deltaP));
		else
			Pf.add(new Double(getPf() - deltaP));
		//Gilles
		//Pf.add(new Double(getPf())); //to be changed (log changes are
		// gaussian)t1
	}
	/**
	 * Decide if P changes or not P is in competition with the quickest agent
	 */
	public void newP() {
		double t1 = realisation(java.lang.Math.max(0, beta * (E + mu)));
		double t2 = realisation(-java.lang.Math.min(0, beta * (E + mu)));
		//Gilles
		//System.out.println("t / t1 / t2 = " + t + " / " + t1 + " / " + t2);
		//Gilles
		//Gilles
		double dtmin = java.lang.Math.min(t, java.lang.Math.min(t1, t2));
		if (dtmin == t) {
			// t is the smallest
			P.add(new Double(getP()));
			T.add(new Double(getT() + t));
			adjust();
			System.out.println("T=" + (int) getT());
		}
		if (dtmin == t1) {
			// t1 is the smallest (smallest duration, i.e. quickest event)
			P.add(new Double(getP() + deltaP));
			T.add(new Double(getT() + t1));
			System.out.println("T=" + (int) getT() + ", price up P="
					+ (int) getP());
		}
		if (dtmin == t2) {
			// t2 is the smallest
			P.add(new Double(getP() - deltaP));
			T.add(new Double(getT() + t2));
			System.out.println("T=" + (int) getT() + ", price down P="
					+ (int) getP());
		}
		//Gilles
		/**
		 * if(t1 <t2) if (t1 <t){ // t1 is the smallest (smallest duration, i.e.
		 * quickest event) P.add(new Double(getP()+deltaP)); T.add(new
		 * Double(getT()+t1)); System.out.println("T=" +getT()+ " t1 wins P=" +
		 * getP()); } else if (t2 <t){ // t2 is the smallest P.add(new
		 * Double(getP()-deltaP)); T.add(new Double(getT()+t2));
		 * System.out.println("T=" +getT()+ " t2 wins P=" + getP()); } if ((t
		 * <=t1)&(t <=t2)){ // t is the smallest P.add(new Double(getP()));
		 * T.add(new Double(getT()+t)); adjust(); System.out.println("T="
		 * +getT()); }
		 */
	}
	/**
	 * Adjusts the proportion of OPT, PESS and FUND Is used when P and Pf are
	 * unchanged
	 */
	public void adjust() {
		xopt += ((double) future_state[0]) / N;
		xpess += ((double) future_state[1]) / N;
		xfond += ((double) future_state[2]) / N;
		((Agent) agent).setState(future_state);
	}
	public double getT() {
		return ((Double) T.lastElement()).doubleValue();
	}
	public double getP() {
		return ((Double) P.lastElement()).doubleValue();
	}
	public double getPf() {
		return ((Double) Pf.lastElement()).doubleValue();
	}
	public double getDpdt(int i) {
		if (P.size() < i) {
			//return 0;
			return (((Double) P.lastElement()).doubleValue() - ((Double) P
					.firstElement()).doubleValue())
					/ P.size();
		} else {
			return (((Double) P.lastElement()).doubleValue() - ((Double) P
					.get(P.size() - i)).doubleValue())
					/ i;
		}
	}
	public double getXopt() {
		return xopt;
	}
	public double getXpess() {
		return xpess;
	}
	public double getXfond() {
		return xfond;
	}
	public int[] getFuture_state() {
		return future_state;
	}
	public double getE() {
		return E;
	}
	public void setXopt(double d) {
		xopt = d;
	}
	public void setXpess(double d) {
		xpess = d;
	}
	public void setXfond(double d) {
		xfond = d;
	}
	/**
	 * Uses the Exp Law to return the duration of an event of transition rate
	 * beta Returns the "waiting time" (cf Boitout et al.)
	 */
	public double realisation(double beta) {
		if (beta > 0)
			return -java.lang.Math.log(random.getDouble()) / beta;
		else
			return java.lang.Double.POSITIVE_INFINITY;
	}
	public void reset() {
		E = 0;
		t = java.lang.Double.POSITIVE_INFINITY;
	}
	public void affiche() {
		// BUG : A REVOIR : TOURNE, AFFICHE MAIS N'ECRIT PAS DANS LE FICHIER !!!
		try {
			String temporaryString = ""
					+ ((Double) T.lastElement()).doubleValue() + " ; "
					+ ((Double) P.lastElement()).doubleValue() + " ; "
					+ ((Double) Pf.lastElement()).doubleValue();
			out.write(temporaryString);
			out.newLine();
			//System.out.println("Lux.market.affiche() : "+temporaryString);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	public void closeFile() {
		try {
			out.close();
		} catch (Exception e) {
			System.out.println("[Market.closeFile()] Error while closing the file: " + e.toString());
		}
	}
}