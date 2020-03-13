package source;

import java.util.Collections;
import java.util.Map;

import io.jbotsim.core.Message;
import io.jbotsim.core.Node;
import io.jbotsim.core.Point;
import io.jbotsim.ui.icons.Icons;

public class Robot extends WaypointNode {
	Point station;
	boolean park;
	Node max;

	@Override
	public void onStart() {
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
				addDestination(max.getX(), max.getY());

			}
			System.out.println("ALERT  " + ((Red) message.getContent()).listNode);

			addDestination(station.getX(), station.getY());
		}

	}

	@Override
	public void onArrival() {
		park = true;
		// addDestination(Math.random() * 600, Math.random() * 400);
	}
}