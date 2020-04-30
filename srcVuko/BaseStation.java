import io.jbotsim.core.Message;
import io.jbotsim.core.Node;
import io.jbotsim.ui.icons.Icons;

/*
 * au début on construit l'arbre couvrant
 *
 * je dois envoyer le msg de sensing et determiner qui a participé a l'envoie
 * comme ca la station sait quel sensor a envoyé
 * stocker la liste des sensors comme ca la station peut savoir qui est l'emetteur
 *
 * qd un robot arrive à un capteur onArrival(), on recharge sa batterie
 */
public class BaseStation extends Node {
	boolean envoie;

	@Override
	public void onMessage(Message message) {
		
		if (message.getFlag().equals("CRITICAL")) {
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
		else if (message.getFlag().equals("SENSING")) {
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
		else if (message.getFlag().equals("SENSING2")) {
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