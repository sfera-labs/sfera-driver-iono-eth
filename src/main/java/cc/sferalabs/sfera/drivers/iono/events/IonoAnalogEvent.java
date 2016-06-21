package cc.sferalabs.sfera.drivers.iono.events;

import cc.sferalabs.sfera.events.NumberEvent;
import cc.sferalabs.sfera.events.Node;

/**
 * Abstract class for analog events.
 * 
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
public abstract class IonoAnalogEvent extends NumberEvent {

	/**
	 * @param source
	 *            source node
	 * @param id
	 *            event ID
	 * @param value
	 *            new value
	 */
	public IonoAnalogEvent(Node source, String id, Object value) {
		super(source, id, (Number) value);
	}

}
