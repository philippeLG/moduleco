/**
 * Title:        Moduleco<p>
 * Description:  Je suis une classe qui représente des donnees de type "boolean" <p>
 * Copyright:    Copyright (c)enst-bretagne
 * @author sebastien.chivoret@ensta.org
 * Created on may, 11, 2001
 * @version 1.2  august,5, 2002
 */
package modulecoGUI.grapheco.descriptor;
import java.util.ArrayList; //depuis JDK 1.2
import java.lang.reflect.Method;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.AbstractButton;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import modulecoFramework.modeleco.CAgent;
import java.io.PrintWriter; //CK 04/02
public class BooleanDataDescriptor extends DataDescriptor {
	//protected String name="";
	protected boolean value;
	protected JButton button;
	public BooleanDataDescriptor(CAgent ag, String cn, String n, boolean v,
			boolean e, int j) {
		completeName = cn;
		name = n;
		value = v;
		editable = e;
		agent = ag;
		labelForegroundColor = color[j];
		//System.out.println("BooleanDataDescriptor");
	}
	public BooleanDataDescriptor(CAgent ag, String cn, String n, boolean v,
			boolean e) {
		this(ag, cn, n, v, e, 0);
	}
	public boolean getValue() {
		return value;
	}
	public void setValue(boolean b) {
		value = b;
		System.out.println("BooleanDataDescriptor.setValue()");
	}
	// DP 29/06/2001
	public void makeContainer(ArrayList containerList) {
		super.makeContainer(containerList);
		//label.setText(""+value);
		label.setText("" + completeName);
		if (editable == true) {
			/* Button */button = new JButton();
			button.setBackground(Color.lightGray);
			button.setForeground(Color.black);
			button.setText("" + value);
			button.setFont(new Font("Dialog", 0, 11));
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					inverseBooleanValue(); //DP 18/10/2003
				}
			});
			container.add(button);
		} else { // A supprimer ? DP
			JLabel label2 = new JLabel(completeName);
			label2.setHorizontalAlignment(JLabel.CENTER);
			container.add(label2);
		}
	}
	//DP 29/06/2001
	public void inverseBooleanValue() { ////DP 18/10/2003
		value = !value; //DataDescriptor is changed
		button.setText("" + value);
		set();
		//agent.updateDescriptors();
		if (label.getForeground() != color[0])
			updateLabelForegroundColor();
	}
	public void updateLabelForegroundColor() {
		if (label.getForeground() == color[1]) {
			label.setForeground(color[2]);
			System.out.println("Bleu : " + label.getForeground());
		} else {
			label.setForeground(color[1]);
			System.out.println("Rouge :" + label.getForeground());
		}
	}
	public void updateContainer(JPanel container) {
		// double exécution
		((JLabel) container.getComponent(0)).setText(completeName);
		((JLabel) container.getComponent(0))
				.setForeground(labelForegroundColor);
		if (editable == true) {
			//System.out.println(container.getComponent(1));
			((AbstractButton) container.getComponent(1)).setText("" + value); //mise
																			  // au
																			  // point
		}
		//if(label.getForeground()!=color[0]) updateLabelForegroundColor();
	}
	public void set() {
		//if (editable==true){
		try {
			Class[] booleanType = {java.lang.Boolean.TYPE};
			Object[] booleanParameter = {new Boolean(value)};
			Method method;
			String modifiedName = name.substring(0, 1).toUpperCase()
					+ name.substring(1);
			method = agent.getClass().getMethod("set" + modifiedName,
					booleanType);
			method.invoke(agent, booleanParameter); //Agent is changed
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
	public void printCVS(PrintWriter printWriter) {
		printWriter.print(value);
		printWriter.print(',');
	}
	public void printTxt(PrintWriter printWriter) {
		printWriter.print(value + " ");
	}
	//}
}