package source;

import io.jbotsim.core.Message;
import io.jbotsim.core.Node;
import io.jbotsim.ui.icons.Icons;

<<<<<<< HEAD
=======
/*
 * au début on construit l'arbre couvrant
 *
 * je dois envoyer le msg de sensing et determiner qui a participé a l'envoie
 * comme ca la station sait quel sensor a envoyé
 * stocker la liste des sensors comme ca la station peut savoir qui est l'emetteur
 *
 * qd un robot arrive à un capteur onArrival(), on recharge sa batterie
 */
>>>>>>> 33f76a1069338e8c0112a9dfdf3dcab670ff3b6b
public class BaseStation extends Node {
	boolean envoie;

	@Override
	public void onMessage(Message message) {
<<<<<<< HEAD
		int i = 0;
		if (message.getFlag().equals("SENSING")) {
			envoie = true;
			while (i < getNeighbors().size()) {
				if (getNeighbors().get(i) instanceof Robot) {
					if (((Robot) (getNeighbors().get(i))).park && envoie) {
						envoie = false;
						send(getNeighbors().get(i), message);
						// break;
					}
					break;
				}
				i++;
			}
		}
=======

		if (message.getFlag().equals("SENSING")) {
			envoie = true;
			for (int i = 0; i < getNeighbors().size(); i++) {
				if (getNeighbors().get(i) instanceof Robot) {
					if (((Robot) (getNeighbors().get(i))).park == true && envoie) {
						envoie = false;
						send(getNeighbors().get(i), message);
						break;
					}
				}
			}
		}

>>>>>>> 33f76a1069338e8c0112a9dfdf3dcab670ff3b6b
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