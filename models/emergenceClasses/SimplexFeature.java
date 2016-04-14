/**
* Title:   moduleco.models.emergenceClasses.SimplexFeature
* Reference : Axtell, Epstein, Young (2000)
* http://www.brookings.edu/es/dynamics/papers/classes/
* * Copyright:    Copyright (c)enst-bretagne
* @author denis.phan@enst-bretagne.fr
* * @version 1.4.1 april 2004  
*/

package models.emergenceClasses;

public class SimplexFeature {

	double axisHMValue, axisMLValue, axisLHValue;
	public String name;

	public SimplexFeature(double ahm, double aml, double alh, String s) {
		this.axisHMValue = ahm;
		this.axisMLValue = aml;
		this.axisLHValue = alh;
		this.name = s;
	}
	public SimplexFeature(double ahm, double aml, double alh) {
		this(ahm, aml, alh, null);
	}

	public void updateLandscape(double ahm, double aml, double alh, String s) {
		//System.out.println("SimplexFeature.updateLandscape()");
		this.axisHMValue = ahm;
		this.axisMLValue = aml;
		this.axisLHValue = alh;
		this.name = s;
	}

	public double getAxisHMValue() {
		return axisHMValue;
	}
	public double getAxisMLValue() {
		return axisMLValue;
	}
	public double getAxisLHValue() {
		return axisLHValue;
	}

	public String getName() {
		if (name != null)
			return name;
		else
			return null;
	}

	public void setName(String s) {
		this.name = s;
	}
}
