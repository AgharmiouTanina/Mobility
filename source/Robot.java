package source;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

import java.util.LinkedHashMap;
import java.util.Map;

import io.jbotsim.core.Message;
import io.jbotsim.core.Node;
import io.jbotsim.core.Point;
import io.jbotsim.ui.icons.Icons;

public class Robot extends WaypointNode {
	Point station;
	boolean park;
//	List<Node> batt = new ArrayList<Node>();

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

			Map<Node, Integer> sorted = (((Red) message.getContent()).al).entrySet().stream().sorted(comparingByValue())
					.collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));

			for (Map.Entry<Node, Integer> entry : sorted.entrySet()) {
				// ((Red) message.getContent()).listNode.add(entry.getKey());
				// ((Red) message.getContent()).listBattery.add(entry.getValue());

				addDestination(entry.getKey().getX(), entry.getKey().getY());

			}
			// System.out.println(sorted);
			addDestination(station.getX(), station.getY());

		}

	}

	@Override
	public void onArrival() {
		park = true;
		// addDestination(Math.random() * 600, Math.random() * 400);
	}
}