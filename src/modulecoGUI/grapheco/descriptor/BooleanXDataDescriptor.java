/**
 * Title:        Moduleco<p>
 * Description:  Je suis une classe qui représente des donnees de type "boolean" <p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author sebastien.chivoret@ensta.org
 * Created on may, 11, 2001
 * @version 1.2  august,5, 2002
 */
package modulecoGUI.grapheco.descriptor;
import java.lang.reflect.Method;
import modulecoFramework.modeleco.CAgent;
public class BooleanXDataDescriptor extends BooleanDataDescriptor {
	int id;
	public BooleanXDataDescriptor(CAgent ag, String cn, String n, boolean v,
			boolean e, int j) {
		super(ag, cn, n, v, e, 0);
		this.id = j;
	}
	public BooleanXDataDescriptor(CAgent ag, String cn, String n, boolean v,
			boolean e) {
		super(ag, cn, n, v, e, 0);
	}
	public void set() {
		//if (editable==true){
		try {
			Class[] booleanType = {java.lang.Boolean.TYPE,
					java.lang.Integer.TYPE};
			Object[] parameters = {new Boolean(value), new Integer(id)};
			Method method;
			String modifiedName = name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			method = agent.getClass().getMethod("set" + modifiedName,
					booleanType);
			method.invoke(agent, parameters); //Agent is changed
		} catch (NoSuchMethodException e) {
			System.out.println("NoSuchMethodException");
			String modifiedName = name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			System.out.println("PUBLIC method set" + modifiedName
					+ " Invoked by BooleanDataDescriptor not found in "
					+ agent.getClass().getName());
		} catch (java.lang.IllegalAccessException e) {
			System.out.println("java.lang.IllegalAccessException");
		} catch (java.lang.reflect.InvocationTargetException e) {
			System.out
					.println((this.getClass()).getName() + " " + e.toString());
		}
	}
}