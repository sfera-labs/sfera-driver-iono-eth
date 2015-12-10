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
public class IonoDOEvent extends IonoDigitalEvent implements IonoOutputEvent {

	/**
	 * @param source
	 * @param index
	 * @param value
	 */
	public IonoDOEvent(Node source, int index, Object value) {
		super(source, "do" + index, value);
	}

}
