/**
 * EWorldEditor
 * Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr, Denis.Phan@@enst-bretagne.fr,frederic.falempin@enst-bretagne.fr
 * @version 1.0 May 2000
 * @version 1.2  August 2002
 */
package modulecoGUI.grapheco;
import modulecoFramework.modeleco.EWorld;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.awt.Label;
import java.lang.reflect.Field;
/**
 * Editor of the World.
 * 
 * Je suis une interface abstraite que les différents modèles doivent dériver dans leur
 * package respectifs pour me spécialiser. @see{dp.WordEditor, dp.AgentEditor, life.AgentEditor]...<p>
 * ATTENTION: Mes sous classes doivent s'appeler AgentEditor et WorldEditor {@see grapheco.ControlPanel}<p>
 *
 */
public abstract class EWorldEditor extends EAdditionalPanel {
	public EWorld ag;
	public ControlPanel controlPanel;
	public boolean init;
	/**
	 * Create new from AgentEditor.
	 */
	public EWorldEditor() {
	}
	/**
	 * Abstract method to init the components of the World.
	 */
	protected abstract void initComponents();
	/**
	 * Abstract method to update the components of the World.
	 */
	public abstract void update();
	/**
	 * Build the World Editor.
	 */
	public void build() {
		initComponents();
		update();
	}
	/**
	 * Search the right method to update the field fieldName.
	 * 
	 * @param fieldName
	 */
	public void update(String fieldName) {
		Method method = null;
		try {
			/**
			 * First, find an update"fieldName" method in the WorldEditorPanel
			 */
			method = this.getClass().getMethod("update" + fieldName, null);
			method.invoke(this, null);
		} catch (SecurityException getMethodError1) {
			System.out.println(getMethodError1.toString());
		} catch (IllegalAccessException invokeError1) {
			System.out.println(invokeError1.toString());
		} catch (IllegalArgumentException invokeError1) {
			System.out.println(invokeError1.toString());
		} catch (InvocationTargetException invokeError1) {
			System.out.println(invokeError1.toString());
		} catch (NoSuchMethodException getMethodError1) {
			try {
				/**
				 * if no such method, find the method that get the text I method =
				 * this.getClass().getMethod("get"+fieldName,null); invoke it
				 */
				Object Parameters[] = new Object[]{method.invoke(this, null)};
				/**
				 * find its type
				 */
				Class textFieldType[] = new Class[]{method.getReturnType()};
				/**
				 * find the method that set the value to the world
				 */
				method = ag.getClass().getMethod("set" + fieldName,
						textFieldType);
				/**
				 * invoke it
				 */
				method.invoke(ag, Parameters);
				/**
				 * update label (must be public declared in the
				 * WorldEditorPanel)
				 */
				Field field = this.getClass().getField("label" + fieldName);
				Label label = (Label) field.get(this);
				method = this.getClass().getMethod("get" + fieldName, null);
				label.setText(fieldName + " ( " + method.invoke(this, null)
						+ " )");
				// 		System.out.println(this.getClass()+"."+field.getName()+" de
				// type "+field.getType());
				// 		Class test =
				// Class.forName(this.getClass()+"."+field.getName()+".setText");
				// 		method = test.getMethod("get"+fieldName,null);
			} catch (SecurityException getMethodError2) {
				System.out.println(getMethodError2.toString());
			} catch (IllegalAccessException invokeError2) {
				System.out.println(invokeError2.toString());
			} catch (IllegalArgumentException invokeError2) {
				System.out.println(invokeError2.toString());
			} catch (InvocationTargetException invokeError2) {
				System.out.println(invokeError2.toString());
			} catch (NoSuchMethodException getMethodError2) {
				System.out.println(getMethodError2.toString());
			} catch (NoSuchFieldException getFieldError2) {
				System.out.println(getFieldError2.toString());
			}
			//catch (ClassNotFoundException forNameError2) {
			// System.out.println(forNameError2.toString()); }
		} // noSuchMethodExecption
	}
	public void setEWorld(EWorld ew) {
		ag = ew;
	}
	public void setControlPanel(ControlPanel cp) {
		controlPanel = cp;
	}
}