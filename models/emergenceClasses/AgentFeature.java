/**
* Title:   moduleco.models.emergenceClasses.AgentFeature
* Reference : Axtell, Epstein, Young (2000)
* http://www.brookings.edu/es/dynamics/papers/classes/
* * Copyright:    Copyright (c)enst-bretagne
* @author denis.phan@enst-bretagne.fr
* * @version 1.4.1 april 2004  
*/

package models.emergenceClasses;

import java.util.Iterator;
import java.util.ArrayList;

public class AgentFeature {

	double axis1Value, axis2Value, axis3Value;
	public String name;
	protected Agent ag;
	protected int agentMemoryLength;
	protected ArrayList strategyMemory;
	protected double lowFrequency, mediumFrequency, highFrequency;
	protected SimplexFeature asf;

	public AgentFeature(Agent a, int aml) {
		this.ag = a;
		this.agentMemoryLength = aml;
		strategyMemory = new ArrayList();
	}
	public void computeStrategicLandscape() {
		//DOUBLE EMPLOI AVEC COMPUTE NEXT STRATEGY
		int lowCount = 0;
		int mediumCount = 0;
		int highCount = 0;

		for (Iterator i = strategyMemory.iterator(); i.hasNext();) {
			int x = ((Number) i.next()).intValue();
			if (x == 0)
				lowCount++;
			if (x == 1)
				mediumCount++;
			if (x == 2)
				highCount++;
		}
		lowFrequency = (double) lowCount / (double) agentMemoryLength;
		mediumFrequency = (double) mediumCount / (double) agentMemoryLength;
		highFrequency = (double) highCount / (double) agentMemoryLength;
		//updateStrategicLandscape(lowFrequency,mediumFrequency,highFrequency);
	}
	public void buildSimplexFeature() {
		asf = new SimplexFeature(lowFrequency, mediumFrequency, highFrequency);
	}
	public void updateStrategicLandscape() {
		//System.out.println("AgentFeature.updateStrategicLandscape()");
		asf.updateLandscape(lowFrequency, mediumFrequency, highFrequency, null);
	}

	public double getAxis1Value(String s) {
		return axis1Value;
	}

	public double getAxis2Value(String s) {
		return axis2Value;
	}

	public double getAxis3Value(String s) {
		return axis3Value;
	}

	public String getName() {
		if (name != null)
			return name;
		else
			return "Unnamed";
	}

	public void setName(String name) {
		this.name = name;
	}

}
