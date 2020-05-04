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

import java.util.HashMap;

import io.jbotsim.core.Node;

public class Critical {
	/*
	 * Key --> Critical Sensor
	 *
	 * Value --> Battery level
	 */
	HashMap<Node, Integer> alertedSensors = new HashMap<Node, Integer>();
}
