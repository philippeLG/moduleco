/** class lux.Agent.java
 * Title:        Moduleco<p>
 * Description:
 * Copyright:    Copyright (c)enst-bretagne
 * @author denis.Phan@enst-bretagne.fr , Philippe LeGoff
 * * @version 1.2  august,5, 2002  
 */
package models.lux;
import modulecoFramework.modeleco.EAgent;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import java.util.ArrayList;
/**
 * 
 */
public class Agent extends EAgent {
	protected CRandomDouble random;
	protected Market market;
	//Gilles
	protected double v1 = 2, v2 = 0.6, v3 = 0.005, a1 = 0.6, a2 = 1.5, a3 = 1,
			/* k=4, */tc = 0.001, g = 0.01, R = 0.0004, s = 0.75;
	//Gilles
	//protected double
	// v1=2,v2=0.6,v3=0.005,a1=0.6,a2=1.5,a3=1,/*k=4,*/tc=0.001,g=0.01,R=0.0004,s=0.75;
	protected double p, dpdt, U1, U21, U22;
	protected int OPT = 0, PESS = 1, FOND = 2, state, N, b1, b2;
	protected int[] future_state;
	protected double pop_min = 0.08;
	protected double xopt, xpess, xfond;
	protected double pf, ed, t1, t2, fe, t;
	protected int horizon;
	public Agent() {
		super();
		//if (agentID==0)
		//System.out.println(" agent.constructeur");
		future_state = new int[3];
	}
	public void getInfo() {
		//if (agentID==0)
		//System.out.println(" agent.getInfo() ");
	}
	public void init() {
		//System.out.println("BEGINNING OF Agent.init()");
		//if (agentID==0)
		//System.out.println(" agent.init() ");
		this.random = ((World) world).random;
		//prob etat
		market = ((World) world).getMarket();
		state = ((World) world).getInitialState();
		N = ((World) world).getAgentSetSize();
		horizon = 1 + (int) (random.getDouble() * 5);
		//System.out.println("END OF Agent.init()");
	}
	public void compute() {
		//if (agentID==0)
		//System.out.println(" agent.compute() ");
		p = ((Market) market).getP();
		dpdt = ((Market) market).getDpdt(horizon);
		pf = ((Market) market).getPf();
		xopt = ((Market) market).getXopt();
		xpess = ((Market) market).getXpess();
		xfond = ((Market) market).getXfond();
		if (state == OPT) {
			ed = tc;
			if (xopt > pop_min) {
				U1 = a1 * (xopt - xpess) / (xopt + xpess) + a2 * (dpdt / p)
						/ v1;
				U21 = a3
						* (R * (pf / p - 1) + (dpdt / p) / v2 - s
								* java.lang.Math.abs((pf - p) / p));
				//U22=a3*(R*(1-pf/p)-(dpdt/p)/v2-s*java.lang.Math.abs((pf-p)/p));
				t1 = realisation(v1 * ((xopt + xpess) / N)
						* java.lang.Math.exp(-U1));
				t2 = realisation(v2 * (xfond / N) * java.lang.Math.exp(-U21));
				if (t1 < t2) {
					// Swap from OPT to PESS
					future_state[0] = -1;
					future_state[1] = 1;
					future_state[2] = 0;
					t = t1;
				} else {
					// Swap from OPT TO FUND
					future_state[0] = -1;
					future_state[1] = 0;
					future_state[2] = 1;
					t = t2;
				}
			} else {
				t = java.lang.Double.POSITIVE_INFINITY;
			}
		} else {
			if (state == PESS) {
				//Gilles
				ed = -tc;
				//Gilles
				//ed=-tc;
				if (xpess > pop_min) {
					U1 = a1 * (xopt - xpess) / (xopt + xpess) + a2 * (dpdt / p)
							/ v1;
					//U21=a3*(R*(pf/p-1)+(dpdt/p)/v2-s*java.lang.Math.abs((pf-p)/p));
					U22 = a3
							* (R * (1 - pf / p) - (dpdt / p) / v2 - s
									* java.lang.Math.abs((pf - p) / p));
					t1 = realisation(v1 * ((xopt + xpess) / N)
							* java.lang.Math.exp(U1));
					t2 = realisation(v2 * (xfond / N)
							* java.lang.Math.exp(-U22));
					if (t1 < t2) {
						// Swap from PESS to OPT
						future_state[0] = 1;
						future_state[1] = -1;
						future_state[2] = 0;
						t = t1;
					} else {
						// Swap from PESS to FUND
						future_state[0] = 0;
						future_state[1] = -1;
						future_state[2] = 1;
						t = t2;
					}
				} else {
					t = java.lang.Double.POSITIVE_INFINITY;
				}
			} else {
				ed = g * ((pf - p) / p);
				if (xfond > pop_min) {
					//U1=a1*(xopt-xpess)/(xopt+xpess)+a2*(dpdt/p)/v1;
					U21 = a3
							* (R * (pf / p - 1) + (dpdt / p) / v2 - s
									* java.lang.Math.abs((pf - p) / p));
					U22 = a3
							* (R * (1 - pf / p) - (dpdt / p) / v2 - s
									* java.lang.Math.abs((pf - p) / p));
					t1 = realisation(v2 * (xopt / N) * java.lang.Math.exp(U21));
					t2 = realisation(v2 * (xpess / N) * java.lang.Math.exp(U22));
					if (t1 < t2) {
						// Swap from FUND to OPT
						future_state[0] = 1;
						future_state[1] = 0;
						future_state[2] = -1;
						t = t1;
					} else {
						// Swap from FUND TO PESS
						future_state[0] = 0;
						future_state[1] = 1;
						future_state[2] = -1;
						t = t2;
					}
				} else {
					t = java.lang.Double.POSITIVE_INFINITY;
				}
			}
		}
	}
	public void commit() {
		//if (agentID==0)
		//System.out.println(" agent.commit() ");
		((Market) market).analyze(this, t, future_state, ed);
	}
	public Object getState() {
		//System.out.println(" agent.getState() ");
		return new Integer(state);
	}
	public int getEtat() {
		return state;
	}
	public void setState(int[] i) {
		if (i[0] == 1)
			state = 0;
		if (i[1] == 1)
			state = 1;
		if (i[2] == 1)
			state = 2;
	}
	public void setHorizon(int i) {
		horizon = i;
	}
	public boolean getBooleanState() {
		return true;
	}
	public void inverseState() {
	}
	protected void setRandom(CRandomDouble random) {
		this.random = random;
	}
	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(new IntegerDataDescriptor(this, "state", "state",
				state, false));
		descriptors.add(new IntegerDataDescriptor(this, "horizon", "horizon",
				horizon, true));
		descriptors.add(new DoubleDataDescriptor(this, "t", "t", t, false, 3));
		descriptors
				.add(new DoubleDataDescriptor(this, "ed", "ed", ed, false, 3));
		return descriptors;
	}
	public double realisation(double d) {
		if (d > 0)
			return -java.lang.Math.log(random.getDouble()) / d;
		else
			return java.lang.Double.POSITIVE_INFINITY;
	}
	public double getEd() {
		return ed;
	}
}