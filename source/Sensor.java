package source;

import java.util.List;

import io.jbotsim.core.Color;
import io.jbotsim.core.Message;
import io.jbotsim.core.Node;

public class Sensor extends Node {
	Node parent = null;
	int battery = 255;
	Node base;
	String str = "";
	List<Node> copy;

	public Node getBase() {
		return base;
	}

	public void setBase(Node station) {
		this.base = this.base == null ? station : this.base;
	}

	public int getBattery() {
		return battery;
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

			if (message.getContent() instanceof Red) {
				if (battery <= 100) {
					// ((Red) message.getContent()).listNode.add(this);
					((Red) message.getContent()).al.put(this, getBattery());
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
			double sensedValue = 0;
			if (Math.random() < 0.02) { // from time to time...
				sensedValue = Math.random(); // sense a value
				Red red = new Red();

				if (battery <= 100)
					// red.listNode.add(this);
					red.al.put(this, getBattery());
				send(parent, new Message(red, "SENSING")); // send it to parent

			}
		}
	}

	protected void updateColor() {
		setColor(battery == 0 ? Color.red : new Color(255 - battery, 255 - battery, 255));
	}
}