package cc.sferalabs.sfera.drivers.iono.events;

import cc.sferalabs.sfera.events.BooleanEvent;
import cc.sferalabs.sfera.events.Node;

/**
 * Abstract class for digital events.
 * 
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
public abstract class IonoDigitalEvent extends BooleanEvent {

	/**
	 * @param source
	 *            source node
	 * @param id
	 *            event ID
	 * @param value
	 *            new value
	 */
	public IonoDigitalEvent(Node source, String id, Object value) {
		super(source, id, ((Long) value) == 1);
	}

}