/** class market.Competitor.java
 * Title:        Moduleco<p>
 * Description:  EAgent who represents one competitor company
 * Copyright:    Copyright (c)enst-bretagne
 * @author philippe LeGoff, Denis.Phan@enst-bretagne.fr
 * @version 1.1  july,10, 2001  ; Revision 1 (28 July 2001 DP)
 * @version 1.4. february 2004  
 */
package models.twoPartTarifCompetition;
// import java.util.Iterator;
import java.util.ArrayList;
import modulecoFramework.modeleco.EAgent;
import modulecoFramework.modeleco.randomeco.CRandomDouble;
import modulecoGUI.grapheco.descriptor.DoubleDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;
public class Competitor extends EAgent {
	/**
	 * state is the actual state . stateC is the computing state
	 */
	protected int firmID;
	/**
	 * Competitor's data Control variables : subscription (access) and usage
	 * price Parameters (costs) : customer (access) and usage cost resulting
	 * variables : activity level (in unit of usage product) profit
	 * decomposition : profit by customer (subsciptor), profit by usage unit ,
	 * total current profit, total cumulative profit
	 */
	protected double subscription, price;
	protected int customers;
	protected double CustomerCost, UsageCost;
	protected double subProfit, useProfit, profit, cumulativeProfit;
	protected double activity;
	protected boolean endogeneous;
	protected double earlyAdopters;
	/**
	 * random
	 */
	protected CRandomDouble random;
	protected Market market;
	public Competitor() {
		//System.out.println("ConstructeurCompetitorAlone");
	}
	public Competitor(int num) {
		firmID = num + 1;
		//System.out.println(" ConstructeurnumCompetitor = "+firmID);
	}
	public void getInfo() {
		//System.out.println(" Competitor getInfo()DEBUT");
		// voir l'origine des variables (world, additional panel..;
		// vérifier qu'elles proviennent bien de la bonne class
		try {
			/*
			 * this.random = ((World) world).random; aWep = (AdditionalPanel)
			 * ((World) world).aWepList.get(firmID-1); subscription =
			 * aWep.getSubscription(); CustomerCost = aWep.getCustomerCost();
			 * price = aWep.getPrice(); UsageCost = aWep.getUsageCost();
			 * subProfit = subscription - CustomerCost; useProfit = price -
			 * UsageCost;
			 */
			subscription = 0.4;
			CustomerCost = 0.3;
			price = 0.2;
			UsageCost = 0.1;
			subProfit = subscription - CustomerCost;
			useProfit = price - UsageCost;
			earlyAdopters = 0.01;
		} catch (IndexOutOfBoundsException e) {
			System.out.println(e.toString());
		}
		//System.out.println(" Competitor getInfo()FIN");
	}
	public Object getState() {
		//System.out.println(" Competitor.getState() = "+firmID);
		return new Integer(firmID);
	}
	/**
	 * commit() get agregate demand (activity) from the market get
	 */
	public void commit() {
		//System.out.println(" Competitor.commit()DEBUT ");
		//int subscriptions;
		activity = market.getDemand(firmID);
		customers = market.getCustomers(firmID);
		profit = (customers * subProfit) + (activity * useProfit);
		//System.out.println("Profit "+firmID+" :"+profit);
		cumulativeProfit += profit;
		//if (aWep != null) aWep.update ();
		//System.out.println("..." +activity+" "+profit+" "+cumulativeProfit);
		//System.out.println(" Competitor.commit()FIN");
	}
	public String toString() {
		//System.out.println(" Competitor.toString() ");
		return (new Integer(firmID)).toString();
	}
	public void compute() {
		market.givePrice(price, subscription, firmID);
		//System.out.println(" Competitor.compute() "+firmID);
	}
	public void inverseState() {
	}
	public void init() {
		market = ((World) world).getMarket();
		profit = 0;
		cumulativeProfit = 0;
	}
	public void setMenuPrice() {
		market.givePrice(price, subscription, firmID);
	}
	public ArrayList getDescriptors() {
		//DoubleDataDescriptor(CAgent ag, String textLabel, String
		// nameVariable, double doubleVariable, boolean editable)
		//DoubleDataDescriptor(CAgent ag, String textLabel, String
		// nameVariable, double doubleVariable, boolean editable, int precision)
		//DoubleDataDescriptor(CAgent ag, String textLabel, String
		// nameVariable, double doubleVariable, boolean editable, int precision,
		// int codeColor)
		descriptors.clear();
		descriptors.add(new DoubleDataDescriptor(this, "subscription",
				"subscription", subscription, true, 3, firmID));
		descriptors.add(new DoubleDataDescriptor(this, "CustomerCost",
				"CustomerCost", CustomerCost, true, 3, firmID));
		descriptors.add(new DoubleDataDescriptor(this, "price", "price", price,
				true, 3, firmID));
		descriptors.add(new DoubleDataDescriptor(this, "usageCost",
				"usageCost", UsageCost, true, 3, firmID));
		descriptors.add(new DoubleDataDescriptor(this, "earlyAdopters",
				"earlyAdopters", earlyAdopters, true, 3, firmID));
		descriptors.add(new DoubleDataDescriptor(this, "activity", "activity",
				activity, false, 2, firmID));
		descriptors.add(new DoubleDataDescriptor(this, "profit", "profit",
				profit, false, 2, firmID));
		descriptors.add(new DoubleDataDescriptor(this, "cumulativeProfit",
				"cumulativeProfit", cumulativeProfit, false, 2, firmID));
		descriptors.add(new BooleanDataDescriptor(this, "endogeneous",
				"endogeneous", endogeneous, true));
		return descriptors;
	}
	public double getEarlyAdopters() {
		return earlyAdopters;
	}
	public void setEarlyAdopters(double d) {
		earlyAdopters = d;
	}
	public void setSubscription(double d) {
		subscription = d;
	}
	public void setCustomerCost(double d) {
		CustomerCost = d;
	}
	public void setPrice(double d) {
		price = d;
	}
	public void setUsageCost(double d) {
		UsageCost = d;
	}
	public void setActivity(double d) {
		activity = d;
	}
	public void setProfit(double d) {
		profit = d;
	}
	public void setCumulativeProfit(double d) {
		cumulativeProfit = d;
	}
	public void setEndogeneous(boolean b) {
		endogeneous = b;
	}
}