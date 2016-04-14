///*
// * Created on 27 mai 2004
// *
// * To change the template for this generated file go to
// * Window - Preferences - Java - Code Generation - Code and Comments
// */
//package models.ising2;
//import java.util.Random;
// 
///**
// * @author vsemesh
// *
// * To change the template for this generated type comment go to
// * Window - Preferences - Java - Code Generation - Code and Comments
// */
//public class RandomGenerator {
//	static long seed = System.currentTimeMillis();
//	static int seedint = (int)seed;
//	public static Random random = new Random();
////	public static cern.jet.random.Uniform generatoruniform;
////	generatoruniform = new cern.jet.random.Uniform(LOW_PREFERENCE,HIGH_PREFERENCE,SEED);
////	public static final double LOW_PREFERENCE = -1.7;
////	public static final double HIGH_PREFERENCE = 1.7;
//	
//	public static double getRandLogistic(){
//		double U = random.nextDouble();
//		//System.out.println("the random number is "+U);
//		return  (-(1.0/World.BETA)*(Math.log((1-U)/U)));
//	}
////	public static double getNumber() {
////	return generatoruniform.nextDouble();
////}
//	
//	public static void setSeed(long s){
//		long seed=s;
//	}
//	public static long getSeed (){
//		return seed ;
//	}
//}
//
