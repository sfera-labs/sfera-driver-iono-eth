package cc.sferalabs.sfera.drivers.iono.events;

import cc.sferalabs.sfera.events.NumberEvent;
import cc.sferalabs.sfera.events.Node;

/**
 *
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
public abstract class IonoAnalogEvent extends NumberEvent {

	/**
	 * @param source
	 * @param id
	 * @param value
	 */
	public IonoAnalogEvent(Node source, String id, Object value) {
		super(source, id, (Number) value);
	}

}
