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
public class IonoAVEvent extends IonoAnalogEvent implements IonoInputEvent {

	/**
	 * @param source
	 * @param index
	 * @param value
	 */
	public IonoAVEvent(Node source, int index, Object value) {
		super(source, "av" + index, value);
	}

}
