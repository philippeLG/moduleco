/**
 * CalcultedVar.java
 *
 *
 * Created: Mon Sep 18 10:12:44 2000
 *
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */
package modulecoGUI.grapheco.statManager;
import java.lang.reflect.Method;
/**
 * This class is a Var, usualy contained by the statmanager. Each iteration,
 * the statmanager will compute this variable calling a method in the eWorld.
 * 
 * @see grapheco.statManager.StatManager
 */
public class Var {
	/**
	 * The name of the variable (the same as in statmanager).
	 * 
	 * @see grapheco.statManager.StatManager
	 */
	private String varName;
	/**
	 * The method the statmanager will use to access the variable value in the
	 * eworld.
	 * 
	 * @see grapheco.statManager.StatManager
	 */
	private Method getMethod;
	/**
	 * Usualy used in the constructor of a world this way: <BR>
	 * <CODE>statManager.add(new Var(.....);</CODE><BR>
	 * <BR>
	 * 
	 * @param varName
	 *            The variable name
	 * @param getMethod
	 *            The method the statmanager will use to access the variable
	 *            value in the eworld.
	 */
	public Var(String varName, Method getMethod) {
		this.varName = varName;
		this.getMethod = getMethod;
	}
	/**
	 * @return The variable name
	 */
	public String getName() {
		return varName;
	}
	/**
	 * @return The method the statmanager will use to access the variable value
	 *         in the eworld.
	 */
	public Method getMethod() {
		return getMethod;
	}
} // CalculatedVar
