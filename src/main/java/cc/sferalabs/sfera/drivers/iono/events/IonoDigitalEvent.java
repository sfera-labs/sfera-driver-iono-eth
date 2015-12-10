package cc.sferalabs.sfera.drivers.iono.events;

import cc.sferalabs.sfera.events.BooleanEvent;
import cc.sferalabs.sfera.events.Node;

/**
 *
 * @author Giampiero Baggiani
 *
 * @version 1.0.0
 *
 */
public abstract class IonoDigitalEvent extends BooleanEvent {

	/**
	 * @param source
	 * @param id
	 * @param value
	 */
	public IonoDigitalEvent(Node source, String id, Object value) {
		super(source, id, ((Long) value) == 1);
	}

}