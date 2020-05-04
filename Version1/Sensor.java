/*
* Nom		AGHARMIOU
* Prénom	TANINA
*
* Nom		VUKOVIC
* Prénom	REMZI
*
* Module	Mobilité
* Année		2019/2020
* Cursus	M2 IMPAIRS
*
*/
package source;

import io.jbotsim.core.Color;
import io.jbotsim.core.Message;
import io.jbotsim.core.Node;

public class Sensor extends Node {
	Node parent = null;
	int battery = 255;

	public int getBattery() {
		return this.battery;
	}

	@Override
	public void onMessage(Message message) {
		// "INIT" flag : construction of the spanning tree
		// "SENSING" flag : transmission of the sensed values
		// You can use other flags for your algorithms
		if (message.getFlag().equals("INIT")) {
			// if not yet in the tree
			if (parent == null) {
				// enter the tree
				parent = message.getSender();
				getCommonLinkWith(parent).setWidth(4);
				// propagate further
				sendAll(message);
			}
		} else if (message.getFlag().equals("SENSING")) {
			// Si le message reçu est un capteur en état d'alerte alors on le rajoute à la
			// liste d'alerte
			if (message.getContent() instanceof Critical) {
				if (battery <= 100) {
					((Critical) message.getContent()).alertedSensors.put(this, getBattery());
				}
			}

			send(parent, message);
		}
	}

	@Override
	public void send(Node destination, Message message) {
		if (battery > 0) {
			super.send(destination, message);
			battery--;
			updateColor();
		}
	}

	@Override
	public void onClock() {

		if (parent != null) { // if already in the tree
			if (Math.random() < 0.02) { // from time to time...
				Critical critic = new Critical();
				if (battery <= 100) {
					critic.alertedSensors.put(this, getBattery());// On ajoute le capteur en alerte et son niveau de
																	// batterie
				}
				send(parent, new Message(critic, "SENSING")); // send it to parent
			}
		}
	}

	protected void updateColor() {
		setColor(battery == 0 ? Color.red : new Color(255 - battery, 255 - battery, 255));
	}
}
