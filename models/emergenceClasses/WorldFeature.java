/**
* Title:   moduleco.models.emergenceClasses.WorldFeature
* Reference : Axtell, Epstein, Young (2000)
* http://www.brookings.edu/es/dynamics/papers/classes/
* * Copyright:    Copyright (c)enst-bretagne
* @author denis.phan@enst-bretagne.fr
* * @version 1.4.1 april 2004  
*/

package models.emergenceClasses;

public class WorldFeature {

	protected World world;
	protected String name;
	protected SimplexFeature sf1, sf2, sf3, mixedNashEquilibrium;

	public WorldFeature(World w) {
		this.world = w;
		buildSimplexZones(null);
	}
	public WorldFeature(World w, String name) {
		this.world = w;
		this.name = name;
		buildSimplexZones(name);
	}
	public void buildSimplexZones(String name) {
		/**
		* Compute the value of the best reply zones frontiers 
		*/

		double z1m =
			(double) world.PayoffMatrix[2][1]
				/ (double) world.PayoffMatrix[1][2];
		//0.6;
		double z1h = 1 - z1m; //0.4;
		double z1l = 0.0;
		sf1 = new SimplexFeature(z1h, z1m, z1l);
		//sf1.setName(name);
		double z2l =
			(double) world.PayoffMatrix[1][1]
				/ (double) world.PayoffMatrix[0][2];
		// 5.0/7.0;
		double z2m = 1.0 - z2l; // 2.0/7.0;
		double z2h = 0.0;
		sf2 = new SimplexFeature(z2h, z2m, z2l);
		//sf2.setName(name);
		double z3l =
			(double) world.PayoffMatrix[2][0]
				/ (double) world.PayoffMatrix[0][2];
		//3.0/7.0;
		double z3m = 0.0;
		double z3h = 1 - z3l; //4.0/7.0;
		sf3 = new SimplexFeature(z3h, z3m, z3l);
		//sf2.setName(name);
		/**
		*compute the mixedNashEquilibria
		*/
		double sigmaH = z1h; // 1.0 - 2.0 * t; // (14.0/35.0)
		double sigmaM = z2m * z1m;
		//(t - 2.0 * (double)Math.pow(t, 2D)) / (1.0 - t);// (6.0/35.0)
		double sigmaL = z3l; // t / (1.0 - t); // (15.0/35.0)
		mixedNashEquilibrium = new SimplexFeature(sigmaH, sigmaM, sigmaL);
		//mixedNashEquilibrium.setName(name);
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