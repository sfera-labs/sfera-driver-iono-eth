/**
 * 
 */
package cc.sferalabs.sfera.drivers.iono.events;

import cc.sferalabs.sfera.events.Node;

/**
 *
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
public class IonoDIEvent extends IonoDigitalEvent implements IonoInputEvent {

	/**
	 * @param source
	 * @param index
	 * @param value
	 */
	public IonoDIEvent(Node source, int index, Object value) {
		super(source, "di" + index, value);
	}

}
