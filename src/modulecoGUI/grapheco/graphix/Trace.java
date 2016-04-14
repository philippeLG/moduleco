/**
 * Trace.java
 * description: This class defines a trace that can be then added to a
 *              grapheco.graphix.Graphique object.
 *
 * Created: Mon Sep 18 13:00:05 2000
 *
 * @author frederic.falempin@enst-bretagne.fr
 * @version 1.2  august,5, 2002
 */
package modulecoGUI.grapheco.graphix;
import java.awt.Color;
/**
 * This class represents a curve, linked to a statmanager variable. The curve
 * can be added to a graphique object, and then drawed by it each iteration.
 * 
 * @see grapheco.graphix.Graphique
 * @see grapheco.statManager.StatManager
 */
public class Trace {
	/**
	 * Used when you want the curve to be a continous line
	 */
	public static int LINE = 0;
	/**
	 * Used when you want the curve to be a set of point
	 */
	public static int POINT = 1;
	/**
	 * The name of the trace
	 */
	private String name;
	/**
	 * The name of the statmanager variable used to draw the trace
	 */
	private String varName;
	/**
	 * The color in which the trace should be represented
	 */
	private Color color;
	/**
	 * The type of trace. Default is LINE
	 */
	private int type = 0;
	/**
	 * Create a new trace. Usualy called in the constructor of a Graphique
	 * inheriting class this way: <BR>
	 * <CODE>add(new Trace(.....);</CODE><BR>
	 * <BR>
	 * The type of representation is set by default to LINE.
	 * 
	 * @param name
	 *            The name of the trace
	 * @param color
	 *            The color in which the trace should be represented
	 * @param varName
	 *            The name of the statmanager variable used to draw the trace
	 * @see graphix.statManager.StatManager
	 */
	public Trace(String name, Color color, String varName) {
		this.color = color;
		this.name = name;
		this.varName = varName;
	}
	/**
	 * Create a new trace. Usualy called in the constructor of a Graphique
	 * inheriting class this way: <BR>
	 * <CODE>add(new Trace(.....);</CODE><BR>
	 * <BR>
	 * 
	 * @param name
	 *            The name of the trace
	 * @param color
	 *            The color in which the trace should be represented
	 * @param varName
	 *            The name of the statmanager variable used to draw the trace
	 * @param type
	 *            The type of trace.
	 * @see graphix.statManager.StatManager
	 */
	public Trace(String name, Color color, String varName, int type) {
		this.color = color;
		this.name = name;
		this.varName = varName;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public String getVarName() {
		return varName;
	}
	public Color getColor() {
		return color;
	}
	public int getType() {
		return type;
	}
} // Trace
