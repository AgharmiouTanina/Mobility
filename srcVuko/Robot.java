import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import io.jbotsim.core.Message;
import io.jbotsim.core.Node;
import io.jbotsim.core.Point;
import io.jbotsim.ui.icons.Icons;

/* En fonction de l'id du robot, on va vérifier certains noeuds et pas d'autres
 * A rajouter: chercher dans listNode, le noeud ayant le moins de batterie et envoyer le robot là-bas(ou même le noeud à plus grande distance ayant le moins de batterie)
 */

public class Robot extends WaypointNode {
	int idRobot;
	int tmp = 0;
	ArrayList<Node> toRecharge;
	Point station;
	boolean park;
	Node max;

	@Override
	public void onStart() {
		idRobot = Main.robotId;
		toRecharge = new ArrayList<Node>();
		park = true;
		station = getLocation();
		setSensingRange(30);
		setIcon(Icons.ROBOT);
		setIconSize(16);
		onArrival();
	}

	@Override
	public void onSensingIn(Node node) {
		if (node instanceof Sensor) {
			((Sensor) node).battery = 255;
		}
	}

	@Override
	public void onMessage(Message message) {
		if (message.getContent() instanceof Red) {
			park = false;
			for (Map.Entry<Node, Integer> entry : ((Red) message.getContent()).al.entrySet()) {

				((Red) message.getContent()).listNode.add(entry.getKey());
				Collections.sort(((Red) message.getContent()).listNode);
				max = Collections.max(((Red) message.getContent()).listNode);
				if(max.getID() > 15 && this.getID() == 34 && max.distance(this) > 150) {
					System.out.println("Robot 1 id:" + this.getID());
					addDestination(max.getX(), max.getY());
				}
				if(max.getID() < 16 && this.getID() == 33 && max.distance(this) > 150) {
					System.out.println("Robot 0 id:" + this.getID());
					addDestination(max.getX(), max.getY());
				}

			}
			//System.out.println("ALERT  " + ((Red) message.getContent()).listNode);

			addDestination(station.getX(), station.getY());
		}

	}

	@Override
	public void onArrival() {
		park = true;
		// addDestination(Math.random() * 600, Math.random() * 400);
	}
}