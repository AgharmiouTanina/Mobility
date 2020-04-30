
import java.util.List;

import io.jbotsim.core.LinkResolver;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;

public class Main {
	
	public static int robotId = 0;
	public static int maxNodes = 0;
	
	public static void main(String[] args) {
		// Create topology with clock not started
		Topology tp = new Topology();

		// Forbid communication between robots and sensors
		tp.setLinkResolver(new LinkResolver() {
			@Override
			public boolean isHeardBy(Node n1, Node n2) {
				if ((n1 instanceof Robot && n2 instanceof Sensor) || (n1 instanceof Sensor && n2 instanceof Robot))
					return false;
				else
					return super.isHeardBy(n1, n2);
			}
		});

		// Add sensors
		tp.setDefaultNodeModel(Sensor.class);
		String filename = "topology/sensors.tp"; // to be adapted
		String data = tp.getFileManager().read(filename);
		tp.getSerializer().importFromString(tp, data);

		// Add base station
		tp.addNode(100, 80, new BaseStation());

		// Add two robots
		tp.addNode(90, 40, new Robot());
		List<Node> noeuds = tp.getNodes();
		robotId = noeuds.size();
		tp.addNode(60, 80, new Robot());
		robotId++;
		noeuds = tp.getNodes();
		maxNodes = noeuds.size();

		new JViewer(tp);
		tp.start();
	}
}