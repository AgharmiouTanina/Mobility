package source;

import java.util.LinkedList;
import java.util.Queue;

import io.jbotsim.core.Node;
import io.jbotsim.core.Point;

public class WaypointNode extends Node {
	Queue<Point> destinations = new LinkedList<>();
	double speed = 1;

	public void addDestination(double x, double y) {
		destinations.add(new Point(x, y));
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public void onClock() {
		if (!destinations.isEmpty()) {
			Point dest = destinations.peek();
			if (distance(dest) > speed) {
				setDirection(dest);
				move(speed);
			} else {
				setLocation(dest);
				destinations.poll();
				onArrival();
			}
		}
	}

	public void onArrival() { // to be overridden
	}
}