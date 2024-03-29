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

import io.jbotsim.core.Message;
import io.jbotsim.core.Node;
import io.jbotsim.ui.icons.Icons;

public class BaseStation extends Node {
	boolean envoie;

	@Override
	public void onMessage(Message message) {
		int i = 0;
		if (message.getFlag().equals("SENSING")) {
			envoie = true;
			// On compte le nombre de robots pour distribuer efficacement les capteurs à
			// recharger
			while (i < getNeighbors().size()) {
				if (getNeighbors().get(i) instanceof Robot) {
					if (((Robot) (getNeighbors().get(i))).park && envoie) {
						envoie = false;
						send(getNeighbors().get(i), message);

						break;
					}
				}
				i++;
			}
		}
	}

	@Override
	public void onStart() {
		envoie = false;
		setIcon(Icons.STATION);
		setIconSize(16);
		// Initiates tree construction with an empty message
		sendAll(new Message(this, "INIT"));
	}
}
