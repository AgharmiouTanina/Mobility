package source;

import io.jbotsim.core.LinkResolver;
import io.jbotsim.core.Node;
import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;

public class Main {
	public static void main(String[] args) {
		// Create topology with clock not started
		Topology tp = new Topology();
<<<<<<< HEAD
		long startTime = System.currentTimeMillis();
=======
>>>>>>> 33f76a1069338e8c0112a9dfdf3dcab670ff3b6b

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
		String filename = "topology/sensors2.tp"; // to be adapted
		String data = tp.getFileManager().read(filename);
		tp.getSerializer().importFromString(tp, data);

		// Add base station
		tp.addNode(100, 80, new BaseStation());

		// Add two robots
		tp.addNode(90, 40, new Robot());
		tp.addNode(60, 80, new Robot());

		new JViewer(tp);
		tp.start();
<<<<<<< HEAD

		long endTime = System.currentTimeMillis();

		System.out.println("That took " + (endTime - startTime) + " milliseconds");

=======
>>>>>>> 33f76a1069338e8c0112a9dfdf3dcab670ff3b6b
	}
}