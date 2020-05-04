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
	boolean park;// Cette variable permet de dire aux robots qu'ils sont à la station de base

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
		// Si le message reçu est de type Critical alors il y'a des capteurs en alerte
		if (message.getContent() instanceof Critical) {
			park = false;
			// On trie les capteurs en alerte par ordre de batterie, le capteur dont la
			// batterie est la plus petite est prioritaire
			Map<Node, Integer> sorted = (((Critical) message.getContent()).alertedSensors).entrySet().stream()
					.sorted(comparingByValue())
					.collect(toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new));
			// Une fois les capteurs triés, on envoie les robots pour les recharger
			for (Map.Entry<Node, Integer> entry : sorted.entrySet()) {
				addDestination(entry.getKey().getX(), entry.getKey().getY());
			}
			// Dans tous les cas, les robots reviennent à la station de base pour être à
			// jour
			addDestination(station.getX(), station.getY());
		}

	}

	@Override
	public void onArrival() {
		park = true;
	}
}
