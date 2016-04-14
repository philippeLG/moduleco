/** class life.Agent.java
 * Title:        Moduleco<p>
 * Description:  Implantation simple du jeu de la vie de Conway.<p>
 * Les constantes de vivacité sont liées à UNE instance. Ceci permet de créer
 * des "inégalités"...
 * Copyright:    Copyright (c)enst-bretagne
 * @author Antoine.Beugnard@enst-bretagne.fr
 * 
 * @version 1.2  august,5, 2002  
 */
package models.life;

import java.util.ArrayList;
import java.util.Iterator;

import modulecoFramework.modeleco.EAgent;
import modulecoGUI.grapheco.descriptor.IntegerDataDescriptor;
import modulecoGUI.grapheco.descriptor.BooleanDataDescriptor;

import modulecoFramework.medium.NeighbourMedium;

public class Agent extends EAgent {

	/**
	 * etat est l'état stable.
	 */
	protected boolean etat;
	protected int nVivants;
	protected int isole = 1;
	protected int etouffe = 3;
	protected int geniteur = 2;

	public Agent() {
		super();
		etat = false; //( (Math.random() >= 0.5) ? true : false);
	}

	public Object getState() {
		return new Boolean(etat); // pour affichage c'est celui là
	}

	/*
	 * Valider consiste à naitre ou mourir selon les "lois de la nature".
	 */
	public void commit() {
		if (nVivants <= isole || nVivants >= etouffe) {
			etat = false;
		} else {
			if (nVivants == geniteur) {
				etat = true;
			}
		}
		//if (ae != null) ae.update ();
	}

	public String toString() {
		return (new Boolean(etat)).toString();
	}

	public void inverseState() {
		etat = !etat;
		System.out.println("gant : " + agentID + " inverseState()");
	}

	/*
	 * Un pas consiste à compter ses voisins etats.
	 */
	public void compute() {
		nVivants = 0;
		if (world.getAgents() == neighbours) {
			//optimisation ok pour late, mais pas pour early !!!!
			nVivants = ((World) world).nbVivants;
		} else { //cas général
			for (Iterator i = neighbours.iterator(); i.hasNext();) {
				if (((Agent) i.next()).etat) {
					nVivants++;
				}
			}
		}
	}

	protected void setI(int m) {
		// On pourrait tester par rapport à la taille du Voisinage !
		//if ((m >=0) && (m < etouffe) && (m < geniteur))
		isole = m;
	}
	protected void setS(int m) {
		// On pourrait tester par rapport à la taille du Voisinage !
		//if ((m >=1) && (isole < m) && (m > geniteur))
		etouffe = m;
	}
	protected void setG(int m) {
		// On pourrait tester par rapport à la taille du Voisinage !
		//if ((m >=1) && (m > isole) && (m < etouffe))
		geniteur = m;
	}

	public void getInfo() {
		this.isole = ((World) world).getI();
		this.etouffe = ((World) world).getS();
		this.geniteur = ((World) world).getG();
	}

	public void init() {
		etat =
			((Math.random() >= ((World) world).getPregenerated())
				? false
				: true);
		neighbours = ((NeighbourMedium) mediums[0]).getNeighbours();
	}

	public ArrayList getDescriptors() {
		descriptors.clear();
		descriptors.add(
			new BooleanDataDescriptor(this, "Etat", "etat", etat, true));
		descriptors.add(
			new IntegerDataDescriptor(this, "Isole", "isole", isole, true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"Etouffe",
				"etouffe",
				etouffe,
				true));
		descriptors.add(
			new IntegerDataDescriptor(
				this,
				"Geniteur",
				"geniteur",
				geniteur,
				true));
		return descriptors;
	}

	public void inverseEtat() {
		etat = !etat;
		System.out.println(
			"gant : " + agentID + " inverseState() nrw stae = " + etat);
	}
	public void setIsole(int newIsole) {
		isole = newIsole;
		System.out.println("isole : " + isole);
	}
	public void setEtouffe(int newEtouffe) {
		etouffe = newEtouffe;
		System.out.println("etouffe : " + etouffe);
	}
	public void setGeniteur(int newGeniteur) {
		geniteur = newGeniteur;
		System.out.println("geniteur : " + geniteur);
	}

}
